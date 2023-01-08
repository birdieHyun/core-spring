package hello.core.order;

import hello.core.annotation.MainDiscountPolicy;
import hello.core.discount.DiscountPolicy;
import hello.core.discount.FixDiscountPolicy;
import hello.core.discount.RateDiscountPolicy;
import hello.core.member.Member;
import hello.core.member.MemberRepository;
import hello.core.member.MemoryMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
//@RequiredArgsConstructor //final 이 붙은 필드를 가진 생성자를 만들어준다. 생성자가 필요할 때만 쓰고 웬만하면 롬복 기능 사용한다.
public class OrderServiceImpl implements OrderService {

//    private final MemberRepository memberRepository = new MemoryMemberRepository();

    // OrderServiceImpl 은 DiscountPolicy 와 FixDiscountPolicy 모두에 의존하고 있다. 즉 추상과 구체 모두에게 의존하고 있다.
//    private final DiscountPolicy discountPolicy = new FixDiscountPolicy();
//    private final DiscountPolicy discountPolicy = new RateDiscountPolicy();

    private final MemberRepository memberRepository;
    private final DiscountPolicy discountPolicy;
    // final 이 있으면 생성자에서만 값을 넣어줄 수 있고, 이후에 변경할 수 없다.
    // private final 선언하면 값이 있어야 한다는 의미이다. 생성자에서 값이 들어와야 한다는 의미이다.
    // 생성자에 들어가 있으면 관례상 값을 다 넣어줘야 한다.

//    @Autowired
//    public void setDiscountPolicy(DiscountPolicy discountPolicy) {
//        this.discountPolicy = discountPolicy;
//    }
//    setter 주입 (수정자 주입)
//    @Autowired
//    public void setMemberRepository(MemberRepository memberRepository) {
//        this.memberRepository = memberRepository;
//    }


    //    이 생성자를 롬복이 만들어준다.
    @Autowired   // 생성자가 하나라면 Autowired는 생략 가능하다.
    public OrderServiceImpl(MemberRepository memberRepository, @MainDiscountPolicy DiscountPolicy discountPolicy) {
        this.memberRepository = memberRepository;
        this.discountPolicy = discountPolicy;
    }

    @Override
    public Order createOrder(Long memberId, String itemName, int itemPrice) {
        Member member = memberRepository.findById(memberId);
        int discountPrice = discountPolicy.discount(member, itemPrice);

        return new Order(memberId, itemName, itemPrice, discountPrice);
    }
    // 주문을 해서 반환해주는 역할을 담당


    // test 용도
    public MemberRepository getMemberRepository() {
        return memberRepository;
    }

}

// 인터페이스에만 의존하고 있다.