
const PATH_TO_REGISTER = '/KinoCMS-SichniyA/register';

const form = document.getElementById( "registerForm" );

form.addEventListener( "submit", ( event ) => {
    event.preventDefault();
    const formData = new FormData( form );
    console.log( formData );
    fetch( PATH_TO_REGISTER, {
        method: "POST",
        body: formData
    } )
        .then( response => {
            if ( response.ok ) {
                window.location.href = "/KinoCMS-SichniyA/index";
            } else {
                alert( "Error. Check form data" );
            }
        } );
} );
