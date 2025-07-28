function updateDateTime() {
    const now = new Date();
    const date = now.toLocaleDateString();
    const time = now.toLocaleTimeString();
    // Put the date and time on separate lines with a line break
    document.getElementById('datetime').innerHTML = date + '<br>' + time;
}

updateDateTime();
setInterval(updateDateTime, 1000);