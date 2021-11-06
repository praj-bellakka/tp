# Brendan Lau Siew Zhi (brendanlsz) - Project Portfolio Page

## Product Overview
FitNUS Tracker is a desktop app for tracking daily food intake to help users keep a healthy lifestyle. 
FitNUS is specially made for Computing Students living in University Town, with features and 
functionalities taylor made for them. It is based on a Command Line Interface (CLI) and has 
rich functionality in order to cater to the needs of potential users.


## Summary of Contributions
Backend Software Engineer: Responsible for implementing various backend features including
`Storage`, `FoodDatabase`, and `EntryDatabase`.

### Code Contributed

[This is the RepoSense report of the code contributed.](https://nus-cs2113-ay2122s1.github.io/tp-dashboard/?search=&sort=groupTitle&sortWithin=title&timeframe=commit&mergegroup=&groupSelect=groupByRepos&breakdown=true&checkedFileTypes=docs~functional-code~test-code~other&since=2021-09-25&tabOpen=true&tabType=authorship&tabAuthor=brendanlsz&tabRepo=AY2122S1-CS2113T-W12-1%2Ftp%5Bmaster%5D&authorshipIsMergeGroup=false&authorshipFileTypes=docs~functional-code~test-code~other&authorshipIsBinaryFileTypeChecked=false)


### Enhancements Implemented

Implemented the core functionalities of the following:
- `Storage`: I implemented the file storage functionality, which allows the FitNus application
to save the user's data to a text file. This enables FitNus to retain the user's progress and data
even after terminating the application. The `Storage` class is also designed such that it fails
gracefully in the event that the text files are corrupted. This is achieved by discarding corrupted 
data and providing messages to the user.
- `FoodDatabase`: At the beginning of the project, I implemented the core functionalities of the `FoodDatabase` 
class, used to keep a record of all the various types of `Food` objects. Over the course of the project, 
I also continuously added enhancements, including features that allow users to search `Food` objects 
as well as find `Food` suggestions.
- `EntryDatabase`: I co-implemented the basic features of the `EntryDatabase` class, used to handle 
all functionalities relating to the Entry objects.
- `ViewSuggestionsCommand`: In V2.0, I implemented the `ViewSuggestionsCommand`, which enables users
to find suggestions from their `FoodDatabase` on what to eat based on their calorie goal and 
the food type they specify.
- `FindFoodsCommand`: I implemented the `FindFoodsCommand` in V2.0 which allows users to search the
`FoodDatabase` using a keyword. This allows users to easily find matching `Food` objects in the database.
- `FindEntriesCommand`: I implemented the `FindEntriesCommand` in V2.0 which allows users to search the
`EntryDatabase` using a keyword. This allows users to easily find matching `Entry` objects in the database.


### Contributions to the UG:
- Feature: Searching for foods with keyword.
- Feature: Searching for entries with keyword.
- Feature: Suggest food based on food type and calorie goal.
- I also contributed to the `Command summary` section of the UG.

### Contributions to the DG:
- Design and implementation: `Storage`
  - Added a UML sequence diagram demonstrating the process of saving the `FoodDatabase`
  data to text file.
  - Added a UML sequence diagram demonstrating the process of preloading the `FoodDatabase`
  data from text file.
  - Provided explanation on the implementation as well as design considerations taken into account.
- Design and implementation: `FoodDatabase`
  - Added a UML class diagram to highlight the relationships between the `FoodDatabase` 
  class and various components.
  - Added UML sequence diagrams for `preloadDatabase()` and `convertDatabaseToString()` methods.
  - Provided information on the various methods within the `FoodDatabase` class and their use cases.
- Design and implementation: `ViewSuggestionsCommand`
  - Added a UML sequence diagram that shows the execution of the 
  `ViewSuggestionsCommand`.
  - Provided detailed explanation on the sequence of the execution.


### Developer Guide Extract: Storage

The Storage class reads and writes data to and from the text file.

#### Storage format

**Every line in each text file represents one object / entry / item**

*   FoodDatabase:`FOODNAME | CALORIE_VALUE`  
    Example: `Nasi Lemak | 400`   `Ramen | 600`
*   EntryDatabase:`MEALTYPE | FOODNAME | CALORIE_VALUE | DATE`  
    Example: `Dinner | Ramen | 500 | 2021-10-20`   `Lunch | Fried rice | 600 | 2021-10-20`
*   User:`CALORIE_GOAL | GENDER`  
    Example: `1000 | 0`
*   User weight:`WEIGHT | DATE`  
    Example: `60.0 | 2021-07-20`   `59.0 | 2021-08-20`   `58.0 | 2021-09-20`   `45.0 | 2021-10-21`

#### Implementation

1.  **Saving to text file**

    `FoodDatabase`, `EntryDatabase`, and `User` classes each have a method to convert its data to String format. This String is then saved to the text file.  
    For instance, when saving the `FoodDatabase` data, `Storage` calls the `convertDatabaseToString()` method to obtain the String representation of all the data within the \`FoodDatabase\`. This String is then written to the text file.
2.  **Loading from text file**

    `Storage` makes use of the `BufferedReader` and `FileInputStream` provided by `java.io` to access the contents of the storage text files. This is then passed to the respective objects for preloading.  
    For instance, when preloading the `FoodDatabase` data, `Storage` accesses the storage text file and passes the file contents to the `preLoadDatabase()` method in ,`FoodDatabase` which populates the ArrayList in `FoodDatabase`.

#### Implementation considerations

1. The `Path` of each text file is hardcoded within the `Storage` class. This eliminates
   the need to pass the `Path` of the destination file each time. For example, to save the `FoodDatabase`
   contents, the method call is `saveFoodDatabase()` rather than `saveFoodDatabase(PATH)`.
2. All public methods are declared as `static` methods. This allows various methods within the
   `Storage` class to be called without having to instantiate a `Storage` object.



#### UML Sequence Diagram

The following sequence diagram describes the operation of the `saveFoodDatabase()` operation.  
![](./../diagrams-DG/Storage_sequence.png)

