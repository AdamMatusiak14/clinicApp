package ad.clinic.repository;  

import org.springframework.data.jpa.repository.JpaRepository;

import ad.clinic.model.Doctor;



public interface DoctorRepository extends JpaRepository<Doctor, Long> {
// package main.java.ad.clinic.repository;
    // This interface will automatically provide CRUD operations for Doctor entity
    // No additional methods are needed unless custom queries are require

    

    
}
