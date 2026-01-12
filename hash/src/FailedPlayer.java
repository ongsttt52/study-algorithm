import java.util.HashMap;
import java.util.HashSet;

/**
 * p.239 완주하지 못한 선수
 *
 * 단 한 명의 선수를 제외하고 모두가 마라톤에 완주 하였다. 마라톤에 참여한 선수들의 이름이 담긴 배열 participant와 완주한 선수들의 이름 배열 completion이 있을 때, 완주하지 못한 선수의 이름을 반환하는 solution() 함수를 구현하시오
 *
 * participant는 1 이상 100,000 이하, 참가자 이름은 1개 이상 20개 이하의 알파벳 소문자이다. 참가자 중에는 동명이인이 있을 수 있다.
 */
public class FailedPlayer {
    public static String solution(String[]participant, String[] completion) {
        // 내 생각 : completion을 다 해시셋에 넣고 이름 하나씩 넣으면서 돌리다가 contains=false 나오면 return 하기 (시간 복잡도 O(N))
        // 동명이인이 있는 경우, 반복문을 한번 더 돌려서 동명이인을 찾아 반환한다
        HashSet<String> hs = new HashSet<>();
        for(String s : completion) hs.add(s);

        for(int i=0; i<participant.length; i++) {
            String p = participant[i];
            if(!hs.contains(p)) return p;

            if(i==participant.length) { // 동명이인 때문에 contains=false가 안나옴
                HashSet<String> hs2 = new HashSet<>();
                for(String s : participant) {
                    hs2.add(s);
                    if(hs2.contains(s)) {
                        return p;
                    }
                }
            }
        }
        return null;
    }
    /**
     * 버그 1 : HashSet은 중복을 처리 못함 -> 동명이인 저장이 무시됨
     * 버그 2 : if(i==participant.length) 에 도달 불가 (i<participant.length 이므로)
     * 버그 3 : hs2.add(s) 이후 if(...) 처리하면 add 직후라 항상 true임. contains 먼저 확인한 후 add 해야함
     */

    // HashMap 사용하는 버전
    public static String solution2(String[] participant, String[] completion) {
        HashMap<String, String> map = new HashMap<>();

        for(String s : completion) map.put(s, s);

        for(int i=0; i<participant.length; i++) {
            String p = participant[i];
            if(!map.containsKey(p)) return p;

            if(i == participant.length-1) {
                HashMap<String, String> map2 = new HashMap<>();
                for(String s : participant) {
                    if(map2.containsKey(s)) return s;
                    map2.put(s, s);
                }
            }
        }
        return null;
    }
    /**
     * 버그 : 여러 종류의 동명 이인이 있을 때, 먼저 등장하는 중복을 반환해버림. 예를 들어 :
     * participant = ["a", "a", "b", "b"]
     * completion  = ["a", "a", "b"]
     *
     * 정답: "b"
     * 출력: "a" (오답)
     *
     * 근본 원인은 HashMap<String, String> 으로 "이 이름이 몇번 나왔는가" 를 세려고 시도한 것. 횟수를 세려면 HashMap<String, Integer> 를 썼어야 함
     */

    // HashMap 으로 횟수 카운트
    public static String solution3(String[] participant, String[] completion) {
        HashMap<String, Integer> map = new HashMap<>();

        for(String p : participant) {
            map.put(p, map.getOrDefault(p, 0) + 1); // p가 총 몇번 나왔는지를 value에 저장
        }

        for(String c : completion) {
            map.put(c, map.get(c) - 1); // 완주자 목록에 있으면 value를 하나 차감함
        }

        for(String key : map.keySet()) {
            if(map.get(key) > 0) return key; // value가 0이 아닌 key를 찾음. (동명 이인까지 전부 완주했다면 value가 0일 것이므로)
        }

        return null;
    }
}
