package com.kintai.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kintai.Dao.StampsDAO;
import com.kintai.Entity.StampsEntity;

@Service
public class AttendanceAgreeService {
	//DB操作が必要なためStampsDAOを準備
	@Autowired
	private StampsDAO stampsDAO;

	//打刻変更申請の一覧表示
	public List<StampsEntity> getAttendanceAgreeList() {
		List<StampsEntity> resultDb2 = stampsDAO.showAttendanceAgreeTable();

		return resultDb2;

	}
}
