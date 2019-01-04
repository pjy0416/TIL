import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class test_16460 {
	private static void printUser(User mainUser, ArrayList<User> partners, ArrayList<Integer> sameIdx) {
		boolean isPrint = false;
		String mainGender = mainUser.getGender();
		String mainDist = mainUser.getDist();
		ArrayList<String> recomList = new ArrayList<>();
		
		if(mainGender.equals("FM") || mainGender.equals("MF")) {
			int size = partners.size();
			for(int i=0; i< size; i++) {
				User tmp = partners.get(i);
				if(Integer.parseInt(tmp.getDist()) <= Integer.parseInt(mainDist)) {
					recomList.add(tmp.getName());
					//System.out.println(tmp.getName());
					isPrint = true;
				}
			}
		} else {
			int size = sameIdx.size();
			if(size !=0) {
				for(int i=0; i< size; i++) {
					int index = sameIdx.get(i);
					User tmp = partners.get(index);
					if(Integer.parseInt(tmp.getDist()) <= Integer.parseInt(mainDist)) {
						recomList.add(tmp.getName());
						//System.out.println(tmp.getName());
						isPrint = true;
					}
				}
			}
		}
		
		if(!isPrint) {
			System.out.println("No one yet");
		}else {
			Collections.sort(recomList);
			for(int i=0; i<recomList.size(); i++) {
				System.out.println(recomList.get(i));
			}
		}
	}
	
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		String mainInput = scan.nextLine();
		String[] mainData = mainInput.split(" ");
		
		User mainUser = new User(mainData[0],mainData[1],mainData[2]);
		ArrayList<User> partners= new ArrayList();
		ArrayList<Integer> sameGender = new ArrayList(); 
		String mainGender = mainData[1];
		
		int length = Integer.parseInt(scan.nextLine());
		
		for(int i=0; i<length; i++) {
			String inputStr = scan.nextLine();
			String[] datas = inputStr.split(" ");
			
			partners.add(new User(datas[0],datas[1],datas[2]));
			if(mainGender.equals(datas[1])) {
				sameGender.add(i);
			}
		}
		printUser(mainUser, partners, sameGender);
		
	}
}

class User {
	String name;
	String gender;
	String dist;
	
	public User() {      // primary constructor
	   }
	   
	   public User(String name, String gender, String dist) {
	      this.name = name;
	      this.gender = gender;
	      this.dist = dist;
	   }

	   //Getters
	   String getName() {
	      return name;
	   }
	   String getGender() {
	      return gender;
	   }
	   String getDist() {
	      return dist;
	   }
	   
	   //Setters
	   void setName(String name) {
	      this.name = name;
	   }
	   void setGender(String gender) {
	      this.gender = gender;
	   }
	   void setDist(String dist) {
	      this.dist = dist;
	   }
}