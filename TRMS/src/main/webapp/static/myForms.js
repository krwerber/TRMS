checkLogin().then(getThemForms);

async function getThemForms() {

    let forms = await getUserForms();

    let formSection = document.getElementById('formSection');

    if (forms.length > 0) {
        let table = document.createElement('table');

        table.innerHTML = `
            <tr>
                <th>ID</th>
                <th>Date</th>
                <th>Amount</th>
                <th>Description</th>
                <th>Approved</th>
                <th>Supporting Documents</th>
            </tr>
        `;

        for (let form of forms) {
            let tr = document.createElement('tr');
            tr.innerHTML = `
                <td>${form.id}</td>
                <td>${form.date}</td>
                <td>${form.amount}</td>
                <td>${form.description}</td>
                <td>${form.approved}</td>
            `;
            let td = document.createElement('td');
            let ul = document.createElement('ul');
            for (let sd of form.supportingDocs) {
                let li = document.createElement('li');
                li.innerHTML = sd.name;
                ul.appendChild(li);
            }
            td.appendChild(ul);
            tr.appendChild(td);
            table.appendChild(tr);
        }

        formSection.appendChild(table);
    } else {
        formSection.innerHTML = 'You have not submitted any forms';
    }

    let submitBtn = document.getElementById('formSubmit');
    submitBtn.onclick = submit;

}

async function getUserForms() {
    let url = baseUrl + '/form';
    let response = await fetch(url, {method:"GET"});

    return response.json();
}

async function submit(){
    let description = document.getElementById('description').value;
    let location = document.getElementById('location').value;
    let amount = document.getElementById('amount').value;
    let justification = document.getElementById('justification').value;

    let newForm = new form(description,location,amount,justification);

    let url = baseUrl + '/form';
    let response = await fetch(url, {method:'POST', body:JSON.stringify(newForm)});

    switch(response.status) {
        case 201:
            break;
        default:
            alert('Not added');
            break;
    }
}

class form{
    constructor(description,location,amount,justification){
        this.description=description;
        this.location=location;
        this.amount=amount;
        this.justification=justification;
    }
}