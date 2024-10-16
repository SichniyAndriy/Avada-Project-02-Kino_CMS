// @ts-nocheck
$(() => {
    $("#header__title").text("Сторінка акції");
    $("#sidebar-menu .nav-link.active").removeClass("active");
    $("#promotion__link").addClass("active");
    $("#promotion__status").on("change", (event) => {
        $('label[for="promotion__status"]').text(event.target.checked ? "ON" : "OFF");
    });
    $("#download__btn").on("click", event => {
        $("#picture__input").trigger("click");
    });
    $("#picture__input").on("change", event => {
        showPicture(event.target);
    });
    $("#promotion__form").on("submit", event => {
        event.preventDefault();
        savePromotion(event.target);
    });
    $("#promotion_status").on("change", (event) => {
        const $Switcher = $(event.target);
        const status = $Switcher.prop("checked");
        $Switcher.next().text( status ? "ON": "OFF");

        const $Forms = $("form");
        const $Elems = $Forms.find("input").add($Forms.find("textarea"));
        $Elems.prop("disabled", !status);
    }).trigger("change");
});

function showPicture (elem) {
    const file = elem.files[0];
    if (file) {
        const fileReader = new FileReader();
        fileReader.onload = ev => {
            $(elem)
                .prev().children("img")
                .attr("src", ev.target.result);
        };
        fileReader.readAsDataURL(file);
    }
}

async function savePromotion(form) {
    const formData = new FormData(form);

    const file = $("#picture__input")[0].files[0];
    const id = $("#promotion_id").val();
    const pictureUrl = file ? 
        await saveFileOnServer(file, id) : 
        $("#picture__img").attr( "src");
    formData.append("pictureUrl", pictureUrl);

    const status = $("label[for='promotion__status']").text().toUpperCase();
    formData.append("status", status);

    for (const entry of formData.entries()) {
        console.log(entry);
    }

    fetch ("/admin/promotions/save", {
        method:"POST",
        body: formData
    }).then(responce => {
        if (responce.ok) {
            alert("Promotion saved");
        }
    })
}

async function saveFileOnServer(file, id) {
    const ext = file.name.split(".")[1];

    const formData = new FormData();
    formData.append("picture", file, `promotion_${id}`);
    formData.append("timestamp", new Date().getTime());
    formData.append("ext", ext);

    const responce = await fetch("/admin/promotions/save/file", {
        method: "POST",
        body: formData
    })
    return responce.ok ? await responce.text() : "";
}