/**
 * 
 */

// ページ読み込み時に承認待ち件数をチェック
document.addEventListener('DOMContentLoaded', function() {
    checkPendingApprovals();
});

// AJAX で承認待ち件数を取得
function checkPendingApprovals() {
    fetch('/api/pending-count')
        .then(response => response.json())
        .then(data => {
            if (data.count > 0) {
                showPendingPopup(data.count);
            }
        })
        .catch(error => {
            console.error('承認待ち件数の取得に失敗しました:', error);
        });
}

// ポップアップを表示
function showPendingPopup(count) {
    // 既存のポップアップがあれば削除
    const existingPopup = document.getElementById('pending-popup');
    if (existingPopup) {
        existingPopup.remove();
    }

    // ポップアップ要素を作成
    const popup = document.createElement('div');
    popup.id = 'pending-popup';
    popup.innerHTML = `
        <div>
            <span onclick="closePendingPopup()" style="cursor: pointer; float: right; font-weight: bold;">&times;</span>
            <p>承認待ちの打刻変更が${count}件あります</p>
        </div>
    `;

    // ページの最初に追加
    document.body.insertBefore(popup, document.body.firstChild);
}

// ポップアップを閉じる
function closePendingPopup() {
    const popup = document.getElementById('pending-popup');
    if (popup) {
        popup.remove();
    }
}