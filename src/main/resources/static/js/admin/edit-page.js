$( () => {
    // ----------------------------------- *** ----------------------------------- \\
    $( `#${ PAGE_NAME }_status` ).on( "change", ( event ) => {
        const $Switcher = $( event.target );
        const status = $Switcher.prop( "checked" );
        $Switcher.next().text( status ? "ON" : "OFF" );

        $( "form" ).find( "*" ).prop( "disabled", !status );
    } ).trigger( "change" );
    // ----------------------------------- *** ----------------------------------- \\
    $( "#load_banner__btn" ).on( "click", () =>
        $( "#banner_block>input" ).trigger( "click" ) );
    // ----------------------------------- *** ----------------------------------- \\
    $( "#banner_block>input" ).on( "change", ( event ) =>
        showBanner( event.target.parentElement ) );
    $( "#clear__btn" ).on( "click", () => {
        $( "#banner_block>img" )
            .attr( "src", "/pictures/stumb.webp" );
        $( "#banner_block>input" )[ 0 ].files[ 0 ] = null;
    } );
    // ----------------------------------- *** ----------------------------------- \\
    $( "#add_picture_btn" ).on( "click", () => addCard() );
    // ----------------------------------- *** ----------------------------------- \\
    $( "form" ).on( "submit", ( event ) => {
        event.preventDefault();
        saveForm( event.target );
    } );
} );

function showBanner( block ) {
    const $Block = $( block );
    const file = $Block.children( "input" )[ 0 ].files[ 0 ];
    if ( file ) {
        const fileReader = new FileReader();
        fileReader.onload = event => {
            $Block.children( "img" )
                .attr( "src", event.target.result );
        };
        fileReader.readAsDataURL( file );
    }
}

function showPicture( elem ) {
    const file = elem.files[ 0 ];
    if ( file ) {
        const fileReader = new FileReader();
        fileReader.onload = event => {
            $( elem ).prev().children( "img" ).attr( "src", event.target.result );
        };
        fileReader.readAsDataURL( file );
    }
}

function deleteCard( elem ) {
    $( elem ).closest( ".col" ).hide( 250, function () {
        $( this ).remove();
    } );
}

function addCard() {
    $newCard = $( `<div class="col ps-0 pt-3">
                            <div class="position-relative">
                                <img class="img-fluid img-thumbnail" src="/pictures/stumb.webp" alt="picture">
                                <i class="bi bi-x-circle-fill fs-3 position-absolute top-0 start-100 translate-middle"
                                    onclick="deleteCard(this)"></i>
                            </div>
                            <input type="file" accept="image/*" class="form-control mb-2" onchange="showPicture(this)">
                        </div>`).css( "display", "none" );
    $newCard.insertBefore( $( "#add_picture_btn" ).parent() ).show( 250 );
}

async function saveForm( form ) {
    const formData = new FormData( form );
    for ( const [ key, value ] of formData.entries() ) {
        console.log( `${ key } : ${ value }` );
    }

    const pictures = await getPictures();
    const picturesJson = JSON.stringify( pictures );
    formData.append( "picturesJson", picturesJson );
    const bannerUrl = await getBannerUrl();
    formData.append( "bannerUrl", bannerUrl );

    fetch( `/KinoCMS-SichniyA/admin/edit/${ PAGE_NAME }`, {
        method: "POST",
        body: formData
    } ).then( responce => {
        alert( responce.ok ? "Data updated" : "Updated fail" );
    } );
}

async function getPictures() {
    const pictures = [];
    const cards = $( "#add_picture_btn" ).parent().siblings().get();
    let i = 0;
    for ( const card of cards ) {
        ++i;
        const file = $( card ).children( "input" )[ 0 ].files[ 0 ];
        const pictureUrl = file ?
            await uploadFile( file, `${ PAGE_NAME }_${ i }` ) :
            $( card ).find( "img" ).attr( "src" );
        pictures.push( { path: pictureUrl } );
    }
    return pictures;
}

async function getBannerUrl() {
    const file = $( "#banner_block" ).children( "input" )[ 0 ].files[ 0 ];
    const bannerUrl = file ?
        await uploadFile( file, `${ PAGE_NAME }_0` ) :
        $( "#banner_block" ).children( "img" ).attr( "src" );
    return bannerUrl;
}

async function uploadFile( file, newName ) {
    if ( file ) {
        const ext = file.name.split( "." )[ 1 ];
        const formData = new FormData();
        formData.append( "file", file, newName );
        formData.append( "timestamp", new Date().getTime() );
        formData.append( "ext", ext );

        const responce = await fetch( "/KinoCMS-SichniyA/admin/edit/save/file", {
            method: "POST",
            body: formData
        } );
        return responce.ok ? await responce.text() : "";
    }
}