$(window).preloader();

Split(['#myprescription', '#tolloperation'], {
    sizes: [46, 54],
    minSize: [700, 800]
});

function getCardIdInfor() {
    $.ajax({
        url: "/toll/getCardIdInfor",
        type: "post",
        success: function (data) {
            if (null == data.message) {
                $("#cardId").val(data.cardId);
            } else {
                swal(data.message, "", "error")
            }
        }
    })
}


var tollStatus = '';
$('.tollStatus').chosen({disable_search: true, allow_single_deselect: true,}).change(function () {

    tollStatus = $(".tollStatus option:selected").val();
});

$(function () {
    //1.初始化Table
    var oTable = new TableInit();
    oTable.Init();

    //2.初始化Button的点击事件
    var oButtonInit = new ButtonInit();
    oButtonInit.Init();
});

var TableInit = function () {
    var oTableInit = {};
    //初始化Table
    oTableInit.Init = function () {
        $('#MedicalRecord').bootstrapTable({
            url: '/toll/getAllMedicalRecord',         //请求后台的URL（*）
            method: 'get',                      //请求方式（*）
            toolbar: '#toolbar',                //工具按钮用哪个容器
            striped: false,                      //是否显示行间隔色
            cache: false,                       //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
            pagination: false,                   //是否显示分页（*）
            sortable: false,                     //是否启用排序
            // sortOrder: "asc",                   //排序方式
            queryParams: oTableInit.queryParams,//传递参数（*）
            sidePagination: "client",           //分页方式：client客户端分页，server服务端分页（*）
            /*   pageNumber: 1,                       //初始化加载第一页，默认第一页
               pageSize: 5,                       //每页的记录行数（*）
               queryParamsType: "",
               paginationPreText: "上一页",
               paginationNextText: "下一页",*/
            search: false,                       //是否显示表格搜索，此搜索是客户端搜索，不会进服务端，所以，个人感觉意义不大
            strictSearch: true,
            showColumns: false,                  //是否显示所有的列
            showRefresh: false,                  //是否显示刷新按钮
            minimumCountColumns: 0,             //最少允许的列数
            clickToSelect: false,                //是否启用点击选中行
            height: 350,                        //行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
            uniqueId: "loginIp",                     //每一行的唯一标识，一般为主键列
            showToggle: false,                    //是否显示详细视图和列表视图的切换按钮
            cardView: false,                    //是否显示详细视图
            detailView: false,                   //是否显示父子表
            columns: [{
                field: 'no',
                title: '序号',
                sortable: true,
                align: "center",
                width: 35,
                formatter: function (value, row, index) {

                    return index + 1
                }
            }, {
                field: 'outpatientDate',
                title: '门诊时间',
                align: 'center',
                valign: 'middle',
                fontSize: 10
            }, {
                field: 'department',
                title: '科室',
                align: 'center',
                valign: 'middle',
                fontSize: 10
            }, {
                field: 'registerType',
                title: '挂号类型',
                align: 'center',
                valign: 'middle',
                fontSize: 10
            }, {
                field: 'doctorName',
                title: '医生名称',
                align: 'center',
                valign: 'middle',
                fontSize: 10

            }, {
                field: 'prescriptionNum',
                title: '处方号',
                align: 'center',
                valign: 'middle',
                fontSize: 10
            }, {
                field: 'operation',
                title: '操作',
                align: 'center',

                events: operateEvents,//给按钮注册事件
                formatter: addFunctionAlty//表格中增加按钮
            }]
        });
    };

    //得到查询的参数
    oTableInit.queryParams = function (params) {
        var temp = {   //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的

            cardId: $("#cardId").val(),
            tollStatus: tollStatus
        };
        return temp;
    };
    return oTableInit;
};
var ButtonInit = function () {
    var oInit = {};
    var postdata = {};

    oInit.Init = function () {
        //初始化页面上面的按钮事件
    };

    return oInit;
};

function addFunctionAlty() {
    return [

        '<button id="btn_toll" class="btn btn-outline-primary" >选择</button>  '
    ].join('');
}
var registerId = '';
var prescriptionNum = '';
window.operateEvents = {
    // 选择病历收费
    "click #btn_toll": function (e, value, row, index) {
        registerId = row.registerId;
        var cardId = $("#cardId").val();
        var doctorName = row.doctorName;
        var department = row.department;
        prescriptionNum = row.prescriptionNum;
        $.ajax({
            url: "/toll/getMedicalRecord",
            type: "post",
            data: {
                "registerId": registerId,
                "cardId": cardId
            },
            success: function (data) {
                if (data != null) {
                    $("#name").val(data.name);
                    $("#sex").val(data.sex);
                    $("#nationality").val(data.nationality);
                    $("#age").val(data.age);
                    $("#prescriptionNum").val(prescriptionNum);
                    $("#createDate").val(data.createDate);
                    $("#department").val(department);
                    $("#diagnosisResult").val(data.diagnosisResult);
                    $("#medicalOrder").html(data.medicalOrder);
                    $("#drugCost").val(data.drugCost);
                    $("#doctorName").val(doctorName);
                    $("#prescription").html(data.prescription);
                    $("#examinationCost").val(data.examinationCost);
                    $("#nowDate").html(data.nowDate);
                    $("#total").val(data.drugCost)
                }
            }
        })
    }
};

function getMedicalRecord() {

    $('#MedicalRecord').bootstrapTable("destroy");
    //1.初始化Table
    var oTable = new TableInit();
    oTable.Init();

    //2.初始化Button的点击事件
    var oButtonInit = new ButtonInit();
    oButtonInit.Init();

}

/**打印**/
function printPrescription() {

    Print('#myprescription', {
        onStart: function () {
            console.log('onStart', new Date())
        },
        onEnd: function () {
            console.log('onEnd', new Date())
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
    var n = $("#total").val();
    var x = m - n;
    $("#Change").val(x)
}

function saveTollInfo() {

    var cardId = $("#cardId").val();
    var total = $("#total").val();

    var SaveTollInfoReqVO = {
        registerId: registerId,
        prescriptionNum: prescriptionNum
    };


    if (cardId == null || cardId === '') {
        swal("请先读取就诊卡！", "", "error");
        return false;
    }

    if (total == null || total === '') {
        swal("请选择收费的处方笺！", "", "error");
        return false;
    }
    if (payType == null||payType==='') {
        swal("请先付款，再提交！", "", "error");
        return false;
    }
    if (payType === "现金") {
        var change=$("#Change").val();
        if (change==null||change==='') {
            swal("请先付款，再提交！", "", "error");
            return false;
        }
    }
    $.ajax({
        url: "/toll/saveTollInfo",
        type: "post",
        contentType: "application/json",
        data: JSON.stringify(SaveTollInfoReqVO),
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