

def validation_int(number):
    try:
        int(number)
        it_is = print('valid number')
    except ValueError:
        it_is = print('invalid input please try again')
        return False
#validation function to check if valid operator from list of valid operators
def validation_operation(op):
        list_operations = ['+','-','*','//','/']
        if op in list_operations:
            print('valid operation')
        else: 
            print('invalid input please try again')
            return False
# whitebox testing
if (__name__ == '__main__'):
    print("\nWhite box testing\n")
    first_number = input("Enter the first integer: ")
    print("--actual output---")
    while validation_int(first_number) == False:
        first_number = input("Enter the first integer: ") 
    print("--Expected output--\n valid number")
    first_op = input('Enter first operation: ')
    print("--actual output--")
    while validation_operation(first_op) == False:
        first_op = input("Enter first operation: ")
    print("--Expected output--\nvalid operation")