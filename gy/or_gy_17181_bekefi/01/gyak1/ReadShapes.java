package gyak1;

import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;

class ReadShapes {

	public static void main(String[] args) {
		List<Shape> shapes = new ArrayList<>();
		Scanner sc = new Scanner(System.in);
		String text = "";
		do {
			text = sc.nextLine();
			if (text.contains("add")) {
				String data[] = text.split(" "); // 0-add, 1-square, 
				switch (data[1]) {
					case "square": 
						Square s = new Square(Integer.parseInt(data[2]), Integer.parseInt(data[3]), Integer.parseInt(data[4]));
						shapes.add(s);
						break;
					case "circle":
						Circle c = new Circle(Integer.parseInt(data[2]), Integer.parseInt(data[3]), Integer.parseInt(data[4]));
						shapes.add(c);
						break;
				}
			}
		}
		while(!text.equals("quit"));
		for (Shape s : shapes) {
			System.out.println(s.toString());
		}
	}

}

