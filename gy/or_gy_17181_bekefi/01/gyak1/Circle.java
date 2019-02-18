package gyak1;

class Circle extends Shape {
    private int radius;

    public Circle(int x, int y, int radius) {
        super(x, y);
        this.radius = radius;
        calculate();
    }

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        if (radius > 0) {
            this.radius = radius;
            calculate();
        }
    }

    private void calculate() {
        super.area          = radius * radius * Math.PI;
        super.circumference = 2 * radius * Math.PI;
    }

    public String toString() {
		return "Cricle: "+super.toString()+", radius: "+radius;
    }
}