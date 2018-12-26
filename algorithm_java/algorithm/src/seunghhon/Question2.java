package seunghhon;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class Question2 {		
	final static int diceSize = 3;		// 주사위 세개, 변하지 않는 값이라 final을 사용, 해당 클래스에서 재사용 가능성을 고려해서 static으로 선언.
	
	private static int obCount(Set obsSet, String[] obs, int[] dice) {
		int count =0;
		int diceMax = Integer.parseInt(obs[obs.length-1]);
		
		int sumMin =1+diceSize;			// 기본 인덱스 1 + 주사위 최소합 3 ~> 4번째 인덱스가 가장 적게 이동하는 경우
		int sumMax = 1; // 기본 칸 1부터 시작, 1 + 각각 주사위의 최대값을 더해주면 최대 인덱스(배열의 최대길이)
		for(int i=0; i<diceSize; i++) {
			sumMax += dice[i]; 		// 주사위의 최대값들을 더해줌.
		}
		
		for(int i=1; i<=dice[0]; i++) {
			for(int j=1; j<=dice[1]; j++) {
				int overIdx = i+j+1;
				if(overIdx >= diceMax) {	//이미 장애물 Index 보다 넘어선 경우는 종료   
					break;
				}
				for(int k=1; k<=dice[2]; k++) {
					int moveIndex = i+j+k+1;			// 주사위를 던져서 이동한 Index
					String moveStr = String.valueOf(moveIndex);		// Set의 요소는 String이기 때문에 String으로 변환
					if( (moveIndex >= sumMin && moveIndex <= sumMax) && obsSet.contains(moveStr)) {		// 최소Idx~최대 Idx 사이에 존재, 장애물 Idx와 일치할 경우
						count++;		// 위험 카운트
					}
				}
			}
		}
		
		return count;
	}
	
	private static int safeRatio(int obCount, int[] dice) {
		int total = 1;
		
		for(int i=0; i<diceSize; i++) {
			total *=dice[i];
		}
		
		obCount = total - obCount;
		
		float result = (float)obCount/total*1000;
		
		return (int)result;
	}
	
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		String inputStr = scan.next();
		int[] dice = new int[(diceSize)];	// 주사위의 정보를 담을 array 생성
		
		String[] obs = inputStr.replaceAll("[^0-9,]", "").split(",");	// 장애물 위치를 받은 정보를 배열로 변환 (정규식을 통해서 숫자와 ,를 제외한 문자는 제거한 뒤 ,로 split)
		Set<String> obsSet = new HashSet<String>();
		Arrays.sort(obs);
		Collections.addAll(obsSet, obs);
		
		
		for(int i=0; i<diceSize; i++) {	// 각 주사위의 최대값을 입력받아준다.
			dice[i] = scan.nextInt();
		}
		
		int obcount = obCount(obsSet, obs, dice);
		System.out.println(safeRatio(obcount, dice));		
		
		scan.close();
	}
}
