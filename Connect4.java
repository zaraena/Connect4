/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package core;
import java.util.Arrays;
import java.util.Scanner;
import java.util.InputMismatchException;
import ui.Connect4TextConsole;
import ui.Connect4GUI;

/**
 * core logic of the connect 4 program
 */
public class Connect4 {
	
	public int choice;

    /**
     * Represents the connect4 game board. Populated by players via setGameBoard.
     */
    public String gameBoard[][] = new String[7][7];

    /**
     * Used to track the total number of pieces in each column
     */
    public int columnCounter[] = new int[7];

    /**
     * Used to track the total number of pieces in the game board
     */
    public int totalCounter = 0;

    /**
     * Set to either 'X' or 'O' to determine which piece is placed
     */
    public String currentPlayer;

    /**
     * Used in nextState() to determine program state
     */
    public int state = 0;

    /**
     * Flag used to determine if a piece was successfully placed
     */
    public boolean placed;
    
    /**
     * Set to 2 for VS Player mode or 5 for VS CPU mode
     */
    public int playerTwoMode;
    
    /**
     * Decides the mode of the game/which state machine method to use
     */
    public String mode = "";
    
    /**
     * Connect4 object constructor
     * > initializes each index of the columnCounter int array
     * > Talks to nextState() to begin the game
     */
    public Connect4() {
        for (int i = 0; i < columnCounter.length; i++) {
            columnCounter[i] = 0;
        }
        //nextState();
    }
    
    /**
     * @return the game board itself
     */
    public String[][] getGameBoard() {
        return gameBoard;
    }
    
    /**
     * 
     * @param row where the method will attempt to place a piece
     * @param col where the player chose to place a piece
     */
    public void setGameBoard(int row, int col) {
        try {
            if (row > gameBoard.length
             || col > gameBoard[gameBoard.length-1].length
             || row < 0
             || col < 1) {
                System.out.println("Invalid input! Please try again.");
                nextState();
            } else {
                /**
                 * check if the space below is empty, if so drop the piece
                 * recursively until it lands on an occupied space or reaches the
                 * bottom of the board
                 **/
                if (row == gameBoard[row].length-1) {
                    //gamepiece reached the bottom of the board, place it
                    gameBoard[row][col-1] = currentPlayer;
                    placed = true;

                    columnCounter[col-1] += 1;
                    totalCounter++;
                    printGameBoard();

                } else if (columnCounter[col-1] == 7) {
                    if (totalCounter == 49) {
                        state = 4;
                        nextState();
                    }else {
                        //column is full, choose a new column
                        System.out.println("This column is full! Please choose a new column.");
                        nextState();
                    }

                } else if (gameBoard[row+1][col-1] == "X"
                        || gameBoard[row+1][col-1] == "O") {
                    //gamepiece landed on top of another gamepiece, place it
                    gameBoard[row][col-1] = currentPlayer;
                    placed = true;

                    columnCounter[col-1] += 1;
                    totalCounter++;
                    printGameBoard();

                } else {
                    //gamepiece is still falling
                    setGameBoard(row + 1, col);

                }
            }
        } catch (InputMismatchException e) {
            System.out.println("Invalid Input! Your input must be an integer.");
            nextState();
        }
    }
    
    /**
     * 
     * @return win is handled by the logic in this method to determine whether
     * or not the previous turn resulted in a win for that player. Separate
     * checks for horizontal, vertical, and diagonal wins
     */
    public boolean checkWin() {
        boolean win = false;
        int pieceCount = 0;
        
        //check for a horizontal win
        boolean checking = true;
        while (checking) {
            for (int i = 0; i < gameBoard.length; i++) {
                for (int j = 0; j < gameBoard[i].length; j++) {
                    if (gameBoard[i][j] == currentPlayer) pieceCount += 1;
                    else pieceCount = 0;

                    if (pieceCount >= 4) {
                        win = true;
                        checking = false;
                    }
                }
            }
            break;
        }

        //check for a vertical win
        checking = true;
        while (checking) {
            for (int i = 0; i < gameBoard.length; i++) {
                for (int j = 0; j < gameBoard[i].length; j++) {
                    if (gameBoard[j][i] == currentPlayer) pieceCount += 1;
                    else pieceCount = 0;

                    if (pieceCount >= 4) {
                        win = true;
                        checking = false;
                    }
                }
            }
            break;
        }
        
        //check for a diagonal win from top to left
        checking = true;
        while (checking) {
            for (int row = 0; row < gameBoard.length-3; row++) {
                for (int col = 0; col < gameBoard[row].length-3; col++) {
                    if (gameBoard[row][col] == currentPlayer
                     && gameBoard[row][col] == gameBoard[row+1][col+1]
                     && gameBoard[row][col] == gameBoard[row+2][col+2]
                     && gameBoard[row][col] == gameBoard[row+3][col+3]){
                        win = true;
                        checking = false;
                    }
                }
            }
            break;
        }
        
        //check for a diagonal win from top to right
        checking = true;
        while (checking) {
            for (int row = 0; row < gameBoard.length-3; row++) {
                for (int col = 3; col < gameBoard[row].length; col++) {
                    if (gameBoard[row][col] == currentPlayer
                     && gameBoard[row][col] == gameBoard[row+1][col-1]
                     && gameBoard[row][col] == gameBoard[row+2][col-2]
                     && gameBoard[row][col] == gameBoard[row+3][col-3]){
                        win = true;
                        checking = false;
                    }
                }
            }
            break;
        }
        return win;
    }
    
    /**
     * begin game and allow players to place pieces until one player connects 4
     */
        public void nextState() {
        Scanner scan = new Scanner(System.in);
        
        switch (state) {
            //Command-line mode states
            case 0:
                System.out.println("\n\n\n///////////////////////////////////////////////////////////////");
                System.out.println("////////////WELCOME TO CONNECT 4 COMMAND-LINE MODE!////////////");
                System.out.println("///////////////////////////////////////////////////////////////");
                System.out.println("Would you like to play against the computer or a live opponent?\n\nPlease enter 1 to play against a real player or enter 2 to play\nagainst the computer.");
                try {
                    choice = scan.nextInt();
                    if (choice == 1 || choice == 2) {
                        System.out.println("\n\n\n///////////////////////////////////////////////////////////////");
                        printGameBoard();
                        if (choice == 1) {
                            System.out.println("VS Player chosen. Begin Game.");
                            playerTwoMode = 2;
                            
                        } else {
                            System.out.println("VS Computer chosen. Begin Game.");
                            playerTwoMode = 5;
                        }
                        state = 1;
                        nextState();
                    } else {
                        System.out.println("Invalid input! Please enter 1 or 2 to select your mode.\n");
                        nextState();
                    }
                } catch (InputMismatchException e) {
                    System.out.println("Invalid input! Your input must be an integer.");
                    nextState();
                }
            case 1:
                currentPlayer = "X";
                System.out.println("PlayerX – your turn. Choose a column number from 1-7.");
                choice = scan.nextInt();
                setGameBoard(0, choice);
                if (placed) {
                    if (checkWin()) state = 3;
                    else state = playerTwoMode;
                    nextState();
                    //reset placed bool
                    placed = false;
                }
                break;
            case 2:
                currentPlayer = "O";
                System.out.println("PlayerO – your turn. Choose a column number from 1-7.");
                choice = scan.nextInt();
                setGameBoard(0, choice);
                if (placed) {
                    if (checkWin()) state = 3;
                    else state = 1;
                    nextState();
                    //reset placed bool
                    placed = false;
                }
                break;
            case 3:
                System.out.println("Player " + currentPlayer + " Won the Game!");
                break;
            case 4:
                System.out.println("Stalemate! Board is full.");
                System.out.println("///////////////////////////////////////////////////////////////");
                break;
            case 5:
                currentPlayer = "O";
                System.out.println("Computer's turn...");
                Connect4ComputerPlayer cpu = new Connect4ComputerPlayer();        
                choice = cpu.chooseCol();
                setGameBoard(0, choice);
                if (placed) {
                    if (checkWin()) state = 3;
                    else state = 1;
                    nextState();
                    //reset placed bool
                    placed = false;
                }
                break;
            //GUI mode states
            case 11:
                currentPlayer = "X";
                System.out.println("PlayerX – your turn. Choose a button.");
                break;
            case 12:
                currentPlayer = "O";
                System.out.println("PlayerO – your turn. Choose a button.");
                break;
            case 13:
                System.out.println("Player " + currentPlayer + " Won the Game!");
                break;
            case 15:
                currentPlayer = "O";
                System.out.println("Computer Turn – Please wait.");
                Connect4ComputerPlayer cpuTwo = new Connect4ComputerPlayer();        
                choice = cpuTwo.chooseCol();
                setGameBoard(0, choice);
                if (placed) {
                	if (checkWin()) state = 13;
                    else state = 11;
                    nextState();
                    //reset placed bool
                    placed = false;
                }
                break;
        }
    }
    
    /**
     * prints the current state of the game board to the console
     */
    public void printGameBoard() {
        System.out.println("\n\n\n\n\n\n");
        for (int i = 0; i < gameBoard.length; i++) {
            for (int j = 0; j < gameBoard[i].length; j++) {
                if (gameBoard[i][j] == null)
                    System.out.print("| ");
                else
                    System.out.print("|" + gameBoard[i][j]);
            }
            System.out.println("|");
        }
    }
}