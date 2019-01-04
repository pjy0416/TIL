import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class test_1260 {

	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		
		int inputNum = scan.nextInt();
		
		Graph g = new Graph(inputNum);
		
		int edge = scan.nextInt();
		int startNode = scan.nextInt()-1;
		
		for(int i =0; i<edge; i++) {
			int start = scan.nextInt()-1;
			int dest = scan.nextInt()-1;
			g.addEdge(start, dest);
			g.addEdge(dest, start);
		}
		
		
		g.DFS(startNode);
		g.BFS(startNode);
		
		scan.close();
	}

}

class Graph {
	private int nodeNum;
	private LinkedList<Integer>[] adj;	//���� ����Ʈ (Adjacency List)
	
	//������ (constructor)
	Graph(int num) {
		nodeNum = num;
		adj = new LinkedList[num];
		for(int i=0; i<num; i++) {	// ��������Ʈ �ʱ�ȭ
			adj[i] = new LinkedList<Integer>();
		}
	}
	
	void addEdge(int start, int dest) {	// ��� ����(���� ����) 
		adj[start].add(dest);
	}
	
	//	DFS�� ���� ���Ǵ� �Լ� 
	void DFSUtil(int num, boolean visited[]) {
		//���� ��带 �湮�� ������ ǥ���ϰ� ���� ���
		visited[num] = true;
		System.out.print((num+1) + " ");
		
	    // �湮�� ��忡 ������ ��� ��带 �����´�.
		Iterator<Integer> i = adj[num].listIterator();
		List<Integer> nodeList = new ArrayList<>();
		i.forEachRemaining(nodeList::add);
		Collections.sort(nodeList);
		
		for(int idx=0; idx<nodeList.size(); idx++) {
			if(checkEnd(visited)) {
				break;
			}
	    	int n = nodeList.get(idx);
	        // �湮���� ���� ���� �ش� ��带 ���� ���� �ٽ� DFSUtil ȣ��
	    	if (!visited[n]) {
	    		DFSUtil(n, visited); // ��ȯ ȣ��
	    	}
		}
		
	}

	  /** �־��� ��带 ���� ���� DFS Ž�� */
	  void DFS(int num) {		// ���� �켱 Ž��
	      // ����� �湮 ���� �Ǵ� (�ʱ갪: false)
	      boolean visited[] = new boolean[nodeNum];

	      // v�� ���� ���� DFSUtil ��ȯ ȣ��
	      DFSUtil(num, visited);
	      System.out.println();
	  }
	  
	  void BFSUtil(int num, LinkedList<Integer> queue, boolean[] visited) {
		  while(queue.size() !=0) {
			  num = queue.poll();
			  System.out.print((num+1) + " ");
			  
			  Iterator<Integer> i = adj[num].listIterator();
			  List<Integer> nodeList = new ArrayList<>();
			  i.forEachRemaining(nodeList::add);
			  Collections.sort(nodeList);
				
			  for(int idx=0; idx<nodeList.size(); idx++) {
				  int n = nodeList.get(idx);
				  if(!visited[n]) {	// �湮���� ���� ����� ���
					  visited[n] = true;	// �湮
					  queue.add(n);			// queue�� ���� ��� add
				  }
			  }
		  }
	  }
	  
	  /** �־��� ��带 ���� ���� DFS Ž��*/
	  void BFS(int num) {		// �ʺ� �켱 Ž��
		  boolean[] visited = new boolean[nodeNum];
		  LinkedList<Integer> queue = new LinkedList<Integer>();
		  
		  visited[num] = true;
		  queue.add(num);
		  
		  // num�� �������� BFSUtil ȣ��
		  BFSUtil(num, queue, visited);
	  }
	  
	  boolean checkEnd(boolean[] visited) {
		  int cnt =0;
		  for(int i=0; i<nodeNum; i++) {
			  if(visited[i]) {
				  cnt++;
			  }
		  }
		  
		  if(cnt == nodeNum) {
			  return true;
		  }
		  return false;
	  }
}
