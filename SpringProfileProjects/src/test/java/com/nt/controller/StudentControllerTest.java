package com.nt.controller;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Profile;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.operation.preprocess.Preprocessors;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nt.dto.StudentDto;
import com.nt.exception.StudentNotFoundException;
import com.nt.model.Student;
import com.nt.service.StudentService;



@ExtendWith({ RestDocumentationExtension.class,SpringExtension.class})
@WebMvcTest(controllers = StudentController.class)
@AutoConfigureRestDocs(outputDir ="target/generated-snippet" )
//@Profile({"dev","prod","test"})
@ActiveProfiles("test")
public class StudentControllerTest {

	private MockMvc mockMvc;
	
	@MockBean
	private StudentService studentService;
	
	private StudentDto studentDto;
	
	@BeforeEach
	public void setUp(WebApplicationContext webApplicationContext,RestDocumentationContextProvider restDocumentationContextProvider) {
		this.mockMvc=MockMvcBuilders.webAppContextSetup(webApplicationContext).apply(documentationConfiguration(restDocumentationContextProvider)).build();
		studentDto=new StudentDto(1,"Raj","95.24");	
	}
	
	
	@Test
	public void getStudentDetailsByIDTest() throws Exception{
		BDDMockito.given(studentService.getStudentDetailsById(Mockito.anyInt())).willReturn(studentDto);
		//mockMvc.perform(MockMvcRequestBuilders.get("/students/id").param("id", "1"))
		// why param becuase all request mapping in controler class  ("students/{id}") are similer so it confunsed to pick which handler mehtod it should pick so
		//we have go for @RequestParam in the controller class mehtod 
		mockMvc.perform(MockMvcRequestBuilders.get("/students/1"))
						.andExpect(status().isOk())
						.andExpect(jsonPath("$").isMap())
						.andExpect(jsonPath("name").value("Raj"))
						.andExpect(jsonPath("per").value(95.24));
	}
	
	@Test
	public void  StudentNotFoundHttpStatus() throws Exception{
		BDDMockito.given(studentService.getStudentDetailsById(Mockito.anyInt())).willThrow(new StudentNotFoundException("Student not found"));
		mockMvc.perform(MockMvcRequestBuilders.get("/students/12"))
		.andExpect(status().isNotFound());
	}
	
	@Test
	public void testSaveStduent() throws Exception{
	
		BDDMockito.given(studentService.saveStudent(Mockito.any())).willReturn(studentDto);
		String jsonString=new ObjectMapper().writeValueAsString(studentDto);
		mockMvc.perform(MockMvcRequestBuilders.post("/students/")
						.content(jsonString)
						.accept("application/json")
						.contentType("application/json")).andDo(print())
						.andExpect(status().isCreated())
						.andExpect(MockMvcResultMatchers.content().json(jsonString))
						.andDo(document("{methodName}",Preprocessors.preprocessRequest(Preprocessors.prettyPrint()),Preprocessors.preprocessResponse(Preprocessors.prettyPrint())));
	}
	@Test
	public void updateStudentTest() throws Exception{
		studentDto.setName("Raj sinha");
		 BDDMockito.given(studentService.updateStudent(Mockito.anyInt(),Mockito.any())).willReturn(studentDto);
	     mockMvc.perform(MockMvcRequestBuilders.put("/students/1")
	        			.content(new ObjectMapper().writeValueAsString(new StudentDto(1, "Raj","96.36")))
	        			.contentType("application/json")
	        			.accept("application/json")).andDo(print())
	                	.andExpect(status().isOk())
	                	.andExpect(jsonPath("$").isMap())
	                	.andDo(document("{methodName}",Preprocessors.preprocessRequest(Preprocessors.prettyPrint()),Preprocessors.preprocessResponse(Preprocessors.prettyPrint())));
	    }
	
	@Test
	public void getAllStudentTest() throws Exception {
		List <Student> listStudent=List.of(
						new Student(1,"Raj",96.36),
						new Student(2,"Ramesh",97.36),
						new Student(3,"Shubash",86.36),
						new Student(4,"Kuldeep",66.36));
		BDDMockito.given(studentService.getAllRecord()).willReturn(listStudent);
		mockMvc.perform(MockMvcRequestBuilders.get("/students/"))
		                .andExpect(status().isOk())
						.andExpect(jsonPath("$").isArray())
						.andDo(print());
		
	}
	@Test
	public void testDeleteStudentByIdTest() throws Exception{
		BDDMockito.given(studentService.deleteStudentById(Mockito.anyInt())).willReturn("Success");
		mockMvc.perform(MockMvcRequestBuilders.delete("/students/1")
						.contentType(MediaType.APPLICATION_JSON))
						.andExpect(status().isOk())
						.andDo(print());	
	}
	
	@Test
	public void testSaveStudentWithException() throws Exception{
		studentDto=new StudentDto();	
		studentDto.setId(1);
		mockMvc.perform(MockMvcRequestBuilders.post("/students/")
						.content(new ObjectMapper().writeValueAsString(studentDto))
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON))
						.andExpect(status().isBadRequest())
						.andDo(print());
	}
	
	@Test
	public void testUpdateStudentWithException() throws Exception{
		studentDto=new StudentDto();	
		studentDto.setId(1);
		 //BDDMockito.given(studentService.updateStudent(Mockito.anyInt(),Mockito.any())).willThrow(new StudentNotFoundException("Student not found"));
	     mockMvc.perform(MockMvcRequestBuilders.put("/students/1")
	        			.content(new ObjectMapper().writeValueAsString(new StudentDto(1, "","")))
	        			.contentType(MediaType.APPLICATION_JSON)
	        			.accept(MediaType.APPLICATION_JSON))
	                	.andExpect(status().isBadRequest());	                	
	                	
	}
	
	@Test
	public void testDeleteStudentByIdWithException() throws Exception{
		BDDMockito.given(studentService.deleteStudentById(Mockito.anyInt())).willThrow(new StudentNotFoundException("Student not found"));
		mockMvc.perform(MockMvcRequestBuilders.delete("/students/195")
						.contentType(MediaType.APPLICATION_JSON))
						.andExpect(status().isNotFound());					
	}
	
	@Test
	public void testGetStudentDetailsByIDwithException() throws Exception{
		BDDMockito.given(studentService.getStudentDetailsById(Mockito.anyInt())).willThrow(new StudentNotFoundException("Student not found"));
		mockMvc.perform(MockMvcRequestBuilders.get("/students/1"))
						.andExpect(status().isNotFound());		
	}
	
	@Test
	public void getAllStudentTestWithException() throws Exception {
		BDDMockito.given(studentService.getAllRecord()).willThrow(new StudentNotFoundException("!!!Sorry no record"));
		mockMvc.perform(MockMvcRequestBuilders.get("/students/"))
		                .andExpect(status().isNotFound());	
	}	
	
	@AfterEach
	public void clear() {
		studentDto=null;
	}
}


	


