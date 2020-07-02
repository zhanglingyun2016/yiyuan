$(window).preloader();

var drugType = '';
var efficacyClassification = '';
var drug = '';

$(function () {

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
                $.ajax({
                    url: "/drugstore/getDrugInfor",
                    type: "post",
                    data: {
                        drug: drug
                    },
                    success: function (data) {
                        $("#drugInfo_type").val(data.drugType);
                        $("#drugInfo_efficacyClassification").val(data.efficacyClassification);
                        $("#drugInfo_specification").val(data.specification);
                        $("#drugInfo_manufacturer").val(data.manufacturer);
                        $("#drugInfo_storageQuantity").val(data.storageQuantity)
                    }
                })
            });
        }
    })

});


var limitStatus = '';
$('.limitStatusSelect').chosen({disable_search: true, allow_single_deselect: true, width: '90px'}).change(function () {

    limitStatus = $(".limitStatusSelect option:selected").val();
});

function addNewDrug() {


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
        storageQuantity: $("#storageQuantity").val(),
        productionDate: $(".productionDate").val(),
        qualityDate: $(".qualityDate").val(),
    };
    $.ajax({
        url: "/drugstore/addNewDrug",
        type: "post",
        contentType: "application/json",
        data: JSON.stringify(DrugReqVO),
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

function addDrugType() {
    var drugType = $("#add_drug_type").val();

    if (drugType == null || drugType === '') {
        swal("请填写剂型！", "", "error")
    }

    $.ajax({
        url: "/drugstore/addDrugType",
        type: "post",
        data: {
            "drugType": drugType
        },
        success: function (data) {

            if (data !== null && data.status === 1) {
                swal("添加成功！", "", "success");

            } else {
                swal(data.message, "", "error")
            }

        }

    })
}

function addEfficacyClassification() {
    var efficacyClassification = $("#add_efficacyClassification").val();

    if (efficacyClassification == null || efficacyClassification === '') {
        swal("请填写药品功效！", "", "error")
    }

    $.ajax({
        url: "/drugstore/addEfficacyClassification",
        type: "post",
        data: {
            "efficacyClassification": efficacyClassification
        },
        success: function (data) {

            if (data !== null && data.status === 1) {
                swal("添加成功！", "", "success");
            } else {
                swal(data.message, "", "error")
            }

        }

    })
}

//日期选择初始化
$(".productionDate").flatpickr({
    maxDate: "today",
});
$(".qualityDate").flatpickr({
    minDate: "today",
});

function addStorageQuantity() {

    var addStorageQuantity = $("#add_StorageQuantity").val();

    $.ajax({
        url: "/drugstore/addStorageQuantity",
        type: "post",
        data: {
            drug: drug,
            addStorageQuantity: addStorageQuantity
        },
        success: function (data) {

            if (data !== null && data.status === 1) {
                swal("入库成功！", "", "success");
                $.ajax({
                    url: "/drugstore/getDrugInfor",
                    type: "post",
                    data: {
                        drug: drug
                    },
                    success: function (data) {
                        $("#drugInfo_type").val(data.drugType);
                        $("#drugInfo_efficacyClassification").val(data.efficacyClassification);
                        $("#drugInfo_specification").val(data.specification);
                        $("#drugInfo_manufacturer").val(data.manufacturer);
                        $("#drugInfo_storageQuantity").val(data.storageQuantity);
                        $("#add_StorageQuantity").val('')
                    }
                })
            } else {
                swal(data.message, "", "error")
            }

        }
    })
}