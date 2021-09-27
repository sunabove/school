package school.lesson;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;
import school.model.BaseEntity;

@Entity
@Table(name = "lesson")
@Data
public class Lesson extends BaseEntity {

	private static final long serialVersionUID = 5857476193395691627L;

	@Column(name = "visit_date")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate date;

	@NotEmpty
	@Column(name = "description")
	private String description;

	@Column(name = "subject_id")
	private Integer subjectId;

	/**
	 * Creates a new instance of Lesson for the current visitDate
	 */
	public Lesson() {
		this.date = LocalDate.now();
	} 

}
