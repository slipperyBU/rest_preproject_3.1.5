const url = 'http://localhost:8080/rest/admin/';
let adminTBody = document.querySelector("#allUsers")
let deleteModal = document.querySelector("#deleteModal");
let editModal = document.querySelector("#editModal");

//Все юзеры
function getAllUsers(){
    fetch(url)
        .then(response => response.json())
        .then(data => {
            let dataOfOneUser = '';
            data.map(
                person => {
                    dataOfOneUser += `<tr>
                                   <td>${person.id}</td>
                                   <td>${person.name}</td>
                                   <td>${person.lastName}</td>
                                   <td>${person.age}</td>
                                   <td>${person.email}</td>
                                   <td>${person.address}</td>
                                   <td>${person.roles.map(role => role.name).join(' , ')}</td>
                                   <td>
                  <button type="button" class="btn btn-info" data-bs-toggle="modal" data-bs-target="#editModal" onclick="editUser(${person.id})">Edit</button>
                                    <td/>
                                    <td>
                    <button type="button"  class="btn btn-danger" data-bs-toggle="modal" data-bs-target="#deleteModal" onclick="deleteUser(${person.id})">Delete</button>
                                    </td>
                                    </tr>`;
                })
            adminTBody.innerHTML = dataOfOneUser;
        });
}

getAllUsers();

// Создание Юзера
document.getElementById("newUserForm").addEventListener('submit',e=>{
    e.preventDefault();
    let nameValue =  document.getElementById('name').value;
    let lastNameValue = document.getElementById('lastname').value;
    let ageValue = document.getElementById('age').value;
    let emailValue = document.getElementById('email').value;
    let addressValue =document.getElementById('address').value;
    let passwordValue = document.getElementById('password').value;
    let rolesValue = Array.from(document.getElementById('roles').selectedOptions).map(role => role.value);

    let newUser = {
        name: nameValue,
        lastName: lastNameValue,
        age: ageValue,
        email: emailValue,
        address: addressValue,
        password: passwordValue,
        roles: rolesValue
    }
    fetch(url, {
        method : "POST",
        headers: new Headers({
            'Accept' : 'application/json',
            'Content-Type': 'application/json; charset=UTF-8'
        }),
        body : JSON.stringify(newUser)
    })
        .then(data => getAllUsers(data))
        .then(() => document.getElementById("admin-pill").click())
})

//Редактирование Юзера
function editUser(id) {
    fetch(url +id)
        .then(resp => resp.json())
        .then(person => {
            document.getElementById('editId').value = person.id;
            document.getElementById('editName').value = person.name;
            document.getElementById('editLastName').value = person.lastName;
            document.getElementById('editAge').value = person.age;
            document.getElementById('editEmail').value = person.email;
            document.getElementById('editAddress').value = person.address;
            document.getElementById('editRoles').value = person.roles.map(role => role.name);

            editModal.show();
        })
}

async function submitEdit() {
    let idValue = document.getElementById('editId').value;
    let nameValue =  document.getElementById('editName').value;
    let lastNameValue = document.getElementById('editLastName').value;
    let ageValue = document.getElementById('editAge').value;
    let emailValue = document.getElementById('editEmail').value;
    let addressValue =document.getElementById('editAddress').value;
    let passwordValue = document.getElementById('editPassword').value;
    let rolesValue = Array.from(document.getElementById('editRoles').selectedOptions).map(role => role.value);

    let user = {
        id : idValue,
        name: nameValue,
        lastName: lastNameValue,
        age: ageValue,
        email: emailValue,
        address: addressValue,
        password: passwordValue,
        roles: rolesValue
    }
    await fetch(url + document.getElementById('editId').value,{
        method : "PATCH",
        headers: new Headers({
            'Accept' : 'application/json',
            'Content-Type': 'application/json; charset=UTF-8'
        }),
        body : JSON.stringify(user)
    })
        .then((data)=>{
            getAllUsers(data)})
        .then(()=>document.getElementById("closeEdit").click())
}

//Удалени Юзера
function deleteUser(id){
    fetch(url + id)
        .then(resp => resp.json())
        .then(person =>{
            document.getElementById('deleteId').value = person.id;
            document.getElementById('deleteName').value = person.name;
            document.getElementById('deleteLastName').value = person.lastName;
            document.getElementById('deleteAge').value = person.age;
            document.getElementById('deleteEmail').value = person.email;
            document.getElementById('deleteAddress').value = person.address;

            deleteModal.show();
        })
}
async function submitDelete(){
    await fetch(url + document.getElementById('deleteId').value, {
        method : 'DELETE',
        body : JSON.stringify(document.getElementById('deleteId').value)
    })
        .then(data => getAllUsers(data))
        .then(()=> document.getElementById("closeDelete").click())
}