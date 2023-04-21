import json
import boto3
from boto3.dynamodb.conditions import Key



dynamodb = boto3.resource("dynamodb")
table = dynamodb.Table("lotion-30142179")

def lambda_handler(event, context):
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