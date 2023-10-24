
//necessary library import
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

/** SlidingPuzzle.java starts the program
 * 
 * @author Erik Schumann
 * @version 1.0 on 21/09/2023 (dd/mm/yyyy)
 */
public class SlidingPuzzle {
	static Set<String> numbers;
	
	/**
	 * Standard constructor (not used)
	 */
	public SlidingPuzzle() {
		
	}
	/**
	 * Main function to start program
	 * @param args input (will not be considered)
	 */
	public static void main(String[] args) {
		//create Hashset for isValidInput() function to check for duplicates
		numbers = new HashSet<String>();
		String[][] state;
		//initialize testBoard for testing purposes
		/*state = {  { " 2", " 7", " 9", " 8" },
							   		  { "14", "13", " 1", " 5" },
									  { " 3", " 4", "10", "15" },
									  { "12", " 6", "11", "  " }};*/
		//if you do not want to use user defined board, comment line 17 out
		state =readInInitialState();
		//create an instance of the SlidingPuzzle problem, passing the testBoard
		SlidingPuzzleProblem slidingPuzzleProblem = new SlidingPuzzleProblem(state);
		//create an instance of the SlidingPuzzle search agent, passing the SlidingPuzzle Problem
		SearchAgent searchAgent = new SearchAgent(slidingPuzzleProblem);
		//active the search of the search agent
		searchAgent.search();
	}
	/**
	 * readInInitialState() reads in a user defined board, ensuring it is a valid board
	 * @return the user defined board
	 */
	public static String[][] readInInitialState(){
		//define new scanner object, to read in console input
		Scanner userInput = new Scanner( System.in );
		//define empty board, which should be filled
		String[][] boardSoFar = {  { "  ", "  ", "  ", "  " },
							   	   { "  ", "  ", "  ", "  " },
								   { "  ", "  ", "  ", "  " },
								   { "  ", "  ", "  ", "  " }};
		//print out welcome Message and Command
		System.out.println("-------------------------------------");
		System.out.println("|Sliding Puzzle Search Agent by Erik|");
		System.out.println("-------------------------------------");
		System.out.println("For  \"  \" please input 0");
		//iterate through every element of the empty board
		for (int i = 0; i < boardSoFar.length; i++) {
			for (int j = 0; j < boardSoFar[i].length; j++) {
				//mark the element, which should be filled
				boardSoFar[i][j]="**";
				//visualize board so far for user
				visualizeState(boardSoFar);
				//print out request to enter new number
				System.out.print(">>>");
				//read in new number
				int number = userInput.nextInt();
				//when input is not valid, repeat this steps until its valid
				while(!isValidInput(number)) {
					//print out Error message
					System.out.println("Invalid Input, try again!");
					//print out request to enter new number
					System.out.print(">>>");
					//read in new number
					number = userInput.nextInt();
				}
				//if 0 is the input insert empty space
				if(number==0) {
					boardSoFar[i][j]="  ";
				}
				//else convert number to 2-digit string and insert it into the element
				else {
					boardSoFar[i][j]=convertInput(number);
				}
				//make new line for design purposes
				System.out.println();
			}
		}
		//close scanner object
		userInput.close();
		//print success message
		System.out.println("Input finished!");
		//return the user defined board
		return boardSoFar;
	}
	/**
	 * isValidInput() is validating the user input (check for duplicates or out of range numbers)
	 * @param input number which should be checked
	 * @return boolean, if number is a valid input
	 */
	public static Boolean isValidInput(int input) {
		//check range
		if(input>15 || input<0) {
			return false;
		}
		//check dupicates
		if(numbers.contains(convertInput(input))) {
			return false;
		}
		else {
			//add to exisiting numbers
			numbers.add(convertInput(input));
		}
		//return true, if all checks passed
		return true;
	}
	
	/**
	 * convertInput() converts a number into a 2 digit string
	 * @param i number to be converted
	 * @return converted string
	 */
	public static String convertInput(int i) {
		//declare output string
		String inp;
		//is it's just a one digit number(0-9), convert and add a empty space in front of it
		if(i<10) {
			inp = " ".concat(String.valueOf(i));
		}
		//if its a two digit number (10-15), just convert it to string
		else {
			inp= String.valueOf(i);
		}
		//return string representation of the number
		return inp;
	}
	/**
	 * visualizeState() creates a console visualization of the passed state
	 * @param state the state which should be visualized
	 */
	public static void visualizeState(String[][] state) {
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
}
