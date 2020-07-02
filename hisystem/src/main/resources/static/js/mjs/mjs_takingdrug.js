$(window).preloader();

Split(['#myprescription', '#takingDrugOperation'], {
    sizes: [65, 35],
    minSize: [800, 400]
});

function getMedicalRecord() {
    var prescriptionNum = $("#prescriptionNum_input").val();

    if (prescriptionNum == null || prescriptionNum === '') {
        swal("请填写处方号！", "", "error");
        return false;
    }

    $.ajax({
        url: "/takingdrug/getMedicalRecord",
        type: "post",
        data: {
            "prescriptionNum": prescriptionNum
        },
        success: function (data) {
            if (data.message == null) {
                $("#name").val(data.name);
                $("#sex").val(data.sex);
                $("#nationality").val(data.nationality);
                $("#age").val(data.age);
                $("#prescriptionNum").val(prescriptionNum);
                $("#createDate").val(data.createDate);
                $("#department").val(data.department);
                $("#diagnosisResult").val(data.diagnosisResult);
                $("#medicalOrder").html(data.medicalOrder);
                $("#drugCost").val(data.drugCost);
                $("#doctorName").val(data.doctorName);
                $("#prescription").html(data.prescription);
                $("#examinationCost").val(data.examinationCost);
                $("#nowDate").html(data.nowDate);
            } else {
                swal(data.message, "", "error")
            }
        }
    })
}

function saveTakingDrugInfo() {

    var prescriptionNum = $("#prescriptionNum_input").val();

    if (prescriptionNum == null || prescriptionNum === '') {
        swal("请填写处方号！", "", "error");
        return false;
    }

    $.ajax({
        url: "/takingdrug/saveTakingDrugInfo",
        type: "post",
        data: {
            "prescriptionNum": prescriptionNum
        },
        success: function (data) {

            if (data !== null && data.status === 1) {

                swal({
                    title: "提交成功！",
                    type: "success",
                    showCancelButton: true,
                    closeOnConfirm: false,
                    showLoaderOnConfirm: true
                }, function () {
                    window.location.reload()
                })
            } else {
                swal(data.message, "", "error")
            }
        }

    })
}