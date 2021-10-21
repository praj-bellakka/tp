
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

{Describe the target user profile}

### Value proposition

{Describe the value proposition: what problem does it solve?}

## User Stories

|Version| As a ... | I want to ... | So that I can ...|
|--------|----------|---------------|------------------|
|v1.0|new user|see usage instructions|refer to them when I forget how to use the application|
|v2.0|user|find a to-do item by name|locate a to-do without having to go through the entire list|

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


## SummaryCommand
The SummaryCommand class provides an overview of user's diet over the past week/month.

### command format
`summary /week` or `summary /month`

### implementation
`SummaryCommand` class provides a method to generate the most and less frequently foods ate by users.
`ViewMonthlyCalorieTrend` and `ViewDailyCalorieTrend` have static methods to generate monthly/weekly calorie intake graphs.

### UML Sequence Diagram
The following sequence diagram describes the operation of the `generateSummary()` method.
![UML Sequence Diagram for Summary](diagrams/Summary%20Command%20Diagram.png)

## Storage
The Storage class reads and writes data to and from the text file.

### Storage format

- `FoodDatabase`: FOODNAME | CALORIE_VALUE

Example:
```
Nasi Lemak | 400
```

- `EntryDatabase`: MEALTYPE | FOODNAME | CALORIE_VALUE | DATE

Example:
```
Dinner | Ramen | 500 | 2021-10-20
```

- `User`: CALORIE_GOAL | GENDER

Example:
```
1000 | 0
```

### Implementation
`FoodDatabase`, `EntryDatabase`, and `User` classes each have a method to convert
its data to String format. This String is then saved to the text file.

For instance, when saving the `FoodDatabase` data, `Storage` calls the `convertDatabaseToString()`
method to obtain the String representation of all the data within the `FoodDatabase`. This String is
then written to the text file.

### UML Sequence Diagram
The following sequence diagram describes the operation of the `saveFoodDatabase()` operation.

![UML Sequence Diagram for Storage - saving data](diagrams/StorageSequenceUML.PNG)