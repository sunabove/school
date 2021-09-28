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
	//@Query("SELECT DISTINCT owner FROM Student owner left join fetch owner.pets WHERE owner.lastName LIKE :lastName%")
	//@Transactional(readOnly = true)
	Collection<Student> findByLastName(@Param("lastName") String lastName);
	
	Collection<Student> findAllOrderById(); 

}
