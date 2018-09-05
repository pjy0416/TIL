package heap;

import java.util.PriorityQueue;
import java.util.Scanner;

public class MinHeap {

	private static PriorityQueue<Integer> pq = new PriorityQueue<>(); 
	
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
