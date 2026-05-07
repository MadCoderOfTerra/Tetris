import java.awt.*;
import javax.swing.*;
import javax.swing.Timer;
import javax.swing.plaf.ColorUIResource;
import java.nio.file.FileAlreadyExistsException;
import java.util.*;

//----------------------------------------------------------------------------------------------------------------------

public class Main {
    public static void main(String[] args){
        Window w = new Window();
    }
}




// ----------------------------------------------------------------------------------------------






/*
don't address this just some notes

Render the box 10x20
Render a square
Make that square fall each seconds
Stop it when touching the bottom
allow for real time input
allow real time movement
allow rotation
Create other shapes and testing its collision with the bottom wall
Limit them inside left and right wall
Allow for speeding to the bottom
(For now no collision between shapes yet) allow for random generated shapes and move them around with diff colors
Now check for collisions and stop them
Now check for row clearing and moving the squares down several blocks
Scoring system
Menu
LeaderBoard


-------------------------

Render box 10x20
Render & fall one square
Stop at bottom
Real time input + movement + wall limits
Rotation
Speed up (hard drop / soft drop)
Other shapes + colors
Bottom & wall collision for all shapes
Spawn new piece when one lands
Shape-to-shape collision
Row clearing
Game over
Scoring
Menu + Leaderboard
Ghost piece (bonus)
 */