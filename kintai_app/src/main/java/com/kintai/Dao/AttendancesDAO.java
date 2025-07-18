package com.kintai.Dao;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.kintai.Entity.AttendancesEntity;

@Repository
public class AttendancesDAO {

	
	//DB操作専用オブジェクトをDIで受け取る
	private final JdbcTemplate db;
	public AttendancesDAO(JdbcTemplate db) {
		this.db = db;
	}
	
	
	//従業員の名前を取得
	public List<AttendancesEntity> readNameDb(){
		String sql = "SELECT DISTINCT name FROM attendances";
		List<Map<String, Object>> resultDb1 = db.queryForList(sql);
		List<AttendancesEntity> resultDb2 = new ArrayList<AttendancesEntity>();
		for(Map<String,Object> result1:resultDb1) {
			AttendancesEntity entitydb = new AttendancesEntity();
			entitydb.setName((String)result1.get("name"));
			resultDb2.add(entitydb);
		}
		return resultDb2;
	}
	
	
	//出勤処理
	public void checkin(String name) {
		System.out.println("出勤処理を行いました");
		LocalTime nowtime = LocalTime.now();
		LocalDate today = LocalDate.now(); 
		db.update("INSERT INTO attendances (name, checkin_time, date) VALUES(?,?,?)",name, java.sql.Time.valueOf(nowtime),java.sql.Date.valueOf(today));
	}
	
	
	//退勤処理
	public void checkout(String name) {
		System.out.println("退勤処理を行いました");
	    LocalTime nowtime = LocalTime.now();
	    
	    // 最新の出勤時間を取得
	    java.sql.Time latestCheckinTime = db.queryForObject("SELECT MAX(checkin_time) FROM attendances WHERE name = ?", java.sql.Time.class, name);

	    if (latestCheckinTime != null) {
	
	        String updateQuery = "UPDATE attendances SET checkout_time = ? WHERE name = ? AND checkin_time = ?";
	        db.update(updateQuery, java.sql.Time.valueOf(nowtime), name, latestCheckinTime);
	        System.out.println("最新の出勤記録に退勤時間を登録しました");
	    } else {
	        System.out.println("該当する出勤記録が見つかりませんでした");
	    }
	}
}
