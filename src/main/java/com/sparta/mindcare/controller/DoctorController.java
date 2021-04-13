package com.sparta.mindcare.controller;

import com.sparta.mindcare.controllerReturn.DoctorDetailReturn;
import com.sparta.mindcare.controllerReturn.DoctorReturn;
import com.sparta.mindcare.model.Doctor;
import com.sparta.mindcare.repository.DoctorRepository;
import com.sparta.mindcare.service.DoctorService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class DoctorController {

    private final DoctorRepository doctorRepository;
    private final DoctorService doctorService;

    @GetMapping("/api/doctors")
    public DoctorReturn getDoctors(){
        DoctorReturn doctorReturn = new DoctorReturn();
        List<Doctor> doctors = doctorRepository.findAll();
        doctorReturn.setOk(true);
        doctorReturn.setResults(doctors);
        return doctorReturn;
    }

    @GetMapping("/api/doctors/{id}")
    public DoctorDetailReturn getDoctorDetail(@PathVariable Long id){
        DoctorDetailReturn doctorDetailReturn = new DoctorDetailReturn();
        Doctor doctor = doctorRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("상담사 ID가 존재하지 않습니다.")
        );
        doctorDetailReturn.setOk(true);
        doctorDetailReturn.setResults(doctor);
        return doctorDetailReturn;
    }

    @PostMapping("/api/doctors")
    public void createDoctor(@RequestBody Doctor requestDoctor){
        doctorRepository.save(requestDoctor);
    }
}
