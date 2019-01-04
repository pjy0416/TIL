import java.util.Scanner;
import java.util.Stack;

/*public class test_2257 {			// ~> ��Ÿ�� ����
	final static int hydro = 1;
	final static int carb = 12;
	final static int oxy = 16;
	
	// Type �Ǻ� methods
	private static boolean isBlank(String str) {			// ���� ���ڿ��� �� ���� ���ڿ� �Ǵ�
		if(str.matches("^*[\\W]*$")) {
			return true;
		}
		return false;
	}
	
	private static boolean onlyNum(String str) {			// ���ڿ��� ������ ��� �Ǻ�
		if(str.matches("[0-9]")) {
			return true;
		}
		return false;
	}
	
	private static boolean isNumber(String str) {			// ���ڿ��� ���� + ������ ��� �Ǻ�
		if(str.matches("[A-Z]*[0-9][A-Z]*")) {
			return true;
		}
		return false;
	}
	
	private static boolean onlyNum(char ch) {				// ���� + ������ ���ڿ� �� ������ �ε��� �Ǻ�
		if(ch <48 || ch >58) {
			return false;
		}
		
		return true;
	}
	
	// ���ڿ� Type�� ��� method
	private static int calElement(String str) {				// ���ڸ����� ������ ���
		int weight =0;
		
		if(str.contains("C")) {
			weight += carb;
		}
		if(str.contains("H")) {
			weight += hydro;
		}
		if(str.contains("O")) {
			weight += oxy;
		} 
		
		return weight;
	}
	
	private static int calElement(char ch, int scale) {		// ���� + ���ڷ� ������ ���
		int weight =0;
		
		switch(ch) {
			case 'C' :
				weight += carb*scale;
				break;
				
			case 'H' :
				weight += hydro*scale;
				break;
				
			case 'O' :
				weight += oxy*scale;
				break;
				
			default :
				break;
		}
		
		return weight;
	}
	
	
	// �� ���ڿ��� ����� return�ϴ� method
	private static int getResult(String str) {
		int result =0;
		
		boolean isNum = isNumber(str);
		
		if(isNum) {				// ���ڰ� ���� �Ǿ� �ִ� ���
			int len = str.length();
			for(int i=0; i<len; i++) {
				char ch = str.charAt(i);
				if(onlyNum(ch)) {	// �ε����� ������ ���
					int scale = Character.getNumericValue(ch);
					result += calElement(str.charAt(i-1), scale-1);
				} else {			// �ƴ� ���
					result += calElement(ch,1);
				}
			}
		} else {		// ���ڸ� ���� ���
			result += calElement(str);
		}
		
		return result;
	}
	
	// ��ü ���ڿ� ���
	private static int getAllResult(ArrayList<String> eleList) {
		int size = eleList.size();
		int result = 0;
		
		for(int i=0; i<size; i++) {
			String tmp = eleList.get(i);
			if(onlyNum(tmp)) {
				int scale = Integer.parseInt(tmp);
				result += (scale-1)*getResult(eleList.get(i-1));
			}else {
				result += getResult(tmp);	
			}
		}
		
		return result;
	}
	
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		String inputStr = scan.nextLine();
		String[] tmps = inputStr.replaceAll("[(]", " ").replaceAll("[)]", " ").split(" ");
		ArrayList<String> elementList = new ArrayList<String>();
		
		
		int len = tmps.length;
		for(int i =0; i <len; i++) {
			if(!isBlank(tmps[i])) {
				elementList.add(tmps[i]);
			}
		}
		
		System.out.println(getAllResult(elementList));
	}
}*/

/*public class test_2257 {
	// Ǯ�� : http://js1jj2sk3.tistory.com/79
	final static int hydro = 1;
	final static int carb = 12;
	final static int oxy = 16;
	
	private static int getResult(String inputStr) {
		int len = inputStr.length();
		int[] stack = new int[len/2+1];
		int cnt =0;
		int tmp =0;
		
		for (int i = 0; i < len; ++i) {
			char c = inputStr.charAt(i);
		 
			if (c == 'H') {
				tmp = hydro;
	            stack[cnt] += hydro;
	        } else if (c == 'C') {
	        	tmp = carb;
	            stack[cnt] += carb;
	        } else if (c == 'O') {
	        	tmp = oxy;
	            stack[cnt] += oxy;
	        } else if (c == '(') {
	        	stack[++cnt] = 0;
	        } else if (c == ')') {
	            tmp = stack[cnt--];
	            stack[cnt] += tmp;
	        } else if ('1' < c && c <= '9') {
	        	stack[cnt] += tmp * (c - '1');
	        }
		}
		return stack[0];
	}
	
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		String inputStr = scan.nextLine();
		
		System.out.println(getResult(inputStr));
		scan.close();
	}
}*/

public class test_2257 {
	final static int hydro = 1;
	final static int carb = 12;
	final static int oxy = 16;
	final static int brace = -1;
	
	private static Stack<Integer> getResultStack(String inputStr) {
		int len = inputStr.length();
		Stack<Integer> stack = new Stack<Integer>();
		
		for (int i = 0; i < len; ++i) {
			char ch = inputStr.charAt(i);
			if (ch == 'H') {
				stack.push(hydro);
	        } else if (ch == 'C') {
	        	stack.push(carb);
	        } else if (ch == 'O') {
	        	stack.push(oxy);
	        } else if (ch == '(') {
	        	stack.push(brace);
	        } else if (ch == ')') {
	        	int weight =0;
	        	
	        	while(true) {
	        		int tmp = stack.pop();

	        		if(tmp == brace) {
	        			break;
	        		}
	        		
	        		weight += tmp;
	        	}
	        	stack.push(weight);
	        } else if ('1' < ch && ch <= '9') {
	        	int weight = stack.pop();
	        	stack.push(weight * (ch - '0'));
	        }
		}
		return stack;
	}
	
	private static int getResult(String inputStr) {
		Stack<Integer> stack = getResultStack(inputStr);
		int weight =0;
		
		while(!stack.isEmpty()) {
			weight += stack.pop();
		}
		
		return weight;
	}
	
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		String inputStr = scan.nextLine();
		
		System.out.println(getResult(inputStr));
		scan.close();
	}
}
