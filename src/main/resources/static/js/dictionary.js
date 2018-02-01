$(function () {
    //1.初始化Table
    var oTable = new TableInit();
    oTable.Init();

    //2.初始化Button的点击事件
    var oButtonInit = new ButtonInit();
    oButtonInit.Init();

    formValidate();
});

/*************** 全局变量 start ******************/
var addViewPath = "/dictionary/view/add";
var editViewPath = "/dictionary/view/edit";

var pagePath = "/dictionary/service/list";
var addSavePath = "/dictionary/service/add";
var editSavePath = "/dictionary/service/edit";
var deletePath = "/dictionary/service/delete";
var checkValidPath = "/dictionary/service/validate";
/*************** 全局变量 end ******************/

var TableInit = function () {
    var oTableInit = new Object();
    //初始化Table
    oTableInit.Init = function () {
        $("#table_dictionary_list").bootstrapTable({
            url: pagePath,         //请求后台的URL（*）
            method: "get",                      //请求方式（*）
            toolbar: "#toolbar",                //工具按钮用哪个容器
            striped: true,                      //是否显示行间隔色
            cache: false,                       //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
            pagination: true,                   //是否显示分页（*）
            queryParams: oTableInit.queryParams,//传递参数（*）
            sidePagination: "server",           //分页方式：client客户端分页，server服务端分页（*）
            pageNumber: 1,                       //初始化加载第一页，默认第一页
            pageSize: 10,                       //每页的记录行数（*）
            pageList: [10, 25, 50, 100],        //可供选择的每页的行数（*）
            strictSearch: true,
            showColumns: true,                  //是否显示所有的列
            showRefresh: true,                  //是否显示刷新按钮
            minimumCountColumns: 2,             //最少允许的列数
            clickToSelect: true,                //是否启用点击选中行
            uniqueId: "id",                     //每一行的唯一标识，一般为主键列
            showToggle: true,                    //是否显示详细视图和列表视图的切换按钮
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
            },]
        });
    };

    //得到查询的参数
    oTableInit.queryParams = function (params) {
        var queryParams = {
            code: $("#txt_query_code").val(),
            name: $("#txt_query_name").val()
        };
        var temp = {   //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
            limit: params.limit,   //页面大小
            offset: params.offset,  //页码
            queryParams: JSON.stringify(queryParams)
        };
        return temp;
    };
    return oTableInit;
};

var ButtonInit = function () {
    var oInit = new Object();
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

var formValidate = function () {
    $("form").bootstrapValidator({
        live: 'enabled',
        message: '信息校验失败',
        feedbackIcons: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
            name: {
                message: '字典名称校验失败',
                validators: {
                    notEmpty: {
                        message: '字典名称不能为空'
                    }
                }
            },
            code: {
                message: '字典编码校验失败',
                validators: {
                    notEmpty: {
                        message: '字典编码不能为空'
                    },
                    regexp: {
                        regexp: /^[a-zA-Z0-9_\.]+$/,
                        message: '字典编码只能由字母，数字，点和下划线组成'
                    },
                    remote: {
                        delay: 1000,
                        type: 'POST',
                        url: checkValidPath,
                        message: '字典编码已存在'
                    }
                }
            }
        }
    }).on('success.form.bv', function (e) {
        e.preventDefault();
        var $form = $(e.target);
        var bv = $form.data('bootstrapValidator');
        var formId = $form.getAttribute("id");
        if(formId=="form_add_dictionary"){
            addSave();
        }else if (formId=="form_edit_dictionary"){
            editSave();
        }
    });
};

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
            if (data.retCode == "0000") {
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
 * 修改用户
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
            if (data.retCode == "0000") {
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
 * 删除用户
 */
var remove = function (url) {
    var selections = $("#table_dictionary_list").bootstrapTable("getSelections");
    if (selections.length == 0) {
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
                method: "post",
                dataType: "json",
                contentType: "application/json;charset=UTF-8",
                data: JSON.stringify(ids),
                success: function (data) {
                    if (data.retCode == "0000") {
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

var dataFormat = function (columnName, value) {
    var type = value;
    if (columnName == "type") {
        if (value == "1") {
            type = "字典项";
        } else if (value == "2") {
            type = "字典值";
        }
    } else if (columnName == "status") {
        if (value == "on") {
            type = "启用";
        } else if (value == "off") {
            type = "禁用";
        }
    }
    return type;
};