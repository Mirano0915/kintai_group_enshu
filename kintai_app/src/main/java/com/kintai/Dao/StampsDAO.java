package com.kintai.Dao;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.kintai.Entity.AttendancesEntity;

@Repository
public class StampsDAO {

	//DB操作専用オブジェクトを準備
	private final JdbcTemplate db;

	public StampsDAO(JdbcTemplate db) {
		this.db = db;
	}

	//打刻申請を追加
	public void updateAttendanceTime() {

	}

	//打刻申請を削除
	public void deleteAttendanceTime() {

	}

	//勤怠テーブルと打刻変更テーブルを外部結合

	public List<AttendancesEntity> showAttendanceAgreeTable(){
		
	}
}
