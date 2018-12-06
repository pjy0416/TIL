import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class test_2322 {		// 틀림... 모르겠다.. 하...

	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		int babels = scan.nextInt();
		ArrayList<Integer> order = new ArrayList<Integer>();
		
		for(int i=0;  i<babels; i++) {
			order.add(scan.nextInt());
		}
		
		final ArrayList<Integer> copyData = (ArrayList<Integer>) order.clone();
		Collections.sort(copyData);
		int sum =0;
		int idx =0;
		while(!order.equals(copyData)) {
			int light = copyData.get(idx);	// 비교적 가벼운놈
			int lightIdx = order.indexOf(light);
			int target = copyData.get(lightIdx);
			int targetIdx = order.indexOf(target);
			if(light == target) {
				idx++;
				continue;
			}
			
			order.set(targetIdx, light);
			order.set(lightIdx, target);
			
			sum = sum + light + target;
		}
		
		System.out.println(sum);
		scan.close();
	}
}
