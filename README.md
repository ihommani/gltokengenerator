Google Service Account Token generator
===================
 
What is it?
----------
 
CLI utility to output a fresh Google Oauth2 token from a service account keyfile and scopes.  

Purpose
----------
To make authenticated call to Google API we need a valid OAuth2 token.  
The flow to get a service account token requires to produce a JWS with the protocol RSAWithSHA256.   
This protocol is not widely available.  
For instance it is not available in crypto.js library, the one used in the Postman sandbox.  
Moreover, it is easy to make mistake when constructing the JWS manually.   
All these reason tend to make the use of official Google libraries safer.  

Prerequisities
---------- 
JDK1.7

Generating the tool
----------  
From the base directory run:    
`mvn clean install`  
Get the jar from the target directory.

Usage
----------
`java -jar  tokengenerator.jar [keyfile.json] [google_oauth2_scopes...]`

Exampe of usage
----------  
To test Google cloud endpoints APIs I extensively use Postman.   
Usually I create token by using a refresh token created from a dedicated user account (token creation happens during a Postman prescript. See my article).      
However in a work environment, this is not wise since the user may leave the company.   
We shoud instead use a service account linked to the Google project hosting the API.   
On a CI tool such as travis, we may call this utility with the wanted keyfile and scopes, get the token and pass it to the Postman environment.  
Then launching newman with the updated environement will make authenticated calls.

Limitation
----------  
The provided token has a fixed TTL of ~1h.  


Former way
---------- 
TokenGeneratorOldWay class exposes another to generate Credentials.   
It is based on the Credential google class (and not Credentials).   
We may need this way of doing for some Google API clients. (e.g: the Sheet client).