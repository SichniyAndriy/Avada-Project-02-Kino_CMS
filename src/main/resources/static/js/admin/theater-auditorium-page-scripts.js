// @ts-nocheck
const PATH_TO_THEATERS = "/admin/theaters";

$(() => {
    $("#header__title").text("Сторінка зала");
    $("#sidebar-menu .nav-link.active").removeClass("active");
    $("#theaters__link").addClass("active");
    // JS Scheme block
    $("#download_scheme__btn").on("click", () => $("#scheme__input").trigger("click"));
    $("#delete_scheme__btn").on("click", () => {
        $("#scheme_block>div>img").attr("src", "");
        $("#scheme_block>div>input")[0].files[0] = null;
    });
    $("#scheme__input").on("change", event => showPicture(event.target));
    // JS Banner block
    $("#download_banner__btn").on("click", () => $("#banner__input").trigger("click"));
    $("#delete_banner__btn").on("click", () => {
        $("#banner_block>div>img").attr("src", "");
        $("#banner_block>div>input")[0].files[0] = null;
    });
    $("#banner__input").on("change", event => showPicture(event.target));
    //
    $("#add_picture_card").on("click", event => addCard(event.target));
    //
    $("#auditorium__from").on("submit", event => {
        event.preventDefault();
        saveAuditorium(event.target);
    })
});

function showPicture(elem) {
    const file = elem.files[0];
    if (file) {
        const fileReader = new FileReader();
        fileReader.onload = event => {
            $(elem)
                .prev("div").children("img")
                .attr("src", event.target.result);
        }
        fileReader.readAsDataURL(file);
    }
}

function addPictureCard(elem) {
    const count = $(elem).siblings() + 1;
    const $newCard = $(`<div class="col card py-4 mt-0" id="picture_card_${count}">
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
    $card.hide("slow", function() {
        $(this).remove();
    })
}

async function saveAuditorium(form) {
    const formData = new FormData(form);
    for (const entry of formData.entries()) {
        console.log(entry[0], entry[1]);
    }

    const id = $("#auditorium_id").val();

    const scheme = $("#scheme__input")[0].files[0];
    const schemeUrl = scheme ? 
        await uploadFileToServer(scheme, "scheme", id) : 
        $("#scheme_block img").attr("src");
    formData.append("schemeUrl", schemeUrl);

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

    fetch ("/admin/theaters/save/auditorium", {
        method: "POST",
        body: formData
    }).then(responce => {
        const elem = document.getElementById("theater_id");
        const thId = parseInt(elem.value);
        location.href = `${PATH_TO_THEATERS}/show/${thId}`;
    })
}

async function uploadFileToServer(file, name, id) {
    const ext = file.name.split(".")[1];
    const formData = new FormData();
    formData.append("file", file, `${name}_${id}`);
    formData.append("timestamp", new Date().getTime());
    formData.append("ext", ext);

    const responce = await fetch("/admin/theaters/save/auditorium/file", {
        method: "POST",
        body: formData
    });
    return responce.ok ?  await responce.text() : "";
}