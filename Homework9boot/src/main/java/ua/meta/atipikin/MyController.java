package ua.meta.atipikin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MyController {
	List<Peach> peachList;

	@Autowired
	private PeachRepository peachRepository;
	
	@GetMapping("/") // показ сторінки з кноками, але без даних
	public String getFruit() {
		return "index";
	}
	@GetMapping("/upload") // завантаження даних з бази
	private String upload(Model model) {
		Iterable<Peach> peachVarieties = peachRepository.findAll(); // завантажені усі об'єкти у вигляді iterable
		peachList = Streamable.of(peachVarieties).toList(); // перетворення iterable на звичний список
		model.addAttribute("peachList", peachList);
		return "index";
	}
	@GetMapping("/delete") // видалення обраних об'єктів зі сторінки
	private String delete(@RequestParam(value = "checkedItems", required = false) List<String> checkedItems, Model model) {
		List<Peach> peachList1 = new ArrayList<>(); // новий список, де будуть залишені тільки невибрані сорти
		
		if(checkedItems != null) { // в цих двох циклах помічаємо вибрані сорти
			for(int i = 0; i < peachList.size(); i++) {
				for(int j = 0; j < checkedItems.size(); j++) {
					if(peachList.get(i).getName().equals(checkedItems.get(j))) {
						peachList.get(i).setSelected(true); // ставимо мітку, якщо назва сорту присутня у списку чекбоксів
					}
				}
			}
		}
		for(int i = 0; i < peachList.size(); i++) { // у новий список копіюємо тільки невибрані сорти
			if(peachList.get(i).getSelected() == false) {
				peachList1.add(peachList.get(i));
			}
		}
		model.addAttribute("peachList", peachList1); // виводимо на сторінку новий список
		return "index";
	}
}