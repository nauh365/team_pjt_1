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
    const username = body.username;
    const userAuth = body.auth;
    console.log(username)
    console.log(userAuth)

    document.getElementById("authorId").value = body.username;

    // 사용자 이름을 확인하여 특정 div의 표시 여부 결정
    const authorDiv = document.getElementById("authorDiv");
    const authorId = document.getElementById("authorDiv").getAttribute('data-author-id');


    console.log(authorDiv)
    console.log(authorId)

    if (username === authorId) {
        authorDiv.style.display = 'block';
    } else {
        authorDiv.style.display = 'none';
    }



    if (userAuth === "[ROLE_ADMIN]") {
        replyDiv.style.display = 'block';
    } else {
        replyDiv.style.display = 'none';
    }

}).catch(err => {
    console.log(err);
})

function toggleEditForm(answerId) {
    var form = document.getElementById('editForm-' + answerId);
    var content = document.getElementById('content-' + answerId);
    var editButton = document.getElementById('editButton-' + answerId);

    if (form.style.display === 'none') {
        form.style.display = 'block'; // 수정 폼을 보여줍니다.
        content.style.display = 'none'; // 원래의 답변 내용을 숨깁니다.
        editButton.style.display = 'none'; // 수정 버튼을 숨깁니다.
    } else {
        form.style.display = 'none'; // 수정 폼을 숨깁니다.
    }
}

// 수정 완료 처리
function showEditButtonAndContent(answerId) {
    var form = document.getElementById('editForm-' + answerId);
    var content = document.getElementById('content-' + answerId);
    var editButton = document.getElementById('editButton-' + answerId);

    form.style.display = 'none'; // 수정 폼을 숨깁니다.
    content.style.display = 'block'; // 수정된 답변 내용을 다시 보여줍니다.
    editButton.style.display = 'inline'; // 수정 버튼을 다시 보여줍니다.
}