import java.util.Scanner;

public class Application {
    private boolean[] bombPlacementBoard = new boolean[25];
    private String[] board = new String[25];
    private int numberOfBombs = 5;

    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        new Application().run();
    }

    private void run() {
        placeBombOnBoard();

        setBoard();
        while (true) {
            displayBoard();
            System.out.println("Podaj liczbe");
            int playerMove = scanner.nextInt();
            if(bombPlacementBoard[playerMove - 1]) {
                System.out.println("przegrales, trafiles na bombe");
                break;
            }
            else {
                System.out.println("uff, to nie byla bomba");
                board[playerMove - 1] = "";
            }
        }
    }

    private void displayBoard() {
        for (int i = 0; i < board.length; i++) {
            System.out.print("[" + board[i] + "]");
            if((i + 1) % 5 == 0) {
                System.out.println();
            }
        }
    }

    private void setBoard() {
        for (int i = 0; i < board.length; i++) {
            board[i] = Integer.toString(i + 1);
        }
    }

    private void placeBombOnBoard() {
        int randomNumber;

        System.out.println("bomby: ");
        for (int i = 0; i < numberOfBombs; i++) {
            randomNumber = randomNumber();
            bombPlacementBoard[randomNumber - 1] = true;
            System.out.print(randomNumber + ", ");
        }
        System.out.println();
    }

    private int randomNumber() {
        return (int) Math.floor(Math.random() * bombPlacementBoard.length);
    }
}
