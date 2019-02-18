import java.io.Serializable;

public class Point implements Serializable {

    public long serialVersionUID = 1234567;
    
    private int x;
    private int y;

    public Point(int x, int y) {
	this.x = x;
	this.y = y;
    }

    @Override
    public String toString() {
	return "(" + x + "," + y + ")";
    }

    public void move(int dx, int dy) {
	x += dx;
	y += dy;
    }
}