package com.kintai.Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
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
	
	
	//idとってくるようのおまじない
	@Autowired
    private DataSource dataSource;

    public Connection getConnection() throws SQLException {
        return dataSource.getConnection(); // これで接続にゃ！
    }

	//勤怠テーブルと時給テーブルを内部結合して読み取り
	public List<HourlyWagesEntity> readDb(){	
		String sql = "SELECT hourly_wages.name, hourly_wages.hourly_wage, " + //name_id、名前、時給を従業員ごとにSELECT
				 "SUM(TIMESTAMPDIFF(MINUTE, attendances.checkin_time, attendances.checkout_time)) AS total_work_minutes, " + //合計出勤時間を算出
	             "SUM(CASE WHEN attendances.checkin_time >= '22:00:00' THEN TIMESTAMPDIFF(MINUTE, attendances.checkin_time, attendances.checkout_time) ELSE 0 END) AS night_work_minutes, " + //深夜労働時間を算出
	             "COUNT(*) AS days_worked, " + //出勤回数
	             "COUNT(*) * (210 + 250) AS transportation, " + //賄い代 + 交通費算出
	             "(SUM(TIMESTAMPDIFF(MINUTE, attendances.checkin_time, attendances.checkout_time)) * hourly_wages.hourly_wage / 60) + (COUNT(*) * 210) AS total_amount " + //月給算出
	             "FROM attendances " +
	             "INNER JOIN hourly_wages ON attendances.name_id = hourly_wages.name_id " +
	             "GROUP BY attendances.name_id, hourly_wages.name_id";

		List<Map<String, Object>> resultDb1 = db.queryForList(sql);
		
		List<HourlyWagesEntity>resultDb2 = new ArrayList<HourlyWagesEntity>();//従業員ごとの給与情報を入れておくリスト
		
		//データを取得
		for(Map<String, Object> result1:resultDb1) {
			HourlyWagesEntity entitydb = new HourlyWagesEntity();
		
			
//			entitydb.setNameId(result1.get("name_id") != null ? ((Number) result1.get("name_id")).longValue() : 0L);
		    entitydb.setName(result1.get("name") != null ? (String) result1.get("name") : ""); // デフォルト値として空文字を設定
		    entitydb.setHourlyWage(result1.get("hourly_wage") != null ? ((Number) result1.get("hourly_wage")).intValue() : 0);
		    
		    int totalWorkMinutes = (result1.get("total_work_minutes") != null ? ((Number) result1.get("total_work_minutes")).intValue() : 0);
		    int totalWorkHour = totalWorkMinutes / 60;
		    int minutesRemaining = totalWorkMinutes % 60;
		    System.out.println(totalWorkHour + "時間" + entitydb.getName());
		    entitydb.setTotalWorkingTime(totalWorkHour + "時間" + minutesRemaining + "分");
		    
		    entitydb.setNightWorkingTime(result1.get("night_work_minutes") != null ? ((Number) result1.get("night_work_minutes")).intValue() : 0);
		    entitydb.setDaysWorked(result1.get("days_worked") != null ? ((Number) result1.get("days_worked")).intValue() : 0);
		    entitydb.setTransportation(result1.get("transportation") != null ? ((Number) result1.get("transportation")).intValue() : 0);
		    entitydb.setTotalAmount(result1.get("total_amount") != null ? ((Number) result1.get("total_amount")).intValue() : 0);
			resultDb2.add(entitydb);
			
		}
		return resultDb2;
			
	}
		
		//index.html用　idで名前をとってくる
		public Long getNameIdByName(String name) {
		    String sql = "SELECT name_id FROM hourly_wages WHERE name = ?";

		    try (Connection conn = getConnection();
		         PreparedStatement stmt = conn.prepareStatement(sql)) {

		        stmt.setString(1, name);

		        try (ResultSet rs = stmt.executeQuery()) {
		            if (rs.next()) {
		                return rs.getLong("name_id");
		            } else {
		                return null; // 該当なしにゃ
		            }
		        }

		    } catch (SQLException e) {
		        e.printStackTrace();
		        return null;
		    }
		

		
	}
}
