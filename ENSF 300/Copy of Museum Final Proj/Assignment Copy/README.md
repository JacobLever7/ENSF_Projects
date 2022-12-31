[![Open in Visual Studio Code](https://classroom.github.com/assets/open-in-vscode-c66648af7eb3fe8bc4f294546bfd86ef473780cde1dea487d3c4ff354943c9ae.svg)](https://classroom.github.com/online_ide?assignment_repo_id=9483584&assignment_repo_type=AssignmentRepo)
# Museum-Project
## To Do list:
- modify this file to include your group members information and tasks assigned per each
- modify this file to include any notes on how to use and run the program
- include any features you have added beyond the minimum requirements in a features list

## Organization:
- code folder: contains your main python application code
- sql scripts folder: contains all sql scripts required (database creation and initialization, sql script with query tasks in the handout, etc...)
- database design folder: EERD and relational schema
- optional data folder: has data files that you can sue to load data to your application if you use this optional implementation requirement

# Group Member Information
-- #1 Jori Duguid UCID: 30145690
- #2 Syril Jacob UCID: 30145947
-  #3 Jacob Lever UCID: 30147405

# Task Distribution 
- 1. EERD and relational schema were created communally with equal participation between all members, we thought this was important because having to brainstorm what exactly the task at hand is allows everyone to get a good understanding of what we were trying to achieve with the project.
- 2. The creation of the database was a job for Jori, who, along with Syril, was assigned with finding entries for tables in the database. This was decided because working on an incomplete database from multiple computers was inefficient in nature, because of merge conflicts and unability to see live updates. Jacob worked as a support member during this time he worked on making sure everything was implemented correctly and according to our goals for this project. Since the job of creating the database can be repetitive with the amount of entries, and things to keep track of made it hard to find small errors. Jacob served as a fresh set of eyes on the database which was important to catching errors and debugging.
- 3. The query Tasks assigned mainly for Syril, he wrote the majority of the scripts. But since it requires collaboration, all group members played a role in making sure they ran properly. 
- 4. We all decided to take initiative with designing the interface with python, but since it was easier to work on one computer to avoid commit conflicts, we gave Jacob the role of manager of the python file, Syril and Jori worked behind the shoulder helping Jacob successful create the interface. Because it was a little unfamiliar we encountered some learning curves which made it better to collaborate so that we all knew exactly what went into it. 

# How to Use Our Program
- 1. Initially, the user will be prompted with choosing one of 3 options, to:
1: Initialize/RESET Database(recommended only for the first time running this program or to RESET to default database)
2: Skip to database login
3: QUIT PROGRAM
considering valid input, the user can proceed with their choice.
IF the user enters one, the database will be connected to python and initialized. If the user wants any functions withing the database login to work, this must be done. It will prompt the user to enter their 'root' username and password (the one used for mysql). Note that once done, you wont need to do again until VScode is restarted. 
WHEN the user enters two, the user will be prompted to sign in:
They can sign in with three different accounts which all hold different priveledges
1. username: Administrator, password "admin"
which can acess
a) Administrator Privledges (according to project outline)
    -run sql scripts
b) Data Entry 
    -Lookup database
    -Insert data
    -update or remove data
c) Browsing
2. username : data_entry, password "user"
which can acess
a) Data Entry
    -Lookup database
    -Insert data
    -update or remove data
b) Browsing
3. username: Guest, no password
can only acess Browsing
The user can also select to quit the program at any point. 

# Basic Features
1. Can view any table within the database with any role.
2. Can Insert data/ tables to the database and remove/ update data.
3. Can run sql scripts to the database.
4. Can quit the program.

# Interface Login
1. Username: Administrator, Password: admin
2. Username: data_entry, Password: user
3. Username: guest, no Password

# Extra Requirements(For succesful compliation)
1. Must have 'tabulate' imported
2. Must have 'requests' imported
3. Must have 'numpy' imported
4. Must have 'Pandas' imported
5. Must have 'mysql.connector' imported

