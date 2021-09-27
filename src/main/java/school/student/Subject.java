/*
 * Copyright 2012-2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package school.student;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.beans.support.MutableSortDefinition;
import org.springframework.beans.support.PropertyComparator;
import org.springframework.format.annotation.DateTimeFormat;

import school.lesson.Lecture;
import school.model.NamedEntity;

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
	@JoinColumn(name = "owner_id")
	private Student owner;

	@Transient
	private Set<Lecture> visits = new LinkedHashSet<>();

	public void setBirthDate(LocalDate birthDate) {
		this.birthDate = birthDate;
	}

	public LocalDate getBirthDate() {
		return this.birthDate;
	}

	public SubjectType getSubjectType() {
		return this.subjectType;
	}

	public void setSubjectType(SubjectType type) {
		this.subjectType = type;
	}

	public Student getOwner() {
		return this.owner;
	}

	protected void setOwner(Student owner) {
		this.owner = owner;
	}

	protected Set<Lecture> getVisitsInternal() {
		if (this.visits == null) {
			this.visits = new HashSet<>();
		}
		return this.visits;
	}

	public void setVisitsInternal(Collection<Lecture> visits) {
		this.visits = new LinkedHashSet<>(visits);
	}

	public List<Lecture> getVisits() {
		List<Lecture> sortedVisits = new ArrayList<>(getVisitsInternal());
		PropertyComparator.sort(sortedVisits, new MutableSortDefinition("date", false, false));
		return Collections.unmodifiableList(sortedVisits);
	}

	public void addVisit(Lecture lesson) {
		getVisitsInternal().add(lesson);
		lesson.setSubjectId(this.getId());
	}

}