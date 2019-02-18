import java.io.Serializable;

public class Circle implements Serializable {

    private static final long serialVersionUID = 12345L; // automatikusan is van, de mi is allitgathatjuk, a server-kliensek csak akkor hajlandoak egymassal kommunikalni, ha a ket serialVersion megegyezik
    
    private Point cp;
    private int r;
    private transient Double c = null; // kiszamolhato a tobbibol -> serializacional nem kell kiirni -> transient modosito szo

    public Circle(Point cp, int r) {
	this.cp = cp;
	this.r = r;
    }

    public double getC() {
	if (null == c) {
	    c = new Double(2 * r * Math.PI);
	}
	return c.doubleValue();
    }

    public String toString() {
	return cp + " r: " + r + " area: " + c;
    }

    public void move(int dx, int dy) {
	cp.move(dx, dy);
    }

}