// @ts-nocheck
const PATH_TO_BANNERS = "/KinoCMS-SichniyA/admin/banners";

$( () => {
    $( "#header__title" ).text( "Банери головної сторінки" );
    $( "#sidebar-menu .nav-link.active" ).removeClass( "active" );
    $( "#banners__link" ).addClass( "active" );
} );

function addBannerCard() {
    addCard( "banners_cards", "banner" );
}

function addPromotionCard() {
    addCard( "promotions_cards", "promotion" );
}

function addCard( collection, elem ) {
    const $bannersCard = $( `#${ collection }` );
    const amount = $bannersCard.children().length;
    const $addCard = $( `#add_${ elem }_card` );
    const $newCard = $(
        `<div id="${ elem }_card_${ amount }" class="card col py-4 mt-0">
                <div id="img_block_${ amount }" class="mb-2 position-relative">
                    <img class="img-fluid" src="../../pictures/stumb.webp" alt="photo">
                    <i class="bi bi-x-circle-fill fs-3 position-absolute top-0 start-100 translate-middle"></i>
                </div>
                <input type="file" name="${ elem }_${ amount }" accept="image/*"
                    id="${ elem }__input_${ amount }" class="mb-2 form-control">
                <label for="${ elem }__text_${ amount }|">Текст</label>
                <input id="${ elem }__text_${ amount }|" type="text" class="form-control">
        </div>`);
    $newCard.on( "click", `#img_block_${ amount } > i`,
        ( event ) => deleteCard( event.target ) );
    $newCard.on( "change", `#${ elem }__input_${ amount }`,
        ( event ) => showPicture( event.target ) );
    $newCard.insertBefore( $addCard );
}

function deleteCard( el ) {
    $( el ).closest( ".card" ).remove();
}

function showPicture( el ) {
    const file = el.files[ 0 ];
    if ( file ) {
        const fileReader = new FileReader();
        fileReader.onload = ev => {
            const $prevDiv = $( el ).prev();
            $prevDiv.children( "img" ).attr( "src", ev.target.result );
        };
        fileReader.readAsDataURL( file );
    }
}

function showBackground() {
    $( "#background__input" ).trigger( "click" );
}

function updateUpBanners() {
    update( "banners_cards", "banner", "up-banners" );
}

function updateBottomPromotions() {
    update( "promotions_cards", "promotion", "down-promotions" );
}

async function update( collection, target, endPoint ) {
    let i = 0;
    const banners = [];
    const elements = $( `#${ collection }` ).find( `input[type="file"]` ).get();
    for ( const element of elements ) {
        ++i;
        const file = element.files[ 0 ];
        const img = element.previousElementSibling.children[ 0 ];
        const text = element.nextElementSibling.nextElementSibling.value;

        const path = file ?
            await uploadFileOnServer( file, `${ target }_${ i }` ) :
            img.src.replace( "http://localhost:8080/", "/" );

        banners.push( { path, text } );
    }

    const res = JSON.stringify( banners );
    fetch( `${ PATH_TO_BANNERS }/update/${ endPoint }`, {
        method: "PUT",
        headers: {
            "Content-Type": "application/json"
        },
        body: res
    } ).then( responce => {
        alert( `${ target } saved`.toUpperCase() );
    } );
}

async function saveBackground() {
    const file = $( "#background__input" )[ 0 ].files[ 0 ];
    const path = file ?
        await uploadFileOnServer( file, "background" ) :
        $( "#background__img" ).attr( "src" );
    const obj = {
        path
    };

    fetch( `${ PATH_TO_BANNERS }/update/background`, {
        method: "PUT",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify( obj )
    } ).then( responce => {
        if ( responce.ok ) {
            alert( "background saved".toUpperCase() );
        }
    } );
}

async function uploadFileOnServer( file, name ) {
    const formData = new FormData();
    const ext = file.name.split( "." )[ 1 ];

    formData.append( "file", file, name );
    formData.append( "timestamp", new Date().getTime() );
    formData.append( "ext", ext );

    const responce = await fetch( `${ PATH_TO_BANNERS }/add`, {
        method: "POST",
        body: formData
    } );
    return responce.ok ? await responce.text() : "";
}