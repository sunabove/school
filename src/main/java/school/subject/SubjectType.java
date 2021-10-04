package school.subject;

import javax.persistence.Entity;
import javax.persistence.Table;

import school.model.NamedEntity;

@Entity
@Table(name = "subject_type")
public class SubjectType extends NamedEntity {

	private static final long serialVersionUID = -1292475497535377996L;
	
	public SubjectType() {
	}

}
