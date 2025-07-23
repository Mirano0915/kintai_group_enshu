package com.kintai.Dao;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.kintai.Entity.StampsEntity;
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

		String sql = "INSERT INTO stamps (attendance_id, name_id, pre_checkin_time, pre_checkout_time, comment) VALUES (?, ?, ?, ?, ?)";

		db.update(
				sql,
				form.getAttendanceId(),
				form.getNameId(),
				form.getPreCheckinTimeAsSqlTime(), // java.sql.Time に変換
				form.getPreCheckoutTimeAsSqlTime(), // java.sql.Time に変換
				form.getComment());

	}

	//打刻申請を削除
	public void deleteAttendanceAgree(Long stampId) {
		
		String sql = "DELETE FROM stamps WHERE stamp_id=?";
		
		db.update(sql, stampId);

	}

	//勤怠テーブルと打刻変更テーブルを内部結合

	public List<StampsEntity> showAttendanceAgreeTable() {
		
		String sql ="SELECT "
				+ "STAMPS.STAMP_ID, "
				+ "NAME, "
				+ "CHECKIN_TIME, "
				+ "CHECKOUT_TIME, "
				+ "PRE_CHECKIN_TIME, "
				+ "PRE_CHECKOUT_TIME, "
				+ "DATE, "
				+ "COMMENT "
				+ "FROM STAMPS "
				+ "INNER JOIN "
				+ "ATTENDANCES ON STAMPS.NAME_ID = ATTENDANCES.NAME_ID";
		
		List<Map<String, Object>> resultDb1 = db.queryForList(sql);
		
		List<StampsEntity>resultDb2 = new ArrayList<StampsEntity>();//勤怠履歴を入れておくリスト


		for(Map<String, Object> result1:resultDb1) {
			StampsEntity entitydb = new StampsEntity();
			
			entitydb.setStampId((Long)result1.get("stamp_id"));
			entitydb.setName((String)result1.get("name"));
			entitydb.setCheckinTime((Time)result1.get("checkin_time"));
			entitydb.setCheckoutTime((Time)result1.get("checkout_time"));
			entitydb.setPreCheckinTime((Time)result1.get("pre_checkin_time"));
			entitydb.setPreCheckoutTime((Time)result1.get("pre_checkout_time"));
			entitydb.setDate((Date)result1.get("date"));
			entitydb.setComment((String)result1.get("comment"));

			
			resultDb2.add(entitydb);
			
		}
		return resultDb2;
	}
	
	
	public Long readNameId(Long attendanceId) {
		String sql = "SELECT name_id FROM attendances WHERE attendance_id = ?";
		
		return db.queryForObject(sql, Long.class, attendanceId);
	}
}
