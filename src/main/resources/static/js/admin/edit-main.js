// @ts-nocheck
$( () => {
    $("#header__title").text("Редагування головної сторінки");
    $("#sidebar-special").addClass("show");
    $("#info__from").on("submit", (event) => {
        event.preventDefault();
        updateInfo(event.target);
    });
})

function updateInfo(form) {
    const formData = new FormData(form);
    fetch("/admin/edit/main", {
        method: "POST",
        body: formData
    }).then(responce => {
        console.log(`${responce.status} ${responce.statusText}`);
        if (responce.ok) {
            alert("Data updated");
        }
    });
}
