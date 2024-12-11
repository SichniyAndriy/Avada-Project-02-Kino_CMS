const PROMOTIONS_PATH = "/KinoCMS-SichniyA/admin/promotions";

$( () => {
    $( "#header__title" ).text( "Список акцій" );
    $( "#sidebar-menu .nav-link.active" ).removeClass( "active" );
    $( "#promotions__link" ).addClass( "active" );
} );

function showPromotion( id = 0 ) {
    location.href = `${ PROMOTIONS_PATH }/show/${ id }`;
}

function deletePromotion( id, elem ) {
    fetch( `${ PROMOTIONS_PATH }/${ id }` ).then( responce => {
        if ( responce.ok ) {
            const $row = $( elem ).closest( "tr" );
            $row.fadeOut( "fast" );
        }
    } );
}