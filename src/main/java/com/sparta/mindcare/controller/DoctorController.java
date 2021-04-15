package com.sparta.mindcare.controller;

import com.sparta.mindcare.controllerReturn.DoctorDetailReturn;
import com.sparta.mindcare.controllerReturn.DoctorReturn;
import com.sparta.mindcare.dto.DoctorDto;
import com.sparta.mindcare.model.Doctor;
import com.sparta.mindcare.repository.DoctorRepository;
import com.sparta.mindcare.service.DoctorService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class DoctorController {

    private final DoctorRepository doctorRepository;
    private final DoctorService doctorService;

    //상담사 전체
    @GetMapping("/api/doctors")
    public DoctorReturn getDoctors(){
        DoctorReturn doctorReturn = new DoctorReturn();
        List<Doctor> doctors = doctorRepository.findAll();
        doctorReturn.setOk(true);
        doctorReturn.setResults(doctors);
        return doctorReturn;
    }

    @PostMapping("/api/doctors/categories") // Category(Specialty) 별 상담사 List 불러오기
    public DoctorReturn getDoctorsCategories(@RequestBody List<String> requestCategories){
        DoctorReturn doctorReturn = new DoctorReturn();
        List<Doctor> returnList = doctorRepository.findAllBySpecialties(requestCategories.get(0));
        for(String category : requestCategories){
            List<Doctor> doctorListCategory = doctorRepository.findAllBySpecialties(category);
            returnList.retainAll(doctorListCategory);
        }
        doctorReturn.setOk(true);
        doctorReturn.setResults(returnList);
        return doctorReturn;
    }

    //상담사 상세
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

    //상담사 추가
    @PostMapping("/api/doctors")
    public void createDoctor(@RequestBody Doctor requestDoctor){
        doctorRepository.save(requestDoctor);
    }

    //상담사 리스트 추가
    @PostMapping("/api/doctors/all")
    public void createDoctor(@RequestBody List<DoctorDto> requestDtoList){
        for(DoctorDto docDto : requestDtoList){
            Doctor doctor = new Doctor(docDto);
            doctorRepository.save(doctor);
        }
    }

    //상담사 리스트 추가
    @PutMapping("/api/doctors/all")
    @Transactional
    public void updateDoctor(@RequestBody List<DoctorDto> requestDtoList){
        for(DoctorDto docDto : requestDtoList){
                Long id = docDto.getId();
                doctorService.update(id,docDto);
        }
    }

    @DeleteMapping("/api/doctors/{id}")
    public void deleteDoctor(@PathVariable Long id){
        doctorRepository.deleteById(id);
    }

}
