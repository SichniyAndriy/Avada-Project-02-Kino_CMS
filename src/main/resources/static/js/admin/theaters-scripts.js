const THEATER_PATH = "/admin/theaters"

$(event => {
    $("#header__title").text("Список кінотеатрів");
    $("#sidebar-menu .nav-link.active").removeClass("active");
    $("#theaters__link").addClass("active");
});

function showTheater(id = 0) {
    window.location.href = `${THEATER_PATH}/show/${id}`;
}

function addNew() {
    window.location.href = `${THEATER_PATH}/add-new`;
}

function deleteTheater(id) {
    window.location.href = `${THEATER_PATH}/delete/${id}`;
}
