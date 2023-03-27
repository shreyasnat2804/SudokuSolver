import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.awt.*;
import javax.swing.*;

public class Driver {
  // tester main
  public static void main(String[] args) {
    File text = new File("/Users/shreyasnatarajan/Desktop/PersonalProjects/SudokuSolver/table.txt");
    Scanner scnr = null;
    try {
      scnr = new Scanner(text);
    } catch (FileNotFoundException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    Table dispTable = new Table(text);
    dispTable.makeTable();

    
    dispTable.display(false);
    
    dispTable.solve();


    // System.out.println(dispTable.toString());
    dispTable.display(false);
    
    
    

  }



}
