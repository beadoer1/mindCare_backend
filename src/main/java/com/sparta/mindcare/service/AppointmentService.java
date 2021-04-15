package com.sparta.mindcare.service;

import com.sparta.mindcare.controllerReturn.AppointmentDateReturn;
import com.sparta.mindcare.controllerReturn.AppointmentTimeCheck;
import com.sparta.mindcare.model.Appointment;
import com.sparta.mindcare.model.Doctor;
import com.sparta.mindcare.repository.AppointmentRepository;
import com.sparta.mindcare.repository.DoctorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AppointmentService {

    private final DoctorRepository doctorRepository;
    private final AppointmentRepository appointmentRepository;
    // ※ 오늘 이전 날짜 선택 시 불가하다고 반환하는 로직 필요할 듯
    public AppointmentDateReturn getPossibleTime(Long doctorId, String requestDate){
        // 1. 사용자가 선택한 날짜에 상담사가 근무하는지 확인
        // 사용자가 선택한 날짜를 LocalDate 객체로 바꿔 요일 format으로 변경
        LocalDate appointmentDate = LocalDate.parse(requestDate);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("E").withLocale(Locale.forLanguageTag("ko"));
        String checkDayOfWeek = appointmentDate.format(formatter);

        // 상담사 정보를 가져와 근무 요일 List를 불러옴
        Doctor doctor;
        try {
            doctor = doctorRepository.findById(doctorId).orElseThrow(
                    () -> new IllegalArgumentException("해당 상담사의 정보가 존재하지 않습니다.")
            );
        } catch(IllegalArgumentException e){
            return new AppointmentDateReturn(false,null,e.getMessage());
        }

        List<String> daysOfWeek = doctor.getDaysOfWeek();

        // 상담사의 근무 요일 List에 선택한 날짜의 요일이 포함되어있는지 확인 후 없는 경우 false 반환
        if(!daysOfWeek.contains(checkDayOfWeek)){
            return new AppointmentDateReturn(false,null,"상담사가 근무하지 않는 날짜입니다. 근무일을 확인해주세요!");
        }

        // 2. 예약 현황에서 이미 예약된 시간에 대해 체크
        // doctor의 근무 시간을 불러옴
        Map<String,Long> workingTime = doctor.getWorkingTime();
        Long startTime = workingTime.get("startTime");
        Long endTime = workingTime.get("endTime");

        // 해당 일, 해당 상담사에 대해 사전에 예약된 List를 불러옴
        List<Appointment> alreadyAppointmentList = appointmentRepository.findAllByDateAndDoctorId(appointmentDate, doctorId);
        List<LocalTime> alreadyAppointmentTimeList = new ArrayList<>();
        for(Appointment alreadyAppointment : alreadyAppointmentList) {
            alreadyAppointmentTimeList.add(alreadyAppointment.getTime());
        }
        // 반환값에 넣을 시간대 리스트를 생성
        List<AppointmentTimeCheck> timeList = new ArrayList<>();

        // 기 예약 시간들을 시간대 리스트에 반영
        for(Long i = startTime; i < endTime; i++){
            AppointmentTimeCheck timecheck = new AppointmentTimeCheck(Math.toIntExact(i));
            timecheck.setPossibleAppointment(!alreadyAppointmentTimeList.contains(timecheck.getTime()));
            timeList.add(timecheck);
        }

        // 확인 결과 반환값 정리
        return new AppointmentDateReturn(true,timeList,"상담 가능한 시간을 반환합니다.");
    }
}
