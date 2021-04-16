package loyalty.controller;

import static java.lang.invoke.MethodHandles.lookup;
import static org.slf4j.LoggerFactory.getLogger;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import loyalty.entity.Person;
import loyalty.service.PersonService;
import loyalty.utils.FileUploadUtils;

@Controller
public class PersonController {
	static final Logger log = getLogger(lookup().lookupClass());

	@Value("${upload-dir}")
	private String uploadDir;

	@Autowired
	PersonService personService;

	@InitBinder
	public void initBinder(WebDataBinder dataBinder) {
		StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
		dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
	}

	@RequestMapping("/")
	public String getALLPerson(Model model) {
		List<Person> persons = personService.getAllPerson();
		model.addAttribute("persons", persons);
		return "index";
	}

	@GetMapping(value = "/save")
	public ModelAndView createPersonPage() {
		return new ModelAndView("add");
	}

	@PostMapping("/save")
	public ModelAndView save(@Valid @ModelAttribute("person") Person person, BindingResult result,
			@RequestParam("image") MultipartFile file, ModelMap model) throws IOException {
		log.info("Post Save");
		
		ModelAndView modelAndView = new ModelAndView("redirect:/");

		if (result.hasErrors()) {
			model.addAttribute("person", person);
			modelAndView.setViewName("add");
			modelAndView.addAllObjects(model);
		} else {
			person.setIsDeleted(false);
			Person savePerson = personService.savePerson(person);

			if (!file.isEmpty()) {
				String fileName = savePerson.getId() + "_" + file.getOriginalFilename();
				savePerson.setPicture(fileName);
				personService.updatePerson(savePerson);
				FileUploadUtils.saveFile(uploadDir, fileName, file);
			}
		}

		return modelAndView;

	}

	@GetMapping(value = "/update/{id}")
	public ModelAndView update(ModelMap model,
			@PathVariable(value = "id") Integer id) {
		
		 Optional<Person> person = personService.findById(id);
		 if(person.isPresent()) {
			 model.addAttribute("person", person.get());
			return new ModelAndView("update", model);
		 } else {
			 return new ModelAndView("redirect:../");
		 }
	}
	
	@PostMapping(value = "/update")
	public ModelAndView update(@Valid @ModelAttribute("person") Person person,
			BindingResult result,
			@RequestParam("image") MultipartFile file, ModelMap model) throws IOException {
		
		ModelAndView modelAndView = new ModelAndView("redirect:/");

		if (result.hasErrors()) {
			
			model.addAttribute("person", person);
			modelAndView.setViewName("update");
			modelAndView.addAllObjects(model);
			
		} else {
			
			person.setIsDeleted(false);

			if (!file.isEmpty()) {
				String fileName = person.getId() + "_" + file.getOriginalFilename();
				person.setPicture(fileName);
				FileUploadUtils.saveFile(uploadDir, fileName, file);
			}

			personService.updatePerson(person);
			
		}

		return modelAndView;
		
	}
	
	@GetMapping(value = "/delete/{id}")
	public RedirectView delete(@PathVariable(value = "id", required = false) Integer id) {
		Optional<Person> person = personService.findById(id);
		if(person.isPresent()) {
			person.get().setIsDeleted(true);
			personService.updatePerson(person.get());
		}
		return new RedirectView("../");
		
	}

	@GetMapping(value = "/search")
	public ModelAndView search(ModelMap model, @RequestParam(value = "phone", required = false, defaultValue = "") String search) {

		if (search == null) {
			return new ModelAndView("redirect:/");
		}

		model.addAttribute("persons", personService.searchByPhone(search));
		model.addAttribute("search", search);

		return new ModelAndView("index", model);
	}

	@RequestMapping(value = "/image/{imageName}")
	@ResponseBody
	public byte[] getImage(@PathVariable(value = "imageName") String imageName) throws IOException {

		File serverFile = new File(uploadDir + imageName);

		if (serverFile.exists()) {
			return Files.readAllBytes(serverFile.toPath());
		}

		return null;

	}

	@ModelAttribute(value = "person")
	public Person defaultEntity() {
		return new Person();
	}

}
