$( () => {
    $( ".maps" ).each( function () {
        const input = $( this ).prev();
        const coord = input.val();
        const arr = coord.split( ';' );
        const latitude = parseFloat( arr[ 0 ].replace( ",", "." ) );
        const longitude = parseFloat( arr[ 1 ].replace( ",", "." ) );
        const map = L.map( this ).setView( [ longitude, latitude ], 10 );
        L.tileLayer( 'https://tile.openstreetmap.org/{z}/{x}/{y}.png', {
            maxZoom: 25,
            attribution: '&copy; <a href="http://www.openstreetmap.org/copyright">OpenStreetMap</a>'
        } ).addTo( map );
    } );
} );