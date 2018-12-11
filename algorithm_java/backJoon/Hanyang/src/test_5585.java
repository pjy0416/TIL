import java.util.Scanner;

public class test_5585 {
	
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		int[] change = {500, 100, 50, 10, 5, 1};
		int charge = 1000 -scan.nextInt();
		int size = change.length;
		int count =0;
		
		for(int i=0; i<size; i++) {
			count += charge /change[i];
			charge %= change[i];
		}
		
		System.out.println(count);
		
		///////////////////// short coding
		/*Scanner scan = new Scanner(System.in);	
		int change = 1000 -scan.nextInt();
		
		System.out.println(change/500 + (change%=500)/100 + (change%=100)/50 + (change%=50)/10 + (change%=10)/5 + change%5);*/
		scan.close();
	}

}
