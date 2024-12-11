// @ts-nocheck
const NEWS_PATH = "/KinoCMS-SichniyA/admin/news";

$( () => {
    $( "#header__title" ).text( "Список новин" );
    $( "#sidebar-menu .nav-link.active" ).removeClass( "active" );
    $( "#news__link" ).addClass( "active" );
} );

function showNews( id = 0 ) {
    location.href = `${ NEWS_PATH }/show/${ id }`;
}

async function deleteNews( id, elem ) {
    const responce = await fetch( `${ NEWS_PATH }/delete/${ id }` );
    if ( responce.ok ) {
        const $row = $( elem ).closest( "tr" );
        $row.fadeOut( "fast" );
    }
}
