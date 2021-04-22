package com.sparta.mindcare.util;

import com.sparta.mindcare.service.AppointmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ScheduledTasks {
    // 매 시간마다 예약 상태를 업데이트 시켜주기 위한 Scheduling Tasks 클래스
    private final AppointmentService appointmentService;

    @Scheduled(cron = "1 0 * * * *")
    public void reportCurrentTime(){
        appointmentService.updateIsCompleted();
    }
}
