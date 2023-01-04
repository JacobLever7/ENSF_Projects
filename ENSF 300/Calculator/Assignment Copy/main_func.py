#inputs and validation
from evaluate_funct import evaluate_funct
from myOperations import myAdd, myDiv, myMul, mySub 
from print_funct import print_function
from validation_funct import validation_int, validation_operation

def main():
    
    print('\nWelcome to the ENSF 300 Calculator Program!\n')
    first_number = input('Enter the first integer: ')
    while validation_int(first_number) == False:
        first_number = input('Enter the first integer: ') 
    first_op = input('Enter first operation: ')
    while validation_operation(first_op) == False:
        first_op = input('Enter first operation: ') 
    second_number = input('Enter the second integer: ')
    while validation_int(second_number) == False:
        second_number = input('Enter the second integer: ') 
    second_op = input('Enter second operation: ')
    while validation_operation(second_op) == False:
        second_op = input('Enter second operation: ')
    third_number = input('Enter the third integer: ')
    while validation_int(third_number) == False:
        third_number = input('Enter the third integer: ') 
        
    number_list = [first_number, second_number, third_number]
    operator_list = [first_op,second_op]
    
        #call to evalutate expression
    result = evaluate_funct(operator_list,number_list)
        #call to print the expression
    print_function(operator_list,number_list,result)
    #whitebox testing