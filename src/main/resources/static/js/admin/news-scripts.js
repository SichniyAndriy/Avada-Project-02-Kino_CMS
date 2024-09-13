const NEWS_PATH = "/admin/news"

$(() => {
    $("#header__title").text("Список новин");
    $("#sidebar-menu .nav-link.active").removeClass("active");
    $("#news__link").addClass("active");
})


function showNews(id=0) {
    location.href = `${NEWS_PATH}/show/${id}`;
}