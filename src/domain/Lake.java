package domain;

import common.Constants;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.Serializable;

public class Lake implements Serializable {
  private final char[][] lake = new char[Constants.MAZE_HEIGHT][Constants.MAZE_WIDTH];

  public Lake() {
    super();
  }

  public final char[][] getLake() {
    final File file = new File("resources/frozen-lake.txt");
    try (final FileInputStream fileInputStream = new FileInputStream(file)) {
      int i = 0;
      int j = 0;
      int content;
      while ((content = fileInputStream.read()) != -1) {
        final char c = (char) content;
        if (c != Constants.START_STEP
            && c != Constants.AVAILABLE_STEP
            && c != Constants.IMPOSSIBLE_STEP
            && c != Constants.END_STEP) {
          continue;
        }
        lake[i][j] = c;
        j++;
        if (j == Constants.MAZE_WIDTH) {
          j = 0;
          i++;
        }
      }
      fileInputStream.close();
    } catch (final IOException e) {
      e.printStackTrace();
    }

    return lake;
  }
}
