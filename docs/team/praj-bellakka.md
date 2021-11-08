# Bellakka Krishnamurthy Prajwal (praj-bellakka) - Project Portfolio Page

## Product Overview
FitNUS Tracker is a desktop app for tracking daily food intake and
to help users achieve a healthy lifestyle.
FitNUS is specially made for Computing Students living in University Town.

## Summary of Contributions
Backend Software Engineer: Responsible for implementing various backend features including
`Parser`, `MealPlan`, and `MealPlanDatabase`.

### Code Contributed

[This is the RepoSense report of the code contributed.](https://nus-cs2113-ay2122s1.github.io/tp-dashboard/?search=&sort=groupTitle&sortWithin=title&timeframe=commit&mergegroup=&groupSelect=groupByRepos&breakdown=true&checkedFileTypes=docs~functional-code~test-code~other&since=2021-09-25&tabOpen=true&tabType=authorship&tabAuthor=praj-bellakka&tabRepo=AY2122S1-CS2113T-W12-1%2Ftp%5Bmaster%5D&authorshipIsMergeGroup=false&authorshipFileTypes=docs~functional-code~test-code&authorshipIsBinaryFileTypeChecked=false)


#### Enhancements Implemented: Implemented the core functionalities of the following features:
- **Feature**: Parser
  - What it does: Parses user inputs into its respective commands and calls the respective functions by returning the respective `Command` function.
  - Justification: This feature is crucial in detecting what the user inputs and calling the respective functions.
  - Highlights: This feature was challenging to implement as it required careful considerations in error handling when the user doesn't enter the stated format. Certain commands also required the parser to reprompt the user for additional inputs such as calories and meal type.

- **Feature**: Meal plans
  - What it does: I implemented the `MealPlan` class which allows user to group multiple food items together
  - Justification: The feature improves the usability of FitNus as users can group multiple food items together and add them all at once.
  - Highlights: This feature was challenging to implement has it involved the integration of different classes such as `Ui`, `MealPlanDatabase`, `Parser` and `Storage`. Custom methods in the storage also needed to be implemented in order to save the meal plans to local storage. 
  - Credits: The storage functionality of the Meal plan was a modified method of the one created originally by of @brendanlsz.
### Contributions to the UG:
- Feature: Creating new meal plan, Adding existing meal plan in database, Listing meal plans in database, Adding new food to database.
- I also contributed to the `Command summary` section of the UG.

### Contributions to the DG:
- Design and implementation: `Parser`
  - Added a UML sequence diagram demonstrating the process of returning an `AddFoodEntryCommand` when the user enters a food. 
  - Added a UML sequence diagram demonstrating the process of returning a `CreateMealPlanCommand` when the user creates a meal plan.
  - Added a UML sequence diagram demonstrating the process of `promptUserCalories` used to prompt user to enter more details about the food added. 


- Design and implementation: `MealPlanDatabase`
    - Added a UML class diagram to highlight the relationships between the `MealPlanDatabase`class and various components.
    - Added a UML class diagram to highlight the `MealPlanDatabase` methods and attributes.
    - Added UML sequence diagrams for `preloadDatabase()` and `convertDatabaseToString()` methods.
    - Added UML sequence diagrams for `listMealPlan()` method.
    - Provided information on the various methods within the `MealPlanDatabase` class and their use cases.

### Enhancements to existing features:

- Refactored a large amount of code to make the code more robust for Parser and Ui. (PR [#353](https://github.com/AY2122S1-CS2113T-W12-1/tp/pull/353), [#358](https://github.com/AY2122S1-CS2113T-W12-1/tp/pull/358)) 
