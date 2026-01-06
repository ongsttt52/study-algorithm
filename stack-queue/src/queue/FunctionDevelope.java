package queue;

import java.util.*;

/**
 * p.204 기능 개발
 * 진도가 100% 일 때 서비스에 반영할 수 있는 기능들을 개발 중이다. 각 기능의 개발 속도는 모두 다르므로, 뒤의 기능이 앞의 기능보다 먼저 개발될 수 있다.
 * 뒤의 기능은 앞의 기능이 배포될 때 함께 배포되어야 한다. 배포 순서대로 작업 진도가 적힌 정수 배열 progresses와 각 작업의 개발 속도가 적힌 정수 배열 speeds가 주어질 때, 각 배포마다 몇개의 기능이 배포되는지 반환하는 solution() 함수를 구현하시오
 *
 * 작업 갯수는 100개 이하 (progresses, speeds), 작업 진도, 속도는 100미만의 자연수, 배포는 하루에 한 번만 할 수 있고, 하루의 끝에 이루어진다고 가정 (진도 95%인 작업의 개발속도가 4% 라면 배포는 2일 뒤에 이루어짐)
 * 권장 시간 복잡도 : O(N)
 */

public class FunctionDevelope {
    public static int[] solution(int[] progresses, int[] speeds) {
        // 문제 요약 : 맨 앞 기능부터 조건에 따라 제거하는 문제 -> 큐 활용
        // 구현 :반복문으로 각 기능에 speed 더하면서 맨 앞에꺼가 100이다 -> poll -> 그 다음꺼도 100이다 -> poll -> 100 아니면 멈춘다
        List<Integer> answer = new ArrayList<>();
        ArrayDeque<Integer> ad = new ArrayDeque<>();
        for(int x : progresses) ad.add(x);

        int count = 0;
        int index = -1;
        int answerCount = 1;
        while(!ad.isEmpty()) {
            int first = ad.poll();
            index++;
            first += speeds[index]*count;
            if(first >= 100) {
                answerCount++;
            } else {
                answer.add(answerCount);
                answerCount = 1;
            }
            while(first < 100) {
                first += speeds[index];
                count++;
            }
        }

        return answer.stream().mapToInt(i -> i).toArray(); // List -> int[] 로 변환 필요

        // 내일 다시 풀기...
    }


/**
 * # 버그
 * ### 1. 첫 번째 기능 완료 전에 answer에 추가됨
 * if(first >= 100) {
 *     answerCount++;
 * } else {
 *     answer.add(answerCount);  // 첫 기능이 완료 안됐는데 추가
 *     answerCount = 1;
 * }
 * ```
 *
 * 첫 기능이 100 미만이면 아직 아무것도 배포 안 했는데 answer에 1을 넣습니다.
 *
 * ---
 *
 * ### 2. 마지막 배포가 누락됨
 *
 * 루프 종료 후 마지막 `answerCount`를 `answer`에 추가하는 코드가 없습니다.
 *
 * ---
 *
 * ### 3. 배포 순서가 뒤집힘
 * ```
 * progresses = [93, 30, 55], speeds = [1, 30, 5]
 *
 * 실행 흐름:
 * 1. first=93 → 100 미만 → answer.add(1) → 7일 걸려서 완료
 * 2. first=30+30*7=240 → 100 이상 → answerCount=2
 * 3. first=55+5*7=90 → 100 미만 → answer.add(2)
 *
 * 결과: [1, 2] (오답)
 * 정답: [2, 1]
 */



// 근본 문제
// 로직 구조 자체가 맞지 않습니다. "완료 안 됐을 때 answer에 추가"하는 게 아니라 "완료됐을 때 함께 배포할 기능을 카운트"해야 합니다.

// 권장 접근법
// 각 기능의 완료 소요 일수를 먼저 계산하고, 앞 기능 기준으로 묶으면 됩니다.
// 큐를 활용한다는 발상은 나쁘지 않지만, 이 문제는 "각 작업의 완료 일수 계산 → 묶음 카운트"가 더 직관적입니다. 매번 시뮬레이션하지 않고 한 번에 일수를 계산하는 게 핵심입니다.

    public static int[] solution2(int[] progresses, int[] speeds) {
        int n = progresses.length;
        int[] days = new int[n];

        // 각 기능 완료까지 필요한 일 수 계산
        for(int i=0; i<days.length; i++) {
            int count = 0;
            int p = progresses[0];
            while(p < 100) {
                p += speeds[i];
                count++;
            }
            days[i] = count;
            // days[i] = (int)Math.ceil((100.0 - progresses[i]) / speeds[i]);
        }
        List<Integer> answer = new ArrayList<>();
        int maxDay = days[0];
        int count = 1;

        for(int i=1; i<n; i++) {
            if(days[i]<=maxDay) {
                // 앞 기능과 같이 배포 가능
                count++;
            } else {
                // 새 배포 시작
                answer.add(count);
                count = 1;
                maxDay = days[i];
            }
        }
        answer.add(count); // 마지막 배포

        return answer.stream().mapToInt(i -> i).toArray(); // List<Integer> -> int[]

    }
}
