let baseUrl = '/TRMS';
let nav = document.getElementById('navBar');
let loggedUser = null;
checkLogin();
setNav();
function setNav() {
    nav.innerHTML = `
            <a href="index.html"><strong>TRMS</strong></a>
            <a href="viewForms.html">View Reimburesment Forms</a>`;
    if (!loggedUser) {
        nav.innerHTML += `
        <div class="hero">
        <div class="form-box">
            <div class="button-box">
                <div id="btn"></div>
                <button id="toLogin" type="button" class="toggle-btn">Login</button>
                <button id="toReg" type="button" class="toggle-btn">Register</button>
            </div>
            <form id="login" class="input-group">
                <input id="user-login" type="text" class="input-field" placeholder="username" required>
                <input id="pass-login" type="password" class="input-field" placeholder="password" required>
                <button id="loginBtn" type="button" class="submit-btn">Login</button>
            </form>
            <form id="register" class="input-group">
                <input id="firstName" type="text" class="input-field" placeholder="first name" required>
                <input id="lastName" type="text" class="input-field" placeholder="last name" required>
                <input id="email" type="email" class="input-field" placeholder="email" required>
                <select id="department" class="input-menu" required>
                    <option selected disabled>Select department</option>
                    <option value="Administration">Administration</option>
                    <option value="Software Development">Software Development</option>
                    <option value="Operations">Operations</option>
                </select>
                <input id="user-reg" type="text" class="input-field" placeholder="username" required>
                <input id="pass-reg" type="password" class="input-field" placeholder="password" required>
                <button id="regBtn" type="button" class="submit-btn">Register</button>
            </form>
        </div>
        </div>
        `;
        let toLoginBtn = document.getElementById('toLogin');
        toLoginBtn.onclick = toLogin;
        let toRegBtn = document.getElementById('toReg');
        toRegBtn.onclick = toRegister;
    } else {
        nav.innerHTML += `
            <span>
                <a href="myForms.html">View or submit forms</a>
                <a href="profile.html">${loggedUser.username}&nbsp;</a>
                <button type="button" id="loginBtn">Logout</button>
            </span>
        `;
    }

    let regBtn = document.getElementById('regBtn');
    let loginBtn = document.getElementById('loginBtn');
    if (loggedUser) loginBtn.onclick = logout;
    else {loginBtn.onclick = login; regBtn.onclick=register;}
}

class Department {
    constructor(name) {
        this.name = name;
    }
}

class Employee {
    constructor(username, passwd, email, first, lastname, department) {
        this.username = username;
        this.passwd = passwd;
        this.email = email;
        this.firstName = first;
        this.lastName = lastname;
        this.department = JSON.stringify(new Department(department));
    }
}
function toRegister() {
    let x = document.getElementById("login");
    let y = document.getElementById("register");
    let z = document.getElementById("btn");

    x.style.left = "-400px";
    y.style.left = "50px";
    z.style.left = "110px";
}

function toLogin() {
    let x = document.getElementById("login");
    let y = document.getElementById("register");
    let z = document.getElementById("btn");
    x.style.left = "50px";
    y.style.left = "450px";
    z.style.left = "0";
}
async function login() {
    let url = baseUrl + '/user/login?';
    url += 'user=' + document.getElementById('user-login').value + '&';
    url += 'pass=' + document.getElementById('pass-login').value;
    let response = await fetch(url, { method: 'POST' });

    switch (response.status) {
        case 200: // successful
            loggedUser = await response.json();
            setNav();
            break;
        case 400: // incorrect password
            alert('Incorrect password, try again.');
            document.getElementById('pass').value = '';
            break;
        case 404: // user not found
            alert('That user does not exist.');
            document.getElementById('user-login').value = '';
            document.getElementById('pass-login').value = '';
            break;
        default: // other error
            alert('Something went wrong.');
            break;
    }
}

async function register() {
    let user=document.getElementById('user-reg').value;
    let pass=document.getElementById('pass-reg').value;
    let dpmt=document.getElementById('department').value;
    let first=document.getElementById('firstName').value;
    let last=document.getElementById('lastName').value;
    let email = document.getElementById('email').value;

    let newUser = new Employee(user, pass, email,first,last, dpmt);

    let url = baseUrl + '/user';
    let response = await fetch(url, {method:'POST', body:JSON.stringify(newUser)});

    switch(response.status) {
        case 201:
            loggedUser= await response.json();
            setNav();
            break;
        case 409:
            alert('Account already associated with that username/email');
            break;
        default:
            alert('Something went wrong');
            break;
    }
}

async function logout() {
    let url = baseUrl + '/user/login';
    let response = await fetch(url, { method: 'DELETE' });

    if (response.status != 200) alert('Something went wrong.');
    loggedUser = null;
    setNav();
}

async function checkLogin() {
    let url = baseUrl + '/user';
    let response = await fetch(url, {method:"GET"});
    if (response.status === 200) loggedUser = await response.json();
    setNav();
}
