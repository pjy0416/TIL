import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class test_16472 {		
	
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		
		byte alphaSize = scan.nextByte();	// 알파벳 종류
		scan.nextLine();	// 개행문자 제거를 위한 method call
		String str = scan.nextLine().replaceAll("[^a-zA-Z]", "");
		char[] letters = str.toCharArray();		//for문에서 str.charAt(idx) 보다 빠를것이라고 판단하여 변수 선언
		
		Set<Character> alphaKind = new HashSet<>();
		
		int line = str.length();
		int max =0;
		
		int count=0;
		for(int i=0; i<line; i++) {
			alphaKind.add(letters[i]);
			if(alphaKind.size() > alphaSize) {
				max = Math.max(max, count);
				i = i -count;
				count =0;
				alphaKind.clear();
				continue;
			}
			count++;
		}
		
		if(max == 0) {
			max = line;
		} 
		System.out.println(max);
		scan.close();
	}
}