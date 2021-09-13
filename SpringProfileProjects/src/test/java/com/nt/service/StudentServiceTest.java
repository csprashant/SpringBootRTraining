package com.nt.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import com.nt.dto.StudentDto;
import com.nt.exception.StudentNotFoundException;
import com.nt.model.Student;
import com.nt.repo.StudentRepo;

@SpringBootTest
@ActiveProfiles("test")
public class StudentServiceTest {
	
	@Autowired
	private StudentService service;
	
	@MockBean
	private StudentRepo repo;

	
	@Test
	public void  testGetStudentDetailsByIdWithException() throws Exception{
		Mockito.when(repo.findById(Mockito.anyInt())).thenThrow(StudentNotFoundException.class);
		assertThrows(StudentNotFoundException.class,()->service.getStudentDetailsById(1));
		
	}
	
	@Test
	public void testsaveStduent() {
		Mockito.when(repo.save(Mockito.any())).thenReturn(new Student(1,"Raj",85.25));
		StudentDto sdto=service.saveStudent(new StudentDto(1,"Raj","85.25"));
		assertEquals("Raj", sdto.getName());
	}
		
	@Test
	public void testUpdateStudent() {
		Student student=new Student(1,"Raj",85.25 );
		student.setId(102);
		student.setName("OM");
		Mockito.when(repo.save(Mockito.any())).thenReturn(student);
		
	}
	
	@Test
	public void testGetAllRecord() {
		var listStudent=List.of(new Student(1,"Raj",96.36),
								new Student(2,"Ramesh",97.36),
								new Student(3,"Shubash",86.36),
								new Student(4,"Kuldeep",66.36));
		Mockito.when(repo.findAll()).thenReturn(listStudent);
		assertThat(service.getAllRecord()).isEqualTo(listStudent);
	}
	
	@Test
	public void  testGetAllRecordwithException() throws Exception{
		Mockito.when(repo.findAll()).thenThrow(StudentNotFoundException.class);
		assertThrows(StudentNotFoundException.class,()->service.getAllRecord());
	}
	
	@Test 
	public void testDeleteStudentById() {
		Student student=new Student(1,"Raj",96.36);
		Mockito.when(repo.findById(1)).thenReturn(Optional.of(student));
		Mockito.when(repo.existsById(student.getId())).thenReturn(false);
		assertEquals(service.deleteStudentById(1),"Record deleted");
	}
		
	@Test
	public void testGetStudentDetailsById() {
		Student student=new Student(1,"Raj",96.36);
		Mockito.when(repo.findById(Mockito.anyInt())).thenReturn(Optional.of(student));
		StudentDto sdto=service.getStudentDetailsById(1);
		assertEquals(sdto.getName(), student.getName());
		assertEquals(Double.parseDouble(sdto.getPer()), student.getPer());
	}
	
	@Test
	public void  testGetStudentDetailsByIdwithException() throws Exception{
		Mockito.when(repo.findById(36)).thenThrow(StudentNotFoundException.class);
		assertThrows(StudentNotFoundException.class,()->service.getStudentDetailsById(36));
	}
	
	@Test
	public void testUpdateStudentWithException() {
		Mockito.when(repo.findById(36)).thenThrow(StudentNotFoundException.class);
		assertThrows(StudentNotFoundException.class,()->service.getStudentDetailsById(36));;
		
	}

}
