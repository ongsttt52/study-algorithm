package stack; /**
 * p.168 크레인 인형 뽑기 게임
 *
 * 크레인 인형뽑기 기계를 모바일 게임으로 만드려 한다. 게임 화면 board는 1x1 크기의 격자로 구성된 NxN 크기의 격자이며 위쪽에는 크레인이 있고, 오른쪽에는 바구니가 있다.
 *
 * board는 2차원 배열, 5*5 ~ 30*30 크기의 격자이다.
 * board의 각 칸에는 0 이상 100 이하인 정수가 담겨 있으며, 0은 빈칸을 나타낸다. 1~100 사이의 정수는 각기 다른 모양의 인형을 나타낸다.
 * moves 배열은 크레인의 움직임을 나타내며, 1 이상 1000 이하의 크기의 배열이다. moves 배열의 원소값들은 1이상이며 board 배열 가로 크기 이하인 자연수이다.
 *
 * 크레인이 moves의 움직임을 따라 board의 인형을 하나 뽑아서 바구니에 넣을 때, 모양이 같은 2개의 인형이 연속해서 들어가게 되면 인형이 사라진다.
 * 인형이 사라진 횟수 result를 구하는 함수 solution을 만드시오.
 *
 */
import java.util.*;


public class DollClawGame {
    public static int solution(int[][] board, int[] moves) {
        int result = 0;
        int n = board.length;
        int m = moves.length;

        // 바구니 스택
        ArrayDeque<Integer> basket = new ArrayDeque<>();

        for (int i = 0; i < m; i++) {
            // 크레인이 뽑을 board 열
            int column = moves[i] - 1;

            // 크레인이 뽑은 숫자 구하기
            int number = 0;
            for (int x = 0; x < n; x++) {
                if (board[x][column] != 0) {
                    number = board[x][column];
                    board[x][column] = 0;
                    break;
                }
            }

            // 뽑은 숫자 바구니에 넣기
            if (number != 0) {
                if (basket.isEmpty() || basket.peek() != number) {
                    basket.push(number);
                } else {
                    basket.pop();
                    result++;
                }
            }
        }
        return result;
    }


/**
 * 버그 1. break 누락 : 크레인이 뽑은 숫자 구하는 반복문에서 break가 없어 열 전체를 비우고 맨 아래 인형만 반환하게 됨
 * 버그 2. number == 0 인 경우 예외 처리 누락 : 열이 비어있으면 number가 0인 채로 basket에 push됨
 */


// board를 스택으로 변환하는 버전
    public static int solution2(int[][] board, int[] moves) {
        int result = 0;
        int n = board.length;

        ArrayDeque<Integer>[] lanes = new ArrayDeque[board.length];
        for(int i=0; i<lanes.length; i++) {
            lanes[i] = new ArrayDeque<>();
        }

        // board를 역순으로 탐색하며 각 열의 인형을 lanes에 추가
        for(int i=board.length-1; i>=0; i--) {
            for(int j=0; j<board[i].length; i++) {
                if(board[i][j] > 0) {
                    lanes[j].push(board[i][j]);
                }
            }
        }
        return result;
    }
}