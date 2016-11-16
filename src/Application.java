import java.util.Scanner;

public class Application {
    private boolean[] bombPlacementBoard = new boolean[25];
    private int[] board = new int[25];
    private int numberOfBombs = 5;
    private boolean isGameLost = false;

    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        new Application().run();
    }

    private void run() {
        placeBombOnBoard();

        setBoard();
        while (!isGameLost) {
            displayBoard();
            System.out.println("Podaj liczbe");
            int playerMove = scanner.nextInt();
            if(bombPlacementBoard[playerMove]) {
                System.out.println("przegrales, trafiles na bombe");
                break;
            }
            else {
                System.out.println("uff, to nie byla bomba");
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
            board[i] = i + 1;
        }
    }

    private void placeBombOnBoard() {
        int randomNumber;

        System.out.println("bomby: ");
        for (int i = 0; i < numberOfBombs; i++) {
            randomNumber = randomNumber();
            bombPlacementBoard[randomNumber] = true;
            System.out.print(randomNumber + ", ");
        }
        System.out.println();
    }

    private int randomNumber() {
        return (int) Math.floor(Math.random() * bombPlacementBoard.length);
    }
}
