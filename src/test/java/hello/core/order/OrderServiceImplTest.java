package hello.core.order;

import static org.junit.jupiter.api.Assertions.*;

import hello.core.discount.DiscountPolicy;
import hello.core.discount.RateDiscountPolicy;
import hello.core.member.Grade;
import hello.core.member.Member;
import hello.core.member.MemberRepository;
import hello.core.member.MemoryMemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class OrderServiceImplTest {
    @Test
    void createOrder() {
        MemberRepository memberRepository = new MemoryMemberRepository();
        memberRepository.save(new Member(1L, "memberA", Grade.VIP));

        DiscountPolicy discountPolicy = new RateDiscountPolicy();
        OrderServiceImpl orderService = new OrderServiceImpl(memberRepository, discountPolicy);

        Order order = orderService.createOrder(1L, "itenmA", 10000);

        Assertions.assertThat(order.getDiscountPrice()).isEqualTo(1000);
    }

    /**
     * NullPointerException 나오는 이유
     * -> OrderServiceImpl 내에서 memberRepository 와 discountPolicy 에 값을 세팅해주어야 하기 때문이다.
     */

}