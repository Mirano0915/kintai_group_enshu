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
	  <a href="/attendanceAgree" class="popup-inner">
	    <p>承認待ちの打刻変更が ${count} 件あります</p>
	    <span class="close-button" onclick="closePendingPopup(event)">&times;</span>
	  </a>
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
function closePendingPopup(event) {
    event.stopPropagation();     // 親へのイベント伝播を防ぐ
    event.preventDefault();      // デフォルトのリンク挙動を防ぐ
    const popup = document.getElementById('pending-popup');
    if (popup) {
        popup.remove();
    }
}
   

//ホバーの画像
const hoverImages = [
  {
    buttonId: 'attendanceAgree',
    imageId: 'money',
    normal: '/img/hanko.png',
    hover: '/img/hanko-blue.png'
  },
  {
    buttonId: 'payroll',
    imageId: 'hanko',
    normal: '/img/money.png',
    hover: '/img/money-blue.png'
  },
  {
    buttonId: 'attendance',
    imageId: 'calender',
    normal: '/img/calender.png',
    hover: '/img/calender-blue.png'
  }
];

hoverImages.forEach(({ buttonId, imageId, normal, hover }) => {
  const button = document.getElementById(buttonId);
  const img = document.getElementById(imageId);

  if (button && img) {
    button.addEventListener('mouseenter', () => {
      img.src = hover;
    });

    button.addEventListener('mouseleave', () => {
      img.src = normal;
    });
  }
});
