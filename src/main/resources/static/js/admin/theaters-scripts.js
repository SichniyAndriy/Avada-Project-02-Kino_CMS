const THEATER_PATH = "/admin/theaters"

$(event => $("#header__title").text("Список кінотеатрів"));

function showTheater(id) {
    window.location.href = `${THEATER_PATH}/show/${id}`;
}
