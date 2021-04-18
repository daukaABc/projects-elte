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

exports.success = function(group, awsRequestId, callback) {
  callback(null, {
    statusCode: 201,
    body: JSON.stringify({
        Group: group,
        Reference: awsRequestId
    }),
    headers: {
        'Access-Control-Allow-Origin': '*',
    },
  });
}