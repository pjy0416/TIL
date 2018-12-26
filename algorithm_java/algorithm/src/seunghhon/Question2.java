package seunghhon;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class Question2 {		
	final static int diceSize = 3;		// �ֻ��� ����, ������ �ʴ� ���̶� final�� ���, �ش� Ŭ�������� ���� ���ɼ��� ����ؼ� static���� ����.
	
	private static int obCount(Set obsSet, String[] obs, int[] dice) {
		int count =0;
		int diceMax = Integer.parseInt(obs[obs.length-1]);
		
		int sumMin =1+diceSize;			// �⺻ �ε��� 1 + �ֻ��� �ּ��� 3 ~> 4��° �ε����� ���� ���� �̵��ϴ� ���
		int sumMax = 1; // �⺻ ĭ 1���� ����, 1 + ���� �ֻ����� �ִ밪�� �����ָ� �ִ� �ε���(�迭�� �ִ����)
		for(int i=0; i<diceSize; i++) {
			sumMax += dice[i]; 		// �ֻ����� �ִ밪���� ������.
		}
		
		for(int i=1; i<=dice[0]; i++) {
			for(int j=1; j<=dice[1]; j++) {
				int overIdx = i+j+1;
				if(overIdx >= diceMax) {	//�̹� ��ֹ� Index ���� �Ѿ ���� ����   
					break;
				}
				for(int k=1; k<=dice[2]; k++) {
					int moveIndex = i+j+k+1;			// �ֻ����� ������ �̵��� Index
					String moveStr = String.valueOf(moveIndex);		// Set�� ��Ҵ� String�̱� ������ String���� ��ȯ
					if( (moveIndex >= sumMin && moveIndex <= sumMax) && obsSet.contains(moveStr)) {		// �ּ�Idx~�ִ� Idx ���̿� ����, ��ֹ� Idx�� ��ġ�� ���
						count++;		// ���� ī��Ʈ
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
		int[] dice = new int[(diceSize)];	// �ֻ����� ������ ���� array ����
		
		String[] obs = inputStr.replaceAll("[^0-9,]", "").split(",");	// ��ֹ� ��ġ�� ���� ������ �迭�� ��ȯ (���Խ��� ���ؼ� ���ڿ� ,�� ������ ���ڴ� ������ �� ,�� split)
		Set<String> obsSet = new HashSet<String>();
		Arrays.sort(obs);
		Collections.addAll(obsSet, obs);
		
		
		for(int i=0; i<diceSize; i++) {	// �� �ֻ����� �ִ밪�� �Է¹޾��ش�.
			dice[i] = scan.nextInt();
		}
		
		int obcount = obCount(obsSet, obs, dice);
		System.out.println(safeRatio(obcount, dice));		
		
		scan.close();
	}
}
