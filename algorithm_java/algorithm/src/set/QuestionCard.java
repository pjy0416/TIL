package set;

import java.util.HashSet;
import java.util.Scanner;

public class QuestionCard {
	
	public static void isHold(HashSet<Integer> deck, String[] list) {
		boolean holdChecker = false;
		
		for(String one : list) {
			holdChecker = false;
			for(int card : deck) {
				if(card == Integer.parseInt(one)) {
					holdChecker = true;
					break;
				}
			}
			if(holdChecker) {
				System.out.print(1+" ");
			}else {
				System.out.print(0+" ");
			}
		}
		System.out.println();
	}
	
	public static void main(String[] args) {
		HashSet<Integer> holdCard = new HashSet<Integer>();
		holdCard.add(10);
		holdCard.add(-10);
		holdCard.add(5);
		holdCard.add(3);
		holdCard.add(1);
		holdCard.add(9);
		
		Scanner scan = new Scanner(System.in);
		String inStr;
		String[] cards;
		while(true) {
			inStr = scan.nextLine();
			if(inStr.equals("q")) {
				break;
			}
			cards = inStr.split(" ");
			isHold(holdCard,cards);
		}
		
	}

}
