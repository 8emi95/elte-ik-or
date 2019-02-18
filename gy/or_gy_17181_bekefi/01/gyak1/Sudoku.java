package gyak1;

import java.util.Arrays;

class Sudoku {
	
	public static boolean check(byte[][] square) {
		boolean result = true;
		// 3x3 ?
		result &= (square.length == 3);
		for (byte[] row : square) {
			result &= (row.length == 3);
		}
		
		// for all x: x in [1..9] ?
		for (int i = 0; i < square.length; i++) {
			for (int j = 0; j < square[i].length; j++) {
				result &= (1 <= square[i][j] && square[i][j] <= 9);
			}
		}

		// for all i, j, k, l: square[i][j] /= square[k][l], if i /= k && j /= l
		byte[] frequencies = new byte[9];
		for (int i = 0; i < frequencies.length; i++) frequencies[i] = 0;
		for (int i = 0; i < square.length; i++) {
			for (int j = 0; j < square[i].length; j++) {
				frequencies[square[i][j] - 1] += 1;
			}
		}		
		for (int i = 0; i < frequencies.length; i++) {
			result &= (frequencies[i] < 2);
		}

		return result;
	}
	
	public static String show(byte[][] square) {
		String text = "";
		for (int i=0; i<square.length; i++) {
			for (int j=0; j<square[i].length; j++) {
				text += square[i][j]+" ";
			}
			text += "\n";
		}
		return text;
	}
	
	public static void main(String[] args) {
		
	}
}