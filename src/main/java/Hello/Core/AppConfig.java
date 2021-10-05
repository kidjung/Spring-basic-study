package Hello.Core;

import Hello.Core.discount.DiscountPolicy;
import Hello.Core.discount.FixDiscountPolicy;
import Hello.Core.discount.RateDiscountPolicy;
import Hello.Core.member.MemberRepository;
import Hello.Core.member.MemberService;
import Hello.Core.member.MemberServiceImpl;
import Hello.Core.member.MemoryMemberRepository;
import Hello.Core.order.OrderService;
import Hello.Core.order.OrderServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class AppConfig {

    @Bean
    public MemberService memberService() {
        System.out.println("AppConfig.memberService");
        return new MemberServiceImpl(memberRepository());
    }

    @Bean
    public MemberRepository memberRepository() {
        System.out.println("AppConfig.memberRepository");
        return new MemoryMemberRepository();
    }

    @Bean
    public OrderService orderService() {
        System.out.println("AppConfig.orderService");
        return new OrderServiceImpl(memberRepository(), discountPolicy());
    }

    @Bean
    public DiscountPolicy discountPolicy() {
        return new RateDiscountPolicy();
    }

}
