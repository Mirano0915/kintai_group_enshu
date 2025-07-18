/**
 * 
 */
document.addEventListener("DOMContentLoaded", function () {
    const csrfToken = document.querySelector("meta[name='_csrf']").getAttribute("content");
    const csrfHeader = document.querySelector("meta[name='_csrf_header']").getAttribute("content");

    document.querySelectorAll(".approve-btn").forEach(function (button) {
        button.addEventListener("click", function () {
            const stampId = button.getAttribute("data-stamp-id");

            fetch("/approve-request", {
                method: "POST",
                headers: {
                    "Content-Type": "application/x-www-form-urlencoded",
                    [csrfHeader]: csrfToken
                },
                body: `stampId=${encodeURIComponent(stampId)}`
            })
            .then(response => {
                if (!response.ok) {
                    throw new Error("通信エラー");
                }
                // 成功時に行を削除
                button.closest("tr").remove();
            })
            .catch(error => {
                alert("承認処理に失敗しました: " + error.message);
            });
        });
    });
});
