import java.util.Scanner;

public class test_14430 {
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		 
		int yMax = scan.nextInt();
		int xMax = scan.nextInt();
		
		int[][] resourses = new int[yMax][xMax];
		int[][] count = new int[yMax+1][xMax+1]; 
				
		for(int y=0; y<yMax; y++) {
			for(int x=0; x<xMax; x++) {
				resourses[y][x] = scan.nextInt();
			}
		}
		
		for(int y=0; y<yMax; y++) {
			int tmpY = y+1;
			for(int x=0; x<xMax; x++) {
				int tmpX = x+1;
				count[tmpY][tmpX] = resourses[y][x] + Math.max(count[tmpY][tmpX-1], count[tmpY-1][tmpX]);
			}
		}
		
		System.out.println(count[yMax][xMax]);
		scan.close();
	}
}
