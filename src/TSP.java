import java.util.ArrayList;
import java.util.List;


public class TSP<T> {
	private Graph<T> g;
	private T startNode;

	public TSP(Graph <T> g, T startNode) {
		this.g = g;
		this.startNode = startNode;
	}

	
	public List<T> shortestPath() {
		List<T>newList = new ArrayList<T>(g.getNodes());
		newList.remove(startNode);
		double minLength = Double.POSITIVE_INFINITY;
		List<T> shortestPath = null;
		for(List<T> path: allPermutations(newList)) {
			double cost = 0;
			cost += g.getCost(startNode,path.get(0));
			for (int i = 0; i < path.size()-1; i++) {
				cost+= g.getCost(path.get(i), path.get(i+1));
			}
			cost += g.getCost(path.get(path.size()-1),startNode);
			if(cost <= minLength) {
				path.add(0, startNode);
				path.add(path.size(), startNode);
				shortestPath = path;
				minLength = cost;
			}
		}
		System.out.println(minLength);
		return shortestPath;
	}
	
	public List<List<T>> allPermutations(List<T> list) {
		
		List<List<T>> allPermutations = new  ArrayList<List<T>>();
		if(list.size() == 1) {
			allPermutations.add(list);
			return allPermutations;
		}
		for(T node : list) {
			List<T> tempList = new ArrayList<T>(list);
			tempList.remove(node);
			List<List<T>> smallerListPerm = allPermutations(tempList);
			for(List<T> smallerList : smallerListPerm) {
				smallerList.add(0, node);
			}
			allPermutations.addAll(smallerListPerm);
		}
		
		return allPermutations;
	}
}
