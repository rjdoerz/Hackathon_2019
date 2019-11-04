package workbench;

import java.util.Random;

import model.Coordinate;
import model.Tile;

public class TwoDArrDemo {

	public static void main(String[] args) {
//		oneDimArr();
//		twoDimArr();
		loopNest();
	}

	private static void oneDimArr() {
		Random random = new Random();
		
		int points = 3;
		int size = 20;
		String arr[] = new String[size];
		arr[0] = "S";
		arr[arr.length-1] = "E";
		
		int min = 1;
		int max, bracket;
		bracket = (arr.length - 2) / points;
		
		for(int i = 0; i < points; i++) {
			max = (bracket * (i+1));
			
			int j = min;
			for(; j <= max; j++) {
				arr[j] = String.valueOf(i);
			}
			arr[random.nextInt(max - min) + min] = "x";
//			System.out.println(i + ": " + min + " - " + max);
//			System.out.println(random.nextInt(max - min) + min);
			min = max + 1;
		}
		
		for (String e : arr) {
			System.out.print(e + " ");			
		}		
	}

	private static void twoDimArr() {
		Random random = new Random();
		
		int points = 4;	// numbOfPoints
		int size = 10;	// grid size
		String arr[][] = new String[size][size];
		
		// Stylization
		for(int i = 0; i < arr[0].length; i++) {
			arr[i][0] = "-";
		}
		for(int i = 0; i < arr[arr.length-1].length; i++) {
			arr[i][arr.length-1] = "-";
		}
		
		// Start and End points
		arr[random.nextInt(size)][0] = "S";
		arr[random.nextInt(size)][arr.length-1] = "E";
		
		// Variable declarations and assignments
		int min = 1;
		int max, bracket;
		bracket = (arr.length - 2) / points;
		
		for(int i = 0; i < points; i++) {
			max = (bracket * (i+1));
			
			int j = min;											//
			for(; j <= max; j++) {									//
				for(int p = 0; p < arr[arr.length-1].length; p++) {	//	Populating array 
					arr[p][j] = String.valueOf(i);					//
				}													//
			}
			int r = random.nextInt(max - min + 1) + min;					//
			System.out.println(i + ": " + min + " - " + max + " : " + r);	//	Select random node in bracket
			arr[random.nextInt(arr.length)][r] = "_";						// 
			
			// Update bracket
			min = max + 1;	
		}
		
		for (String e[] : arr) {
			for(String t : e) {
				System.out.print(t + "  ");			
			}
			System.out.println();
		}	
	}
	
	private static void loopNest() {
		int max = 10;
		Tile[][] arr = new Tile[max][max];
		for(int i = 0; i < max; i++) {
			for(int j = 0; j < max; j++) {
//				arr[j][i] = c++;
				arr[i][j] = new Tile(new Coordinate(i, j));
			}
		}
		printer(arr);
		System.out.println("\n" + arr[3][5].getCoordinate());
	}
	
	private static  void printer(Tile[][] arr) {
		int x = 0;
		for(Tile[] a : arr) {
			System.out.print("Arr " + x++ + ": ");
			for(Tile n : a) {
//				System.out.printf("%2d ", n);
				System.out.print(n.getCoordinate() + " ");
			}
			System.out.println();
		}
		
		
		
//		for(int i = 0; i < arr.length; i++) {
//			for(int j = 0; j < arr.length; j++) {
//				System.out.print(arr[j][i] + " ");
//			}
//			System.out.println();
//		}
	}
}
