// @ts-nocheck
$(() => {
    $("#header__title").text("Сторінка новини");
    $("#sidebar-menu .nav-link.active").removeClass("active");
    $("#news__link").addClass("active");
    $("#download_picture__btn").on("click", triggerInputChange);
    $("#picture__input").on("change", showPicture);
    $("#news__form").on("submit",  async (event) => {
        event.preventDefault();
        saveNews(event.target);
    });
    $("#news_status").on("change", (event) => {
        const $Switcher = $(event.target);
        const status = $Switcher.prop("checked");
        $Switcher.next().text( status ? "ON": "OFF");
        $("form>input[type='checkbox']").val(status ? "ON": "OFF");
    }).trigger("change");
    $("#clear_picture__btn").on("click", evemt => {
        $("#news_picture").attr("src", "");
        $("picture__input")[0].files[0] = null;
    })
})

$("#news_status").on("change", (event) => {
    $('label[for="news_status"]').text(event.target.checked ? "ON" : "OFF");
});

function triggerInputChange() {
    $("#picture__input").trigger("click");
}

function goToNews() {
    location.href = "/admin/news";
}

function showPicture() {
    const $input = $("#picture__input");
    const file = $input[0].files[0];
    if(file) {
        const fileReader = new FileReader();
        fileReader.onload = (ev) => {
            const $img = $input.prev("div").children("img");
            $img.attr("src", ev.target.result);
        }
        fileReader.readAsDataURL(file);
    }
}

async function saveNews(form) {
    const formData = new FormData(form);

    let pictureUrl;
    const file = $("#picture__input")[0].files[0];
    if(file) {
        const id = parseInt($("#news_id").val());
        pictureUrl = await saveFileOnServer(file, id);
    } else {
        pictureUrl = $("#news_picture").attr("src");
    }
    formData.append("pictureUrl", pictureUrl ?? "");
    const status = $("#news_status").val().toUpperCase();
    formData.append("status", status);

    for (const pair of formData.entries()) {
        console.log(`${pair[0]}: ${pair[1]}`);
    }

    fetch("/admin/news/save", {
        method: "POST",
        body: formData
    }).then(responce => {
        if(responce.ok) {
           goToNews();
        }
    })
}

async function saveFileOnServer(file, id) {
    const ext = file.name.split(".")[1];

    const formData = new FormData();
    formData.append("picture",file,  `news_${id}`);
    formData.append("timestamp", new Date().getTime());
    formData.append("ext", ext);

    const responce = await fetch("/admin/news/save/file", {
        method: "POST",
        body: formData
    });
    return responce.ok ? await responce.text() : "";
}

function entriesToObject(entries) {
    const obj = {};
    for(let entry of entries) {
        if (entry.name.includes(".")) {
            const arr = entry.name.split(".");
            if (!obj[arr[0]]) {
                obj[arr[0]] = {};
            }
            obj[arr[0]][arr[1]] = entry.value;
        } else {
            obj[entry.name] = entry.value;
        }
    }
    return obj;
}