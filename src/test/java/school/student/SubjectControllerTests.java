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

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.test.web.servlet.MockMvc;

import school.student.Student;
import school.student.StudentRepository;
import school.student.Subject;
import school.student.SubjectController;
import school.student.SubjectRepository;
import school.student.SubjectType;
import school.student.SubjectTypeFormatter;

/**
 * Test class for the {@link SubjectController}
 *
 * @author Colin But
 */
@WebMvcTest(value = SubjectController.class,
		includeFilters = @ComponentScan.Filter(value = SubjectTypeFormatter.class, type = FilterType.ASSIGNABLE_TYPE))
class SubjectControllerTests {

	private static final int TEST_OWNER_ID = 1;

	private static final int TEST_PET_ID = 1;

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private SubjectRepository pets;

	@MockBean
	private StudentRepository owners;

	@BeforeEach
	void setup() {
		SubjectType cat = new SubjectType();
		cat.setId(3);
		cat.setName("hamster");
		given(this.pets.findSubjectTypes()).willReturn(Lists.newArrayList(cat));
		given(this.owners.findById(TEST_OWNER_ID)).willReturn(new Student());
		given(this.pets.findById(TEST_PET_ID)).willReturn(new Subject());

	}

	@Test
	void testInitCreationForm() throws Exception {
		mockMvc.perform(get("/studentRepository/{ownerId}/subjectRepository/new", TEST_OWNER_ID)).andExpect(status().isOk())
				.andExpect(view().name("subjectRepository/createOrUpdatePetForm")).andExpect(model().attributeExists("pet"));
	}

	@Test
	void testProcessCreationFormSuccess() throws Exception {
		mockMvc.perform(post("/studentRepository/{ownerId}/subjectRepository/new", TEST_OWNER_ID).param("name", "Betty")
				.param("type", "hamster").param("birthDate", "2015-02-12")).andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/studentRepository/{ownerId}"));
	}

	@Test
	void testProcessCreationFormHasErrors() throws Exception {
		mockMvc.perform(post("/studentRepository/{ownerId}/subjectRepository/new", TEST_OWNER_ID).param("name", "Betty").param("birthDate",
				"2015-02-12")).andExpect(model().attributeHasNoErrors("owner"))
				.andExpect(model().attributeHasErrors("pet")).andExpect(model().attributeHasFieldErrors("pet", "type"))
				.andExpect(model().attributeHasFieldErrorCode("pet", "type", "required")).andExpect(status().isOk())
				.andExpect(view().name("subjectRepository/createOrUpdatePetForm"));
	}

	@Test
	void testInitUpdateForm() throws Exception {
		mockMvc.perform(get("/studentRepository/{ownerId}/subjectRepository/{petId}/edit", TEST_OWNER_ID, TEST_PET_ID))
				.andExpect(status().isOk()).andExpect(model().attributeExists("pet"))
				.andExpect(view().name("subjectRepository/createOrUpdatePetForm"));
	}

	@Test
	void testProcessUpdateFormSuccess() throws Exception {
		mockMvc.perform(post("/studentRepository/{ownerId}/subjectRepository/{petId}/edit", TEST_OWNER_ID, TEST_PET_ID).param("name", "Betty")
				.param("type", "hamster").param("birthDate", "2015-02-12")).andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/studentRepository/{ownerId}"));
	}

	@Test
	void testProcessUpdateFormHasErrors() throws Exception {
		mockMvc.perform(post("/studentRepository/{ownerId}/subjectRepository/{petId}/edit", TEST_OWNER_ID, TEST_PET_ID).param("name", "Betty")
				.param("birthDate", "2015/02/12")).andExpect(model().attributeHasNoErrors("owner"))
				.andExpect(model().attributeHasErrors("pet")).andExpect(status().isOk())
				.andExpect(view().name("subjectRepository/createOrUpdatePetForm"));
	}

}
