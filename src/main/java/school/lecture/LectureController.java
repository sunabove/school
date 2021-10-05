package school.lecture;

import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import school.subject.Subject;
import school.subject.SubjectRepository;

@Controller
public class LectureController {

	@Autowired
	private LectureRepository lectureRepository;

	@Autowired
	private SubjectRepository subjectRepository;

	public LectureController() {
	}

	@InitBinder
	public void setAllowedFields(WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}

	/**
	 * Called before each and every @RequestMapping annotated method. 2 goals: - Make sure
	 * we always have fresh data - Since we do not use the session scope, make sure that
	 * Subject object always has an id (Even though id is not part of the form fields)
	 * @param petId
	 * @return Subject
	 */
	@ModelAttribute("lecture")
	public Lecture loadPetWithVisit(@PathVariable("subjectId") int subjectId, Map<String, Object> model) {
		Subject subject = this.subjectRepository.findById(subjectId);
		subject.setLecturesInternal(this.lectureRepository.findBySubjectId(subjectId));
		Lecture lecture = new Lecture();
		subject.addLecture(lecture);
		
		model.put("subject", subject);
		
		return lecture;
	}

	// Spring MVC calls method loadPetWithVisit(...) before initNewVisitForm is called
	@GetMapping("/student/*/subject/{subjectId}/lecture/new")
	public String initNewVisitForm(@PathVariable("subjectId") int subjectId, Map<String, Object> model) {
		return "subject/createOrUpdateLectureForm";
	}

	// Spring MVC calls method loadPetWithVisit(...) before processNewVisitForm is called
	@PostMapping("/student/{studentId}/subject/{subjectId}/lecture/new")
	public String processNewVisitForm(@Valid Lecture lecture, BindingResult result) {
		if (result.hasErrors()) {
			return "subject/createOrUpdateLectureForm";
		}
		else {
			this.lectureRepository.save(lecture);
			return "redirect:/student/{studentId}";
		}
	}

}
