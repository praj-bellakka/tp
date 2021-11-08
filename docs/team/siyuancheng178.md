# Cheng Siyuan - Project Portfolio Page

## Product Overview - FitNus
FitNUS Tracker is a command line based desktop app for tracking daily food intake to keep a healthy lifestyle.
FitNUS is specially made for Computing Students living in University Town, with features and
functionalities taylor made for them.

## Summary of Contributions

During the development this software, I am responsible for implement the following features:
- Adding custom food entry
- setting user gender 
- Showing weekly food intake summary
- Showing monthly food intake summary
- exit the program

Given below are my contributions to the project:

- Code contribution: [This is the RepoSense report of the code contributed.](https://nus-cs2113-ay2122s1.github.io/tp-dashboard/?search=&sort=groupTitle&sortWithin=title&timeframe=commit&mergegroup=&groupSelect=groupByRepos&breakdown=true&checkedFileTypes=docs~functional-code~test-code~other&since=2021-09-25&tabOpen=true&tabType=authorship&tabAuthor=siyuancheng178&tabRepo=AY2122S1-CS2113T-W12-1%2Ftp%5Bmaster%5D&authorshipIsMergeGroup=false&authorshipFileTypes=docs~functional-code~test-code&authorshipIsBinaryFileTypeChecked=false)


## Enhancement implementation

### Adding custom food entry

- What it does: Allows the user to add and track their food intake which is not provided inside our default database
- Justification: This is one of FitNUS's core features. It is important to keep track of user daily food intake espeacially
if the user is having his/her custom food.

### Setting user gender

- What it does: Allows the user to change the gender if he/she sets it wrongly before.
- Justification: This is one of FitNUS's core features. It allows the user to modify in the event the user gives a wrong input previously.

### Remove food from daily intake

- What it does: Allows the user to delete a specified food entry.
- Justification: This is one of FitNUS's core features. As a diet tracker, it should allow the user to
  delete any food entry in the event they make any mistakes.
- Highlights: This enhancement required a good understanding of how entries are being logged in. The challenge when
  implementing this feature is to ensure that the specified entry exists, and throws the proper exception otherwise.


### View weekly entry summary

- What it does: allow users to view a concise summary of what he/she has eaten over the past seven days, including 
components of calorie trend graph, average daily calorie and most/least eaten foods.
- Highlights: This enhancement required a good understanding of how entries are being logged in. The challenge comes in
  differentiating how many entries to include in the summary. This required the use of
  `EntryDatabase#getPastDaysEntryDatabase(int)` which returns the past seven days' entries. It also needs to take into account that new users may have very few records, so FitNus 
  need to adjust the summary accordingly.



### view monthly entry summary

- What it does: allow users to view a concise summary of what he/she has eaten in the current month, including components 
of average daily calorie and most/least eaten foods.
- Highlights: This enhancement required a good understanding of how entries are being logged in. The challenge comes in
differentiating how many entries to include in the summary. This required the use of
`EntryDatabase#getPastMonthEntryDatabase()` which returns past month entries. It also needs to take into account 
that new users may have very few records, so FitNus need to adjust the summary accordingly.


### Contributions to the UG:
- Add documentation for to view weekly summary
- Add documentation for to view monthly summary


### Contributions to the DG:
- Add the overall architecture diagram and explain the functionality for each component.
- Add the class diagram for `tracker` package.
- Add sequence diagram for `ViewWeekSummaryCommand` and `ViewMonthSummaryCommand`
- Add Instructions for manual testing for delete food, find food and list food.