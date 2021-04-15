package com.sparta.mindcare.controller;

import com.sparta.mindcare.controllerReturn.AppointmentDateReturn;
import com.sparta.mindcare.controllerReturn.AppointmentPhoneReturn;
import com.sparta.mindcare.controllerReturn.AppointmentTimeCheck;
import com.sparta.mindcare.model.Appointment;
import com.sparta.mindcare.model.Doctor;
import com.sparta.mindcare.model.User;
import com.sparta.mindcare.repository.AppointmentRepository;
import com.sparta.mindcare.repository.DoctorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Locale;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class AppointmentController {

    private final DoctorRepository doctorRepository;
    private final AppointmentRepository appointmentRepository;

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
    public AppointmentDateReturn getPossibleTime(@PathVariable Long doctorId, @RequestBody String requestDate){ // "2021-04-14"
        // 사용자가 선택한 날짜에 상담사가 근무하는지 확인 필요함
        // 사용자가 선택한 날짜를 LocalDate 객체로 바꿔 요일 format으로 변경
        LocalDate appointmentDate = LocalDate.parse(requestDate);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("E").withLocale(Locale.forLanguageTag("ko"));
        String checkDayOfWeek = appointmentDate.format(formatter);

        // 상담사 정보를 가져와 근무 요일 List를 불러옴
        Doctor doctor = doctorRepository.findById(doctorId).orElseThrow(
                () -> new IllegalArgumentException("해당 상담사의 정보가 존재하지 않습니다.")
        );
        List<String> daysOfWeek = doctor.getDaysOfWeek();

        // 확인 결과를 반환하기 위한 객체 생성
        AppointmentDateReturn appointmentDateReturn = new AppointmentDateReturn();

        // 상담사의 근무 요일 List에 선택한 날짜의 요일이 포함되어있는지 확인 후 없는 경우 false 반환
        if(!daysOfWeek.contains(checkDayOfWeek)){
            appointmentDateReturn.setOk(false);
            appointmentDateReturn.setMsg("근무하지 않는 날짜입니다. 근무일을 확인해주세요!");
            return appointmentDateReturn;
        }

        // 예약 현황에서 이미 예약된 시간에 대해 체크하는 과정이 필요함
        // doctor의 근무 시간을 불러옴
        Map<String,Long> workingTime = doctor.getWorkingTime();
        Long startTime = workingTime.get("startTime");
        Long endTime = workingTime.get("endTime");

        // 해당일에 사전에 예약된 List를 불러옴 // Doctor로도 거르는 작업 필요
//        List<Appointment> alreadyAppointmentList = appointmentRepository.findAllByDate(appointmentDate);
//        List<LocalTime> alreadyAppointmentTimeList = new ArrayList<>();
//        for(Appointment alreadyAppointment : alreadyAppointmentList) {
//            alreadyAppointmentTimeList.add(alreadyAppointment.getTime());
//        }
        // 반환값에 넣을 시간대 리스트를 생성
        List<AppointmentTimeCheck> timeList = new ArrayList<>();

        // 기 예약 시간들을 시간대 리스트에 반영
        for(Long i = startTime; i < endTime; i++){
            AppointmentTimeCheck timecheck = new AppointmentTimeCheck(Math.toIntExact(i));
//            timecheck.setPossibleAppointment(!alreadyAppointmentTimeList.contains(timecheck.getTime()));
            timecheck.setPossibleAppointment(true);
            timeList.add(timecheck);
        }

        // 확인 결과 반환값 정리
        appointmentDateReturn.setOk(true);
        appointmentDateReturn.setResults(timeList);
        appointmentDateReturn.setMsg("확인 성공!");
        return appointmentDateReturn;
    }
}
