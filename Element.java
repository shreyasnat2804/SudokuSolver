
public class Element {
  // defining fields
  private int x, y;
  private int value;

  public Element(int x, int y) {
    this.x = x;
    this.y = y;
  }

  // getters and setters
  public int getX() {
    return this.x;
  }

  public int getY() {
    return this.y;
  }

  public int getValue() {
    return this.value;
  }

  public void setX(int x) {
    this.x = x;
  }

  public void setY(int y) {
    this.y = y;
  }

  public void setValue(int val) {
    this.value = val;
  }

  @Override
  public String toString() {
    return ((Integer) this.value).toString();
  }

}
