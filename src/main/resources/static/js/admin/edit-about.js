$( () => {
    $("#header__title").text("Редагування інформаціїї про компанію");
    $("#sidebar-special").addClass("show");
    $("#contacts_status").on("change", (event) => {
        const $Switcher = $(event.target);
        const status = $Switcher.prop("checked");
        $Switcher.next().text( status ? "ON": "OFF");

        const $Forms = $("form");
        const $Elems = $Forms.find("input").add($Forms.find("textarea"));
        $Elems.prop("disabled", !status);
    });
    $("#about_status").on("change", (event) => {
        const $Switcher = $(event.target);
        const status = $Switcher.prop("checked");
        $Switcher.next().text( status ? "ON": "OFF");

        const $Forms = $("form");
        const $Elems = $Forms.find("input").add($Forms.find("textarea"));
        $Elems.prop("disabled", !status);
    }).trigger("change");
})
