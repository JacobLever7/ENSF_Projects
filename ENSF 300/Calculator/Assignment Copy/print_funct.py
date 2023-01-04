#prints the equation and result 
def print_function(a,b,result):
    a.append('=')
    print("\nYour equation is:\n")
    print("{} {} {} {} {} {} {}\n".format(b[0],a[0],b[1],a[1],b[2],a[2],result))

if (__name__ == '__main__'):
    testnumber1 = ['+','-']
    testnumber2 = [1,2,3]
    result = 4
    print(print_function(testnumber1,testnumber2,result))