// @ts-nocheck
$(() => {
    $("#header__title").text("Сторінка акції");
    $("#sidebar-menu .nav-link.active").removeClass("active");
    $("#promotion__link").addClass("active");
})

$("#promotion__status").on("change", (event) => {
    $('label[for="promotion__status"]').text(event.target.checked ? "ON" : "OFF");
});
