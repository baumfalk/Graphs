
public interface Search<T> {
	public void traverse();
	public void traverse(T startNode,int numberOfSteps);
	public boolean search();
	boolean search(T currentNode, int numberOfSteps);
	public void setGraph(Graph<T> g);
	public T getGoal();
	public void setGoal(T goalNode);
	public void setStart(T startNode);
	public T getStart();
	
}
