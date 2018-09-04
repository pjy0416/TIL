package sorting;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Scanner;

public class NumSort2 {

	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		ArrayList<Integer> data = new ArrayList<Integer>();
		
		String inStr;
		System.out.println("수를 입력하세요. 멈추시려면 q를 입력해주세요.");
		while(true) {
			inStr = scan.nextLine();
			if(inStr.equals("q")) {
				if(!data.isEmpty() ) {
					System.out.println(data + "\n시스템 종료.");
				} else {
					System.out.println("시스템 종료.");
				}
				break;
			} else {
				data.add(Integer.parseInt(inStr));
			}
		}
		Collections.sort(data); //내장 메소드를 이용한 sorting
		HashSet<Integer> distinctData = new HashSet<Integer>(data);
		data = new ArrayList<Integer>(distinctData);
		System.out.println("오름차 정렬,중복값 제거 이후--------------\n"+data);
	}
}
