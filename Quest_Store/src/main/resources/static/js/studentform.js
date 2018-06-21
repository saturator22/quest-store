function createForm() {
    hideAddButton();
    
    let studentsForm = document.getElementById("studentsform");
    studentsForm.style.cssText = "display:flex; flex-direction: column; align-items: center;";
    
    let form = document.getElementById("form");
    form.style.cssText = 'position:relative;padding: 10px; padding-right:30px; padding-left:20px; max-width:500px; max-height:500px; margin-top: 25%; background-color: rgba(4, 4, 75, 0.645); border:2px solid white; border-radius:5%; display:flex; flex-direction: column;';
    form.setAttribute("class", "animate")
    if(form.childNodes.length < 2) {
        createFormElements();
    }
    if(!document.getElementById("process")) {
        addSaveButton();
    } else {
        document.getElementById("process").style = "display:initial";
    }
}

function hideAddButton() {
    let add = document.getElementById("addbutton");
    add.style = "display:none";
}

function createFormElements() {
    let rowNames = ["Name", "Last Name", "Login", "Email", "Password", "Class"];
    
    for(index = 0; index < rowNames.length; index++) {
        let name = rowNames[index];
        createFormColumns(name);
    }
    
}
function createFormColumns(name) {
    let form = document.getElementById("form");

    let row = document.createElement("div");
    row.setAttribute("class", "row");
    form.appendChild(row);
    
    let column25 = document.createElement("div");
    column25.setAttribute("class", "col-25");
    row.appendChild(column25);

    let label = document.createElement("label");
    label.setAttribute("for", name);
    label.innerHTML = name;
    column25.appendChild(label);

    let column75 = document.createElement("div");
    column75.setAttribute("class", "col-75");
    row.appendChild(column75);

    if(name === "Class") {
        var xhr = new XMLHttpRequest();
        xhr.open('GET', 'http://localhost:8000/ajaxrequest');
        xhr.send(null);

        let select = document.createElement("select");
        select.setAttribute("id", name);
        select. setAttribute("name", name);
        column75.appendChild(select)

        xhr.onreadystatechange = function () {
          var DONE = 4; // readyState 4 means the request is done.
          var OK = 200; // status 200 is a successful return.


          if (xhr.readyState === DONE) {
            if (xhr.status === OK)
              console.log(xhr.responseText); // 'This is the returned text.'
              var jsonData = JSON.parse(xhr.responseText);
              for (var i = 0; i < jsonData.length; i++) {
                  var counter = jsonData[i];
                    let option = document.createElement("option");
                    option.setAttribute("value", counter.name);
                    option.innerHTML = counter.name;
                    select.appendChild(option);
              }
            } else {
              console.log('Error: ' + xhr.status); // An error occurred during the request.
            }
        };

    } else {
        let input = document.createElement("input");
        input.setAttribute("type", "text");
        input.setAttribute("id", name);
        input.setAttribute("name", name);
        input.setAttribute("placeholder", "Student " + name + "...");
        column75.appendChild(input);    
    }
}

function closeForm() {
    let addStudentButton = document.getElementById("addbutton");
    addStudentButton.style = "display:block";
    
    let saveButton = document.getElementById("process");
    saveButton.style = "display:none";

    let form = document.getElementById("form");
    form.style = "display:none";

    event.preventDefault();
}

function addSaveButton() {
    let add = document.getElementById("add");
    add.style.textAlign = "center";
    add.setAttribute("class", "savebutton animate");

    let button = document.createElement("button");
    button.setAttribute("id", "process");
    button.style.borderRadius = "5%";
    button.innerHTML = "Save";
    button.addEventListener('click', closeForm);
    add.appendChild(button);
    
}


