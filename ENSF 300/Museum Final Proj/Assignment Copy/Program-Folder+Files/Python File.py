import mysql.connector
from mysql.connector import Error
from tabulate import tabulate
import pandas as pd
import pwinput


def create_server_connection(host_name, user_name, user_password, database_name):
    connection = None
    while True:
        try:
            connection = mysql.connector.connect(
                host = host_name,
                user = user_name,
                passwd = user_password,
                database = database_name
            )
            print("MySQL Database connection successful")
            break
        except Error as err:
            print(f"Error: '{err}'")
            print("Please try again...")
            user_name = input("Please input username:\t")
            user_password = pwinput.pwinput(prompt = "Please input password:\t", mask = '*')
            print(60*"-")
            pass

    return connection, user_name

def print_function(results, cursor):
    print("Printing results...\n")

    field_names = [i[0] for i in cursor.description]

    from_db = []

    for result in results:
        result = list(result)
        from_db.append(result)

    df = pd.DataFrame(from_db, columns=field_names)
    print(tabulate(df, headers = 'keys', tablefmt = 'psql'))   

def press_key():
    print("\n----Press any key to continue----")
    input()
    
def admin_interface(connection):
    while True:
        press_key()
        print("🎉Welcome to the administrator interface!🎉")
        print("------------------------------------------")
        print("Select one of the following options:")
        print("1: Run SQL Commands")
        print("2: QUIT TO INTERFACE SELECTION")

        choice = input("Your choice is:\t")
        while choice not in ['1','2']:
                print("Invalid input, please input a number from 1-2")
                choice = input("Your choice is:\t")

        if choice == '1':
            while True:   
                print("Please type in your SQL command below, do not user ENTER key until you have finished writing query.")
                admin_query = input().lower()
                if read_data(connection, admin_query) == Error:
                    print("+------------------------------------+")
                    print("| Invalid query, please try again... |")
                    print("+------------------------------------+")
                    continue
                else:
                    print("Running command...")
                    results, cursor = read_data(connection, admin_query)
                    break

                
            if admin_query.find('select') >= 0:
                print_function(results, cursor)
            else:
                print("Command successfully executed.")
        else:
            print("\nQUITING TO INTERFACE SELECTION...\n")
            return    
        
def data_entry_interface(connection):
    while True:
        press_key()
        print("🎉Welcome to the data-entry interface!🎉")
        print("---------------------------------------")
        print("Select one of the following options:")
        print("1: Lookup Database")
        print("2: Insert Data From File")
        print("3: Update or Delete Data")
        print("4: QUIT TO INTERFACE SELECTION")

        choice = input("Your choice is:\t")
        while choice not in ['1','2','3','4']:
                print("Invalid input🤦😭😭, please input a number from 1-4")
                choice = input("Your choice is:\t")

        if choice == '1':
            browsing_interface(connection)
        elif choice == '2':
            print("The following tables are available for selection:")
            cursor = connection.cursor()

            cursor.execute("SHOW TABLES")
            tables = cursor.fetchall()
            print(60*'-')
            list = []
            for table_name in tables:
                print(table_name[0])
                list.append(table_name[0])
            print(60*'-')

            
            print("+----------------------------------------------------------+")
            print("| NOTE: if the 'id_no' does not exist in 'ART_OBJECTS'     |")
            print("| you will NOT be able to insert data into any other table |")
            print("| Add the data to 'ART_OBJECTS' first                      |")
            print("+----------------------------------------------------------+")

            print("Type the name of the table you would like to select.")
           
            table_name = input().upper()
            while table_name not in list:
                print("Invalid table name🤦😭😭, please try again.")
                table_name = input().upper()

            print(f"Table {table_name} selected.")
    
            query = f"""
                    SELECT *
                    FROM {table_name}
            """
            results, cursor = read_data(connection, query)
            
            field_names = [i[0] for i in cursor.description]

            num = 0
            for word in field_names:
                num += 1
            
            file_path = 'ENSF 300/Museum Final Proj/Assignment Copy/Program-Folder+Files/data_entry.txt'

            with open(file_path, 'w') as file:
                i=0
                for item in field_names:
                    if i < num-1:
                        # write each item
                        file.write("%s, " % item)
                    elif i == num-1:
                        file.write("%s" % item) 
                
                    i+=1    
                print(f"Imported format into 'data_entry.txt' please fill in new data now..." )

            press_key()
            
            #create command for query
            command = ', '.join(field_names)
            
            #create list with number of variables for query
            variables = []
            i=0
            while i < num:
                variables.append('%s')
                i+=1
            
            variables_string = ', '.join(variables)


            #read new data
            
            data_list = open(file_path).read().splitlines()
            for string in data_list:
                if string == command:
                    print("+-----------------------------------+")
                    print("| ❗Data was not added to the file❗ |")
                    print("+-----------------------------------+")
                    print("\nQUITING TO PREVIOUS MENU...\n")
                    return
                else:
                    break

            
            
            list_of_list = [i[:].split(', ') for i in data_list]
            
            #change id_no to int data type
            i=0
            for list_item in list_of_list:
                list_of_list[i][0] = int(list_of_list[i][0])
                i+=1
                
            
            for index, l in enumerate(list_of_list):
                list_of_list[index] = tuple(l)
            
            #generate query from user input
            data_insert_query = f"""

                        INSERT INTO {table_name} ({command}) 
                        VALUES ({variables_string})
            """
        

            run_list_query(connection, data_insert_query, list_of_list)
            
        elif choice == '3':
            print("Would you like to UPDATE or DELETE data?")
            print("1: UPDATE")
            print("2: DELETE")
            option = input("Your choice is:\t")
            #validate input
            while option not in ['1', '2']:
                print("Invalid input😞😭, please try again...")
                option = input("Your new input is:\t")
            if option == '1':
                #display tables
                print("Select a table you would like to UPDATE data for:")
                cursor = connection.cursor()

                cursor.execute("SHOW TABLES")
                tables = cursor.fetchall()
                print(60*'-')
                list = []
                for table_name in tables:
                    print(table_name[0])
                    list.append(table_name[0])
                print(60*'-')

                print("Type the name of the table you would like to select.")
            
                table_name = input().upper()
                while table_name not in list:
                    print("Invalid table name🤦😭😭, please try again.")
                    table_name = input().upper()

                print(f"Table {table_name} selected.")

                id_no_query = f"""
                            SELECT id_no
                            FROM {table_name}
                """
                y, cursor = read_data(connection, id_no_query)
                
                id_no_list = [i[0] for i in y]

                query = f"""
                        SELECT *
                        FROM {table_name}
                """
                results, cursor = read_data(connection, query)
                
                
                print("Your current selection is:")
                print_function(results, cursor)
                
                field_names = [i[0] for i in cursor.description]
                
                print("Type the 'id_no' for the data tuple that you would like to UPDATE:")
                id_no_up = int(input("'id_no':\t"))
                
                while id_no_up not in id_no_list:
                    print("'id_no' not in the table, please choose a valid 'id_no'...")
                    id_no_up = int(input("Input new 'id_no':\t"))
                
                #show refined table
                refined_query = f"""
                        SELECT *
                        FROM {table_name}
                        WHERE id_no = '{id_no_up}'
                """
                refined_results, cursor = read_data(connection, refined_query)

                print("Your current selection is now:")
                print_function(refined_results, cursor)
                
                print("Select which item you would like to update:")
                print(60*'-')
                i = 0    
                for obj in field_names[1:]:
                    print(f"{obj}")
                    i+=1
                print(60*'-')
                selection_list = []
                new_value = input("Selection:\t")
                while new_value not in field_names[1:]:
                    print("Invalid selection, please try again🤦😭😭")
                    new_value = input("Selection:\t")
                selection_list.append(new_value)

                n = 1
                while n <= i:
                    print("Would you like to select another item?")
                    print("1: YES")
                    print("2: NO")
                    user_input = input()
                    while user_input not in ['1', '2']:
                        print("Invalid input, please try again🤦😭😭")
                        user_input = input("New input:\t")
                    if user_input == '1':
                        new_value = input("Selection:\t")
                        while new_value not in field_names[1:]:
                            print("Invalid selection, please try again🤦😭😭")
                            new_value = input("Selection:\t")
                        selection_list.append(new_value)
                        continue
                    elif user_input == '2':
                        break

                #ask user for value associated to selection list
                value_list = []
                for select in selection_list:
                    value_list.append(input(f"The new value for {select} is: "))

                set_string = ""
                i=0
                for value in selection_list:
                    if i < (len(value_list)-1):
                        set_string += f"{value} = '{value_list[i]}', "
                    elif i == (len(value_list)-1):
                        set_string += f"{value} = '{value_list[i]}'"
                    i+=1
        
                update_query = f"""
                            UPDATE {table_name}
                            SET {set_string}
                            WHERE id_no = '{id_no_up}';   
                """
                run_query(connection, update_query)
            elif option == '2':
                #display tables
                print("Select a table you would like to DELETE data from:")
                cursor = connection.cursor()

                cursor.execute("SHOW TABLES")
                tables = cursor.fetchall()
                print(60*'-')
                list = []
                for table_name in tables:
                    print(table_name[0])
                    list.append(table_name[0])
                print(60*'-')

                print("Type the name of the table you would like to select.")
            
                table_name = input().upper()
                while table_name not in list:
                    print("Invalid table name🤦😭😭, please try again.")
                    table_name = input().upper()

                print(f"Table {table_name} selected.")

                id_no_query = f"""
                            SELECT id_no
                            FROM {table_name}
                """
                y, cursor = read_data(connection, id_no_query)
                
                id_no_list = [i[0] for i in y]

                query = f"""
                        SELECT *
                        FROM {table_name}
                """
                results, cursor = read_data(connection, query)
                
                
                print("Your current selection is:")
                print_function(results, cursor)
                
                field_names = [i[0] for i in cursor.description]
                
                print("Type the 'id_no' for the data tuple that you would like to DELETE:")
                id_no_del = int(input("DELETE 'id_no':\t"))
                
                while id_no_del not in id_no_list:
                    print("'id_no' not in the table, please choose a valid 'id_no'...")
                    id_no_del = int(input("Input new 'id_no':\t"))
                
                delete_query = f"""
                            DELETE FROM {table_name}
                            WHERE id_no = '{id_no_del}';   
                """
                run_query(connection, delete_query)
        else:
            print("\nQUITING TO INTERFACE SELECTION...\n")
            return    

def browsing_interface(connection):
    while True:
        print("🎉Welcome to the browsing Interface!🎉")
        print("-------------------------------------")
        print("The following tables are available for selection:")
        cursor = connection.cursor()

        cursor.execute("SHOW TABLES")
        tables = cursor.fetchall()
        print(60*'-')
        list = []
        for table_name in tables:
            print(table_name[0])
            list.append(table_name[0])
        print(60*'-')

        print("Type the name of the table you would like to select.")
        table_name = input().upper()
        while table_name not in list:
            print("Invalid table name🤦😞😭😭, please try again.")
            table_name = input().upper()

        print(f"Table {table_name} selected.")
        query = f"""
                SELECT *
                FROM {table_name}
        """
        results, cursor = read_data(connection, query)
        print("Your current selection is:")
        print_function(results, cursor)

        print("Would you like to veiw a different table?")
        print("1: YES")
        print("2: NO")
        option = input()

        while option not in ['1','2']:
            print("Invalid input, please try again.")
            option = input("Your new input is:\t")

        if option == '1':
            pass 
        elif option == '2':
            print("\nQUITING TO PREVIOUS MENU...\n")
            break

def run_query(connection, query):
    cursor = connection.cursor()
    try:
        cursor.execute(query)
        connection.commit()
        print("Query successfull")
    except Error as err:
        print(f"Error: '{err}'")
    
def read_data (connection, query):
    cursor = connection.cursor()
    result = None
    try:
        cursor.execute(query)
        result = cursor.fetchall()
        return result, cursor
    except Error as err:
        print(f"Error: '{err}'")
        return Error

def run_list_query(connection, query, list_of_vals):
    cursor = connection.cursor()
    try:
        cursor.executemany(query, list_of_vals)
        connection.commit()
        print("Query successful")
    except Error as err:
        print(f"Error: '{err}'")

def executeScriptsFromFile(connection, filename):
    cursor = connection.cursor()
    with open(filename, 'r') as sql_file:
        commands = cursor.execute(sql_file.read(), multi=True)
        for command in commands:
            print("Running query: ", command)
            if command.with_rows:
                    fetch_result = command.fetchall()
                    print(fetch_result)
            elif command.rowcount > 0:
                    print(f"Affected {command.rowcount} rows" )

    connection.commit()

def main_menu():
    print("MAIN MENU")
    print("---------")
    print("Please choose one of following the options to get started:")
    print("1: Initialize/RESET Database(recommended only for the first time running this program or to RESET to default database)")
    print("2: Skip to database login")
    print("3: QUIT PROGRAM")
    option = input("Your choice is:\t")
    
    while option not in ['1','2','3']:
            print("Invalid input, please input a number from 1-3")
            option = input("Your choice is:\t")
    
    if (option == '1'):
        print("Establishing connection to MySQL on local computer")
        print(60*"-")
        print("Please input your username(typically 'root') and password to connect to your local instance of MySQL:")
        localusername = input("Please input username:  ")
        localpassword = pwinput.pwinput(prompt = "Please input password:  ", mask = '*')
        print("Starting connection...")

        #connect function without database name as we have not yet initialized the database
        connection = None
        while True:
            try:
                connection = mysql.connector.connect(
                    host = "localhost",
                    user = localusername,
                    passwd = localpassword
                )
                print("MySQL connection successful")
                break
            except Error as err:
                print(f"Error: '{err}'")
                print("Please try again...")
                user_name = input("Please input username:\t")
                user_password = pwinput.pwinput(prompt = "Please input password:\t", mask = '*')
                print(60*"-")
                pass
        
        print("Initializing database...")
        print(60*"-")
        filename = "ENSF 300/Museum Final Proj/Assignment Copy/sql-scripts-folder/museumDB.sql"
        executeScriptsFromFile(connection, filename)
        print(60*"-")
        print("Successfully initialized!")
    elif (option == '2'):
        print("Skipping to database login...")
        
    elif (option == '3'):
        print("\nGoodbye 👋\n")
        return
        


def main():
    
    main_menu()
    
    #ask for user input
    print("To skip login use username: 'guest'")
    print(60*'-')
    username = input("Please input username to database:  ")
    
    if (username != 'guest'):
        password = pwinput.pwinput(prompt = "Please input password to database:  ", mask = '*')
    else:
        password = ''
    default_db = "Museum"
    print(60*"-")

    #connect to database
    connection, username = create_server_connection("localhost", username, password, default_db)
    print(f"You are logged in as:\t{username}") 

    print(60*"-")
  
    while True:
        print("Which interface would you like to access?")
        print("1: Administrator")
        print("2: Data Entry")
        print("3: Browsing")
        print("4: QUIT PROGRAM")
        interface_type = input("Interface: ")
        while interface_type not in ['1', '2', '3', '4']:
            print("Invalid input🤦😭😭, please try again")
            interface_type = input("Interface: ")
        while interface_type == '1' and username == 'guest':
            print("❗You do not have permission to access this interface❗")
            interface_type = input("Please input different interface: ")
        while interface_type == '1' and username == 'data_entry':
            print("❗You do not have permission to access this interface❗")
            interface_type = input("Please input different interface: ")
        while interface_type == '2' and username == 'guest':
            print("❗You do not have permission to access this interface❗")
            interface_type = input("Please input different interface: ") 

        if interface_type == '1':
            admin_interface(connection)
        elif interface_type == '2':
            data_entry_interface(connection)
        elif interface_type == '3':
            browsing_interface(connection)
        elif interface_type == '4':
            print("\nGoodbye 👋\n")
            return

if __name__ == '__main__':
    main()