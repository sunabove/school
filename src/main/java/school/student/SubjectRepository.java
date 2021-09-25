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
	List<SubjectType> findPetTypes();

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
