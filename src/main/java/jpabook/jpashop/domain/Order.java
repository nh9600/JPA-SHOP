package jpabook.jpashop.domain;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.metamodel.model.domain.internal.MapMember;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.LAZY;

@Entity
@Table(name = "orders")
@Getter
@Setter
public class Order {

    @Id
    @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    //ont to many는 기본설정이 lazy
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)//모든 items
    private List<OrderItem> orderItems = new ArrayList<>();

    @OneToOne(fetch = LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;

    private LocalDateTime orderDate; //주문시간

    @Enumerated(EnumType.STRING)
    private OrderStatus status; //주문상태 [order, cancel]
    
    //연관관계 메서드
    public void setMember(Member member){
        this.member = member;
        member.getOrders().add(this);
    }

    public void addOrderItem(OrderItem orderItem){
        orderItems.add(orderItem);
        orderItem.setOrder(this);

    }

    public void setDelivery(Delivery delivery){
        this.delivery = delivery;
        delivery.setOrder(this);//양쪽 셋팅
    }

    //생성 메소드
    public static Order createOrder(Member member, Delivery delivery, OrderItem... orderItems){ //주문생성
        Order order = new Order();
        order.setMember(member);
        order.setDelivery(delivery);
        for(OrderItem orderItem : order.orderItems){
            order.addOrderItem(orderItem);
        }
        order.setStatus(OrderStatus.ORDER); //연관관계를 걸어줌
        order.setOrderDate(LocalDateTime.now()); //현재시각
        return order;
    }

    //비즈니스 로직
    //주문취소(cancel)
    public void cancel(){
        if(delivery.getStatus() == DeliveryStatus.COMP){
            throw new IllegalStateException("이미 배송완료된 상품은 취소가 불가능합니다");
        }

        this.setStatus(OrderStatus.CANCLE);
        for(OrderItem orderItem : orderItems){
            orderItem.cancel();
        }
    }

    //조회 로직
    //전체 주문 가격 조회
    public int getTotalPrice(){
        int totalPrice = orderItems.stream().mapToInt(OrderItem::getTotalPrice).sum();
        return totalPrice;
    }

}
