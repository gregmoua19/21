import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class Draw21 {
	
	static int playerTotalScore = 0;
	static int computerTotalScore = 0;
	static int round = 0;
	static ArrayList<String> playerHand = new ArrayList<String>(1);
	static ArrayList<String> computerHand = new ArrayList<String>(1);
	
	public static void main(String[] args) {
		Scanner input = new Scanner (System.in);
		run(input);
	}
	
	
	public static void run(Scanner input) {
		String userChoice = wantToPlay(input);
		if(userChoice.equalsIgnoreCase("no") || userChoice.equalsIgnoreCase("n")) {
			scoreBoard(userChoice);
			return;	
		}
		
		ArrayList<String> deck = createDeck();		
		playGame(deck);
		userChoice = wantToPlay(input);
		if(userChoice.equalsIgnoreCase("no") || userChoice.equalsIgnoreCase("n")) {
			scoreBoard(userChoice);
		} else {
			while(userChoice.equalsIgnoreCase("Yes") || userChoice.equalsIgnoreCase("Y")) {
				run(input);
			}
		}
	}
	
	//Method that will repeatedly prompt the user if they would like to play
	public static String wantToPlay(Scanner input) {
		if(round < 1) {
			System.out.println("Welcome to BlackJack. In this game we will shuffle an ordinary deck of cards and play to the classic drawing to 21 game.");
		} 
		System.out.println("Please enter Yes or No to play");
		String userChoice = input.nextLine();
		while(!(userChoice.equalsIgnoreCase("Y") || userChoice.equalsIgnoreCase("Yes") || userChoice.equalsIgnoreCase("N") || userChoice.equalsIgnoreCase("No"))) {
		System.out.println("Please enter Y/Yes or N/No");
		userChoice = input.nextLine();
		}
		return userChoice;
	}
	
	//Method that will print out the scoreboard
	public static void scoreBoard(String userChoice) {
		System.out.println("Thanks for playing! ");
		if(playerTotalScore != 0 || computerTotalScore != 0) {
			System.out.println("Your score: " + playerTotalScore + "\nComputer score: " + computerTotalScore);
		}
	}
	
	//Method that will "play the game" ie. call the createDeck method,
	//draw the cards for both the player and computer, and call the winCondition method.
	public static void playGame(ArrayList<String> deck) {
		round++;
		System.out.println("Round " + round + ": ");
		Random rand = new Random();
		int mystery = 0;
		String temp = "";
		int computerCards = 0;
		int playerCards = 0;
		//.size for arrayLists 
		//.length for arrays
		for(int i = 0; i < 2;i++) {
			mystery = rand.nextInt(deck.size());
			System.out.println("You drew " + deck.get(mystery));
			temp = deck.get(mystery);
			playerHand.add(temp);
			temp = temp.substring(0,1);
			playerCards += pointValue(temp);
			deck.remove(mystery);
		}
		
		System.out.println("You currently have " + playerCards + " points");
		
		
		
		for(int i = 0; i < 2; i++) {
			mystery = rand.nextInt(deck.size());
		//System.out.println("Computer drew " + deck.get(mystery));
		temp = deck.get(mystery);
		computerHand.add(temp);
		temp = temp.substring(0,1);
		computerCards += pointValue(temp);
		deck.remove(mystery);
		}
	
		Scanner input = new Scanner(System.in);
		String repeat = keepGoing(input);
		int counter = 0;
		
		while(repeat.equalsIgnoreCase("Draw")) {
			mystery = rand.nextInt(deck.size());
			System.out.println("You drew " + deck.get(mystery));
			temp = deck.get(mystery);
			playerHand.add(temp);
			temp = temp.substring(0,1);
			playerCards += pointValue(temp);
			System.out.println("You currently have " + playerCards + " points." );
			deck.remove(mystery);
			aceCondition(playerCards, computerCards);
			if(playerCards >= 21) {
				counter++;
				winCondition(playerCards,computerCards);
				break;
			}
			//keep looping until user is satisfied
			repeat = keepGoing(input);

		} 
		while(computerCards < 16) {
			mystery = rand.nextInt(deck.size());
			temp = deck.get(mystery);
			computerHand.add(temp);
			temp = temp.substring(0,1);
			computerCards += pointValue(temp);
			deck.remove(mystery);
			}
		if(counter == 0) {
		winCondition(playerCards, computerCards);
		}
	}
	
	//Method that allows the user to keep drawing cards if they would like to
	public static String keepGoing(Scanner input) {
		System.out.println("Would you like to stay where you are or draw another card?\nCheck\\Draw");
		String repeat = input.nextLine();
		while(!(repeat.equalsIgnoreCase("Check") || repeat.equalsIgnoreCase("Draw"))) {
			System.out.println("Please enter either 'Check' or 'Draw'");
			repeat = input.nextLine();
		}
		
		return repeat;
	}
	
	//Method that calculates the aces since aces can be worth either 11 or 1 in this game
	public static void aceCondition(int playerCards, int computerCards) {
		if(playerHand.contains("Ace of Spades") || playerHand.contains("Ace of Clubs") ||  playerHand.contains("Ace of Hearts") ||  playerHand.contains("Ace of Diamonds")) {
			if(playerCards > 21 ) {
				playerCards -= 10;
			}
		} else if(computerHand.contains("Ace of Spades") || computerHand.contains("Ace of Clubs") || computerHand.contains("Ace of Hearts") || computerHand.contains("Ace of Diamonds")) {
			if (computerCards > 21) {
				computerCards -= 10;
			}
		}
	}
	
	//Method that covers all the win conditions for both the player
	//and the computer and increments their points by whoever wins.
	public static void winCondition(int playerCards, int computerCards) {

		//in this one if they have an ace
		//split it into two scores
		//original 11 and one that is -10
		//if the original +11 is over 21, then throw it out
		System.out.println("\nTime to show your hands! ");
		System.out.println("Your hand: " + playerHand.toString() + "\nYour Points: " + playerCards + "\n");
		System.out.println("Computer hand: " + computerHand.toString() + "\nComputer Points " + computerCards + "\n");
	
		
		if(playerCards > 21 && computerCards < 22) {
			System.out.println("Looks like you busted. You lost!");
			computerTotalScore++;
		} else if (computerCards > 21 && playerCards < 22) {
			System.out.println("The computer busted. You won!");
			playerTotalScore++;
		} else if (computerCards > 21 && playerCards > 21) {
			System.out.println("You both bust! Nobody wins this one!");
		} else if(playerCards < 22 && computerCards < 22 && playerCards == computerCards) {
			System.out.println("It was a tie!");
		} else if (playerCards < 22 && playerCards > computerCards) {
			System.out.println("You won!");
			playerTotalScore++;
		} else {
			System.out.println("You lost!");
			computerTotalScore++;
		}
		
		//empty both hands
		playerHand.clear();
		computerHand.clear();
		
		//reset both card points
		playerCards = 0;
		computerCards = 0;	
	}
	
	//Method that attaches values to the associated String.
	//This allows us to add to the user's and computer's round score as they try to 
	//get closer to 21
	public static int pointValue(String name) {
		
		if(name.contains("A")) {
			return 11;
		} else if (name.contains("2")) {
			return 2;
		} else if (name.contains("3")) {
			return 3;
		} else if (name.contains("4")) {
			return 4;
		} else if (name.contains("5")) {
			return 5;
		} else if (name.contains("6")) {
			return 6;
		} else if (name.contains("7")) {
			return 7;
		} else if (name.contains("8")) {
			return 8;
		} else if (name.contains("9")) {
			return 9;
		}  else {
			return 10;
		}
	}
	
	//Method that creates the deck by making two ArrayList of Strings
	//(one of Suits and one of Values) and combining them together to 
	//creating an ordinary 52 deck of cards
	public static ArrayList<String> createDeck() {
		
		//creating suits
		ArrayList<String> suits = new ArrayList<String>();
		suits.add("Clubs");
		suits.add("Spades");
		suits.add("Diamonds");
		suits.add("Hearts");
		
		//creating values
		ArrayList<String> values = new ArrayList<String>();
		values.add("2");
		values.add("3");
		values.add("4");
		values.add("5");
		values.add("6");
		values.add("7");
		values.add("8");
		values.add("9");
		values.add("10");
		values.add("Jack");
		values.add("Queen");
		values.add("King");
		values.add("Ace");
		
		//creating deck
		ArrayList<String> deck = new ArrayList<String>();
		
		//making nested for each loops that will add all values of 
		//ordinary deck to the deck arraylist
		for(String suit : suits) {
			for(String value : values) {
				deck.add(value + " of " + suit);
			}
		}
		return deck;
		
	}

}
