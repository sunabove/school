package school.student;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import school.lecture.LectureRepository;

import javax.validation.Valid;
import java.util.Collection;
import java.util.Map;

@Controller
class StudentController {

	private static final String VIEWS_OWNER_CREATE_OR_UPDATE_FORM = "students/createOrUpdateOwnerForm";

	private final StudentRepository students;

	private LectureRepository lessons;

	public StudentController(StudentRepository students, LectureRepository lessons) {
		this.students = students;
		this.lessons = lessons;
	}

	@InitBinder
	public void setAllowedFields(WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}

	@GetMapping("/students/new")
	public String initCreationForm(Map<String, Object> model) {
		Student owner = new Student();
		model.put("owner", owner);
		return VIEWS_OWNER_CREATE_OR_UPDATE_FORM;
	}

	@PostMapping("/students/new")
	public String processCreationForm(@Valid Student owner, BindingResult result) {
		if (result.hasErrors()) {
			return VIEWS_OWNER_CREATE_OR_UPDATE_FORM;
		}
		else {
			this.students.save(owner);
			return "redirect:/students/" + owner.getId();
		}
	}

	@GetMapping("/students/find")
	public String initFindForm(Map<String, Object> model) {
		model.put("student", new Student());
		return "students/findStudents";
	}

	@GetMapping("/students")
	public String processFindForm(Student owner, BindingResult result, Map<String, Object> model) {

		// allow parameterless GET request for /students to return all records
		if (owner.getLastName() == null) {
			owner.setLastName(""); // empty string signifies broadest possible search
		}

		// find students by last name
		Collection<Student> results = this.students.findByLastName(owner.getLastName());
		if (results.isEmpty()) {
			// no students found
			result.rejectValue("lastName", "notFound", "not found");
			return "students/findStudents";
		}
		else if (results.size() == 1) {
			// 1 owner found
			owner = results.iterator().next();
			return "redirect:/students/" + owner.getId();
		}
		else {
			// multiple students found
			model.put("selections", results);
			return "students/studentsList";
		}
	}

	@GetMapping("/students/{ownerId}/edit")
	public String initUpdateOwnerForm(@PathVariable("ownerId") int ownerId, Model model) {
		Student owner = this.students.findById(ownerId);
		model.addAttribute(owner);
		return VIEWS_OWNER_CREATE_OR_UPDATE_FORM;
	}

	@PostMapping("/students/{ownerId}/edit")
	public String processUpdateOwnerForm(@Valid Student owner, BindingResult result,
			@PathVariable("ownerId") int ownerId) {
		if (result.hasErrors()) {
			return VIEWS_OWNER_CREATE_OR_UPDATE_FORM;
		}
		else {
			owner.setId(ownerId);
			this.students.save(owner);
			return "redirect:/students/{ownerId}";
		}
	}

	/**
	 * Custom handler for displaying an owner.
	 * @param ownerId the ID of the owner to display
	 * @return a ModelMap with the model attributes for the view
	 */
	@GetMapping("/students/{ownerId}")
	public ModelAndView showOwner(@PathVariable("ownerId") int ownerId) {
		ModelAndView mav = new ModelAndView("students/ownerDetails");
		Student student = this.students.findById(ownerId);
		for (Subject subject : student.getPets()) {
			subject.setVisitsInternal(lessons.findBySubjectId(subject.getId()));
		}
		mav.addObject(student);
		return mav;
	}

}
