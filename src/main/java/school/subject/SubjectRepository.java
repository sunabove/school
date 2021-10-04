package school.subject;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.transaction.annotation.Transactional;

public interface SubjectRepository extends Repository<Subject, Integer> {

	/**
	 * Retrieve all {@link SubjectType}s from the data store.
	 * @return a Collection of {@link SubjectType}s.
	 */
	@Query("SELECT ptype FROM SubjectType ptype ORDER BY ptype.name")
	@Transactional(readOnly = true)
	List<SubjectType> findSubjectTypes();

	/**
	 * Retrieve a {@link Subject} from the data store by id.
	 * @param id the id to search for
	 * @return the {@link Subject} if found
	 */
	@Transactional(readOnly = true)
	Subject findById(Integer id);

	/**
	 * Save a {@link Subject} to the data store, either inserting or updating it.
	 * @param pet the {@link Subject} to save
	 */
	void save(Subject pet);

}
