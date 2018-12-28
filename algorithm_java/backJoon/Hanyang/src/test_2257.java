import java.util.Scanner;
import java.util.Stack;

/*public class test_2257 {			// ~> 런타임 에러
	final static int hydro = 1;
	final static int carb = 12;
	final static int oxy = 16;
	
	// Type 판별 methods
	private static boolean isBlank(String str) {			// 여러 문자열들 중 공백 문자열 판단
		if(str.matches("^*[\\W]*$")) {
			return true;
		}
		return false;
	}
	
	private static boolean onlyNum(String str) {			// 문자열이 숫자인 경우 판별
		if(str.matches("[0-9]")) {
			return true;
		}
		return false;
	}
	
	private static boolean isNumber(String str) {			// 문자열이 문자 + 숫자인 경우 판별
		if(str.matches("[A-Z]*[0-9][A-Z]*")) {
			return true;
		}
		return false;
	}
	
	private static boolean onlyNum(char ch) {				// 문자 + 숫자인 문자열 중 숫자인 인덱스 판별
		if(ch <48 || ch >58) {
			return false;
		}
		
		return true;
	}
	
	// 문자열 Type별 계산 method
	private static int calElement(String str) {				// 문자만으로 구성된 경우
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
	
	private static int calElement(char ch, int scale) {		// 문자 + 숫자로 구성된 경우
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
	
	
	// 한 문자열의 결과를 return하는 method
	private static int getResult(String str) {
		int result =0;
		
		boolean isNum = isNumber(str);
		
		if(isNum) {				// 숫자가 포함 되어 있는 경우
			int len = str.length();
			for(int i=0; i<len; i++) {
				char ch = str.charAt(i);
				if(onlyNum(ch)) {	// 인덱스가 숫자인 경우
					int scale = Character.getNumericValue(ch);
					result += calElement(str.charAt(i-1), scale-1);
				} else {			// 아닌 경우
					result += calElement(ch,1);
				}
			}
		} else {		// 문자만 있을 경우
			result += calElement(str);
		}
		
		return result;
	}
	
	// 전체 문자열 계산
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
	// 풀이 : http://js1jj2sk3.tistory.com/79
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
