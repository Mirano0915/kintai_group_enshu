package com.kintai.Dao;

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
	public List<AttendancesEntity> readDb(){
		String sql = "SELECT DISTINCT name FROM attendances";
		List<Map<String, Object>> resultDb1 = db.queryForList(sql);
		List<AttendancesEntity> resultDb2 = new ArrayList<AttendancesEntity>();
		for(Map<String,Object> result1:resultDb1) {
			AttendancesEntity entitydb = new AttendancesEntity();
			entitydb.setName((String)result1.get("name_id"));
			resultDb2.add(entitydb);
		}
		return resultDb2;
	}
}
