# TimeManagementApp
# Database REST application
This project is a multifunctional application for time and task management, communication with other users, and social interactions.
## 1. Description of the project:
* **Project Name**: An application for self-development to achieve goals.
* **Author**: Pavlyutkin Pavel Sergeevich

## 2. Minimum functional requirements:
* User Authentication/Authorization/Registration
* The possibility of an authorized/registered user:
   * ability to create/delete/edit tasks:
      * adding a description to the task
      * adding priority to a task (adding a category)
      * adding execution time to a given task
      * the date of addition will be set automatically / the completion date will be created automatically after the user completes the task
   * Tracking progress on a specific task
   * tracking progress on completed tasks (after completing the task, the average_score in users will be increased 
   * adding a review (this is a page that will be offered to the user at the end of the day to track his impressions / mood - it will present a regular text field so that the user is not limited in anything, where the user can share his impressions for the day
   * adding posts - a post that other users will already be able to see, as well as be able to comment and rate it 
   * possible purchase of certain things for your own currency (counter of completed tasks)
* the ability to view the purchase history for introspection where the money was spent and for what
* Role system (authorized /unauthorized user; administrator (the one who will monitor the user's progress (will act as an observer, he will not be able to create a user)))
* Tracking user progress (admin option)

## 3. Description of entities:
1. **User (Users)**
- The user is the main entity that stores information about registered users.
2. **Roles**
- The role determines the access level and user rights in the system:
      - authorized / registered 
      - Administrator - a user who is an observer can track the progress of registered users (completed tasks)
3. **Review**
- This is a page that will be offered to the user at the end of the day to track his impressions / mood - it will represent a regular text field so that the user is not limited in anything, where the user can share his impressions for the day (add a picture according to mood and a description to it and then after a while - it will be possible to return to view every day))(this is like a personal page that is visible only to the author of this "review"
4. **Tasks**
- This entity represents tasks that users can create and manage (create a task description, give priority (add a category)).
5. **Task category (Label)**
- This entity allows you to group tasks into categories (by priority), add styles (colors, etc.) for the task so that they can be distinguished and it is convenient for the user to sort them himself.
6. **Task Progress (task progress) (UserProgress)**
- This entity tracks the progress of a specific user task.
7. **Post (Posts)**
- This entity is a page that can be filled in by a registered user, like a post on a social network that other users can see and comment on.
8. **Comments**
- This entity will allow authorized users to leave comments on other users' posts. 
9. **Achievements**
- This entity allows users to earn rewards and achievements for completing certain tasks, achieving certain goals, or actively using the application. This can be an incentive to engage and motivate users.
10. **Product (Products)**
- This entity represents a specific product that is available for purchase in the store (style for the task, custom image, etc.).
11. **Purchase History**:
- Each user can have access to their own purchase history to view information about their previous purchases and the ability to analyze their purchase history. This can be useful for tracking activity.
      
## 4. Description of the table:
1. **User (Users)**
- Fields:
     - ID: integer, primary key, auto-increment.
     - Username: varchar(50), unique, NOT NULL.
     - E-mail (Email): varchar(255), unique.
     - Password: varchar(255), hashed password, NOT NULL.
   - Communication:
     - A user can have one role (One-to-One).
     - The user can have many tasks (One-to-Many).
     - A user can have one or more reviews (One-to-Many).
     - A user can have one or more achievements (One-to-Many).
     - All users can have only one store.(Many-to-One)
2. **Roles**
- Fields:
     - ID: integer, primary key, auto-increment.
     - Name (Role_Name): varchar(50), unique, NOT NULL.
   - Communication:
     - A user can have one role (One-to-One).
3. **Review**
- Fields:
     - ID: integer, primary key, auto-increment.
     - Description: text, NOT NULL.
     - Creation date(create_time): datetime - timestamp is NOT NULL.
   - Communication:
     - One user can have many posts (One-to-Many).
4. **Tasks**
- Fields:
     - ID: integer, primary key, auto-increment.
     - Title - Title of the task: varchar(255), NOT NULL.
     - Description: text.
     - Due Date: Date and time - will be created immediately after adding the task (datetime - timestamp), NOT NULL.
     - Execution (Check):boolean, NOT NULL.
   - Communication:
     - One task can have several categories and one category can have multiple tasks (label) (Many-to-Many).
     - Each task has a One-to-One relationship with progress.
     - Each task belongs to a specific user (One-to-One).
5. **Task Category (Label)**
- Fields:
     - ID: integer, primary key, auto-increment.
     - Category: varchar(255), NOT NULL.
     - Priority: varchar(255), NOT NULL.
     - Style: varchar(100).
- Link:
     - Task categories are related to tasks - one task can have multiple categories and one category can have multiple tasks (Many-to-Many).
6. **Task Progress (UserProgress)**/..
- Fields:
     - ID: integer, primary key, auto-increment.
     - Task progress: integer, NOT NULL.
     - Start Time: Date and time - will also be added automatically (datetime - timestamp), NOT NULL.
     - End Time: The date and time will also be added automatically (datetime - timestamp), NOT NULL.
   - Communication:
     - Each progress record is associated with a specific task (One-to-One).
7. **Post (Posts)**
- Fields:
     - ID: integer, primary key, auto-increment.
     - Author of the post (Authot): varchar(255), NOT NULL.
     - Title of the post: varchar(255), NOT NULL.
     - The text of the article (Content): text.
     - Creation Date: Date and time (datetime - timestamp), NOT NULL.
   - Communication:
     - A user can have multiple posts (Many-to-One).
8. **Comments**
- Fields:
     - ID: integer, primary key, auto-increment.
     - Author of the post (Authot): varchar(255), NOT NULL.
     - Title of the post: varchar(255), NOT NULL.
     - The text of the article (Content): text.
     - Creation Date: Date and time (datetime - timestamp), NOT NULL.
   - Communication:
     - There can be many comments on one post (Many-to-One).
9. **Achievements**
- Fields:
     - ID: integer, primary key, auto-increment.
     - Name: varchar(255), NOT NULL.
     - Description: text.
   - Communication:
     - Achievements can be earned by users - one user can have several achievements (for example, 10 tasks completed in a day, completed in such and such a time, etc.) (Many-to-One).
10. **Product (Products)**
- Fields:
        - ID: integer, primary key, auto-increment.
        - Name: varchar(255), NOT NULL.
        - Description: text.
        - Price: float - decimal, NOT NULL.
        - Image: string is the path to the product image.
        - Availability: boolean, NOT NULL.
    - Communication:
        - Products are associated with a user - one user can have many products and different users can have the same product (Many-to-Many).
11. **Purchase History**
- Fields:
       - ID: integer, primary key, auto-increment.
       - List of purchased products (Product List): text.
       - Amount spent (Total amount): decimal.
     - Communication:
       - Achievements can be earned by users - one user can have several achievements (for example, 10 tasks completed in a day, completed in such and such a time, etc.) (Many-to-One).
        
## 5. Schema:
![image](https://github.com/PavlyutkinPavel/TimeManagementApp/assets/93840829/08ca06a7-e97b-462a-94b9-2a9e472128e0)

## 6. Technologies used for the REST project
  1) Spring Boot
  2) Spring Core
  3) Spring Data Jpa
  4) SQL
  5) Spring Security
  6) Java 8+ features
  7) Flyway
  8) Swagger

## 7. Description of the functionality
