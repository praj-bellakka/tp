# User Guide

## Introduction

FitNUS Tracker is a desktop app for tracking daily food intake to keep a healthy lifestyle. 
It is based on a Command Line Interface (CLI) and has rich functionality in order to cater to the needs of potential users.

Here is the list of sections we will be covering in this User Guide.

- Quick Start

- Features
  - Food Tracker
    - Adding food tracker entry: `add`
    - Editing existing food tracker entry: `edit`
    - Deleting food tracker entry: `remove /entry`
    - Listing tracker entries for the day: `list /entry`
    - Searching for tracker entries with keyword: `find /entry`
  - Food Database
    - Deleting food from food database: `remove /food`
    - Searching for foods with keyword: `find /food`
    - Listing foods in food database: `list /food`
  - Weight Tracker
    - Recording weight: `weight /set`
    - Listing weight records: `list /weight`
  - Personalisation
    - Setting gender: `gender /set`
    - Setting height: `height /set`
    - Setting age: `age /set`
    - Setting calorie goal: `calorie /set`
    - Generate and set calorie goal: `calorie /generate`
  - Other
    - Viewing statistics: `summary`
    - Viewing help: `help`
    - Getting food recommendations: `suggest`
    - Exiting FitNUS: `exit`
- Command Summary

---
## Quick Start

1. Ensure that you have Java 11 installed in your computer. 
2. Download the latest version of `fitNus.jar` from [here](https://github.com/AY2122S1-CS2113T-W12-1/tp/releases/tag/V2.0).
3. Copy the file to the folder you want to use as the home folder for your FitNUS Tracker.
4. Type the following command in your terminal to run this program: 
```java -jar fitnus.jar```
5. If you have successfully run the programme, you should see the following message as follows:

## Features 
> **⚠️ Notes about command format**
> 
> - Words in **UPPER_CASE** are the parameters to be input by the user!

>  e.g. in `add /food NAME`, NAME is a parameter which can be substituted as `add /food burgers`.
> - Items in **[Square brackets]** are optional!

>  e.g. `add [/MEALTYPE] FOOD_NAME` can be used as `add /lunch sandwiches` or `add sandwiches`.

### Food Tracker

#### Adding food tracker entry: `add`
Adds an entry to the food tracker and prompts the user to fill in any additional information needed if an exact match to the user inputted food name was not found in the food database. 

Format: `add [/MEALTYPE] FOOD_NAME`

* The `MEALTYPE` can be of the following 4 types:
  * `bfast` - to denote breakfast
  * `lunch` - to denote lunch
  * `dinner` - to denote dinner
  * `snack` - to denote snacks
* If several matches (TODO THIS PART)

> **⚠️ Notes about omitting `MEALTYPE`**
>
> The `MEALTYPE` will be automatically added based on the current time if not explicitly specified based on the following criteria:
>- Breakfast: 6am to 10am
>- Lunch: 11am to 2pm
>- Dinner: 6pm to 9pm
>- Snack: Remaining time

Examples of usage:

`add /bfast chocolate rolls`

- Adds a tracker entry of type Breakfast and 

`add mushroom soup`

#### Editing existing food tracker entry: `edit`
Edits an existing food tracker entry's food information. FitNUS will search for FOOD_NAME in the food database and update the chosen entry's food description and calorie information accordingly. If an exact match to FOOD_NAME was not found in the food database, (???)

Format: `edit INDEX_OF_FOOD FOOD_NAME`

Example of usage:

#### Deleting food tracker entry: `remove`
Deletes a food entry from the food tracker.

Format: `remove /entry INDEX_OF_FOOD`

Example of usage:

`remove /entry 2`

#### Listing tracker entries for the day: `list`
Lists out all foods entered for the day.

Format: `list /entry`

Example of usage:

`list /entry`

#### Searching for tracker entries with keyword: `find`
Finds all matching entries in the EntryDatabase based on the keyword you provided.

Format: `find /entry KEYWORD`

Example of usage:

`find /food rice`

###Food Database

#### Deleting food from food database: `remove`
Deletes food from the food database.

Format: `remove /food INDEX_OF_FOOD`

Example of usage:

`remove /food 12`

#### Searching for foods with keyword: `find`
Finds all matching food in the FoodDatabase based on the keyword you provided.

Format: `find /food KEYWORD`

Example of usage:

`find /entry rice`

#### Listing foods in food database: `list`
Lists out all foods in the database and their respective calories.

Format: `list /food`

Example of usage:

`list /food`

### Weight Tracker

#### Recording weight: `weight /set`
Lists out all past records of weight entered by the user.

Format: `list /weight`

Example of usage:

`list /weight`

#### Listing weight records: `list`
Lists out all past records of weight entered by the user.

Format: `list /weight`

Example of usage:

`list /weight`

#### Viewing Help: `help`
Lists out available commands and additional information regarding each command.

Format: `help`

#### View statistics: `summary`
FitNUS supports two kinds of diet report, which are weekly report and monthly report.

##### Weekly report
Weekly report gives you an overview of your diet over the past 7 days, which includes daily calorie intake graph, average calorie intake,
most and least frequently eaten foods.

Format: `summary /week`
##### Monthly report
Monthly report gives you an overview of your diet over this month, which includes average calorie intake, most and least frequently eaten foods.

Format: `summary /month`



### Suggest food based on food type and calorie goal: `suggest`
Filters food items in the database based on food type (meal, snack, beverage, others) 
that if consumed, will not exceed the daily calorie goal set by the user.


Format: `suggest /FOODTYPE`

> **_OPTIONAL:_** You can sort the suggestions by calorie value by simply appending "/sort"
> to the command above e.g. suggest /FOODTYPE /sort

Example of usage:

`suggest /meal`
`suggest /snack /sort`


## FAQ


## Command summary

Action | Command Format | Example
--- | --- | --- | 
Add | add /MEALTYPE FOOD_NAME | `add /bfast chocolate rolls`
Edit | edit INDEX_OF_FOOD FOOD_NAME | `edit 1`
Remove entry | remove /entry INDEX_OF_FOOD | `remove /entry 2`
Remove food | remove /food INDEX_OF_FOOD | `remove /food 12`
Find food | find /food KEYWORD | `find /food rice`
Find entry | find /entry KEYWORD | `find /entry rice`
List food | list /food | `list /food`
List daily entry | list /entry | `list /entry`
List weight record | list /weight | `list /weight`
View weekly statistics | summary /week | `summary /week`
View monthly statistics | summary /month | `summary /month`
Suggest food | suggest /FOODTYPE <br /> suggest /FOODTYPE /sort | `suggest /meal` <br /> `suggest /snack /sort`


