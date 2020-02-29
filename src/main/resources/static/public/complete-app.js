window.onload = function () {
    let url_string = window.location.href; //window.location.href
    let url = new URL(url_string);
    let c = url.searchParams.get("token");

    if (c !== null) {
        document.getElementById("token").innerText = c;
        document.getElementById("loginContainer").hidden = false;
    }

    console.log(c);
};

function complete() {
    let name = document.getElementById("name").value;
    let password = document.getElementById("password").value;
    let position = document.getElementById("position").value;
    let token = document.getElementById("token").innerText;

    let json = JSON.stringify({
        "name" : name,
        "password" : password,
        "position" : position,
        "token" : token
    });

    return fetch("/api/v1/auth/complete-profile", {
        method: 'POST',
        mode: 'cors',
        cache: 'no-cache',
        credentials: 'same-origin',
        headers: {
            'Content-Type': 'application/json'
        },
        redirect: 'follow',
        referrer: 'no-referrer',
        body: json
    });
}
