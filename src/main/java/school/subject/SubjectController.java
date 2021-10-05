package school.subject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import school.student.Student;
import school.student.StudentRepository;

import javax.validation.Valid;
import java.util.Collection;

@Controller
@RequestMapping("/student/{studentId}")
public class SubjectController {

	private static final String VIEWS_SUBJECTS_CREATE_OR_UPDATE_FORM = "subject/createOrUpdateSubjectForm";

	@Autowired
	private SubjectRepository subjectRepository;
	@Autowired
	private StudentRepository studentRepository;
	
	public SubjectController() {		
	}

	@ModelAttribute("subjectTypes")
	public Collection<SubjectType> populateSubjectTypes() {
		return this.subjectRepository.findSubjectTypes();
	}

	@ModelAttribute("student")
	public Student findOwner(@PathVariable("studentId") int studentId) {
		var opStudent = this.studentRepository.findById(studentId);
		
		if( opStudent.isEmpty()) {
			return null;
		} else {
			return opStudent.get();
		}
	}

	@InitBinder("student")
	public void initOwnerBinder(WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}

	@InitBinder("subject")
	public void initPetBinder(WebDataBinder dataBinder) {
		dataBinder.setValidator(new SubjectValidator());
	}

	@GetMapping("/subject/new")
	public String initCreationForm(Student student, ModelMap model) {
		Subject subject = new Subject();
		student.addSubject(subject);
		
		model.put("subject", subject);
		model.put("subjectTypes", this.populateSubjectTypes()); 
		
		return VIEWS_SUBJECTS_CREATE_OR_UPDATE_FORM;
	}

	@PostMapping("/subject/new")
	public String processCreationForm(Student student, @Valid Subject subject, BindingResult result, ModelMap model) {
		if (StringUtils.hasLength(subject.getName()) && subject.isNew() && student.getSubject(subject.getName(), true) != null) {
			result.rejectValue("name", "duplicate", "already exists");
		}
		
		student.addSubject(subject);
		
		if (result.hasErrors()) {
			model.put("subject", subject);
			
			return VIEWS_SUBJECTS_CREATE_OR_UPDATE_FORM;
		} else {
			this.subjectRepository.save(subject);
			
			return "redirect:/student/{studentId}";
		}
	}

	@GetMapping("/subject/{subjectId}/edit")
	public String initUpdateForm(@PathVariable("subjectId") int subjectId, ModelMap model) {
		Subject subject = this.subjectRepository.findById(subjectId);
		model.put("subject", subject);
		
		return VIEWS_SUBJECTS_CREATE_OR_UPDATE_FORM;
	}

	@PostMapping("/subject/{subjectId}/edit")
	public String processUpdateForm(@Valid Subject subject, BindingResult result, Student student, ModelMap model) {
		if (result.hasErrors()) {
			subject.setStudent(student);
			model.put("subject", subject);
			
			return VIEWS_SUBJECTS_CREATE_OR_UPDATE_FORM;
		} else {
			student.addSubject(subject);
			this.subjectRepository.save(subject);
			
			return "redirect:/student/{studentId}";
		}
	}

}
