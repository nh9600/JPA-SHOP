package jpabook.jpashop.service;

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

    public List<Item> findItems(){
        return itemRepository.findALL();
    }

    public Item findOne(Long itemId){
        return itemRepository.findOne(itemId);
    }
}
