const PROMOTIONS_PATH = "/admin/promotions";

$(() => {
    $("#header__title").text("Список акцій");
    $("#sidebar-menu .nav-link.active").removeClass("active");
    $("#promotions__link").addClass("active");
})

function showPromotion(id=0) {
    location.href = `${PROMOTIONS_PATH}/show/${id}`;
}

function deletePromotion(id, elem) {
    fetch(`/admin/promotions/delete/${id}`).then(responce => {
        if(responce.ok) {
            const $row = $(elem).closest("tr");
            $row.fadeOut("fast");
        }
    })
}