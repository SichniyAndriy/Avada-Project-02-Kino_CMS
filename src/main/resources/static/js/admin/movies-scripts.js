const FILMS_PATH = "/admin/movies";

$(event => {
    $("#header__title").text("Списки фільмів");
    $("#sidebar-menu .nav-link.active").removeClass("active");
    $("#movies__link").addClass("active");
    loadPictures();
});

function showMovie(id=0) {
    window.location.href = `${FILMS_PATH}/show/${id}`;
}

function loadPictures() {
    const loadLazy = function(entries, observer) {
        for (const entry of entries) {
            if (entry.isIntersecting) {
                const $img = $(entry.target);
                const path = $img.data("src");
                $img.attr("src", path);
                $img.removeData("src").removeClass("lazy");
                observer.unobserve($img[0]);
            }
        }
    }

    const observer = new IntersectionObserver(loadLazy, {
        root: null,
        rootMargin: "0px",
        threshold: 0.1
    });

    $("img.lazy").each((i, img) => observer.observe(img));
}