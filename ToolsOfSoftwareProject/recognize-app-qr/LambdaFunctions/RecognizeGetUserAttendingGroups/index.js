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

    try {
        var groupIds = await getAttendingGroupIds(user);
        var groups = await getGroups(groupIds);
        return response.success(groups, context.awsRequestId, callback);
    } catch(err) {
        return response.error(err, context.awsRequestId, callback);
    }

};


async function getAttendingGroupIds(user) {
    var result;
    try {
        result = await ddb.get({
            TableName : 'RecognizeUsersProd',
            Key: { email : user }
        }).promise();
    } catch (err) {
        throw err;
    }

    if (result.Item.email != user || !result.Item.groups_created) {
        throw "There was an issue with the info received from the server";
    }

    return result.Item.groups_attending;
}

async function getGroup(groupId) {
    var result;
    try {
        result = await ddb.get({
            TableName : 'RecognizeGroupsProd',
            Key: { id : groupId }
        }).promise();
    } catch (err) {
        throw err;
    }

    if (!result.Item.id || !result.Item.creator || !result.Item.name) {
        throw "There was an issue with the info received from the server";
    }

    const group = {
        id : result.Item.id,
        name : result.Item.name,
        creator : result.Item.creator
    };

    return group;
}

async function getGroups(groupIds) {
    var groups = [];

    for (let i = 0; i < groupIds.length; i++) {
        let groupId = groupIds[i];
        try {
            let group = await getGroup(groupId);
            groups.push(group);
        } catch (err) {
            throw err;
        }

    }

    return groups;
}
