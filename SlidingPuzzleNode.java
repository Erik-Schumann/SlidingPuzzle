//necessary library import
import java.util.HashSet;
import java.util.Set;

/**
 * The class SlidingPuzzleNode implements INode for the Sliding Puzzle Problem
 */
public class SlidingPuzzleNode implements INode {
	SlidingPuzzleNode predecessorNode;
	String state[][];
	String act;
	int pathCostSoFar;
	SlidingPuzzleProblem slidingPuzzleProblem;

	/**
	 * constructor to create a SlidingPuzzleNode
	 * @param state of the Node
	 * @param predecessorNode parent node, if available
	 * @param act action which was taken to get from predeccessor to this node
	 * @param pathCostSoFar path costs so far of this node
	 * @param slidingPuzzleProblem problem class
	 */
	SlidingPuzzleNode(String state[][], SlidingPuzzleNode predecessorNode, String act,int pathCostSoFar,SlidingPuzzleProblem slidingPuzzleProblem) {
		this.state = state;
		this.predecessorNode = predecessorNode;
		this.act = act;
		this.pathCostSoFar = pathCostSoFar;
		this.slidingPuzzleProblem= slidingPuzzleProblem;
	}

	/**
	 * visualizeState() creates a console visualization of the state
	 */
	@Override
	public void visualizeState() {
		//iterate through every element
		for (int i = 0; i < state.length; i++) {
			//create horizontal border
			System.out.println("|--||--||--||--|");
			for (int j = 0; j < state[i].length; j++) {
				//print element & vertical border
				System.out.print("|" + state[i][j] + "|");
			}
			//switch lines
			System.out.println();
		}
		//print button border
		System.out.println("|--||--||--||--|");
	}

	/**
	 * getState() returns the state of the node
	 */
	public String[][] getState() {
		return state;
	}

	/**
	 * visualizePath() is a recursive function to print the path in order to get from the initial state to the goal state
	 */
	public void visualizePath() {
		//visualize the current node
		this.visualizeState();
		//new line
		System.out.println("");
		//print act which led from predecessor node to this node
		System.out.println("^^" + act + "^^");
		//print metrics
		System.out.println("Pathcost so far: "+ pathCostSoFar);
		//if there is a predecessor node:
		if (predecessorNode != null) {
			//new line
			System.out.println("");
			//call its visualizePath() function
			predecessorNode.visualizePath();
		}
		else {
			//print success message
			System.out.println("Goal!:)");
		}
	}
	/**
	 * visualizePath() is a recursive function to print the path in order to get from the initial state to the goal state
	 * It additionally counts the steps to reach the goal
	 * @param count of how many nodes were already visited
	 */
	public void visualizePath(int count) {
		//visualize the current node
		this.visualizeState();
		//increment count
		count++;
		//new line
		System.out.println("");
		//print act which led from predecessor node to this node
		System.out.println("^^ " + act + " ^^");
		//print metrics
		System.out.println("Pathcost so far: "+ pathCostSoFar);
		System.out.println("Heuristics so far: "+ slidingPuzzleProblem.heuristicsHamming(this));
		System.out.println("Evaluation so far: "+ slidingPuzzleProblem.evaluationFunction(this));
		//if there is a predecessor node:
		if (predecessorNode != null) {
			//new line
			System.out.println("");
			//call its visualizePath() function
			predecessorNode.visualizePath(count);
		}
		else {
			//print success message with count of steps
			System.out.println("Steps to Goal: "+count);
		}
	}

	/**
	 * compareStates() checks whether a passed node is in the same state as the given node
	 * @param compareState is the state which should be used for comparison
	 * @return Boolean, stating if both states are in the same state or not
	 */
	public Boolean compareStates(SlidingPuzzleNode compareState) {
		//get state of the passed node
		String[][] otherState = compareState.getState();
		//iterate through all elements of the nodes state
		for (int i = 0; i < state[0].length; i++) {
			for (int j = 0; j < state[1].length; j++) {
				//if there is a different element in the same position in the state, return false
				if (!state[i][j].equals(otherState[i][j])) {
					return false;
				}
			}
		}
		//if all elements are the same return true
		return true;
	}
	/**
	 * isValidState() is validating if there are any duplicate numbers in the state of the node
	 * @return Boolean, if the node is in a valid state
	 */
	public Boolean isValidState() {
		//define hash set to store numbers in
		Set<String> numbers = new HashSet<String>();
		//iterate through every element
		for (int i = 0; i < state[0].length; i++) {
			for (int j = 0; j < state[1].length; j++) {
				//if element is new, save it to hash set
				if (!numbers.contains(state[i][j])) {
					numbers.add(state[i][j]);
				}
				//if element is already in state, return false
				else {
					return false;
				}
			}
		}
		//if all elements are new (no duplicates), return true
		return true;
	}
	/**
	 * getPathCost() is a getter for the path costs so far
	 * @return the path cost so far of the node
	 */
	public int getPathCost() {
		return pathCostSoFar;
	}
	/**
	 * toString() generates a string representation of a node, enabling the generation of a hash value
	 * to perform fast comparisons in hash sets
	 * @return string representation of state
	 */
	public String toString() {
		//initialize string to save the state in
		String s="";
		//iterate through every element
		for (int i = 0; i < state.length; i++) {
			for (int j = 0; j < state[i].length; j++) {
				//add the value of the element to the string
				s=s+state[i][j];
			}
		}
		//return string
		return s;
		
	}
}
