let stomp = null;

function setConnected(isConnected) {
    document.getElementById("login").disabled = isConnected;
    document.getElementById("password").disabled = isConnected;
    document.getElementById("submitBtn").disabled = isConnected;
}

function connect(companyId) {
    let socket = new SockJS('/websocket-example');
    stomp = Stomp.over(socket);
    stomp.connect({}, function (frame) {
        setConnected(true);
        console.log('Connected : ' + frame);
        stomp.subscribe('/topic/'+companyId, function(message) {
            showCreatedNotification(message.body);
        });
    })
}

function showCreatedNotification(body) {
    let json = JSON.parse(body);
    document.getElementById("notificationText").innerText = "We got new member : '" + json.name +"'";
    hideNotification(false);
}

function hideNotification(hide) {
    if (!hide) {
        document.getElementById("notificationContainer").style.display = "block";
    } else {
        document.getElementById("notificationContainer").style.display = "none";
    }
}

async function signIn() {
    console.log("json");
    let email = document.getElementById("login").value;
    let password = document.getElementById("password").value;

    let json = '{ "email" : "' + email + '", "password" : "' + password + '"}';

    console.log(json);

    let response = await fetch("/api/v1/auth/sign-in", {
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
    const data = await response.json();
    document.getElementById("token").value = "Bearer " + data.accessToken;
    if (data.userInformationDto !== null) {
        connect(data.userInformationDto.companyId);
    }
}
