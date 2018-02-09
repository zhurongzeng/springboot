$(function () {
    //1.初始化Table
    var oTable = new TableInit();
    oTable.Init();

    //2.初始化Button的点击事件
    var oButtonInit = new ButtonInit();
    oButtonInit.Init();
});

/*************** 全局变量 start ******************/
var addViewPath = "/dictionary/view/add";
var editViewPath = "/dictionary/view/edit";
var subAddViewPath = "/dictionary/view/subAdd";

var pagePath = "/dictionary/service/list";
var addSavePath = "/dictionary/service/add";
var editSavePath = "/dictionary/service/edit";
var deletePath = "/dictionary/service/delete";
var singleDeletePath = "/dictionary/service/delete";
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
                    parent: {id: "null"}
                };
                return {
                    limit: params.limit,
                    offset: params.offset,
                    paramJson: JSON.stringify(queryParams)
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
                if (row.children.length > 0) {
                    oTableInit.InitSubTable(index, row, $detail);
                }
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
                if (row.children.length > 0) {
                    oTableInit.InitSubTable(index, row, $Subdetail);
                }
            }
        });
    };

    oTableInit.InitOperation = function operateFormatter(value, row, index) {
        var buttons = [];
        if (row.type === '1') {
            buttons.push('<button type="button" class="subAdd btn btn-primary btn-xs" style="margin:0 5px;">添加下级</button>');
        }
        buttons.push('<button type="button" class="edit btn btn-warning btn-xs" style="margin:0 5px;">编辑</button>');
        buttons.push('<button type="button" class="delete btn btn-danger btn-xs" style="margin:0 5px;">删除</button>');
        return buttons.join('');
    };

    oTableInit.InitEvents = {
        'click .subAdd': function (e, value, row, index) {
            subAdd(subAddViewPath, row.id);
        },
        'click .edit': function (e, value, row, index) {
            subAdd(editViewPath, row.id);
        },
        'click .delete': function (e, value, row, index) {
            remove(deletePath, 1, row.id);
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
        $("#btn_dictionary_query").click(function () {
            query();
        });

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

var query = function () {
    $("#table_dictionary_list").bootstrapTable("refresh");
}

/**
 * 新增用户
 */
var add = function (url) {
    layer.open({
        title: "新增",
        type: 2,
        area: ["550px", "70%"],
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
    var status = $("#txt_add_status").bootstrapSwitch("state");
    if (status == true) {
        $("#txt_add_status").val("1");
    } else {
        $("#txt_add_status").val("0");
    }
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
    var status = $("#txt_edit_status").bootstrapSwitch("state");
    if (status == true) {
        $("#txt_edit_status").val("1");
    } else {
        $("#txt_edit_status").val("0");
    }
    var formData = $("#form_edit_dictionary").serialize();
    if (status == '0') {
        formData += '&status=0';
    }
    $.ajax({
        type: "post",
        url: editSavePath,
        data: formData,
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
var remove = function (url, type, id) {
    var ids = [];
    if (type === 1) {// 单条删除
        ids.push(id);
    } else {// 批量删除
        var selections = $("#table_dictionary_list").bootstrapTable("getSelections");
        if (selections.length === 0) {
            layer.alert("请至少选择一条记录进行操作!", {icon: 7});
            return;
        }
        for (var i = 0; i < selections.length; i++) {
            ids.push(selections[i].id);
        }
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

/**
 * 添加下级
 * @param url
 * @param id
 */
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
    var status = $("#txt_subadd_status").bootstrapSwitch("state");
    if (status == true) {
        $("#txt_subadd_status").val("1");
    } else {
        $("#txt_subadd_status").val("0");
    }
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