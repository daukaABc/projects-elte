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

exports.success = function(id, time, attender, awsRequestId, callback) {
  callback(null, {
    statusCode: 201,
    body: JSON.stringify({
        ID: id,
        Time: time,
        Attender: attender,
        Reference: awsRequestId
    }),
    headers: {
        'Access-Control-Allow-Origin': '*',
    },
  });
}