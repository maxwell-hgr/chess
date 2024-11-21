package org.maxwellhgr.chess_gamefx.chess.pieces;


import org.maxwellhgr.chess_gamefx.boardgame.Board;
import org.maxwellhgr.chess_gamefx.boardgame.Position;
import org.maxwellhgr.chess_gamefx.chess.ChessPiece;
import org.maxwellhgr.chess_gamefx.chess.Color;

import java.util.List;

public class Bishop extends ChessPiece {

    public Bishop(Color color) {
        super(color);
    }

    @Override
    public boolean[][] possibleMoves(Board board) {
        boolean[][] possibleMoves = new boolean[8][8];

        List<Position> positions = bishopPositions(this.getPosition(), board);

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

    public static List<Position> bishopPositions(Position position, Board board) {
        int[][] directions = {
                {-1, -1},
                {-1, 1},
                {1, -1},
                {1, 1}
        };
        return ChessPiece.rookAndBishopPositions(directions, board, position);
    };

    @Override
    public String toString() {
        return "B";
    }
}
