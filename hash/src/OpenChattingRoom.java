/**
 * p. 249 오픈채팅방
 * 카카오톡 오픈채팅방에 관리자가 볼 수 있는 로그가 있다. 이 로그에는 [닉네임]님이 들어왔습니다, [닉네임]님이 나갔습니다 가 출력된다.
 * 사용자는 채팅방을 나가거나, 들어오거나, 닉네임을 변경할 수 있는데, 닉네임 변경은 채팅방 내에서 변경하거나 방을 나간 후 바뀐 닉네임으로 들어오는 경우 두가지가 있다.
 * 채팅방 내에서 변경한 경우 Change uid1234 {바뀐 닉네임} 으로 record 배열에 기록되며, 채팅방을 나간 후 닉네임을 변경하고 재입장하면 Enter uid1234 {바뀐 닉네임} 으로 기록된다.
 * 채팅방을 나갈때는 Leave uid1234 로 기록되며, 채팅방을 나간 후 닉네임을 변경하는 경우는 없다.
 * 닉네임을 변경했을 경우, 관리자 로그의 모든 메시지가 해당 변경의 영향을 받는다. 예를 들어 uid1234가 닉네임을 변경하면 이전 uid1234가 남긴 로그의 닉네임도 전부 변경된다.
 * 이 때, 관리자가 볼 수 있는 최종 메시지 로그를 문자열 배열로 반환하는 solution() 함수를 구현하시오.
 * 권장 시간 복잡도 O(N)
 */
import java.util.*;
public class OpenChattingRoom {
    public static String[] solution(String[] record) {
        // 내 생각 : 전체 기록 배열 record를 관리자가 보는 최종 기록으로 변환하기 위해서는 2가지를 고려해야 함.
        // 같은 uid의 최종 change 또는 최종 enter + 닉네임 변경 / 긴 배열에서 특정 단어를 검색 = Hash 활용, key,value 구조가 필요하므로 hashMap을 활용
        // HashMap<String, String> 으로 uid별 닉네임 기록하기 (끝까지 반복 돌리면 가장 나중 닉네임이 들어가므로 변경된 닉네임 확인 가능)

        HashMap<String, String> map = new HashMap<>();
        List<String> answer = new ArrayList<>();
        for(String r : record) {
            String[] arr = r.split(" ");
            if(arr[0].equals("Leave")) continue;
            map.put(arr[1], arr[2]);
        }

        for(int i=0; i<record.length; i++) {
            String[] arr = record[i].split(" ");
            if(arr[0].equals("Enter")) {
                answer.add(map.get(arr[1])+"님이 들어왔습니다.");
            } else if(arr[0].equals("Leave")) {
                answer.add(map.get(arr[1])+"님이 나갔습니다.");
            } else {
                continue;
            }
        }
        return answer.toArray(new String[0]);
    }
}
/**
 * 정답! 근데 두번째 루프에서 split하지 않고 첫번째 루프에서 Enter/Leave 정보를 미리 저장하면 더 효율적으로 구현 가능
 */
