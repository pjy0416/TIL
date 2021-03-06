import java.util.Scanner;

public class Question1 {
	static String black ="1";
	static String white = "0";	
	
	private static int firstBlack(String[] tiles, String firstTile) {
		int len = tiles.length;
		int changeCount=0;
		
		if(firstTile.equals(white)) {
//			System.out.println("callblack");
			changeCount++;
		}
		
		for(int i=1; i<len-1; i++) {
			if(i%2 !=0) {	// �ι�° ���� ¦��Ÿ�� 
				if(tiles[i].equals(black)) {	// ¦�� Ÿ���� ����̸� count
//					System.out.println("black- ¦�� black ī��Ʈ");
					changeCount++;
				}
			}else {			// ����° ���� Ȧ�� Ÿ��
				if(tiles[i+1].equals(white)) {	// Ȧ�� Ÿ���� ����̸� count
//					System.out.println("black- Ȧ�� white ī��Ʈ");
					changeCount++;
				}
			}
		}
		
		return changeCount;
	}
	
	private static int firstWhite(String[] tiles, String firstTile) {
		int len = tiles.length;
		int changeCount=0;
		
		if(firstTile.equals(black)) {
//			System.out.println("callWhite");
			changeCount++;
		}
		
		for(int i=1; i<len-1; i++) {
			if(i%2 !=0) {	// �ι�° ���� ¦��Ÿ�� 
				if(tiles[i].equals(white)) {	// ¦�� Ÿ���� ����̸� count
//					System.out.println("white- ¦�� white ī��Ʈ");
					changeCount++;
				}
			}else {			// ����° ���� Ȧ�� Ÿ��
				if(tiles[i+1].equals(black)) {	// Ȧ�� Ÿ���� ����̸� count
//					System.out.println("white- Ȧ�� black ī��Ʈ");
					changeCount++;
				}
			}
		}
		
		return changeCount;
	}
	
	private static void printMin(int num1, int num2) {
		if(num1 <num2) {
			System.out.println(num1);
		} else {
			System.out.println(num2);
		}
	}
	
	//0 => ���Ÿ��, 1 => ����Ÿ��
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		
		String[] tileLine = scan.nextLine().split(",");
		String firstTile = tileLine[0];

		int whiteCount = firstWhite(tileLine, firstTile);
		int blackCount = firstBlack(tileLine, firstTile);

		printMin(whiteCount, blackCount);
		scan.close();
	}
}
