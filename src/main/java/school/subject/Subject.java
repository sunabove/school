package school.subject;

import java.time.LocalDate;
import java.util.*;

import javax.persistence.*; 

import org.springframework.beans.support.MutableSortDefinition;
import org.springframework.beans.support.PropertyComparator;
import org.springframework.format.annotation.DateTimeFormat;

import school.lecture.Lecture;
import school.model.NamedEntity;
import school.student.Student;

@Entity
@Table(name = "subject")
public class Subject extends NamedEntity {

	private static final long serialVersionUID = -1293065869305310710L;

	@Column(name = "birth_date")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate birthDate;

	@ManyToOne
	@JoinColumn(name = "subject_type_id")
	private SubjectType subjectType;

	@ManyToOne
	@JoinColumn(name = "student_id")
	private Student student;

	@Transient
	private Set<Lecture> lectures = new LinkedHashSet<>();

	public void setBirthDate(LocalDate birthDate) {
		this.birthDate = birthDate;
	}

	public LocalDate getBirthDate() {
		return this.birthDate;
	}

	public SubjectType getSubjectType() {
		return this.subjectType;
	}

	public void setSubjectType(SubjectType subjectType) {
		this.subjectType = subjectType;
	}

	public Student getStudent() {
		return this.student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	public Set<Lecture> getLecturesInternal() {
		if (this.lectures == null) {
			this.lectures = new HashSet<>();
		}
		
		return this.lectures;
	}

	public void setLecturesInternal(Collection<Lecture> lectures) {
		this.lectures = new LinkedHashSet<>(lectures);
	}

	public List<Lecture> getLectures() {
		List<Lecture> sortedLectures = new ArrayList<>(getLecturesInternal());
		PropertyComparator.sort(sortedLectures, new MutableSortDefinition("date", false, false));
		
		return Collections.unmodifiableList(sortedLectures);
	}

	public void addLecture(Lecture lecture) {
		getLecturesInternal().add(lecture);
		lecture.setSubjectId(this.getId());
	}

}
