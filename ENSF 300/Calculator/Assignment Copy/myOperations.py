#Jori branch

#function that takes 2 integers passed into it and adds them
def myAdd(a,b):
    x = a + b
    return x

#function that takes 2 integers passed into it and subtracts them
def mySub(a,b):
    x = a - b
    return x

#function that takes 2 integers passed into it and multiplies them
def myMul(a,b):
    x = a * b
    return x
#function that takes 2 integers passed into it and divides them
def myDiv(a,b):
    x = a // b
    return x

if __name__=="__main__":
    testnumber1 = 5
    testnumber2 = 4
    print("\nwhitebox testing")
    print("myAdd test, expected result: 9, result: ", myAdd(testnumber1,testnumber2))
    print("mySub test, expected result: 0, result: ", mySub(testnumber2,testnumber2))
    print("myMul test, expected result: 20, result:", myMul(testnumber1,testnumber2))
    print("myDiv test, expected result: 1, result:", myDiv(testnumber2,testnumber2))
    print("\n")