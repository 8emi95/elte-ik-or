package gyak1;

class Square extends Shape {
    private int side;

    public Square(int x, int y, int side) {
        super(x, y);
        this.side = side;
        calculate();
    }

    public int getSide() {
        return side;
    }

    public void setSide(int side) {
        if (side > 0) {
            this.side = side;
            calculate();
        }
    }

    private void calculate() {
        super.area          = side * side;
        super.circumference = 4 * side;
    }

    public String toString() {
		return "Square: "+super.toString()+", side: "+side;
    }
}