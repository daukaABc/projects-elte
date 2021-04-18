exports.error = function(errorMessage, awsRequestId, callback) {
    callback(null, {
        statusCode: 500,
        body: JSON.stringify({
            Error: errorMessage,
            Reference: awsRequestId,
        }),
        headers: {
            'Access-Control-Allow-Origin': '*',
        },
    });
}

exports.success = function(id, name, creator, awsRequestId, callback) {
    callback(null, {
        statusCode: 201,
        body: JSON.stringify({
            ID: id,
            Name: name,
            Creator: creator,
            Reference: awsRequestId
        }),
        headers: {
            'Access-Control-Allow-Origin': '*',
        },
    });
}
