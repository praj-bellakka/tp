# Chan Keng Jit (kengjit) - Project Portfolio Page

## Product Overview
FitNUS Tracker is a desktop app for tracking daily food intake to help users keep a healthy lifestyle. 
FitNUS is specially made for Computing Students living in University Town, with features and 
functionalities taylor made for them. It is based on a Command Line Interface (CLI) and has 
rich functionality in order to cater to the needs of potential users.


## Summary of Contributions

As one of FitNUS's full stack developer, I am responsible for implementing various features including:

- Adding Entries
- Editing Entries
- Listing Entries
- Deleting Entries
- Showing food intake summary
- Show help message

### Enhancements Implemented

#### Adding Entries

- What it does: Allows the user to add and track their food intake. They can do so either by choosing from our
extensive database of foods, or create their own custom food.
- Justification: This is one of FitNUS's core features. As a diet tracker, it is vital that it allows the user
to log in their food intake.
- Highlights: This enhancement was challenging as it was affected by many other changes made in FitNUS, causing it
to be constantly updated. Some examples include adding meal types and food types, which resulted in a massive overhaul
of this feature.

#### Editing Entries

- What it does: Allows the user to edit their past food entries. They can either change it to a food from the 
database, or to a custom food.
- Justification: This is one of FitNUS's core features. As a diet tracker, it is vital that it allows the user
  to edit their food entries in the event the user inputs a wrong entry.
- Highlights: This enhancement required a deep understanding of both the `EntryDatabase` and `FoodDatabase` classes,
The implementation was challenging as well since it has to not only edit the entries, but also add a new food if it 
does not exist.

#### Listing Entries

- What it does: Allows the user to list their past food entries. They can either list out all entries in the past day,
week, or since inception.
- Justification: This is one of FitNUS's core features. As a diet tracker, it is important that it allows the user
to see all the entries inputted.
- Highlights: This enhancement required a good understanding of how entries are being logged in. The challenge comes in 
differentiating how many entries to list out. This required the use of `EntryDatabase#getPastDaysEntryDatabase(int)`
which returns a subset of the current `EntryDatabase`. 

#### Deleting Entries

- What it does: Allows the user to delete a specified food entry.
- Justification: This is one of FitNUS's core features. As a diet tracker, it should allow the user to
delete any food entry in the event they make any mistakes.
- Highlights: This enhancement required a good understanding of how entries are being logged in. The challenge when 
implementing this feature is to ensure that the specified entry exists, and throws the proper exception otherwise.

#### Showing food intake summary

- What it does: Allows the user to view a summary of their past food intake. They can either view a summary of the
  past week or month. Not only does it provide useful information such as the user's average daily calorie intake, it also
provides information about the user's most/least eaten food and a visual diagram of the user's calorie intake.
- Justification: 
- Highlights: This enhancement required a good understanding of how entries are being logged in. The challenge comes in
  differentiating how many entries to include in the summary. This required the use of 
`EntryDatabase#getPastDaysEntryDatabase(int)` and `EntryDatabase#getPastMonthEntryDatabase()` which returns a subset of 
the current `EntryDatabase`.
- Credits: Due to the complexity of this feature, it was implemented with the help of @siyuancheng178, which assisted
with the visualisation of the summary diagrams.

#### Show help message

- What it does: Allows the user to list out all the available commands FitNUS accepts. 
- Justification: This is one of FitNUS's core features. As a diet tracker, it is vital that it tells the user what
the application can or cannot do.
- Highlights: This enhancement required a good understanding of how all the features in FitNUS operates. 


### Code Contributed

Click
[here](https://nus-cs2113-ay2122s1.github.io/tp-dashboard/?search=kengjit&sort=groupTitle&sortWithin=title&timeframe=commit&mergegroup=&groupSelect=groupByRepos&breakdown=true&checkedFileTypes=docs~functional-code~test-code~other&since=2021-09-25&tabOpen=true&tabType=authorship&tabAuthor=kengjit&tabRepo=AY2122S1-CS2113T-W12-1%2Ftp%5Bmaster%5D&authorshipIsMergeGroup=false&authorshipFileTypes=docs~functional-code~test-code&authorshipIsBinaryFileTypeChecked=false)
for the RepoSense to my code contribution.


### Contributions to the UG:
Added documentation for the whole of the following segments:
- `Entry Database`
  - Adding food entry
  - Editing existing food entry
  - Deleting food entry
  - Listing tracker entries
  - Searching for tracker entries with keyword
- `Viewing help` 
- `User Stories`

### Contributions to the DG:
Designed and implemented the following segments:
- `Entry Database`
- `Implementation`
  - Add Food Entry
  - Edit Food Entry
  - List Food Entry
  - Delete Food Entry
- `User Stories`
- `Instructions for manual testing`
  - Add Food Entry
  - Edit Food Entry
  - List Food Entry
  - Delete Food Entry
