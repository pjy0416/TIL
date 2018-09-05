package heap;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Scanner;

public class MaxHeap {

	/// 내림차순 정렬을 위해 Comparator를 사용
	private static class Compare implements Comparator<Integer> {
		@Override
		public int compare(Integer o1, Integer o2) {
			return o2.compareTo(o1);
		}
	}
	
	private static Compare cmp = new Compare();
	private static PriorityQueue<Integer> pq = new PriorityQueue<>(1,cmp); // 객체 생성시 compare가 적용되도록 초기화 
	
	private static void cmdHeap(int input) {
		if(input ==0) {
			if(!pq.isEmpty()) {
				System.out.println(pq.poll());
			}else {
				System.out.println(0);
			}
		}else {
			pq.add(input);
		}
	}

	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		int input =0;
		while(true) {
			input = scan.nextInt();
			cmdHeap(input);
		}
	}

}
