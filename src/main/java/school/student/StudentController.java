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
import java.util.Optional;

@Controller
class StudentController {

	private static final String VIEWS_OWNER_CREATE_OR_UPDATE_FORM = "students/createOrUpdateStudentForm";

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
	public String processCreationForm(@Valid Student student, BindingResult result) {
		if (result.hasErrors()) {
			return VIEWS_OWNER_CREATE_OR_UPDATE_FORM;
		} else {
			this.students.save(student);
			return "redirect:/students/" + student.getId();
		}
	}

	@GetMapping("/students/find")
	public String initFindForm(Map<String, Object> model) {
		model.put("student", new Student());
		
		return "students/findStudents";
	}

	@GetMapping("/students")
	public String processFindForm(Student student, BindingResult result, Map<String, Object> model) {

		// allow parameterless GET request for /students to return all records
		if (student.getLastName() == null) {
			student.setLastName(""); // empty string signifies broadest possible search
		}

		// find students by last name
		Collection<Student> results = new java.util.ArrayList<>();
		
		if(student.getLastName().trim().length() < 1 ) {
			results = this.students.findAllOrderById();  
		} else {
			results = this.students.findByLastName(student.getLastName());
		}
		
		if (results.isEmpty()) {
			// no students found
			result.rejectValue("lastName", "notFound", "not found");
			
			return "students/findStudents";
		}
		else if (results.size() == 1) {
			// 1 owner found
			student = results.iterator().next();
			return "redirect:/students/" + student.getId();
		}
		else {
			// multiple students found
			model.put("selections", results);
			
			return "students/studentsList";
		}
	}

	@GetMapping("/students/{ownerId}/edit")
	public String initUpdateOwnerForm(@PathVariable("studentId") int studentId, Model model) {
		Optional<Student> student = this.students.findById(studentId);
		if( student.isPresent()) { 
			model.addAttribute(student.get());
		}
		return VIEWS_OWNER_CREATE_OR_UPDATE_FORM;
	}

	@PostMapping("/students/{studentId}/edit")
	public String processUpdateOwnerForm(@Valid Student student, BindingResult result,
			@PathVariable("studentId") int studentId) {
		if (result.hasErrors()) {
			return VIEWS_OWNER_CREATE_OR_UPDATE_FORM;
		}
		else {
			student.setId(studentId);
			this.students.save(student);
			return "redirect:/students/{studentId}";
		}
	}

	/**
	 * Custom handler for displaying an owner.
	 * @param studentId the ID of the owner to display
	 * @return a ModelMap with the model attributes for the view
	 */
	@GetMapping("/students/{studentId}")
	public ModelAndView showOwner(@PathVariable("studentId") int studentId) {
		ModelAndView mav = new ModelAndView("students/studentDetails");
		Optional<Student> student = this.students.findById(studentId);
		if( student.isPresent() ) { 
			for (Subject subject : student.get().getSubjects()) {
				subject.setLecturesInternal(lessons.findBySubjectId(subject.getId()));
			}
			mav.addObject(student.get());
		}
		
		return mav;
	}

}
