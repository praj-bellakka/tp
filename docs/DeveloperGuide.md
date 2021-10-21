
# Developer Guide

## Acknowledgements

{list here sources of all reused/adapted ideas, code, documentation, and third-party libraries -- include links to the original source as well}

## Design & implementation

{Describe the design and implementation of the product. Use UML diagrams and short code snippets where applicable.}


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

### Add Food Entry Feature
The add food entry mechanism is facilitated by <code>AddFoodEntryCommand</code>. 
It extends <code>Command</code> and stores the data internally into <code>EntryDatabase</code> 
and <code>FoodDatabase</code>. 
Additionally, it implements the following operations:
- <code>EntryDatabase#addEntry(Entry)</code> -- Adds a new entry into the entry database
- <code>FoodDatabase#addFood</code> -- Adds a new food into the food database

![AddFoodEntrySeqDiagram](AddFoodEntry.png "AddFoodEntry Sequence Diagram")