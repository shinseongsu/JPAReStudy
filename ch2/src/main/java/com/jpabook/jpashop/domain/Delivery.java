package com.jpabook.jpashop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@Setter
public class Delivery {

    @Id
    @GeneratedValue
    @Column(name = "delivery_id")
    private Long id;

    @OneToOne(mappedBy = "delivery", fetch= LAZY)
    private Order order;

    @Embedded
    private Address address;

    //EnumType.ORDINAL 은 숫자로 들어감. 중간에 새로운 상태값 생기면 망함. 상태값은 꼭 스트링으로
    @Enumerated(EnumType.STRING)
    private DeliveryStatus status; //READY, COMP
}
