$(function () {
    var height = document.documentElement.clientHeight - document.getElementById("nav-header").clientHeight;
    document.getElementById('iframe-page-content').style.height = height + 'px';

    $('.navbar-nav li').click(function (e) {
        e.preventDefault();
        $('li').removeClass('active');
        $(this).addClass('active');
    });

    var menuArry = [
        {id: 1, name: "办公管理", pid: 0},
        {id: 2, name: "请假申请", pid: 1},
        {id: 3, name: "出差申请", pid: 1},
        {id: 4, name: "请假记录", pid: 2},
        {id: 5, name: "系统设置", pid: 0},
        {id: 6, name: "权限管理", pid: 5},
        {id: 7, name: "用户角色", pid: 6},
        {id: 8, name: "菜单设置", pid: 6},
    ];
    GetData(0, menuArry);
    $("#menu").append(menus);
});

function menuClick(menuUrl) {
    $("#iframe-page-content").attr('src', menuUrl);
};

//菜单列表html
var menus = '';

//根据菜单主键id生成菜单列表html
//id：菜单主键id
//arry：菜单数组信息
function GetData(id, arry) {
    var childArry = GetParentArry(id, arry);
    if (childArry.length > 0) {
        for (var i in childArry) {
            menus += '<li class="panel panel-default dropdown">'
                + '<a data-toggle="collapse" href="#dropdown-element' + childArry[i].id + '">'
                + '<span class="icon fa fa-gears"></span><span class="title">' + childArry[i].name + '</span>'
                + '</a>'
                + '<div id="dropdown-element' + childArry[i].id + '" class="panel-collapse collapse">'
                + '<div class="panel-body">'
                + '<ul class="nav navbar-nav">';
            for (var j = 0; j < 3; j++) {
                menus += '<li>'
                    + '<a href="#" onclick="menuClick(\'/user/view/list\')">'
                    + '<span class="icon fa fa-user"></span>用户管理'
                    + '</a>'
                    + '</li>';
            }
            menus += '</ul>'
                + '</div>'
                + '</div>'
                + '</li>';
        }
        menus += '</li>';
    }
}

//根据菜单主键id获取下级菜单
//id：菜单主键id
//arry：菜单数组信息
function GetParentArry(id, arry) {
    var newArry = new Array();
    for (var i in arry) {
        if (arry[i].pid == id)
            newArry.push(arry[i]);
    }
    return newArry;
}