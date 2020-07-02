$(window).preloader();

window.onload = function () {

    refreshQueue();
};

function getCardIdInfor(command) {
    var GetCardIdInforReqVO = {
        command: command, //0:表示读卡器输入卡号 1:表示手动输入卡号
        cardId: $("#cardId").val()
    };
    $.ajax({
        url: "/medicalExamination/getCardIdInfor",
        type: "post",
        contentType: 'application/json',
        data: JSON.stringify(GetCardIdInforReqVO),
        success: function (data) {
            if (null == data.message) {
                $("#cardId").val(data.cardId);
                $("#name").val(data.name);
                $("#sex").val(data.sex);
                $("#nationality").val(data.nationality);
                $("#age").val(data.age);
                $("#queueId").val(data.queueId);
            } else {
                swal(data.message, "", "error")
            }
        }
    })
}

function savemedicalExaminationInfo() {

    var medicalExaminationInfoReqVO = {
        cardId: $("#cardId").val(),
        bodyTemperature: $("#bodyTemperature").val(),
        pulse: $("#pulse").val(),
        heartRate: $("#heartRate").val(),
        bloodPressure: $("#bloodPressure").val(),
        examinationCost: $("#examinationCost").val(),
        prescriptionNum:$("#prescriptionNum").val(),
        queueId: $("#queueId").val()
    };

    $.ajax({
        url: "/medicalExamination/saveMedicalExaminationInfo",
        type: "post",
        data: JSON.stringify(medicalExaminationInfoReqVO),
        contentType: "application/json",
        success: function (data) {

            if (data !== null && data.status === 1) {
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

<!--控制队列侧边栏-->
$(".showbar").on('click', function () {
    $('.widget-bar').toggleClass('on1');
    $('.showbar').toggleClass('on2');
});

//获取当前医生下所有门诊队列患者
function refreshQueue() {

    $.ajax({
        url: "/outpatient/getalloutpatientqueue",
        type: "post",
        dataType: "json",
        success: function (data) {

            var html = '';
            $.each(data, function (i, value) {
                html += '<tr class="alloutpatientqueue">';
                html += '<th>' + (i + 1) + '</th>';
                html += '<td>' + value.cardId + '</td>';
                html += '<td>' + value.patientName + '</td>';
                html += ' </tr>'
            });
            $('#alloutpatientqueue').html(html)
        }
    });
}