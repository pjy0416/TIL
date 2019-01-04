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
	private LinkedList<Integer>[] adj;	//인접 리스트 (Adjacency List)
	
	//생성자 (constructor)
	Graph(int num) {
		nodeNum = num;
		adj = new LinkedList[num];
		for(int i=0; i<num; i++) {	// 인접리스트 초기화
			adj[i] = new LinkedList<Integer>();
		}
	}
	
	void addEdge(int start, int dest) {	// 노드 연결(간선 생성) 
		adj[start].add(dest);
	}
	
	//	DFS에 의해 사용되는 함수 
	void DFSUtil(int num, boolean visited[]) {
		//현재 노드를 방문한 것으로 표시하고 값을 출력
		visited[num] = true;
		System.out.print((num+1) + " ");
		
	    // 방문한 노드에 인접한 모든 노드를 가져온다.
		Iterator<Integer> i = adj[num].listIterator();
		List<Integer> nodeList = new ArrayList<>();
		i.forEachRemaining(nodeList::add);
		Collections.sort(nodeList);
		
		for(int idx=0; idx<nodeList.size(); idx++) {
			if(checkEnd(visited)) {
				break;
			}
	    	int n = nodeList.get(idx);
	        // 방문하지 않은 노드면 해당 노드를 시작 노드로 다시 DFSUtil 호출
	    	if (!visited[n]) {
	    		DFSUtil(n, visited); // 순환 호출
	    	}
		}
		
	}

	  /** 주어진 노드를 시작 노드로 DFS 탐색 */
	  void DFS(int num) {		// 깊이 우선 탐색
	      // 노드의 방문 여부 판단 (초깃값: false)
	      boolean visited[] = new boolean[nodeNum];

	      // v를 시작 노드로 DFSUtil 순환 호출
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
				  if(!visited[n]) {	// 방문하지 않은 노드의 경우
					  visited[n] = true;	// 방문
					  queue.add(n);			// queue에 현재 노드 add
				  }
			  }
		  }
	  }
	  
	  /** 주어진 노드를 시작 노드로 DFS 탐색*/
	  void BFS(int num) {		// 너비 우선 탐색
		  boolean[] visited = new boolean[nodeNum];
		  LinkedList<Integer> queue = new LinkedList<Integer>();
		  
		  visited[num] = true;
		  queue.add(num);
		  
		  // num을 시작으로 BFSUtil 호출
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
