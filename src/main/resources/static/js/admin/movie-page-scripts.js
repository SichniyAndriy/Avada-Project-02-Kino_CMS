// @ts-nocheck
const PATH_TO_MOVIES = "/KinoCMS-SichniyA/admin/movies";

$( () => {
    $( "#header__title" ).text( "Редагування даних фільма" );
    $( "#sidebar-menu .nav-link.active" ).removeClass( "active" );
    $( "#movies__link" ).addClass( "active" );
    $( "#movie__form" ).on( "submit", ( event ) => {
        event.preventDefault();
        saveMovieInfo( event.target );
    } );
    $( ".form-check-input" ).on( "change", event => {
        const $elem = $( event.target );
        const flag = $elem.prop( "checked" );
        $elem.val( flag );
    } );
} );

function addPictureCard( elem ) {
    const $addCardBtn = $( elem );
    const i = $addCardBtn.siblings().length + 1;
    const $newCard = $( `<div class="col card py-4 mt-0" id="picture_card_${ i }">
                                <div class="mb-2 position-relative" id="img_block_${ i }">
                                    <img class="img-fluid" src="../../../pictures/stumb.webp" alt="photo">
                                    <i class="bi bi-x-circle-fill fs-3 position-absolute top-0 start-100 translate-middle"
                                        onclick="deleteCard(this)"></i>
                                </div>
                                <input type="file" accept="image/*" name="picture_${ i }" class="mb-2 form-control"
                                    id="picture__input_${ i }" onchange="showPicture(this)">
                        </div>`);
    $newCard.insertBefore( $addCardBtn );
}

function deleteCard( elem ) {
    $( elem ).closest( ".card" ).remove();
}

function triggerChange( elem ) {
    $( "#btn_block" ).prev().trigger( "click" );
}

function showPicture( elem ) {
    const file = elem.files[ 0 ];
    if ( file ) {
        const fileReader = new FileReader();
        fileReader.onload = ev => {
            $( elem )
                .prev()
                .children( "img" )
                //@ts-ignore
                .attr( "src", ev.target.result );
        };

        fileReader.readAsDataURL( file );
    }
}

function deletePicture( el ) {
    $( "#poster_block > img" ).attr( "src", "" );
}
/////////////////////////////////////////////////////////////////////////////////////////////////////
function goToMovies() {
    location.href = PATH_TO_MOVIES;
}

async function saveMovieInfo( form ) {
    const formData = new FormData();

    for ( const entry of form ) {
        const name = new String( entry.name );
        if ( name.length === 0 || name.includes( "pic" ) || name.startsWith( "_" ) ) continue;
        formData.append( name, entry.value );
    }

    const val = $( "#movie_id" ).val();
    const id = parseInt( val );
    const picturesPromise = getPictures( id );
    const posterUrlPromise = getPosterUrl( id );

    const picturesJson = JSON.stringify( await picturesPromise );
    formData.append( "picturesJson", picturesJson );
    const posterUrl = await posterUrlPromise;
    formData.append( "posterUrl", posterUrl );

    for ( const entry of formData.entries() ) {
        console.log( entry );
    }
    fetch( `${ PATH_TO_MOVIES }/save`, {
        method: "POST",
        body: formData
    } ).then( responce => {
        if ( responce.ok ) {
            goToMovies();
        } else {
            alert( "Дані про фільм не збережено\nПеревірте поля" );
        }
    } );
}

async function getPosterUrl( id ) {
    const posterFile = $( "#poster__input" )[ 0 ].files[ 0 ];
    const posterUrl = posterFile ?
        await saveFileOnServer( posterFile, `poster_${ id }` ) :
        $( "#poster_picture" ).attr( "src" );
    return posterUrl;
}

async function getPictures( id ) {
    const pictures = [];
    const cards = $( "#add_picture_card" ).siblings().get();
    let i = 0;
    for ( const card of cards ) {
        ++i;
        const last = card.lastElementChild;
        const pictureFile = last.files[ 0 ];
        const pictureName = pictureFile ?
            await saveFileOnServer( pictureFile, `picture_${ id }_${ i }` ) :
            $( card ).find( "img" ).attr( "src" );
        pictures.push( { path: pictureName } );
    }
    return pictures;
}
/////////////////////////////////////////////////////////////////////////////////////////////////////
async function saveFileOnServer( file, filename ) {
    const ext = file.name.split( "." )[ 1 ];

    const formData = new FormData();
    formData.append( "file", file, filename );
    //@ts-ignore
    formData.append( "timestamp", new Date().getTime() );
    formData.append( "ext", ext );

    const responce = await fetch( `${ PATH_TO_MOVIES }/save/file`, {
        method: "POST",
        body: formData
    } );

    return responce.ok ? responce.text() : "";
}
