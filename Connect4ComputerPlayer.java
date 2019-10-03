/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package core;

import java.util.Random;

/**
 *
 * @author Ethan
 */
public class Connect4ComputerPlayer {
    
    public Connect4ComputerPlayer() {    
    }
       
    /**
     *
     * @return returns the computer's selection
     */
    public int chooseCol() {
    	Random rand = new Random();
        return rand.nextInt(7);
    }
}
