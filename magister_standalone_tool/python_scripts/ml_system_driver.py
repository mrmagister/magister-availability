from numpy import loadtxt
from keras.models import Sequential
from keras.layers import Dense
from keras.models import model_from_json # to save and retrieve data
from sklearn.model_selection import train_test_split
from sklearn.preprocessing import StandardScaler
from keras.optimizers import SGD
from decimal import Decimal
import zlib

import os

from io import StringIO
import numpy as np

def main(arg1):

    # Import the dataset
    print("step 1")
    # (0) string (1) float -- entropy (2) string -- heuristic (3) float (4) string -- pattern (5) float (6) label -- float -- true 1.0 || false 0.0 (7) float --- length (8) float -- weight
    #dataset = loadtxt(dataset_file_location, delimiter='\t', converters={0: convertToNumber, 1: convertToFloat, 2: convertToFloatFromHeuristic, 3: convertToFloat, 4: convertToFloatFromPattern, 5:convertToFloat, 6:convertToFloat, 7:convertToFloat, 8:convertToFloat})

    print("step 2")
    # (0) string    (1) entropy (2) heuristics (3) heuristic weight (4) regex patterns (5) regex weights (6) label

    from scipy.special import softmax

    print("step 3")
    #Split the data for testing and training
    #X_train, X_test, y_train, y_test = train_test_split(X, y, test_size = 0.2, random_state = 42)
    #np.seterr(divide='ignore', invalid='ignore')

    print("step 4")
    # Standardize the data
    #scaler = StandardScaler().fit(X_train)

    print("step 5")
    ## Scale the train set
    #X_train = scaler.transform(X_train)

    print("step 6")
    ## Scale the test set
    #X_test = scaler.transform(X_test)

    print("step 7")
    #model = Sequential()
    #model.add(Dense(12, input_dim=7, activation='relu'))
    #model.add(Dense(24, activation='selu'))
    #model.add(Dense(36, activation='relu'))
    #model.add(Dense(96, activation='elu'))
    #model.add(Dense(96, activation='selu'))
    #model.add(Dense(32, activation='relu'))
    #model.add(Dense(24, activation='relu'))
    #model.add(Dense(1, activation='sigmoid'))

    print("step 8")
    #model.compile(loss='binary_crossentropy', optimizer='adam', metrics=['accuracy'])
    #model.fit(X_train, y_train, epochs=450, batch_size=1000, verbose=0)

    from sklearn.metrics import confusion_matrix, precision_score, recall_score, f1_score, cohen_kappa_score, r2_score, mean_squared_error

    #mse_value, mae_value = model.evaluate(X_test, y_test, verbose = 0)
    #
    #print("mse: ", mse_value)
    #print("mae: ", mae_value)

    # serialize model to JSON -----------------
    #model_json = model.to_json()
    #with open("model.json", "w") as json_file:
    #    json_file.write(model_json)

    # serialize weight to hdf5 ----------------
    #model.save_weights("model.h5")
    #print("save model to disk")

    # later
    # load json and create model
    json_file = open("-------------------------model.json", "r")
    loaded_model_json = json_file.read()
    json_file.close()
    loaded_model = model_from_json(loaded_model_json)
    loaded_model.load_weights("---------------------------model.h5")
    print("loaded model from disk")

    # evaluate loaded model on test data
    loaded_model.compile(loss='binary_crossentropy', optimizer='adam', metrics=['accuracy'])
    #mse_value, mae_value = loaded_model.evaluate(X_test, y_test, verbose=0)

    #print("loaded_model mse: ", mse_value)
    #print("loaded_model mae: ", mae_value)

    #_, accuracy = model.evaluate(X_train, y_train, verbose=0)
    #print('Accuracy: %.2f' % (accuracy*100))

    #converters={0: convertToNumber, 1: convertToFloat, 2: convertToFloatFromHeuristic, 3: convertToFloat, 4: convertToFloatFromPattern, 5:convertToFloat, 6:convertToFloat, 7:convertToFloat, 8:convertToFloat}

    # (0) string (1) float -- entropy (2) string -- heuristic (3) float (4) string -- pattern (5) float (6) label -- float -- true 1.0 || false 0.0 (7) float --- length (8) float -- weight
    #X = dataset[0:,[1,2,3,4,5,7,8]] # input data -- entropy -- heuristic -- regex
    #entropy = 4.247927513443588
    #floatHeuristic = convertToFloatFromHeuristic("[none]")
    #floatHeuristicWeight = 0.0
    #convertPattern = convertToFloatFromPattern("[none]")
    #convertToFloatFromPatternWeight = 0.0
    #stringWeight = weightComputation("!@#$%ˆ&*()123456789")
    #stringLength = float(len("!@#$%ˆ&*()123456789"))

    # (0) string (1) float -- entropy (2) string -- heuristic (3) float (4) string -- pattern (5) float (6) label -- float -- true 1.0 || false 0.0 (7) float --- length (8) float -- weight
    incomingPath = arg1
    target_file = incomingPath
    print(target_file)
    data_set = loadtxt(target_file, delimiter='\t', converters={0: convertToNumber, 1: convertToFloat, 2: convertToFloatFromHeuristic, 3: convertToFloat, 4: convertToFloatFromPattern, 5:convertToFloat, 6:convertToFloat, 7:convertToFloat, 8:convertToFloat})

    X_test = data_set[0:,[1,2,3,4,5,7,8]]

    #X_test = [[entropy, floatHeuristic, floatHeuristicWeight, convertPattern, convertToFloatFromPatternWeight, stringLength, stringWeight]]
    
    import numpy as np
    dataset2 = np.genfromtxt(target_file, dtype='str')
    
    x_strings = dataset2[0:,[0]]
    
    print("step 9")
    print(X_test)

    y_pred = loaded_model.predict(X_test)
    y_pred = np.array(list(map(predictionOutcome, y_pred)))
    print("prediction: ", y_pred)

    output_file = target_file.replace("output-file_extra.txt", "results.txt")
    file2 = open(output_file, "w")

    i = 0
    while y_pred[i] < y_pred.size:
        #str1 = ("%s", y_pred[i])
        #str2 = ("%s", x_strings[i])
        #output_string = "{},{}".format(str1, str2)
        output_string = ("%s, %s" % (y_pred[i], x_strings[i]))
        print(output_string)
        file2.write(output_string + "\n")
        
        i = i + 1
        if(i == y_pred.size):
            break
    #print("shape of the y_pred array", y_pred.shape)
    #print("first 5 rows of the y_pred array", y_pred[:100])

    file2.close()

    print("step 10")
    #r2 = r2_score(y_test, y_pred)

    #precition = accuracyCalculation(y_test, y_pred)
    #print(precition)

    #y_pred2 = model.predict(X_plot)

    ##print("r2: ", r2)
    #
    #import matplotlib.pyplot as plt
    #import pandas as pd
    #
    #
    #for i in range(100):
    #    print('%s => %s (expected %s)' % (X[i].tolist(), y_pred[i], y_test[i]))
    #
    #df = pd.DataFrame({'x': range(0, len(y_test)), 'breadps values':y_test, 'breadps predicted':y_pred.flatten()})
    #plt.plot( 'x', 'breadps values', data=df, marker='', markerfacecolor='blue', markersize=10, color='skyblue', linewidth=2)
    #plt.plot( 'x', 'breadps predicted', data=df, marker='', color='olive', linewidth=1)
    #plt.legend()
    #plt.show()

# (0) string (1) float -- entropy (2) string -- heuristic (3) float (4) string -- patter (5) float (6) label -- float -- true 1.0 || false 0.0

import math

def convertToNumber(s):
    #string = s
    #string1 = string.replace("|-|", ",")
    #print(string1)
    #decimal_number = Decimal(int.from_bytes(str(string1).encode(), 'little'))
    decimal_number = Decimal(int.from_bytes(str(s).encode(), 'little'))
    #print(decimal_number)
    float_number = float(decimal_number)
    #print(float_number)
    return float_number

def convertFromNumber (n):
    decompress_string = zlib.decompress(n)
    return decompress_string.to_bytes(math.ceil(n.bit_length() / 8), 'little').decode()
    
def accuracyCalculation(arr1, arr2):
    
    hits = 0.0
    for x, y in zip(arr1, arr2):
        if x == y:
            hits = hits + 1.0
    
    return hits/float(len(arr1))
    
    
def convertToFloat(s):
    try:
        f = float(s)
        #print(f)
        return f
    except (Exception):
        return float(0)

def convertToFloatFromHeuristic(s):
    return_float = 0.0;
    string = str(s)
    
    if "none" in string:
        return 0.0
    
    while (("api" in string) or ("key" in string) or ("username" in string) or ("user" in string) or ("uname" in string) or ("pw" in string) or ("password" in string) or ("pass" in string) or ("email" in string) or ("metadata" in string) or ("mail" in string) or ("test" in string) or ("github" in string) or ("json" in string) or ("credentials" in string) or ("credential" in string) or ("login" in string) or ("token" in string) or ("secret" in string) or ("annotation" in string) or ("herobrine" in string) or ("aws" in string) or ("com_package" in string) or ("dot_com" in string) or ("home_folder" in string)):
        
        if "api" in string:
            string = string.replace("api", "")
            return_float = return_float + 4.0
        if "key" in string:
            string = string.replace("key", "")
            return_float = return_float + 4.0
        if "username" in string:
            string = string.replace("username", "")
            return_float = return_float + 4.0
        if "user" in string:
            string = string.replace("user", "")
            return_float = return_float + 4.0
        if "uname" in string:
            string = string.replace("uname", "")
            return_float = return_float + 4.0
        if "pw" in string:
            string = string.replace("pw", "")
            return_float = return_float + 4.0
        if "password" in string:
            string = string.replace("password", "")
            return_float = return_float + 4.0
        if "pass" in string:
            string = string.replace("pass", "")
            return_float = return_float + 4.0
        if "email" in string:
            string = string.replace("email", "")
            return_float = return_float - 4.0
        if "metadata" in string:
            string = string.replace("metadata", "")
            return_float = return_float + 10.0
        if "mail" in string:
            string = string.replace("mail", "")
            return_float = return_float - 10.0
        if "test" in string:
            string = string.replace("test", "")
            return_float = return_float + 4.0
        if "github" in string:
            string = string.replace("github", "")
            return_float = return_float - 4.0
        if "json" in string:
            string = string.replace("json", "")
            return_float = return_float - 4.0
        if "credentials" in string:
            string = string.replace("credentials", "")
            return_float = return_float + 4.0
        if "credential" in string:
            string = string.replace("credential", "")
            return_float = return_float + 4.0
        if "login" in string:
            string = string.replace("login", "")
            return_float = return_float + 7.0
        if "token" in string:
            string = string.replace("token", "")
            return_float = return_float + 4.0
        if "secret" in string:
            string = string.replace("secret", "")
            return_float = return_float + 4.0
        if "annotation" in string:
            string = string.replace("annotation", "")
            return_float = return_float - 4.0
        if "herobrine" in string:
            string = string.replace("herobrine", "")
            return_float = return_float - 10.0;
        if "aws" in string:
            string = string.replace("aws", "")
            return_float = return_float + 4.0
        if "com_package" in string:
            string = string.replace("com_package", "")
            return_float = return_float - 7.0
        if "dot_com" in string:
            string = string.replace("dot_com", "")
            return_float = return_float - 7.0
        if "home_folder" in string:
            string = string.replace("home_folder", "")
            return_float = return_float - 7.0
    
    return return_float

def convertToFloatFromPattern(s):
    return_float = 0.0;
    string = str(s)

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

    return return_float

def weightComputation(s):

    string = s.lower()
    return_float = 0.0;

    while (("password" in string) or ("pass" in string) or ("p@ss" in string) or ("p4ss" in string) or ("p4ss" in string) or ("pa!!" in string) or ("pa22" in string) or ("pa55" in string) or ("p@55" in string) or ("3works" in string) or ("pwd" in string) or ("word" in string) or ("super" in string) or ("w0rd" in string) or ("0rd" in string) or ("ord++" in string) or ("r@ndom" in string) or ("rand0m" in string) or ("12345" in string) or ("1234" in string) or ("uuid" in string) or ("123" in string) or ("456" in string) or ("bdadm" in string) or ("adm" in string) or ("abcdef" in string) or ("abc" in string) or ("def" in string) or ("asd" in string) or ("publickey" in string) or ("secretkey" in string) or ("secret" in string) or ("s3cret" in string) or ("secr3t" in string) or ("s3cr3t" in string) or ("sec" in string) or ("cr3" in string) or ("key" in string) or ("k3y" in string) or ("store" in string) or ("stor3" in string) or ("secure" in string) or ("s3cure" in string) or ("s3cur3" in string) or ("1nsecur3" in string) or ("insecure" in string) or ("!" in string) or ("-" in string) or ("+" in string)):
        
        if "password" in string:
            string = string.replace("password", "")
            return_float = return_float + 10.0
        if "pass" in string:
            string = string.replace("pass", "")
            return_float = return_float + 9.5
        if "uuid" in string:
            string = string.replace("uuid", "")
            return_float = return_float + 19.6
        if "p@ss" in string:
            string = string.replace("p@ss", "")
            return_float = return_float + 19.6
        if "p4ss" in string:
            string = string.replace("p4ss", "")
            return_float = return_float + 19.6
        if "pa!!" in string:
            string = string.replace("pa!!", "")
            return_float = return_float + 19.6
        if "pa22" in string:
            string = string.replace("pa22", "")
            return_float = return_float + 19.6
        if "pa55" in string:
            string = string.replace("pa55", "")
            return_float = return_float + 19.6
        if "p@55" in string:
            string = string.replace("p@55", "")
            return_float = return_float + 19.7
        if "pwd" in string:
            string = string.replace("pw", "")
            return_float = return_float + 7.0
        if "3works" in string:
            string = string.replace("3works", "")
            return_float = return_float + 7.1
        if "word" in string:
            string = string.replace("word", "")
            return_float = return_float + 5.0
        if "super" in string:
            string = string.replace("super", "")
            return_float = return_float + 5.1
        if "w0rd" in string:
            string = string.replace("w0rd", "")
            return_float = return_float + 19.5
        if "0rd" in string:
            string = string.replace("0rd", "")
            return_float = return_float + 19.5
        if "ord++" in string:
            string = string.replace("ord++", "")
            return_float = return_float + 19.5
        if "r@ndom" in string:
            string = string.replace("r@ndom", "")
            return_float = return_float + 19.5
        if "rand0m" in string:
            string = string.replace("rand0m", "")
            return_float = return_float + 19.5
        if "12345" in string:
            string = string.replace("12345", "")
            return_float = return_float + 10.0
        if "1234" in string:
            string = string.replace("1234", "")
            return_float = return_float + 10.0
        if "123" in string:
            string = string.replace("123", "")
            return_float = return_float + 5.0
        if "456" in string:
            string = string.replace("456", "")
            return_float = return_float + 5.5
        if "bdadm" in string:
            string = string.replace("bdadm", "")
            return_float = return_float + 15.0
        if "adm" in string:
            string = string.replace("adm", "")
            return_float = return_float + 15.5
        if "abcdef" in string:
            string = string.replace("abcdef", "")
            return_float = return_float + 19.5
        if "abc" in string:
            string = string.replace("abc", "")
            return_float = return_float + 15.0
        if "def" in string:
            string = string.replace("def", "")
            return_float = return_float + 10.0
        if "asd" in string:
            string = string.replace("asd", "")
            return_float = return_float + 10.0
        if "publickey" in string:
            string = string.replace("publickey", "")
            return_float = return_float - 25.0
        if "secretkey" in string:
            string = string.replace("secretkey", "")
            return_float = return_float + 5.0
        if "secret" in string:
            string = string.replace("secret", "")
            return_float = return_float + 5.0
        if "s3cret" in string:
            string = string.replace("s3cret", "")
            return_float = return_float + 19.5
        if "secr3t" in string:
            string = string.replace("secr3t", "")
            return_float = return_float + 19.5
        if "s3cr3t" in string:
            string = string.replace("s3cr3t", "")
            return_float = return_float + 19.5
        if "sec" in string:
            string = string.replace("sec", "")
            return_float = return_float + 5.0
        if "s3c" in string:
            string = string.replace("s3c", "")
            return_float = return_float + 15.5
        if "cr3" in string:
            string = string.replace("cr3", "")
            return_float = return_float + 10.0
        if "key" in string:
            string = string.replace("key", "")
            return_float = return_float + 5.0
        if "k3y" in string:
            string = string.replace("k3y", "")
            return_float = return_float + 15.5
        if "store" in string:
            string = string.replace("store", "")
            return_float = return_float + 5.0
        if "stor3" in string:
            string = string.replace("stor3", "")
            return_float = return_float + 15.5
        if "secure" in string:
            string = string.replace("secure", "")
            return_float = return_float + 10.0
        if "s3cure" in string:
            string = string.replace("s3cure", "")
            return_float = return_float + 16.0
        if "s3cur3" in string:
            string = string.replace("s3cur3", "")
            return_float = return_float + 19.5
        if "1nsecur3" in string:
            string = string.replace("1nsecur3", "")
            return_float = return_float + 19.5
        if "insecure" in string:
            string = string.replace("insecure", "")
            return_float = return_float + 10.0
        if "!" in string:
            string = string.replace("!", "")
            return_float = return_float + 0.5
        if "-" in string:
            string = string.replace("-", "")
            return_float = return_float + 0.5
        if "+" in string:
            string = string.replace("+", "")
            return_float = return_float + 0.5

    return return_float

def predictionOutcome(elem):
    if elem > 0.6:
        return 1.0
    else:
        return 0.0
    



import sys

if __name__ == "__main__":
    main(sys.argv[1])
