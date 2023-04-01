import json
import boto3
from boto3.dynamodb.conditions import Key
import urllib.parse
import urllib.request




dynamodb = boto3.resource("dynamodb")
table = dynamodb.Table("lotion-30142179")

def lambda_handler(event, context):
    #backend authentication
    access_token = event['headers']['authentication']


    validation_url = f'https://www.googleapis.com/oauth2/v1/userinfo?access_token=%7Baccess_token%7D'
    print(validation_url)
    res = urllib.request.urlopen(validation_url)

    #parse res
    token = json.loads(res.read())

    #check for error in token
    if 'error' in token:
        return {
            'statusCode': 401,
            'body': 'Authentication error'
        }
    
    #get is all the same below
    queryParameter = event["queryStringParameters"]
    try:
        result = table.query(KeyConditionExpression=Key('email').eq(queryParameter["email"]))
        return {
            "statusCode": 200,
            "body": json.dumps({
                "message": "success",
                "notes": result
                })
        }
    except Exception as exp:
        print(f"exception: {exp}")
        return {
            "statusCode": 500,
                "body": json.dumps({
                    "message":str(exp)
            })
        }