
public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Graph<Integer> g = new Graph<Integer>();
		g.addEdge(0, 1, 10);
		g.addEdge(0, 2, 3);
		g.addEdge(1,3,5);
		g.addEdge(2,4,7);
		g.addEdge(3,5,1);
		g.addEdge(4, 5,8);
		DFS<Integer> dfs = new DFS<Integer>();
		
		dfs.setGraph(g);
		dfs.setStart(0);
		dfs.setGoal(5);
		dfs.search();
		
	}

}
