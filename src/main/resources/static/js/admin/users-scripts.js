const USERS_PATH = "/admin/users"

$( () => {
    $("#header__title").text("Список користувачів");
    $("#sidebar-menu .nav-link.active").removeClass("active");
    $("#users__link").addClass("active");
    $("#page__size").on("change", (event) => {
        // @ts-ignore
        pageSize = parseInt(event.target.value);
        // @ts-ignore
        goToUsers(pageNumber, pageSize);
    });
});

function showUser(id=0) {
    location.href = `${USERS_PATH}/show/${id}`;
}

function deleteUser(id) {
    fetch(`${USERS_PATH}/delete/${id}`, {
        method: "DELETE"
    }).then(responce => {
        if(responce.ok) {
            // @ts-ignore
            goToUsers(pageNumber, pageSize);
        } else {
            alert("Помилка видалення");
        }
    })
}

// @ts-ignore
function goToUsers(number = pageNumber, size = pageSize) {
    location.href = `${USERS_PATH}?number=${number}&size=${size}`;
}
