const url = 'http://localhost:8080/rest/user';
let tBody = document.querySelector("#table_body")
fetch(url)
    .then(response => response.json())
        .then(data => {
                tBody.innerHTML = `<td>${data.id}</td>
                                   <td>${data.name}</td>
                                   <td>${data.lastName}</td>
                                   <td>${data.age}</td>
                                   <td>${data.email}</td>
                                   <td>${data.address}</td>
                                   <td>${data.roles.map(role => role.name).join(' ')}</td>
                                    `;
        });