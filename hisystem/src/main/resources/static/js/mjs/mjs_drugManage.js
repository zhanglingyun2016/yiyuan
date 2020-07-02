$(window).preloader();


var drugType = '';
var efficacyClassification = '';


var drug = '';
var drugType_search = '';
var efficacyClassification_search = '';


$(function () {

    //1.初始化Table
    var oTable = new TableInit();
    oTable.Init();

    //2.初始化Button的点击事件
    var oButtonInit = new ButtonInit();
    oButtonInit.Init();


    $.ajax({
        url: "/drugstore/getAllDrugType",
        type: "post",
        dataType: "json",
        success: function (data) {
            var optionHtml = '<option value=""></option>';
            $.each(data, function (i) {
                optionHtml += '<option value="' + data[i] + '" >' + data[i] + '</option>';
            });
            $('.drugTypeSelect').html(optionHtml).trigger("chosen:updated").chosen({
                no_results_text: "没有找到结果！",
                search_contains: true,
                allow_single_deselect: true,
                disable_search: false,
                disable_search_threshold: 0, //当选项少等于于指定个数时禁用搜索。
                inherit_select_classes: true, //是否继承原下拉框的样式类，此处设为继承
                /*placeholder_text_single: '',*/ //单选选择框的默认提示信息，当选项为空时会显示。如果原下拉框设置了data-placeholder，会覆盖这里的值。

                width: '125px',
                max_shown_results: 5, //下拉框最大显示选项数量
                display_disabled_options: false,
                single_backstroke_delete: false, //false表示按两次删除键才能删除选项，true表示按一次删除键即可删除
                case_sensitive_search: false, //搜索大小写敏感。此处设为不敏感
                group_search: false, //选项组是否可搜。此处搜索不可搜
                include_group_label_in_selected: true //选中选项是否显示选项分组。false不显示，true显示。默认false。
            }).change(function () {

                drugType = $(".drugTypeSelect option:selected").val()
            });

            /*搜索下拉框*/

            $('.drugTypeSelect_search').html(optionHtml).trigger("chosen:updated").chosen({
                no_results_text: "没有找到结果！",
                search_contains: true,
                allow_single_deselect: true,
                disable_search: false,
                disable_search_threshold: 0, //当选项少等于于指定个数时禁用搜索。
                inherit_select_classes: true, //是否继承原下拉框的样式类，此处设为继承
                /*placeholder_text_single: '',*/ //单选选择框的默认提示信息，当选项为空时会显示。如果原下拉框设置了data-placeholder，会覆盖这里的值。

                width: '125px',
                max_shown_results: 5, //下拉框最大显示选项数量
                display_disabled_options: false,
                single_backstroke_delete: false, //false表示按两次删除键才能删除选项，true表示按一次删除键即可删除
                case_sensitive_search: false, //搜索大小写敏感。此处设为不敏感
                group_search: false, //选项组是否可搜。此处搜索不可搜
                include_group_label_in_selected: true //选中选项是否显示选项分组。false不显示，true显示。默认false。
            }).change(function () {

                drugType_search = $(".drugTypeSelect_search option:selected").val()
            })

        }
    });


    $.ajax({
        url: "/drugstore/getAllEfficacyClassification",
        type: "post",
        dataType: "json",
        success: function (data) {
            var optionHtml = '<option value=""></option>';
            $.each(data, function (i) {
                optionHtml += '<option value="' + data[i] + '" >' + data[i] + '</option>';
            });
            $('.efficacyClassificationSelect').html(optionHtml).trigger("chosen:updated").chosen({
                no_results_text: "没有找到结果！",
                search_contains: true,
                allow_single_deselect: true,
                disable_search: false,
                disable_search_threshold: 0, //当选项少等于于指定个数时禁用搜索。
                inherit_select_classes: true, //是否继承原下拉框的样式类，此处设为继承
                /*placeholder_text_single: '',*/ //单选选择框的默认提示信息，当选项为空时会显示。如果原下拉框设置了data-placeholder，会覆盖这里的值。

                width: '175px',
                max_shown_results: 5, //下拉框最大显示选项数量
                display_disabled_options: false,
                single_backstroke_delete: false, //false表示按两次删除键才能删除选项，true表示按一次删除键即可删除
                case_sensitive_search: false, //搜索大小写敏感。此处设为不敏感
                group_search: false, //选项组是否可搜。此处搜索不可搜
                include_group_label_in_selected: true //选中选项是否显示选项分组。false不显示，true显示。默认false。
            }).change(function () {

                efficacyClassification = $(".efficacyClassificationSelect option:selected").val()
            });

            /*搜索下拉框*/
            $('.efficacyClassificationSelect_search').html(optionHtml).trigger("chosen:updated").chosen({
                no_results_text: "没有找到结果！",
                search_contains: true,
                allow_single_deselect: true,
                disable_search: false,
                disable_search_threshold: 0, //当选项少等于于指定个数时禁用搜索。
                inherit_select_classes: true, //是否继承原下拉框的样式类，此处设为继承
                /*placeholder_text_single: '',*/ //单选选择框的默认提示信息，当选项为空时会显示。如果原下拉框设置了data-placeholder，会覆盖这里的值。

                width: '175px',
                max_shown_results: 5, //下拉框最大显示选项数量
                display_disabled_options: false,
                single_backstroke_delete: false, //false表示按两次删除键才能删除选项，true表示按一次删除键即可删除
                case_sensitive_search: false, //搜索大小写敏感。此处设为不敏感
                group_search: false, //选项组是否可搜。此处搜索不可搜
                include_group_label_in_selected: true //选中选项是否显示选项分组。false不显示，true显示。默认false。
            }).change(function () {

                efficacyClassification_search = $(".efficacyClassificationSelect_search option:selected").val()
            })
        }
    });

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

                width: '180px',
                max_shown_results: 5, //下拉框最大显示选项数量
                display_disabled_options: false,
                single_backstroke_delete: false, //false表示按两次删除键才能删除选项，true表示按一次删除键即可删除
                case_sensitive_search: false, //搜索大小写敏感。此处设为不敏感
                group_search: false, //选项组是否可搜。此处搜索不可搜
                include_group_label_in_selected: true //选中选项是否显示选项分组。false不显示，true显示。默认false。
            }).change(function () {

                drug = $(".drugSelect option:selected").val();
            });
        }
    })
});

var limitStatus_search = '';
$('.limitStatus_search').chosen({disable_search: true, allow_single_deselect: true, width: '90px'}).change(function () {

    limitStatus_search = $(".limitStatus_search option:selected").val();
});

var limitStatus = '';
$('.limitStatusSelect').chosen({disable_search: true, allow_single_deselect: true, width: '90px'}).change(function () {

    limitStatus = $(".limitStatusSelect option:selected").val();
});

var TableInit = function () {
    var oTableInit = {};
    //初始化Table
    oTableInit.Init = function () {
        $('#AllDrug').bootstrapTable({
            url: '/drugstore/getAllDrug',         //请求后台的URL（*）
            method: 'get',                      //请求方式（*）
            toolbar: '#toolbar',                //工具按钮用哪个容器
            striped: false,                      //是否显示行间隔色
            cache: false,                       //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
            pagination: true,                   //是否显示分页（*）
            sortable: false,                     //是否启用排序
            // sortOrder: "asc",                   //排序方式
            queryParams: oTableInit.queryParams,//传递参数（*）
            sidePagination: "server",           //分页方式：client客户端分页，server服务端分页（*）
            pageNumber: 1,                       //初始化加载第一页，默认第一页
            pageSize: 10,                       //每页的记录行数（*）
            queryParamsType: "",
            paginationPreText: "上一页",
            paginationNextText: "下一页",
            height: 550,
            search: true,                       //是否显示表格搜索，此搜索是客户端搜索，不会进服务端，所以，个人感觉意义不大
            strictSearch: true,
            showColumns: false,                  //是否显示所有的列
            showRefresh: true,                  //是否显示刷新按钮
            minimumCountColumns: 1,             //最少允许的列数
            clickToSelect: false,                //是否启用点击选中行
            // height: 500,                        //行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
            uniqueId: "cardId",                     //每一行的唯一标识，一般为主键列
            showToggle: true,                    //是否显示详细视图和列表视图的切换按钮
            cardView: false,                    //是否显示详细视图
            detailView: false,                   //是否显示父子表
            columns: [{
                field: 'no',
                title: '序号',
                sortable: true,
                align: "center",
                // width: 40,
                formatter: function (value, row, index) {
                    //获取每页显示的数量
                    var pageSize = $('#AllDrug').bootstrapTable('getOptions').pageSize;
                    //获取当前是第几页
                    var pageNumber = $('#AllDrug').bootstrapTable('getOptions').pageNumber;
                    //返回序号，注意index是从0开始的，所以要加上1
                    return pageSize * (pageNumber - 1) + index + 1
                }

            }, {
                field: 'num',
                title: '药品编号',
                align: 'center',
                valign: 'middle'

            }, {
                field: 'name',
                title: '药品名',
                align: 'center',
                valign: 'middle',
            }, {
                field: 'drugType',
                title: '剂型',
                align: 'center',
                valign: 'middle',
            }, {
                field: 'specification',
                title: '规格',
                align: 'center',
                valign: 'middle',
            }, {
                field: 'unit',
                title: '规格单位',
                align: 'center',
                valign: 'middle',
            }, {
                field: 'limitStatus',
                title: '是否限制药',
                align: 'center',
                valign: 'middle',
            }, {
                field: 'efficacyClassification',
                title: '功效',
                align: 'center',
                valign: 'middle',
            }, {
                field: 'wholesalePrice',
                title: '批发价',
                align: 'center',
                valign: 'middle',
            }, {
                field: 'price',
                title: '售价',
                align: 'center',
                valign: 'middle',
            }, {
                field: 'manufacturer',
                title: '生产厂家',
                align: 'center',
                valign: 'middle',
            }, {
                field: 'storageQuantity',
                title: '库存量',
                align: 'center',
                valign: 'middle',
            }, {
                field: 'productionDate',
                title: '生产日期',
                align: 'center',
                valign: 'middle',
            }, {
                field: 'qualityDate',
                title: '保质日期',
                align: 'center',
                valign: 'middle',
            }, {
                field: 'operation',
                title: '操作',
                align: 'center',
                /*width: '25%',*/
                events: operateEvents,//给按钮注册事件
                formatter: addFunctionAlty//表格中增加按钮
            }]
        });
    };

    //得到查询的参数
    oTableInit.queryParams = function (params) {
        var temp = {   //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
            pageSize: params.pageSize,   //页面大小
            pageNumber: params.pageNumber - 1,//页码
            drug: drug,
            drugType_search: drugType_search,
            efficacyClassification_search: efficacyClassification_search,
            limitStatus_search: limitStatus_search
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

//操作
function addFunctionAlty(value, row, index) {
    return [

        '<button id="btn_modify" class="btn btn-outline-primary" >修改</button>  ',
        '<button id="btn_delete"  class="btn  btn-outline-danger" >删除</button>  '

    ].join('');

}

window.operateEvents = {

    // 修改
    "click #btn_modify": function (e, value, row, index) {
        //弹出模态框
        window.location.hash = "#mymodal_2";
        $("#name").val(row.name);


        $(".drugTypeSelect").val(row.drugType);
        $(".drugTypeSelect").trigger("chosen:updated");

        $("#specification").val(row.specification);
        $("#unit").val(row.unit);

        if (row.limitStatus == 1) {
            $(".limitStatusSelect").val(1);
            $(".limitStatusSelect").trigger("chosen:updated");
        } else {
            $(".limitStatusSelect").val(0);
            $(".limitStatusSelect").trigger("chosen:updated");
        }


        $(".efficacyClassificationSelect").val(row.efficacyClassification);
        $(".efficacyClassificationSelect").trigger("chosen:updated");

        $("#wholesalePrice").val(row.wholesalePrice);
        $("#price").val(row.price);
        $(".productionDate").val(row.productionDate);
        $(".qualityDate").val(row.qualityDate);
        $("#manufacturer").val(row.manufacturer);

    },
    // 删除
    'click #btn_delete': function (e, value, row, index) {
        window.location.hash = "#mymodal_1";
        $("#drugName").val(row.name);
    }
};


//日期选择初始化
$(".productionDate").flatpickr({
    maxDate: "today",
});
$(".qualityDate").flatpickr({
    minDate: "today",
});

function updateDrug() {


    var DrugReqVO = {

        name: $("#name").val(),
        drugType: drugType,
        specification: $("#specification").val(),
        unit: $("#unit").val(),
        limitStatus: limitStatus,
        efficacyClassification: efficacyClassification,
        wholesalePrice: $("#wholesalePrice").val(),
        price: $("#price").val(),
        manufacturer: $("#manufacturer").val(),
        productionDate: $(".productionDate").val(),
        qualityDate: $(".qualityDate").val(),
    };
    $.ajax({
        url: "/drugstore/updateDrug",
        type: "post",
        contentType: "application/json",
        data: JSON.stringify(DrugReqVO),
        success: function (data) {

            if (data !== null && data.status === 1) {
                swal({
                    title: "修改成功！",
                    type: "success",
                    showCancelButton: true,
                    closeOnConfirm: true,
                    showLoaderOnConfirm: false
                }, function () {

                    window.location.hash = "#";
                    $("#AllDrug").bootstrapTable('refresh');
                })
            } else {
                swal(data.message, "", "error")
            }
        }
    })
}

function deleteDrug() {

    $.ajax({
        url: "/drugstore/deleteDrug",
        type: "post",
        data: {
            "drugName": $("#drugName").val()
        },
        success: function (data) {

            if (data !== null && data.status === 1) {
                swal({
                    title: "删除成功！",
                    type: "success",
                    showCancelButton: true,
                    closeOnConfirm: true,
                    showLoaderOnConfirm: false
                }, function () {

                    window.location.hash = "#";
                    $("#AllDrug").bootstrapTable('refresh');
                })
            } else {

                swal(data.message, "", "error")
            }
        }
    })
}

function searchDrug() {

    $('#AllDrug').bootstrapTable('destroy');
    //1.初始化Table
    var oTable = new TableInit();
    oTable.Init();

    //2.初始化Button的点击事件
    var oButtonInit = new ButtonInit();
    oButtonInit.Init();

}