package com.nt.repo;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.nt.model.Student;
@ExtendWith(SpringExtension.class)
@DataJpaTest
//@Profile({"dev","prod"})
@ActiveProfiles("test")
public class TestStudentRepo {
	
	@Autowired
	private StudentRepo repo;
	@Test
	public  void testSaveStduent() {
		Student saveStudent = repo.save(new Student(120,"opop",96.36));
        Optional<Student> returnstudent = repo.findById(120);
        assertTrue(returnstudent.isPresent());
        assertEquals(saveStudent,returnstudent.get());
	}
	

	@Test
	public void testGetAllStudent() {
		List<Student> listStudent=repo.findAll();
		assertTrue(!listStudent.isEmpty());
		assertEquals(listStudent.size(), 4);	
	}
	
	@Test
	public void testUpdateStudent() {
		 Optional<Student> toBeDeletedStudent = (repo.findById(1001));
		 repo.delete(toBeDeletedStudent.get());
		 Optional<Student> deletedStudent = (repo.findById(1001));
	     assertTrue(deletedStudent.isEmpty());  
	}
	@Test
	public void testDeleteStudent() {
		 Optional<Student> toBeUpdatedStudent = repo.findById(1001);
		 System.out.println(toBeUpdatedStudent);
	        toBeUpdatedStudent.get().setPer(100.00);
	        repo.save(toBeUpdatedStudent.get());
	        Optional<Student> updateStudent = repo.findById(1001);
	        assertEquals(100.00,updateStudent.get().getPer());
	}
}
