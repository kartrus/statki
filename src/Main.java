import model.Board;
import java.util.Scanner;

public class Main {

    static Scanner scan = new Scanner(System.in);

    public static void main(String[] args) {
        Board board = new Board();

        prepareGame(board);
        playGame(board);
    }

    private static void prepareGame(Board board) {
        System.out.println("Select dimension of the board:");
        board.setLength(setTheSizeOfTheBoard("Length:"));
        board.setWidth(setTheSizeOfTheBoard("Width:"));
        board.initBoard();
        board.addRandomShips(5,4);
        board.randomDistributeShipsOnBoard();
    }

    private static void playGame(Board board) {
        String stringValue;

        while(true){
            System.out.println("To shot[s] to end[e]:");
            stringValue = scan.nextLine();
            if(stringValue.equals("e")){
                return;
            }
            if(stringValue.equals("s")){
                board.shoot();
                board.showStats();
                board.showBoard();
            }
            if(board.isSunkAll()){
                System.out.println("You win!");
                return;
            }
        }
    }

    public static int setTheSizeOfTheBoard(String boardDimension) {
        String stringValue;
        int value;

        while(true) {
            System.out.println(boardDimension);
            stringValue = scan.nextLine();

            try {
                value = Integer.parseInt(stringValue);
                if(value <= 0)
                {
                    System.out.println("Dimension cannot be less than 1");
                }
                else {
                    return value;
                }
            } catch (NumberFormatException e) {
                System.out.println("Wrong type of dimension. Please, enter correct number.");
            }
        }
    }
}
