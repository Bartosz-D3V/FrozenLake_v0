package common;

import java.io.Serializable;

public final class Constants implements Serializable {
  public static final short MAZE_HEIGHT = 3;
  public static final short MAZE_WIDTH = 4;
  public static final short STATES = MAZE_HEIGHT * MAZE_WIDTH;
  public static final char START_STEP = 'S';
  public static final char END_STEP = 'E';
  public static final char AVAILABLE_STEP = 'F';
  public static final char IMPOSSIBLE_STEP = 'O';
}
