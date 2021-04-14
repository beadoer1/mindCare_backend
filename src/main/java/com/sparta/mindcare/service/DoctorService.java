package com.sparta.mindcare.service;

import com.sparta.mindcare.dto.DoctorDto;
import com.sparta.mindcare.model.Doctor;
import com.sparta.mindcare.repository.DoctorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class DoctorService {

    private final DoctorRepository doctorRepository;

    @Transactional
    public void update(Long id, DoctorDto requestDto){
        Doctor doctor = doctorRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("해당 Id의 상담사가 존재하지 않습니다.")
        );
        doctor.update(requestDto);
    }


}
