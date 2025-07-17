package com.kintai.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kintai.Dao.AttendancesDAO;

@Service
public class IndexService {

	//DB操作が必要なためAttendancesDaoを準備
	@Autowired
	private AttendancesDAO attendancesDAO;
	
	
}
