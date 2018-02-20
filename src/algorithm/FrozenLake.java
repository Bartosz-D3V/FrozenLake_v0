package algorithm;

import common.Constants;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public final class FrozenLake implements Serializable {
  private final char[][] lake;
  private final char[][] rewardLookup = new char[Constants.STATES][Constants.STATES];
  private final char[][] q = new char[Constants.STATES][Constants.STATES];

  public FrozenLake(final char[][] lake) {
    this.lake = lake;
  }

  private void calcQ() {
    final Random random = new Random();
    for (int i = 0; i < Settings.CYCLES; ++i) {
      final int initialState = random.nextInt(Constants.STATES);

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
        final double tempQ = q[initialState][newRandomAction];
      }
    }
  }

  private int[] getPossibleActionsFromState(final int state) {
    final List<Integer> possibleActions = new ArrayList<>();
    for (int i = 0; i < Constants.STATES; ++i) {
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
