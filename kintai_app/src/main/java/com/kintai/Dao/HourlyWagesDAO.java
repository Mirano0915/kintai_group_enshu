package com.kintai.Dao;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.kintai.Entity.HourlyWagesEntity;

@Repository
public class HourlyWagesDAO {

	
	//DB操作専用オブジェクトを準備
	private final JdbcTemplate db;
	public HourlyWagesDAO(JdbcTemplate db) {
		this.db = db;
	}
	
	
	//勤怠テーブルと時給テーブルを内部結合して読み取り
	public List<HourlyWagesEntity> readDb(){
		String sql = "SELECT * FROM attendances INNER JOIN hourly_wages ON attendances.name_id = hourly_wages.name_id";
	}
}
