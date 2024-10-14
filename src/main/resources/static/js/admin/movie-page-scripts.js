// @ts-nocheck
const PATH_TO_MOVIES = "/admin/movies";

$(() => {
    $("#header__title").text("Редагування даних фільма");
    $("#sidebar-menu .nav-link.active").removeClass("active");
    $("#movies__link").addClass("active");
    $("#movie__form").on("submit", (event) => {
        event.preventDefault();
        saveMovieInfo(event.target);
    })
    $(".form-check-input").on("change", event => {
        const $elem = $(event.target);
        const flag = $elem.attr("checked");
        $elem.attr("checked", !flag).val(!flag);
    });
});

function addPictureCard(elem) {
    const $addCardBtn = $(elem);
    const i = $addCardBtn.siblings().length + 1;
    const $newCard = $(`<div class="col card py-4 mt-0" id="picture_card_${i}">
                                <div class="mb-2 position-relative" id="img_block_${i}">
                                    <img class="img-fluid" src="../../../pictures/stumb.webp" alt="photo">
                                    <i class="bi bi-x-circle-fill fs-3 position-absolute top-0 start-100 translate-middle"
                                        onclick="deleteCard(this)"></i>
                                </div>
                                <input type="file" accept="image/*" name="picture_${i}" class="mb-2 form-control"
                                    id="picture__input_${i}" onchange="showPicture(this)">
                        </div>`);
    $newCard.insertBefore($addCardBtn);
}

function deleteCard(elem) {
    $(elem).closest(".card").remove();
}

function triggerChange(elem) {
    $("#btn_block").prev().trigger("click");
}

function showPicture(elem) {
     const file = elem.files[0];
     if (file) {
        const fileReader = new FileReader();
        fileReader.onload = ev => {
            $(elem)
            .prev()
            .children("img")
            //@ts-ignore
            .attr("src", ev.target.result);
        }

        fileReader.readAsDataURL(file);
     }
}

function deletePicture(el) {
    $("#poster_block > img").attr("src", "");
}
/////////////////////////////////////////////////////////////////////////////////////////////////////
async function saveMovieInfo(form) {
    const formData = new FormData();
    
    for (const entry of form) {
        const name = new String(entry.name);
        if (name.length === 0 || name.includes("pic") || name.startsWith("_")) continue;
        formData.append(name, entry.value);
    }

    const picturesPromise = getPictures();
    const posterUrlPromise = getPosterUrl();
    
    const picturesJson = JSON.stringify(await picturesPromise);
    formData.append("picturesJson", picturesJson);
    formData.append("posterUrl", await posterUrlPromise);

    for (const entry of formData.entries()) {
        console.log(entry);
    }
    fetch("/admin/movies/save", {
        method: "POST",
        body: formData
    }).then(responce => {
        if(responce.ok) {
            location.href = PATH_TO_MOVIES;
        } else {
            alert("Дані про фільм не збережено");
        }
    })
}

async function getPosterUrl() {
    const posterFile = $("#poster__input")[0].files[0];
    const posterUrl  = posterFile ? 
        await saveFileOnServer(posterFile, "poster") : 
        $("#poster_picture").attr("src");
    return posterUrl;
}

async function getPictures() {
    const pictures = [];
    const cards = $("#add_picture_card").siblings().get();
    let i = 0;
    for (const card of cards) {
        const last = card.lastElementChild;
        const pictureFile = last.files[0];
        ++i;
        if (pictureFile) {
            const pictureName = await saveFileOnServer(pictureFile, `picture_${i}`);
            pictures.push({ path: pictureName });
        } else {
            pictures.push({ path: card.firstElementChild.firstElementChild.src });
        }
    }
    return pictures;
}
/////////////////////////////////////////////////////////////////////////////////////////////////////
async function saveFileOnServer(file, filename) {
    const ext = file.name.split(".")[1];

    const formData = new FormData();
    formData.append("file", file, filename);
    //@ts-ignore
    formData.append("timestamp", new Date().getTime());
    formData.append("ext", ext);

    const responce = await fetch("/admin/movies/save/file", {
        method: "POST",
        body: formData
    });

    return responce.ok ? responce.text() : "";
}
