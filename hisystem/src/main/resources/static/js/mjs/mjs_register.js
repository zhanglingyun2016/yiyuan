$(window).preloader();

function getCardIdInfor(command) {

    var GetCardIdInforReqVO = {
        command: command, //0:表示读卡器输入卡号 1:表示手动输入卡号
        cardId: $("#cardId").val()
    };
    $.ajax({
        url: "/register/getCardIdInfor",
        type: "post",
        contentType: 'application/json',
        data: JSON.stringify(GetCardIdInforReqVO),
        success: function (data) {
            if (null == data.message) {
                $("#cardId").val(data.cardId);
                $("#name").val(data.name);
                $("#sex").val(data.sex);
                $("#nationality").val(data.nationality);
                $("#age").val(data.age)
            } else {
                swal(data.message, "", "error")
            }
        }
    })
}

function getDefaultGetCardId() {
    $.ajax({
        url: "/register/getDefaultGetCardId",
        type: "post",
        success: function (data) {

            if (data.data === "fail") {
                swal("读卡失败！请刷新页面重试", "", "error")

            } else if (data.data === "none") {
                swal("未识别到卡片！", "", "error")
            }else {
                $("#cardId_1").val(data.data)
            }
        }
    })
}

function getIDcardInfor() {
    $.ajax({
        url: "/register/getIDcardInfor",
        type: "post",
        dataType: "json",
        success: function (data) {

            if (null != data.idcard) {
                $("#IDname").val(data.name);
                $("#IDcard").val(data.idcard);
                $("#IDsex").val(data.sex);
                $("#IDnationality").val(data.nationality);
                $("#IDbirthday").val(data.birth);
                $("#IDaddress").val(data.address);
            } else {
                swal(data.message, "", "error")
            }
        }
    })
}

function cancel() {

    $("#IDname").val("");
    $("#IDcard").val("");
    $("#IDsex").val("");
    $("#IDnationality").val("");
    $("#IDbirthday").val("");
    $("#IDaddress").val("");
    $("#cardId_1").val("");
    $("#IDtelphone").val("")
}

function addPatientInfor() {

    var PatientInforReqVO = {
        name: $("#IDname").val(),
        idCard: $("#IDcard").val(),
        sex: $("#IDsex").val(),
        nationality: $("#IDnationality").val(),
        birthday: $("#IDbirthday").val(),
        address: $("#IDaddress").val(),
        cardId: $("#cardId_1").val(),
        telphone: $("#IDtelphone").val()
    };
    $.ajax({
        url: "/register/addPatientInfor",
        type: "post",
        contentType: 'application/json',
        data: JSON.stringify(PatientInforReqVO),
        success: function (data) {

            if (data !== null && data.status === 1) {
                swal("办理成功！", "", "success");
                cancel();
                window.location.hash = "#";
            } else if (data.message === "FAIL") {
                swal("办理异常！请刷新页面重新操作", "", "error");
                cancel()
            } else if (data.message === "ACTIVATED") {
                swal("该就诊卡已被激活！", "", "error");
                $("#cardId_1").val()
            } else if (data.message === "COVER") {
                swal({
                    title: "该患者已有就诊卡，点击“确认”进行补办操作！",
                    type: "info",
                    showCancelButton: true,
                    closeOnConfirm: false,
                    showLoaderOnConfirm: true
                }, function () {
                    $.ajax({
                        url: "/register/coverCardId",
                        type: "post",
                        contentType: 'application/json',
                        data: JSON.stringify(PatientInforReqVO),
                        success: function (data) {

                            if (data !== null && data.status === 1) {
                                setTimeout(function () {
                                    swal("就诊卡补办成功！", "", "success");
                                    cancel();
                                    window.location.hash = "#";
                                }, 1500);
                            } else {
                                setTimeout(function () {
                                    swal("办理异常！请刷新页面重新操作", "", "error");
                                }, 1500);
                            }
                        }
                    })
                });
            } else {
                swal(data.message, "", "error")
            }
        }
    })
}

var department = 0;
var registerType = 0;

var departmentName = "";

$(function () {

    //1.初始化Table
    var oTable = new TableInit();
    oTable.Init();

    //2.初始化Button的点击事件
    var oButtonInit = new ButtonInit();
    oButtonInit.Init();

    $.ajax({
        url: "/admin/getDepartment",
        type: "post",
        dataType: "json",
        success: function (data) {
            var optionHtml = '<option value=""></option>';
            $.each(data, function (i, value) {
                optionHtml += '<option value="' + value.code + '" id="' + value.type + '">' + value.name + '</option>';
            });

            $('#departmentSelect').html(optionHtml).trigger("chosen:updated").chosen({
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
                //选择科室
                department = $("#departmentSelect option:selected").val();
                departmentName = $("#departmentSelect option:selected").text();

                //科室类型
                registerType = $("#departmentSelect option:selected").attr("id");

                if ("0" === registerType) {
                    $("#departmentTypeSelect").val("普通门诊");
                }
                if ("1" === registerType) {
                    $("#departmentTypeSelect").val("急诊");
                }
            });
        }
    })
});


function getRegisterDoctor() {

    var name = $("#name").val();
    //患者姓名作为限制
    if (name == null || name === '') {
        swal("请先读取就诊卡！", "", "error");
        return false;
    }

    if (department == null || department === '') {
        swal("请选择科室！", "", "error");
        return false;
    }


    $('#RegisterDoctor').bootstrapTable("destroy");
    //1.初始化Table
    var oTable = new TableInit();
    oTable.Init();

    //2.初始化Button的点击事件
    var oButtonInit = new ButtonInit();
    oButtonInit.Init();

}

var doctor = '';
var doctorId = '';
var treatmentPrice = '';
var doctorName = '';
var workAddress = '';

var TableInit = function () {
    var oTableInit = {};
    //初始化Table
    oTableInit.Init = function () {
        $('#RegisterDoctor').bootstrapTable({
            url: '/register/getAllRegisterDoctor',         //请求后台的URL（*）
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
            height: 300,                        //行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
            uniqueId: "loginIp",                     //每一行的唯一标识，一般为主键列
            showToggle: false,                    //是否显示详细视图和列表视图的切换按钮
            cardView: false,                    //是否显示详细视图
            detailView: false,                   //是否显示父子表
            columns: [{
                field: 'no',
                title: '序号',
                sortable: true,
                align: "center",
                width: 40,
                formatter: function (value, row, index) {

                    return index + 1
                }
            }, {
                field: 'doctorName',
                title: '医生名称',
                align: 'center',
                valign: 'middle'
            }, {

                field: 'workDateTime',
                title: '门诊时间',
                align: 'center',
                valign: 'middle'

            }, {
                field: 'price',
                title: '诊查费',
                align: 'center',
                valign: 'middle'

            }, {
                field: 'allowNum',
                title: '限额数',
                align: 'center',
                valign: 'middle'

            }, {
                field: 'nowNum',
                title: '已挂号数',
                align: 'center',
                valign: 'middle',
                Color: 'red'
            }, {
                field: 'operation',
                title: '操作',
                align: 'center',
                width: '25%',
                events: operateEvents,//给按钮注册事件
                formatter: addFunctionAlty//表格中增加按钮
            }]
        });
    };

    //得到查询的参数
    oTableInit.queryParams = function (params) {
        var temp = {   //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的

                department: department,
                registerType: registerType
            }
        ;
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

//操作
function addFunctionAlty(value, row, index) {
    if (row.allowNum === row.nowNum) {
        return [

            '<button id="btn_register" disabled="disabled" class="btn btn-outline-primary" >选择</button>  '
        ].join('');
    } else {
        return [

            '<button id="btn_register" class="btn btn-outline-primary" >选择</button>  '
        ].join('');
    }

}

window.operateEvents = {

    //选择医生
    "click #btn_register": function (e, value, row, index) {

        doctorId = row.id;
        treatmentPrice = row.price;
        doctorName = row.doctorName;
        workAddress = row.workAddress;

        cardId = $("#cardId").val();

        if (cardId == null || cardId === '') {
            swal("请先读取就诊卡！", "", "error");
            return false;
        }

        swal
        ({
                title: "确认选择吗?",
                text: "医生:<span style='color: #2C9FAF'>" + doctorName + "</span>",
                html: true,
                showCancelButton: true,
            },
            function () {

                $("#treatmentPrice").val(treatmentPrice)
            })
    }
};

/**支付方式**/
var payType = '';

$('.payType').chosen({disable_search: true}).change(function () {
    payType = $(".payType option:selected").val();
    if (payType === "现金") {
        $("#money").css("display", "block");
        $("#apay").css("display", "none");
        $("#payMoney").val("");
        $("#Change").val("")
    } else if (payType === "支付宝") {
        $("#money").css("display", "none");
        $("#apay").css("display", "block")
    } else {
        $("#money").css("display", "none");
        $("#apay").css("display", "none")
    }
});

/**计算找零**/
function getChange() {
    var a = $("#registerPriice").val();
    var m = $("#payMoney").val();
    var n = $("#treatmentPrice").val();
    var x = m - n - a;
    $("#Change").val(x)
}


function addRegisterInfor() {

    cardId = $("#cardId").val();

    var registerTypeName = $("#departmentTypeSelect").val();

    var RegisterInforReqVO = {
        cardId: cardId,
        doctorId: doctorId,
        department: department,
        registerType: registerTypeName,
        doctor: doctorName,
        treatmentPrice: treatmentPrice,
        payType: payType
    };
    if (cardId == null || cardId === '') {
        swal("请先读取就诊卡！", "", "error");
        return false;
    }

    if (treatmentPrice == null || treatmentPrice === '') {
        swal("请选择挂号医生！！", "", "error");
        return false;
    }

    swal
    ({
            title: "请确认挂号信息",
            text: "卡号:<span style='color: #2C9FAF'>" + cardId + "</span>&emsp;科室:<span style='color: #2C9FAF'>" + departmentName + "</span>" +
                "<br>类型:<span style='color: #2C9FAF'>" + registerTypeName + "</span>&emsp;医生:<span style='color: #2C9FAF'>" + doctorName + "</span>" +
                "<br>地址:<span style='color: #2C9FAF'>" + workAddress + "</span>",
            type: "info",
            html: true,
            showCancelButton: true,
            closeOnConfirm: false,
            showLoaderOnConfirm: true
        },

        function () {

            $.ajax({
                url: "/register/addRegisterInfor",
                type: "post",
                contentType: 'application/json',
                data: JSON.stringify(RegisterInforReqVO),
                success: function (data) {

                    if (data !== null && data.status === 1) {
                        setTimeout(function () {
                            swal({
                                    title: "提交成功！",
                                    type: "success"
                                },
                                function () {
                                    setTimeout(function () {
                                        window.location.reload()
                                    }, 500)

                                });
                        }, 1500);

                    } else {
                        swal(data.message, "", "error")
                    }
                }
            })

        })
}