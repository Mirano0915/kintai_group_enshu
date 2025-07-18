package com.kintai.Service;

import org.springframework.stereotype.Service;

import com.kintai.Dao.StampsDAO;

@Service
public class ManagerService {

    private final StampsDAO stampsDAO;

    public ManagerService(StampsDAO stampsDAO) {
        this.stampsDAO = stampsDAO;
    }

    /**
     * 承認待ちの打刻変更申請件数を取得
     * manager.html で件数表示するためのメソッド
     */
    public int getPendingRequestCount() {
        // StampsDAOから承認待ちの申請数を取得
        // 実際のメソッドはStampsDAOが完成してから実装
        
        // TODO: 実際の実装例
        // return stampsDAO.countPendingRequests();
        
        // テスト用：固定値を返す
        return 3; // 3件の申請があると仮定
    }
}