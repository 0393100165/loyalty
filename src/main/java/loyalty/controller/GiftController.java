package loyalty.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import loyalty.entity.Gift;
import loyalty.service.GiftService;

@Controller
@RequestMapping("/gift")
public class GiftController {

	@Autowired
	private GiftService giftService;

	@GetMapping(value = "/")
	public ModelAndView index(ModelMap modelMap) {
		List<Gift> fetchAll = giftService.fetchAll();
		modelMap.addAttribute("gifts", fetchAll);
		return new ModelAndView("gift", modelMap);
	}
	
	@PostMapping(value = "/")
	public RedirectView randomAndSave(RedirectAttributes redirect,
			@RequestParam(value = "code") String code) {
		
		Gift gift = giftService.randomAndSave(code.trim());
		redirect.addFlashAttribute("savedGift", gift);
		
		return new RedirectView("/gift/");
	}

}
