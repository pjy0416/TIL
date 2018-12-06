import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class test_9933 {

	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		short lines = (short)scan.nextInt();
		
		String[] texts = new String[lines];
		String[] reverses = new String[lines];
		
		scan.nextLine();
		
		for(int i=0; i<lines; i++) {
			String input = scan.nextLine();
			texts[i] = input;
			reverses[i] = new StringBuilder(input).reverse().toString();
		}
		
		Set<String> values = new HashSet<String>(Arrays.asList(texts));
		
		for(int i=0; i<lines; i++) {
			String tmp = reverses[i];
			if(values.contains(tmp)) {
				int idx = tmp.length();
				System.out.print(idx +" "+ tmp.charAt(idx/2));
				break;
			}
		}
		
		scan.close();
	}
}
