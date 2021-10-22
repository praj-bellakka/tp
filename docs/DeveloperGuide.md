
# Developer Guide

## Acknowledgements

{list here sources of all reused/adapted ideas, code, documentation, and third-party libraries -- include links to the original source as well}

## Design & implementation

## Architecture

The entry point of the app is the `FitNUS` class, where the application is run and all other components are initialised and used. The primary components of the app are listed below:
- `Command`: The abstract class that all other command classes inherit from
- `EntryDatabase`: For handling all functionality regarding food tracker entries.
- `FoodDatabase`: For handling all functionality regarding food database entries.
- `Parser`: For parsing user input.
- `Storage`: For handling backend storage.
- `Ui`: For displaying information to the user.
- `User`: For handling all functionality regarding personalisation of user experience.

## Product scope
### Target user profile

NUS Computer Engineering students reside in UTown 

### Value proposition

{Describe the value proposition: what problem does it solve?}

## Quick Start
1. Ensure you have Java 11 or above installed in your Computer.
2. Download the latest fitnus.jar from here (no link for now).
3. Copy the file to the folder you want to use as the home folder for your WellNUS Tracker
4. Type the following command in your terminal to run this program: java -jar fitnus.jar (You should change directory to where the wellnus.jar file is located or provide the absolute path of wellnus.jar).
<br/>Some example commands you can try:
- `add Chicken Rice /cal 607`: Adds an entry of Chicken Rice with 607 calories to your food tracker and food database.
- `list food`: Lists all foods in database
- `list intake /DAY`: Lists all entries in the food tracker for the day.
- `exit`: Exits the app.
<br/>
Refer to the User Guide (no link for now) for details of each command.


## User Stories

|Version| As a ... | I want to ... | So that I can ...|
|--------|----------|---------------|------------------|
|v1.0|new user|see usage instructions|refer to them when I forget how to use the application|
|v2.0|user|find a to-do item by name|locate a to-do without having to go through the entire list|

## Architecture
{An Architecture Diagram here}
The Architecture Diagram given above explains the high-level design of the App. 
Given below is a quick overview of main components and how they interact with each other.

### Main components of the architecture

{TODO here}


## Non-Functional Requirements

{Give non-functional requirements}

## Glossary

* *glossary item* - Definition

## Instructions for manual testing

{Give instructions on how to do a manual product testing e.g., how to load sample data to be used for testing}

## Add Food Entry Feature
The add food entry mechanism is facilitated by <code>AddFoodEntryCommand</code>.
It extends <code>Command</code> and stores the data internally into <code>EntryDatabase</code>
and <code>FoodDatabase</code>.
Additionally, it implements the following operations:
- <code>EntryDatabase#addEntry(Entry)</code> -- Adds a new entry into the entry database
- <code>FoodDatabase#addFood</code> -- Adds a new food into the food database

![AddFoodEntrySeqDiagram](AddFoodEntry.png "AddFoodEntry Sequence Diagram")

## Weight Tracker Design & Implementation

###Architecture
**Main components of the architecture**

The weight tracker exists as an ArrayList called `WeightProgressEntries` within the User class. The ArrayList contains objects of class `WeightProgressEntry`.

**SetWeightCommand Component**

The entry point for setting or updating weight. The `execute` method in this object calls `updateWeightAndWeightTracker` method in the User object initialised in the main file in order to update the user's weight and weight progress.

**Storage Component**

Weight progress entries are stored in a text file in the following format:

`WEIGHT | DATE(YYYY-MM-DD)`

Example: `100 | 2021-03-01`

The weight progress storage file is updated every time the user sets or updates their weight for the day, as all storage files are updated at every iteration of the main loop using the `saveFitNus` method.

On startup, the storage file is  parsed and the corresponding WeightProgressEntry objects are created and loaded into the ArrayList.

**User Component**

How the User component works in the context of the weight tracker:
1. When the user inputs the weight setting command, User is called upon to execute the function to update the user's weight and weight tracker.
2. In all cases, the weight attribute of the initialised User object will be updated to the new weight inputted by the user.
3. If no weight progress entries were present in the storage text file, the tracker does not attempt to calculate the difference between the updated weight and the previous weight.
4. If the latest weight progress entry was recorded on the same day, that entry is updated with the new weight (that is, no new entry is added to the weight tracker). Otherwise, a new weight progress entry is created in the ArrayList with the current date and new weight.


## View Diet Summary
The Summary class provides an overview of user's diet over the past week/month.

### command format
`summary /week` or `summary /month`

### implementation
`Summary` class provides two methods `generateWeekSummaryReport()` and `generateMonthSummaryReport()` to give the user weekly/monthly report of their diets.
- generateWeekSummaryReport() shows weekly calorie intake trend graph, average daily calorie intake, and the most/least frequently eaten food over past 7 days.
- generateMonthSummaryReport() shows average daily calorie intake, and the most/least frequently eaten food over this month.

### UML Sequence Diagram
The following sequence diagram describes the operation of the `generateWeekSummary()` method.
![UML Sequence Diagram for generateWeekSummaryReport()](diagrams/weekly%20report.png) <br/>

The following sequence diagram describes the operation of the `generateMonthSummary()` method.
![UML Sequence Diagram for generateMonthSummaryReport()](diagrams/monthly%20report.png) <br/>
## Storage
The Storage class reads and writes data to and from the text file.

### Storage format
> **_NOTE:_** Every line in each text file represents one object / entry / item

- `FoodDatabase`: FOODNAME | CALORIE_VALUE

Example:
```
Nasi Lemak | 400
Ramen | 600
```

- `EntryDatabase`: MEALTYPE | FOODNAME | CALORIE_VALUE | DATE

Example:
```
Dinner | Ramen | 500 | 2021-10-20
Lunch | Fried rice | 600 | 2021-10-20
```

- `User`: CALORIE_GOAL | GENDER

Example:
```
1000 | 0
```

- `User weight`: WEIGHT | DATE

Example:
```
60.0 | 2021-07-20
59.0 | 2021-08-20
58.0 | 2021-09-20
45.0 | 2021-10-21
```


### Implementation
#### 1. Saving to file
`FoodDatabase`, `EntryDatabase`, and `User` classes each have a method to convert
its data to String format. This String is then saved to the text file.

For instance, when saving the `FoodDatabase` data, `Storage` calls the `convertDatabaseToString()`
method to obtain the String representation of all the data within the `FoodDatabase`. This String is
then written to the text file.

#### 2. Loading from file 
`Storage` makes use of the `BufferedReader` and `FileInputStream` provided  by `java.io` to access 
the contents of the storage text files. This is then passed to the respective objects for preloading.

For instance, when preloading the `FoodDatabase` data, `Storage` accesses the storage text file
and passes the file contents to the `preLoadDatabase()` method in `FoodDatabase` which populates
the ArrayList in `FoodDatabase`.

### UML Sequence Diagram
The following sequence diagram describes the operation of the `saveFoodDatabase()` operation.

![UML Sequence Diagram for Storage - saving data](diagrams/StorageSequenceUML.PNG)

---
## Parser Component

**API: `Parser.java`**

The parser component makes use of the user input String from the `fitNus` class to detect the type of `Command` object called.
It then returns a `Command` object that represents the type of command called through the input.

The `Parser` component:

- determines the type of `Command` object and returns it.
- handles input exceptions and returns relevant `FitNusException` command.

### Implementation
**1. Identifying type of method called**

The `Parser` is invoked through the `parseCommandType()` method. The input is first split up by identifying a space character.
If no space character is detected, and the `help` or `exit` method was not called, a `FitNusException` is thrown. The first string element is 
then compared with default list of commands to determine the type of method called using if-else statements.