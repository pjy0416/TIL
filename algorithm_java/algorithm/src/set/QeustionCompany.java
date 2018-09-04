package set;

import java.util.LinkedHashSet;
import java.util.Scanner;

public class QeustionCompany {
	private static LinkedHashSet<String> attendList = new LinkedHashSet<String>();
	
	private static void isInCompany(String[] cmd) {
		if(cmd[0].equals("print")) {
			for(String data : attendList) {
				System.out.println(data);
			}
		}else if(cmd[1].equals("enter")) {
			attendList.add(cmd[0]);
		}else if(cmd[1].equals("leave")) {
			attendList.remove(cmd[0]);
		}
	}
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		String inStr;
		String[] cmd;
		while(true) {
			inStr = scan.nextLine();
			if(inStr.equals("q")) {
				break;
			}
			cmd = inStr.split(" ");
			try {
				isInCompany(cmd);
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
	}

}
