package school.lecture;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.data.repository.Repository;

public interface LectureRepository extends Repository<Lecture, Integer> {

	void save(Lecture visit) throws DataAccessException;

	List<Lecture> findBySubjectId(Integer subjectId);

}
