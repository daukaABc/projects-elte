/* global Recognize _config*/

var Recognize = window.Recognize || {};

(function recognizeScopeWrapper($) {
    let authToken;

    const urlParams = new URLSearchParams(window.location.search);
    const session = urlParams.get('session');
    if (session == null) {
        // TODO: Print it to html, then redirect user. Remove these 3 lines.
        console.log('No session provided in URL.');
        alert('No session provided in URL.');
        window.location.href = '/index.html';
    }

    Recognize.authToken.then((token) => {
        if (token) {
            authToken = token;
        } else {
            window.location.href = '/signin.html';
        }
    }).catch((error) => {
        alert(error);
        window.location.href = '/signin.html';
    });


    // Register click handler for #request button
    $(() => {
        document.getElementById('barcode').src='https://api.qrserver.com/v1/create-qr-code/?size=200x200&data=https://s3.eu-central-1.amazonaws.com/aws-codestar-eu-central-1-247216321180-recognize-app/public/attendance.html?session=' + session;
    });


}(jQuery));
