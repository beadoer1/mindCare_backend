package com.sparta.mindcare.controller;

import com.sparta.mindcare.controllerReturn.AppointmentDateReturn;
import com.sparta.mindcare.controllerReturn.AppointmentPhoneReturn;
import com.sparta.mindcare.controllerReturn.AppointmentReturn;
import com.sparta.mindcare.controllerReturn.MsgReturn;
import com.sparta.mindcare.dto.AppointmentDto;
import com.sparta.mindcare.model.Appointment;
import com.sparta.mindcare.model.Doctor;
import com.sparta.mindcare.model.User;
import com.sparta.mindcare.repository.AppointmentRepository;
import com.sparta.mindcare.repository.DoctorRepository;
import com.sparta.mindcare.service.AppointmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class AppointmentController {

    private final DoctorRepository doctorRepository;
    private final AppointmentRepository appointmentRepository;
    private final AppointmentService appointmentService;

    // doctor 전화 예약을 위한 전화번호 반환
    @GetMapping("/api/appointments/{doctorId}/phone")
    public AppointmentPhoneReturn getDoctorPhone(@PathVariable Long doctorId){
        try {
            Doctor doctor = doctorRepository.findById(doctorId).orElseThrow(
                    () -> new IllegalArgumentException("상담사 ID가 존재하지 않습니다.")
            );
            String phone = doctor.getPhone();
            return new AppointmentPhoneReturn(true,phone,"연결 성공!");
        }catch(IllegalArgumentException e){
            return new AppointmentPhoneReturn(false,null,e.getMessage());
        }
    }

    @PostMapping("/api/appointments/{doctorId}")
    public MsgReturn createAppointment(@PathVariable Long doctorId, @AuthenticationPrincipal User user, @RequestBody Map<String,String> requestDateTime){
        try {
            Doctor doctor = doctorRepository.findById(doctorId).orElseThrow(
                    () -> new IllegalArgumentException("예약 요청한 상담사가 존재하지 않습니다.")
            );
            LocalDate date = LocalDate.parse(requestDateTime.get("date"));
            LocalTime time = LocalTime.parse(requestDateTime.get("time"));
            AppointmentDto requestDto = new AppointmentDto(user,doctor,date,time);
            Appointment appointment = new Appointment(requestDto);
            appointmentRepository.save(appointment);
            return new MsgReturn(true,"예약이 완료 되었습니다.");
        }catch(IllegalArgumentException e){
            return new MsgReturn(false,e.getMessage());
        }
    }

    // user가 예약한 예약현황 전부 return
    @GetMapping("/api/appointments")
    public AppointmentReturn getAppointment(@AuthenticationPrincipal User user) throws NullPointerException{
        List<Appointment> appointmentList = appointmentRepository.findAllByUserId(user.getId());
        if(appointmentList.size() == 0) {
            return new AppointmentReturn(false, appointmentList, "예약 정보가 존재하지 않습니다.");
        }
        return new AppointmentReturn(true, appointmentList, "검색 성공!");
    }

    // doctor 예약 가능 날짜, 시간 확인
    @PostMapping("/api/appointments/{doctorId}/date")
    public AppointmentDateReturn getPossibleTime(@PathVariable Long doctorId, @RequestBody String requestDate){ // "2021-04-14"
        return appointmentService.getPossibleTime(doctorId,requestDate);
    }
}
