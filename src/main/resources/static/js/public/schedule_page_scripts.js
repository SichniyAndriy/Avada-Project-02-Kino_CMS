const PATH_TO_SCHEDULE = '/KinoCMS-SichniyA/schedule';

$( () => {
    $( "#2D_check" )
        .add( "#3D_check" )
        .add( "#Imax_check" )
        .add( "#theater__select" )
        .add( "#dates__select" )
        .add( "#movie__select" )
        .add( "#auditorium__select" )
        .on( "change", chooseEntries );
} );

function chooseEntries() {
    const is2D = $( "#2D_check" ).prop( "checked" );
    const is3D = $( "#3D_check" ).prop( "checked" );
    const isImax = $( "#Imax_check" ).prop( "checked" );
    const theater = $( "#theater__select" ).val();
    const date = $( "#dates__select" ).val();
    const movie = $( "#movie__select" ).val();
    const auditorium = $( "#auditorium__select" ).val();

    const formData = new FormData();
    formData.append( "is2D", is2D );
    formData.append( "is3D", is3D );
    formData.append( "isImax", isImax );
    formData.append( "theater", theater );
    formData.append( "date", date );
    formData.append( "movie", movie );
    formData.append( "auditorium", auditorium );

    fetch( `${ PATH_TO_SCHEDULE }/choice`, {
        method: "POST",
        body: formData
    } )
        .then( responce => responce.text() )
        .then( html => {
            $( "#schedules__tables" ).fadeOut( 250, function () {
                $( this ).html( html ).fadeIn( 200 );
            } );
        } )
        .catch( error => console.error( error ) );
}

function goToBooking( date, time, movieId, theater, auditorium, price ) {
    location.href = `${ PATH_TO_SCHEDULE }/booking/${ date }/${ time }/${ movieId }/${ theater }/${ auditorium }/${ price }`;
}
