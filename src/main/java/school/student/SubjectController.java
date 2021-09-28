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

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;

/**
 * @author Juergen Hoeller
 * @author Ken Krebs
 * @author Arjen Poutsma
 */
@Controller
@RequestMapping("/students/{ownerId}")
class SubjectController {

	private static final String VIEWS_PETS_CREATE_OR_UPDATE_FORM = "subjects/createOrUpdatePetForm";

	private final SubjectRepository subjects;

	private final StudentRepository students;

	public SubjectController(SubjectRepository subjects, StudentRepository students) {
		this.subjects = subjects;
		this.students = students;
	}

	@ModelAttribute("types")
	public Collection<SubjectType> populatePetTypes() {
		return this.subjects.findPetTypes();
	}

	@ModelAttribute("owner")
	public Student findOwner(@PathVariable("ownerId") int ownerId) {
		return this.students.findById(ownerId);
	}

	@InitBinder("owner")
	public void initOwnerBinder(WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}

	@InitBinder("pet")
	public void initPetBinder(WebDataBinder dataBinder) {
		dataBinder.setValidator(new SubjectValidator());
	}

	@GetMapping("/subjects/new")
	public String initCreationForm(Student student, ModelMap model) {
		Subject pet = new Subject();
		student.addSubject(pet);  
		model.put("pet", pet);
		return VIEWS_PETS_CREATE_OR_UPDATE_FORM;
	}

	@PostMapping("/subjects/new")
	public String processCreationForm(Student student, @Valid Subject subject, BindingResult result, ModelMap model) {
		if (StringUtils.hasLength(subject.getName()) && subject.isNew() && student.getSubject(subject.getName(), true) != null) {
			result.rejectValue("name", "duplicate", "already exists");
		}
		student.addSubject(subject);
		if (result.hasErrors()) {
			model.put("pet", subject);
			return VIEWS_PETS_CREATE_OR_UPDATE_FORM;
		}
		else {
			this.subjects.save(subject);
			return "redirect:/students/{ownerId}";
		}
	}

	@GetMapping("/subjects/{petId}/edit")
	public String initUpdateForm(@PathVariable("petId") int petId, ModelMap model) {
		Subject pet = this.subjects.findById(petId);
		model.put("pet", pet);
		return VIEWS_PETS_CREATE_OR_UPDATE_FORM;
	}

	@PostMapping("/subjects/{petId}/edit")
	public String processUpdateForm(@Valid Subject pet, BindingResult result, Student owner, ModelMap model) {
		if (result.hasErrors()) {
			pet.setStudent(owner);
			model.put("pet", pet);
			return VIEWS_PETS_CREATE_OR_UPDATE_FORM;
		}
		else {
			owner.addSubject(pet);
			this.subjects.save(pet);
			return "redirect:/students/{ownerId}";
		}
	}

}
