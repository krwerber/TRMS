checkLogin().then(showInfo);
let changingUser = false;
let changingPass = false;

function showInfo() {
    let heading = document.getElementById('heading');
    heading.innerHTML = loggedUser.username;

    let info = document.getElementById('info');
    info.innerHTML = `
        <button type="button" id="chsngeUser">Change Username</button>
        <span id="usernameForm" hidden>
            
            <input id="newUser" type="text" />
        </span>
        <br>
        <button type="button" id="chsngePass">Change Password</button>
        <span id="passwordForm" hidden>
            <label for="newPass">New Password:&nbsp;</label>
            <input name="newPass" id="newPass" type="password" />
            <br>
            <label for="newPassConf">Confirm:&nbsp;</label>
            <input name="newPassConf" id="newPassConf" type="password" />
        </span>
        <br>
    `;
    info.innerHTML += `
        <button id="submitChanges" type="button">Submit Changes</button>`;
    let changeUser = document.getElementById('chsngeUser');
    changeUser.onclick=changeUsername;
    let changePass = document.getElementById('chsngePass');
    changePass.onclick=changePassword;
    let subButton = document.getElementById('submitChanges');
    subButton.onclick=submitChanges;
}

function changeUsername() {
    changingUser = !changingUser;
    document.getElementById('usernameForm').toggleAttribute('hidden');
}

function changePassword() {
    changingPass = !changingPass;
    document.getElementById('passwordForm').toggleAttribute('hidden');
}

async function submitChanges() {
    let confirmed = false;
    if (changingUser) {
        loggedUser.username = document.getElementById('newUser').value;
        confirmed = true;
    }
    if (changingPass) {
        let newPass = document.getElementById('newPass').value;
        let newPassConf = document.getElementById('newPassConf').value;
        if (newPass === newPassConf) {
            loggedUser.passwd = newPass;
            confirmed = true;
        } else {
            alert('Passwords don\'t match. Try again.');
            confirmed = false;
        }
    }
    if (confirmed) {
        let url = baseUrl + '/user/' + loggedUser.id;
        let response = await fetch(url, {method:'PUT', body:JSON.stringify(loggedUser)});
        if (response.status === 202 || response.status === 200) {
            alert('Updated successfully.');
        } else {
            alert('Something went wrong.');
        }
        checkLogin().then(showInfo);
    }
}
