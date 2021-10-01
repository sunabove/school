package school.student;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface StudentRepository extends PagingAndSortingRepository<Student, Integer> {

	/**
	 * Retrieve {@link Student}s from the data store by last name, returning all studentRepository
	 * whose last name <i>starts</i> with the given name.
	 * @param lastName Value to search for
	 * @return a Collection of matching {@link Student}s (or an empty Collection if none
	 * found)
	 */
	@Query("SELECT DISTINCT student FROM Student student left join fetch student.subjects WHERE student.lastName LIKE :lastName%")
	@Transactional(readOnly = true)
	Collection<Student> findByLastName(@Param("lastName") String lastName);
	
	Collection<Student> findAllByOrderById(); 

}
