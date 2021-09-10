package com.nt.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class StudentDto implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Integer Id;
	
	private String name;

	private String per;
}
