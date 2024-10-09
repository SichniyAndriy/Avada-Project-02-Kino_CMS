// @ts-nocheck
$(() => {
    $("#header__title").text("Сторінка користувача");
    $("#sidebar-menu .nav-link.active").removeClass("active");
    $("#users__link").addClass("active");
    $("div.d-flex").addClass("align-items-baseline").addClass("gap-3");
    $("div.d-flex > label").addClass("w-25").addClass("text-end").addClass("me-3");
    $("div.d-flex > input").addClass("w-75");
});

function goToUsers() {
    sessionStorage.clear();
    location.href = USERS_PATH;
}

$("form").on("submit", (event) => {
    event.preventDefault();

    const gender = $("#gender_male__input").attr("checked") ? "MALE" : "FEMALE";
    const language = $("#language_ua__input").attr("checked") ? "UKRAINIAN" : "ENGLISH";
    const firstName = $("#first_name__input").val();
    const lastName = $("#last_name__input").val();
    const nickName = $("#nick_name__input").val();
    const phone = $("#phone__input").val();
    const email = $("#email__input").val();
    const birthDate = $("#birth_date__input").val();
    const password = $("#password__input").val();
    const cardNumber = $("#card_number__input").val();
    const addressInput = $("#address__input").val();
    const arr = addressInput.split(',');
    arr.forEach((item, i) => arr[i] = item.trim());

    const user = {
        id: sessionStorage.getItem("userId"),
        firstName,
        lastName,
        nickName,
        phone,
        email,
        birthDate,
        registrationDate: sessionStorage.getItem("registrationDate"),
        password,
        cardNumber,
        gender,
        language,
        address: {
            id: sessionStorage.getItem("addressId"),
            city: arr[0] || null,
            zipCode: sessionStorage.getItem("zipCode"),
            street: arr[1] || null,
            houseNumber: arr[2] || null,
            flatNumber: arr[4] || null
        }
    }

    fetch("/admin/users/save", {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify(user)
    }).then(response => {
        if (response.ok) {
            goToUsers();
        } else {
            alert("Дані не вірні. Перевірте поля");
        }
    });
});
