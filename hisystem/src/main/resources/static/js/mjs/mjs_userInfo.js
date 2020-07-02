$(window).preloader();

$(function () {
    $.ajax({
        url: "/user/getUserInfo",
        type: "post",
        dataType: "json",
        success: function (data) {

            $.each(data, function (index, userInfo) {

                $("#username").val(userInfo.username);

                /*select同样可以这样赋值*/
                $("#sex").val(userInfo.sex);

                $("#birthday").val(userInfo.birthday);

                $("#politicalStatus").val(userInfo.politicalStatus);

                $("#phone").val(userInfo.phone);

                $("#address").val(userInfo.address);
            })
        }
    })

});

function changeUserInfo() {

    var username = $("#username").val();
    var userInfor = {
        username: username,
        sex: $("#sex").val(),
        birthday: $("#birthday").val(),
        politicalStatus: $("#politicalStatus").val(),
        phone: $("#phone").val(),
        address: $("#address").val()
    };
    $.ajax({
        url: "/user/changeUserInfo",
        type: "post",
        contentType: 'application/json',
        data: JSON.stringify(userInfor),
        success: function (data) {

            if (data !== null && data.status === 1) {

                swal({
                    title: "修改成功！",
                    type: "success",
                    showCancelButton: true,
                    closeOnConfirm: false,
                    showLoaderOnConfirm: true
                });
                /*异步刷新导航栏用户昵称*/
                $("#head_username").html(username);
            } else {

                swal(data.message, "", "error")
            }

        }
    })
}
