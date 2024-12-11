const PATH_TO_THEATERS = "/KinoCMS-SichniyA/admin/theaters";

$( event => {
    $( "#header__title" ).text( "Список кінотеатрів" );
    $( "#sidebar-menu .nav-link.active" ).removeClass( "active" );
    $( "#theaters__link" ).addClass( "active" );
} );

function showTheater( id = 0 ) {
    window.location.href = `${ PATH_TO_THEATERS }/show/${ id }`;
}

function addNew() {
    window.location.href = `${ PATH_TO_THEATERS }/show/0`;
}

function deleteTheater( id ) {
    window.location.href = `${ PATH_TO_THEATERS }/delete/${ id }`;
}
