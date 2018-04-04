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

  /**
   * Construct FrozenLake class based on
   * lake of type char[][]
   *
   * @param lake lake
   */
  public FrozenLake(final char[][] lake) {
    this.lake = lake;
  }

  /**
   * Populate reward lookup table (R) with
   * values calculated based on Q learning algorithm
   * formula
   */
  public void populateRewardLookupTable() {
    for (int k = 0; k < Constants.STATES; k++) {
      final int i = k / Constants.MAZE_WIDTH;
      final int j = k - i * Constants.MAZE_WIDTH;

      // Try to move in all directions if not in a final state
      if (lake[i][j] != Constants.END_STEP) {

        // Move left from current position in frozen lake
        final int moveLeft = j - 1;
        if (moveLeft > -1) {
          moveHorizontally(k, i, moveLeft);
        }

        // Move right from current position in frozen lake
        final int moveRight = j + 1;
        if (moveRight < Constants.MAZE_WIDTH) {
          moveHorizontally(k, i, moveRight);
        }

        // Move up from current position in frozen lake
        final int moveUp = i - 1;
        if (moveUp > -1) {
          moveVertically(k, j, moveUp);
        }

        // Move down from current position in frozen lake
        final int moveDown = i + 1;
        if (moveDown < Constants.MAZE_HEIGHT) {
          moveVertically(k, j, moveDown);
        }
      }
    }
  }

  /**
   * Populate Q array with calculated Q values
   */
  public void calcQ() {
    final Random random = new Random();
    for (int i = 0; i < Settings.CYCLES; i++) {
      int currentState = random.nextInt(Constants.STATES);

      while (!isFinalState(currentState)) {
        // Retrieve all available actions from current state
        final int[] possibleActions = getPossibleActionsFromState(currentState);

        // Select any random action from possibleActions
        final int newRandomAction = random.nextInt(possibleActions.length);
        // Check new state that corresponds to newRandomAction
        final int nextState = possibleActions[newRandomAction];

        /*
         Calculate Q value accordingly to the formula:
         Q(state, action) = Q(state, action) + alpha * (R(state, action) +
         gamma * Max(next state, all possible actions) - Q(state, action))
        */
        final double tempQ = q[currentState][nextState];
        final double tempMaxQ = calcMaxQ(nextState);
        final double tempR = rewardLookup[currentState][nextState];
        final double formulaResult =
          tempQ + Settings.ALPHA * (tempR + Settings.GAMMA * tempMaxQ - tempQ);
        q[currentState][nextState] = formulaResult;

        currentState = nextState;
      }
    }
  }

  /**
   * Move either left or right in a reward lookup table
   *
   * @param k              k
   * @param i              i
   * @param horizontalMove horizontalMove
   */
  private void moveHorizontally(final int k, final int i, final int horizontalMove) {
    final int target = i * Constants.MAZE_WIDTH + horizontalMove;
    if (lake[i][horizontalMove] == 'O') {
      rewardLookup[k][target] = Settings.PENALTY;
    } else if (lake[i][horizontalMove] == 'E') {
      rewardLookup[k][target] = Settings.REWARD;
    } else {
      rewardLookup[k][target] = 1;
    }
  }

  /**
   * Move either up or down in a reward lookup table
   *
   * @param k            k
   * @param j            j
   * @param verticalMove verticalMove
   */
  private void moveVertically(final int k, final int j, final int verticalMove) {
    final int target = verticalMove * Constants.MAZE_WIDTH + j;
    if (lake[verticalMove][j] == 'O') {
      rewardLookup[k][target] = Settings.PENALTY;
    } else if (lake[verticalMove][j] == 'E') {
      rewardLookup[k][target] = Settings.REWARD;
    } else {
      rewardLookup[k][target] = 1;
    }
  }

  /**
   * Return maximum Q values for all available actions from a given state
   *
   * @param nextState nextState
   * @return int nextState
   */
  private double calcMaxQ(final int nextState) {
    final int[] possibleActionsFromState = getPossibleActionsFromState(nextState);
    // maxVal initially set to the lowest possible value
    double maxVal = Double.MIN_VALUE;
    for (final int possibleNextAction : possibleActionsFromState) {
      final double currentValue = q[nextState][possibleNextAction];

      if (currentValue > maxVal) {
        maxVal = currentValue;
      }
    }
    return maxVal;
  }

  /**
   * Select best action from a given state
   *
   * @param currentState currentState
   * @return int currentState
   */
  private int getNextMovement(final int currentState) {
    final int[] actionsFromState = getPossibleActionsFromState(currentState);
    int nextMovement = currentState;
    // maxValue initially set to the lowest possible value
    double maxValue = Double.MIN_VALUE;

    for (final int nextPossibleState : actionsFromState) {
      final double movementValue = q[currentState][nextPossibleState];
      if (movementValue > maxValue) {
        maxValue = movementValue;
        nextMovement = nextPossibleState;
      }
    }

    return nextMovement;
  }

  /**
   * Get possible states from a given state
   *
   * @param state state
   * @return int[] possibleActions
   */
  private int[] getPossibleActionsFromState(final int state) {
    final List<Integer> possibleActions = new ArrayList<>();
    for (int i = 0; i < Constants.STATES; i++) {
      if (rewardLookup[state][i] != -1) {
        possibleActions.add(i);
      }
    }
    return possibleActions.stream().mapToInt(i -> i).toArray();
  }

  /**
   * Checks whether given index is a final step
   *
   * @param index index
   * @return boolean isFinalState
   */
  private boolean isFinalState(final int index) {
    final int i = index / Constants.MAZE_WIDTH;
    final int j = index - i * Constants.MAZE_WIDTH;

    return lake[i][j] == Constants.END_STEP;
  }

  /**
   * Print all states
   */
  public void printSelectedStates() {
    for (int state = 0; state < Constants.STATES; state++) {
      final int nextState = getNextMovement(state);
      System.out.println("From state " + state + " go to state " + nextState);
    }
  }
}
