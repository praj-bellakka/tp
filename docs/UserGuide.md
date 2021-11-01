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
    - Listing tracker entries: `list /entry`
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
    - View personal data: `list /user`
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
4. Type the following command in your terminal to run this program:`java -jar FitNus.jar`
> **⚠️ Notes to Windows user**
> 
> We strongly recommend you using cmd to execute. If you are using git-bash, before running the program,
> please type `chcp.com 65001`
5. The application will prompt first-time users (i.e. users with incomplete or missing user data) to set up their profile. If you have successfully run the programme, you should see the following message as follows:
```
Welcome to FitNUS Tracker!
Successfully preloaded 0 foods
Successfully preloaded 0 entries
Successfully preloaded weight data
Please enter your gender (m/f):
```


## Features
> **⚠️ Notes about command format**
> 
> - Words in **UPPER_CASE** are the parameters to be input by the user!

>  e.g. in `add /food NAME`, NAME is a parameter which can be substituted as `add /food burgers`.
> - Items in **[Square brackets]** are optional!

>  e.g. `add [/MEALTYPE] FOOD_NAME` can be used as `add /lunch sandwiches` or `add sandwiches`.

### Food Tracker

#### Adding food tracker entry: `add`
Adds a food entry to the food tracker and prompts the user to fill in any additional information needed if an exact match to the user inputted food name was not found in the food database. 

Format: `add [/MEALTYPE] FOOD_NAME`

* The `MEALTYPE` can be of the following 4 types:
  * `bfast` - to denote breakfast
  * `lunch` - to denote lunch
  * `dinner` - to denote dinner
  * `snack` - to denote snacks

> **⚠️ Notes about omitting `MEALTYPE`**
>
> The `MEALTYPE` will be automatically added based on the current time if not explicitly specified based on the following criteria:
>- Breakfast: 6am to 10am
>- Lunch: 11am to 2pm
>- Dinner: 6pm to 9pm
>- Snack: Remaining time

* If there are any pre-set food that matches `FOOD_NAME`:
  * You can do one of the following:
    * Select which food you would like to add
    * Create your own custom food



Examples of usage: `add /bfast chocolate`

Sample output:
```
Here are the matching foods in your database:
 1.chocolate bar (125 Kcal) Type: SNACK
 2.chocolate cake (300 Kcal) Type: SNACK
 3.chocolate croissant (120 Kcal) Type: MEAL
 4.chocolate rolls (110 Kcal) Type: SNACK
Select the food you want by entering the number below. If the food doesn't exist, enter 0 to create a new custom food!
User Input: 4
You have successfully added chocolate rolls (110 Kcal) Type: SNACK
```


<p>&nbsp;</p>

#### Editing existing food tracker entry: `edit`
Edits an existing food entry's food information. FitNUS will search for FOOD_NAME in the food database 
and update the specified entry's food details accordingly.

Format: `edit INDEX_OF_ENTRY FOOD_NAME`
> **⚠️ Notes about `INDEX_OF_ENTRY`**
>
> INDEX_OF_ENTRY refers to the index of the entry shown when command `list /entry` is used.


Example of usage:
`edit 2 fish n chips`

- Edits whatever food is in index 2 to "fish n chips"

<p>&nbsp;</p>

#### Deleting food tracker entry: `remove`
Deletes a food entry from the food tracker.

Format: `remove /entry INDEX_OF_FOOD`

Example of usage:

`remove /entry 2`

<p>&nbsp;</p>

#### Listing tracker entries: `list`
Lists out all foods entered for a given timeframe.

Format: `list /entry [/TIMEFRAME]`
* The `TIMEFRAME` can be of the following 2 types:
  * `day` - to show entries in the current day
  * `week` - to show entries in the past week

> **⚠️ Notes about omitting `TIMEFRAME`**
>
> Command will list out **ALL** available food entries.

Example of usage: 

`list /entry /week`
- lists out all food entries logged in for the past week.

<p>&nbsp;</p>

#### Searching for tracker entries with keyword: `find`
Finds all matching entries in the EntryDatabase based on the keyword you provided.

Format: `find /entry KEYWORD`

Example of usage:

`find /entry rice`

- Prints out all food entries that contains "rice".

<p>&nbsp;</p>

### Food Database

#### Deleting food from food database: `remove`
Deletes food from the food database.

Format: `remove /food INDEX_OF_FOOD`

Example of usage:

`remove /food 12`

- Removes the 12th food in the list of preset foods.

<p>&nbsp;</p>

#### Searching for foods with keyword: `find`
Finds all matching food in the FoodDatabase based on the keyword you provided.

Format: `find /food KEYWORD`

Example of usage:

`find /food rice`

- Prints out all preset foods that contains "rice".

<p>&nbsp;</p>

#### Listing foods in food database: `list`
Lists out all foods in the database and their respective calories.

Format: `list /food`

Example of usage:

`list /food`

<p>&nbsp;</p>


### Weight Tracker

#### Recording weight: `weight /set`
Updates the user's current weight as well as their weight record for the day in the weight tracker.

Format: `weight /set WEIGHT`

Example of usage:

`weight /set 55.6`

<p>&nbsp;</p>


#### Listing weight records: `list`
Lists out all past records or the past month of weight entered by the user.

Format: `list /weight /all` or `list /weight /month MONTH_INTEGER`

Example of usage:

1. `list /weight /all` 

Sample output:

```
Your weight progress since the start of your FitNUS journey: 
2001-03-12: 51.5kg
2001-03-13: 51.7kg
2001-03-14: 51.8kg
2001-03-15: 51.9kg
2001-04-12: 54.2kg
2021-10-27: 63.3kg
2021-10-31: 54.0kg

You have gained 2.5 kg since the start of your FitNUS Journey!
```

2.`list /weight /month 3`

Sample output:

```
Your weight progress in March: 
2001-03-12: 51.5kg
2001-03-13: 51.7kg
2001-03-14: 51.8kg
2001-03-15: 51.9kg

You have gained 0.4 kg during the month of March!
```

<p>&nbsp;</p>

### Personalisation

####  Setting gender: `gender /set`
Sets the user's gender to either Male or Female.

Format: `gender /set GENDER_SYMBOL`

Example of usage: `gender /set m`

Sample output:
```
You have set your gender to Male
```

<p>&nbsp;</p>


####  Setting height: `height /set`
Sets the user's height in centimeters.

Format: `height /set HEIGHT`

Example of usage:`height /set 180`

Sample output:
```
You have set your height to 180 cm!
```

<p>&nbsp;</p>


####  Setting age: `age /set`
Sets the user's age in years.

Format: `age /set AGE`

Example of usage:`age /set 18`

Sample output:
```
You have set your age to 18 years old!
```

<p>&nbsp;</p>

####  Setting calorie goal: `calorie /set`
Sets the user's calorie goal in kcal.

Format: `calorie /set CALORIE_GOAL`

Example of usage:`calorie /set 2000`

Sample output:
```
The calorie goal has been set to 2000
```

<p>&nbsp;</p>

####  Generate and set calorie goal: `calorie /generate`
Generates and sets a calorie goal based on the user's target weight loss/gain per week, age, height, weight and gender.
Format: `calorie /generate /WEIGHT_CHANGE_TYPE WEEKLY_TARGET`

* The `WEIGHT_CHANGE_TYPE` can be of the following 2 types: 
  * `lose` - to denote aiming to lose weight
  * `gain` - to denote aiming to gain weight

Example of usage:`calorie /generate /gain 0.5`

Sample output:
```
Your new calorie goal to gain 0.5 kg per week is 2702 kcal daily!
```

<p>&nbsp;</p>

#### View personal data: `list /user`
View all the personal body measurements including gender, age, weight, height and calorie goal.
Format: `list /user`

Sample output:
```
Here are the matching foods in your database:
Calorie Goal: 2365 
Gender: m
Age: 18
Weight: 63.3
Height: 180
```

### Other

#### Viewing Help: `help`
Lists out available commands and additional information regarding each command.

Format: `help`

<p>&nbsp;</p>

#### View statistics: `summary`
FitNUS supports two kinds of diet reports:



##### Weekly report
Weekly report gives you an overview of your diet over the past 7 days, which includes:
- Graph of daily calorie intake
- Average calorie intake
- Most frequently eaten foods
- Least frequently eaten foods

Format: `summary /week`

##### Monthly report
Monthly report gives you an overview of your diet over this month, which includes:
- Average calorie intake
- Most frequently eaten foods
- Least frequently eaten foods

Format: `summary /month`

<p>&nbsp;</p>

#### Suggest food based on food type and calorie goal: `suggest`
Filters food items in the database based on food type (meal, snack, beverage, others) 
that if consumed, will not exceed the daily calorie goal set by the user.

Format: `suggest /FOODTYPE [/sort]`

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
Edit | edit INDEX_OF_FOOD FOOD_NAME | `edit 1 burger`
Remove entry | remove /entry INDEX_OF_FOOD | `remove /entry 2`
Remove food | remove /food INDEX_OF_FOOD | `remove /food 12`
Find food | find /food KEYWORD | `find /food rice`
Find entry | find /entry KEYWORD | `find /entry rice`
List food | list /food | `list /food`
List all entries | list /entry | `list /entry`
List daily entry | list /entry | `list /entry /day`
List weekly entry | list /entry | `list /entry /week`
List weight record | list /weight | `list /weight`
View weekly statistics | summary /week | `summary /week`
View monthly statistics | summary /month | `summary /month`
Suggest food | suggest /FOODTYPE <br /> suggest /FOODTYPE /sort | `suggest /meal` <br /> `suggest /snack /sort`


