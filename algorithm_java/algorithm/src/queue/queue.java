package queue;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class queue {
	private static Queue<String> queue = new LinkedList<String>();
	
	private static void commandQ(String[] cmd) {
		if(cmd[0].equals("push")) {
			queue.add(cmd[1]);
		}else if(cmd[0].equals("front")) {
			if(queue.size() !=0) {
				System.out.println(queue.peek());
			}else {
				System.out.println("None");
			}
		}else if(cmd[0].equals("back")) {
			if(queue.size() !=0) {
				System.out.println("Queue로 구현 불가, LinkedList로 바꿔서 구현하면 됨");
				//System.out.println(queue.get(queue.size()-1));
			}else {
				System.out.println("None");
			}
		}else if(cmd[0].equals("empty")) {
			if(queue.isEmpty()) {
				System.out.println("1");
			}else {
				System.out.println("0");
			}
		}else if(cmd[0].equals("pop")) {
			System.out.println(queue.poll());
		}else if(cmd[0].equals("size")) {
			System.out.println(queue.size());
		}
	}
	
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		String inStr ="";
		String[] cmd;
		while(!inStr.equals("q")) {
			inStr = scan.nextLine();
			cmd =inStr.split(" ");
			
			commandQ(cmd);
		}
	}

}
