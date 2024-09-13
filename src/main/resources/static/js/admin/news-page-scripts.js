$(() => {
    $("#header__title").text("Сторінка новини");
    $("#sidebar-menu .nav-link.active").removeClass("active");
    $("#news__link").addClass("active");
})

$("#news__status").on("change", (event) => {
    const label = $('label[for="news__status"]');
    console.log(event.target.checked);
    label.text(event.target.checked ? "ON" : "OFF");
});
