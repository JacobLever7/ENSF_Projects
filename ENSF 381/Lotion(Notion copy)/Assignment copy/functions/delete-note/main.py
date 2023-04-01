# add your delete-note function here
import json
import boto3
import urllib.parse
import urllib.request

dynamodb_resource = boto3.resource("dynamodb")
table = dynamodb_resource.Table("lotion-30142179")

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
    
    #delete is all the same below 
    queryParameters = event["queryStringParameters"]
    try:
        table.delete_item(Key=queryParameters)
        
        return {
            "statusCode": 200,
            "body": json.dumps({
                    "message": "success"
            })
        }
    
    except Exception as exp:
        
        return {
            "statusCode": 500,
                "body": json.dumps({
                    "message":str(exp)
            })
        }