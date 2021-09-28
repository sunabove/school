package school.lecture;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import org.springframework.format.annotation.DateTimeFormat;

import school.model.BaseEntity;

@Entity
@Table(name = "lecture")
public class Lecture extends BaseEntity {

	private static final long serialVersionUID = 5857476193395691627L;

	@Column(name = "visit_date")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate date;

	@NotEmpty
	@Column(name = "description")
	private String description;

	@Column(name = "subject_id")
	private Integer subjectId;

	public Lecture() {
		this.date = LocalDate.now();
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getSubjectId() {
		return subjectId;
	}

	public void setSubjectId(Integer subjectId) {
		this.subjectId = subjectId;
	} 

}
