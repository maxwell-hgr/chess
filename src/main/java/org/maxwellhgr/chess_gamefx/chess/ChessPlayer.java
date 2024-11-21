package org.maxwellhgr.chess_gamefx.chess;

import org.maxwellhgr.chess_gamefx.boardgame.Board;
import org.maxwellhgr.chess_gamefx.chess.pieces.King;

import java.util.ArrayList;
import java.util.List;

public class ChessPlayer {

    private boolean inCheck = false;
    private final Color color;
    private boolean[][] targets;

    private final List<ChessPiece> pieces = new ArrayList<>();
    private final List<ChessPiece> capturedPieces = new ArrayList<>();

    public ChessPlayer(Color color) {
        this.color = color;
    }

    public boolean isInCheck() { return inCheck; }

    public void setInCheck(boolean inCheck) { this.inCheck = inCheck; }

    public Color getColor() {
        return color;
    }

    public boolean[][] getTargets() {
        return targets;
    }

    public void setTargets(boolean[][] targets) {
        this.targets = targets;
    }

    public List<ChessPiece> getPieces() {
        return pieces;
    }

    public List<ChessPiece> getCapturedPieces() {
        return capturedPieces;
    }

    public void updateTargets(Board board) {
        boolean[][] targets = new boolean[8][8];
        for (ChessPiece piece : pieces) {
            boolean[][] moves = piece.possibleMoves(board);
            for (int i = 0; i < moves.length; i++) {
                for (int j = 0; j < moves[0].length; j++) {
                    if (moves[i][j]) {
                        targets[i][j] = true;
                    }
                }
            }
        }
        setTargets(targets);
    }

    public ChessPiece king() {
        return pieces.stream()
                .filter(piece -> piece instanceof King)
                .findFirst()
                .orElse(null);
    }

}
