const THEATERS_PATH = "/admin/theaters"

$(() => {
    $("#header__title").text("Сторінка кінотеатра");
    $("#sidebar-menu .nav-link.active").removeClass("active");
    $("#theaters__link").addClass("active");
});

function showAuditorium(id = 0) {
    location.href = `${THEATERS_PATH}/show/auditorium/${id}`
}

function deleteAuditorium(audId) {
    fetch(`/admin/theaters/delete/auditorium/${audId}`, {
        method: "DELETE"
    }).then( responce => {
        if (responce.ok) {
            $(`#delete__btn_${audId}`)
            .closest("tr")
            .fadeOut("fast");
        }
    });
}
