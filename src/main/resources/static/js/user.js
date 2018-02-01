$(function () {
    //1.初始化Table
    var oTable = new TableInit();
    oTable.Init();

    //2.初始化Button的点击事件
    var oButtonInit = new ButtonInit();
    oButtonInit.Init();

    addFormValidate();
});

/*************** 全局变量 start ******************/
var addViewPath = "/user/view/add";
var editViewPath = "/user/view/edit";

var pagePath = "/user/service/list";
var addSavePath = "/user/service/add";
var editSavePath = "/user/service/edit";
var deleteSavePath = "/user/service/delete";
/*************** 全局变量 end ******************/

var TableInit = function () {
    var oTableInit = new Object();
    //初始化Table
    oTableInit.Init = function () {
        $("#table_user_list").bootstrapTable({
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
                field: "username",
                title: "用户名"
            }, {
                field: "password",
                title: "密码"
            }, {
                field: "fullname",
                title: "姓名"
            }, {
                field: "Desc",
                title: "描述"
            },]
        });
    };

    //得到查询的参数
    oTableInit.queryParams = function (params) {
        var queryParams = {
            username: $("#txt_query_username").val(),
            fullname: $("#txt_query_fullname").val()
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
        $("#btn_user_add").click(function () {
            add(addViewPath);
        });

        $("#btn_user_edit").click(function () {
            edit(editViewPath);
        });

        $("#btn_user_delete").click(function () {
            remove(deleteSavePath);
        });
    };
    return oInit;
};

var addFormValidate = function () {
    $("#form_add_user").bootstrapValidator({
        live: 'enabled',
        message: '信息校验失败',
        feedbackIcons: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
            fullname: {
                message: '姓名校验失败',
                validators: {
                    notEmpty: {
                        message: '姓名不能为空'
                    },
                    stringLength: {
                        min: 2,
                        message: '姓名长度必须大于2'
                    }
                }
            },
            username: {
                message: '用户名校验失败',
                validators: {
                    notEmpty: {
                        message: '用户名不能为空'
                    },
                    stringLength: {
                        min: 6,
                        max: 30,
                        message: '用户名长度在6~30之间'
                    },
                    regexp: {
                        regexp: /^[a-zA-Z0-9_\.]+$/,
                        message: '用户名只能由字母，数字，点和下划线组成'
                    },
                    remote: {
                        delay: 1000,
                        type: 'POST',
                        url: '/user/service/validate',
                        message: '用户名已占用'
                    },
                    different: {
                        field: 'password',
                        message: '用户名和密码不能相同'
                    }
                }
            },
            password: {
                validators: {
                    notEmpty: {
                        message: '密码不能为空'
                    },
                    stringLength: {
                        min: 6,
                        max: 30,
                        message: '密码长度在6~30之间'
                    },
                    different: {
                        field: 'username',
                        message: '密码不能和用户名相同'
                    }
                }
            }
        }
    }).on('success.form.bv', function (e) {
        e.preventDefault();
        var $form = $(e.target);
        var bv = $form.data('bootstrapValidator');
        addSave();
    });
};

/**
 * 新增用户
 */
var add = function (url) {
    layer.open({
        title: "新增",
        type: 2,
        area: ["550px", "450px"],
        fixed: false,
        content: url,
        end: function () {
            $("#table_user_list").bootstrapTable("refresh");
        }
    });
};

/**
 * 新增保存
 */
var addSave = function () {
    $.ajax({
        type: "post",
        url: addSavePath,
        data: $("#form_add_user").serialize(),
        success: function (data) {
            if (data.retCode == "0000") {
                layer.alert(data.retMsg, {icon: 1}, function () {
                    parent.layer.closeAll();
                });
            } else {
                layer.alert(data.retMsg, {icon: 2});
            }
        }
    });
};

/**
 * 修改用户
 */
var edit = function (url) {
    var selections = $("#table_user_list").bootstrapTable("getSelections");
    if (selections.length > 1) {
        layer.alert("只能选择一行进行编辑！", {icon: 7});
        return;
    }
    if (selections.length <= 0) {
        layer.alert("请选择一行数据进行编辑！", {icon: 7});
        return;
    }
    var userId = selections[0].id;
    layer.open({
        title: "编辑",
        type: 2,
        area: ["768px", "450px"],
        fixed: false,
        content: url + "?id=" + userId,
        btn: ["保存", "取消"],
        yes: function (index, layero) {
            var body = layer.getChildFrame("body", index);
            $.ajax({
                type: "post",
                url: editSavePath,
                data: body.find("#form_edit_user").serialize(),
                success: function (data) {
                    if (data.retCode == "0000") {
                        layer.alert(data.retMsg, {icon: 1});
                        layer.close(index);
                    } else {
                        layer.alert(data.retMsg, {icon: 2});
                    }
                }
            });
        },
        btn2: function (index, layero) {
            layer.close(index);
        },
        end: function () {
            $("#table_user_list").bootstrapTable("refresh");
        }
    });
};

/**
 * 删除用户
 */
var remove = function (url) {
    var selections = $("#table_user_list").bootstrapTable("getSelections");
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
                        $("#table_user_list").bootstrapTable("refresh");
                    } else {
                        layer.alert("删除失败!", {icon: 2});
                    }
                }
            });
            layer.close(index);
        });
};
