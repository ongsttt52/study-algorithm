package queue;

/**
 * p.199 요세푸스 문제 (백준 실버 5 정도)
 *
 * N명의 사람들이 원으로 서있고, 각자 1~N까지의 번호표를 가지고 있다. K가 주어질 때 1번부터 K번째 사람을 없애고, 없앤 사람의 다음을 기준으로 또 K번째 사람을 없애고, 이것을 반복할 때 마지막까지 남는 사람의 번호를 반환하는 solution() 함수를 구현하시오
 * - K번째는 시작 인원을 포함하여 센다
 * - 1 < N, K <= 1000 , 권장 시간 복잡도 O(N*K)
 *
 */

import java.util.*;
import java.io.*;

public class Josephus {
    public static int solution(int n, int k) {
        // 내 생각 : 원형 + 정해진 순서대로 제거 -> 원형 큐 활용, 최대 1000^2 이므로 O(N^2) 가능
        // 우선 싹 다 큐에 넣고, 1~K까지 전부 poll, K를 제외하고 전부 add 하면 원형 모양 유지하면서 K만 빼기 가능
        ArrayDeque<Integer> ad = new ArrayDeque<>();

        for(int i=1; i<=n; i++) {
            ad.add(i);
        }
        while(ad.size() > 1) {
            for(int i=1; i<=k; i++) {
                int x = ad.pollFirst();
                if(i==k) break;
                ad.addLast(x);
            }
        }
        return ad.poll();
    }
}

/**
 * 정답 맞춤!! 근데 좀 더 깔끔한 버전 :
 *      while (ad.size() > 1) {
 *          for (int i = 0; i < k - 1; i++) {
 *              ad.addLast(ad.pollFirst());  // k-1명 뒤로 보내기
 *          }
 *          ad.pollFirst();  // k번째 제거
 *      }
 *  -> k-1명 뒤로 보내고, k번째 제거라는 의도가 더 명확해진다.
 *
 *  위 코드는 "k-1명 건너뛰고 1명 제거"라는 구현 관점이고, 내 코드는 "k번째를 없앤다"는 문제 관점
 */