// https://www.hackerrank.com/challenges/the-quickest-way-up

import java.util.*;
import java.util.stream.Stream;

public class Solution {

    private Scanner scanner;
    private SnakesAndLaddersBoard[] testCases;
    private List<String> results;

    public Solution(Scanner scanner) {
        this.scanner = scanner;
        results = new ArrayList<>();
    }

    public static void main(String[] args) {
        Solution solution = new Solution(new Scanner(System.in));
        solution.calculate().print();
    }

    public Solution calculate() {
        readInput();
        calculateTestCases();
        return this;
    }

    private void calculateTestCases() {
        for (SnakesAndLaddersBoard current : testCases) {
            results.add(current.obtainMinNumberOfRolls());
        }
    }

    private void readInput() {
        int numTestCases = scanner.nextInt();
        testCases = new SnakesAndLaddersBoard[numTestCases];
        for (int i = 0; i < numTestCases; i++) {
            SnakesAndLaddersBoard currentBoard = new SnakesAndLaddersBoard();
            readLadders(currentBoard);
            readSnakes(currentBoard);
            testCases[i] = currentBoard;
        }
    }

    private void readSnakes(SnakesAndLaddersBoard currentBoard) {
        readExtraMovements(currentBoard);
    }

    private void readLadders(SnakesAndLaddersBoard currentBoard) {
        readExtraMovements(currentBoard);
    }

    private void readExtraMovements(SnakesAndLaddersBoard currentBoard) {
        int cases = scanner.nextInt();
        for (int j = 0; j < cases; j++) {
            currentBoard.addLink(scanner.nextInt(), scanner.nextInt());
        }
    }

    public void print() {
        results.stream().forEach(System.out::println);
    }

    static private class SnakesAndLaddersBoard {

        private final Map<Integer, Integer> movements;

        private int[] rollsToGetTo;

        public SnakesAndLaddersBoard() {
            movements = new HashMap<>();
            rollsToGetTo = new int[101];
            startOnSquare1();
            initializeRolls();
            initializeMovements();
        }

        private void initializeRolls() {
            for (int i = 2; i < rollsToGetTo.length; i++) {
                rollsToGetTo[i] = Integer.MAX_VALUE;
            }
        }

        private void startOnSquare1() {
            rollsToGetTo[1] = 0;
        }

        private Stream<Integer> positions() {
            return movements.keySet().stream();
        }

        private void initializeMovements() {
            initialPositions().forEach((i) -> {
                int from = i;
                int to = from;
                movements.put(from, to);
            });
        }

        private Stream<Integer> initialPositions() {
            List<Integer> result = new ArrayList<>();
            for (int i = 1; i <= 100; i++) {
                result.add(i);
            }
            return result.stream();
        }

        public String obtainMinNumberOfRolls() {
            positions().forEach((k) -> {
                final Integer i = movements.get(k);
                Integer currentRolls = rollsToGetTo[i] + 1;
                for (int j = 1; j <= 6; j++) {
                    final int targetPosition = i + j;

                    if (targetPosition > 100) { //if the throw goes over the last position
                        continue;
                    }

                    final Integer to = movements.get(targetPosition);
                    rollsToGetTo[to] = Math.min(currentRolls, rollsToGetTo[to]);
                }
            })

            ;
            final int rollsToGetTo100 = rollsToGetTo[100];
            if (Integer.MAX_VALUE == rollsToGetTo100) {
                return "-1";
            }
            return String.valueOf(rollsToGetTo100);
        }

        public void addLink(int from, int to) {
            movements.put(from, to);
        }
    }


}
