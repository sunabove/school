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

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import school.student.SubjectRepository;
import school.student.SubjectType;
import school.student.SubjectTypeFormatter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

/**
 * Test class for {@link SubjectTypeFormatter}
 *
 * @author Colin But
 */
@ExtendWith(MockitoExtension.class)
class SubjectTypeFormatterTests {

	@Mock
	private SubjectRepository pets;

	private SubjectTypeFormatter petTypeFormatter;

	@BeforeEach
	void setup() {
		this.petTypeFormatter = new SubjectTypeFormatter(pets);
	}

	@Test
	void testPrint() {
		SubjectType petType = new SubjectType();
		petType.setName("Hamster");
		String petTypeName = this.petTypeFormatter.print(petType, Locale.ENGLISH);
		assertThat(petTypeName).isEqualTo("Hamster");
	}

	@Test
	void shouldParse() throws ParseException {
		given(this.pets.findPetTypes()).willReturn(makePetTypes());
		SubjectType petType = petTypeFormatter.parse("Bird", Locale.ENGLISH);
		assertThat(petType.getName()).isEqualTo("Bird");
	}

	@Test
	void shouldThrowParseException() throws ParseException {
		given(this.pets.findPetTypes()).willReturn(makePetTypes());
		Assertions.assertThrows(ParseException.class, () -> {
			petTypeFormatter.parse("Fish", Locale.ENGLISH);
		});
	}

	/**
	 * Helper method to produce some sample pet types just for test purpose
	 * @return {@link Collection} of {@link SubjectType}
	 */
	private List<SubjectType> makePetTypes() {
		List<SubjectType> petTypes = new ArrayList<>();
		petTypes.add(new SubjectType() {
			{
				setName("Dog");
			}
		});
		petTypes.add(new SubjectType() {
			{
				setName("Bird");
			}
		});
		return petTypes;
	}

}
