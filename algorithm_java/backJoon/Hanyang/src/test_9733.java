import java.util.Scanner;

public class test_9733 {								// 틀린코드ㅡ..... 이유를 모르겠음
	private final static int size = 7;
	private final static String[] workName = {"Re","Pt","Cc","Ea","Tb","Cm","Ex", "Total"};
	
	private static int[] addCnts(String[] works) {
		int[] workCnt = new int[size+1];
		int len = works.length; 
		
		for(int i=0; i<len; i++) {
			judgeWork(works[i], workCnt);
			workCnt[size]++;
		}
		
		return workCnt;
	}
	
	private static void judgeWork(String work, int[] workCnt) {
		if (work.equals(workName[0])) {
			workCnt[0]++;
		} else if (work.equals(workName[1])) {
			workCnt[1]++;
		} else if (work.equals(workName[2])) {
			workCnt[2]++;
		} else if (work.equals(workName[3])) {
			workCnt[3]++;
		} else if (work.equals(workName[4])) {
			workCnt[4]++;
		} else if (work.equals(workName[5])) {
			workCnt[5]++;
		} else if (work.equals(workName[6])) {
			workCnt[6]++;
		} 
	}
	 
	private static double[] calWorkRatio(int[] workCnt) {
		double[] workRatio = new double[size+1];
		
		double allCnt = workCnt[size];
		for(int i=0; i<size; i++) {
			workRatio[i] = workCnt[i]/allCnt;
		}
		workRatio[size] = 1.00;
		
		if(allCnt ==0) {
			for(int i=0; i<size; i++) {
				workRatio[i] =0.00;
			}
		}
		return workRatio;
	}
	
	private static void printWork(int[] workCnt, double[] workRatio) {
		for(int i=0; i<size+1; i++) {
			System.out.println(workName[i] +" " + workCnt[i]+ " " + String.format("%.2f", workRatio[i]));
		}
	}
	
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		String inputStr = scan.nextLine();
		
		String[] works= inputStr.split(" ");
		if(works.length == 16) {
			String tmp = scan.nextLine();
			if(!tmp.equals("")) {
				inputStr = inputStr.concat(" ").concat(tmp);
				works = inputStr.split(" ");
			} 
		}
		
		int[] workCnt = addCnts(works);
		double[] workRatio = calWorkRatio(workCnt);
		printWork(workCnt, workRatio);
		
		scan.close();
	}

}
