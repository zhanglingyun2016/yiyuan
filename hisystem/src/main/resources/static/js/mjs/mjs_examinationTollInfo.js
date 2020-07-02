$(window).preloader();


function getExaminationTollInfo(command) {
    var GetCardIdInforReqVO = {
        command: command, //0:表示读卡器输入卡号 1:表示手动输入卡号
        cardId: $("#cardId").val()
    };
    $.ajax({
        url: "/toll/getexaminationtollinfo",
        type: "post",
        contentType: 'application/json',
        data: JSON.stringify(GetCardIdInforReqVO),
        success: function (data) {

            if (null != data && data.status === 1) {

                data=data.data;

                $("#name").val(data.name);
                $("#sex").val(data.sex);
                $("#nationality").val(data.nationality);
                $("#age").val(data.age);
                $("#bodyTemperature").val(data.bodyTemperature);
                $("#heartRate").val(data.heartRate);
                $("#bloodPressure").val(data.bloodPressure);
                $("#examinationCost").val(data.examinationCost);
                $("#prescriptionNum").val(data.prescriptionNum);
                $("#pulse").val(data.pulse);

                $("#registerId").val(data.registerId);
            } else {
                swal(data.message, "", "error")
            }
        }
    })
}

/*保存体检收费记录*/
function saveExaminationTollInfo() {

    $.ajax({
        url: "/toll/saveexaminationtollinfo",
        type: "post",
        data: {
            "registerId": $("#registerId").val()
        },
        success: function (data) {

            if (null != data && data.status === 1) {
                swal({
                    title: "保存成功！",
                    type: "success",
                }, function () {
                    setTimeout(function () {
                        window.location.reload()
                    }, 500)
                })

            } else {
                swal(data.message, "", "error")
            }
        }
    })
}

var payType = '';

$('.payType').chosen({disable_search: true}).change(function () {

    payType = $(".payType option:selected").val();

    if (payType === "现金") {
        $("#money").css("display", "block");
        $("#apay").css("display", "none");
        $("#payMoney").val("");
        $("#Change").val("")
    }
    else if (payType === "支付宝") {
        $("#money").css("display", "none");
        $("#apay").css("display", "block")
    } else {
        $("#money").css("display", "none");
        $("#apay").css("display", "none")
    }
});


function getChange() {
    var m = $("#payMoney").val();
    var n = $("#examinationCost").val();
    var x = m - n;
    $("#Change").val(x)
}
