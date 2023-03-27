import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

// this class defines a sudoku table from an inputed file
public class Table {

  private Element[][] table = new Element[9][9];
  File text;
  Scanner sc;

  public Table(File text) {
    this.text = text;
  }

  public Table() {

  }

  public Element[][] getTable() {
    return table;
  }

  // /**
  // * Displays the table
  // */
  // public void display() {
  //
  // // printing a line on top
  // System.out
  // .println("\n-------------------------------------------------------------------------");
  //
  // for (Element[] i : this.table) {
  // System.out.print("| ");
  // for (Element j : i) {
  //
  // // printing a blank if it is 0
  // if (j.getValue() == 0) {
  // System.out.print(" | ");
  // } else {
  // System.out.print(j.getValue() + " | ");
  // }
  //
  //
  // }
  //
  // // printing a new l;ine of every line
  // System.out.print("\n");
  //
  // // printing next line to space it out
  // for (int j = 0; j < 9; j++) {
  // System.out.print("| ");
  // }
  //
  // System.out.println(
  // "|
  // \n-------------------------------------------------------------------------");
  // }
  // }

  /**
   * Displays the table
   */
  public void display(boolean editable) {

    JFrame frame = new JFrame("Sudoku Table");
    JPanel panel = new JPanel();
    JTextField[][] fields = new JTextField[9][9];
    // Create the main window

    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    // Create a panel to hold the Sudoku grid
    panel.setLayout(new GridLayout(9, 9));

    // Create the text fields for the Sudoku grid

    for (int row = 0; row < 9; row++) {
      for (int col = 0; col < 9; col++) {
        if (this.table[row][col].getValue() == 0) {
          fields[row][col] = new JTextField("");
        } else {
          fields[row][col] = new JTextField(this.table[row][col].toString());
        }

        fields[row][col].setHorizontalAlignment(JTextField.CENTER);
        panel.add(fields[row][col]);
      }
    }

    // Add the panel to the main window
    frame.add(panel);

    // making sure the user can or cannot change it
    panel.setEnabled(editable);

    // if (!editable)
    // panel.setBackground(Color.LIGHT_GRAY);
    // else {
    panel.setBackground(Color.GRAY);
    // }

    // setting size
    panel.setPreferredSize(new Dimension(400, 400));

    // Display the window
    frame.pack();
    frame.setVisible(true);

  }

  /**
   * Takes input from a file and converts it into an array
   * 
   * @param f , the file to extract the table from
   */
  public void makeTable() {

    try {
      sc = new Scanner(text);
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }

    // looping through file

    // counter variable to know which row we are filling in
    int countRow = 0;
    while (text != null && sc.hasNextLine()) {
      // this is a line of numbers
      String line = sc.nextLine();

      // add this line to the row
      int[] arr = stringToArr(line);
      for (int i = 0; i < 9; i++) {
        this.table[countRow][i] = new Element(i, countRow);
        this.table[countRow][i].setValue(arr[i]);

      }
      countRow += 1;
    }
    sc.close();
  }

  /**
   * Takes one line of a string and converts it into one row on the table
   * 
   * @param nums the string of numbers to make into a row
   */

  private int[] stringToArr(String nums) {
    int[] ret = new int[9];
    char[] chArr = nums.toCharArray();
    int counter = 0;
    for (char ch : chArr) {
      // if the character is a number
      if ((int) ch >= 48 && (int) ch <= 57) {
        // add it to the int array
        ret[counter] = ((int) ch) - 48;
        counter++;
      }
    }
    return ret;
  }

  /**
   * getter method to get the 3 by 3 box a number is in
   * 
   * @param x the x coordinate of the number
   * @param y the x coordinate of the number
   */
  public Element[][] get3By3(Element[][] tempTable, int x, int y) {
    Element[][] ret = new Element[3][3];
    // using integer division
    x /= 3;
    y /= 3;
    x *= 3;
    y *= 3;
    int xCount = 0, yCount = 0;
    for (int i = y; i < y + 3; i++) {
      xCount = 0;
      for (int j = x; j < x + 3; j++) {
        ret[yCount][xCount] = new Element(xCount, yCount);
        ret[yCount][xCount].setValue(tempTable[i][j].getValue());
        ;
        xCount++;
      }
      yCount++;
    }
    return ret;
  }

  public boolean numIsValid(Element[][] tempTable, int x, int y, int num) {

    for (int i = 0; i < 9; i++) {

      // checking row
      if (num != 0 && tempTable[y][i].getValue() == num) {
        return false;
      }

      // checking col
      if (num != 0 && tempTable[i][x].getValue() == num) {
        return false;
      }
    }

    // checking 3 by 3 box

    Element[][] subArray = this.get3By3(tempTable, x, y);
    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 3; j++) {
        if (subArray[i][j].getValue() != 0 && subArray[i][j].getValue() == num) {
          return false;
        }
      }
    }

    return true;
  }

  /**
   * This method solves this table.
   */
  public void solve() {

    // calling solverHelper to recurse through all elements
    if (this.solveHelper(table, 0, 0)) {
      return;
    } else {
      System.out.println("ERROR");
    }

  }

  /**
   * this method solves a certain element and recurses throughout the board
   * 
   * @param x
   * @param y
   */
  public boolean solveHelper(Element[][] tempTable, int x, int y) {

    // if reached the last column,
    if (y == 9) {
      return true;
    }

    // storing next column to recur
    int nextRow = 0;
    int nextCol = 0;

    if (x != 8) {

      nextRow = y;
      nextCol = x + 1;
    } else {

      nextRow = y + 1;
      nextCol = 0;
    }

    //if the current element is solved
    if (this.isSolved(tempTable[y][x])) {
      if (solveHelper(tempTable, nextCol, nextRow)) {
        return true;
      }
    } else {
      for (int i = 1; i <= 9; i++) {

        //loop through its possibilities
        if (this.numIsValid(tempTable, x, y, i)) {

          tempTable[y][x].setValue(i);

          if (solveHelper(tempTable, nextCol, nextRow)) {
            return true;
          } else {
            //if it does not wor, set it back to 0
            this.table[y][x].setValue(0);
          }
        }
      }
    }
    return false;

  }

  public boolean isSolved(Element currElement) {
    return currElement.getValue() != 0;
  }

  // to test
  public void display3by3(Element[][] arr) {
    System.out.println("\n-----------------------");
    for (Element[] i : arr) {
      System.out.print("|  ");
      for (Element j : i) {
        if (j.getValue() == 0) {
          System.out.print("    |  ");
        } else {
          System.out.print(j.getValue() + "   |  ");
        }
      }
      System.out.print("\n");
      for (int j = 0; j < 3; j++) {
        System.out.print("|      ");
      }
      System.out.println("|       \n-----------------------");
    }
  }

  @Override
  public String toString() {

    String ret = "";

    for (int i = 0; i < 9; i++) {
      for (int j = 0; j < 9; j++) {
        ret += this.table[i][j].toString() + " ";
      }
      ret += "\n";
    }

    return ret;

  }

}
