
const PATH_TO_USERS = "/KinoCMS-SichniyA/admin/users";

$( () => {
    $( "#header__title" ).text( "Список користувачів" );
    $( "#sidebar-menu .nav-link.active" ).removeClass( "active" );
    $( "#users__link" ).addClass( "active" );
    $( "#page__size" ).on( "change", ( event ) => {
        // @ts-ignore
        pageSize = parseInt( event.target.value );
        // @ts-ignore
        goToUsers( pageNumber, pageSize );
    } );
} );

function showUser( id = 0 ) {
    location.href = `${ PATH_TO_USERS }/show/${ id }`;
}

function deleteUser( id ) {
    fetch( `${ PATH_TO_USERS }/delete/${ id }`, {
        method: "DELETE"
    } ).then( responce => {
        if ( responce.ok ) {
            // @ts-ignore
            goToUsers( pageNumber, pageSize );
        } else {
            alert( "Помилка видалення" );
        }
    } );
}

// @ts-ignore
function goToUsers( number = pageNumber, size = pageSize ) {
    location.href = `${ PATH_TO_USERS }?number=${ number }&size=${ size }`;
}
