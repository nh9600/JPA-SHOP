package jpabook.jpashop.service;

import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemService { //간단히 위임만 하는 로직

    private final ItemRepository itemRepository;
    
    @Transactional //오버라이드
    public void savaItem(Item item){
        itemRepository.save(item);
    }

    //merge를 쓰지 않고 변경 감지 사용 `
    //엔티티를 변경할 때는 항상 변경 감지를 사용하세요
    @Transactional
    public void updateItem(Long itemId, String name, int price, int stockQuantity ){
        Item findItem = itemRepository.findOne(itemId);
        findItem.setName(name);
        findItem.setPrice(price);
        findItem.setStockQuantity(stockQuantity);
    }

    public List<Item> findItems(){
        return itemRepository.findALL();
    }

    public Item findOne(Long itemId){
        return itemRepository.findOne(itemId);
    }
}
