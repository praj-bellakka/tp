## Weight Tracker Design & Implementation

###Architecture
**Main components of the architecture**

The weight tracker exists as an ArrayList called `WeightProgressEntries` within the User class. The ArrayList contains objects of class `WeightProgressEntry`.

**SetWeightCommand Component**

The entry point for setting or updating weight. The `execute` method in this object calls `updateWeightAndWeightTracker` method in the User object initialised in the main file in order to update the user's weight and weight progress.

**Storage Component**

Weight progress entries are stored in a text file in the following format:

`WEIGHT | DATE(YYYY-MM-DD)`

Example: `100 | 2021-03-01`

The weight progress storage file is updated every time the user sets or updates their weight for the day, as all storage files are updated at every iteration of the main loop using the `saveFitNus` method.

On startup, the storage file is  parsed and the corresponding WeightProgressEntry objects are created and loaded into the ArrayList.

**User Component**

How the User component works in the context of the weight tracker:
1. When the user inputs the weight setting command, User is called upon to execute the function to update the user's weight and weight tracker.
2. In all cases, the weight attribute of the initialised User object will be updated to the new weight inputted by the user.
3. If no weight progress entries were present in the storage text file, the tracker does not attempt to calculate the difference between the updated weight and the previous weight.
4. If the latest weight progress entry was recorded on the same day, that entry is updated with the new weight (that is, no new entry is added to the weight tracker). Otherwise, a new weight progress entry is created in the ArrayList with the current date and new weight.

