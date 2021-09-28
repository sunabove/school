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
import java.util.Collection;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

/**
 * Instructs Spring MVC on how to parse and print elements of type 'SubjectType'. Starting
 * from Spring 3.0, Formatters have come as an improvement in comparison to legacy
 * PropertyEditors. See the following links for more details: - The Spring ref doc:
 * https://docs.spring.io/spring-framework/docs/current/spring-framework-reference/core.html#format
 *
 * @author Mark Fisher
 * @author Juergen Hoeller
 * @author Michael Isvy
 */
@Component
public class SubjectTypeFormatter implements Formatter<SubjectType> {

	private final SubjectRepository subjects;

	@Autowired
	public SubjectTypeFormatter(SubjectRepository subjects) {
		this.subjects = subjects;
	}

	@Override
	public String print(SubjectType subjectType, Locale locale) {
		return subjectType.getName();
	}

	@Override
	public SubjectType parse(String text, Locale locale) throws ParseException {
		Collection<SubjectType> findSubjectTypes = this.subjects.findSubjectTypes();
		for (SubjectType subjectType : findSubjectTypes) {
			if (subjectType.getName().equals(text)) {
				return subjectType;
			}
		}
		throw new ParseException("type not found: " + text, 0);
	}

}
