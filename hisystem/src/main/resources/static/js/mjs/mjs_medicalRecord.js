$(window).preloader();

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
        $('#medicalRecord').bootstrapTable({
            url: '/outpatient/getMedicalRecord',         //请求后台的URL（*）
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
                    var pageSize = $('#medicalRecord').bootstrapTable('getOptions').pageSize;
                    //获取当前是第几页
                    var pageNumber = $('#medicalRecord').bootstrapTable('getOptions').pageNumber;
                    //返回序号，注意index是从0开始的，所以要加上1
                    return pageSize * (pageNumber - 1) + index + 1
                }

            }, {
                field: 'cardId',
                title: '就诊卡号',
                align: 'center',
                valign: 'middle'

            }, {
                field: 'name',
                title: '姓名',
                align: 'center',
                valign: 'middle',
            }, {
                field: 'department',
                title: '科室',
                align: 'center',
                valign: 'middle',
            }, {
                field: 'registerType',
                title: '类型',
                align: 'center',
                valign: 'middle',
            }, {
                field: 'doctor',
                title: '就诊医生',
                align: 'center',
                valign: 'middle',
            }, {
                field: 'createDateTime',
                title: '创建时间',
                align: 'center',
                valign: 'middle',
            }, {
                field: 'createPerson',
                title: '创建人',
                align: 'center',
                valign: 'middle',
            }, {
                field: 'createPersonEmail',
                title: '标识',
                align: 'center',
                valign: 'middle',
            }]
        });
    };

    //得到查询的参数
    oTableInit.queryParams = function (params) {
        var temp = {   //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
            pageSize: params.pageSize,   //页面大小
            pageNumber: params.pageNumber - 1,//页码
            /*department: department,
            registerType: registerType,
            startTime: $(".startTime").val(),
            endTime: $(".endTime").val()*/
        };
        return temp;
    };
    return oTableInit;
};

//日期选择初始化
$(".startTime").flatpickr({
    maxDate: "today",
});
$(".endTime").flatpickr({
    minDate: "today",
});


var ButtonInit = function () {
    var oInit = {};
    var postdata = {};

    oInit.Init = function () {
        //初始化页面上面的按钮事件
    };

    return oInit;
};

var department = '';
var registerType = '';

$('.registerSearch_1').chosen({
    no_results_text: "没有找到结果！",//搜索无结果时显示的提示
    search_contains: true,   //关键字模糊搜索。设置为true，只要选项包含搜索词就会显示；设置为false，则要求从选项开头开始匹配
    allow_single_deselect: true, //单选下拉框是否允许取消选择。如果允许，选中选项会有一个x号可以删除选项
    disable_search: false, //禁用搜索。设置为true，则无法搜索选项。
    disable_search_threshold: 0, //当选项少等于于指定个数时禁用搜索。
    inherit_select_classes: true, //是否继承原下拉框的样式类，此处设为继承
    /*placeholder_text_single: '',*/ //单选选择框的默认提示信息，当选项为空时会显示。如果原下拉框设置了data-placeholder，会覆盖这里的值。

    /*max_shown_results: 7,*/ //下拉框最大显示选项数量
    display_disabled_options: false,
    single_backstroke_delete: false, //false表示按两次删除键才能删除选项，true表示按一次删除键即可删除
    case_sensitive_search: false, //搜索大小写敏感。此处设为不敏感
    group_search: false, //选项组是否可搜。此处搜索不可搜
    include_group_label_in_selected: true //选中选项是否显示选项分组。false不显示，true显示。默认false。
}).change(function () {

    department = $(".registerSearch_1 option:selected").val();
});
$('.registerSearch_2').chosen({disable_search: true, allow_single_deselect: true,}).change(function () {

    registerType = $(".registerSearch_2 option:selected").val();
});

function searchRegisterRecord() {

    $('#registerRecord').bootstrapTable('destroy');
    //1.初始化Table
    var oTable = new TableInit();
    oTable.Init();

    //2.初始化Button的点击事件
    var oButtonInit = new ButtonInit();
    oButtonInit.Init();

}