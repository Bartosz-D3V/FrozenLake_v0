import algorithm.FrozenLake;
import domain.Lake;

public final class Main {

  public static void main(final String[] args) {
    final Lake lake = new Lake();
    final FrozenLake frozenLake = new FrozenLake(lake.getLake());
    frozenLake.populateRewardLookupTable();
    frozenLake.calcQ();
  }
}
