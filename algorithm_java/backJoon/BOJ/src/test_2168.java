import java.math.BigInteger;
import java.util.Scanner;

public class test_2168 {
	public static long gcd(long a,long b){
		BigInteger b1 = BigInteger.valueOf(a);
		BigInteger b2 = BigInteger.valueOf(b);
	    BigInteger gcd = b1.gcd(b2);

	    return gcd.intValue();
	}
	
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in); 
		long n = scan.nextLong(); 
		long m = scan.nextLong(); 
		System.out.println(n+m-gcd(n,m));
	}
	
	
	// ���� ����Ʈ : https://m.blog.naver.com/PostView.nhn?blogId=programmer18&logNo=221107256038&proxyReferer=https%3A%2F%2Fwww.google.co.kr%2F
	/*
	 * nxm �׸��� �׷����� gcd(n,m) >1  ~>  Ÿ�ϵ��� ������������ ������. 
	 * gcd = 1  ~>  �������� �ƿ� ������ �ʴ´�. 
	 * ���� ��� �ΰ���
	 *  
	 * 1) gcd(n,m) = 1
	 * �밢���� ���ο� �������� �ѹ��� ���������� �밢���� �׷��� Ÿ���� ������ ���� 
	 * (n-1)�� ������, (m-1)�� ������, ó�� ������ Ÿ��(1)�� ���� ~> (n-1)+(m-1)+1 = n+m-1 ���� Ÿ���� ��ĥ.
	 * 
	 * 2)g =gcd(n,m) > 1 
	 * �� g ���� (n/g) x (m/g) �� gcd(n/g,m/g) = 1 �� ���簢���� ���� �� �����Ƿ� g(n/g + m/g -1) = n+m-g. 
	 */
}
