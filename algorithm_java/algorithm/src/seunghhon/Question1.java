package seunghhon;

import java.util.Scanner;

public class Question1 {
	static String black ="1";
   static String white = "0";   
   
   private static int firstBlack(String[] tiles, String firstTile) {	//첫번째 타일이 검은색으로 가정한 함수
      int len = tiles.length;
      int changeCount=0;
      
      if(firstTile.equals(white)) {	// 첫번째 타일이 흰색이면 뒤집어야함
         changeCount++;
      }
      
      for(int i=1; i<len; i++) {
         if(i%2 !=0) {   // 두번째 부터 짝수타일 
            if(tiles[i].equals(black)) {   // 짝수 타일이 흑색이면 count
               changeCount++;
            }
         }else {         // 세번째 부터 홀수 타일
            if(tiles[i].equals(white)) {   // 홀수 타일이 흰색이면 count
               changeCount++;
            }
         }
      }
      
      return changeCount;
   }
   
   private static int firstWhite(String[] tiles, String firstTile) {	//첫번째 타일이 흰색으로 가정한 함수
      int len = tiles.length;
      int changeCount=0;
      
      if(firstTile.equals(black)) {	// 첫째타일이 검은색이면 뒤집어야함
         changeCount++;
      }
      
      for(int i=1; i<len; i++) {
         if(i%2 !=0) {   // 두번째 부터 짝수타일 
            if(tiles[i].equals(white)) {   // 짝수 타일이 흰색이면 count
               changeCount++;
            }
         }else {         // 세번째 부터 홀수 타일
            if(tiles[i].equals(black)) {   // 홀수 타일이 흑색이면 count
               changeCount++;
            }
         }
      }
      
      return changeCount;
   }
   
   private static void printMin(int num1, int num2) {
	  System.out.println(Math.min(num1, num2));
   }
   
   //0 => 흰색타일, 1 => 검은타일
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
