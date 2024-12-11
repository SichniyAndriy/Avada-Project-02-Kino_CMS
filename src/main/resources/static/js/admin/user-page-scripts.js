// @ts-nocheck
const PATH_TO_USERS = "/KinoCMS-SichniyA/admin/users";

$( () => {
    $( "#header__title" ).text( "Сторінка користувача" );
    $( "#sidebar-menu .nav-link.active" ).removeClass( "active" );
    $( "#users__link" ).addClass( "active" );
} );

function goToUsers() {
    location.href = PATH_TO_USERS;
}

$( "#user__form" ).on( "submit", ( event ) => {
    event.preventDefault();
    const form = event.target;
    const formData = new FormData( form );

    for ( const [ key, value ] of formData.entries() ) {
        console.log( key, value );
    }

    fetch( `${ PATH_TO_USERS }/save`, {
        method: "POST",
        body: formData
    } ).then( response => {
        if ( response.ok ) {
            goToUsers();
        } else {
            alert( "Дані не вірні. Перевірте поля" );
        }
    } );
} );
