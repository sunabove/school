package school.teacher;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Simple domain object representing a list of veterinarians. Mostly here to be used for
 * the 'teachers' {@link org.springframework.web.servlet.view.xml.MarshallingView}.
 *
 * @author Arjen Poutsma
 */
@XmlRootElement
public class Teachers {

	private List<Teacher> teachers;

	@XmlElement
	public List<Teacher> getTeacherList() {
		if (teachers == null) {
			teachers = new ArrayList<>();
		}
		return teachers;
	}

}
