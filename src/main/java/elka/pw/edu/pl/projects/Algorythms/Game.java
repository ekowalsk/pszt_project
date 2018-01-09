package elka.pw.edu.pl.projects.Algorythms;

import elka.pw.edu.pl.projects.Board;
import elka.pw.edu.pl.projects.Enums.FieldType;
import elka.pw.edu.pl.projects.Position;

import java.util.LinkedList;
import java.util.List;

/**
 * stores current board and current player symbol, it also has a list of possible moves
 * of current player on current board
 */
public class Game {

    private Board board;
    private FieldType playerSymbol;
    private FieldType opponentSymbol;
    private List<Board> possibleMoves;

    public Game(Board otherBoard, FieldType symbol) {
        board = new Board();
        board.setBoard(otherBoard.getBoard());
        this.playerSymbol = symbol;
        if (symbol == FieldType.CIRCLE)
            this.opponentSymbol = FieldType.CROSS;
        else
            this.opponentSymbol = FieldType.CIRCLE;
        possibleMoves = new LinkedList<Board>();
    }

    /**
     * returns points in order to estimate how gainful is for the current Player the state of the Board after his move
     */
    public int rateBoard() {
        int rating = 0;
        int playerRowCount = 0;
        int opponentRowCount = 0;
        int playerColumnCount = 0;
        int opponentColumnCount = 0;
        int playerCross1Count = 0;
        int opponentCross1Count = 0;
        int playerCross2Count = 0;
        int opponentCross2Count = 0;

        for (int x = 0; x < 3; x++) {
            for (int y = 0; y < 3; y++) {
                if (board.getField(x, y) == playerSymbol)
                    playerColumnCount++;
                else if (board.getField(x, y) == opponentSymbol)
                    opponentColumnCount++;
                if (board.getField(y, x) == playerSymbol)
                    playerRowCount++;
                else if (board.getField(y, x) == opponentSymbol)
                    opponentRowCount++;
                if ((x == 0 || x == 2) && (y == 0 || y == 2)) {
                    if (board.getField(x, y) == playerSymbol)
                        rating += 2;
                    else rating -= 2;
                }
                if (x == 1 && y == 1) {
                    if (board.getField(x, y) == playerSymbol)
                        rating += 6;
                    else rating -= 6;
                }
            }
            if (playerColumnCount == 3)
                rating += 100;
            if (playerColumnCount == 2 && opponentColumnCount == 0)
                rating += 60;
            if (playerColumnCount == 0 && opponentColumnCount == 2)
                rating -= 100;
            if (playerRowCount == 3)
                rating += 100;
            if (playerRowCount == 2 && opponentRowCount == 0)
                rating += 60;
            if (playerRowCount == 0 && opponentRowCount == 2)
                rating -= 100;

            playerColumnCount = 0;
            opponentColumnCount = 0;
            playerRowCount = 0;
            opponentRowCount = 0;
        }
        for (int x = 0, y = 2; x < 3; x++, y--) {
            if (board.getField(x, x) == playerSymbol) {
                playerCross1Count++;
            } else if (board.getField(x, x) == opponentSymbol) {
                opponentCross1Count++;
            }
            if (board.getField(x, y) == playerSymbol) {
                playerCross2Count++;
            } else if (board.getField(x, y) == opponentSymbol) {
                opponentCross2Count++;
            }
        }
        if (playerCross1Count == 3)
            rating += 100;
        if (playerCross1Count == 2 && opponentCross1Count == 0)
            rating += 60;
        if (playerCross1Count == 0 && opponentCross1Count == 2)
            rating -= 100;
        if (playerCross2Count == 3)
            rating += 100;
        if (playerCross2Count == 2 && opponentCross2Count == 0)
            rating += 60;
        if (playerCross2Count == 0 && opponentCross2Count == 2)
            rating -= 100;

        return rating;
    }

    /**
     * finds all possible moves for the current Player and the board
     */
    public void findAllMoves() {
        for (int y = 0; y < 3; y++) {
            for (int x = 0; x < 3; x++) {
                if (board.getField(x, y) == FieldType.EMPTY) {
                    Board newBoard = new Board();
                    newBoard.setBoard(board.getBoard());
                    newBoard.setField(x, y, playerSymbol);
                    possibleMoves.add(newBoard);
                }
            }
        }
    }

    public void printMoves() {
        for (Board board : possibleMoves)
            board.print();
    }

    public void setBoard(Board other) {
        board.setBoard(other.getBoard());
    }

    public Board getBoard() {
        return board;
    }

    public void setPlayerSymbol(FieldType symbol) {
        this.playerSymbol = symbol;
    }

    public void setOpponentSymbol(FieldType symbol) {
        this.opponentSymbol = symbol;
    }

    public FieldType getPlayerSymbol() {
        return playerSymbol;
    }

    public FieldType getOpponentSymbol() {
        return opponentSymbol;
    }

    public List<Board> getPossibleMoves() {
        return possibleMoves;
    }
}