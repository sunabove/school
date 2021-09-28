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
package school.lecture;

import java.util.Map;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import school.student.Subject;
import school.student.SubjectRepository;

@Controller
public class LectureController {

	private final LectureRepository lessons;

	private final SubjectRepository subjects;

	public LectureController(LectureRepository lessons, SubjectRepository subjects) {
		this.lessons = lessons;
		this.subjects = subjects;
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
	@ModelAttribute("visit")
	public Lecture loadPetWithVisit(@PathVariable("petId") int petId, Map<String, Object> model) {
		Subject pet = this.subjects.findById(petId);
		pet.setLecturesInternal(this.lessons.findBySubjectId(petId));
		model.put("pet", pet);
		Lecture visit = new Lecture();
		pet.addLecture(visit);
		return visit;
	}

	// Spring MVC calls method loadPetWithVisit(...) before initNewVisitForm is called
	@GetMapping("/studentRepository/*/subjects/{petId}/lessons/new")
	public String initNewVisitForm(@PathVariable("petId") int petId, Map<String, Object> model) {
		return "subjects/createOrUpdateVisitForm";
	}

	// Spring MVC calls method loadPetWithVisit(...) before processNewVisitForm is called
	@PostMapping("/studentRepository/{ownerId}/subjects/{petId}/lessons/new")
	public String processNewVisitForm(@Valid Lecture visit, BindingResult result) {
		if (result.hasErrors()) {
			return "subjects/createOrUpdateVisitForm";
		}
		else {
			this.lessons.save(visit);
			return "redirect:/studentRepository/{ownerId}";
		}
	}

}