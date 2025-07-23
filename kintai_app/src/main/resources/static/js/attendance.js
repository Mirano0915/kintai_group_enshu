/**
 * 
 */

// AJAX削除機能
function deleteAttendance(button) {
    const attendanceId = button.getAttribute('data-attendance-id');
    const name = button.getAttribute('data-name');
    
    if (confirm('本当に ' + name + 'さん を削除しますか？')) {
        // 実際のAJAX呼び出し
        fetch('/api/delete-attendance', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({
                attendanceId: attendanceId
            })
        })
        .then(response => response.json())
        .then(data => {
            if (data.success) {
                alert(data.message);
                // 削除成功：ページをリロード
                location.reload();
            } else {
                alert('削除に失敗しました: ' + data.message);
            }
        })
        .catch(error => {
            alert('エラーが発生しました: ' + error);
        });
    }
}