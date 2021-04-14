package com.sparta.mindcare.controller;

import com.sparta.mindcare.controllerReturn.AppointmentDateReturn;
import com.sparta.mindcare.controllerReturn.AppointmentPhoneReturn;
import com.sparta.mindcare.model.Doctor;
import com.sparta.mindcare.repository.DoctorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class AppointmentController {

    private final DoctorRepository doctorRepository;

    @GetMapping("/api/appointments/phone/{id}")
    public AppointmentPhoneReturn getDoctorPhone(@PathVariable Long id){
        AppointmentPhoneReturn appointmentPhoneReturn = new AppointmentPhoneReturn();
        Doctor doctor = doctorRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("상담사 ID가 존재하지 않습니다.")
        );
        String phone = doctor.getPhone();

        appointmentPhoneReturn.setOk(true);
        appointmentPhoneReturn.setResults(phone);
        return appointmentPhoneReturn;
    }

    @PostMapping("/api/appointments/date/{doctorId}")
    public void getPossibleTime(@PathVariable Long doctorId, @RequestBody String requestDate){
        // 사용자가 선택한 날짜를 LocalDate 객체로 바꿔 요일 format으로 변경
        LocalDate appointmentDate = LocalDate.parse(requestDate);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("E").withLocale(Locale.forLanguageTag("ko"));
        String checkDayOfWeek = appointmentDate.format(formatter);

        // 상담사 정보를 가져와 근무 요일 List를 불러
        Doctor doctor = doctorRepository.findById(doctorId).orElseThrow(
                () -> new IllegalArgumentException("해당 상담사의 정보가 존재하지 않습니다.")
        );
        List<String> daysOfWeek = doctor.getDaysOfWeek();

        // 확인 결과를 반환하기 위한 객체 생성
        AppointmentDateReturn appointmentDateReturn = new AppointmentDateReturn();

        // 상담사의 근무 요일 List에 선택한 날짜의 요일이 포함되어있는지 확인
        if(!daysOfWeek.contains(checkDayOfWeek)){
            appointmentDateReturn.setOk(false);
            appointmentDateReturn.setMsg("근무하지 않는 날짜입니다. 근무일을 확인해주세요!");
        }

        // 예약 현황에서 이미 예약된 시간에 대해 체크하는 과정이 필요함



    }
}
