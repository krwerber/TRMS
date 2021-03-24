let baseUrl = '/TRMS';
class Department{
    constructor(name){
        this.name = name;
    }
}

class Employee {
    constructor(username, passwd, email, first, lastname, department){
        this.username=username;
        this.passwd=passwd;
        this.email=email;
        this.firstName=first;
        this.lastName=lastname;
        this.department=JSON.stringify(new Department(department));
    }
}
async function login() {
    let loggedUser=null;
    let url = baseUrl +'/user/login?';
    url += 'user=' + document.getElementById('user-login').value + '&';
    url += 'pass=' + document.getElementById('pass-login').value;
    let response = await fetch(url, {method: 'POST'});

    switch (response.status) {
        case 200: // successful
            loggedUser = await response.json();
            break;
        case 400: // incorrect password
            alert('Incorrect password, try again.');
            document.getElementById('pass').value = '';
            break;
        case 404: // user not found
            alert('That user does not exist.');
            document.getElementById('user-reg').value = '';
            document.getElementById('pass-reg').value = '';
            break;
        default: 
            alert('Something went wrong.');
            break;
    }
    
    redirect(loggedUser);
    
}

function redirect(loggedUser){
    if (loggedUser){
        window.location.href="http://localhost:8080/TRMS/static/home.html";
    } else {
        window.location.href="http://localhost:8080/TRMS/static";
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
}

async function logout() {
    let url = baseUrl + '/user/login';
    let response = await fetch(url, {method:'DELETE'});

    if (response.status != 200) alert('Something went wrong.');
    loggedUser = null;
}

