document.addEventListener("DOMContentLoaded", function() {
	// 承認ボタン
	document.querySelectorAll(".approve-btn").forEach(function(btn) {
		btn.addEventListener("click", function() {
			const row = this.closest("tr");
			const stampId = this.closest("tr").getAttribute("data-stamp-id");
			$.post("/approve-request", { stampId: stampId }, function(response) {
				document.getElementById("message-box").textContent = response;

				row.remove();
				checkTableEmpty();
			});
		});
	});

	// 却下ボタン
	document.querySelectorAll(".deny-btn").forEach(function(btn) {
		btn.addEventListener("click", function() {
			const row = this.closest("tr");
			const stampId = this.closest("tr").getAttribute("data-stamp-id");
			$.post("/deny-request", { stampId: stampId }, function(response) {
				document.getElementById("message-box").textContent = response;
				row.remove();
				checkTableEmpty();
			});
		});
	});
});

function checkTableEmpty() {
    const tableBody = document.querySelector("tbody");
    const table = document.querySelector("table");
    const rows = tableBody.querySelectorAll("tr");

    if (rows.length === 0) {
        // テーブル全体を削除
        table.remove();

        // メッセージ表示
        const message = document.createElement("div");
        message.textContent = "申請はありません";
        message.style.fontSize = "1.2em";
        message.style.marginTop = "20px";

        document.body.appendChild(message);
    }
}

