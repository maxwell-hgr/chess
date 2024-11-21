package org.maxwellhgr.chess_gamefx.chess.pieces;

import org.maxwellhgr.chess_gamefx.boardgame.Board;
import org.maxwellhgr.chess_gamefx.boardgame.Position;
import org.maxwellhgr.chess_gamefx.chess.ChessPiece;
import org.maxwellhgr.chess_gamefx.chess.Color;

import java.util.List;

public class Rook extends ChessPiece {

    public Rook(Color color) {
        super(color);
    }

    @Override
    public boolean[][] possibleMoves(Board board) {
        boolean[][] possibleMoves = new boolean[8][8];

        List<Position> positions = rookPositions(this.getPosition(), board);

        for (Position p : positions) {
            if (board.piece(p) != null) {
                ChessPiece chessPiece = (ChessPiece) board.piece(p);
                if (chessPiece.getColor() != this.getColor()) {
                    possibleMoves[p.getRow()][p.getColumn()] = true;
                }
                continue;
            }
            possibleMoves[p.getRow()][p.getColumn()] = true;
        }
        return possibleMoves;
    }

    public static List<Position> rookPositions(Position position, Board board) {
        int[][] directions = {
                {-1, 0},
                {0, 1},
                {+1, 0},
                {0, -1}
        };

        return ChessPiece.rookAndBishopPositions(directions, board, position);
    };

    @Override
    public String toString() {
        return "R";
    }
}
