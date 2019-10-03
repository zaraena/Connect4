/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ui;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.scene.text.Text;
import javafx.geometry.Insets;
import javafx.scene.layout.GridPane;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import java.io.IOException;
import javafx.scene.Parent;
import javafx.stage.Modality;
import javafx.scene.control.TableView;

import core.Connect4;

/**
 *
 * @author Ethan
 */
public class Connect4GUI extends Application {

    /**
     * decides whether we're playing against a live player or the computer
     */
    public int versusMode = 0;
    
    /**
     *
     * @param primaryStage begins our gui program
     */
    @Override
    public void start(final Stage primaryStage) {
        primaryStage.setTitle("Connect 4");
        
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Scene scene = new Scene(grid, 300, 275);
        primaryStage.setScene(scene);
        
        //Title
        Text sceneTitle = new Text("Welcome to Connect 4!");
        grid.add(sceneTitle, 0, 0, 2, 1);
        
        //Play Against Player
        Label lbl_CommandLineMode = new Label("Play via command-line");
        grid.add(lbl_CommandLineMode, 0, 1);
        
        Button btn_CommandLineMode = new Button("Begin!");
        grid.add(btn_CommandLineMode, 1, 1);
        
        btn_CommandLineMode.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                
                //lock the GUI and tell user that we've started command-line mode
                Label secondLabel = new Label();
 
                StackPane secondaryLayout = new StackPane();
                secondaryLayout.getChildren().add(secondLabel);
 
                Scene secondScene = new Scene(secondaryLayout, 500, 1);
 
                // New window (Stage)
                Stage newWindow = new Stage();
                newWindow.setTitle("Connect 4: Playing via Command-Line!");
                newWindow.setScene(secondScene);
 
                // Set position of second window, related to primary window.
                newWindow.setX(primaryStage.getX() - 100);
                newWindow.setY(primaryStage.getY() + 75);
 
                newWindow.show();
                
                Connect4 game = new Connect4();
                game.state = 0;
                game.nextState();
            }
        });
        
        //Play Against CPU
        Label lbl_GUIMode = new Label("Play via GUI interface");
        grid.add(lbl_GUIMode, 0, 2);
        
        Button btn_GUIMode = new Button("Begin!");
        grid.add(btn_GUIMode, 1, 2);
        
        btn_GUIMode.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                //Begin Connect 4 GUI Mode
                GridPane grid = new GridPane();
                grid.setAlignment(Pos.CENTER);
                grid.setHgap(10);
                grid.setVgap(10);
                grid.setPadding(new Insets(25, 25, 25, 25));
        
                //welcome text
                Text txt_Welcome = new Text("Welcome to Connect 4 GUI Mode!");
                grid.add(txt_Welcome, 0, 0);
                
                //as player which mode they want to play in
                Label lbl_playerTurn = new Label("Would you like to play against a live player or the computer?");
                grid.add(lbl_playerTurn, 0, 1);
                
                Scene secondScene = new Scene(grid, 640, 480);
                
                //allows player to choose versus player mode
                Button btn_VersusPlayer = new Button("Play Against a Live Player");
                grid.add(btn_VersusPlayer, 0, 2);
                
                btn_VersusPlayer.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent e) {
                        versusMode = 1;
                        
                        //start game in the chosen mode
                        Connect4 game = new Connect4();
                        game.state = 10;
                        game.nextState();
                        
                        gameWindow(game, versusMode);
                    }
                });
                
                //allows player to choose versus computer mode
                Button btn_VersusComputer = new Button ("Play Against the Computer");
                grid.add(btn_VersusComputer, 0, 3);
                
                btn_VersusComputer.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent e) {
                        //begin game in versus computer mode
                        versusMode = 2;
                        
                        //start game in the chosen mode
                        Connect4 game = new Connect4();
                        game.state = 10;
                        game.nextState();
                        
                        gameWindow(game, versusMode);
                    }
                });
 
                // New window (Stage)
                Stage newWindow = new Stage();
                newWindow.setTitle("Connect 4: GUI Mode");
                newWindow.setScene(secondScene);
 
                newWindow.show();
                
                Connect4 game = new Connect4();
                game.state = 11;
                game.nextState();
            }
        });
        
        //Draw
        primaryStage.show();
    }
    
    /**
     *
     * @param game the current game object
     * @param mode decides whether we're playing against a live player or the computer
     */
    public void gameWindow(final Connect4 game, int mode) {
        //game board spaces
        String asterisk = "*";
        String X = "X";
        String O = "O";
        
        //begin game in versus player mode
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        //button control
        Button btn_chooseOne = new Button("1");
        grid.add(btn_chooseOne, 0, 7);
        btn_chooseOne.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                game.setGameBoard(0, 1);
                nextTurn(game, versusMode);
            }
        });
                
        Button btn_chooseTwo = new Button("2");
        grid.add(btn_chooseTwo, 1, 7);
        btn_chooseTwo.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                game.setGameBoard(0, 2);
                nextTurn(game, versusMode);
            }
        });
        
        Button btn_chooseThree = new Button("3");
        grid.add(btn_chooseThree, 2, 7);
        btn_chooseThree.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                game.setGameBoard(0, 3);
                nextTurn(game, versusMode);
            }
        });
        
        Button btn_chooseFour = new Button("4");
        grid.add(btn_chooseFour, 3, 7);
        btn_chooseFour.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                game.setGameBoard(0, 4);
                nextTurn(game, versusMode);
            }
        });
        
        Button btn_chooseFive = new Button("5");
        grid.add(btn_chooseFive, 4, 7);
        btn_chooseFive.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                game.setGameBoard(0, 5);
                nextTurn(game, versusMode);
            }
        });
        
        Button btn_chooseSix = new Button("6");
        grid.add(btn_chooseSix, 5, 7);
        btn_chooseSix.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                game.setGameBoard(0, 6);
                nextTurn(game, versusMode);
            }
        });
        
        Button btn_chooseSeven = new Button("7");
        grid.add(btn_chooseSeven, 6, 7);
        btn_chooseSeven.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                game.setGameBoard(0, 7);
                nextTurn(game, versusMode);
            }
        });
        
        //game board
        
        
        Scene thirdScene = new Scene(grid, 1024, 768);
        
        // New window (Stage)
        Stage newWindow = new Stage();
        newWindow.setTitle("Connect 4: GUI Mode");
        newWindow.setScene(thirdScene);

        newWindow.show();
    }
    
    /**
     *
     * @param game the current Connect4 game instance
     * @param mode tells us if we're playing against a live player or the computer
     */
    public void nextTurn(Connect4 game, int mode) {
        if (mode == 1) {
            //go to next player turn
            if (game.state == 11) {
                game.state = 12;
            } else {
                game.state = 11;
            }
        } else {
            //go to computer turn
            game.state = 15;
        }
        game.nextState();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}