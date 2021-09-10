package com.nt.convertor;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;

import com.nt.dto.StudentDto;
import com.nt.model.Student;




public class StudentConvertor {
	public StudentDto modelToDto(Student student) {
		return new ModelMapper().map(student, StudentDto.class);
	}
	
	public List<StudentDto> modelToDto(List<Student> listsStudent) {
		return listsStudent.stream().map(x->modelToDto(x)).collect(Collectors.toList());
	}
	
	public List<Student> dtoToModel(List<StudentDto> listStudentDto) {
		return listStudentDto.stream().map(x->dtoToModel(x)).collect(Collectors.toList());
	}
	
	public Student dtoToModel(StudentDto studentDto) {
		return new ModelMapper().map(studentDto,Student.class);
	}

}
