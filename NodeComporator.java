
//necessary library import
import java.util.Comparator;


/**
 * NodeComporator is an implementation of the comparator interface, to sort Nodes based on their evaluation function 
 */
public class NodeComporator implements Comparator<SlidingPuzzleNode>{
	SlidingPuzzleProblem slidingPuzzleProblem;
	/**
	 * constructor to create an instance of NodeComporator
	 * @param slidingPuzzleProblem which implements the evaluation function
	 */
	public NodeComporator(SlidingPuzzleProblem slidingPuzzleProblem) {
		this.slidingPuzzleProblem=slidingPuzzleProblem;
	}
	/**
	 * compare() is comparing the "total expected path costs"(path so far + heuristics) of 2 nodes
	 */
	@Override
	public int compare(SlidingPuzzleNode o1, SlidingPuzzleNode o2) {
		//if node o1 has a worse total expected path, return 1
		if(slidingPuzzleProblem.evaluationFunction(o1)>slidingPuzzleProblem.evaluationFunction(o2)) {
			return 1;
		}
		//if node o1 has a better total expected path, return -1
		else if(slidingPuzzleProblem.evaluationFunction(o1)<slidingPuzzleProblem.evaluationFunction(o2)) {
			return -1;
		}
		//if node o1 has the same total expected path, return 0
		else{
			return 0;
		}
	}

}
