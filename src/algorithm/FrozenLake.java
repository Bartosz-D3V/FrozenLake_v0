package algorithm;

import common.Constants;

import java.io.Serializable;

public class FrozenLake implements Serializable {
  private final char[][] lake;
  private final char[][] rewardLookup = new char[Constants.states][Constants.states];

  public FrozenLake(final char[][] lake) {
    this.lake = lake;
  }

  private void calcQ() {

  }

  private boolean isFinalState(final int index) {
    final int i = index / Constants.mazeWidth;
    final int j = index / Constants.mazeHeight;

    return lake[i][j] == Constants.endStep;
  }
}
