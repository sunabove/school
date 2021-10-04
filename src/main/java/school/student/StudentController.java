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
import school.subject.Subject;

import javax.validation.Valid;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;

@Controller
class StudentController {

	private static final String VIEWS_STUDENT_CREATE_OR_UPDATE_FORM = "student/createOrUpdateStudentForm";

	private final StudentRepository studentRepository;

	private LectureRepository lessonRespository;

	public StudentController(StudentRepository studentRepository, LectureRepository lessonRespository) {
		this.studentRepository = studentRepository;
		this.lessonRespository = lessonRespository;
	}

	@InitBinder
	public void setAllowedFields(WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}

	@GetMapping("/student/new")
	public String initCreationForm(Map<String, Object> model) {
		Student owner = new Student();
		model.put("student", owner);
		
		return VIEWS_STUDENT_CREATE_OR_UPDATE_FORM;
	}

	@PostMapping("/student/new")
	public String processCreationForm(@Valid Student student, BindingResult result) {
		if (result.hasErrors()) {
			return VIEWS_STUDENT_CREATE_OR_UPDATE_FORM;
		} else {
			this.studentRepository.save(student);
			return "redirect:/student/" + student.getId();
		}
	}

	@GetMapping("/student/find")
	public String initFindForm(Map<String, Object> model) {
		model.put("student", new Student());
		
		return "student/findStudent";
	}

	@GetMapping("/student")
	public String processFindForm(Student student, BindingResult result, Map<String, Object> model) {

		// allow parameterless GET request for /studentRepository to return all records
		if (student.getLastName() == null) {
			student.setLastName(""); // empty string signifies broadest possible search
		}

		// find studentRepository by last name
		Collection<Student> results = this.studentRepository.findByLastName(student.getLastName());
		
		if (results.isEmpty()) {
			// no studentRepository found
			result.rejectValue("lastName", "notFound", "not found");
			
			return "student/findStudent";
		}
		else if (results.size() == 1) {
			// 1 owner found
			student = results.iterator().next();
			return "redirect:/student/" + student.getId();
		}
		else {
			// multiple studentRepository found
			model.put("selections", results);
			
			return "student/studentList";
		}
	}

	@GetMapping("/student/{studentId}/edit")
	public String initUpdateOwnerForm(@PathVariable("studentId") int studentId, Model model) {
		Optional<Student> student = this.studentRepository.findById(studentId);
		if( student.isPresent()) { 
			model.addAttribute(student.get());
		}
		
		return VIEWS_STUDENT_CREATE_OR_UPDATE_FORM;
	}

	@PostMapping("/student/{studentId}/edit")
	public String processUpdateOwnerForm(@Valid Student student, BindingResult result,
			@PathVariable("studentId") int studentId) {
		if (result.hasErrors()) {
			return VIEWS_STUDENT_CREATE_OR_UPDATE_FORM;
		} else {
			student.setId(studentId);
			this.studentRepository.save(student);
			
			return "redirect:/student/{studentId}";
		}
	}

	/**
	 * Custom handler for displaying an owner.
	 * @param studentId the ID of the owner to display
	 * @return a ModelMap with the model attributes for the view
	 */
	@GetMapping("/student/{studentId}")
	public ModelAndView showOwner(@PathVariable("studentId") int studentId) {
		ModelAndView mav = new ModelAndView("student/studentDetail.html");
		
		Optional<Student> student = this.studentRepository.findById(studentId);
		
		if( student.isPresent() ) { 
			for (Subject subject : student.get().getSubjects()) {
				subject.setLecturesInternal(lessonRespository.findBySubjectId(subject.getId()));
			}
			mav.addObject(student.get());
		}
		
		return mav;
	}

}
