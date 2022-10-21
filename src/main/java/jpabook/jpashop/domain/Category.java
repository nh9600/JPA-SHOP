package jpabook.jpashop.domain;

import jpabook.jpashop.domain.item.Item;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Category { //실무에서는 ManyToMany를 쓰지말고 중간에 카테고리 클래스를 만들기

    @Id @GeneratedValue
    @Column(name="category_id")
    private Long id;

    private String name;

    @ManyToMany
    @JoinTable(name="category_item",
    joinColumns = @JoinColumn(name="category_id"),
    inverseJoinColumns = @JoinColumn(name="item_id")) //n : n테이블은 중간 테이블이 있어야 함
    private List<Item> items = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="parent_id") //부모
    private Category parent;

    @OneToMany(mappedBy = "parent") //자식
    private List<Category> child = new ArrayList<>();
    
    //연관관계 메서드
    public void addChildCategory(Category child){
        this.child.add(child);
        child.setParent(this);
    }

}
