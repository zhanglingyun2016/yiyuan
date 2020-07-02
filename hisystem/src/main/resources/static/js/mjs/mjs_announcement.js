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
    var oTableInit = new Object();
    //初始化Table
    oTableInit.Init = function () {
        $('#Announcement').bootstrapTable({
            url: '/admin/getAnnouncement',         //请求后台的URL（*）
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
            pageSize: 5,                       //每页的记录行数（*）
            queryParamsType: "",
            paginationPreText: "上一页",
            paginationNextText: "下一页",
            search: false,                       //是否显示表格搜索，此搜索是客户端搜索，不会进服务端，所以，个人感觉意义不大
            strictSearch: true,
            showColumns: false,                  //是否显示所有的列
            showRefresh: true,                  //是否显示刷新按钮
            minimumCountColumns: 1,             //最少允许的列数
            clickToSelect: false,                //是否启用点击选中行
            // height: 500,                        //行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
            uniqueId: "loginIp",                     //每一行的唯一标识，一般为主键列
            showToggle: false,                    //是否显示详细视图和列表视图的切换按钮
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
                    var pageSize = $('#Announcement').bootstrapTable('getOptions').pageSize;
                    //获取当前是第几页
                    var pageNumber = $('#Announcement').bootstrapTable('getOptions').pageNumber;
                    //返回序号，注意index是从0开始的，所以要加上1
                    return pageSize * (pageNumber - 1) + index + 1
                }

            }, {
                field: 'title',
                title: '标题',
                align: 'center',
                valign: 'middle'

            }, {
                field: 'contents',
                title: '内容',
                align: 'center',
                valign: 'middle',
                width: '60%'
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
            pageSize: params.pageSize,   //页面大小
            pageNumber: params.pageNumber - 1 //页码
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
    if (row.annStatus == 0) {

        return [

            '<button id="btn_modify" class="btn btn-outline-primary" >修改</button>&emsp;',
            '<button id="btn_delete" class="btn btn-outline-danger">删除</button>&emsp;',
            '<button id="btn_add" class="btn btn-outline-primary"  title="添加到主页">+</button>'
        ].join('');
    } else {
        return [

            '<button id="btn_modify" class="btn btn-outline-primary" >修改</button>&emsp;',
            '<button id="btn_delete" class="btn btn-outline-danger">删除</button>&emsp;',
            '<button id="btn_sub"  style="width: 35.61px;text-align: center" class="btn btn-outline-primary" title="从主页移除">-</button>'
        ].join('');
    }
}

window.operateEvents = {

    // 修改
    "click #btn_modify": function (e, value, row, index) {
        //弹出模态框
        window.location.hash = "#mymodal_2";
        $("#change_id").val(row.id);
        $("#title").val(row.title);
        $("#contents").val(row.contents);
    },
    // 删除
    'click #btn_delete': function (e, value, row, index) {
        window.location.hash = "#mymodal_1";
        $("#delete_id").val(row.id);
    },
    //添加到主页
    'click #btn_add': function (e, value, row, index) {
        window.location.hash = "#mymodal_3";
        $("#add_id").val(row.id);
    },
    //从主页移除
    'click #btn_sub': function (e, value, row, index) {
        window.location.hash = "#mymodal_4";
        $("#sub_id").val(row.id);
    }
};

//修改公告
function changeAnnouncement() {
    var AnnouncementVO = {
        id: $("#change_id").val(),
        title: $("#title").val(),
        contents: $("#contents").val()
    };

    $.ajax({
        url: "/admin/changeAnnouncement",
        type: "post",

        contentType: 'application/json',
        data: JSON.stringify(AnnouncementVO),
        success: function (data) {

            if (null != data && data.status === 1) {

                window.location.hash = "#";

                $("#Announcement").bootstrapTable('refresh');

            } else {
                $('.modalxa').html(data);
                $('.modalx').fadeIn();
                setTimeout(function () {
                    $('.modalx').fadeOut();
                }, 3000);
            }

        }
    });

}

/*添加公告*/
function addAnnouncement() {
    var AnnouncementVO = {
        title: $("#add_title").val(),
        contents: $("#add_contents").val()
    };

    $.ajax({
        url: "/admin/addAnnouncement",
        type: "post",

        contentType: 'application/json',
        data: JSON.stringify(AnnouncementVO),
        success: function (data) {

            if (null != data && data.status === 1) {

                swal({
                        title: "添加成功！",
                        type: "success"
                    },
                    function () {
                        setTimeout(function () {
                            window.location.reload()
                        }, 500)

                    });

            } else {
                swal(data.message, "", "error")
            }


        }
    });
}

/*删除公告*/
function deleteAnnouncement() {
    $.ajax({
        url: "/admin/deleteAnnouncement",
        type: "post",
        data: {
            "id": $("#delete_id").val()
        },
        success: function (data) {

            if (null != data && data.status === 1) {

                $("#Announcement").bootstrapTable('refresh');
            } else {

                $('.modalxa').html(data);
                $('.modalx').fadeIn();
                setTimeout(function () {
                    $('.modalx').fadeOut();
                }, 3000);
            }
        }
    })

}

/*添加到主页*/
function showAnnouncement() {
    var id = $("#add_id").val();
    $.ajax({
        url: "/admin/showAnnouncement",
        type: "post",
        data: {
            "id": id
        },
        success: function (data) {

            if (null != data && data.status === 1) {

                $("#Announcement").bootstrapTable('refresh');
            } else {

                $('.modalxa').html(data);
                $('.modalx').fadeIn();
                setTimeout(function () {
                    $('.modalx').fadeOut();
                }, 3000);
            }
        }
    })
}

/*从主页移除*/
function hiddenAnnouncement() {
    $.ajax({
        url: "/admin/hiddenAnnouncement",
        type: "post",
        data: {
            "id": $("#sub_id").val()
        },
        success: function (data) {

            if (null != data && data.status === 1) {

                $("#Announcement").bootstrapTable('refresh');
            } else {

                $('.modalxa').html(data);
                $('.modalx').fadeIn();
                setTimeout(function () {
                    $('.modalx').fadeOut();
                }, 3000);
            }
        }
    })
}
