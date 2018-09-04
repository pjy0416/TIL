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
		System.out.println("���� �Է��ϼ���. ���߽÷��� q�� �Է����ּ���.");
		while(true) {
			inStr = scan.nextLine();
			if(inStr.equals("q")) {
				if(!data.isEmpty() ) {
					System.out.println(data + "\n�ý��� ����.");
				} else {
					System.out.println("�ý��� ����.");
				}
				break;
			} else {
				data.add(Integer.parseInt(inStr));
			}
		}
		Collections.sort(data); //���� �޼ҵ带 �̿��� sorting
		HashSet<Integer> distinctData = new HashSet<Integer>(data);
		data = new ArrayList<Integer>(distinctData);
		System.out.println("������ ����,�ߺ��� ���� ����--------------\n"+data);
	}
}
