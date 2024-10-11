const FILMS_PATH = "/admin/movies";

$(event => {
    $("#header__title").text("Списки фільмів");
    $("#sidebar-menu .nav-link.active").removeClass("active");
    $("#movies__link").addClass("active");
    loadPictures();
});

function showFilm(id) {
    window.location.href = `${FILMS_PATH}/show/${id}`;
}

function loadPictures() {
    const loadLazy = (entries, observer) => {
        entries.forEach(entrie => {
            if (entrie.isIntersecting) {
                let img = entrie.target;
                img.src = img.dataset.src;
                img.classList.remove("lazy");
                img.removeAttribute("data-src");
                observer.unobserve(img);
            }
        });
    }

    const observer = new IntersectionObserver(loadLazy, {
        root: null,
        rootMargin: "0px",
        threshold: 0.1
    })

    $("img.lazy").each((i, img) => observer.observe(img));
}