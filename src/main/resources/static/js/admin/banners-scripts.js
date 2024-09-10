const BANNERS_PATH = "/admin/banners";

$(event => {
    $("#header__title").text("Банери головної сторінки");
    $("#sidebar-menu .nav-link.active").removeClass("active");
    $("#banners__link").addClass("active");
});
