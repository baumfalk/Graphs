import java.util.ArrayList;
import java.util.HashMap;

public class Graph<T> {

	private HashMap<T, ArrayList<Pair<Integer,T>>> edges;
	private ArrayList<T> nodes;

	public Graph() {
		edges = new HashMap<T, ArrayList<Pair<Integer,T>>>();
		nodes = new ArrayList<T>();
	}

	public void addNode(T node) {
		if (nodes.contains(node)) {
			System.out.println(node + " already exists");
		} else {
			nodes.add(node);
			ArrayList<Pair<Integer,T>> nodeEdges = new ArrayList<Pair<Integer,T>>();
			edges.put(node, nodeEdges);
	
		}
	}

	public void addEdge(T nodeA, T nodeB, int weight) {
		if (!nodes.contains(nodeA))
			addNode(nodeA);
		if (!nodes.contains(nodeB))
			addNode(nodeB);
		
		boolean exists = false;
		for(int i = 0; i < edges.get(nodeA).size(); i++) {
			Pair<Integer,T> p = edges.get(nodeA).get(i);
			if(p.y.equals(nodeB)) {
				exists = true;
				break;
			}
		}
		if (exists) {
			System.out.println(nodeA + " already has an edge to " + nodeB);
		} else {
			edges.get(nodeA).add(new Pair<Integer,T>(weight,nodeB));
		}
	}

	public void addBiEdge(T nodeA, T nodeB, int weight) {
		addEdge(nodeA, nodeB, weight);
		addEdge(nodeB, nodeA, weight);
	}

	public boolean deleteNode(T node) {
		boolean removed = nodes.remove(node);
		deleteEdge(node);
		deleteEdgesWithNode(node);

		return removed;
	}

	public void deleteEdgesWithNode(T node) {
		int numberRemoved = 0;
		for (T otherNode : edges.keySet()) {
			if (deleteEdge(otherNode, node))
				numberRemoved++;
		}
		System.out.println(numberRemoved + " occurrences of " + node
				+ " removed.");
	}

	public void deleteEdge(T node) {
		edges.remove(node);
	}

	public boolean deleteEdge(T nodeA, T nodeB) {
		boolean success = false;
		for(int i = 0; i < edges.get(nodeA).size(); i++) {
			Pair<Integer,T> p = edges.get(nodeA).get(i);
			if(p.y.equals(nodeB))
				success =  edges.get(nodeA).remove(p);
		}
		return success;
	}


	public ArrayList<Pair<Integer, T>> neighbors(T node) {
		return edges.get(node);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Nodes:\n");
		for (T node : nodes) {
			sb.append("\t" + node + "\n");
			sb.append("\t" + node + " has the following neighbors\n");
			for (Pair<Integer,T> neighborEdge : edges.get(node)) {
				sb.append("\t\tdistance:" + neighborEdge.x + " node: " + neighborEdge.y + "\n");
			}
		}

		return sb.toString();
	}
}
