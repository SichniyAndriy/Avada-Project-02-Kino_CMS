$(event => {
    $("#header__title").text("Сторінка кінотеатра");
    $("#sidebar-menu .nav-link.active").removeClass("active");
    $("#theaters__link").addClass("active");
});
