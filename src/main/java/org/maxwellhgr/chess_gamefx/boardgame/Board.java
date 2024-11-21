package org.maxwellhgr.chess_gamefx.boardgame;


import org.maxwellhgr.chess_gamefx.chess.ChessPiece;

public class Board {

    private int rows;
    private int columns;
    private Piece[][] pieces;

    public Board(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;
        this.pieces = new Piece[rows][columns];
    }

    public Piece piece(Position position) {
        return pieces[position.getRow()][position.getColumn()];
    }

    public void clearPosition(Position position) {
        pieces[position.getRow()][position.getColumn()] = null;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public int getColumns() {
        return columns;
    }

    public void setColumns(int columns) {
        this.columns = columns;
    }

    public Piece[][] getPieces() {
        return pieces;
    }

    public void setPieces(Piece[][] pieces) {
        this.pieces = pieces;
    }

    public void placePiece(Position position, Piece piece) {
        pieces[position.getRow()][position.getColumn()] = piece;
    }

    public boolean isValidPosition(Position position) {
        int row = position.getRow();
        int column = position.getColumn();
        return row >= 0 && row < rows && column >= 0 && column < columns;
    }

    public void movePiece(ChessPiece piece, Position target) {
        this.clearPosition(piece.getPosition());
        this.placePiece(target, piece);
        piece.setPosition(target);
    }

    public void undoMove(ChessPiece piece, Position source){
        this.clearPosition(piece.getPosition());
        this.placePiece(source, piece);
        piece.setPosition(source);
    }
}
