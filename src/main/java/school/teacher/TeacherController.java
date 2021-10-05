package school.teacher;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Controller
class TeacherController {

	@Autowired
	private TeacherRepository teacherRepository;

	public TeacherController() {
	}

	@GetMapping("/teacher.html")
	public String showVetList(Map<String, Object> model) {
		// Here we are returning an object of type 'Teachers' rather than a collection of Teacher
		// objects so it is simpler for Object-Xml mapping
		Teachers teachers = new Teachers();
		teachers.getTeacherList().addAll(this.teacherRepository.findAll());
		
		model.put("teachers", teachers);
		
		return "teacher/teacherList";
	}

	@GetMapping( { "/teacher" } )
	public @ResponseBody Teachers showResourcesVetList() {
		// Here we are returning an object of type 'Teachers' rather than a collection of Teacher
		// objects so it is simpler for JSon/Object mapping
		Teachers teachers = new Teachers();
		teachers.getTeacherList().addAll(this.teacherRepository.findAll());
		
		return teachers;
	}

}
