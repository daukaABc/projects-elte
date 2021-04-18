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

exports.success = function(user, group, awsRequestId, callback) {
  callback(null, {
    statusCode: 201,
    body: JSON.stringify({
        Message: "Successfully enrolled user " + user + " in group " + group,
        User: user,
        Group: group,
        Reference: awsRequestId
    }),
    headers: {
        'Access-Control-Allow-Origin': '*',
    },
  });
}