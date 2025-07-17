package com.kintai.Service;

import org.springframework.stereotype.Service;

import com.kintai.Dao.AttendancesDAO;
import com.kintai.Dao.StampsDAO;
import com.kintai.Entity.AttendancesEntity;
import com.kintai.Entity.StampsEntity;

@Service
public class AttendanceService {

    // AttendancesDAOの用意
    private final AttendancesDAO attendancesDAO;
    
    // StampsDAOの用意
    private final StampsDAO stampsDAO;
    
    public AttendanceService(AttendancesDAO attendancesDAO, StampsDAO stampsDAO) {
        this.attendancesDAO = attendancesDAO;
        this.stampsDAO = stampsDAO;
    }

    
//      新規勤怠データを作成

    public void createAttendance(AttendancesEntity attendance) {
        attendancesDAO.createDB(attendance);
    }

    
//     勤怠データを削除（管理者のみ）
    
    public void deleteAttendance(Long nameId) {
        attendancesDAO.deleteDB(nameId);
    }

    
//     勤怠データを更新（管理者のみ）
   
    public void updateAttendance(AttendancesEntity attendance) {
        attendancesDAO.updateDB(attendance);
    }

    
//     変更申請を送信（従業員モード）
     
    public void submitChangeRequest(StampsEntity stampsEntity) {
        stampsDAO.createDB(stampsEntity);
    }

    
//      出勤状況を判定（出勤済み、退勤済み、未出勤）
    
    public String getAttendanceStatus(AttendancesEntity attendance) {
        if (attendance.getCheckinTime() == null) {
            return "未出勤";
        } else if (attendance.getCheckoutTime() == null) {
            return "出勤中";
        } else {
            return "退勤済み";
        }
    }
}