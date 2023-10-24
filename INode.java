
/**
 * Interface defining requirements on Node class
 * 
 * @author Erik Schumann
 * @version 1.0 on 21/09/2023 (dd/mm/yyyy)
 */
public interface INode {
	/**
	 * A node needs to be able to visualize itself
	 */
	void visualizeState();
	/**
	 * A node should be able to return the state its holding
	 * @return state the node is in
	 */
	String[][] getState();
}
