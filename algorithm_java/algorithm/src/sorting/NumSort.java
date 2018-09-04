package sorting;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;

public class NumSort {
	
	public static int partition(ArrayList<Integer> list, int left, int right) {
		if(list.size() <= 1) {
			return 0;
		}
		int temp=0;
		int pivot = list.get((left+right)/2);
		while (left <right) {
			while(list.get(left)<pivot && (left<right)) {
				left++;
			}
			while(list.get(right)>pivot && (left<right)) {
				right--;
			}
			
			if(left <right) {
				temp = list.get(left);
				list.set(left, list.get(right));
				list.set(right, temp);
			}
			
			///////////중복값일 때 카운팅
			if(list.get(left) == pivot) {
				left += 1;
			}
			if(list.get(right) == pivot) {
				right -=1;
			}
		}
		return left;
	}
	
	public static void quickSort(ArrayList<Integer> input, int left, int right) {
		if(left <right) {
			int pivotIndex = partition(input, left, right);
			quickSort(input, left, pivotIndex-1);
			quickSort(input, pivotIndex+1, right);
		}
	}
	
	//HashSet을 이용하여 중복값 제거
	public static ArrayList<Integer> removeDuplication(ArrayList<Integer> input) {
		HashSet<Integer> distinctData = new HashSet<Integer>(input);
		input = new ArrayList<Integer>(distinctData);
		
		return input;
	}
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
		quickSort(data,0,data.size()-1);
		data = removeDuplication(data);
		
		if(!data.isEmpty()) {
			System.out.println("오름차 정렬,중복값 제거 이후--------------\n"+data);
		}
	}
}
