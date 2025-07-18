package com.kintai.Dao;

import java.sql.Time;
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
		
		
//		String sql = "SELECT * FROM attendances INNER JOIN hourly_wages ON attendances.name_id = hourly_wages.name_id";
//		List<Map<String, Object>> resultDb1 = db.queryForList(sql);
		
		String sql = "SELECT attendances.name_id, attendances.name, SUM(TIMESTAMPDIFF(MINUTE, attendances.checkin_time, attendances.checkout_time)) AS total_work_minutes " +
	             "FROM attendances " +
	             "INNER JOIN hourly_wages ON attendances.name_id = hourly_wages.name_id " +
	             "GROUP BY attendances.name_id, attendances.name";
		List<Map<String, Object>> resultDb1 = db.queryForList(sql);
		
		
		List<HourlyWagesEntity>resultDb2 = new ArrayList<HourlyWagesEntity>();//従業員ごとの出勤時間を入れておくリスト
		
		//勤怠時間を取得
		for(Map<String, Object> result1:resultDb1) {
			HourlyWagesEntity entitydb = new HourlyWagesEntity();
			
//			entitydb.setNameId((Long)result1.get("name_id"));
			entitydb.setNameId(result1.get("name_id") != null ? ((Number) result1.get("name_id")).longValue() : null);
			entitydb.setName((String)result1.get("name"));
			entitydb.setCheckinTime((Time)result1.get("checkin_time"));
			entitydb.setCheckoutTime((Time)result1.get("checkout_time"));
//			entitydb.setDate((Date)result1.get("date"));
//			entitydb.setHourlyWage((Integer)result1.get("hourly_wage"));
			entitydb.setHourlyWage(result1.get("hourly_wage") != null ? ((Number) result1.get("hourly_wage")).intValue() : null);

			
			resultDb2.add(entitydb);
			
		}
		return resultDb2;
			
			
		
	}
}
