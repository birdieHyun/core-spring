package hello.core.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 이 클래스의 문제점은 MemberServiceImpl 이 MemberRepository 라는 인터페이스를 의존하고 있는데,
 * MemoryMemberRepository 라는 구현체까지 의존하고 있다.
 * 즉 추상화에도 의존하고, 구체화에도 의존하고 있다.
 * -> DIP 를 위반하고 있다.
 * 구체적인 건 주문까지 만들고 설명
 */
@Component
public class MemberServiceImpl implements MemberService { // 구현체가 하나만 있으면 인터페이스명 뒤에 Impl 만 붙인다.

    // 인터페이스를 생성하기 위해서는 구현 객체를 생성해 주어야 한다. 여기서는 방금 만든 메모리멤버리파지토리 넣는다.
    private MemberRepository memberRepository;

    @Autowired // ac.getBean(MemberRepository.class) 와 같이 동작한다고 이해하면 된다(세부적으로는 다르지만)
    public MemberServiceImpl(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public void join(Member member) {
        memberRepository.save(member);
    }
    // join 에서 save 를 호출하면, 다형성에 의해서 memberRepository 의 save 가 아닌 MemoryMemberRepository 의 save 가 호출된다.

    @Override
    public Member findMember(Long memberId) {
        return memberRepository.findById(memberId);
    }

    // test 용도
    public MemberRepository getMemberRepository() {
        return memberRepository;
    }
}
