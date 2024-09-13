$(() => {
    $("#header__title").text("Сторінка новини");
    $("#sidebar-menu .nav-link.active").removeClass("active");
    $("#news__link").addClass("active");
})

$("#input__status").on("change", (event) => {
    const label = $('label[for="input__status"]');
    console.log(event.target.checked);
    label.text(event.target.checked ? "ON" : "OFF");
});
