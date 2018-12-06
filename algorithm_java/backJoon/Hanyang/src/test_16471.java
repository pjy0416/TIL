import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;

public class test_16471 {
	
	private static void print(boolean isWin) {
		if(isWin) {
			System.out.println("YES");
		}else {
			System.out.println("NO");
		}
	}
	
	private static boolean checkWin(int guestPoint, int winPoint) {
		if(guestPoint >= winPoint/2) {
			return true;
		}else {
			return false;
		}
	}
	
	private static void addInfo(ArrayList<Integer> user, int idx, Scanner scan) {
		for(int i=0; i<idx; i++) {	// add a master's cards as total
			user.add(scan.nextInt());
		}
	}
	
	private static int countWin(int idx, ArrayList<Integer> master, ArrayList<Integer> guest) {
		//temp º¯¼ö
		int guestPoint =0;
		int cardMaster =0;
		int cardGuest =0;
		//guest card °¹¼ö
		for(int i=0; i<idx; i++) {
			cardGuest = guest.get(0);
			cardMaster = master.get(0);
			
			if(cardGuest >= cardMaster) {
				guest.remove(guest.get(0));
			} else {
				guestPoint++;
				guest.remove(guest.get(0));
				master.remove(master.get(0));
			}
		}
		return guestPoint;
	}
	
	public static void main(String[] args) {
		// player information
		ArrayList<Integer> master = new ArrayList<Integer>();		// ÁÖÀÎ
		ArrayList<Integer> guest = new ArrayList<Integer>();	// ¼Õ´Ô
		
		// input variance
		Scanner scan = new Scanner(System.in);	
		
		// total cards number, win point
		int numCards = scan.nextInt();	// input a number of cards
		int winPoint = numCards+1;		// win point
		
		// add data into users
		addInfo(guest, numCards, scan);
		addInfo(master, numCards, scan);
		
		// Ascending sort for a comparing guest's cards with master's cards 
		Collections.sort(master, new AscendingInteger());
		Collections.sort(guest, new AscendingInteger());
		
		int guestPoint = countWin(numCards, master, guest);
		
		print(checkWin(guestPoint, winPoint));
		
		scan.close();
	}
}

class AscendingInteger implements Comparator<Integer> {
	@Override 
	public int compare(Integer a, Integer b) {
		return b.compareTo(a); 
	} 
}
