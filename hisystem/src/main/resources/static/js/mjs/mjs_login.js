/*初始化登录注册js*/
Auth.init({});

/* 注册显示所有角色*/
function choseRole() {
    $.ajax({
        url: "/user/getAllRole",
        type: "post",
        dataType: "json",
        success: function (data) {
            var optionHtml = '<li value=""></li>';
            $.each(data, function (i, val) {
                optionHtml += '<li value="' + val.roleValue + '" >' + val.description + '</li>';
            });
            $('#allRole').html(optionHtml)
        }
    })

}

/*浏览器信息*/
function getBroswer() {
    var Sys = {};
    var ua = navigator.userAgent.toLowerCase();
    var s;
    (s = ua.match(/edge\/([\d.]+)/)) ? Sys.edge = s[1] :
        (s = ua.match(/rv:([\d.]+)\) like gecko/)) ? Sys.ie = s[1] :
            (s = ua.match(/msie ([\d.]+)/)) ? Sys.ie = s[1] :
                (s = ua.match(/firefox\/([\d.]+)/)) ? Sys.firefox = s[1] :
                    (s = ua.match(/chrome\/([\d.]+)/)) ? Sys.chrome = s[1] :
                        (s = ua.match(/opera.([\d.]+)/)) ? Sys.opera = s[1] :
                            (s = ua.match(/version\/([\d.]+).*safari/)) ? Sys.safari = s[1] : 0;

    if (Sys.edge) return {broswer: "Edge", version: Sys.edge};
    if (Sys.ie) return {broswer: "IE", version: Sys.ie};
    if (Sys.firefox) return {broswer: "Firefox", version: Sys.firefox};
    if (Sys.chrome) return {broswer: "Chrome", version: Sys.chrome};
    if (Sys.opera) return {broswer: "Opera", version: Sys.opera};
    if (Sys.safari) return {broswer: "Safari", version: Sys.safari};

    return {broswer: "", version: "0"};
}

/*当前浏览器*/
var broswer = getBroswer();

/*当前ip*/
var ip = returnCitySN["cip"];

/*点击Enter登录*/
$(document).keypress(function (e) {
    if ((e.keyCode || e.which) === 13) {
        $("#loginId").click(dologin());
    }
});

/*登录*/
function dologin() {

    var user = {
        email: $("#LoginEmail").val(),
        password: $("#LoginPassword").val(),
        broswer: broswer.broswer,
        ip: ip
    };

    $.ajax({
        url: "/user/dologin",
        type: "post",
        dataType: "text",
        contentType: 'application/json',
        data: JSON.stringify(user),
        success: function (data) {

            data=JSON.parse(data);

            if (data !== null && data.status === 1) {
                window.location.href = "/main"
            } else {

                swal(data.message, "", "error")
            }

        }
    });

    /**记住密码**/
    setCookie();
}


/*注册*/
function register() {

    var email = $("#registerEmail").val();
    var register = {
        username: $("#username").val(),
        email: email,
        password: $("#registerPassword").val(),
        roleName: $("#chooseRole").text()
    };

    $.ajax({
        url: "/user/doregister",
        type: "post",
        datatype: "text",
        contentType: 'application/json',
        data: JSON.stringify(register),

        success: function (data) {

            if (data !== null && data.status === 1) {

                /*注册成功，页面跳转*/
                window.location.href = "/fmail?email=" + email
            } else {
                swal(data.message, "", "error")
            }
        }
    })
}

/*记住密码，cookie相关*/
function setCookie() {
    var checked = $("#checkbox").prop("checked");

    if (checked) {
        var loginCode = $("#LoginEmail").val();
        var pwd = $("#LoginPassword").val();
        var flag = true;

        $.cookie("login_code", loginCode);
        $.cookie("pwd", $.base64.encode(pwd), {expires: 7});

        $.cookie("flag", flag)

    } else {
        $.cookie("flag", false);
    }
}

/*填充登录信息*/
function getCookie() {
    var loginCode = $.cookie("login_code");
    var pwd = $.cookie("pwd");

    var flag = $.cookie("flag");

    $("#LoginEmail").val(loginCode);

    if (loginCode !== undefined && flag === "true") {
        $("#checkbox").prop("checked", true);
        $("#LoginPassword").val($.base64.decode(pwd));

    } else {
        $("#checkbox").prop("checked", false);
        $("#LoginPassword").val();
    }

}


