# Brendan Lau Siew Zhi (brendanlsz) - Project Portfolio Page

## Product Overview
FitNUS Tracker is a desktop app for tracking daily food intake to help users keep a healthy lifestyle. 
FitNUS is specially made for Computing Students living in University Town, with features and 
functionalities taylor made for them. It is based on a Command Line Interface (CLI) and has 
rich functionality in order to cater to the needs of potential users.


## Summary of Contributions

### Code Contributed

[This is the RepoSense report of the code contributed.](https://nus-cs2113-ay2122s1.github.io/tp-dashboard/?search=&sort=groupTitle&sortWithin=title&timeframe=commit&mergegroup=&groupSelect=groupByRepos&breakdown=true&checkedFileTypes=docs~functional-code~test-code~other&since=2021-09-25&tabOpen=true&tabType=authorship&tabAuthor=brendanlsz&tabRepo=AY2122S1-CS2113T-W12-1%2Ftp%5Bmaster%5D&authorshipIsMergeGroup=false&authorshipFileTypes=docs~functional-code~test-code~other&authorshipIsBinaryFileTypeChecked=false)


### Enhancements Implemented

Implemented the core functionalities of the following:
- **New Feature:** Implemented and maintained the `Storage` component.
  - What it does: Saves and reads data to and from text files. 
  - Justification: This feature enables FitNus to retain the user's progress and various data
  even after terminating the application. 
  - Highlights: The `Storage` component is designed such that it fails gracefully in the 
  event that the text files are corrupted. This is achieved by discarding corrupted 
  data and providing messages to the user. Implementing the storage functionality also 
  involved adding methods to various classes to preload data and also convert data to String for storage.
- **New Feature:** Implemented the core functionalities of `FoodDatabase`: 
  - What it does: Keeps a record of all the various types of `Food` objects and handles various operations
  on `Food` objects. 
  - Justification: This feature eliminates the need for users to enter details repetitively for the same `Food` and also
  allows users to find suggestions on what `Food` to eat. 
- **New Feature:** Added the ability to view `Food` suggestions: 
  - What it does: Enables users to find suggestions from their `FoodDatabase` on what to eat 
  based on their calorie goal and the food type they specify.
  - Justification: This feature improves the product significantly as it allows users to easily find `Food` suggestions
  that meet their calorie goal from the `FoodDatabase`.
- **New Feature:** Added the ability to search for foods with keyword: 
  - What it does: Allows users to search the `FoodDatabase` using a keyword. 
  - Justification: This allows users to easily find matching `Food` objects in the database.
- **New Feature:** Added the ability to search for entries with keyword: 
  - What it does: Allows users to search the `EntryDatabase` using a keyword. 
  - Justification: This allows users to easily find matching `Entry` objects in the database.


### Contributions to the UG:
Added documentation for the following features:
- Searching for foods with keyword.
- Searching for entries with keyword.
- Suggest food based on food type and calorie goal. 

Contributed to the `Command summary` section of the UG.


### Contributions to the DG:
- Added design and implementation details of the `Storage` component.
- Added design and implementation details of the `Food Database` component.
- Added implementation details of the `View Food Suggestions` feature.

### Contributions to team-based tasks:

- Setting up milestone `v2.0`.
- Protect the team repository's master branch using GitHub's Protected Branches feature.
- Keep track of deadlines and milestones.
- Refactored code to improve consistency and to improve the use of OOP principles.

### Review/mentoring contributions

- [Here the PRs that I reviewed or commented on.](https://github.com/AY2122S1-CS2113T-W12-1/tp/pulls?q=is%3Apr+commenter%3Abrendanlsz+)
- Assisted my teammates with `Storage` and `FoodDatabase` related technical issues.


