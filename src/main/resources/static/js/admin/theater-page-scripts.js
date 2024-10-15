// @ts-nocheck
const THEATERS_PATH = "/admin/theaters"

$(() => {
    $("#header__title").text("Сторінка кінотеатра");
    $("#sidebar-menu .nav-link.active").removeClass("active");
    $("#theaters__link").addClass("active");
    // JS Logo block 
    $("#download_logo__btn").on("click", () => {
        $("#logo__input").trigger("click");
    });
    $("#delete_logo__btn").on("click", (event) => {
        $("#logo_block").find("div>img").attr("src", "");
        $("#logo__input")[0].files[0] = null;
    });
    $("#logo__input").on("change", event => showPicture(event.target));
    // JS banner block
    $("#download_banner__btn").on("click", () =>   {
        $("#banner__input").trigger("click");
    });
    $("#delete_banner__btn").on("click", (event) => {
        $("#banner_block").find("div>img").attr("src", "");
        $("#banner__input")[0].files[0] = null;
    })
    $("#banner__input").on("change",event => showPicture(event.target));
    $("#theater__form").on("submit", event => {
        event.preventDefault();
        saveTheater(event.target);
    })
});

function showAuditorium(id = 0) {
    location.href = `${THEATERS_PATH}/show/auditorium/${id}`
}

function deleteAuditorium(audId) {
    fetch(`/admin/theaters/delete/auditorium/${audId}`, {
        method: "DELETE"
    }).then( responce => {
        if (responce.ok) {
            const $tr = $(`#delete__btn_${audId}`).closest("tr");
            $tr.fadeOut(500, function() {
                $(this).remove();
            });
        }
    });
}

function showPicture(input) {
    const file = input.files[0];
    if (file) {
        const fileReader = new FileReader();
        fileReader.onload = event => {
            $(input)
                .prev().children("img")
                .attr("src", event.target.result);
        }
        fileReader.readAsDataURL(file);
    }
}

function addPictureCard(elem) {
    const count = $(elem).siblings().length + 1;
    const $newCard =  $(`<div class="col card py-4 mt-0" id="picture_card_${count}">
                            <div id="img_block" class="mb-2 position-relative" id="img_block_${count}">
                                <img class="img-fluid" src="/pictures/stumb.webp" alt="photo">
                                <i class="bi bi-x-circle-fill fs-3 position-absolute top-0 start-100 translate-middle"
                                    onclick="deleteCard(this)"></i>
                            </div>
                            <input type="file" accept="image/*" class="mb-2 form-control" id="picture__input_${count}"
                                onchange=showPicture(this)>
                        </div>`);
    $newCard.insertBefore($(elem));
}

function deleteCard(elem) {
    const $card = $(elem).closest("div[id*='card']");
    $card.hide(500, function () { 
        $(this).remove();
    });
}

async function saveTheater(form) {
    const formData = new FormData(form);
    for (const entry of formData.entries()) {
        console.log(entry[0], entry[1]);
    }

    const id = $("#theater_id").val();

    const scheme = $("#logo__input")[0].files[0];
    const schemeUrl = scheme ? 
        await uploadFileToServer(scheme, "logo", id) : 
        $("#logo_block img").attr("src");
    formData.append("logoUrl", schemeUrl);

    const banner = $("#banner__input")[0].files[0];
    const bannerUrl = banner ? 
        await uploadFileToServer(banner, "banner",  id) :
        $("#banner_block img").attr("src");
    formData.append("bannerUrl", bannerUrl);

    const cards = $("#add_picture_card").siblings();
    const pictures = [];
    let i = 0;
    for (const card of cards) {
        ++i;
        const picture = $(card).children("input")[0].files[0];
        let pictureUrl = picture ?
            await uploadFileToServer(picture, `picture_${id}`, i) :
            $(card).find("img").attr("src");
        pictures.push({ path: pictureUrl });
    }
    formData.append("picturesJson", JSON.stringify(pictures));

    fetch ("/admin/theaters/save", {
        method: "POST",
        body: formData
    }).then(responce => {
        alert("Theater saved");
    })
}

async function uploadFileToServer(file, name, id) {
    const ext = file.name.split(".")[1];
    const formData = new FormData();
    formData.append("file", file, `${name}_${id}`);
    formData.append("timestamp", new Date().getTime());
    formData.append("ext", ext);

    const responce = await fetch("/admin/theaters/save/file", {
        method: "POST",
        body: formData
    });
    return responce.ok ?  await responce.text() : "";
}