//necessary library import
import java.util.*;

/**
 * Interface defining requirements on Problem class
 * @author Erik Schumann
 * @version 1.0 on 21/09/2023 (dd/mm/yyyy)
 */
public interface IProblem {
	/**
	 * A problem should be able to return its initial Node
	 * @return INode the Problem was initialized with
	 */
	INode getInitialNode();
	/**
	 * A problem should be able to return a set of successor nodes based on a state
	 * @param node which should be expanded
	 * @return List of successor nodes
	 */
	List<SlidingPuzzleNode> getSuccessors(SlidingPuzzleNode node);
	/**
	 * A problem  should offer a function which checks, whether a node is in the goal state
	 * @param node which should be tested
	 * @return Boolean, if node is in goal state
	 */
	Boolean isGoalState(SlidingPuzzleNode node);
	/**
	 * A problem should be able to return step costs between 2 states
	 * @param stateFrom current state
	 * @param stateTo aimed state
	 * @return step costs
	 */
	int getStepCost(String[][] stateFrom, String[][] stateTo);
	/**
	 * A problem should offer an evaluation function to approximate the step costs (so far + approximation to goal)
	 * @param node which should be evaluated
	 * @return approximated steps to reach goal
	 */
	int evaluationFunction(SlidingPuzzleNode node);
}
