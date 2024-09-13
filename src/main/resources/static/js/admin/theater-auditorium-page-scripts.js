$(event => {
    $("#header__title").text("Сторінка зала");
    $("#sidebar-menu .nav-link.active").removeClass("active");
    $("#theaters__link").addClass("active");
});
