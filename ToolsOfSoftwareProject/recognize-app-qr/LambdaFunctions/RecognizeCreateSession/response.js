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

exports.success = function(id, groupId, starts_at, ends_at, awsRequestId, callback) {
  callback(null, {
    statusCode: 201,
    body: JSON.stringify({
        ID: id,
        GroupID: groupId,
        StartsAt: starts_at,
        EndsAt: ends_at,
        Reference: awsRequestId
    }),
    headers: {
        'Access-Control-Allow-Origin': '*',
    },
  });
}