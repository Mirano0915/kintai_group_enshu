package com.kintai.Dao;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.kintai.Entity.AttendancesEntity;
import com.kintai.Form.AttendanceChangeForm;

@Repository
public class StampsDAO {

	//DB操作専用オブジェクトを準備
	private final JdbcTemplate db;

	public StampsDAO(JdbcTemplate db) {
		this.db = db;
	}

	//打刻申請を追加
	public void insertAttendanceTime(AttendanceChangeForm form) {

		String sql = "INSERT INTO stamps (name_id, pre_checkin_time, pre_checkout_time, comment) VALUES (?, ?, ?, ?)";

		db.update(
				sql,
				form.getNameId(),
				form.getPreCheckinTimeAsSqlTime(), // java.sql.Time に変換
				form.getPreCheckoutTimeAsSqlTime(), // java.sql.Time に変換
				form.getComment());

	}

	//打刻申請を削除
	public void deleteAttendanceTime() {

	}

	//勤怠テーブルと打刻変更テーブルを外部結合

	public List<AttendancesEntity> showAttendanceAgreeTable() {

	}
}
