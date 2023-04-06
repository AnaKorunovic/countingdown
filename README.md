# countingdown application - owerview

The application was developed for the needs of the Master's subject "Alati i metode vestacke inteligencije" at the Faculty of organizational sciences. The goal of the application was primarily to master the Clojure programming language and its concepts. Clojure is a dynamic and functional dialect of the Lisp programming language on the Java platform and the first functional language I used in my studies(life) so far. In this regard, implementing an application in a language that was not familiar to me was a serious and not easy task to complete. The whole process required a lot of surfing the Internet and searching for useful literature and projects (good practices). The most demanding thing was to adopt a new logic of thinking, which was quite different from programming in object-oriented languages.
The main purpose of the application is to provide suggestions for the daily menu based on the age, height, weight, gender and activity of the user. The idea for the app came naturally because I am very dedicated to training and proper nutrition. In addition to the aforementioned functions, which are available to all users, an administrative function has also been introduced, which includes reviewing and adding recipes.


# Functionalities:
The functionality of the dountingdown application is explained below:
-When the application is started, an index page is available to all users, which displays a welcome message. If the user wants to access all other functions, he/she must log in.
-It is also possible to register a new user, but only if there is no user with the same username in the database.
-The user can log in as an administrator or as a normal user
-When the administrator is logged in, all the functions of the application are available to him: searching for recipes, creating a new recipe, accessing the database with all the foods, creating a daily diet plan, etc.
-For normal users, the functions of searching the food database, sorting, and creating a daily nutrition plan based on the data entered through the form. The data required to create the plan are height, weight, physical activity, gender and age. The determination of calories is based on a generally accepted algorithm.
-Sessions are used for user management, distinguishing between users with the role of administrator and normal users.
-The application uses an SQLite database, as well as the concept of .csv files for storing recipes and ingredients. Users database stores user's information (id, name, username, password).  In the application, caloric values, the total number of proteins, carbohydrates, and fats per 100g of product are available for each food item/dish.


# Application's routes - examples

## route "/" - Index page
The main route is the "/" route. This route opens a blank page with a navigation bar and a welcome message. The user can log in from this point, as an administrator or a regular user. Users can also register. When a user logs in as a regular user, they will be redirected to log in as a user. A login is required to continue using the application. If the user logs in as admin, he will be redirected to the route "/recipes"

In rhis appplication, there are 2 roles(admin and client).

Client credentials to log in for example:
username: test password: test
Admin credentials to log in are always:
username: admin password: 12345

##route "/register"
The registration route contains a form for adding a new user that reports an error if there is already a user with the entered username.

##route "/login"
The login route contains a form for logging that reports an error if the username or password aren't correct. All fields in this form are required.

##route "/food"
On this route, there is a table with all the foods available in the application, sorted by the group they belong to. Some of the food groups are vegetables, meat, eggs, snacks, etc

##route "/data"
This route can only be accessed by a logged-in user. To generate the report, it is necessary to fill in all the form fields. Also, the age, height, and weight fields must be numbers, otherwise, the user will be notified of an error. The gender entry field must have the value male/female.If all fields aren't filled, the message "You must fill all fields" will be shown. If "age" filled contains letters, the message "Age field must contain only digits." will be shown. If "Height" filled contains letters, the message "Height field must contain only digits." will be shown, etc.
At the click of a button, the user receives a personalized nutrition plan, the total number of calories to consumed, as well as the amount of protein, carbohydrates, and fats needed to achieve results.

##route "/addmeal"
This route can only be accessed by a user with admin privileges. The functionality available at this route is adding a new recipe. The new recipe is saved in a .csv file, which can only be accessed by the admin.

#Testing
For testing, I used a "clojure.test" and Midje library. In the package test.countingdown.handler and test.countingdown.logic various tests cover the calls of the routes, as well as the logic of the application itself.


#Problems I ran into

While developing the application, I encountered several significant problems due to my inexperience. First of all, I had to get used to a completely new way of thinking and adopt the syntax of the Clojure programming language. At the beginning, it was necessary to find the development environment that I was most comfortable with and understand how it worked. Eventually I decided to use InteliJ IDEA environment.
After taking small steps into the world of functional programming, I encountered the problem of sending POST requests where I failed to send data through the form (i.e. read it from the request body). I tried to solve this problem in countless ways, trying every solutions I found from stack overflow, studied various literature, but without success. Finally, I managed to find the answer on the stackoverflow website int this post: https://stackoverflow.com/questions/37397531/ring-read-body-of-a-http-request-as-string, but after many failed attempts.
What prevented me most from understanding this language quickly and easily is the lack of specific literature that contains a set of solutions of more complex tasks (good practices).
The next step in developing the application was to incorporate csv files for data storage. The main difficulty in this area was the algorithm, which was executed too slowly. Along the way to solving the problem, I was helped by a professor who pointed out that the algorithm was based on multiple passes through the csv file, which resulted in poor performance. 
After completing the main functions, I worried about implementing sessions in the application and assigning roles to users. I had to read a lot of literature and understand how sessions work in Clojure and how to integrate them into my application. Incorporating sessions into the application meant deciding which routes would be available to which users, since the original idea was that the application would only be used by one user. Eventually, I created separate sessions for users and admin using ring.middleware.session.
In addition to sessions, user data also needed to be stored, so I decided to use an embedded SQLite database. Since I had never used this database before, it took me a while to figure out how to connect to it. I fount some documentation on this topic and successfully implemented SQLite into my app.
The easiest stuff were testing and writing hiccup code for frontend.
Although I had books available that contained an overview of the Clojure programming language, I learned the most from the mistakes and difficulties I had in developing the application.

##Conclusion
The development of the application took a long time, but I am glad that I learned a new programming language and a way of programming that I was not familiar with.
As for my future plans, I would like to improve my knowledge of functional programminga a bit more.

#References

-Clojure For the Brave and True, Daniel Higginbotham
-Web Development with Clojure, Dmitri Sotnikov
-https://clojure-doc.org/
-https://github.com/bbatsov/clojure-style-guide
-Stack overflow
-and many more.. :)





