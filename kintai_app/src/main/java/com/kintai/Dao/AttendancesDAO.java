package com.kintai.Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.kintai.Entity.AttendancesEntity;
import com.kintai.Form.AttendanceChangeForm;

@Repository
public class AttendancesDAO {

	//DB操作専用オブジェクトをDIで受け取る
	private final JdbcTemplate db;

	//出勤ボタン押したとき用
	@Autowired
	private DataSource dataSource;

	public AttendancesDAO(JdbcTemplate db) {
		this.db = db;
	}
	

	//従業員の名前を取得
	public List<AttendancesEntity> readNameDb() {
		String sql = "SELECT * FROM hourly_wages";
		List<Map<String, Object>> resultDb1 = db.queryForList(sql);
		List<AttendancesEntity> resultDb2 = new ArrayList<AttendancesEntity>();
		for (Map<String, Object> result1 : resultDb1) {
			AttendancesEntity entitydb = new AttendancesEntity();

			//			entitydb.setNameId(result1.get("name_id") != null ? ((Number) result1.get("name_id")).longValue() : null);
			entitydb.setName((String) result1.get("name"));
			resultDb2.add(entitydb);
		}
		return resultDb2;
	}

	//出勤処理
	public String checkin(Long nameId) {
		System.out.println("出勤処理を行いました");

		LocalTime nowtime = LocalTime.now();
		LocalDate today = LocalDate.now();

		//勤怠登録をした従業員のname_idを取得
		String sql = "SELECT name_id FROM hourly_wages WHERE name = ?";

		db.update("INSERT INTO attendances (name_id, checkin_time, date) VALUES(?,?,?)", nameId,
				java.sql.Time.valueOf(nowtime), java.sql.Date.valueOf(today));

		
		
		 // フォーマットパターンを定義
		 DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MM月dd日", Locale.JAPANESE);
	     DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm", Locale.JAPANESE);

	     // LocalDate と LocalTime をフォーマット
	     String formattedDate = today.format(dateFormatter);
	     String formattedTime = nowtime.format(timeFormatter);

	     // 日付と時間を結合
	     return formattedDate + " " + formattedTime;
	}
	
	

	//退勤処理
	public String checkout(Long nameId) {
		System.out.println("退勤処理を行いました");
		LocalTime nowtime = LocalTime.now();
		LocalDate today = LocalDate.now();

		// 退勤した従業員の最新の出勤時間を取得
		java.sql.Time latestCheckinTime = db.queryForObject(
				"SELECT MAX(checkin_time) FROM attendances WHERE name_id = ?", java.sql.Time.class, nameId);

		if (latestCheckinTime != null) {

			String updateQuery = "UPDATE attendances SET checkout_time = ? WHERE name_id = ? AND checkin_time = ?";
			db.update(updateQuery, java.sql.Time.valueOf(nowtime), nameId, latestCheckinTime);
			System.out.println("最新の出勤記録に退勤時間を登録しました");
		} else {
			System.out.println("該当する出勤記録が見つかりませんでした");
		}
		
		
		 // フォーマットパターンを定義
		 DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MM月dd日", Locale.JAPANESE);
	     DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm", Locale.JAPANESE);

	     // LocalDate と LocalTime をフォーマット
	     String formattedDate = today.format(dateFormatter);
	     String formattedTime = nowtime.format(timeFormatter);

	     // 日付と時間を結合
	     return formattedDate + " " + formattedTime;
	}

	//		 勤怠データを更新（管理者のみ）

	public void updateDB(AttendancesEntity attendance) {
		String sql = "UPDATE attendances SET checkin_time = ?, checkout_time = ?, date = ? WHERE name_id = ? AND date = ?";
		db.update(sql,
				attendance.getCheckinTime(),
				attendance.getCheckoutTime(),
				attendance.getDate(),
				attendance.getNameId(),
				attendance.getDate() != null ? attendance.getDate() : java.time.LocalDate.now());
		System.out.println("勤怠データを更新しました: nameId = " + attendance.getNameId());
	}

	//		  新規勤怠データを作成

	public void createDB(AttendancesEntity attendance) {
		String sql = "INSERT INTO attendances (name_id, checkin_time, checkout_time, date) VALUES (?, ?, ?, ?)";
		db.update(sql,
				attendance.getNameId(),
				attendance.getCheckinTime(),
				attendance.getCheckoutTime(),
				attendance.getDate() != null ? attendance.getDate() : java.time.LocalDate.now());
		System.out.println("新規勤怠データを作成しました: nameId = " + attendance.getNameId());
	}

	//	JDBCへの接続

	public Connection getConnection() throws SQLException {
        return dataSource.getConnection(); // ここで自動的にプールされた Connection を取得するにゃ！
    }
	
	//	出勤ボタンを押したとき、出勤済みか判定
	public boolean hasCheckedInToday(Long nameId) {
	    String sql = """
	        SELECT COUNT(*) FROM attendances
	        WHERE name_id = ? AND date = ? AND checkin_time IS NOT NULL
	    """;

	    try (Connection conn = getConnection();
	         PreparedStatement stmt = conn.prepareStatement(sql)) {

	        stmt.setLong(1, nameId);
	        stmt.setDate(2, new java.sql.Date(System.currentTimeMillis()));

	        try (ResultSet rs = stmt.executeQuery()) {
	            return rs.next() && rs.getInt(1) > 0;
	        }

	    } catch (SQLException e) {
	        e.printStackTrace();
	        return false;
	    }
	}
	

//	退勤ボタンを押したとき、退勤済みか判定
	public boolean hasCheckedoutToday(Long nameId) {
	    String sql = """
	        SELECT COUNT(*) FROM attendances
	        WHERE name_id = ? AND date = ? AND checkout_time IS NOT NULL
	    """;

	    try (Connection conn = getConnection();
	         PreparedStatement stmt = conn.prepareStatement(sql)) {

	        stmt.setLong(1, nameId);
	        stmt.setDate(2, new java.sql.Date(System.currentTimeMillis()));

	        try (ResultSet rs = stmt.executeQuery()) {
	            return rs.next() && rs.getInt(1) > 0;
	        }

	    } catch (SQLException e) {
	        e.printStackTrace();
	        return false;
	    }
	}
	
		 
//	 従業員データを削除（管理者のみ）
	 
	public void deleteDB(Long attendanceId) {  
	    String sql = "DELETE FROM attendances WHERE attendance_id = ?";  
	    db.update(sql, attendanceId);
	    System.out.println("勤怠データを削除しました: attendanceId = " + attendanceId);
	}
		

	// 勤怠一覧取得 - 最新の打刻順で表示
	public List<AttendancesEntity> readAllAttendanceDb() {
		String sql = "SELECT a.attendance_id, a.name_id, h.name, a.checkin_time, a.checkout_time, a.date " +
				"FROM attendances a " +
				"INNER JOIN hourly_wages h ON a.name_id = h.name_id " +
				"ORDER BY a.attendance_id DESC";

		List<Map<String, Object>> resultDb1 = db.queryForList(sql);
		List<AttendancesEntity> resultDb2 = new ArrayList<AttendancesEntity>();

		for (Map<String, Object> result1 : resultDb1) {
			AttendancesEntity entitydb = new AttendancesEntity();

			entitydb.setAttendance_id(
					result1.get("attendance_id") != null ? ((Number) result1.get("attendance_id")).longValue() : null);
			entitydb.setNameId(result1.get("name_id") != null ? ((Number) result1.get("name_id")).longValue() : null);
			entitydb.setName((String) result1.get("name"));
			entitydb.setCheckinTime((java.sql.Time) result1.get("checkin_time"));
			entitydb.setCheckoutTime((java.sql.Time) result1.get("checkout_time"));
			entitydb.setDate((java.sql.Date) result1.get("date"));

			resultDb2.add(entitydb);
		}
		
		return resultDb2;
	}

		
		// stampsテーブルの変更時間をattendancesに適用
		public void updateWorkTime(Long stampId) {
			// stamp_idに紐づくデータ取得
			String selectSql = "SELECT pre_checkin_time, pre_checkout_time, attendance_id FROM stamps WHERE stamp_id = ?";
			Map<String, Object> result = db.queryForMap(selectSql, stampId);

			// Mapから個別の値を取り出す
			Time preCheckinTime = (Time) result.get("pre_checkin_time");
			Time preCheckoutTime = (Time) result.get("pre_checkout_time");
			Long attendanceId = ((Number) result.get("attendance_id")).longValue(); // Number → Long にキャスト

			// attendancesテーブルの更新
			String updateSql = "UPDATE attendances SET checkin_time = ?, checkout_time = ? WHERE attendance_id = ?";
			db.update(updateSql, preCheckinTime, preCheckoutTime, attendanceId);
 
		

		
	}

		
	//打刻変更（管理者のみ）
	public void managerUpdateDB(AttendanceChangeForm form) {
		
		String sql = "UPDATE attendances SET name_id = ?, checkin_time = ?, checkout_time = ? WHERE attendance_id = ?";


		db.update(
				sql,
				form.getNameId(),
				form.getPreCheckinTimeAsSqlTime(), // java.sql.Time に変換
				form.getPreCheckoutTimeAsSqlTime(), // java.sql.Time に変換
				form.getAttendanceId());
		
	}
		

}
