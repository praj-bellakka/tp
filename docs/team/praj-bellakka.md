# Bellakka Krishnamurthy Prajwal (praj-bellakka) - Project Portfolio Page

## Product Overview
FitNUS Tracker is a desktop app for tracking daily food intake to help users keep a healthy lifestyle.
FitNUS is specially made for Computing Students living in University Town, with features and
functionalities taylor made for them. It is based on a Command Line Interface (CLI) and has
rich functionality in order to cater to the needs of potential users.


## Summary of Contributions
Backend Software Engineer: Responsible for implementing various backend features including
`Parser`, `MealPlan`, and `MealPlanDatabase`.

### Code Contributed

[This is the RepoSense report of the code contributed.](https://nus-cs2113-ay2122s1.github.io/tp-dashboard/?search=&sort=groupTitle&sortWithin=title&timeframe=commit&mergegroup=&groupSelect=groupByRepos&breakdown=true&checkedFileTypes=docs~functional-code~test-code~other&since=2021-09-25&tabOpen=true&tabType=authorship&tabAuthor=praj-bellakka&tabRepo=AY2122S1-CS2113T-W12-1%2Ftp%5Bmaster%5D&authorshipIsMergeGroup=false&authorshipFileTypes=docs~functional-code~test-code&authorshipIsBinaryFileTypeChecked=false)


### Enhancements Implemented

Implemented the core functionalities of the following:
- `Parser`: I implemented the main parser functions that take in user input and return the respective `Command` function. 
They include adding a new `Food` item and adding/creating a new `MealPlan`.

- `MealPlan`: I implemented the meal plan which allows user to group multiple food items together.

- `MealPlanDatabase`: I implemented the  features of the `MealPlanDatabase` class, used to handle
  all functionalities relating to the MealPlan objects, including saving and reading MealPlan items from local storage.

- `AddMealPlanEntryCommand`: I implemented the `AddMealPlanEntryCommand`, which enables users
  to find enter meal plans through the CLI.

Implemented the non-core functionalities of the following:
- `Ui`: I implemented a single method in the Ui class, i.e. `readIndexesInput()`, which parses user input when adding meal plans. 
The method has built in checks to only allow integer values within the range of the `MealPlanDatabase` to be added.
- `ListMealPlanDatabaseCommand`: I implemented this Command to allow users to view all currently saved meal plans in the database.

### Contributions to the UG:
- Feature: Creating new meal plan.
- Feature: Adding existing meal plan in database.
- Feature: Listing meal plans in database.
- Feature: Adding new food to database.
- I also contributed to the `Command summary` section of the UG.

### Contributions to the DG:
- Design and implementation: `Parser`
  - Added a UML sequence diagram demonstrating the process of returning an `AddFoodEntryCommand` when the user enters a food. 
  - Added a UML sequence diagram demonstrating the process of returning a `CreateMealPlanCommand` when the user creates a meal plan.
  - Added a UML sequence diagram demonstrating the process of `promptUserCalories` used to prompt user to enter more details about the food added. 


- Design and implementation: `MealPlanDatabase`
    - Added a UML class diagram to highlight the relationships between the `MealPlanDatabase`
      class and various components.
    - Added a UML class diagram to highlight the `MealPlanDatabase` methods and attributes.
    - Added UML sequence diagrams for `preloadDatabase()` and `convertDatabaseToString()` methods.
    - Added UML sequence diagrams for `listMealPlan()` method.
    - Provided information on the various methods within the `MealPlanDatabase` class and their use cases.


