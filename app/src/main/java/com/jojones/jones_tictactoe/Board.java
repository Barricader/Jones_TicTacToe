package com.jojones.jones_tictactoe;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

class Board {
    enum State { SQUARE_EMPTY, SQUARE_X, SQUARE_O }
    enum AILevel { OFF, EASY, MEDIUM, HARD };

    static final int NUM_SQUARES = 9;

    State[] squares;
    byte winner;
    AILevel aiLevel;
    State playerPiece;

    MainActivity ma;

    Board(MainActivity ma) {
        squares = new State[NUM_SQUARES];
        winner = 0;
        aiLevel = AILevel.HARD; // TODO: FIX
        playerPiece = State.SQUARE_X;
        // TODO: change above when you can choose piece

        this.ma = ma;

        for (int i = 0; i < NUM_SQUARES; i++) {
            squares[i] = State.SQUARE_EMPTY;
        }
    }

    void updateSquare(int sq, State s) {
        if (sq < NUM_SQUARES) {
            squares[sq] = s;

            ma.updateImg(sq, s);

            // Check win conditions
            winner = checkWin();
            if (winner == 0) {
                if (aiLevel != AILevel.OFF) {
                    moveAI();
                }
            }
        }
    }

    void updateAI(int sq, State s) {
        squares[sq] = s;

        ma.updateImg(sq, s);

        // Check win conditions
        winner = checkWin();
    }

    private void moveAI() {
        boolean isValid = false;
        if (aiLevel == AILevel.EASY) {
            while (!isValid) {
                State[] s = new State[NUM_SQUARES];
                System.arraycopy(squares, 0, s, 0, s.length);
                HashMap<Integer, Integer> sc2 = new HashMap<>();

                for (int i = 0; i < s.length; i++) {
                    if (s[i] == State.SQUARE_EMPTY) {
                        int score = -genScore(s, i);

                        sc2.put(i, score);
                    }
                }

                HashMap<Integer, Integer> sorted = sortMap(sc2);

                Iterator<Map.Entry<Integer, Integer>> x = sorted.entrySet().iterator();
                int test = sorted.entrySet().iterator().next().getValue();
                int count = 0;

                while (x.hasNext()) {
                    if (x.next().getValue() == test) {
                        count++;
                    }
                }

                ArrayList<Integer> possibleMoves = new ArrayList<>(sorted.keySet());

                int move = possibleMoves.get(new Random().nextInt(count));

                isValid = takeSquare(move);

                if (isValid) {
                    State aiState;
                    if (playerPiece == State.SQUARE_X) {
                        aiState = State.SQUARE_O;
                    }
                    else {
                        aiState = State.SQUARE_X;
                    }

                    updateAI(move, aiState);
                }
            }
        }
        else if (aiLevel == AILevel.MEDIUM) {
            while (!isValid) {
                Random r = new Random();

                int move = r.nextInt(NUM_SQUARES);

                isValid = takeSquare(move);

                if (isValid) {
                    State aiState;
                    if (playerPiece == State.SQUARE_X) {
                        aiState = State.SQUARE_O;
                    }
                    else {
                        aiState = State.SQUARE_X;
                    }

                    updateAI(move, aiState);
                }
            }
        }
        else if (aiLevel == AILevel.HARD) {
            while (!isValid) {
                State[] s = new State[NUM_SQUARES];
                System.arraycopy(squares, 0, s, 0, s.length);

                HashMap<Integer, Integer> sc2 = new HashMap<>();

                for (int i = 0; i < s.length; i++) {
                    if (s[i] == State.SQUARE_EMPTY) {
                        int score = genScore(s, i);

                        sc2.put(i, score);
                    }
                }

                HashMap<Integer, Integer> sorted = sortMap(sc2);

                Iterator<Map.Entry<Integer, Integer>> x = sorted.entrySet().iterator();
                int test = sorted.entrySet().iterator().next().getValue();
                int count = 0;

                while (x.hasNext()) {
                    if (x.next().getValue() == test) {
                        count++;
                    }
                }

                ArrayList<Integer> possibleMoves = new ArrayList<>(sorted.keySet());

                int move = possibleMoves.get(new Random().nextInt(count));

                isValid = takeSquare(move);

                if (isValid) {
                    State aiState;
                    if (playerPiece == State.SQUARE_X) {
                        aiState = State.SQUARE_O;
                    }
                    else {
                        aiState = State.SQUARE_X;
                    }

                    updateAI(move, aiState);
                }
            }
        }
    }

    private int genScore(State[] b, int i) {
        int score = 0;
        State sqX = State.SQUARE_X;
        State sqO = State.SQUARE_O;
        final int TOP_LEFT = 0, TOP_MID = 1, TOP_RIGHT = 2,
                MID_LEFT = 3, MID_MID = 4, MID_RIGHT = 5,
                BOT_LEFT = 6, BOT_MID = 7, BOT_RIGHT = 8;

        switch (i) {
            case TOP_LEFT:
                if (b[TOP_MID] == sqX && b[TOP_RIGHT] == sqX)
                    score++;
                if (b[MID_LEFT] == sqX && b[BOT_LEFT] == sqX)
                    score++;
                if (b[MID_MID] == sqX && b[BOT_RIGHT] == sqX)
                    score++;
                if (b[TOP_MID] == sqO && b[TOP_RIGHT] == sqO)
                    score += 2;
                if (b[MID_LEFT] == sqO && b[BOT_LEFT] == sqO)
                    score += 2;
                if (b[MID_MID] == sqO && b[BOT_RIGHT] == sqO)
                    score += 2;
                break;
            case TOP_MID:
                if (b[TOP_LEFT] == sqX && b[TOP_RIGHT] == sqX)
                    score++;
                if (b[MID_MID] == sqX && b[BOT_MID] == sqX)
                    score++;
                if (b[TOP_LEFT] == sqO && b[TOP_RIGHT] == sqO)
                    score += 2;
                if (b[MID_MID] == sqO && b[BOT_MID] == sqO)
                    score += 2;
                break;
            case TOP_RIGHT:
                if (b[TOP_MID] == sqX && b[TOP_LEFT] == sqX)
                    score++;
                if (b[MID_RIGHT] == sqX && b[BOT_RIGHT] == sqX)
                    score++;
                if (b[MID_MID] == sqX && b[BOT_LEFT] == sqX)
                    score++;
                if (b[TOP_MID] == sqO && b[TOP_LEFT] == sqO)
                    score += 2;
                if (b[MID_RIGHT] == sqO && b[BOT_RIGHT] == sqO)
                    score += 2;
                if (b[MID_MID] == sqO && b[BOT_LEFT] == sqO)
                    score += 2;
                break;
            case MID_LEFT:
                if (b[MID_MID] == sqX && b[MID_RIGHT] == sqX)
                    score++;
                if (b[TOP_LEFT] == sqX && b[BOT_LEFT] == sqX)
                    score++;
                if (b[MID_MID] == sqO && b[MID_RIGHT] == sqO)
                    score += 2;
                if (b[TOP_LEFT] == sqO && b[BOT_LEFT] == sqO)
                    score += 2;
                break;
            case MID_MID:
                if (b[MID_LEFT] == sqX && b[MID_RIGHT] == sqX)
                    score++;
                if (b[TOP_MID] == sqX && b[BOT_MID] == sqX)
                    score++;
                if (b[TOP_LEFT] == sqX && b[BOT_RIGHT] == sqX)
                    score++;
                if (b[TOP_RIGHT] == sqX && b[BOT_LEFT] == sqX)
                    score++;
                if (b[MID_LEFT] == sqO && b[MID_RIGHT] == sqO)
                    score += 2;
                if (b[TOP_MID] == sqO && b[BOT_MID] == sqO)
                    score += 2;
                if (b[TOP_LEFT] == sqO && b[BOT_RIGHT] == sqO)
                    score += 2;
                if (b[TOP_RIGHT] == sqO && b[BOT_LEFT] == sqO)
                    score += 2;
                break;
            case MID_RIGHT:
                if (b[MID_MID] == sqX && b[MID_LEFT] == sqX)
                    score++;
                if (b[TOP_RIGHT] == sqX && b[BOT_RIGHT] == sqX)
                    score++;
                if (b[MID_MID] == sqO && b[MID_LEFT] == sqO)
                    score += 2;
                if (b[TOP_RIGHT] == sqO && b[BOT_RIGHT] == sqO)
                    score += 2;
                break;
            case BOT_LEFT:
                if (b[BOT_MID] == sqX && b[BOT_RIGHT] == sqX)
                    score++;
                if (b[MID_LEFT] == sqX && b[TOP_LEFT] == sqX)
                    score++;
                if (b[MID_MID] == sqX && b[TOP_RIGHT] == sqX)
                    score++;
                if (b[BOT_MID] == sqO && b[BOT_RIGHT] == sqO)
                    score += 2;
                if (b[MID_LEFT] == sqO && b[TOP_LEFT] == sqO)
                    score += 2;
                if (b[MID_MID] == sqO && b[TOP_RIGHT] == sqO)
                    score += 2;
                break;
            case BOT_MID:
                if (b[BOT_LEFT] == sqX && b[BOT_RIGHT] == sqX)
                    score++;
                if (b[MID_MID] == sqX && b[TOP_MID] == sqX)
                    score++;
                if (b[BOT_LEFT] == sqO && b[BOT_RIGHT] == sqO)
                    score += 2;
                if (b[MID_MID] == sqO && b[TOP_RIGHT] == sqO)
                    score += 2;
                break;
            case BOT_RIGHT:
                if (b[BOT_LEFT] == sqX && b[BOT_MID] == sqX)
                    score++;
                if (b[MID_RIGHT] == sqX && b[TOP_RIGHT] == sqX)
                    score++;
                if (b[MID_MID] == sqX && b[TOP_LEFT] == sqX)
                    score++;
                if (b[BOT_LEFT] == sqO && b[BOT_RIGHT] == sqO)
                    score += 2;
                if (b[MID_RIGHT] == sqO && b[TOP_RIGHT] == sqO)
                    score += 2;
                if (b[MID_MID] == sqO && b[TOP_LEFT] == sqO)
                    score += 2;
                break;
        }

        return score;
    }

    private boolean takeSquare(int square) {
        boolean result = false;

        if (squares[square] == State.SQUARE_EMPTY) {
            result = true;
        }

        return result;
    }

    private LinkedHashMap<Integer, Integer> sortMap(HashMap<Integer, Integer> map) {
        List<Integer> keys = new ArrayList<>(map.keySet());
        List<Integer> vals = new ArrayList<>(map.values());
        Collections.sort(vals);
        Collections.reverse(vals);
        Collections.sort(keys);
        Collections.reverse(keys);

        LinkedHashMap<Integer, Integer> sorted = new LinkedHashMap<>();

        for (Integer val : vals) {

            for (Integer key : keys) {
                String comp1 = map.get(key).toString();
                String comp2 = val.toString();

                if (comp1.equals(comp2)) {
                    map.remove(key);
                    keys.remove(key);
                    sorted.put(key, val);
                    break;
                }

            }

        }
        return sorted;
    }

    private byte checkWin() {
        // A bit of shorthand to make writing the conditionals easier and faster
        State[] s = new State[NUM_SQUARES];
        State sqX = State.SQUARE_X;
        State sqO = State.SQUARE_O;
        State sqE = State.SQUARE_EMPTY;
        System.arraycopy(squares, 0, s, 0, s.length);

        if ((s[0] == sqX && s[1] == sqX && s[2] == sqX) ||		// X wins
                (s[3] == sqX && s[4] == sqX && s[5] == sqX) ||
                (s[6] == sqX && s[7] == sqX && s[8] == sqX) ||
                (s[0] == sqX && s[3] == sqX && s[6] == sqX) ||
                (s[1] == sqX && s[4] == sqX && s[7] == sqX) ||
                (s[2] == sqX && s[5] == sqX && s[8] == sqX) ||
                (s[0] == sqX && s[4] == sqX && s[8] == sqX) ||
                (s[6] == sqX && s[4] == sqX && s[2] == sqX)) {
            return 1;
        }
        else if ((s[0] == sqO && s[1] == sqO && s[2] == sqO) ||	// O wins
                (s[3] == sqO && s[4] == sqO && s[5] == sqO) ||
                (s[6] == sqO && s[7] == sqO && s[8] == sqO) ||
                (s[0] == sqO && s[3] == sqO && s[6] == sqO) ||
                (s[1] == sqO && s[4] == sqO && s[7] == sqO) ||
                (s[2] == sqO && s[5] == sqO && s[8] == sqO) ||
                (s[0] == sqO && s[4] == sqO && s[8] == sqO) ||
                (s[6] == sqO && s[4] == sqO && s[2] == sqO)) {
            return 2;
        }
        else if (s[0] != sqE && s[1] != sqE && s[2] != sqE &&	// Tie
                s[3] != sqE && s[4] != sqE && s[5] != sqE &&
                s[6] != sqE && s[7] != sqE && s[8] != sqE) {
            return 3;
        }

        return 0;
    }
}
