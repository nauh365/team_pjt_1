var token = sessionStorage.getItem("token");
fetch("/users/getUser", {
    method: 'GET',
    headers: {
        AUTHORIZATION: 'Bearer ' + token
    },
    redirect: "follow"
}).then(res => {
    console.log(res);
    return res.json();
}).then(body => {
    console.log(body.username);
    document.getElementById("authorId").value = body.username;
}).catch(err => {
    console.log(err);
});