package school.teacher;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Table;

import school.model.NamedEntity;

@Entity
@Table(name = "specialty")
public class Specialty extends NamedEntity implements Serializable {

	private static final long serialVersionUID = -8685670132517294071L;
	
	public Specialty() {
		
	}

}
