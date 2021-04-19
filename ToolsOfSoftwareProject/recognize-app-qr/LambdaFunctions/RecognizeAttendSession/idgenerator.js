const randomBytes = require('crypto').randomBytes;

exports.generate = function() {
    return toHexString(randomBytes(12));
}

function toHexString(buffer) {
    return buffer.toString('base64')
        .replace(/\+/g, '-')
        .replace(/\//g, '_')
        .replace(/=/g, '');
}