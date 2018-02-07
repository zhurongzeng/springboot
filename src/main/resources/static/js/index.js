$(function () {
    initIFrame();
    showMenu('/menu/service/menuList', '');
});

var menuClick = function (menuUrl) {
    $("#iframe-page-content").attr('src', menuUrl);
};

var initIFrame = function () {
    var height = document.documentElement.clientHeight - document.getElementById("nav-header").clientHeight;
    document.getElementById('iframe-page-content').style.height = height + 'px';
};

/**
 * 生成菜单
 */
var showMenu = function (url, menuHtml) {
    $.ajax({
        url: url,
        type: "get",
        success: function (data) {
            $.each(data, function (i, parent) {
                menuHtml += '<li class="panel panel-default dropdown">'
                    + '<a data-toggle="collapse" href="#dropdown-element' + parent.id + '">'
                    + '<span class="icon ' + parent.icon + '"></span><span class="title">' + parent.name + '</span>'
                    + '</a>'
                    + '<div id="dropdown-element' + parent.id + '" class="panel-collapse collapse">'
                    + '<div class="panel-body">'
                    + '<ul class="nav navbar-nav">';
                $.each(parent.children, function (j, child) {
                    menuHtml += '<li>'
                        + '<a href="#" onclick="menuClick(\'' + child.url + '\')">'
                        + '<span class="icon ' + child.icon + '"></span>' + child.name
                        + '</a>'
                        + '</li>';
                });
                menuHtml += '</ul>'
                    + '</div>'
                    + '</div>'
                    + '</li>';
            });
            $("#menu").append(menuHtml);
            $('.navbar-nav li').click(function (e) {
                e.preventDefault();
                $('li').removeClass('active');
                $(this).addClass('active');
            });
        }
    });
};