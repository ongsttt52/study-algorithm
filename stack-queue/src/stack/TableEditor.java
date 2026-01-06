package stack;

import java.util.Arrays;
import java.util.Stack;

/**
 * p.177 표 편집
 *
 * n개의 행을 가진 표가 있다. 각 표에는 행 번호(0~n-1)와 이름이 있다. 처음 선택한 행의 위치를 나타내는 정수 k, 명령어들이 담긴 문자열 배열 cmd가 있다.
 *
 * 명령어는 각각 U X, D X, C, Z가 있다. U는 현재 선택한 행으로부터 X만큼 위의 행을 선택하는 명령어, D X는 X만틈 아래 행을 선택하는 명령어, C는 선택한 행을 삭제하는 명령어, Z는 최근 삭제한 행을 복구시키는 명령어이다.
 * 행을 복구시키면 원래 있던 위치와 번호로 복구되며, 선택중인 행을 삭제한 경우에는 한칸 아래의 행이 자동으로 선택된다. 만약 마지막 행을 선택 후 삭제했을 경우는 한칸 위의 행을 선택한다.
 * 행을 벗어나거나, 행을 모두 삭제하고 추가 입력이 있는 경우, 삭제한 행이 없을 때 Z가 명령어로 주어지는 경우는 없다.
 *
 * 모든 명령어를 수행한 후의 표의 상태와 맨 처음 표의 상태를 비교하여 삭제되지 않은 행은 O, 삭제된 행은 X로 표시해 문자열 배열 형태로 반환하는 solution 함수를 완성하시오.
 *
 * 5<= n <= 1,000,000
 * 0<= k < n
 * 1 <= cmd의 원소 갯수 <= 200,000
 * X는 1 이상 300,000 이하인 자연수이며, 0으로 시작하지 않음
 * cmd에 등장하는 모든 X들의 값을 합친 결과가 1,000,000 이하인 경우만 입력으로 주어짐
 * 표의 '이름'은 이해를 돕기 위한 값이며, 실제 문제 풀이에 사용되지 않음. 서로 다른 값들로 채워져있다고 가정할 것
 *
 * 정확성 테스트 케이스 제약 조건 : 10초, 5<= n <= 1000, 1<= cmd의 원소 갯수 <= 1000
 */

public class TableEditor {
    public static String solution(int n, int k, String[] cmd) {
        int pointer  = k;

        Stack<Integer> deleted = new Stack<>();

        int[] table = new int[n];

        for(int i=0; i<cmd.length; i++) {
            String c = cmd[i];
            char c0 = c.charAt(0);
            if(c0 == 'U') {
                int x = Integer.parseInt(c.substring(2));
                pointer = pointer - x;
                while(table[pointer] == -1) {
                    pointer--;
                }
            } else if(c0 == 'D') {
                int x = Integer.parseInt(c.substring(2));
                pointer = pointer + x;
                while(table[pointer] == -1) {
                    pointer++;
                }
            } else if(c0 == 'C') {
                table[pointer] = -1;
                deleted.push(pointer);
                if(pointer == n-1) {
                    pointer--;
                } else {
                    pointer++;
                }
            } else if(c0 == 'Z') {
                table[deleted.pop()] = 0;
            }
        }

        StringBuilder sb = new StringBuilder();
        for(int i=0; i<table.length; i++) {
            if(table[i] == 0) sb.append("O");
            else sb.append("X");
        }

        return sb.toString();
    }
    /** 버그 1 : n개 행인데 new int[n-1]
     *  버그 2 : pointer = pointer - c.charAt(2); 에서 X가 여러 자리일 수 있으므로 int x = Integer.parseInt(c.substring(2));
     *  버그 3 : C에서 삭제된 위치를 저장해야 하는데, 이동 후 위치를 저장하도록 설계함
     *  버그 4 : 삭제된 행을 건너뛰며 이동하는 방식은 이동하는데 O(N), 명령어 처리 O(M) 으로 O(N+M) 으로 시간 초과됨, 인덱스만으로 연산하거나, 양방향 연결 리스트를 활용해야 함
     */

    // 인덱스만으로 연산하기 (연결 리스트를 배열로 구현한 방식)
     public static String solution2(int n, int k, String[] cmd) {
         Stack<Integer> deleted = new Stack<>();

         // 각 행을 기준으로 U,D 연산에 따른 위치를 표시하기 위한 배열
         int[] up = new int[n+2];
         int[] down = new int[n+2];

         for(int i=0; i<n+2; i++) {
             up[i] = i-1;
             down[i] = i+1;
         }
         // 현재 위치를 나타내는 인덱스, 위 아래로 가상의 행이 하나씩 생겼으므로 1을 더해줌
         k++;

         // 주어진 명령어를 하나씩 처리
         for(String c : cmd) {
             if(c.startsWith("C")) {
                 deleted.push(k);
                 up[down[k]] = up[k];
                 down[up[k]] = down[k];
                 // down[k] 가 n보다 크면 = 행 삭제 후 내려갈 수 없는 위치이면 k를 up[k]로, 내려가도 괜찮은 위치이면 down[k]로 한다
                 k = n < down[k] ? up[k] : down[k];
             } else if(c.startsWith("Z")) {
                 int restore = deleted.pop();
                 // restore가 가리키던 up의 down을 restore로, restore가 가리키던 down의 up을 restore로 만듦 = restore가 사이에 끼워짐
                 down[up[restore]] = restore;
                 up[down[restore]] = restore;
             } else {
                 String[] s = c.split(" ");
                 int x = Integer.parseInt(s[1]);

                 for(int i=0; i<x; i++) {
                     k = s[0].equals("U") ? up[k] : down[k];
                 }
             }
         }

         char[] answer = new char[n];

         Arrays.fill(answer, 'O');

         for(int i : deleted) {
             answer[i-1] = 'X';
         }
         return new String(answer);
     }
}
