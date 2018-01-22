$(function () {
    var height = document.documentElement.clientHeight;
    document.getElementById('iframe-page-content').style.height = height + 'px';

    $('.navbar-nav li').click(function(e){
        e.preventDefault();
        $('li').removeClass('active');
        $(this).addClass('active');
    });
});

function menuClick(menuUrl) {
    $("#iframe-page-content").attr('src', menuUrl);
};
