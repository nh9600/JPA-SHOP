package jpabook.jpashop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;

@Embeddable
@Getter
public class Address { //생성할 때만 값이 생성이 돼서, 값이 변경되면 안 됨

    private String city;
    private String street;
    private String zipcode;

    protected Address() { //손대지 말자, 함부로 new생성하지말기

    }

    public Address(String city, String street, String zipcode) {
        this.city = city;
        this.street = street;
        this.zipcode = zipcode;
    }

}
