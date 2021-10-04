package school.subject;

import java.text.ParseException;
import java.util.Collection;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

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
