import java.util.ArrayList;

public class DFS<T> implements Search<T>{
	private Graph<T> g;
	private T startNode;
	private T goalNode;
	private ArrayList<T> visited;
	
	public DFS() {
		visited = new ArrayList<T>();
	}
	
	@Override
	public void traverse() {
		T temp = goalNode;
		goalNode = null;
		traverse(startNode,0);
		goalNode = temp;
	}
	
	@Override
	public void traverse(T startNode, int numberOfSteps) {
		search(startNode, numberOfSteps);
	}
	
	@Override
	public boolean search() {
		return search(startNode,0);

	}

	@Override
	public boolean search(T currentNode, int numberOfSteps) {
		if(currentNode.equals(goalNode)) {
			System.out.println("Found the node " + goalNode + " in " +numberOfSteps + " steps");
			return true;
		}
		visited.add(currentNode);
		System.out.println(currentNode);
		ArrayList<Pair<Integer, T>> n = g.neighbors(currentNode);
		
		if(null == n)
			return false;
		int newNumberOfSteps = numberOfSteps;
		for(Pair<Integer,T> edge : n) {
			 newNumberOfSteps = numberOfSteps + edge.x;
			if(edge.y.equals(goalNode)) {
				System.out.println("Found the node " + goalNode + " in " +numberOfSteps + " steps");
				return true;
			}
			if(visited.contains(edge.y)) {
				System.out.println("Already visited node " + edge.y);
				continue;
			}
			search(edge.y,newNumberOfSteps);
		}
		return false;
	}

	@Override
	public void setGraph(Graph<T> g) {
		this.g = g;
	}

	@Override
	public void setStart(T startNode) {
		this.startNode = startNode;
	}

	@Override
	public void setGoal(T goalNode) {
		this.goalNode = goalNode;
	}

	@Override
	public T getGoal() {
		// TODO Auto-generated method stub
		return goalNode;
	}

	@Override
	public T getStart() {
		// TODO Auto-generated method stub
		return startNode;
	}
}
