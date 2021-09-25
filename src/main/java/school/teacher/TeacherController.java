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
package school.teacher;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Controller
class TeacherController {

	private final TeacherRepository vets;

	public TeacherController(TeacherRepository clinicService) {
		this.vets = clinicService;
	}

	@GetMapping("/vets.html")
	public String showVetList(Map<String, Object> model) {
		// Here we are returning an object of type 'Teachers' rather than a collection of Teacher
		// objects so it is simpler for Object-Xml mapping
		Teachers vets = new Teachers();
		vets.getVetList().addAll(this.vets.findAll());
		model.put("vets", vets);
		return "vets/vetList";
	}

	@GetMapping({ "/vets" })
	public @ResponseBody Teachers showResourcesVetList() {
		// Here we are returning an object of type 'Teachers' rather than a collection of Teacher
		// objects so it is simpler for JSon/Object mapping
		Teachers vets = new Teachers();
		vets.getVetList().addAll(this.vets.findAll());
		return vets;
	}

}
