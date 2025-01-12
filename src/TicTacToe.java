import java.util.Scanner;
import java.util.ArrayList;

public class TicTacToe {
    String player1;
    String player2;
    ArrayList<String> board = new ArrayList<>();
    Scanner scanner = new Scanner(System.in);

    TicTacToe(String player1, String player2) {
        this.player1 = player1;
        this.player2 = player2;
        String user = " ";
        this.board = this.createBoard();
        System.out.print("Which mode you want to play? (ai/2player): ");
        String mode = scanner.next().toLowerCase();
        if (mode.equals("ai")) {
            this.player2 = "AI";
            this.playAI();
        } else {
            this.playTwoPlayer();
        }
    }

    ArrayList<String> createBoard() {
        ArrayList<String> reset_board = new ArrayList<>(9);
        for (int i = 0; i < 9; i++) {
            reset_board.add(" ");
        }
        return reset_board;
    }

    private boolean isTied(ArrayList<String> board) {
        for (String pos : board) {
            if (pos.equals(" ")) {
                return false;
            }
        }
        return true;
    }

    private void showBoard() {
        System.out.println("\n");
        for (int i = 0; i < 9; i += 3) {
            System.out.println(this.board.get(i) + "  |  " + this.board.get(i + 1) + "  |  " + this.board.get(i + 2));
            if(i < 6){
                System.out.println("______________");
            }
        }
        System.out.println();
    }

    private String checkWin(ArrayList<String> board) {
        int[][] winning_combo = {{0, 1, 2}, {3, 4, 5}, {6, 7, 8}, {0, 3, 6}, {1, 4, 7}, {2, 5, 8}, {0, 4, 8}, {2, 4, 6}};
        for (int[] combo : winning_combo) {
            if (board.get(combo[0]).equals(board.get(combo[1])) && board.get(combo[0]).equals(board.get(combo[2])) && !board.get(combo[0]).equals(" ")) {
                return board.get(combo[0]).equals("X") ? this.player1 : this.player2;
            } else if (this.isTied(board)) {
                return "Tie";
            }
        }
        return "None";
    }

    private void updateBoard(int move, String curr_player) {
        String sign = curr_player.equals(this.player1) ? "X" : "O";
        this.board.set(move, sign);
    }

    private int minimax(ArrayList<String> minimax_board, boolean is_maximzing, int depth) {
        if (this.checkWin(minimax_board).equals(this.player2)) {
            return 10 - depth;
        } else if (this.checkWin(minimax_board).equals(this.player1)) {
            return depth - 10;
        } else if (this.isTied(minimax_board)) {
            return 0;
        }
        if (is_maximzing) {
            int best_score = -1000;
            for (int i = 0; i < 9; i++) {
                if (minimax_board.get(i).equals(" ")) {
                    minimax_board.set(i, "O");
                    int score = this.minimax(minimax_board, false, depth + 1);
                    minimax_board.set(i, " ");
                    best_score = Integer.max(best_score, score);
                }
            }
            return best_score;
        } else {
            int best_score = 1000;
            for (int i = 0; i < 9; i++) {
                if (minimax_board.get(i).equals(" ")) {
                    minimax_board.set(i, "X");
                    int score = this.minimax(minimax_board, true, depth + 1);
                    minimax_board.set(i, " ");
                    best_score = Integer.min(best_score, score);
                }
            }
            return best_score;
        }
    }

    private int findBestMove(ArrayList<String> board) {
        int best_move = -1;
        int best_score = -1000;
        for (int i = 0; i < 9; i++) {
            if (board.get(i).equals(" ")) {
                board.set(i, "O");
                int score = this.minimax(board, false, 0);
                board.set(i, " ");
                if (score > best_score) {
                    best_score = score;
                    best_move = i;
                }
            }
        }
        return best_move;
    }

    public void playTwoPlayer() {
        boolean is_player1_turn = true;
        int move;
        String curr_player = this.player1;
        this.createBoard();

        while (true) {
            this.showBoard();
            System.out.println("Current Turn: " + curr_player);
            System.out.print("Enter your move(0-8): ");
            try {
                move = scanner.nextInt();
                if (this.board.get(move).equals(" ")) {
                    this.updateBoard(move, curr_player);
                    is_player1_turn = !is_player1_turn;
                    curr_player = is_player1_turn ? this.player1 : this.player2;
                }
                if (this.checkWin(this.board).equals(this.player1)) {
                    this.showBoard();
                    System.out.println(this.player1 + " WINS!");
                    break;
                } else if (this.checkWin(this.board).equals(this.player2)) {
                    this.showBoard();
                    System.out.println(this.player2 + " WINS");
                    break;
                } else if (this.checkWin(this.board).equals("Tie")) {
                    this.showBoard();
                    System.out.println("The Game is Tied!");
                    break;
                }

            } catch (Exception e) {
                System.out.println(e);
            }
        }

    }

    private void playAI() {
        boolean is_player1_turn = true;
        int move;
        String curr_player = this.player1;
        this.createBoard();

        while (true) {
            this.showBoard();
            System.out.println("Current Turn: " + curr_player);
            try {
                if(!is_player1_turn){
                    move = this.findBestMove(this.board);
                    if (this.board.get(move).equals(" ")){
                        this.updateBoard(move, this.player2);
                        is_player1_turn = !is_player1_turn;
                        curr_player = is_player1_turn ? this.player1 : this.player2;
                    }
                }
                else if(is_player1_turn) {
                    System.out.print("Enter your move(0-8): ");
                    move = scanner.nextInt();
                    if(this.board.get(move).equals(" ")){
                    this.updateBoard(move, curr_player);
                    is_player1_turn = !is_player1_turn;
                    curr_player = is_player1_turn ? this.player1 : this.player2;
                    }
                }
                if (this.checkWin(this.board).equals(this.player1)) {
                    this.showBoard();
                    System.out.println(this.player1 + " WINS!");
                    break;
                } else if (this.checkWin(this.board).equals(this.player2)) {
                    this.showBoard();
                    System.out.println(this.player2 + " WINS");
                    break;
                } else if (this.checkWin(this.board).equals("Tie")) {
                    this.showBoard();
                    System.out.println("The Game is Tied!");
                    break;
                }

            } catch (Exception e) {
                System.out.println(e);
            }
        }

    }
}
