$(() => {
    $("#header__title").text("Сторінка новини");
    $("#sidebar-menu .nav-link.active").removeClass("active");
    $("#news__link").addClass("active");
})

$("#news__status").on("change", (event) => {
    $('label[for="news__status"]').text(event.target.checked ? "ON" : "OFF");
});
