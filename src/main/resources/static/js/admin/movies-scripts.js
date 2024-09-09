const FILMS_PATH = "/admin/movies";

$(event => $("#header__title").text("Списки фільмів"));

function showFilm(id) {
    window.location.href = `${FILMS_PATH}/show/${id}`;
}
