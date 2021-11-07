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
- `Storage`: 
  - I implemented the file storage functionality, which allows the FitNus application to save and read data 
  to and from text files. This enables FitNus to retain the user's progress and various data
  even after terminating the application. 
  - The `Storage` class is also designed such that it fails
  gracefully in the event that the text files are corrupted. This is achieved by discarding corrupted 
  data and providing messages to the user.
  - Implementing the storage functionality also involved adding methods to various classes to
  preload data and also convert data to String for storage.
- `FoodDatabase`: 
  - At the beginning of the project, I implemented the core functionalities of the `FoodDatabase` 
  class, used to keep a record of all the various types of `Food` objects. 
  - Over the course of the project, 
  I also continuously added enhancements, including features that allow users to search `Food` objects 
  as well as find `Food` suggestions.
- `EntryDatabase`: 
  - I co-implemented the basic features of the `EntryDatabase` class, used to handle 
  functionalities relating to the `Entry` objects.
- `ViewSuggestionsCommand`: 
  - In v2.0, I implemented the `ViewSuggestionsCommand`, which enables users
  to find suggestions from their `FoodDatabase` on what to eat based on their calorie goal and 
  the food type they specify.
- `FindFoodsCommand`: 
  - I implemented the `FindFoodsCommand` in v2.0 which allows users to search the
  `FoodDatabase` using a keyword. This allows users to easily find matching `Food` objects in the database.
- `FindEntriesCommand`: 
  - I implemented the `FindEntriesCommand` in v2.0 which allows users to search the
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

### Contributions to team-based tasks:

- Setting up milestone `v2.0`.
- Protect the team repository's master branch using GitHub's Protected Branches feature.
- Keep track of deadlines and milestones.
- Refactored code to improve consistency and to improve the use of OOP principles.

### Review/mentoring contributions

- [Here the PRs that I reviewed or commented on.](https://github.com/AY2122S1-CS2113T-W12-1/tp/pulls?q=is%3Apr+commenter%3Abrendanlsz+)
- Assisted my teammates with `Storage` and `FoodDatabase` related technical issues.


