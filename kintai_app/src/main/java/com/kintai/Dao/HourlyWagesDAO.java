package com.kintai.Dao;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
		List<Map<String, Object>> resultDb1 = db.queryForList(sql);
		
		List<HourlyWagesEntity>resultDb2 = new ArrayList<HourlyWagesEntity>();//勤怠履歴を入れておくリスト
		
		//勤怠テーブルから勤怠履歴を取得
		for(Map<String, Object> result1:resultDb1) {
			HourlyWagesEntity entitydb = new HourlyWagesEntity();
			entitydb.setNameId((Long)result1.get("name_id"));
			entitydb.setName((String)result1.get("name"));
			entitydb.setCheckinTime((LocalTime)result1.get("checkin_time"));
			entitydb.setCheckoutTime((LocalTime)result1.get("checkout_time"));
			entitydb.setDate((LocalDate)result1.get("date"));
			entitydb.setHourlyWage((Integer)result1.get("hourly_wage"));
			
			resultDb2.add(entitydb);
			
		}
		return resultDb2;
			
			
		
	}
}
