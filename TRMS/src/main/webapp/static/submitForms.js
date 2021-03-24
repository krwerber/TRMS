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