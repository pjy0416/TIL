package set;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.TreeSet;

public class Set {
	
	public static void hashFunc() {
		HashSet<Integer> hs = new HashSet<Integer>();
		
		/////// �Ʒ� �� �� print ����� ����.
		/*for (int i=10; i>0; i--) {
			hs.add(i);
		}*/
		/*for (int i=1; i<=10; i++) {
			hs.add(i);
		}*/
		for (int element : hs) {
			System.out.print(element + " ");
		}
	}
	
	public static void treeFunc() {
		TreeSet<Integer> ts = new TreeSet<>();
		
		///////// �Ʒ� �� �� print ��� ����.
		for(int i=10; i>=1; i--) {
			ts.add(i);
		}
		/*for(int i=1; i<=10; i++) {
			ts.add(i);
		}*/
		for(int element : ts) {
			System.out.print(element+" ");
		}
	}

	public static void linkedFunc() {
		LinkedHashSet<Integer> lhs = new LinkedHashSet<>();
		//////// �Ʒ� print�� �ٸ�(�Է� ������ ���� ���)
		/*for(int i=1; i<=10; i++) {
			lhs.add(i);
		}*/
		for(int i=10; i>=1; i--) {
			lhs.add(i);
		}
		for(int element : lhs) {
			System.out.print(element + " ");
		}
	}
	
	public static void main(String[] args) {
		//hashFunc();
		//treeFunc();
		linkedFunc();
	}
}
