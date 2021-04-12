package com.sparta.mindcare.controller;

import com.sparta.mindcare.controllerReturn.AppointmentPhoneReturn;
import com.sparta.mindcare.model.Doctor;
import com.sparta.mindcare.repository.DoctorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AppointmentController {

    private DoctorRepository doctorRepository;

    @GetMapping("/api/appointments/phone/{id}")
    public AppointmentPhoneReturn getDoctorPhone(@PathVariable Long id){
        AppointmentPhoneReturn appointmentPhoneReturn = new AppointmentPhoneReturn();
        Doctor doctor = doctorRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("상담사 ID가 존재하지 않습니다.")
        );
        Long phone = doctor.getPhone();

        appointmentPhoneReturn.setOk(true);
        appointmentPhoneReturn.setResults(phone);
        return appointmentPhoneReturn;
    }
}
