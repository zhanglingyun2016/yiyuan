$(window).preloader();

Split(['#outpatient-one', '#outpatient-two', '#outpatient-three'], {
    sizes: [25, 48, 27],
    minSize: [380, 760, 410]
});

window.onload = function () {

    refreshQueue();

    getAllDrug();
};

<!--控制队列-->
$(".showbar").on('click', function () {
    $('.widget-bar').toggleClass('on1');
    $('.showbar').toggleClass('on2');
});

function getCardIdInfor(command) {
    var GetCardIdInforReqVO = {
        command: command, //0:表示读卡器输入卡号 1:表示手动输入卡号
        cardId: $("#cardId").val()
    };
    $.ajax({
        url: "/outpatient/getCardIdInfor",
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
                $("#prescriptionNum").val(data.prescriptionNum);
                $("#date").val(data.date);
                $("#department").val(data.department);

                if (data.maritalStatus == null || data.career == null || data.personalHistory == null || data.pastHistory == null || data.familyHistory == null) {
                    $("#new-img").css("visibility", "visible");

                }
                $("#maritalStatus").val(data.maritalStatus);
                $("#career").val(data.career);
                $("#personalHistory").val(data.personalHistory);
                $("#pastHistory").val(data.pastHistory);
                $("#familyHistory").val(data.familyHistory);

                $("#queueId").val(data.queueId);

            } else {
                swal(data.message, "", "error")
            }
        }
    })
}

function changePatientInfor() {

    var cardId = $("#cardId").val();

    if (cardId == null || cardId === '') {
        swal("请先读取就诊卡！", "", "error");

        return false;
    }

    var OtherPatientInforReqVO = {

        cardId: cardId,
        maritalStatus: $("#maritalStatus").val(),
        career: $("#career").val(),
        personalHistory: $("#personalHistory").val(),
        pastHistory: $("#pastHistory").val(),
        familyHistory: $("#familyHistory").val()
    };
    $.ajax({
        url: "/outpatient/changePatientInfor",
        type: "post",
        contentType: "application/json",
        data: JSON.stringify(OtherPatientInforReqVO),
        success: function (data) {

            if (data !== null && data.status === 1) {
                swal("信息提交成功！", "", "success")
            } else {
                swal(data.message, "", "error")
            }
        }
    })
}

var drug = '';

//获取所有药品
function getAllDrug() {

    $.ajax({
        url: "/outpatient/getAllDrug",
        type: "post",
        dataType: "json",
        success: function (data) {
            var optionHtml = '<option value=""></option>';
            $.each(data, function (i) {
                optionHtml += '<option value="' + data[i] + '" >' + data[i] + '</option>';
            });
            $('.drugSelect').html(optionHtml).trigger("chosen:updated").chosen({
                no_results_text: "没有找到结果！",
                search_contains: true,
                allow_single_deselect: true,
                disable_search: false,
                disable_search_threshold: 0, //当选项少等于于指定个数时禁用搜索。
                inherit_select_classes: true, //是否继承原下拉框的样式类，此处设为继承
                /*placeholder_text_single: '',*/ //单选选择框的默认提示信息，当选项为空时会显示。如果原下拉框设置了data-placeholder，会覆盖这里的值。

                max_shown_results: 5, //下拉框最大显示选项数量
                display_disabled_options: false,
                single_backstroke_delete: false, //false表示按两次删除键才能删除选项，true表示按一次删除键即可删除
                case_sensitive_search: false, //搜索大小写敏感。此处设为不敏感
                group_search: false, //选项组是否可搜。此处搜索不可搜
                include_group_label_in_selected: true //选中选项是否显示选项分组。false不显示，true显示。默认false。
            }).change(function () {

                drug = $(".drugSelect option:selected").val();
                $.ajax({
                    url: "/outpatient/getDrugInfor",
                    type: "post",
                    data: {
                        drug: drug
                    },
                    success: function (data) {
                        $("#specification").val(data.specification);
                        $("#price").val(data.price)
                    }
                })
            });
        }
    })
}


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

                if (-1 === value.status) {

                    //registerId需要加引号，需要用\"转义
                    html += "<td>" + "<button class='btn btn-info mybutton' onclick='restorePatientInfor(\"" + value.registerId + "\")'>恢复</button>"
                }
                html += ' </tr>'
            });
            $('#alloutpatientqueue').html(html)
        }
    });
}

var drugMethod = '';

$('.drugMethod').chosen({disable_search: true, allow_single_deselect: true,}).change(function () {

    drugMethod = $(".drugMethod option:selected").val();
});


var drugNum = '';

$('.drugNum').chosen({disable_search: true, allow_single_deselect: true,}).change(function () {

    drugNum = $(".drugNum option:selected").val();
});

/**
 * @return {boolean}
 */
function ProcessLater() {

    var name = $("#name").val();
    var cardId = $("#cardId").val();
    var prescriptionNum = $("#prescriptionNum").val();
    var queueId = $("#queueId").val();

    if (cardId == null || cardId === '') {
        swal("请先读取就诊卡！", "", "error");
        return false;
    }


    var MedicalRecordReqVO = {
        cardId: cardId,
        conditionDescription: $("#conditionDescr").val(),
        prescriptionNum: prescriptionNum,
        queueId: queueId

    };

    swal({
            title: "稍后处理只会保存病历主诉信息，门诊体检需患者提供处方号，请确认您的操作！",
            text: "<div id='printId'>姓名:<span style='color: #2C9FAF'>" + name + "</span>&emsp;卡号:<span style='color: #2C9FAF'>" + cardId + "</span>" +
                "<br>处方号:<span style='color: #2C9FAF'>" + prescriptionNum + "</span></div><a class='mybutton-print' href='#' onclick='printPrescriptionNum()'>打印</a>",
            html: true,
            type: "info",
            showCancelButton: true,
            closeOnConfirm: false,
            showLoaderOnConfirm: true

        }, function () {
            $.ajax({
                url: "/outpatient/ProcessLaterMedicalRecord",
                type: "post",
                contentType: "application/json",
                data: JSON.stringify(MedicalRecordReqVO),
                success: function (data) {

                    if (data !== null && data.status === 1) {

                        setTimeout(function () {
                            swal({
                                title: "操作成功，如要恢复，请点击右侧栏进行操作",
                                type: "success",
                            }, function () {

                                setTimeout(function () {
                                    window.location.reload()
                                }, 500)
                            });
                        }, 1000)

                    } else {
                        swal(data.message, "", "error")
                    }
                }
            });


        }
    );
}

/**打印**/
function printPrescriptionNum() {

    Print('#printId', {
        onStart: function () {
            console.log('onStart', new Date())
        },
        onEnd: function () {
            console.log('onEnd', new Date())
        }
    })
}

function restorePatientInfor(registerId) {
    $.ajax({
        url: "/outpatient/restorePatientInfor",
        type: "post",
        data: {
            "registerId": registerId
        },
        success: function (data) {

            if (null == data.message) {
                $("#cardId").val(data.cardId);
                $("#name").val(data.name);
                $("#sex").val(data.sex);
                $("#nationality").val(data.nationality);
                $("#age").val(data.age);
                $("#maritalStatus").val(data.maritalStatus);
                $("#career").val(data.career);
                $("#personalHistory").val(data.personalHistory);
                $("#pastHistory").val(data.pastHistory);
                $("#familyHistory").val(data.familyHistory);

                $("#prescriptionNum").val(data.prescriptionNum);
                $("#date").val(data.date);
                $("#department").val(data.department);

                $("#conditionDescr").val(data.conditionDescription);

                $("#queueId").val(data.queueId);

                refreshQueue();
            } else {
                swal(data.message, "", "error")
            }
        }
    })
}


function selectTemplate() {

    alert(drug)
}

/*处方药总价*/
var allPrice = 0;

function addDrugs() {
    var cardId = $("#cardId").val();
    var usage = $("#usage").val();
    var price = parseInt($("#price").val());
    var specification = $("#specification").val();

    if (drug == null || drug === '') {
        swal("请先选择药品！", "", "error");
        return false;
    }
    if (usage == null || usage === '') {
        swal("请填写药品每次剂量！", "", "error");
        return false;
    }

    if (drugMethod == null || drugMethod === '') {
        swal("请选择药品服用方式！", "", "error");
        return false;
    }

    if (drugNum == null || drugNum === '') {
        swal("请选择药品每日服用次数！", "", "error");
        return false;
    }
    if (cardId == null || cardId === '') {
        swal("请先读取就诊卡！", "", "error");
        return false;
    }

    $("#drugs ol").append('<li>' + drug + '<span style="margin-left:100px">'
        + specification + '</span></li><div style="margin: 10px 0 10px 5px;">用法：<sapn>'
        + usage + '</sapn><sapn  style="margin-left:40px">' + drugMethod
        + '</sapn><sapn  style="margin-left:60px">' + drugNum + '</sapn></div>');

    allPrice = allPrice + price;

}

function emptyDrugs() {

    swal({
        title: "添加的药品将全部清空，请确认操作！",
        type: "info",
        showCancelButton: true,
        closeOnConfirm: true,
    }, function () {
        $("#drugs ol").empty();
        allPrice = 0;
    })

}

function addMedicalRecord() {
    var cardId = $("#cardId").val();
    var diagnosisResult = $("#diagnosisResult").val();
    var medicalOrder = $("#medicalOrder").val();
    var queueId = $("#queueId").val();

    if (cardId == null || cardId === '') {
        swal("请先读取就诊卡！", "", "error");
        return false;
    }
    if (drug == null || drug === '') {
        swal("请选择药品！", "", "error");
        return false;
    }
    if (diagnosisResult == null || diagnosisResult === '') {
        swal("请填写初步诊断！", "", "error");
        return false;
    }
    if (medicalOrder == null || medicalOrder === '') {
        swal("请填写医嘱！", "", "error");
        return false;
    }

    var MedicalRecordReqVO = {
        cardId: cardId,
        conditionDescription: $("#conditionDescr").val(),
        prescriptionNum: $("#prescriptionNum").val(),
        prescription: $("#drugs").html().trim(),
        medicalOrder: medicalOrder,
        drugCost: allPrice,
        diagnosisResult: diagnosisResult,
        queueId: queueId
    };
    swal({

        title: "请确认患者基本信息是否提交修改",
        type: "info",
        showCancelButton: true,
        closeOnConfirm: false,
        showLoaderOnConfirm: true

    }, function () {
        setTimeout(function () {

            $.ajax({
                url: "/outpatient/addMedicalRecord",
                type: "post",
                contentType: "application/json",
                data: JSON.stringify(MedicalRecordReqVO),
                success: function (data) {

                    if (data !== null && data.status === 1) {
                        swal({
                            title: "病历信息提交成功,本次就诊完成！",
                            type: "success",
                        }, function () {

                            window.location.reload()
                        });
                    } else {
                        swal(data.message, "", "error")
                    }
                }
            })

        }, 1000)
    })


}

/**获取体检信息**/
function getMedicalExamination() {

    var prescriptionNum = $("#prescriptionNum").val();

    $.ajax({
        url: "/outpatient/getMedicalExamination",
        type: "post",
        data: {
            "prescriptionNum": prescriptionNum
        },
        success: function (data) {

            if (data.message == null) {
                $("#cardId").val(),
                    $("#bodyTemperature").val(data.bodyTemperature),
                    $("#pulse").val(data.pulse),
                    $("#heartRate").val(data.heartRate),
                    $("#bloodPressure").val(data.bloodPressure),
                    $("#examinationCost").val(data.examinationCost)
            } else {
                swal(data.message, "", "error")
            }
        }
    })


}