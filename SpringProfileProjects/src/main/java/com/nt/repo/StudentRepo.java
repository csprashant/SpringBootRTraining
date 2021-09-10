package com.nt.repo;

import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

import com.nt.model.Student;
@Profile({"dev","prod"})
public interface StudentRepo extends JpaRepository<Student, Integer> {
}
