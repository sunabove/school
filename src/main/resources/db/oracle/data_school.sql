INSERT INTO teacher(first_name, last_name) VALUES ( 'James', 'Carter');
INSERT INTO teacher(first_name, last_name) VALUES ( 'Helen', 'Leary');
INSERT INTO teacher(first_name, last_name) VALUES ( 'Linda', 'Douglas');
INSERT INTO teacher(first_name, last_name) VALUES ( 'Rafael', 'Ortega');
INSERT INTO teacher(first_name, last_name) VALUES ( 'Henry', 'Stevens');
INSERT INTO teacher(first_name, last_name) VALUES ( 'Sharon', 'Jenkins');

INSERT INTO specialty(name) VALUES ( 'radiology');
INSERT INTO specialty(name) VALUES ( 'surgery');
INSERT INTO specialty(name) VALUES ( 'dentistry');

INSERT INTO teacher_specialty(vet_id, specialty_id) VALUES (2, 1);
INSERT INTO teacher_specialty(vet_id, specialty_id) VALUES (3, 2);
INSERT INTO teacher_specialty(vet_id, specialty_id) VALUES (3, 3);
INSERT INTO teacher_specialty(vet_id, specialty_id) VALUES (4, 2);
INSERT INTO teacher_specialty(vet_id, specialty_id) VALUES (5, 1);

INSERT INTO subject_type(name) VALUES ( 'cat');
INSERT INTO subject_type(name) VALUES ( 'dog' );
INSERT INTO subject_type(name) VALUES ( 'lizard' );
INSERT INTO subject_type(name) VALUES ( 'snake' );
INSERT INTO subject_type(name) VALUES ( 'bird' );
INSERT INTO subject_type(name) VALUES ( 'hamster' );

INSERT INTO student(first_name, last_name, address, city, telephone) VALUES ( 'George', 'Franklin', '110 W. Liberty St.', 'Madison', '6085551023');
INSERT INTO student(first_name, last_name, address, city, telephone) VALUES ( 'Betty', 'Davis', '638 Cardinal Ave.', 'Sun Prairie', '6085551749');
INSERT INTO student(first_name, last_name, address, city, telephone) VALUES ( 'Eduardo', 'Rodriquez', '2693 Commerce St.', 'McFarland', '6085558763');
INSERT INTO student(first_name, last_name, address, city, telephone) VALUES ( 'Harold', 'Davis', '563 Friendly St.', 'Windsor', '6085553198');
INSERT INTO student(first_name, last_name, address, city, telephone) VALUES ( 'Peter', 'McTavish', '2387 S. Fair Way', 'Madison', '6085552765');
INSERT INTO student(first_name, last_name, address, city, telephone) VALUES ( 'Jean', 'Coleman', '105 N. Lake St.', 'Monona', '6085552654');
INSERT INTO student(first_name, last_name, address, city, telephone) VALUES ( 'Jeff', 'Black', '1450 Oak Blvd.', 'Monona', '6085555387');
INSERT INTO student(first_name, last_name, address, city, telephone) VALUES ( 'Maria', 'Escobito', '345 Maple St.', 'Madison', '6085557683');
INSERT INTO student(first_name, last_name, address, city, telephone) VALUES ( 'David', 'Schroeder', '2749 Blackhawk Trail', 'Madison', '6085559435');
INSERT INTO student(first_name, last_name, address, city, telephone) VALUES ( 'Carlos', 'Estaban', '2335 Independence La.', 'Waunakee', '6085555487');

INSERT INTO subject(name, birth_date, subject_type_id, student_id) VALUES ('Leo', TO_DATE('2010-09-07', 'YYYY-MM-DD'), 1, 1);
INSERT INTO subject(name, birth_date, subject_type_id, student_id) VALUES ('Basil', TO_DATE('2012-08-06', 'YYYY-MM-DD'), 6, 2);
INSERT INTO subject(name, birth_date, subject_type_id, student_id) VALUES ('Rosy', TO_DATE('2011-04-17', 'YYYY-MM-DD'), 2, 3);
INSERT INTO subject(name, birth_date, subject_type_id, student_id) VALUES ('Jewel', TO_DATE('2010-03-07', 'YYYY-MM-DD'), 2, 3);
INSERT INTO subject(name, birth_date, subject_type_id, student_id) VALUES ('Iggy', TO_DATE('2010-11-30', 'YYYY-MM-DD'), 3, 4);
INSERT INTO subject(name, birth_date, subject_type_id, student_id) VALUES ('George', TO_DATE('2010-01-20', 'YYYY-MM-DD'), 4, 5);
INSERT INTO subject(name, birth_date, subject_type_id, student_id) VALUES ('Samantha', TO_DATE('2012-09-04', 'YYYY-MM-DD'), 1, 6);
INSERT INTO subject(name, birth_date, subject_type_id, student_id) VALUES ('Max', TO_DATE('2012-09-04', 'YYYY-MM-DD'), 1, 6);
INSERT INTO subject(name, birth_date, subject_type_id, student_id) VALUES ('Lucky', TO_DATE('2011-08-06', 'YYYY-MM-DD'), 5, 7);
INSERT INTO subject(name, birth_date, subject_type_id, student_id) VALUES ('Mulligan', TO_DATE('2007-02-24', 'YYYY-MM-DD'), 2, 8);
INSERT INTO subject(name, birth_date, subject_type_id, student_id) VALUES ('Freddy', TO_DATE('2010-03-09', 'YYYY-MM-DD'), 5, 9);
INSERT INTO subject(name, birth_date, subject_type_id, student_id) VALUES ('Lucky', TO_DATE('2010-06-24', 'YYYY-MM-DD'), 2, 10);
INSERT INTO subject(name, birth_date, subject_type_id, student_id) VALUES ('Sly', TO_DATE('2012-06-08', 'YYYY-MM-DD'), 1, 10);

INSERT INTO lecture(subject_id, visit_date, description) VALUES ( 7, TO_DATE('2013-01-01', 'YYYY-MM-DD'), 'rabies shot');
INSERT INTO lecture(subject_id, visit_date, description) VALUES ( 8, TO_DATE('2013-01-02', 'YYYY-MM-DD'), 'rabies shot');
INSERT INTO lecture(subject_id, visit_date, description) VALUES ( 8, TO_DATE('2013-01-03', 'YYYY-MM-DD'), 'neutered');
INSERT INTO lecture(subject_id, visit_date, description) VALUES ( 7, TO_DATE('2013-01-04', 'YYYY-MM-DD'), 'spayed');

COMMIT ;
