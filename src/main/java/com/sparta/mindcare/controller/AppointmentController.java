package com.sparta.mindcare.controller;

import com.sparta.mindcare.controllerReturn.ResultReturn;
import com.sparta.mindcare.model.Appointment;
import com.sparta.mindcare.model.Doctor;
import com.sparta.mindcare.model.User;
import com.sparta.mindcare.repository.AppointmentRepository;
import com.sparta.mindcare.repository.DoctorRepository;
import com.sparta.mindcare.service.AppointmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

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
    public ResultReturn getDoctorPhone(@PathVariable Long doctorId){
        try {
            Doctor doctor = doctorRepository.findById(doctorId).orElseThrow(
                    () -> new IllegalArgumentException("상담사 ID가 존재하지 않습니다.")
            );
            String phone = doctor.getPhone();
            return new ResultReturn(true,phone,"연결 성공!");
        }catch(IllegalArgumentException e){
            return new ResultReturn(false,null,e.getMessage());
        }
    }

    // 예약하기
    @PostMapping("/api/appointments/{doctorId}")
    public ResultReturn createAppointment(@PathVariable Long doctorId, @AuthenticationPrincipal User user, @RequestBody Map<String,String> requestDateTime){
        try {
            appointmentService.createAppointment(doctorId,user,requestDateTime);
            return new ResultReturn(true,"예약이 완료 되었습니다.");
        }catch(IllegalArgumentException e){
            return new ResultReturn(false,e.getMessage());
        }
    }

    // user가 예약한 예약현황 전부 return
    @GetMapping("/api/appointments")
    public ResultReturn getAppointment(@AuthenticationPrincipal User user){
        List<Appointment> appointmentList = appointmentRepository.findAllByUserId(user.getId(), Sort.by("date").ascending().and(Sort.by("time").ascending()));
        if(appointmentList.size() == 0) {
            return new ResultReturn(false, appointmentList, "예약 정보가 존재하지 않습니다.");
        }
        return new ResultReturn(true, appointmentList, "검색 성공!");
    }

    // doctor 예약 가능 날짜, 시간 확인
    @PostMapping("/api/appointments/{doctorId}/date")
    public ResultReturn getPossibleTime(@PathVariable Long doctorId, @RequestBody Map<String,String> requestDate, @AuthenticationPrincipal User user){ // "2021-04-14"
        if(user == null){
            return new ResultReturn(false,null,"로그인이 필요한 기능입니다!");
        }

        return appointmentService.getPossibleTime(doctorId,requestDate);
    }

    @DeleteMapping("/api/appointments/{appointmentId}")
    public ResultReturn deleteAppointment(@PathVariable Long appointmentId, @AuthenticationPrincipal User user){
        if(user == null){
            return new ResultReturn(false,"로그인이 필요한 서비스입니다.");
        }
        try {
            Appointment appointment = appointmentRepository.findById(appointmentId).orElseThrow(
                    () -> new IllegalArgumentException("해당 후기가 존재하지 않습니다.")
            );
            if(!appointment.getUserId().equals(user.getId())){
                return new ResultReturn(false,"삭제는 작성자 본인만 가능합니다.");
            }
        } catch(IllegalArgumentException e){
            return new ResultReturn(false, e.getMessage());
        }
        appointmentRepository.deleteById(appointmentId);
        return new ResultReturn(true, "예약이 취소 되었습니다.");
    }
}
