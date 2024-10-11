// @ts-nocheck
$(() => {
    $("#header__title").text("Редагування даних фільма");
    $("#sidebar-menu .nav-link.active").removeClass("active");
    $("#movies__link").addClass("active");
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

async function saveMovieInfo() {
    const elements = $("#movie__form").serializeArray();
    const formData = new FormData();
    for (const element of elements) {
        const name = element.name;
        if (name.startsWith("_") || name.length === 0) continue;
        formData.append(name, element.value);
    }
     
    //@ts-ignore
    const posterFile = $("#poster__input")[0].files[0];
    if (posterFile) {
        posterUrl = await saveFileOnServer(posterFile, "poster");
        //@ts-ignore
        formData.append("posterUrl", posterUrl);
    }
    
    const pictures = [];
    const pictureCards = $("#add_picture_card").siblings().get();
    let i = 0;
    for (const card of pictureCards) {
        const last = card.lastElementChild;
        //@ts-ignore
        const pictureFile = last.files[0];
        ++i;
        if (pictureFile) {
            const pictureName = await saveFileOnServer(pictureFile, `picture_${i}`);
            pictures.push({ 
                id: null,
                path: pictureName 
            });
        } else {
            //@ts-ignore
            pictures.push({ 
                id: null,
                path: card.firstElementChild.firstElementChild.src 
            });
        }
    }

    const object = formDataToObject(formData);
    object["pictures"] = pictures;
    const jsoned = JSON.stringify(object);
    fetch("/admin/movies/save", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: jsoned
    }).then(responce => {
        console.log(responce.status);
        return responce.text();
    }
    ).then(data => console.log(data));
}

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

function formDataToObject(formData) {
    const obj = {};
    const entries = formData.entries();

    for (const [key, value] of entries) {
        if(key.includes(".")) {
            const arr = key.split(".");
            if (!obj[arr[0]]) {
                obj[arr[0]] = {};
            }
            obj[arr[0]][arr[1]] = value; 
        } else {
            obj[key] = value;
        }
    }

    return obj;
}
