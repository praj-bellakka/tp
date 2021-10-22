# User Guide

## Introduction

FitNUS Tracker is a desktop app for tracking daily food intake to keep a healthy lifestyle. 
It is based on a Command Line Interface (CLI) and has a rich function list in order to cater to the needs of potential users.

Here is the list of sections we will be covering in this User Guide.

- Quick Start
- Features

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

>  e.g. `add /MEALTYPE FOOD_NAME` can be used as `add /lunch sandwiches` or `add sandwiches`.


### Viewing Help: `help`
Lists out available commands and additional information regarding each command.

Format: `help`

### Adding custom food entry: `add`
Adds a custom food entry to the food tracker as well as the food database.


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

Examples of usage: 

`add /bfast chocolate rolls`

`add mushroom soup`

### Editing existing food entry: `edit`
Edits an existing food entry inside the database.

Format: `edit INDEX_OF_FOOD FOOD_NAME`

Example of usage:


### Delete food entry: `remove`
Deletes a food entry from the food tracker.

Format: `remove /entry INDEX_OF_FOOD`

Example of usage:

`remove /entry 2`

### Delete preset food: `remove`
Deletes food from the food database.

Format: `remove /food INDEX_OF_FOOD`

Example of usage:

`remove /food 12`

### Search food with keyword: `find`
Finds all matching food in the FoodDatabase based on the keyword you provided.

Format: `find /food KEYWORD`

Example of usage:

`find /entry rice`

### Search entry with keyword: `find`
Finds all matching entries in the EntryDatabase based on the keyword you provided.

Format: `find /entry KEYWORD`

Example of usage:

`find /entry rice`

### List foods in database: `list`
Lists out all foods in the database and their respective calories.

Format: `list /food`

Example of usage:

`list /food`

### List out entries for the day: `list`
Lists out all foods entered for the day.

Format: `list /entry`

Example of usage:

`list /entry`

### List past records of weight: `list`
Lists out all past records of weight entered by the user.

Format: `list /weight`

Example of usage:

`list /weight`
## FAQ


## Command Summary


