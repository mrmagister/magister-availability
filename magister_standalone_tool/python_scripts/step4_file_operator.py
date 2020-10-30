#!/usr/bin/python
from numpy import loadtxt
from keras.models import Sequential
from keras.layers import Dense
from sklearn.model_selection import train_test_split
from sklearn.preprocessing import StandardScaler
from keras.optimizers import SGD
from decimal import Decimal
import zlib


from io import StringIO
import numpy as np

def main(arg1):
    # Import the dataset
    print("step 1")
    incoming_path = arg1
    dataset_file_location = incoming_path
    print(dataset_file_location)
    # Using readlines()
    file1 = open(dataset_file_location, 'r')
    Lines = file1.readlines()
    count = 0

    output_file = incoming_path.replace("output-file.txt", "output-file_extra.txt")
    file2 = open(output_file, "w")

    for line in Lines:
        count = count + 1
        string1, string2, string3, string4, string5, string6, string7 = line.split("\t")
        length = len(string1)
        if "\n" in line:
            line = line.replace("\n","\t")
        else:
            line = line + "\t"
        
        line = line + str(float(length)) + "\t"
        line = line + str(weightComputation(string1)) + "\n"
        print("Line{}: {}".format(count, line.strip()))
        file2.write(line)

    file2.close()

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
    
    
# (0) string (1) float -- entropy (2) string -- heuristic (3) float (4) string -- patter (5) float (6) label -- float -- true 1.0 || false 0.0

import sys

if __name__ == "__main__":
    main(sys.argv[1])
