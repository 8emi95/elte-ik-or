package gyak1;

class Shape {
    int x;
	int y;
    protected double area;
    protected double circumference;

    public Shape(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public double getArea() {
        return area;
    }

    public double getCircumference() {
        return circumference;
    }

    public String toString() {
        return String.format("center: "+x+", "+y+", area: "+area+", circumference: "+circumference);
    }
}