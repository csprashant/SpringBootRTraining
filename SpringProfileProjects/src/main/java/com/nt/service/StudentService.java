package com.nt.service;

import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nt.convertor.StudentConvertor;
import com.nt.dto.StudentDto;
import com.nt.exception.StudentNotFoundException;
import com.nt.model.Student;
import com.nt.repo.StudentRepo;

@Service
public class StudentService {
	
	@Autowired
	private StudentRepo repo;

	public StudentDto updateStudent(Integer id,StudentDto studentDto) {
		Optional<Student> existingStudent=repo.findById(id);
		Student student=new StudentConvertor().dtoToModel(studentDto);
		if(existingStudent.isPresent()) {
			existingStudent.get().setName(student.getName());
			existingStudent.get().setPer(student.getPer());
			return new StudentConvertor().modelToDto(repo.save(existingStudent.get()));
			}
		else
			 throw new StudentNotFoundException("Student","ID",id);
	}
	
	public StudentDto saveStudent(StudentDto studentDto) {
		Student student=new StudentConvertor().dtoToModel(studentDto);
		Random random = new Random();  
		student.setId(random.nextInt(10000));
		return new StudentConvertor().modelToDto( repo.save(student));
	}

	public StudentDto getStudentDetailsById(Integer id) {
		 Optional<Student> student = repo.findById(id);
		if(student.isPresent()) 
			return new StudentConvertor().modelToDto( student.get());
		else 
			throw new StudentNotFoundException("Student","Id",id);
	}

	public List<Student> getAllRecord() {
		List<Student> listStudent = repo.findAll();
		if(listStudent.size()>0)
			return listStudent;
		else
			throw new StudentNotFoundException("!!!Sorry no records");
	}

	public String deleteStudentById(int id) {
		Optional<Student> existingStudent=repo.findById(id);
		if(existingStudent.isPresent()) {
			repo.delete(existingStudent.get());
			return "Record deleted";
		}
		else 
			throw new StudentNotFoundException("Student","Id",id);
	}
}
