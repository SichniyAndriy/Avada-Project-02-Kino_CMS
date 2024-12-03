const form = document.getElementById( "user__from" );
form.addEventListener( "submit", ( event ) => {
    event.preventDefault();
    sendForm( event.target );
} );

function sendForm( form ) {
    const formData = new FormData( form );

    fetch( "/cabinet", {
        method: "POST",
        body: formData
    } )
        .then( responce => alert( responce.ok ? "Дані оновлено" : "Перевірте дані" ) )
        .catch( error => console.error( error ) );
}