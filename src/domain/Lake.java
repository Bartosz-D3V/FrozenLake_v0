package domain;

import common.Constants;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.Serializable;

public class Lake implements Serializable {
  private char[][] lake;

  public Lake() {
    super();
  }

  public final char[][] getLake() throws IOException {
    lake = new char[Constants.mazeHeight][Constants.mazeWidth];
    final File file = new File("resources/frozen-lake.txt");
    try (final FileInputStream fileInputStream = new FileInputStream(file)) {
      int i = 0;
      int j = 0;
      int content;
      while ((content = fileInputStream.read()) != -1) {
        final char c = (char) content;
        if (c == Constants.endStep) {
          break;
        }
        lake[i][j] = c;
        ++j;
        if (j == Constants.mazeHeight) {
          j = 0;
          ++i;
        }
      }
      fileInputStream.close();
    } catch (final IOException e) {
      e.printStackTrace();
    }

    return lake;
  }
}
