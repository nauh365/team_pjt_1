var token = sessionStorage.getItem(("token"))
fetch("/users/getUser",{
    method:'GET',
    headers:{
        AUTHORIZATION:'Bearer ' + token
    },
    redirect: "follow"
}).then(res =>{
    console.log(res);
    return res.json()
}).then(body => {
    console.log(body.username);
    console.log(body.auth);
    document.getElementById("username-p").innerText = body.username + body.auth
}).catch(err => {
    console.log(err);
})