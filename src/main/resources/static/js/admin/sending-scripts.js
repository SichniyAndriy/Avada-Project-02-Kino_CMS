// @ts-nocheck
const SENDING_PATH = "/KinoCMS-SichniyA/admin/sending";

const ids = JSON.parse( sessionStorage.getItem( "ids" ) ) || [];
const $pickUsersSMSBtn = $( "#pick_users_sms__btn" );
const $smsToCustomUsers = $( "#sms_to_custom_users" );
const $pickUsersEmailBtn = $( "#pick_users_email__btn" );
const $emailToCustomUsers = $( "#email_to_custom_users" );
const $emailFileInput = $( "#email_file__input" );
const $emailFileName = $( "#email_file_name" );
const $emailTemplateName = $( "#email_template_name" );

const smsPickUsersBtnHandler = () => {
    $pickUsersSMSBtn.prop( "disabled", !$smsToCustomUsers.prop( "checked" ) );
    updateSmsCounter( $pickUsersSMSBtn.prop( "disabled" ) ? usersAmount : ids.length );
};
const emailPickUserBtnHandler = () => {
    $pickUsersEmailBtn.prop( "disabled", !$emailToCustomUsers.prop( "checked" ) );
    updateEmailCounter( $pickUsersEmailBtn.prop( "disabled" ) ? usersAmount : ids.length );
};

$( () => {
    $( "#header__title" ).text( "Сторінка розсилок" );
    $( "#sidebar-menu .nav-link.active" ).removeClass( "active" );
    $( "#delivering__link" ).addClass( "active" );
    $( "#sms__block" ).add( $( "#email__block" ) )
        .addClass( "border" )
        .addClass( "border-1" )
        .addClass( "border-dark" )
        .addClass( "rounded-3" )
        .addClass( "p-4" )
        .addClass( "mt-4" );
    $( ".row.row-cols-2" )
        .addClass( "g-4" )
        .addClass( "text-dark" )
        .addClass( "mt-2" );
    if ( ids.length > 0 ) {
        $( "input[type='checkbox']" ).each( ( idx, input ) => {
            const $Input = $( input );
            const id = parseInt( $Input.parent().next().text() );
            if ( ids.includes( id ) ) {
                $Input.prop( "checked", true );
            }
        } );

        const type = sessionStorage.getItem( "type" );
        if ( type ) {
            switch ( type ) {
                case "SMS":
                    $( "#sms__block input[value='sms_to_custom_users']" ).prop( "checked", true );
                    smsPickUsersBtnHandler();
                    updateSmsCounter( ids.length );
                    break;
                case "EMail":
                    $( "#email__block input[value='email_to_custom_users']" ).prop( "checked", true );
                    emailPickUserBtnHandler();
                    updateEmailCounter( ids.length );
                    break;
            }
        }
    } else {
        updateSmsCounter( usersAmount );
        updateEmailCounter( usersAmount );
    }
} );

$( "#sms__block input" ).on( "change", smsPickUsersBtnHandler );
$( "#email__block input" ).on( "change", emailPickUserBtnHandler );

function pickUsersForSmsSending() {
    sessionStorage.setItem( "type", "SMS" );
    pickUsers();
}

function pickUsersForEmailSending() {
    sessionStorage.setItem( "type", "EMail" );
    pickUsers();
}

function pickUsers( num = 0 ) {
    location.href = `${ SENDING_PATH }/pick/${ num }`;
}

function goToSending() {
    location.href = SENDING_PATH;
}

function toogleCheck( elem ) {
    const $checkBox = $( elem );
    const id = parseInt( $checkBox.parent().next().text() );

    if ( $checkBox.prop( "checked" ) ) {
        ids.push( id );
    } else {
        const i = ids.indexOf( id );
        if ( i !== -1 ) {
            ids.splice( i, 1 );
        }
    }
    sessionStorage.setItem( "ids", JSON.stringify( ids ) );
    console.log( `Check elem : ${ ids }` );
}

function updateSmsCounter( len ) {
    $( "#sms_amount" ).text( len );
}

function updateEmailCounter( len ) {
    $( "#email_amount" ).text( len );
}

function updateSymbolCounter() {
    const len = $( "#sms_text" ).val().length;
    $( "#sms_symbols_counter" ).text( len );
}

/*------------------------- SMS SOCCKET -------------------------*/
function sendSms() {
    const socket = new WebSocket( "ws://localhost:8080/KinoCMS-SichniyA/admin/sending/sms" );

    socket.onopen = ( event ) => {
        const message = $( "#sms_text" ).val();
        if ( !message ) {
            alert( "Write text of SMS" );
            socket.close();
            return;
        }

        let obj;
        if ( $( "#sms_to_all_users" ).prop( "checked" ) ) {
            obj = {
                message, ids: null
            };
        }
        else if ( $( "#sms_to_custom_users" ) ) {
            obj = { message, ids };
        }

        socket.send( JSON.stringify( obj ) );
    };

    socket.onclose = ( event ) => {
        console.log( "Sending SMS complete" + event.reason );
    };

    const $persentElem = $( "#sms_delivering_percent" );
    socket.onmessage = ( event ) => {
        $persentElem.text( event.data );
    };

    socket.onerror = ( error ) => {
        console.log( "SMS sending error:" + error.message );
    };

    sessionStorage.clear();

}
/*------------------------- EMAIL SOCCKET -------------------------*/
function sendEmail() {
    const socket = new WebSocket( "ws://localhost:8080/KinoCMS-SichniyA/admin/sending/email" );

    socket.onopen = () => {
        const fileName = $( "#email_file_name" ).text();
        if ( !fileName ) {
            alert( "Pick Up the email file" );
            socket.close();
            return;
        }

        let obj;
        if ( $( "#email_to_all_users" ).prop( "checked" ) ) {
            obj = { fileName, ids: null };
        }
        else if ( $( "#email_to_custom_users" ) ) {
            obj = { fileName, ids };
        }
        socket.send( JSON.stringify( obj ) );
    };

    socket.onclose = ( event ) => {
        console.log( event.reason );
    };

    const $emailDeliveringElem = $( "#email_delivering_percent" );
    socket.onmessage = ( event ) => {
        $emailDeliveringElem.text( event.data );
    };

    socket.error = ( error ) => {
        console.error( error );
    };
}

function uploadHtmlFile() {
    $emailFileInput.trigger( "click" );
}

$emailFileInput.on( "change", async event => {
    const file = $emailFileInput[ 0 ].files[ 0 ];
    if ( file ) {
        $emailFileName.text( file.name );
        $emailTemplateName.text( file.name.split( '.' )[ 0 ] );

        const fileReader = new FileReader();
        let resultFile;
        fileReader.onload = ( readerEvent ) => {
            resultFile = readerEvent.target.result;
            const nameArr = file.name.split( "." );
            const formData = new FormData();
            formData.append( "fileName", nameArr[ 0 ] );
            formData.append( "fileType", nameArr[ 1 ] );
            formData.append( "file", resultFile );

            fetch( `${ SENDING_PATH }/save/email`, {
                method: "POST",
                body: formData
            } ).then( ( response ) => {
                if ( !response.ok ) {
                    console.error( "Error uploading email template..." );
                }
            } ).then( ( data ) => {
                console.log( data );
            } );
        };
        fileReader.readAsArrayBuffer( file );
    }
} );

function writeFileName( elem ) {
    const text = $( elem ).next().children( "strong" ).text();
    $emailFileName.text( text );
    $emailTemplateName.text( text.split( '.' )[ 0 ] );
}