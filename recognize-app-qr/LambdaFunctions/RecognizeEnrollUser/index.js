const response = require('./response');

const AWS = require('aws-sdk');
const ddb = new AWS.DynamoDB.DocumentClient();


exports.handler = async (event, context, callback) => {
    console.log(event);

    if (!event.requestContext.authorizer) {
      response.error('Authorization not configured', context.awsRequestId, callback);
      return;
    }

    const user = event.requestContext.authorizer.claims['email'];

    if (!event.pathParameters.groupId) {
        response.error("Group ID path parameter is required", context.awsRequestId, callback);
    }
    const groupId = event.pathParameters.groupId;
    try {
            const res = await updateGroup(user, groupId);
            const res2 = await updateUser(user, groupId);
            response.success(user, groupId, context.awsRequestId, callback);
        } catch (err) {
            response.error(err.message, context.awsRequestId, callback);
    }

};


function updateUser(user, groupId) {
    return ddb.update({
        TableName: 'RecognizeUsersProd',
        Key: { email : user },
        UpdateExpression: 'set groups_attending = list_append(groups_attending, :vals)',
        ExpressionAttributeValues: {
            ':vals' : [groupId]
        }
    }).promise();
}

function updateGroup(user, groupId) {
    return ddb.update({
        TableName: 'RecognizeGroupsProd',
        Key: { id : groupId },
        UpdateExpression: 'set attenders = list_append(attenders, :vals)',
        ExpressionAttributeValues: {
            ':vals' : [user]
        }
    }).promise();
}
