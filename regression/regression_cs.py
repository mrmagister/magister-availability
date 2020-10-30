
# Multiple Linear Regression

# Importing the libraries
import numpy as np
import matplotlib.pyplot as plt
import pandas as pd
from numpy import loadtxt
from decimal import Decimal


## Column in the dataset
# (0) string (1) float -- entropy (2) string -- heuristic (3) float (4) string -- patter (5) float (6) label -- float -- true 1.0 || false 0.0
def convertToNumber(s):
    string = s.decode()
    string1 = string.replace("|-|", ",")
    #print(string1)
    decimal_number = Decimal(int.from_bytes(str(string1).encode(), 'little'))
    #decimal_number = Decimal(int.from_bytes(str(s).encode(), 'little'))
    #print(decimal_number)
    float_number = float(decimal_number)
    #print(float_number)
    return float_number

def convertFromNumber (n):
    decompress_string = zlib.decompress(n)
    return decompress_string.to_bytes(math.ceil(n.bit_length() / 8), 'little').decode()
    
def convertToFloat(s):
    try:
        f = float(s)
        #print(f)
        return f
    except (Exception):
        return float(0)

def convertToFloatFromHeuristic(s):
    return_float = 0.0;
    string = s.decode()
    
    if "none" in string:
        return 0.0
    
    if "api" in string:
        string = string.replace("api", "")
        return_float = return_float + 1.0
    if "key" in string:
        string = string.replace("key", "")
        return_float = return_float + 2.0
    if "username" in string:
        string = string.replace("username", "")
        return_float = return_float + 3.0
    if "user" in string:
        string = string.replace("user", "")
        return_float = return_float + 4.0
    if "uname" in string:
        string = string.replace("uname", "")
        return_float = return_float + 5.0
    if "pw" in string:
        string = string.replace("pw", "")
        return_float = return_float + 6.0
    if "password" in string:
        string = string.replace("password", "")
        return_float = return_float + 7.0
    if "pass" in string:
        string = string.replace("pass", "")
        return_float = return_float + 8.0
    if "email" in string:
        string = string.replace("email", "")
        return_float = return_float + 9.0
    if "metadata" in string:
        string = string.replace("metadata", "")
        return_float = return_float + 10.0
    if "mail" in string:
        string = string.replace("mail", "")
        return_float = return_float + 11.0
    if "test" in string:
        string = string.replace("test", "")
        return_float = return_float + 12.0
    if "github" in string:
        string = string.replace("github", "")
        return_float = return_float + 13.0
    if "json" in string:
        string = string.replace("json", "")
        return_float = return_float + 14.0
    if "credentials" in string:
        string = string.replace("credentials", "")
        return_float = return_float + 15.0
    if "credential" in string:
        string = string.replace("credential", "")
        return_float = return_float + 16.0
    if "login" in string:
        string = string.replace("login", "")
        return_float = return_float + 17.0
    if "token" in string:
        string = string.replace("token", "")
        return_float = return_float + 18.0
    if "secret" in string:
        string = string.replace("secret", "")
        return_float = return_float + 19.0
    if "annotation" in string:
        string = string.replace("annotation", "")
        return_float = return_float + 20.0
    if "herobrine" in string:
        string = string.replace("herobrine", "")
        return_float = return_float + 21.0;
    if "aws" in string:
        string = string.replace("aws", "")
        return_float = return_float + 22.0
    if "com_package" in string:
        string = string.replace("com_package", "")
        return_float = return_float + 23.0
    if "dot_com" in string:
        string = string.replace("dot_com", "")
        return_float = return_float + 24.0
    if "home_folder" in string:
        string = string.replace("home_folder", "")
        return_float = return_float + 25.0
    
    return return_float

def convertToFloatFromPattern(s):
    return_float = 0.0;
    string = s.decode()

    if "[none]" in string:
        return 0.0
    while (("GENERIC_DB_CREDENTIAL" in string) or ("GENERIC_PASSWORD3" in string) or ("GENERIC_PASSWORD2" in string) or ("GENERIC_PASSWORD1" in string) or ("GENERIC_APPSECRET" in string) or ("GENERIC_PASSWORD" in string) or ("FILE_PATH" in string) or ("PACKAGE_NAME" in string) or ("EMAIL" in string) or ("IPV6" in string) or ("XML_HTML_BODY" in string) or ("LINUX_FILE_PATH" in string) or ("MAC_ADDRESS" in string) or ("TAG" in string) or ("SOURCE_FILE" in string) or ("FILE_NAME" in string) or ("PASSWORD_URL" in string) or ("OTHER_PASSWORDS" in string) or ("GENERIC_ACCESS_TOKENS" in string) or ("TOKENS" in string) or ("OTHER_SECRETS" in string) or ("SLACK_TOKEN" in string) or ("RSA_PRIVATE_KEY" in string) or ("SSH_OPENSSH_PRIVATE_KEY" in string) or ("SSH_DSA_PRIVATE_KEY" in string) or ("SSH_EC_PRIVATE_KEY" in string) or ("PGP_PRIVATE_KEY_BLOCK" in string) or ("FACEBOOK_OAUTH" in string) or ("TWITTER_OAUTH" in string) or ("GITHUB_OAUTH" in string) or ("GITHUB_OAUTH" in string) or ("GOOGLE_OAUTH" in string) or ("AWS_API_KEY" in string) or ("HEROKU_API_KEY" in string) or ("GENERIC_SECRET" in string) or ("SLACK_WEBHOOK" in string) or ("GENERIC_API_KEY" in string) or ("TWILIO_API_Key" in string) or ("PASSWORD_IN_URL" in string) or ("METHOD_CALL" in string) or ("MD5_HASH" in string) or ("DATE" in string) or ("MYSQL_QUERY" in string) or ("FILEPATH_EXTENSION" in string)):
        
        if "GENERIC_DB_CREDENTIAL" in string:
            string = string.replace("GENERIC_DB_CREDENTIAL", "")
            return_float = return_float + 1.5
        if "GENERIC_PASSWORD3" in string:
            string = string.replace("GENERIC_PASSWORD3", "")
            return_float = return_float + 3.0
        if "GENERIC_PASSWORD2" in string:
            string = string.replace("GENERIC_PASSWORD2", "")
            return_float = return_float + 3.0
        if "GENERIC_PASSWORD1" in string:
            string = string.replace("GENERIC_PASSWORD1", "")
            return_float = return_float + 1.0
        if "GENERIC_APPSECRET" in string:
            string = string.replace("GENERIC_APPSECRET", "")
            return_float = return_float + 3.0
        if "GENERIC_PASSWORD" in string:
            string = string.replace("GENERIC_PASSWORD", "")
            return_float = return_float + 3.0
        if "FILE_PATH" in string:
            string = string.replace("FILE_PATH", "")
            return_float = return_float - 4.0
        if "PACKAGE_NAME" in string:
            string = string.replace("PACKAGE_NAME", "")
            return_float = return_float - 4.0
        if "EMAIL" in string:
            string = string.replace("EMAIL", "")
            return_float = return_float - 4.0
        if "IPV6" in string:
            string = string.replace("IPV6", "")
            return_float = return_float - 4.0
        if "XML_HTML_BODY" in string:
            string = string.replace("XML_HTML_BODY", "")
            return_float = return_float - 4.0
        if "LINUX_FILE_PATH" in string:
            string = string.replace("LINUX_FILE_PATH", "")
            return_float = return_float - 4.0
        if "MAC_ADDRESS" in string:
            string = string.replace("MAC_ADDRESS", "")
            return_float = return_float - 4.0
        if "TAG" in string:
            string = string.replace("TAG", "")
            return_float = return_float - 4.0
        if "SOURCE_FILE" in string:
            string = string.replace("SOURCE_FILE", "")
            return_float = return_float - 4.0
        if "FILE_NAME" in string:
            string = string.replace("FILE_NAME", "")
            return_float = return_float - 4.0
        if "PASSWORD_URL" in string:
            string = string.replace("PASSWORD_URL", "")
            return_float = return_float + 5.0
        if "OTHER_PASSWORDS" in string:
            string = string.replace("OTHER_PASSWORDS", "")
            return_float = return_float + 5.0
        if "GENERIC_ACCESS_TOKENS" in string:
            string = string.replace("GENERIC_ACCESS_TOKENS", "")
            return_float = return_float - 4.0
        if "TOKENS" in string:
            string = string.replace("TOKENS", "")
            return_float = return_float + 4.0
        if "OTHER_SECRETS" in string:
            string = string.replace("OTHER_SECRETS", "")
            return_float = return_float + 4.0;
        if "SLACK_TOKEN" in string:
            string = string.replace("SLACK_TOKEN", "")
            return_float = return_float + 4.0
        if "RSA_PRIVATE_KEY" in string:
            string = string.replace("RSA_PRIVATE_KEY", "")
            return_float = return_float + 4.0
        if "SSH_OPENSSH_PRIVATE_KEY" in string:
            string = string.replace("SSH_OPENSSH_PRIVATE_KEY", "")
            return_float = return_float + 4.0
        if "SSH_DSA_PRIVATE_KEY" in string:
            string = string.replace("SSH_DSA_PRIVATE_KEY", "")
            return_float = return_float + 4.0
        if "SSH_EC_PRIVATE_KEY" in string:
            string = string.replace("SSH_EC_PRIVATE_KEY", "")
            return_float = return_float + 5.0
        if "PGP_PRIVATE_KEY_BLOCK" in string:
            string = string.replace("PGP_PRIVATE_KEY_BLOCK", "")
            return_float = return_float + 5.0
        if "FACEBOOK_OAUTH" in string:
            string = string.replace("FACEBOOK_OAUTH", "")
            return_float = return_float + 6.0
        if "TWITTER_OAUTH" in string:
            string = string.replace("TWITTER_OAUTH", "")
            return_float = return_float + 6.0
        if "GITHUB_OAUTH" in string:
            string = string.replace("GITHUB_OAUTH", "")
            return_float = return_float + 6.0
        if "GOOGLE_OAUTH" in string:
            string = string.replace("GOOGLE_OAUTH", "")
            return_float = return_float + 6.0
        if "AWS_API_KEY" in string:
            string = string.replace("AWS_API_KEY", "")
            return_float = return_float + 6.0
        if "HEROKU_API_KEY" in string:
            string = string.replace("HEROKU_API_KEY", "")
            return_float = return_float + 6.0
        if "GENERIC_SECRET" in string:
            string = string.replace("GENERIC_SECRET", "")
            return_float = return_float + 6.0
        if "GENERIC_API_KEY" in string:
            string = string.replace("GENERIC_API_KEY", "")
            return_float = return_float + 6.0
        if "SLACK_WEBHOOK" in string:
            string = string.replace("SLACK_WEBHOOK", "")
            return_float = return_float + 6.0
        if "TWILIO_API_Key" in string:
            string = string.replace("TWILIO_API_Key", "")
            return_float = return_float + 6.0
        if "PASSWORD_IN_URL" in string:
            string = string.replace("PASSWORD_IN_URL", "")
            return_float = return_float + 6.0
        if "METHOD_CALL" in string:
            string = string.replace("METHOD_CALL", "")
            return_float = return_float - 7.0
        if "MD5_HASH" in string:
            string = string.replace("MD5_HASH", "")
            return_float = return_float - 4.0
        if "DATE" in string:
            string = string.replace("DATE", "")
            return_float = return_float - 9.0
        if "MYSQL_QUERY" in string:
            string = string.replace("MYSQL_QUERY", "")
            return_float = return_float - 9.0
        if "FILEPATH_EXTENSION" in string:
            string = string.replace("FILEPATH_EXTENSION", "")
            return_float = return_float - 9.0

    return return_float# Importing the dataset

# (0) string (1) float -- entropy (2) string -- heuristic (3) float (4) string -- pattern (5) float # of patterns (6) label -- float -- true 1.0 || false 0.0 (7) length -- float (8) key weight -- float
# Import the dataset

from scipy.special import softmax

print("step 1")
dataset_file_location = '-----------------------------------'
dataset = loadtxt(dataset_file_location, delimiter='\t', converters={0: convertToNumber, 1: convertToFloat, 2: convertToFloatFromHeuristic, 3: convertToFloat, 4: convertToFloatFromPattern, 5:convertToFloat, 6:convertToFloat, 7:convertToFloat, 8:convertToFloat})


X = dataset[:,[1,2,3,4,5,7,8]]


y1 = dataset[:,6] # label - 1 true or 0 false --

# Splitting the dataset into the Training set and Test set
from sklearn.model_selection import train_test_split
from sklearn.linear_model import LinearRegression
from sklearn.metrics import r2_score, mean_squared_error

# Fitting Multiple Linear Regression to the Training set
X_train, X_test, y_train, y_test = train_test_split(X, y1, test_size = 0.3, random_state = 0)
regressor = LinearRegression()
regressor.fit(X_train, y_train)

# Predicting the Test set results
# breadps
y_pred = regressor.predict(X_test)
print("label: ")

score=r2_score(y_test, y_pred)
print("label score: ", score)
print("Mean squared error: %.2f" % mean_squared_error(y_test, y_pred))
print("label intercept: ", regressor.intercept_)
print("label betas: ", regressor.coef_)

print("label prediction ------ polynomial regression")
from sklearn.preprocessing import PolynomialFeatures
import matplotlib.pyplot as plt

poly = PolynomialFeatures(degree = 5, interaction_only = False, order='F')
X_poly = poly.fit_transform(X_train)
poly.fit(X_poly, y_train)
lin2 = LinearRegression()
lin2.fit(X_poly, y_train)
y_pred_p = lin2.predict(poly.fit_transform(X_test))
score_p = r2_score(y_test, y_pred_p)
print("label intercept: ", lin2.intercept_)
print("label betas: ", lin2.coef_)
print("label poly score: ", score_p)
print("mean square error: ", mean_squared_error(y_test, y_pred_p))
print("\n\n")
