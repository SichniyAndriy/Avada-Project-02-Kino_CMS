$(() => {
    $("#header__title").text("Сторінка новини");
    $("#sidebar-menu .nav-link.active").removeClass("active");
    $("#promotion__link").addClass("active");
})

$("#promotion__status").on("change", (event) => {
    const label = $('label[for="promotion__status"]');
    console.log(event.target.checked);
    label.text(event.target.checked ? "ON" : "OFF");
});
