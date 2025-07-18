/**
 * 
 */
document.addEventListener("DOMContentLoaded", function () {
    console.log("✅ approve.js 読み込み完了");

    document.querySelectorAll(".approve-btn").forEach(function (button, index) {
        console.log(`🔎 承認ボタン${index + 1}を取得:`, button);

        button.addEventListener("click", function () {
            const row = button.closest("tr");
            const stampId = row.getAttribute("data-stamp-id");

            console.log("🆔 クリックされた行の stampId:", stampId);

            if (!stampId) {
                alert("stampIdが取得できませんでした。データ属性を確認してください。");
                return;
            }

            fetch("/approve-request", {
                method: "POST",
                headers: {
                    "Content-Type": "application/x-www-form-urlencoded"
                },
                body: `stampId=${encodeURIComponent(stampId)}`
            })
            .then(response => {
                console.log("📡 サーバーからのレスポンス:", response);

                if (!response.ok) throw new Error("通信エラー");
                row.remove();
                console.log("🧹 承認された行を削除しました");
            })
            .catch(error => {
                console.error("⚠️ 承認処理に失敗しました:", error);
                alert("承認に失敗しました: " + error.message);
            });
        });
    });
});
