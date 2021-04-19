# Development APIs


You'll need to use these APIs to connect to the backend, i.e. performing actions and reading data.
Use this url to invoke the API:

https://jes2jq1o05.execute-api.eu-central-1.amazonaws.com/tempdev




# /api
  
## /user
<details>
  <summary>/user</summary>
  
  ### /created-groups
  #### GET
  Gets the current user's created groups in json format.

  Required headers: Authorization:AUTH_TOKEN
  
  Body: None

  Sample response:
  ```
  {
    "GroupsCreated": [
        {
            "id": "b6aae2",
            "name": "Crossing Fingers 101",
            "creator": "creator@gmail.com"
        },
        {
            "id": "7e3963",
            "name": "Test Group",
            "creator": "creator@gmail.com"
        },
        {
            "id": "b93d8b",
            "name": "Test Group",
            "creator": "creator@gmail.com"
        }
    ],
    "Reference": "c643da51-2ca4-48a3-a81e-574c975eee50"
  }
  ```


  ### /attending-groups
  #### GET
  Gets the current user's attending groups in json format.

  Required headers: Authorization:AUTH_TOKEN
  
  Body: None

  Sample response:
  ```
  {
      "GroupsAttending": [
          {
              "id": "e836fe",
              "name": "First group yay",
              "creator": "yaru@email.email"
          },
          {
              "id": "5be81a",
              "name": "First group yay",
              "creator": "yaru@email.email"
          },
          {
              "id": "7e3963",
              "name": "Test Group",
              "creator": "creator@gmail.com"
          }
      ],
      "Reference": "9514d67b-d35f-467e-8ec1-38743885127f"
  }
  ```
</details>
  
  
## /group
<details>
  <summary>/group</summary>
  
  #### POST
  Creates a group for the current user.

  Required headers: Authorization:AUTH_TOKEN
  
  Body: 

  {"name": "Test Group"}


  Sample response:
  ```
{
  "ID": "b93d8b",
  "Name": "Test Group",
  "Creator": "creator@gmail.com",
  "Reference": "eb2a547a-96a4-4741-96cf-1cfd1797d91b"
}
  ```
  
  ### /{groupId}
  #### GET
  Gets details for {groupId}. Only creator of the group can call the API.

  Required headers: Authorization:AUTH_TOKEN
  
  Path parameters: {"groupId":GROUP_ID}
  
  Body: None

  Sample call: GET at https://invokeurl/api/group/7e3963

  Sample response:
  ```
{
    "Group": {
        "moderators": [],
        "attenders": [
            "student1@gmail.com",
            "student2@gmail.com"
        ],
        "creator": "creator@gmail.com",
        "id": "7e3963",
        "name": "Test Group"
    },
    "Reference": "7630d4c2-b6b9-41a9-be5c-145d98467dca"
}
  ```
  
  
  #### PUT
  Puts the current user as enrolled in the specified group.

  Required headers: Authorization:AUTH_TOKEN
  
  Path parameters: {"groupId":GROUP_ID}
  
  Body: None

  Sample call: PUT at https://invokeurl/api/group/7e3963

  Sample response:
  ```
{
    "Message": "Successfully enrolled user user@gmail.com in group b6aae2",
    "User": "user@gmail.com",
    "Group": "b6aae2",
    "Reference": "de5e5e35-9e59-4b36-9826-9b279e6ecd34"
}
  ```
  
  #### /session
  ##### POST
  Creates a session for the group. Logged in user must be the creator of the group.

  Required headers: Authorization:AUTH_TOKEN
  
  Path parameters: {"groupId":GROUP_ID}
  
  Body: {"starts_at": "Mon, 01 Apr 2019 18:31:06 GMT","ends_at": "Mon, 01 Apr 2019 20:31:06 GMT"}

  Sample call: PUT at https://invokeurl/api/group/7e3963/session

  Sample response:
  ```
{
    "ID": "c611c92d84f4",
    "GroupID": "7e3963",
    "StartsAt": "Mon, 01 Apr 2019 18:31:06 GMT",
    "EndsAt": "Mon, 01 Apr 2019 20:31:06 GMT",
    "Reference": "b3f314bf-f867-4e94-81f9-7264b4db2056"
}
  ```
</details>

## /session
<details>
  <summary>/session</summary>
  
  ### /{sessionId}
  #### /attendance
  ##### POST
  Attends the current logged in user to the current session. Only allows attendance between start time and 20 minutes after.

  Required headers: Authorization:AUTH_TOKEN
  
  Path parameters: {"sessionId":SESSION_ID}
  
  Body: None

  Sample call: POST at https://invokeurl/api/session/c611c92d84f4/attendance 

  Sample response:
  ```
{
    "ID": "V6d9Jho-XhJEarPR",
    "Time": "Mon, 01 Apr 2019 19:36:49 GMT",
    "Attender": "user@gmail.com",
    "Reference": "fdc8c08a-3142-4513-840a-c62230546477"
}
  ```
</details>

