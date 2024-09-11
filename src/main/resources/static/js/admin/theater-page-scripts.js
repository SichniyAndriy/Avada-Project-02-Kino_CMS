const THEATERS_PATH = "/admin/theaters"


$(event => {
    $("#header__title").text("Сторінка кінотеатра");
    $("#sidebar-menu .nav-link.active").removeClass("active");
    $("#theaters__link").addClass("active");
});

function showAuditorium(id = 0) {
    location.href = `${THEATERS_PATH}/show/auditorium/${id}`
}