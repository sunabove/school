package school.teacher;

import java.util.Collection;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.DataAccessException;
import org.springframework.data.repository.Repository;
import org.springframework.transaction.annotation.Transactional;

public interface TeacherRepository extends Repository<Teacher, Integer> {

	@Transactional(readOnly = true)
	@Cacheable("teachers")
	Collection<Teacher> findAll() throws DataAccessException;

}
