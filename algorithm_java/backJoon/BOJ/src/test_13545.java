import java.util.Scanner;

public class test_13545 {			// time over code
									
	static int getMaxLine(int[] seqs, int seqSize, int start, int end) {
		int maxLine =0;
									
		for(int i=start; i<=end; i++) {		
			int sum =0;
			for(int j=i; j<=end; j++) {		
				sum += seqs[j];
				if(sum ==0) {
					maxLine = Math.max(maxLine, (j-i+1));
				}
			}
		}
		
		return maxLine;
	}
	
	private static void runQuery(int querySize, int[] seqs, int seqSize, Scanner scan) {
		int[] result = new int[querySize];
		for(int i=0; i<querySize; i++) {
			int startLine = scan.nextInt()-1;
			int endLine = scan.nextInt()-1;
			
			result[i] = getMaxLine(seqs, seqSize, startLine, endLine);
		}
		
		printResult(result, querySize);
	}
	
	private static int[] saveSeqs(int seqSize, Scanner scan) {
		int[] result = new int[seqSize];
		
		for(int i=0; i<seqSize; i++) {
			result[i] = scan.nextInt();	// 값 저장
		}
		
		return result;
	}
	
	static void printResult(int[] queryResults, int querySize) {
		for(int i=0; i<querySize; i++) {
			System.out.println(queryResults[i]);
		}
	}

	public static void main(String[] args) {
		int[] seqs;	// 수열을 담을 array 선언
		
		Scanner scan = new Scanner(System.in);
		
		int seqSize = scan.nextInt();		// input a array's size
		seqs = saveSeqs(seqSize, scan);
		
		int querySize = scan.nextInt();
		runQuery(querySize, seqs, seqSize, scan);
		
		scan.close();
	}
}
