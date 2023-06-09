package hello.itemservice.web.item.basic;

import hello.itemservice.domain.item.Item;
import hello.itemservice.domain.item.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.PostConstruct;
import java.util.List;

@Controller
@RequestMapping("/basic/items")
@RequiredArgsConstructor  //@Autowired 의존성 주입
public class BasicItemController {
    private final ItemRepository itemRepository;

    @GetMapping
    public String items(Model model){
        List<Item> items = itemRepository.findAll();
        model.addAttribute("items",items);
        return "basic/items";
    }

    @GetMapping("/{itemId}")
    public String item(Model model, @PathVariable Long itemId){
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item",item);
        return "basic/item";
    }

    @GetMapping("/add")
    public String addForm(){
        return "basic/addForm";
    }

//    @PostMapping("/add")
//    public String addItemV1(@RequestParam String itemName,
//                            @RequestParam Integer price,
//                            @RequestParam Integer quantity,
//                            Model model){
//        Item item =new Item(itemName,price,quantity);
//        itemRepository.save(item);
//        model.addAttribute("item",item);
//        return "basic/item";
//    }

      //model,@RequestParam,Item객체 생성 생략
//    @PostMapping("/add")
//    public String addItemV2(@ModelAttribute("item")Item item){
//        itemRepository.save(item);
//        return "basic/item";
//    }

     //modelAttribute 이름 생략. Model 이름은 클래스 이름(Item)을 소문자로 바꾼것. item
//    @PostMapping("/add")
//    public String addItemV3(@ModelAttribute Item item){
//        itemRepository.save(item);
//        return "redirect:/basic/items/"+item.getId(); //인코딩으로 URL 작성이 안돼. {itemId}
//    }

     //modelAttribute 생략
//    @PostMapping("/add")
//    public String addItemV4(Item item){
//        itemRepository.save(item);
//        return "redirect:/basic/items/"+item.getId(); //인코딩으로 URL 작성이 안돼. {itemId}
//    }

    //Redirect 인코딩 으로 작성할 수 있게. 클라이언트에게 등록 후 "등록완료" 메세지 보내기
    @PostMapping("/add")
    public String addItemV5(@ModelAttribute Item item, RedirectAttributes redirectAttributes){
        itemRepository.save(item);
        redirectAttributes.addAttribute("itemId",item.getId());
        redirectAttributes.addAttribute("status",true); //status가 true면 등록완료 메세지 띄우기 - ?status=true
        return "redirect:/basic/items/{itemId}";  //url: http://localhost:8080/basic/items/3?status=true
    }

    @GetMapping("/{itemId}/edit")
    public String editForm(@PathVariable Long itemId, Model model){
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item",item);
        return "basic/editForm";
    }

    @PostMapping("/{itemId}/edit")
    public String edit(@PathVariable Long itemId,@ModelAttribute Item item){
        itemRepository.update(itemId,item);
        return "redirect:/basic/items/{itemId}"; //pathvariable 값은 인코딩 가능.{itemId}
    }

    /**
     * 테스트용 데이터 추가
     * 초기화 용도: 의존관계주입후 값을 설정
     */
    @PostConstruct
    public void init(){
        itemRepository.save(new Item("testA",10000,10));
        itemRepository.save(new Item("testB",20000,20));
    }


}
