package algorithm;

import common.Constants;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public final class FrozenLake implements Serializable {
  private final char[][] lake;
  private final double[][] rewardLookup = new double[Constants.STATES][Constants.STATES];
  private final double[][] q = new double[Constants.STATES][Constants.STATES];

  public FrozenLake(final char[][] lake) {
    this.lake = lake;
  }

  public void populateRewardLookupTable() {
    for (int k = 0; k < Constants.STATES; k++) {
      final int i = k / Constants.MAZE_WIDTH;
      final int j = k - i * Constants.MAZE_WIDTH;

      // Initially, we set each element in lake with -1
      for (int s = 0; s < Constants.STATES; s++) {
        rewardLookup[k][s] = -1;
      }

      // Try to move in all directions
      if (lake[i][j] != Constants.END_STEP) {
        final int moveLeft = j - 1;
        if (moveLeft > -1) {
          moveHorizontally(k, i, moveLeft);
        }

        final int moveRight = j + 1;
        if (moveRight < Constants.MAZE_WIDTH) {
          moveHorizontally(k, i, moveRight);
        }

        final int moveUp = i - 1;
        if (moveUp > -1) {
          moveVertically(k, j, moveUp);
        }

        final int moveDown = i + 1;
        if (moveDown < Constants.MAZE_HEIGHT) {
          moveVertically(k, j, moveDown);
        }
      }
    }
  }

  public void calcQ() {
    final Random random = new Random();
    for (int i = 0; i < Settings.CYCLES; i++) {
      int currentState = random.nextInt(Constants.STATES);

      while (!isFinalState(i)) {
        // Retrieve all available actions from current state
        final int[] possibleActions = getPossibleActionsFromState(i);

        // Select any random action from possibleActions
        final int newRandomAction = random.nextInt(possibleActions.length);
        // Check new state that corresponds to newRandomAction
        final int nextState = possibleActions[newRandomAction];

        /*
          Calculate Q value accordingly to the formula:
          Q(state, action) = Q(state, action) + alpha * (R(state, action) +
          gamma * Max(next state, all possible actions) - Q(state, action))
         */
        final double tempQ = q[currentState][newRandomAction];
        final double tempMaxQ = calcMaxQ(nextState);
        final double tempR = rewardLookup[currentState][nextState];
        final double formulaResult = tempQ + Settings.ALPHA + tempR +
          Settings.GAMMA * (tempR + Settings.GAMMA * tempMaxQ - tempQ);
        q[currentState][nextState] = formulaResult;

        currentState = nextState;
      }
    }
  }

  private void moveHorizontally(final int k, final int i, final int horizontalMove) {
    final int target = i * Constants.MAZE_WIDTH + horizontalMove;
    if (lake[i][horizontalMove] == 'O') {
      rewardLookup[k][target] = Settings.PENALTY;
    } else if (lake[i][horizontalMove] == 'E') {
      rewardLookup[k][target] = Settings.REWARD;
    } else if (lake[i][horizontalMove] == 'F') {
      rewardLookup[k][horizontalMove] = 0;
    } else {
      rewardLookup[k][horizontalMove] = 0;
    }
  }

  private void moveVertically(final int k, final int j, final int verticalMove) {
    final int target = verticalMove * Constants.MAZE_WIDTH + j;
    if (lake[verticalMove][j] == 'O') {
      rewardLookup[k][target] = Settings.PENALTY;
    } else if (lake[verticalMove][j] == 'E') {
      rewardLookup[k][target] = Settings.REWARD;
    } else if (lake[verticalMove][j] == 'F') {
      rewardLookup[k][j] = 0;
    } else {
      rewardLookup[k][j] = 0;
    }
  }

  private double calcMaxQ(final int nextState) {
    final int[] possibleActionsFromState = getPossibleActionsFromState(nextState);
    // maxVal initially set to the lowest possible value
    double maxVal = Double.MIN_VALUE;
    for (int possibleNextAction : possibleActionsFromState) {
      final double currentValue = q[nextState][possibleNextAction];

      if (currentValue > maxVal) {
        maxVal = currentValue;
      }
    }
    return maxVal;
  }

  private int getNextMovement(final int currentState) {
    final int[] actionsFromState = getPossibleActionsFromState(currentState);
    int nextMovement = currentState;
    // maxValue initially set to the lowest possible value
    double maxValue = Double.MIN_VALUE;

    for (int nextPossibleMovement : actionsFromState) {
      final double movementValue = q[currentState][nextMovement];
      if (movementValue > maxValue) {
        maxValue = movementValue;
        nextMovement = nextPossibleMovement;
      }
    }

    return nextMovement;
  }

  private int[] getPossibleActionsFromState(final int state) {
    final List<Integer> possibleActions = new ArrayList<>();
    for (int i = 0; i < Constants.STATES; i++) {
      if (rewardLookup[state][i] != -1) {
        possibleActions.add(i);
      }
    }
    return possibleActions.stream().mapToInt(i -> i).toArray();
  }

  private boolean isFinalState(final int index) {
    final int i = index / Constants.MAZE_WIDTH;
    final int j = index / Constants.MAZE_HEIGHT;

    return lake[i][j] == Constants.END_STEP;
  }
}
