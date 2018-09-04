package stack;

import java.util.Scanner;
import java.util.Stack;

public class stack {
	
	public static Stack stackFunc(Stack<String> stack, String inStr) {
		String[] cmdStr = inStr.split(" ");
		
		if(cmdStr[0].equals("push")) {
			stack.push(cmdStr[1]);
		}else if(cmdStr[0].equals("top")) {
			System.out.println(stack.get(stack.size()-1));
		}else if(cmdStr[0].equals("size")) {
			System.out.println(stack.size());
		}else if(cmdStr[0].equals("empty")) {
			if(stack.empty() == true) {
				System.out.println(1);
			}else {
				System.out.println(0);
			}
		}else if(cmdStr[0].equals("pop")) {
			if(!stack.empty()) {
				System.out.println(stack.pop());
			}else {
				System.out.println(-1);
			}
		}else {
			System.out.println("잘못된 명령어 입니다. 다시 입력해주세요.");
		}
		
		return stack;
	}
	
	public static void main (String args[]) {
		Stack<String> stack = new Stack<String>();
		Scanner scan = new Scanner(System.in);
		String inStr;
		
		while(true) {
			inStr = scan.nextLine();
			if(inStr.equals("q")) {
				System.out.println("시스템을 종료합니다.");
				break;
			}
			else {
				stack = stackFunc(stack,inStr);
			}
		}
	}
}
