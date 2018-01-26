$(function () {
    //1.初始化Table
    var oTable = new TableInit();
    oTable.Init();

    //2.初始化Button的点击事件
    var oButtonInit = new ButtonInit();
    oButtonInit.Init();

    //3.初始化Modal事件
    var oModalInit = new ModalInit();
    oModalInit.Init();
});

var TableInit = function () {
    var oTableInit = new Object();
    //初始化Table
    oTableInit.Init = function () {
        $('#table_user_list').bootstrapTable({
            url: '/user/service/list',         //请求后台的URL（*）
            method: 'get',                      //请求方式（*）
            toolbar: '#toolbar',                //工具按钮用哪个容器
            striped: true,                      //是否显示行间隔色
            cache: false,                       //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
            pagination: true,                   //是否显示分页（*）
            sortable: false,                     //是否启用排序
            sortOrder: "asc",                   //排序方式
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
            uniqueId: "ID",                     //每一行的唯一标识，一般为主键列
            showToggle: true,                    //是否显示详细视图和列表视图的切换按钮
            cardView: false,                    //是否显示详细视图
            detailView: false,                   //是否显示父子表
            columns: [{
                checkbox: true
            }, {
                field: 'id',
                visible: false
            }, {
                field: 'username',
                title: '用户名'
            }, {
                field: 'password',
                title: '密码'
            }, {
                field: 'fullname',
                title: '姓名'
            }, {
                field: 'Desc',
                title: '描述'
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
            add('/user/view/add');
        });

        $("#btn_user_edit").click(function () {
            edit('/user/view/edit');
        });

        $("#btn_user_delete").click(function () {
            remove('/user/service/delete');
        });
    };
    return oInit;
};

var ModalInit = function () {
    var oModalInit = new Object();
    oModalInit.Init = function () {
        $('#modal_user').on('shown.bs.modal', function (e) {
            // 关键代码，如没将modal设置为 block，则$modala_dialog.height() 为零
            $(this).css('display', 'block');
            var modalHeight = ($(window).height() - $('#modal_user .modal-dialog').height()) / 2;
            $(this).find('.modal-dialog').css({
                'margin-top': modalHeight
            });
        });
        $("#modal_user").on("hide.bs.modal", function () {
            $(this).removeData("bs.modal");
            // $("#table_user_list").bootstrapTable('refresh');
            location.reload();
        });
    }
    return oModalInit;
};

/**
 * 新增用户
 */
var add = function (url) {
    layer.open({
        title: '<strong>新增用户</strong>',
        type: 2,
        area: ['768px', '450px'],
        fixed: false,
        content: url,
        btn: ['保存', '取消'],
        yes: function (index, layero) {
            var body = layer.getChildFrame("body", index);
            $.ajax({
                type: "post",
                url: "/user/service/add",
                data: body.find('#form_add_user').serialize(),
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
            $("#table_user_list").bootstrapTable('refresh');
        }
    });
};

/**
 * 修改用户
 */
var edit = function (url) {
    var selections = $("#table_user_list").bootstrapTable('getSelections');
    if (selections.length > 1) {
        layer.alert('只能选择一行进行编辑！', {icon: 7});
        return;
    }
    if (selections.length <= 0) {
        layer.alert('请选择一行数据进行编辑！', {icon: 7});
        return;
    }
    var userId = selections[0].id;
    layer.open({
        title: '<strong>修改用户</strong>',
        type: 2,
        area: ['768px', '450px'],
        fixed: false,
        content: url + "?userId=" + userId,
        btn: ['保存', '取消'],
        yes: function (index, layero) {
            var body = layer.getChildFrame("body", index);
            $.ajax({
                type: "post",
                url: "/user/service/edit",
                data: body.find('#form_edit_user').serialize(),
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
            $("#table_user_list").bootstrapTable('refresh');
        }
    });
};

/**
 * 删除用户
 */
var remove = function (url) {
    var selections = $("#table_user_list").bootstrapTable('getSelections');
    if (selections.length == 0) {
        layer.alert('请至少选择一条记录进行操作!', {icon: 7});
        return;
    }
    var ids = [];
    for (var i = 0; i < selections.length; i++) {
        ids.push(selections[i].id);
    }
    layer.confirm('确定要删除吗?', {icon: 3, title: '提示'},
        function (index) {
            $.ajax({
                url: url,
                method: 'post',
                dataType: "json",
                contentType: 'application/json;charset=UTF-8',
                data: JSON.stringify(ids),
                success: function (data) {
                    if (data.retCode == "0000") {
                        layer.alert("删除成功，共删除" + data.retData + "条记录！", {icon: 1});
                        $("#table_user_list").bootstrapTable('refresh');
                    } else {
                        layer.alert("删除失败!", {icon: 2});
                    }
                }
            });
            layer.close(index);
        });
};
