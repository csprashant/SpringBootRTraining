package com.nt.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nt.dto.StudentDto;
import com.nt.model.Student;
import com.nt.service.StudentService;

@RestController
@RequestMapping("/students")
public class StudentController {
	
	@Autowired
	private StudentService studentService;
	
	/**
	 * Endpoint for displaying single student details
	 * 
	 * @param id  represents the unique id
	 * @return ResponseEntity having Student details
	 * @throws Exception
	 */
	
	@GetMapping("/{id}")
	public ResponseEntity<StudentDto> getStudentDetailsById(@PathVariable Integer id) throws Exception{
		return new ResponseEntity<>(studentService.getStudentDetailsById(id),HttpStatus.OK);			
	}
	
	/** 
	 *  Endpoint for displaying all students details 
	 * @return ResponseEntity having All Student details
	 */
	
	@GetMapping("/")
	public ResponseEntity<List<Student>> getAllStudents(){
		return new ResponseEntity<List<Student>>(studentService.getAllRecord(),HttpStatus.OK);
	}
	
	/**
	 * 	Endpoint for updating Student details
	 * @param id	represents the unique id
	 * @param studentDto represents modified StudentDto Object
	 * @return ResponseEntity having updated Student details
	 */
	
	@PutMapping("/{id}")
	public ResponseEntity<StudentDto> updateStudent(@PathVariable Integer id,  @Valid @RequestBody StudentDto studentDto){
		return new ResponseEntity<>(studentService.updateStudent(id,studentDto),HttpStatus.OK);
	}
	
	/**
	 * Endpoint for deleting Student details
	 * @param id	represents the unique id
	 * @return	String with success message
	 */
	
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteStudentById(@PathVariable Integer id){
		return  new ResponseEntity<String>(studentService.deleteStudentById(id),HttpStatus.OK);
	}
	/**
	 * Endpoint for saving new Student 
	 * @param studentDto represents StudentDto Object to persist
	 * @return  ResponseEntity having saved Student details
	 */
	@PostMapping("/")
	public ResponseEntity<StudentDto> saveStudent(@Valid @RequestBody StudentDto studentDto){
			return new ResponseEntity<>(studentService.saveStudent(studentDto),HttpStatus.CREATED);	
	}
	
}
