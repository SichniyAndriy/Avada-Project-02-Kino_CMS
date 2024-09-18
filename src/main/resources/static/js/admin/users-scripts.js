const USERS_PATH = "/admin/users"

$( () => {
    $("#header__title").text("Список користувачів");
    $("#sidebar-menu .nav-link.active").removeClass("active");
    $("#users__link").addClass("active");
});

function showUser(id=0) {
    location.href = `${USERS_PATH}/show/${id}`;
}

function deleteUser(id=0) {
    location.href = `${USERS_PATH}/delete/${id}`;
}
