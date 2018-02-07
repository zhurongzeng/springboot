$(function () {
    //1.初始化Table
    var oTable = new TableInit();
    oTable.Init();

    //2.初始化Button的点击事件
    var oButtonInit = new ButtonInit();
    oButtonInit.Init();

    //3.初始化Form
    var oFormInit = new FormInit();
    oFormInit.Init();
});

/*************** 全局变量 start ******************/
var addViewPath = "/dictionary/view/add";
var editViewPath = "/dictionary/view/edit";
var subAddViewPath = "/dictionary/view/subAdd";

var pagePath = "/dictionary/service/list";
var addSavePath = "/dictionary/service/add";
var editSavePath = "/dictionary/service/edit";
var deletePath = "/dictionary/service/delete";
var checkValidPath = "/dictionary/service/validate";
/*************** 全局变量 end ******************/

/**
 * 表格初始化
 */
var TableInit = function () {
    var oTableInit = {};
    //初始化Table
    oTableInit.Init = function () {
        $("#table_dictionary_list").bootstrapTable({
            url: pagePath,
            method: "get",
            toolbar: "#toolbar",
            striped: true,
            cache: false,
            pagination: true,
            sidePagination: "server",
            pageNumber: 1,
            pageSize: 10,
            pageList: [10, 25, 50, 100],
            showColumns: true,
            showRefresh: true,
            clickToSelect: true,
            uniqueId: "id",
            showToggle: true,
            detailView: true,
            queryParams: function (params) {
                var queryParams;
                queryParams = {
                    code: $("#txt_query_code").val(),
                    name: $("#txt_query_name").val(),
                    type: "1"
                };
                return {
                    limit: params.limit,
                    offset: params.offset,
                    queryParams: JSON.stringify(queryParams)
                };
            },
            columns: [{
                checkbox: true
            }, {
                field: "id",
                visible: false
            }, {
                field: "name",
                title: "名称"
            }, {
                field: "code",
                title: "编码"
            }, {
                field: "type",
                title: "类别",
                formatter: function (value, row, index) {
                    return dataFormat("type", value);
                }
            }, {
                field: "status",
                title: "状态",
                formatter: function (value, row, index) {
                    return dataFormat("status", value);
                }
            }, {
                field: "remark",
                title: "备注"
            }, {
                field: 'operation',
                title: '操作',
                events: oTableInit.InitEvents,
                formatter: oTableInit.InitOperation
            }],
            onExpandRow: function (index, row, $detail) {
                oTableInit.InitSubTable(index, row, $detail);
            }
        });
    };

    //初始化子表格(无限循环)
    oTableInit.InitSubTable = function (index, row, $detail) {
        var subTable = $detail.html('<table></table>').find('table');
        $(subTable).bootstrapTable({
            data: row.children,
            striped: true,
            cache: false,
            pagination: true,
            detailView: true,
            checkboxHeader: false,
            pageNumber: 1,
            pageSize: 10,
            pageList: [10, 25, 50, 100],
            clickToSelect: true,
            uniqueId: "id",
            columns: [{
                checkbox: true
            }, {
                field: "id",
                visible: false
            }, {
                field: "name",
                title: "名称"
            }, {
                field: "code",
                title: "编码"
            }, {
                field: "type",
                title: "类别",
                formatter: function (value, row, index) {
                    return dataFormat("type", value);
                }
            }, {
                field: "status",
                title: "状态",
                formatter: function (value, row, index) {
                    return dataFormat("status", value);
                }
            }, {
                field: "remark",
                title: "备注"
            }, {
                field: 'operation',
                title: '操作',
                events: oTableInit.InitEvents,
                formatter: oTableInit.InitOperation
            }],
            onExpandRow: function (index, row, $Subdetail) {
                oTableInit.InitSubTable(index, row, $Subdetail);
            }
        });
    };

    oTableInit.InitOperation = function operateFormatter(value, row, index) {
        var buttons = [];
        if (row.type === '1') {
            buttons.push('<button type="button" class="subAdd btn btn-primary btn-xs" style="margin:0 5px;">添加下级</button>');
        }
        buttons.push('<button type="button" class="edit btn btn-primary btn-xs" style="margin:0 5px;">编辑</button>');
        return buttons.join('');
    };

    oTableInit.InitEvents = {
        'click .subAdd': function (e, value, row, index) {
            subAdd(subAddViewPath, row.id);
        },
        'click .edit': function (e, value, row, index) {
            subAdd(subAddViewPath, row.id);
        }
    };

    return oTableInit;
};

/**
 * 按钮初始化
 */
var ButtonInit = function () {
    var oInit = {};
    oInit.Init = function () {
        $("#btn_dictionary_add").click(function () {
            add(addViewPath);
        });

        $("#btn_dictionary_edit").click(function () {
            edit(editViewPath);
        });

        $("#btn_dictionary_delete").click(function () {
            remove(deletePath);
        });
    };
    return oInit;
};

/**
 * 表单初始化
 */
var FormInit = function () {
    var oInit = {};
    oInit.Init = function () {
        $("form").bootstrapValidator({
            live: "enabled",
            message: "信息校验失败",
            feedbackIcons: {
                valid: "glyphicon glyphicon-ok",
                invalid: "glyphicon glyphicon-remove",
                validating: "glyphicon glyphicon-refresh"
            },
            fields: {
                name: {
                    message: "字典名称校验失败",
                    validators: {
                        notEmpty: {
                            message: "字典名称不能为空"
                        }
                    }
                },
                code: {
                    message: "字典编码校验失败",
                    validators: {
                        notEmpty: {
                            message: "字典编码不能为空"
                        },
                        regexp: {
                            regexp: /^[a-zA-Z0-9_.]+$/,
                            message: "字典编码只能由字母，数字，点和下划线组成"
                        },
                        remote: {
                            delay: 1000,
                            type: "POST",
                            url: checkValidPath,
                            message: "字典编码已存在"
                        }
                    }
                }
            }
        }).on("click", "[type=submit]", function (e) {
            // 编辑时取消编码校验
            $("#form_edit_dictionary").bootstrapValidator("enableFieldValidators", "code", false);
        }).on("success.form.bv", function (e) {
            e.preventDefault();
            var formId = e.target.getAttribute("id");
            if (formId === "form_add_dictionary") {
                addSave();
            } else if (formId === "form_edit_dictionary") {
                editSave();
            } else if (formId === "form_subadd_dictionary") {
                subAddSave();
            }
        });
    };
    return oInit;
}

/**
 * 新增用户
 */
var add = function (url) {
    layer.open({
        title: "新增",
        type: 2,
        area: ["550px", "560px"],
        fixed: false,
        content: url,
        end: function () {
            $("#table_dictionary_list").bootstrapTable("refresh");
        }
    });
};

/**
 * 新增保存
 */
var addSave = function () {
    // 移除下拉菜单disabled属性
    $("#txt_add_type").removeAttr("disabled");
    $.ajax({
        type: "post",
        url: addSavePath,
        data: $("#form_add_dictionary").serialize(),
        success: function (data) {
            if (data.retCode === "0000") {
                layer.alert("保存成功", {icon: 1}, function () {
                    parent.layer.closeAll();
                });
            } else {
                layer.alert("保存失败", {icon: 2});
            }
        }
    });
};

/**
 * 修改
 */
var edit = function (url) {
    var selections = $("#table_dictionary_list").bootstrapTable("getSelections");
    if (selections.length > 1) {
        layer.alert("只能选择一行进行编辑！", {icon: 7});
        return;
    }
    if (selections.length <= 0) {
        layer.alert("请选择一行数据进行编辑！", {icon: 7});
        return;
    }
    var dictionaryId = selections[0].id;
    layer.open({
        title: "修改",
        type: 2,
        area: ["550px", "560px"],
        fixed: false,
        content: url + "?id=" + dictionaryId,
        end: function () {
            $("#table_dictionary_list").bootstrapTable("refresh");
        }
    });
};

/**
 * 保存修改
 */
var editSave = function () {
    // 移除下拉菜单disabled属性
    $("#txt_edit_type").removeAttr("disabled");
    $.ajax({
        type: "post",
        url: editSavePath,
        data: $("#form_edit_dictionary").serialize(),
        success: function (data) {
            if (data.retCode === "0000") {
                layer.alert("保存成功", {icon: 1}, function () {
                    parent.layer.closeAll();
                });
            } else {
                layer.alert("保存失败", {icon: 2});
            }
        }
    });
};

/**
 * 删除
 */
var remove = function (url) {
    var selections = $("#table_dictionary_list").bootstrapTable("getSelections");
    if (selections.length === 0) {
        layer.alert("请至少选择一条记录进行操作!", {icon: 7});
        return;
    }
    var ids = [];
    for (var i = 0; i < selections.length; i++) {
        ids.push(selections[i].id);
    }
    layer.confirm("确定要删除吗?", {icon: 3, title: "提示"},
        function (index) {
            $.ajax({
                url: url,
                method: "delete",
                dataType: "json",
                contentType: "application/json;charset=UTF-8",
                data: JSON.stringify(ids),
                success: function (data) {
                    if (data.retCode === "0000") {
                        layer.alert("删除成功，共删除" + data.retData + "条记录！", {icon: 1});
                        $("#table_dictionary_list").bootstrapTable("refresh");
                    } else {
                        layer.alert("删除失败!", {icon: 2});
                    }
                }
            });
            layer.close(index);
        });
};

var subAdd = function (url, id) {
    layer.open({
        title: "字典值",
        type: 2,
        area: ["550px", "560px"],
        fixed: false,
        content: url + "?id=" + id,
        end: function () {
            $("#table_dictionary_list").bootstrapTable("refresh");
        }
    });
};

/**
 * 保存下级
 */
var subAddSave = function () {
    $.ajax({
        type: "post",
        url: addSavePath,
        data: $("#form_subadd_dictionary").serialize(),
        success: function (data) {
            if (data.retCode === "0000") {
                layer.alert("保存成功", {icon: 1}, function () {
                    parent.layer.closeAll();
                });
            } else {
                layer.alert("保存失败", {icon: 2});
            }
        }
    });
};