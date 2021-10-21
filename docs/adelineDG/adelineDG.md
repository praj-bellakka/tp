# Developer Guide

## Acknowledgements

{list here sources of all reused/adapted ideas, code, documentation, and third-party libraries -- include links to the original source as well}

## Weight Tracker Design & Implementation

{Describe the design and implementation of the product. Use UML diagrams and short code snippets where applicable.}

###Architecture
**Main components of the architecture**

The weight tracker exists as an ArrayList called `WeightProgressEntries` within the User class. The ArrayList contains objects of class `WeightProgressEntry`.

**How the architecture components interact with each other**

The User object 

**Storage**

Weight progress entries are stored in a text file in the following format:

`WEIGHT | DATE(YYYY-MM-DD)`

Example: `100 | 2021-03-01`

The entries are parsed and the corresponding WeightProgressEntry objects are created and loaded into the ArrayList.

**User Component**

How the User component works in the context of the weight tracker:
1. When the user inputs the weight setting command, User is called upon to execute the function to update the user's weight and weight tracker.
2. In all cases, the weight attribute of the initialised User object will be updated to the new weight inputted by the user.
3. If no weight progress entries were present in the storage text file, the tracker does not attempt to calculate the difference between the updated weight and the previous weight.
4. If the latest weight progress entry was recorded on the same day, that entry is updated with the new weight (that is, no new entry is added to the weight tracker). Otherwise, a new weight progress entry is created in the ArrayList with the current date and new weight.



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
