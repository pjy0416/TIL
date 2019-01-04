import java.util.Scanner;

public class test_2010 {

	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		int multiNum = scan.nextInt();
		int sumCodes =1;
		int multiCodes =0;
		final int computer =1;
		
		for(int i=0; i<multiNum; i++) {
			sumCodes+= scan.nextInt();
		}
		
		System.out.println(sumCodes - multiNum); // ÀüÃ¼ ÄÚµå ±¸¸Û¿¡¼­ ¸ÖÆ¼ÅÇ ÄÚµåÀÇ °¹¼ö¸¸Å­ ±¸¸ÛÀ» »©ÁÜ
		
	}

}
