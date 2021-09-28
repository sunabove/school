package school.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import school.lecture.Lecture;
import school.lecture.LectureRepository;
import school.student.Student;
import school.student.StudentRepository;
import school.student.Subject;
import school.student.SubjectRepository;
import school.student.SubjectType;
import school.teacher.Teacher;
import school.teacher.TeacherRepository;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
class ClinicServiceTests {

	@Autowired
	protected StudentRepository studentRepository;

	@Autowired
	protected SubjectRepository subjectRepository;

	@Autowired
	protected LectureRepository lectureRepository;

	@Autowired
	protected TeacherRepository teacherRepository;

	@Test
	void shouldFindOwnersByLastName() {
		Collection<Student> students = this.studentRepository.findByLastName("Davis");
		assertThat(students).hasSize(2);

		students = this.studentRepository.findByLastName("Daviss");
		assertThat(students).isEmpty();
	}

	@Test
	void shouldFindSingleOwnerWithPet() {
		Student student = this.studentRepository.findById(1);
		assertThat(student.getLastName()).startsWith("Franklin");
		assertThat(student.getSubjects()).hasSize(1);
		assertThat(student.getSubjects().get(0).getSubjectType()).isNotNull();
		assertThat(student.getSubjects().get(0).getSubjectType().getName()).isEqualTo("cat");
	}

	@Test
	@Transactional
	void shouldInsertOwner() {
		Collection<Student> students = this.studentRepository.findByLastName("Schultz");
		int found = students.size();

		Student student = new Student();
		student.setFirstName("Sam");
		student.setLastName("Schultz");
		student.setAddress("4, Evans Street");
		student.setCity("Wollongong");
		student.setTelephone("4444444444");
		this.studentRepository.save(student);
		assertThat(student.getId().longValue()).isNotEqualTo(0);

		students = this.studentRepository.findByLastName("Schultz");
		assertThat(students.size()).isEqualTo(found + 1);
	}

	@Test
	@Transactional
	void shouldUpdateOwner() {
		Student student = this.studentRepository.findById(1);
		String oldLastName = student.getLastName();
		String newLastName = oldLastName + "X";

		student.setLastName(newLastName);
		this.studentRepository.save(student);

		// retrieving new name from database
		student = this.studentRepository.findById(1);
		assertThat(student.getLastName()).isEqualTo(newLastName);
	}

	@Test
	void shouldFindPetWithCorrectId() {
		Subject subject7 = this.subjectRepository.findById(7);
		assertThat(subject7.getName()).startsWith("Samantha");
		assertThat(subject7.getStudent().getFirstName()).isEqualTo("Jean");

	}

	@Test
	void shouldFindAllPetTypes() {
		Collection<SubjectType> petTypes = this.subjectRepository.findPetTypes();

		SubjectType petType1 = EntityUtils.getById(petTypes, SubjectType.class, 1);
		assertThat(petType1.getName()).isEqualTo("cat");
		SubjectType petType4 = EntityUtils.getById(petTypes, SubjectType.class, 4);
		assertThat(petType4.getName()).isEqualTo("snake");
	}

	@Test
	@Transactional
	void shouldInsertPetIntoDatabaseAndGenerateId() {
		Student owner6 = this.studentRepository.findById(6);
		int found = owner6.getSubjects().size();

		Subject pet = new Subject();
		pet.setName("bowser");
		Collection<SubjectType> types = this.subjectRepository.findPetTypes();
		pet.setSubjectType(EntityUtils.getById(types, SubjectType.class, 2));
		pet.setBirthDate(LocalDate.now());
		owner6.addSubject(pet);
		assertThat(owner6.getSubjects().size()).isEqualTo(found + 1);

		this.subjectRepository.save(pet);
		this.studentRepository.save(owner6);

		owner6 = this.studentRepository.findById(6);
		assertThat(owner6.getSubjects().size()).isEqualTo(found + 1);
		// checks that id has been generated
		assertThat(pet.getId()).isNotNull();
	}

	@Test
	@Transactional
	void shouldUpdatePetName() throws Exception {
		Subject pet7 = this.subjectRepository.findById(7);
		String oldName = pet7.getName();

		String newName = oldName + "X";
		pet7.setName(newName);
		this.subjectRepository.save(pet7);

		pet7 = this.subjectRepository.findById(7);
		assertThat(pet7.getName()).isEqualTo(newName);
	}

	@Test
	void shouldFindVets() {
		Collection<Teacher> vets = this.teacherRepository.findAll();

		Teacher vet = EntityUtils.getById(vets, Teacher.class, 3);
		assertThat(vet.getLastName()).isEqualTo("Douglas");
		assertThat(vet.getNrOfSpecialties()).isEqualTo(2);
		assertThat(vet.getSpecialties().get(0).getName()).isEqualTo("dentistry");
		assertThat(vet.getSpecialties().get(1).getName()).isEqualTo("surgery");
	}

	@Test
	@Transactional
	void shouldAddNewVisitForPet() {
		Subject pet7 = this.subjectRepository.findById(7);
		int found = pet7.getLectures().size();
		Lecture visit = new Lecture();
		pet7.addLecture(visit);
		visit.setDescription("test");
		this.lectureRepository.save(visit);
		this.subjectRepository.save(pet7);

		pet7 = this.subjectRepository.findById(7);
		assertThat(pet7.getLectures().size()).isEqualTo(found + 1);
		assertThat(visit.getId()).isNotNull();
	}

	@Test
	void shouldFindVisitsByPetId() throws Exception {
		Collection<Lecture> visits = this.lectureRepository.findBySubjectId(7);
		assertThat(visits).hasSize(2);
		Lecture[] visitArr = visits.toArray(new Lecture[visits.size()]);
		assertThat(visitArr[0].getDate()).isNotNull();
		assertThat(visitArr[0].getSubjectId()).isEqualTo(7);
	}

}
