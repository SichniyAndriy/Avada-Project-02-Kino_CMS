// @ts-nocheck
$( () => {
    $("#header__title").text("Редагування інформації про контакти");
    $("#sidebar-special").addClass("show");
    $(".download__btn").on("click", (event) => {
        $(event.target).parent().prev().trigger("click");
    });
    $("input[type='file']").on("change", (event) => showPicture(event.target));
    $(".delete__btn").on("click", (event) => clearPicture(event.target));
    $("#save__btn").on("click", event => {
        const forms = $("form").get();
        updateContacts(forms);
    })
    $("#contacts_status").on("change", (event) => {
        const $Switcher = $(event.target);
        const status = $Switcher.prop("checked");
        $Switcher.next().text( status ? "ON": "OFF");

        const $Forms = $("form");
        const $Elems = $Forms.find("input").add($Forms.find("textarea"));
        $Elems.prop("disabled", !status);
    }).trigger("change");
})

function showPicture(elem) {
    const file = elem.files[0];
    if (file) {
        const fileReader = new FileReader();
        fileReader.onload = event => {
            $(elem)
                .prev().children("img")
                .attr("src", event.target.result);
        }

        fileReader.readAsDataURL(file);
    }
}

function clearPicture(elem) {
    const $div = $(elem).parent();
    $div.prev()[0].files[0] = null;
    $div.prev().prev().children("img").attr("src", "");
}

function updateContacts(forms) {
    const arr = [];
    for (const form of forms) {
        const obj = {};
        for (const elem of form.elements) {
            console.log(elem.name, elem.value);
            obj[elem.name] = elem.value;
        }
        arr.push(obj); 
    }
    const jsonArr = JSON.stringify(arr);
    fetch("/admin/edit/contacts", {
        method: "POST",
        headers: {
            "Content-Type":"application/json"
        },
        body: jsonArr
    }).then(responce => {
        if (responce.ok) {
            alert("Data updated");
        }
    })
}