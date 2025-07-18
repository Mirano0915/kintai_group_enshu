package com.kintai.Dao;

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
		String sql = "SELECT attendances.name_id, attendances.name, hourly_wages.hourly_wage, " + //name_id、名前、時給を従業員ごとにSELECT
				 "SUM(TIMESTAMPDIFF(MINUTE, attendances.checkin_time, attendances.checkout_time)) AS total_work_minutes, " + //合計出勤時間を算出
	             "SUM(CASE WHEN attendances.checkin_time >= '22:00:00' THEN TIMESTAMPDIFF(MINUTE, attendances.checkin_time, attendances.checkout_time) ELSE 0 END) AS night_work_minutes, " + //深夜労働時間を算出
	             "COUNT(*) AS days_worked, " + //出勤回数
	             "COUNT(*) * (210 + 250) AS transportation, " + //賄い代 + 交通費算出
	             "(SUM(TIMESTAMPDIFF(MINUTE, attendances.checkin_time, attendances.checkout_time)) * hourly_wages.hourly_wage / 60) + (COUNT(*) * 210) AS total_amount " + //月給算出
	             "FROM attendances " +
	             "INNER JOIN hourly_wages ON attendances.name_id = hourly_wages.name_id " +
	             "GROUP BY attendances.name_id, attendances.name";

		List<Map<String, Object>> resultDb1 = db.queryForList(sql);
		
		List<HourlyWagesEntity>resultDb2 = new ArrayList<HourlyWagesEntity>();//従業員ごとの給与情報を入れておくリスト
		
		//データを取得
		for(Map<String, Object> result1:resultDb1) {
			HourlyWagesEntity entitydb = new HourlyWagesEntity();
		
			
			entitydb.setNameId(result1.get("name_id") != null ? ((Number) result1.get("name_id")).longValue() : 0L);
		    entitydb.setName(result1.get("name") != null ? (String) result1.get("name") : ""); // デフォルト値として空文字を設定
		    entitydb.setHourlyWage(result1.get("hourly_wage") != null ? ((Number) result1.get("hourly_wage")).intValue() : 0); // デフォルト値として空文字を設定
		    entitydb.setTotalWorkingTime(result1.get("total_work_minutes") != null ? ((Number) result1.get("total_work_minutes")).intValue() : 0);
		    entitydb.setNightWorkingTime(result1.get("night_work_minutes") != null ? ((Number) result1.get("night_work_minutes")).intValue() : 0);
		    entitydb.setDaysWorked(result1.get("days_worked") != null ? ((Number) result1.get("days_worked")).intValue() : 0);
		    entitydb.setTransportation(result1.get("transportation") != null ? ((Number) result1.get("transportation")).intValue() : 0);
		    entitydb.setTotalAmount(result1.get("total_amount") != null ? ((Number) result1.get("total_amount")).intValue() : 0);
			resultDb2.add(entitydb);
			
		}
		return resultDb2;
			
			
		
	}
}
