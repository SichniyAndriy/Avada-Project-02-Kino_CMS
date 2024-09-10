const FILMS_PATH = "/admin/movies";

$(event => {
    $("#header__title").text("Списки фільмів");
    $("#sidebar-menu .nav-link.active").removeClass("active");
    $("#movies__link").addClass("active");
});

function showFilm(id) {
    window.location.href = `${FILMS_PATH}/show/${id}`;
}
