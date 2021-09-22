import java.util.ArrayList;
import java.util.Locale;
import java.util.Random;
import java.util.Scanner;
import java.util.regex.Pattern;

public class RockPaperScissor {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        /*
        //player can choose to play either against computer (name: Cyborg) or against another player.
        //players can decide to calculate final result by either xx number of "individual move" or by xx number of "set"
        //in both case they can decide how many move or set they like to play
        // in each move winner get 1 point, if tied no point
        // when getting results by "individual move", if final result get tied then they will play a penalty move until find a winner
        // when playing by set:
            //each set consists of 3 individual move. Winner of a set will get 1 point, if tied no point
            //if the final result after xx number of set is tie, then they will play a penalty set until a winner is found
         */

        String firstPlayerName = ""; // variable to store name of players
        String secondPlayerName = "";

        String singleOrMultiPlayer = "";//to store decision of either play alone against computer  or multiplayer
        String setOrIndividualMove = ""; //to store decision of either play by set or individual move

        int firstPlayerMoveScore = 0; // to store individual move score for each player.
        int secondPlayerMoveScore = 0;

        int firstPlayerSetScore = 0; // to store set score if players decided to get result by set instead of single move
        int secondPlayerSetScore = 0;

        int numberOfSet = 0; // how many set players like to play
        int numberOfIndividualMove = 0; //if player decided to get result by individual move, will change the value later in program

        String firstPlayerMove = "";// get the individual move by players
        String secondPlayerMove = "";


        //ask if a player like to play against computer or someone else
        //multi = two players playing against each other. single = one player playing against computer.
        System.out.println("Do you like to play single or multiplayer mood");
        System.out.println("write either 'single' or 'multi'");
        singleOrMultiPlayer= isSingleOrMultiPlayer();

        // if single player mood is chosen then ask first player name and set computer name to "Cyborg"
        // if multiplayer mood is chosen then  ask for both players name
        switch (singleOrMultiPlayer){
            case "single":
                firstPlayerName = getPlayerName();
                System.out.println("Welcome " + firstPlayerName + " to RPS game");
                secondPlayerName = "Cyborg";
                System.out.println("I am " + secondPlayerName + ". I will play against you.");
                System.out.println("Are you ready to have some fun?");
                break;
            case "multi":
                firstPlayerName = getPlayerName();
                System.out.println("Welcome " + firstPlayerName + " to RPS game");
                secondPlayerName = getPlayerName();
                System.out.println("Welcome " + secondPlayerName + " to RPS game");
                break;
            default: break;

        }


        // ask if players want to get result based on xx number of set or individual move
        // base on player choice both variable "numberOfSet" and "numberOfIndividualMove" will be updated
        System.out.println("Do you like to get result by set or individual move");
        System.out.println("write 'set' or 'ind'");
        setOrIndividualMove = isSetOrIndividualMove();
        if (setOrIndividualMove.equals("ind")) {
            numberOfSet = 1; // number of set is 1, as player decided to get result by individual move
            System.out.println("Hi "+ firstPlayerName + " and " + secondPlayerName + ", how many individual move you want to play");
            numberOfIndividualMove = scanner.nextInt();  //ask and get how many individual move players want to play
        } else {
            numberOfIndividualMove = 3; // each set consists of 3 individual move by default
            System.out.println("Hi "+ firstPlayerName + " and " + secondPlayerName + ", how many set you want to play");
            numberOfSet = scanner.nextInt();             //ask and get how many set players want to play
        }


        // now that all the setup is done lets start the game
        for (int i = 0; i < numberOfSet; i++) { // number of loop depends on players choice of set
            for (int j = 0; j < numberOfIndividualMove; j++) { // number of move depends on players choice of move

                //get first player's move
                System.out.println(firstPlayerName + ": play your hand: (r = rock, p = paper, s = scissor) ");
                firstPlayerMove = getPlayerMove();

                // get the second player's move (either computer in single mood or person in multiplayer mood)
                switch (singleOrMultiPlayer){
                    case "single":
                        System.out.println(secondPlayerName + ": play your hand: (r = rock, p = paper, s = scissor) ");
                        secondPlayerMove = getComputerMove();
                        System.out.println(secondPlayerMove);
                        break;
                    case "multi":
                        System.out.println(secondPlayerName + ": play your hand: (r = rock, p = paper, s = scissor) ");
                        secondPlayerMove = getPlayerMove();
                        break;
                    default:break;
                }

                //chek who win a single move ( return value 0=tied, 1=Fist player won, 2 = Second player won)
                String individualMoveWinnerIs = getWinnerIndividualMove(firstPlayerName, firstPlayerMove, secondPlayerName, secondPlayerMove);
                 if(individualMoveWinnerIs.equals(firstPlayerName)){
                    System.out.println(firstPlayerName + " won this move");
                    firstPlayerMoveScore +=1;
                } else if (individualMoveWinnerIs.equals(secondPlayerName)){
                    System.out.println(secondPlayerName + " won this move");
                    secondPlayerMoveScore +=1;
                } else {
                    System.out.println("You tied");
                }

                 //calculate the final result when playing based on individual move
                //if player decided to get result by set, this block of code will not execute
                //***** better to make a method, will do it later
                if((setOrIndividualMove.equals("ind")) && (j == (numberOfIndividualMove-1))){
                    if(firstPlayerMoveScore == secondPlayerMoveScore){
                        System.out.println("Final Result: You both tied");
                        System.out.println("****************");
                        System.out.println("As you tied, you have to play a penalty move to find the winner");
                        System.out.println("lets play the penalty move");
                        j = j-1; // this will force the loop to go for one more time
                    } else if(firstPlayerMoveScore > secondPlayerMoveScore){
                        System.out.println("Final Result: Congratulations " + firstPlayerName + "!");
                        System.out.println("You beat " + secondPlayerName+ " by " + firstPlayerMoveScore + " - " + secondPlayerMoveScore );
                    } else if(firstPlayerMoveScore < secondPlayerMoveScore){
                        System.out.println("Final Result: Congratulations " + secondPlayerName + "!");
                        System.out.println("You beat " + firstPlayerName+ " by " + secondPlayerMoveScore + " - " + firstPlayerMoveScore);
                    }
                }

            } // end of "for (int i = 0; i < numberOfIndividualMove; i++)"

            // check who won a set and update set score for both players when playing by sets
            // this segment won't run when playing in "get result by individual move"
            if(setOrIndividualMove.equals("set")){
                if(firstPlayerMoveScore == secondPlayerMoveScore){
                    System.out.println("You tied in this set " + "Current status: " + firstPlayerName + "=" + firstPlayerSetScore + ":" + secondPlayerName + "=" + secondPlayerSetScore);
                } else if(firstPlayerMoveScore > secondPlayerMoveScore){
                    firstPlayerSetScore +=1;
                    System.out.println(firstPlayerName + " won the set. " + "Current status: " + firstPlayerName + "=" + firstPlayerSetScore + ":" + secondPlayerName + "=" + secondPlayerSetScore);
                } else if(firstPlayerMoveScore < secondPlayerMoveScore){
                    secondPlayerSetScore +=1;
                    System.out.println(secondPlayerName + " won the set. " + "Current status: " + firstPlayerName + "=" + firstPlayerSetScore + ":" + secondPlayerName + "=" + secondPlayerSetScore);
                }
                // after calculating set score will need to set first and second player move score to zero to start a new set
                firstPlayerMoveScore = 0;
                secondPlayerMoveScore = 0;
            }


            //check and print the final winner when calculating result by set
            // if player decide to get result by individual move, this block of code will be executed
            //***** better to make a method, will do it later
            if((setOrIndividualMove.equals("set")) && (i == (numberOfSet-1))){
                if(firstPlayerSetScore == secondPlayerSetScore){
                    System.out.println("Final Result: You both tied");
                    System.out.println("****************");
                    System.out.println("As you tied, you have to play a penalty move to find the winner");
                    System.out.println("lets play the penalty move");
                    i = i -1; // this will force the "for (int i = 0; i < numberOfSet; i++)" loop to loop 1 time more to find out the winner
                    System.out.println( firstPlayerName+ " = " + firstPlayerSetScore + " : " + secondPlayerName + " = " + secondPlayerSetScore );
                } else if(firstPlayerSetScore > secondPlayerSetScore){
                    System.out.println("Final Result: Congratulations " + firstPlayerName + "!");
                    System.out.println("You beat " + secondPlayerName+ " by " + firstPlayerSetScore + " - " + secondPlayerSetScore );
                } else if(firstPlayerSetScore < secondPlayerSetScore){
                    System.out.print("Final Result: Congratulations " + secondPlayerName + "! ");
                    System.out.println("You beat " + firstPlayerName+ " by " + secondPlayerSetScore + " - " + firstPlayerSetScore);
                }
            }


        } // end of "for (int i = 0; i < numberOfSet; i++)"


    } // end of main method


    //method to get player move in the system
    //method will also make sure that user type only r/s/p
    public static String getPlayerMove(){
        Scanner scanner = new Scanner(System.in);
        String playerMove = "";
        while (true){
            playerMove = scanner.nextLine();
            if (playerMove.equals("r") || playerMove.equals("s") || playerMove.equals("p")){
                break;
            } else {
                System.out.println("please choose 'r' or 's' or 'p'");
            }
        }
        return playerMove;
    }

    // to get computer move in the system
    public static String getComputerMove(){
        String computerMove = "";
        String[] moveOptions = {"r", "s", "p"};
        computerMove = (moveOptions[new Random().nextInt(moveOptions.length)]);
        return computerMove;
    }

    //this method will chek if user like to play single or multiplayer mood
    //method will also validate input data and make sure that user write either "single" or "multi"
    public static String isSingleOrMultiPlayer(){
        Scanner scanner = new Scanner(System.in);
        String singleOrMultiPlayer;
        while (true){
            singleOrMultiPlayer = (scanner.nextLine()).toLowerCase(Locale.ROOT);
            if((singleOrMultiPlayer.equals("single")) || (singleOrMultiPlayer.equals("multi"))){
                break;
            } else {
                System.out.println("please write either 'single' or 'multi'");
            }
        }
        return singleOrMultiPlayer;
    }

    //method will chek whether player decided to get result by "set" or by "individual move"
    //it will make sure that player writes either "set" or "ind".
    public static String isSetOrIndividualMove(){
        Scanner scanner = new Scanner(System.in);
        String setOrIndividualMove;
        while (true){
            setOrIndividualMove = (scanner.nextLine()).toLowerCase(Locale.ROOT);
            if((setOrIndividualMove.equals("set")) || (setOrIndividualMove.equals("ind"))){
                break;
            } else {
                System.out.println("please write either 'set' or 'ind'");
            }
        }
        return setOrIndividualMove;
    }

    // get the player name and validate that it is a name containing only alphabetic characters
    public static String getPlayerName(){
        Scanner scanner = new Scanner(System.in);
        Pattern pattern = Pattern.compile("[A-Za-z]*");
        String playerName = "";

        System.out.println("Enter your name: ");
        while (true){
            if(scanner.hasNext(pattern)){
                playerName = scanner.next();
                break;
            } else {
                String wrongInput = scanner.next();
                System.out.println(wrongInput + " is not a name. Use only alphabetic character");
            }
        }
        return playerName;
    }

    //set the winning rules in this method and return who won in every set
    // 0 if tied, 1 if player 1 win; 2 if player 2 or computer win
    public static String getWinnerIndividualMove(String firstPlayerName, String firstPlayerMove, String secondPlayerName, String secondPlayerMove){
        String individualMoveWinnerIs = "";
        if(firstPlayerMove.equals(secondPlayerMove)){ // if both player play the same, then that play is tie
            individualMoveWinnerIs ="";
        } else if((firstPlayerMove.equals("r") && secondPlayerMove.equals("s")) || (firstPlayerMove.equals("s") && secondPlayerMove.equals("p")) || (firstPlayerMove.equals("p") && secondPlayerMove.equals("r"))){
            individualMoveWinnerIs = firstPlayerName; // first player win
        } else {
            individualMoveWinnerIs = secondPlayerName; // second player win
        }
        return individualMoveWinnerIs;
    }


}// end of class "RockPaperScissor"
