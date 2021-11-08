# Adeline Boo - Project Portfolio Page

## Product Overview
FitNUS Tracker is a desktop app for tracking daily food intake and
to help users achieve a healthy lifestyle.
FitNUS is specially made for Computing Students living in University Town.

### Summary of Contributions

- **Feature:** Added user profile initialisation 
  - What it does: Detects first time users (no user data) or if user data
    in storage was corrupted and allows user to set up their profile. 
    Upon setting up profile, a daily calorie goal that
    maintains their current weight is calculated and set.
  - Justification: This feature is essential to the calorie goal generation 
    feature, as calorie goal generation cannot be performed if any of the user
    data is missing. 
  - Highlights: This feature was challenging to implement as it required several
    components to be integrated in order for certain aspects of it to be achieved
    (eg continuously prompting the user for their input for a certain attribute (e.g. gender)
    if the entered input when setting up the attribute was invalid, before moving
    on to setting up the next attribute).
- **Feature:** Added user profile editing
  - What it does: Allows the user to edit their user profile (e.g. change gender, change age)
  - Justification: This feature is needed as the user's data may change after a period
    of time, or they may have entered their data wrongly during the profile setup process.
    It is important for the user's data to be accurate so that the generated calorie goal
    is accurate.
  - Highlights: Many practical aspects of each user attribute had to be considered in order
    to implement an upper and lower bound (eg the height has to be between 40 and 300 cm).
- **Feature:** Calorie goal generation
  - What it does: Generates a daily calorie goal automatically
    for the user according to their body type and their desired weekly
    weight change, and sets the user's calorie goal to the new goal
  - Justification: This feature brings convenience to the user who wants to quickly 
    get started on their dieting journey instead of having to research what their daily
    calorie goal should be to achieve their goal weight.
  - Highlights: Much research was done to acquire a formula that can generate the
    daily calorie goal accurately and easily. 
- **Feature:** Viewing remaining calories
  - What it does: Allows the user to view the remaining calories
  that they can consume for the day according to their food tracker
  and their daily calorie goal.
  - Justification: This feature improves the app by allowing the user to keep track
  of how many calories they have left throughout the day in order to make decisions
  about what to eat according to their dieting goals. 
  It is also used by other features in the app such as the food suggestions feature. 
  - Highlights: This feature required the integration
  of several components of the app (the user profile and the food tracker).
- **Feature:** Weight tracker
  - What it does: Allows the user to record their weight daily and view their weight progress.
  - Justification: This feature is one of the main features of the app as a diet and
  weight tracking app. It allows the user to observe their weight progress in order to 
  analyse if their diet is effective in helping them achieve their goal weight.
  - Highlights: This feature was challenging to implement as many use cases had to be considered
    (e.g. when the weight tracker is empty, when the weight tracker has only one entry) as the tracker
  comes with the additional feature of showing their total weight change during the selected time frame.
  This feature also required careful integration of other components such as storage.
  - Credits: This feature was implemented with the help of @brendanlsz, who created the base functions for
  app storage which were reused with slight modifications in this feature.


- Code contributed:[https://nus-cs2113-ay2122s1.github.io/tp-dashboard/#breakdown=true&search=aaapppsss](RepoSense link)

- Enhancements to existing features:
  - Added functionality to initialise UTown food database (Pull request #370)


- Documentation:
  - User Guide: 
    - Added full documentation for the following features:
      - Set gender/age/height/weight
      - Generate calorie goal
      - View remaining calories
      - List weight tracker
  - Developer Guide:
    - Added full documentation for the following features:
      - Set gender/age/height/weight
      - Generate calorie goal
      - View remaining calories
      - List weight tracker
    - Added parts of documentation for the following features:
      - Overall architecture
