import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;


public class BFS<T> {
	private Graph<T> g;
	private T startNode;
	private T goalNode;
	private ArrayList<T> visited;
	public BFS() {
		visited = new ArrayList<T>();
	}
	
	public void addGraph(Graph<T> g) {
		this.g = g;
	}
	
	public void setStartNode(T startNode) {
		this.startNode = startNode;
	}
	
	public void setGoalNode(T goalNode) {
		this.goalNode = goalNode;
	}
	
	public boolean search() {
		Queue<Pair<T,Double>> queue = new LinkedList<Pair<T,Double>>();
		queue.add(new Pair<T,Double>(startNode,0d));
		Pair<T,Double> currentPair;
		
		while(!(currentPair = queue.remove()).x.equals(goalNode)) {
			System.out.println(currentPair.x);
			visited.add(currentPair.x);
			ArrayList<Pair<T, Double>> temp = new ArrayList<Pair<T,Double>>(g.getEdges(currentPair.x));
			
			for(int i=0;i<temp.size();i++) {
				
				if(visited.contains(temp.get(i).x)) {
					temp.remove(i);
				}
			}
			
			queue.addAll(temp);
			if(queue.isEmpty())
			{
				return false;
			}
			
		}
		return true;
	}
	
}
