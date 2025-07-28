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
	  <div class="popup-inner">
	    <p>承認待ちの打刻変更が ${count} 件あります</p>
	    <span onclick="closePendingPopup()" class="close-button">&times;</span>
	  </div>
	`;


    // <h1>タグの後ろに挿入（←ポイント！）
    const h1 = document.querySelector('h1');
    if (h1) {
        h1.insertAdjacentElement('afterend', popup);
    } else {
        // h1が見つからない場合は fallback
        document.body.insertBefore(popup, document.body.firstChild);
    }
}


// ポップアップを閉じる
function closePendingPopup() {
    const popup = document.getElementById('pending-popup');
    if (popup) {
        popup.remove();
    }
}