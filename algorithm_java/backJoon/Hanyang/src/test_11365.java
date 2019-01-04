import java.util.Scanner;

public class test_11365 {			// 14136 KB  104 ms
	static final int size = 100;
	
	private static String reverseString(String s) {
		return ( new StringBuffer(s) ).reverse().toString();
	}
	
	private static void printCodes(String[] codes) {
		for(int i=0; i<size; i++) {
			String code = codes[i];
			if(code == null) {
				break;
			}
			System.out.println(code);
		}
	}
	
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		String[] codes = new String[size];
		
		int idx =0;
		while(true) {
			String inputStr = scan.nextLine();
			if(inputStr.equals("END")) {
				break;
			}
			codes[idx++] = reverseString(inputStr);
		}
		printCodes(codes);
		scan.close();
	}
	
}


/*public class test_11365 {				//	14204 KB 100ms
	private static String reverseString(String s) {
		return ( new StringBuffer(s) ).reverse().toString();
	}
	
	private static void printCodes(ArrayList<String> codes) {
		int size = codes.size();
		
		for(int i=0; i<size; i++) {
			String code = codes.get(i);
			if(code == null) {
				break;
			}
			System.out.println(code);
		}
	}
	
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		ArrayList<String> codes = new ArrayList<>();
		
		while(true) {
			String inputStr = scan.nextLine();
			if(inputStr.equals("END")) {
				printCodes(codes);
				break;
			}
			codes.add(reverseString(inputStr));
		}
		scan.close();
	}
}
*/