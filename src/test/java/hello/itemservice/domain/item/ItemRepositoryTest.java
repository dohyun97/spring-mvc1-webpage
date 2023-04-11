package hello.itemservice.domain.item;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class ItemRepositoryTest {

    private ItemRepository itemRepository = new ItemRepository();

    @AfterEach
    void tearDown() {
        itemRepository.clearStore();
    }

    @Test
    void save() {
        //given
        Item item = new Item("ItemA",200,12);
        //when
        Item saveItem = itemRepository.save(item);
        //Then
        Item findItem = itemRepository.findById(item.getId());
        assertThat(findItem).isEqualTo(saveItem);
    }


    @Test
    void findAll() {
        //given
        Item item1 = new Item("ItemA",200,12);
        Item item2 = new Item("ItemB",100,4);

        Item saveItem1 = itemRepository.save(item1);
        Item saveItem2 = itemRepository.save(item2);
        //when
        List<Item> result = itemRepository.findAll();
        //then
        assertThat(result.size()).isEqualTo(2);
        assertThat(result).contains(item1,item2);
    }

    @Test
    void update() {
        //given
        Item item = new Item("ItemA",200,12);
        Item saveItem = itemRepository.save(item);
        Long itemId = saveItem.getId();
        Item updateParam = new Item("ItemB", 100, 3);
        //when
        itemRepository.update(itemId,updateParam);
        //then
        Item findItem = itemRepository.findById(itemId);
        assertThat(findItem.getItemName()).isEqualTo(updateParam.getItemName());
        assertThat(findItem.getPrice()).isEqualTo(updateParam.getPrice());
        assertThat(findItem.getQuantity()).isEqualTo(updateParam.getQuantity());

    }


}