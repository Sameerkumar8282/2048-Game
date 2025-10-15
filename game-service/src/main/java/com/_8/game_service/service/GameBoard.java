package com._8.game_service.service;

import java.util.*;

public class GameBoard {
    private int size;
    private int[][] board;
    private int score;
    private Random random = new Random();

    public GameBoard(int size) {
        this.size = size;
        board = new int[size][size];
        addRandomTile();
        addRandomTile();
    }

    public int[][] getBoard() { return board; }
    public int getScore() { return score; }

    public void addRandomTile() {
        List<int[]> empty = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (board[i][j] == 0) empty.add(new int[]{i, j});
            }
        }
        if (!empty.isEmpty()) {
            int[] pos = empty.get(random.nextInt(empty.size()));
            board[pos[0]][pos[1]] = random.nextInt(10) < 9 ? 2 : 4;
        }
    }

    public boolean move(String direction) {
        boolean moved = false;
        switch (direction.toUpperCase()) {
            case "LEFT": moved = moveLeft(); break;
            case "RIGHT": moved = moveRight(); break;
            case "UP": moved = moveUp(); break;
            case "DOWN": moved = moveDown(); break;
        }
        if (moved) addRandomTile();
        return moved;
    }

    private boolean moveLeft() {
        boolean moved = false;
        for (int i = 0; i < size; i++) {
            int[] newRow = compressAndMergeRow(board[i]);
            if (!Arrays.equals(board[i], newRow)) {
                board[i] = newRow;
                moved = true;
            }
        }
        return moved;
    }

    private boolean moveRight() {
        boolean moved = false;
        for (int i = 0; i < size; i++) {
            int[] reversed = reverse(board[i]);
            int[] newRow = compressAndMergeRow(reversed);
            newRow = reverse(newRow);
            if (!Arrays.equals(board[i], newRow)) {
                board[i] = newRow;
                moved = true;
            }
        }
        return moved;
    }

    private boolean moveUp() {
        boolean moved = false;
        for (int j = 0; j < size; j++) {
            int[] col = getColumn(j);
            int[] newCol = compressAndMergeRow(col);
            if (!Arrays.equals(col, newCol)) {
                setColumn(j, newCol);
                moved = true;
            }
        }
        return moved;
    }

    private boolean moveDown() {
        boolean moved = false;
        for (int j = 0; j < size; j++) {
            int[] col = reverse(getColumn(j));
            int[] newCol = compressAndMergeRow(col);
            newCol = reverse(newCol);
            if (!Arrays.equals(getColumn(j), newCol)) {
                setColumn(j, newCol);
                moved = true;
            }
        }
        return moved;
    }

    private int[] compressAndMergeRow(int[] row) {
        List<Integer> list = new ArrayList<>();

        for (int val : row) {
            if (val != 0) list.add(val);
        }

        for (int i = 0; i < list.size() - 1; i++) {
            if (list.get(i).equals(list.get(i + 1))) {
                list.set(i, list.get(i) * 2);
                score += list.get(i);
                list.set(i + 1, 0);
            }
        }

        List<Integer> newList = new ArrayList<>();
        for (int val : list) {
            if (val != 0) newList.add(val);
        }

        while (newList.size() < size) newList.add(0);

        return newList.stream().mapToInt(Integer::intValue).toArray();
    }

    private int[] getColumn(int colIndex) {
        int[] col = new int[size];
        for (int i = 0; i < size; i++) col[i] = board[i][colIndex];
        return col;
    }

    private void setColumn(int colIndex, int[] col) {
        for (int i = 0; i < size; i++) board[i][colIndex] = col[i];
    }

    private int[] reverse(int[] arr) {
        int[] rev = new int[arr.length];
        for (int i = 0; i < arr.length; i++) rev[i] = arr[arr.length - 1 - i];
        return rev;
    }

    public boolean isGameOver() {
        for (int[] row : board)
            for (int val : row)
                if (val == 0) return false;

        for (int i = 0; i < size; i++)
            for (int j = 0; j < size - 1; j++)
                if (board[i][j] == board[i][j + 1] ||
                        board[j][i] == board[j + 1][i])
                    return false;

        return true;
    }
    
    public boolean hasWon() {
        for (int[] row : board) {
            for (int val : row) {
                if (val == 2048) return true;
            }
        }
        return false;
    }

}

