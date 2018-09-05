package map;

import java.util.HashMap;
import java.util.Scanner;

public class OhmicResistance {
	private static int calOhm(String[] colors, HashMap<String, Integer> res) {
		int result =0;
		try {
			result = (int)((res.get(colors[0])*10 + res.get(colors[1])) * Math.pow(10, res.get(colors[2])));
		}catch(Exception e) {
		}
		return result;
	}
	
	private static void printOhm(int ohm) {
		if(ohm != 0) {
			System.out.println(ohm);
		} else {
			System.out.println("존재하지 않는 색상입니다.");
		}
	}

	public static void main(String[] args) {
		HashMap<String, Integer> resistance = new HashMap<>();
		//put key, value in HashMap
		resistance.put("black",0);
		resistance.put("brown",1);
		resistance.put("red",2);
		resistance.put("orange",3);
		resistance.put("yellow",4);
		resistance.put("green",5);
		resistance.put("blue",6);
		resistance.put("violet",7);
		resistance.put("grey",8);
		resistance.put("white",9);
		
		Scanner scan = new Scanner(System.in);
		String color[] = {"","",""} ;
		
		for(int i=0; i<3; i++) {
			color[i] = scan.nextLine();
		}
		printOhm(calOhm(color,resistance));
	}
}
