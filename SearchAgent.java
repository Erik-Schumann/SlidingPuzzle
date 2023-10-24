
//necessary library import
import java.util.HashSet;
import java.util.PriorityQueue;

/**
 * The class SearchAgent is the implementation of ISearchAgent, 
 */
public class SearchAgent implements ISearchAgent{
	int maxSteps=5000000;
	SlidingPuzzleProblem problem;
	SlidingPuzzleNode currentNode;
	PriorityQueue<SlidingPuzzleNode> queue;
	HashSet<String> visitedSet;
	Boolean isSolvable;
	//ArrayList<ActionStatePair>
	
	
	/**
	 * Constructor to create a search agent instance
	 * @param problem The problem, the search agent should solve
	 */
	public SearchAgent(SlidingPuzzleProblem problem) {
		//save problem
		this.problem = problem;	
		//create queue for saving nodes, which should to be visited
		queue = new PriorityQueue<SlidingPuzzleNode>(new NodeComporator(problem));
		//instead of putting the Nodes in a Queue (which would be slow to search), 
		//I convert the state to a string and put it into a hash-set, which i can easily analyze (i.e. performing contains()-functions)
		visitedSet = new HashSet<String>();
		//put initial node into the queue
		queue.add(problem.getInitialNode());
		//set the solvable boolean to true
		isSolvable=true;
		//print out success message
		System.out.println("Agent created!");
		}
	/**
	 *	The search() function is the implementation of the search strategies of the search agent
	 *  Currently the A* search algorithm is used, but setting the step costs to 0 in the problem class 
	 *  would convert it into a greedy first algorithm
	 */
	public void search() {
		//get first element of the waiting queue
		currentNode = queue.remove();
		//check, if this Node contains a valid state
		if(!currentNode.isValidState()) {
			//if not print out error message and stop search
			System.out.println("Invalid State!");
			return;
		}
		//check, if the current Node is already the goal state
		if(problem.isGoalState(currentNode)) {
			//if so, print out success message and stop search
			System.out.println("Nothing to do for me ;)");
			return;
		}
		//print success message, that the search was started
		System.out.println("Search started!");
		//initialize variables for metrics
		int i = 0, j = 0, k = 0, l=0, mht=100;
		//return steps, when the goal state was not reached
		while((!problem.isGoalState(currentNode)))
		{
			//get all successors and apply following actions to them
			for(SlidingPuzzleNode successorNode:problem.getSuccessors(currentNode)) {
				//check, if the successor would an already visited state
				if(!Visited(successorNode)) {
					//if this was not visited before add him to the queue holding states which needs to be visted
					queue.add(successorNode);
					//increment metric (queue size)
					l++;
				}
				else {
					//increment metric (duplicates)
					j++;
				}
				//increment metric (evaluated successors)
				k++;
			}
			//when queue is empty or max steps are exceeded, set solvable to false and stop search
			if(queue.isEmpty()||i==maxSteps) {
				isSolvable = false;
				break;
			}
			//add evaluated step to visited steps
			visitedSet.add(currentNode.toString());
			//if a better heuristic than before was achieved, set it to the new best and print out a success message
			if(mht>problem.heuristicsManhattan(currentNode)) {
				mht=problem.heuristicsManhattan(currentNode);
				System.out.println("Manhattan: "+mht);
			}
			//get next Node from queue
			currentNode = queue.remove();
			//increment metrics (steps tested)
			i++;
			//print count of steps, when it reaches a multiple of 100,000
			if(i%100000==0) {
				System.out.println("Checked states: "+i);
			}
		}
		//visualize Path, if problem was solved and print metrics summary
		if(isSolvable) {
			currentNode.visualizePath(0);
			System.out.println("Steps tested to reach goal "+i);
			System.out.println("Queue size: "+l);
			System.out.println("Prevented duplicates: "+j);
			System.out.println("Evaluated Steps: "+k);
		}
		//visualize last state, if problem was not solved and print metrics summary
		else {
			currentNode.visualizeState();
			if(queue.isEmpty()){
				System.out.println("No Solution found! (no more steps available)");
			}
			if(i==maxSteps){
				System.out.println("No Solution found! (step limit reached)");
			}
			System.out.println("Steps tested: "+i);
			System.out.println("Queue size: "+l);
			System.out.println("Prevented duplicates: "+j);
			System.out.println("Evaluated Successor: "+k);
		}

	}
	/**
	 * @param node that should be checked, whether its state was already visited
	 * @return boolean, if state was already visited
	 */
	public boolean Visited(SlidingPuzzleNode node) {
		//set isVisisted to false
		Boolean isVisited= false;
		//if the set already contains this state, set isVisisted to true
		if(visitedSet.contains(node.toString())) {
			isVisited=true;
		}
		//return boolean, if state was already visited
		return isVisited;
	}
}
