# Adeline Boo - Project Portfolio Page

## Product Overview
FitNUS Tracker is a desktop app for tracking daily food intake and
to help users achieve a healthy lifestyle.
FitNUS is specially made for Computing Students living in University Town.

### Summary of Contributions
Given below are my contributions to the project.

- **Enhancement:** Added user profile initialisation 
  - What it does: Detects first time users (no user data) or if user data
    in storage was corrupted and allows user to set up their profile (gender,
    age, height, weight). Upon setting up profile, a daily calorie goal that
    maintains their current weight is calculated and set to their profile
    according to their body type.
  - Justification: This feature is essential to the calorie goal generation 
    feature, as calorie goal generation cannot be performed if any of the user
    data is missing. 
  - Highlights: This feature was challenging to implement as it required several
    components to be integrated in order for certain aspects of it to be achieved
    (eg continuously prompting the user for their input for a certain attribute (e.g. gender)
    if the entered input when setting up the attribute was invalid, before moving
    on to setting up the next attribute)
    
- User profile editing: Allow user to edit their user profile anytime
  (e.g. change gender, change age)
- Calorie goal generation: Generate a daily calorie goal automatically
  for the user according to their body type and their desired weekly
  weight change
- Weight tracker: Allows user to record their weight daily and view
  their weight progress
- Viewing remaining calories: Allow user to view the remaining calories
  that they can can consume for the day according to their food tracker
  and their daily calorie goal

https://nus-cs2113-ay2122s1.github.io/tp-dashboard/#breakdown=true&search=aaapppsss


