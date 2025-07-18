package com.kintai.Service;
import org.springframework.stereotype.Service;

import com.kintai.Dao.StampsDAO;

@Service
public class ManagerService {
	
	private final StampsDAO stampsDAO;
	
	public ManagerService(StampsDAO stampsDAO) {
		this.stampsDAO =stampsDAO;
	}
	
//	承認待ちの打刻変更件数の取得
	
	public int getPendingRequestCount() {

		//		StampsDAOを作成したら実装
		
//		テスト用：一旦固定値を返す
		return 3;
		
	}

}
