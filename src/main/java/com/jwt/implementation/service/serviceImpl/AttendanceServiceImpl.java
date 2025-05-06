package com.jwt.implementation.service.serviceImpl;

import com.jwt.implementation.model.Attendance;
import com.jwt.implementation.repository.AttendanceRepository;
import com.jwt.implementation.service.AttendanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class AttendanceServiceImpl implements AttendanceService {

    @Autowired
    private AttendanceRepository attendanceRepository;

    @Override
    public Attendance markAttendance(Attendance attendance) {
        return attendanceRepository.save(attendance);
    }

    @Override
    public List<Attendance> getAttendanceByStudent(Long studentId) {
        return attendanceRepository.findByStudentStudentProfileId(studentId);
    }

    @Override
    public List<Attendance> getAttendanceByClassRoomAndDate(Long classRoomId, String date) {
        LocalDate localDate = LocalDate.parse(date);
        return attendanceRepository.findByClassRoomIdAndDate(classRoomId, localDate);
    }

    @Override
    public Attendance updateAttendance(Long attendanceId, String newStatus) {
        Attendance attendance = attendanceRepository.findById(attendanceId)
                .orElseThrow(() -> new RuntimeException("Attendance not found"));

        attendance.setStatus(Attendance.Status.valueOf(newStatus));
        return attendanceRepository.save(attendance);
    }

    @Override
    public void deleteAttendance(Long attendanceId) {
        Attendance attendance = attendanceRepository.findById(attendanceId)
                .orElseThrow(() -> new RuntimeException("Attendance not found"));

        attendanceRepository.delete(attendance);
    }

    @Override
    public Map<String, Object> calculateAttendanceData(Long studentId, int month, int year) {
        YearMonth selectedMonth = YearMonth.of(year, month); // Example: 2025, 4 -> April 2025
        LocalDate startDate = selectedMonth.atDay(1);
        LocalDate endDate = selectedMonth.atEndOfMonth();

        List<Attendance> attendanceList = attendanceRepository
                .findByStudent_StudentProfileIdAndDateBetween(studentId, startDate, endDate);

        long total = attendanceList.size();
        long present = attendanceList.stream()
                .filter(a -> a.getStatus() == Attendance.Status.PRESENT)
                .count();
        long absent = total - present;

        double percentage = total > 0 ? (present * 100.0) / total : 0.0;

        List<String> presentDates = attendanceList.stream()
                .filter(a -> a.getStatus() == Attendance.Status.PRESENT)
                .map(a -> a.getDate().toString())
                .collect(Collectors.toList());

        List<String> absentDates = attendanceList.stream()
                .filter(a -> a.getStatus() == Attendance.Status.ABSENT)
                .map(a -> a.getDate().toString())
                .collect(Collectors.toList());

        Map<String, Object> result = new HashMap<>();
        result.put("month", selectedMonth.getMonth().toString());
        result.put("year", selectedMonth.getYear());
        result.put("totalDays", total);
        result.put("presentDays", present);
        result.put("absentDays", absent);
        result.put("percentage", String.format("%.2f", percentage));
        result.put("presentDates", presentDates);
        result.put("absentDates", absentDates);

        return result;
    }



    @Override
    public Map<String, Object> getMonthlyAttendance(Long studentId, int month, int year) {
        List<Attendance> attendanceList = attendanceRepository.findByStudent_StudentProfileId(studentId);

        // Filter records for the given month and year
        List<Attendance> monthlyRecords = attendanceList.stream()
                .filter(a -> a.getDate().getMonthValue() == month && a.getDate().getYear() == year)
                .toList();

        long total = monthlyRecords.size();
        long present = monthlyRecords.stream()
                .filter(a -> a.getStatus() == Attendance.Status.PRESENT)
                .count();
        long absent = total - present;

        double percentage = total > 0 ? (present * 100.0) / total : 0.0;

        List<String> presentDates = monthlyRecords.stream()
                .filter(a -> a.getStatus() == Attendance.Status.PRESENT)
                .map(a -> a.getDate().toString())
                .collect(Collectors.toList());

        List<String> absentDates = monthlyRecords.stream()
                .filter(a -> a.getStatus() == Attendance.Status.ABSENT)
                .map(a -> a.getDate().toString())
                .collect(Collectors.toList());

        Map<String, Object> result = new HashMap<>();
        result.put("month", month);
        result.put("year", year);
        result.put("totalDays", total);
        result.put("presentDays", present);
        result.put("absentDays", absent);
        result.put("percentage", String.format("%.2f", percentage));
        result.put("presentDates", presentDates);
        result.put("absentDates", absentDates);

        return result;
    }

}
