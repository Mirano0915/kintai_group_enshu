package com.kintai.Service;

import org.springframework.stereotype.Service;

import com.kintai.Dao.StampsDAO;

@Service
public class ManagerService {
    
    private final StampsDAO stampsDAO;
    
    public ManagerService(StampsDAO stampsDAO) {
        this.stampsDAO = stampsDAO;
    }
    
   
//      承認待ちの打刻変更申請件数を取得
     public int getPendingRequestCount() {
        // StampsDAOから実際の承認待ち申請数を取得
        return stampsDAO.countPendingRequests();
    }
}