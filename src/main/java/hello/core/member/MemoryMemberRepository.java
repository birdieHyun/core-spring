package hello.core.member;

import java.util.HashMap;
import java.util.Map;

/**
 * 인터페이스와 구현체는 다른 패키지에 있는 것이 좋다. 예제가 복잡해지기 때문에 여기서는 같은 패키지 내에서 생성
 */


public class MemoryMemberRepository implements MemberRepository {

    private static Map<Long, Member> store = new HashMap<>();  // 실무에서는 동시성 이슈때문에 ConCurrentHashMap 사용하지만, 예제이기 때문에 HashMap 사용

    @Override
    public void save(Member member) {
        store.put(member.getId(), member);
    }

    @Override
    public Member findById(Long memberId) {
        return store.get(memberId);
    }
}
