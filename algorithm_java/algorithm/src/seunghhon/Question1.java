package seunghhon;

import java.util.Scanner;

public class Question1 {
	static String black ="1";
   static String white = "0";   
   
   private static int firstBlack(String[] tiles, String firstTile) {	//ù��° Ÿ���� ���������� ������ �Լ�
      int len = tiles.length;
      int changeCount=0;
      
      if(firstTile.equals(white)) {	// ù��° Ÿ���� ����̸� ���������
         changeCount++;
      }
      
      for(int i=1; i<len; i++) {
         if(i%2 !=0) {   // �ι�° ���� ¦��Ÿ�� 
            if(tiles[i].equals(black)) {   // ¦�� Ÿ���� ����̸� count
               changeCount++;
            }
         }else {         // ����° ���� Ȧ�� Ÿ��
            if(tiles[i].equals(white)) {   // Ȧ�� Ÿ���� ����̸� count
               changeCount++;
            }
         }
      }
      
      return changeCount;
   }
   
   private static int firstWhite(String[] tiles, String firstTile) {	//ù��° Ÿ���� ������� ������ �Լ�
      int len = tiles.length;
      int changeCount=0;
      
      if(firstTile.equals(black)) {	// ù°Ÿ���� �������̸� ���������
         changeCount++;
      }
      
      for(int i=1; i<len; i++) {
         if(i%2 !=0) {   // �ι�° ���� ¦��Ÿ�� 
            if(tiles[i].equals(white)) {   // ¦�� Ÿ���� ����̸� count
               changeCount++;
            }
         }else {         // ����° ���� Ȧ�� Ÿ��
            if(tiles[i].equals(black)) {   // Ȧ�� Ÿ���� ����̸� count
               changeCount++;
            }
         }
      }
      
      return changeCount;
   }
   
   private static void printMin(int num1, int num2) {
	  System.out.println(Math.min(num1, num2));
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
