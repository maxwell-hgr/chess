package org.maxwellhgr.chess_gamefx.chess;

import org.maxwellhgr.chess_gamefx.boardgame.Board;
import org.maxwellhgr.chess_gamefx.boardgame.Position;
import org.maxwellhgr.chess_gamefx.chess.pieces.*;

import static org.maxwellhgr.chess_gamefx.gui.util.Alerts.showAlert;

public class ChessMatch {

    // match board
    private Board board;

    // players
    private ChessPlayer whitePlayer;
    private ChessPlayer blackPlayer;

    // match state
    private ChessPlayer currentPlayer;

    public ChessMatch() {
        initialState();
    }

    public ChessPlayer getCurrentPlayer() {
        return currentPlayer;
    }

    public Board getBoard() {
        return board;
    }

    public ChessPiece selectPiece(ChessPosition chessPosition) {
        ChessPiece selectedPiece = (ChessPiece) board.piece(chessPosition.toPosition());

        if (selectedPiece != null && selectedPiece.getColor() == currentPlayer.getColor()) {
            return selectedPiece;
        }
        return null;
    }

    public void moveToTarget(ChessPosition position, ChessPiece piece) {
        Position source = piece.getPosition();
        Position target = position.toPosition();

        if (validateMovement(target, piece)) {

            if (board.piece(target) != null) {
                ChessPiece enemyPiece = (ChessPiece) board.piece(target);
                currentPlayer.getCapturedPieces().add(enemyPiece);
                enemyPlayer().getPieces().remove(piece);
            }

            if(piece instanceof Pawn){
                ((Pawn) piece).setFirstMove(false);
            }

            board.movePiece(piece, target);
            currentPlayer.updateTargets(this.board);

            if (currentPlayer.isInCheck()) {
                board.undoMove(piece, source);
                showAlert("Invalid move!", "King still in check!");
            } else {
                changePlayer();
            }
        } else {
            System.out.println("Invalid move");
        }
    }


    private boolean validateMovement(Position target, ChessPiece piece) {
        kingInCheck();
        boolean[][] possibleMoves = piece.possibleMoves(board);
        return possibleMoves[target.getRow()][target.getColumn()];
    }

    public ChessPlayer enemyPlayer() {
        return currentPlayer == whitePlayer ? blackPlayer : whitePlayer;
    }

    public void changePlayer() {
        currentPlayer = (currentPlayer == whitePlayer) ? blackPlayer : whitePlayer;
    }

    private void initialState() {
        this.board = new Board(8, 8);

        this.whitePlayer = new ChessPlayer(Color.WHITE);
        this.blackPlayer= new ChessPlayer(Color.BLACK);

        this.currentPlayer = whitePlayer;

        char[] columns = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h'};
        for (char column : columns) {
            placeNewPiece(column, 2, new Pawn(Color.BLACK));
            placeNewPiece(column, 7, new Pawn(Color.WHITE));
        }
        placeNewPiece('a', 1, new Rook(Color.BLACK));
        placeNewPiece('h', 1, new Rook(Color.BLACK));
        placeNewPiece('a', 8, new Rook(Color.WHITE));
        placeNewPiece('h', 8, new Rook(Color.WHITE));

        placeNewPiece('b', 1, new Knight(Color.BLACK));
        placeNewPiece('g', 1, new Knight(Color.BLACK));
        placeNewPiece('b', 8, new Knight(Color.WHITE));
        placeNewPiece('g', 8, new Knight(Color.WHITE));

        placeNewPiece('c', 1, new Bishop(Color.BLACK));
        placeNewPiece('f', 1, new Bishop(Color.BLACK));
        placeNewPiece('c', 8, new Bishop(Color.WHITE));
        placeNewPiece('f', 8, new Bishop(Color.WHITE));

        placeNewPiece('d', 1, new Queen(Color.BLACK));
        placeNewPiece('e', 1, new King(Color.BLACK));
        placeNewPiece('d', 8, new Queen(Color.WHITE));
        placeNewPiece('e', 8, new King(Color.WHITE));

        whitePlayer.updateTargets(this.board);
        blackPlayer.updateTargets(this.board);
    }

    private void placeNewPiece(char column, int row, ChessPiece piece) {
        Position position = new ChessPosition(column, row).toPosition();
        board.placePiece(position, piece);
        piece.setPosition(position);

        if (piece.getColor() == Color.WHITE) {
            whitePlayer.getPieces().add(piece);
        } else {
            blackPlayer.getPieces().add(piece);
        }
    }

    public void kingInCheck() {
        boolean[][] enemyMoves = enemyPlayer().getTargets();

        for (int row = 0; row < 8; row++) {
            for (int column = 0; column < 8; column++) {
                if (enemyMoves[row][column]) {
                    Position pos = new Position(row, column);
                    if (currentPlayer.king().getPosition().equals(pos)) {
                        currentPlayer.setInCheck(true);
                        isCheckmate();
                    }
                }
            }
        }
    }

    private void isCheckmate() {
        ChessPiece king = currentPlayer.king();

        // verify if king can escape by own move
        boolean[][] kingMoves = king.possibleMoves(board);
        for (int row = 0; row < board.getRows(); row++) {
            for (int column = 0; column < board.getColumns(); column++) {
                if (kingMoves[row][column]) {
                    Position target = new Position(row, column);

                    // simulates king move
                    Position source = king.getPosition();
                    board.movePiece(king, target);

                    // update enemy move
                    enemyPlayer().updateTargets(board);

                    // verify if still in check
                    boolean stillInCheck = currentPlayer.isInCheck();

                    // undo move
                    board.undoMove(king, source);

                    // if king can escape
                    if (!stillInCheck) {
                        return;
                    }
                }
            }
        }

        for (ChessPiece piece : currentPlayer.getPieces()) {
            boolean[][] possibleMoves = piece.possibleMoves(board);
            for (int row = 0; row < board.getRows(); row++) {
                for (int column = 0; column < board.getColumns(); column++) {
                    if (possibleMoves[row][column]) {
                        Position source = piece.getPosition();
                        Position target = new Position(row, column);

                        // simulate piece move
                        board.movePiece(piece, target);

                        // update enemy targets
                        enemyPlayer().updateTargets(board);

                        // if still in check after move
                        boolean stillInCheck = currentPlayer.isInCheck();

                        // undo move
                        board.undoMove(piece, source);

                        // if some piece can save the king
                        if (!stillInCheck) {
                            return;
                        }
                    }
                }
            }
        }

        showAlert("Checkmate!", "Game will restart!");
        reset();
    }

    public void reset() {
        initialState();
    }
}
