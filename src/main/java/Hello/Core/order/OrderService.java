package Hello.Core.order;

public interface OrderService {
    Order createOrder(Long memberId, String itemName, int itemPrice);
}
