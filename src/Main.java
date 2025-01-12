import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // This is a new java TicTacToe game module.
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the name of player1: ");
        String player1 = scanner.nextLine();
        System.out.print("Enter the name of player2: ");
        String player2 = scanner.nextLine();

        TicTacToe ticTacToe = new TicTacToe(player1, player2);
        String user = "b";
        while(!user.equals("n")){
            System.out.print("DO you want to play again?(y/n): ");
            user = scanner.next().toLowerCase();
            if (user.equals("y")){
                TicTacToe ticTacToe1 = new TicTacToe(player1, player2);
            }

        }
        System.out.println("Thanks for playing my game.");
    }
}