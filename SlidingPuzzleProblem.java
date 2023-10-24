
//necessary library import
import java.util.*;
import java.util.Arrays;

/**
 * SlidingPuzzleProblem implements IProblem and defines the Sliding Puzzle problem
 */
public class SlidingPuzzleProblem implements IProblem {

	SlidingPuzzleNode initialNode;
	/**
	 * constructor to create an instance of the sliding puzzle problem
	 * @param initialState of the problem
	 */
	public SlidingPuzzleProblem(String[][] initialState) {
		 initialNode= new SlidingPuzzleNode(initialState , null, "Start", 0,this);
			System.out.println("Problem created!");
	}

	/**
	 * getInitialNode() is a getter for the initial state of the Sliding Puzzle problem
	 * @return SlidingPuzzleNode the initial node of the problem
	 */
	@Override
	public SlidingPuzzleNode getInitialNode() {
		return initialNode;
	}
	/**
	 * copy() hard copies a string matrix
	 * @param src the string matrix which should be copied
	 * @return hard copied string matrix
	 */
	public static String[][] copy(String[][] src) {
		//source: https://www.techiedelight.com/create-copy-of-2d-array-java/#:~:text=A%20simple%20solution%20is%20to,method%20to%20copy%20each%20row.
        //check for nullpointer
		if (src == null) {
            return null;
        }
		//return copied array
        return Arrays.stream(src).map(String[]::clone).toArray(String[][]::new);
    }
	/**
	 * getSuccessors() is expanding the passed node and returns a list of successor nodes
	 * @param node which should be expanded
	 * @return list of successor nodes of the passed node
	 */
	@Override
	public ArrayList<SlidingPuzzleNode> getSuccessors(SlidingPuzzleNode node) {
		//initialize variables for analyzing coordinates
		int x=0,y=0;
		//copy state from node
		String[][] currentState = copy(node.getState());
		//create new array list to store successor nodes
		ArrayList<SlidingPuzzleNode> successors = new ArrayList <SlidingPuzzleNode>();
		//get x and y of space
		for (int i = 0; i < currentState[0].length; i++) {
			for (int j = 0; j < currentState[1].length; j++) {
				if ("  ".equals(currentState[i][j])) {
					//set the coordinates to be the spaces' one
					x=i;
					y=j;
					//check for every possible neighbor, if its coordinates would be on the board
					//if yes, create a successor and add its to the list
					if(isInBoard(x-1,y)){
						successors.add(createSuccessor(x-1,y,x,y,node));
					}
					if(isInBoard(x+1,y)){
						successors.add(createSuccessor(x+1,y,x,y,node));
					}
					if(isInBoard(x,y-1)) {
						successors.add(createSuccessor(x,y-1,x,y,node));
					}
					if(isInBoard(x,y+1)) {
						successors.add(createSuccessor(x,y+1,x,y,node));
					}
					//return list of successors
					return successors;
				}
			}
		}
		return null;
	}
	/**
	 * isInBoard() is testing, whether a coordinate (x/y) is a valid coordinate on the 4x4 board
	 * @param x coordinate
	 * @param y coordinate
	 * @return Boolean, if the coordinate is valid
	 */
	public Boolean isInBoard(int x, int y) {
		//validate x coordinate
		if(x>3||x<0) {
			//return false, if out of range
			return false;
		}
		//validate y coordinate
		if(y>3||y<0) {
			//return false, if out of range
			return false;
		}
		//return true, if both (x/y) is on Board
		return true;
	}
	/**
	 * createSuccessor() is a function to create a successor based on the information, which element should be moved
	 * @param xPrev x coordinate of the element, should should be moved
	 * @param yPrev y coordinate of the element, should should be moved
	 * @param x coordinate of space element
	 * @param y coordinate of space element
	 * @param predecessorNode of the node, which should be created
	 * @return the created node
	 */
	public SlidingPuzzleNode createSuccessor(int xPrev, int yPrev, int x, int y, SlidingPuzzleNode predecessorNode) {
		//copy old state to new state
		String[][] newState = copy(predecessorNode.getState());
		//create action description for new state
		String act = "Move "+newState[xPrev][yPrev];
		//save the space to the element, which should be moved
		String prevField= newState[xPrev][yPrev];
		//set the space to the moved elements' old place
		newState[xPrev][yPrev]="  ";
		//set the space to the element, which should be moved
		newState[x][y]= prevField;
		//create successor node
		SlidingPuzzleNode successorNode = new SlidingPuzzleNode(newState, predecessorNode, act, (predecessorNode.getPathCost()+this.getStepCost(predecessorNode.getState(), newState)),this);
		//return successor node
		return successorNode;
	}
	/**
	 * isGoalState() compares the state of the passed node to the goal state, by that performing a goal test
	 * @return Boolean, if the passed node is in goal state
	 */
	@Override
	public Boolean isGoalState(SlidingPuzzleNode node) {
		//define what is a goal state
		String[][] goalState = { { " 1", " 2", " 3", " 4" }, { " 5", " 6", " 7", " 8" }, { " 9", "10", "11", "12" },
				{ "13", "14", "15", "  " } };
		//return function to compare both states ( create a temporary node for the goal state)
		return node.compareStates(new SlidingPuzzleNode(goalState , null, null,0,this));
	}
	/**
	 * getStepCost() returns the step costs for one state to another. When set to 0 the search agent uses a greedy first algorithm, when set to 1 A*
	 * @return integer which indicates the step costs from one state to another
	 */
	@Override
	public int getStepCost(String[][] stateFrom, String[][] stateTo) {
		//return 1, since the costs of move a element is 1
		return 1;
	}
	/**
	 * @param node which total step costs (so far + approximation to goal) should be evaluated
	 * @return number of approximated steps
	 */
	public int evaluationFunction(SlidingPuzzleNode node) {
		//return sum of step costs so far + Manhattan distance of node
		return node.getPathCost()+this.heuristicsManhattan(node);
	}
	
	/**
	 * heuristicsHamming() is a function to approximate the steps from that node to goal. 
	 * It is based on how much elements are in the wrong order (how many do I need to put out and set in the correct position. 
	 * Since this is a worse heuristic than Manhattan, this heuristic is provided, but not used
	 * @param node which should be evaluated
	 * @return approximated step costs
	 */
	public int heuristicsHamming(SlidingPuzzleNode node) {
		//https://www.cs.princeton.edu/courses/archive/spring20/cos226/assignments/8puzzle/specification.php#:~:text=The%20Hamming%20distance%20betweeen%20a,tiles%20to%20their%20goal%20positions.
		//define what a goal state is
		String[][] goalState = { { " 1", " 2", " 3", " 4" }, { " 5", " 6", " 7", " 8" }, { " 9", "10", "11", "12" },
				{ "13", "14", "15", "  " } };
		//define and declare how much elements can be possibly incorrect
		int numincorrct=16;
		//iterate through every element and compare it to the goal state
		for (int i = 0; i < goalState[0].length; i++) {
			for (int j = 0; j < goalState[1].length; j++) {
				//if a element is in the same position as in the goal state, decrement the incorrect count
				if (goalState[i][j].equals(node.getState()[i][j])) {
					numincorrct--;
				}
			}
		}
		//return count of incorrects
		return numincorrct;
	}
	/**
	 * heuristicsManhattan() is a function to approximate the steps from that node to goal. 
	 * The function accumulates the Manhattan distance of each element. 
	 * The Manhattan distance is absolute value of the delta in the x coordinate (to the goal position of the element) 
	 * plus absolute value of the delta in the y coordinate (to the goal position of the element) 
	 * @param node which should be evaluated
	 * @return approximated step costs (accumulated Manhattan distance)
	 */
	public int heuristicsManhattan(SlidingPuzzleNode node) {
		//https://www.cs.princeton.edu/courses/archive/spring20/cos226/assignments/8puzzle/specification.php#:~:text=The%20Hamming%20distance%20betweeen%20a,tiles%20to%20their%20goal%20positions.
		//set accumulates distance to 0
		int distance=0;
		//define goal state
		String[][] goalState = { { " 1", " 2", " 3", " 4" }, { " 5", " 6", " 7", " 8" }, { " 9", "10", "11", "12" },
				{ "13", "14", "15", "  " } };
		//iterate through every element
		for (int i = 0; i < goalState[0].length; i++) {
			for (int j = 0; j < goalState[1].length; j++) {
				//get the coordinates of the element in the passed node
				int [] crd = getPosfromState(node, goalState[i][j]);
				//calculate the Manhattan distance and add it to the accumulated distance
				distance+=Math.abs(i-crd[0])+Math.abs(j-crd[1]);
			}
		}
		//return Manhattan distance
		return distance;
	}
	/**
	 * @param node in which state the position of a element should be searched
	 * @param number which is searched
	 * @return 2 dimensional vector, storing the x and y coordinates of the element, which was searched
	 */
	public int[] getPosfromState(SlidingPuzzleNode node, String number) {
		//get state from node
		String[][] currState= node.getState();
		//iterate through state
		for (int i = 0; i < currState[0].length; i++) {
			for (int j = 0; j < currState[1].length; j++) {
				//if the element is the searched number, create 2 dimensional vector, save x/y coordinates and return it
				if (currState[i][j].equals(number)) {
					int [] crd = new int[2];
					crd[0]=i;
					crd[1]=j;
					return crd;
				}
			}
		}
		//if number was not found, return null
		return null;
	}
}
