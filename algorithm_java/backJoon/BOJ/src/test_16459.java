import java.util.ArrayList;
import java.util.Scanner;

public class test_16459 {
	
	private static boolean judgeEnd(String param) {
		if(param.equals("0")) {
			return false;
		} else {
			return true;
		}
	};
	
	
	private static void printStory(ArrayList<String> story, ArrayList<String> author) {
		int size = story.size()-1;
		
		// change data WHO, WHERE in authorData
		author.set(1, author.get(1).replace("WHO", author.get(0)));
		author.set(2, author.get(2).replace("WHO", author.get(0)));
		author.set(2, author.get(2).replace("WHERE", author.get(1)));
		
		// change data which contain WHO, WHERE, WHAT change to authorData 
		for(int i=0; i<size; i++) {
			story.set(i, story.get(i).replace("WHO", author.get(0)));
			story.set(i, story.get(i).replace("WHERE", author.get(1)));
			story.set(i, story.get(i).replace("WHAT", author.get(2)));
		}
		
		for(int i=0; i<size; i++) {
			System.out.println(story.get(i));
		}
	};

	public static void main(String[] args) {
		//input datas
		ArrayList<String> story = new ArrayList<String>();
		ArrayList<String> authorData = new ArrayList<String>();
		
		//scanner
		Scanner scan = new Scanner(System.in);
		
		String inputLine = "";	// scan a line
		Boolean isEnd = true;	// for a while loop
		
		while(isEnd) {
			inputLine = scan.nextLine();
			isEnd = judgeEnd(inputLine);
			
			story.add(inputLine);
		}
		
		for(int i=0; i<3; i++) {
			authorData.add(scan.nextLine());
		}
		
		printStory(story, authorData);
		
		scan.close();
	}
}
