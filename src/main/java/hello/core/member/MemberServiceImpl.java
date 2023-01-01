package hello.core.member;

public class MemberServiceImpl implements MemberService { // 구현체가 하나만 있으면 인터페이스명 뒤에 Impl 만 붙인다.

    // 인터페이스를 생성하기 위해서는 구현 객체를 생성해 주어야 한다. 여기서는 방금 만든 메모리멤버리파지토리 넣는다.
    private MemberRepository memberRepository = new MemoryMemberRepository();

    @Override
    public void join(Member member) {
        memberRepository.save(member);
    }
    // join 에서 save 를 호출하면, 다형성에 의해서 memberRepository 의 save 가 아닌 MemoryMemberRepository 의 save 가 호출된다.

    @Override
    public Member findMember(Long memberId) {
        return memberRepository.findById(memberId);
    }
}
