// @ts-nocheck
$( () => {
    $( "#header__title" ).text( "Редагування головної сторінки" );
    $( "#sidebar-special" ).addClass( "show" );
    $( "#info__form" ).on( "submit", ( event ) => {
        event.preventDefault();
        updateInfo( event.target );
    } );
    $( "#main_page_status" ).on( "change", ( event ) => {
        const $Switcher = $( event.target );
        const status = $Switcher.prop( "checked" );
        $Switcher.next().text( status ? "ON" : "OFF" );

        const $Forms = $( "form" );
        const $Elems = $Forms.find( "input" ).add( $Forms.find( "textarea" ) );
        $Elems.prop( "disabled", !status );
    } ).trigger( "change" );
} );

function updateInfo( form ) {
    const formData = new FormData( form );

    for ( const entry of formData.entries() ) {
        console.log( entry );
        const [ key, value ] = entry;
        console.log( `${ key } : ${ value }` );
    }

    fetch( "/KinoCMS-SichniyA/admin/edit/main", {
        method: "POST",
        body: formData
    } ).then( responce => {
        console.log( `${ responce.status } ${ responce.statusText }` );
        if ( responce.ok ) {
            alert( "Data updated" );
        }
    } );
}
