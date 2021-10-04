package school.teacher;

import java.util.Collection;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.DataAccessException;
import org.springframework.data.repository.Repository;
import org.springframework.transaction.annotation.Transactional;

public interface TeacherRepository extends Repository<Teacher, Integer> {

	/**
	 * Retrieve all <code>Teacher</code>s from the data store.
	 * @return a <code>Collection</code> of <code>Teacher</code>s
	 */
	@Transactional(readOnly = true)
	@Cacheable("teachers")
	Collection<Teacher> findAll() throws DataAccessException;

}
