import java.util.HashSet;

/**
 * p.236 두 개의 수로 특정값 만들기
 * n개의 양의 정수로 이루어진 배열 arr과 정수 target이 주어졌을 때, 이 중에서 합이 target인 두 수가 arr에 있는지 찾고 있으면 true, 없으면 false를 반환하는 solution() 함수를 구현하시오.
 *
 * n은 2 이상, 10,000 이하의 자연수
 * arr의 각 원소는 1 이상 10,000 이하의 자연수
 * arr의 원소 중 중복되는 원소는 없음
 * target은 1 이상 20,000 이하의 자연수
 *
 * 권장 시간 복잡도 O(N+K)
 */
public class TwoNumber {
    public static boolean solution(int[] arr, int target) {
        // 내 생각 : 10,000개 이하의 정수 덧셈 후 검색 -> 대충 1억번 정도 연산 필요 -> 이 횟수를 줄이려면
        // arr[i] 에 대해 arr[i] + x = target 이 되는 x를 찾아야 한다. x를 찾는 동작의 효율이 중요한데, 원소의 유무를 표시할 수 있는 해시셋을 이용하면 O(1) 안에 찾을 수 있다.

        HashSet<Integer> hs = new HashSet<>();
        for (int x : arr) hs.add(x);

        for (int i = 0; i < arr.length; i++) {
            int x = target - arr[i];
            if (hs.contains(x)) return true;
        }
        return false;
    }
    /**
     * 버그 : 자기 자신과 더하는 경우 (같은 원소를 두번 사용하게 됨)
     * arr = [5, 3, 7], target = 10
     * arr[0] = 5일 때, x = 10 - 5 = 5
     * hs.contains(5) → true → 잘못된 정답
     */

    // 순회하면서 추가 (자기 자신과 더하는 경우 원천 차단)
    public static boolean solution2(int[] arr, int target) {
        HashSet<Integer> hs = new HashSet<>();

        for (int num : arr) {
            int x = target - num;
            if (hs.contains(x)) return true;
            hs.add(num);  // 확인 후에 추가
        }
        return false;
}
}