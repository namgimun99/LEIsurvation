$(function(){
   // 글 [삭제] 버튼
   $("#btnDel").click(function(){
        let answer = confirm("삭제하시겠습니까?");
        if(answer){
            $("form[name='frmDelete']").submit();
        }
   });

   // 현재 글의 id 값
    const id = $("input[name='id']").val().trim();

    // 현재 댓글
    loadComment(id);

    $("#btn_comment").click(function(){
        // 입력한 댓글
        const content = $("#input_comment").val().trim();

        // 검증
        if(!content){
            alert("댓글을 입력하세요");
            $("input_comment").focus();
            return;
        }

        // 전달할 parameter들 준비
        const data = {
            "review_id" : id,
            "user_id" : logged_id,
            "content" : content
        };

        $.ajax({
            url: conPath + "/rcomment/write",
            type: "POST",
            data: data,
            cache: false,
            success: function (data, status, xhr){
                if(status == "success"){
                    if(data.stuts !== "OK"){
                        alert(data.status);
                        loadComment(id); // 댓글 목록 다시 업데이트
                        $("#input_comment").val(''); // 입력 리셋
                        return;
                    }
                }
            }
        });
    });

});

function loadComment(review_id){
    $.ajax({
        url: conPath + "/rcomment/list?id=" + review_id,
        type: "GET",
        cache: false,
        success: function(data, status, xhr){
            if(status == "success"){
                // alert(xhr.responseText); // test
                buildComment(data); // 화면 렌더링

                // 댓글 목록을 불러온후 삭제에대해 이벤트리스너 등록
                addDelete();
            }
    }
    });
}

function buildComment(result){
    $("#cmt_cnt").text(result.count); // 댓글 총 개수

    const out = [];

    result.data.forEach(comment => {
       let id =  comment.id;
       let content = comment.content.trim();
       let regdate = comment.regdate;

       let user_id = parseInt(comment.user_id.id);
       let username = comment.user_id.username;
       let name = comment.user_id.name;

       // 삭제 버튼 : 작성자 본인만
        const delBtn = (logged_id !== user_id) ? '' : `
                        <i class="btn fa-solid fa-delete-left text-danger" data-bs-toggle="tooltip"
                            data-cmtdel-id="${id}" title="삭제"></i>
                    `;

        const row = `
            <tr>
            <td><span><strong>${username}</strong><br><small class="text-secondary">(${name})</small></span></td>
            <td>
                <span>${content}</span>${delBtn}
            </td>
            <td><span><small class="text-secondary">${regdate}</small></span></td>
            </tr>
            `;

        out.push(row);
    });

    $("#cmt_list").html(out.join("\n"));
} // end buildComment();

// 댓글 삭제버튼이 눌렸을때. 해당 댓글 삭제하는 이벤트를 삭제버튼에 등록
function addDelete(){

    // 현재 글의 id
    const id = $("input[name='id']").val().trim();

    $("[data-cmtdel-id]").click(function(){
        if(!confirm("댓글을 삭제하시겠습니까?")) return;

        // 삭제할 댓글의 comment_id
        const comment_id = $(this).attr("data-cmtdel-id");

        $.ajax({
            url: conPath + "/rcomment/delete",
            type: "POST",
            cache: false,
            data: {"id": comment_id},
            success: function(data, status, xhr){
                if(status == "success"){
                    if(data.status !== "OK"){
                        alert(data.status);
                        return;
                    }
                    // 삭제후에도 다시 목록 불러와야 한다.
                    loadComment(id);
                }
            },
        });

    });

}



