from myOperations import myAdd,myDiv,myMul,mySub
def evaluate_funct(a, b):
   op_1 = a[0]
   op_2 = a[1]
   num_1 = int(b[0])
   num_2 = int(b[1])
   num_3 = int(b[2])

   if op_1 == '*':
      result_1 = myMul(num_1, num_2)
      if op_2 == '*':
         result = myMul(result_1, num_3)
      elif op_2 == '/' or op_2 == '//':
         result = myDiv(result_1, num_3)
      elif op_2 == '+':
         result = myAdd(result_1, num_3)
      else:
         result = mySub(result_1, num_3)
   elif op_1 == '/' or op_1 == '//':
      result_1 = myDiv(num_1, num_2)
      if op_2 == '*':
        result = myMul(result_1, num_3)
      elif op_2 == '/' or op_2 == '//':
         result = myDiv(result_1, num_3)
      elif op_2 == '+':
         result = myAdd(result_1, num_3)
      else:
         result = mySub(result_1, num_3)
   elif op_2 == '*':
      result_2 = myMul(num_2,num_3)
      if op_1 == '+':
         result = myAdd(num_1, result_2)
      else:
         result = mySub(num_1, result_2)
   elif op_2 == '/' or op_2 == '//':
      result_2 = myDiv(num_2, num_3)
      if op_1 == '+':
         result = myAdd(num_1, result_2)
      else:
         result = mySub(num_1, result_2)
   elif op_1 == '+':
      result_1 = myAdd(num_1,num_2)
      if op_2 == '+':
         result = myAdd(result_1, num_3)
      else:
         result = mySub(result_1, num_3)
   elif op_1 == '-':
      result_1 = mySub(num_1, num_2)
      if op_2 == '+':
         result = myAdd(result_1, num_3)
      else:
         result = mySub(result_1, num_3)
      

   return result


if __name__=="__main__":        #white box testing
    x = ['+','/']
    y = [2,4,6]
    a = evaluate_funct(x,y)
    print("\nWhite box testing\n")
    print("evaluate_funct input: 2 + 4 / 6\nExpected output: 2")
    print("Actual output:", a)