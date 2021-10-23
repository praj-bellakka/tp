
<h1>Developer Guide</h1>

<h2>Content </h2>
1. <a href="#Product-Scope">Product Scope</a>
2. <a href="#quick-start">Quick Start</a>
3. <a href="user-story"> User Story</a>
4. <a href="#Architecture">Application Architecture </a>
5. <a href="#Implementation">Implementation </a>
6. <a href="#instruction-for-manual-testing">Instruction for manual testing</a>
7. <a href="#NF-Requirement">Non-functional Requirement </a>

<h2> Acknowledgements </h2>
{list here sources of all reused/adapted ideas, code, documentation, and third-party libraries -- include links to the original source as well}

<h2 id="Product-Scope">Product scope</h2>
    <h3>Target user profile</h3>
    NUS Computer Engineering students reside in UTown going on diet.
    <h3>Value proposition</h3>
    Help user to keep track of their daily calorie intake, and manage their diet wisely.
<h2 id="quick-start">Quick Start</h2>
1. Ensure you have Java 11 or above installed in your Computer.
2. Download the latest fitnus.jar from here (no link for now).
3. Copy the file to the folder you want to use as the home folder for your WellNUS Tracker
4. Type the following command in your terminal to run this program: java -jar fitnus.jar (You should change directory to where the wellnus.jar file is located or provide the absolute path of wellnus.jar).
   <br/>Some example commands you can try:
- `add Chicken Rice /cal 607`: Adds an entry of Chicken Rice with 607 calories to your food tracker and food database.
- `list food`: Lists all foods in database
- `list intake /DAY`: Lists all entries in the food tracker for the day.
- `exit`: Exits the app.
  <br/>
  Refer to the User Guide (no link for now) for details of each command.

The entry point of the app is the `FitNUS` class, where the application is run and all other components are initialised and used. The primary components of the app are listed below:
- `Command`: The abstract class that all other command classes inherit from
- `EntryDatabase`: For handling all functionality regarding food tracker entries.
- `FoodDatabase`: For handling all functionality regarding food database entries.
- `Parser`: For parsing user input.
- `Storage`: For handling backend storage.
- `Ui`: For displaying information to the user.
- `User`: For handling all functionality regarding personalisation of user experience.






<h2 id="#user-story"> User Stories</h2>

|Version| As a ... | I want to ... | So that I can ...|
|--------|----------|---------------|------------------|
|v1.0|new user|see usage instructions|refer to them when I forget how to use the application|
|v2.0|user|find a to-do item by name|locate a to-do without having to go through the entire list|

<h2 id="Architecture"> Architecture </h2>

![Overall Architecture Diagram](diagrams/overall%20architecture.png) <br/>
The Architecture Diagram given above explains the high-level design of the App. <br/>
Given below is a quick overview of main components and how they interact with each other.

<h3> Main components of the architecture </h3>

<h4>command component</h4>

![command class diagram](diagrams/command%20class%20diagram.drawio.png) <br/>
- Different kinds of commands inherit from abstract class command, and inside which there is an abstract method called `execute()`
- Subclasses are instantiated through parser after parsing the user's input, and each command has its own `execute()` command to perform its task.

<h4>tracker</h4>

![tracker class diagram](diagrams/tracker%20class%20diagram.png) <br/>

<h4>database</h4>

{TO BE ADDED HERE}


## Glossary

* *glossary item* - Definition

## Add Food Entry Feature
The add food entry mechanism is facilitated by <code>AddFoodEntryCommand</code>.
It extends <code>Command</code> and stores the data internally into <code>EntryDatabase</code>
and <code>FoodDatabase</code>.
Additionally, it implements the following operations:
- <code>EntryDatabase#addEntry(Entry)</code> -- Adds a new entry into the entry database
- <code>FoodDatabase#addFood</code> -- Adds a new food into the food database

![AddFoodEntrySeqDiagram](AddFoodEntry.png "AddFoodEntry Sequence Diagram")


<h2 id="Implementation"> Implementation </h2>
<br/>
<ol>
<li>
<h3>Weight Tracker</h3>
The weight tracker exists as an ArrayList called <code>WeightProgressEntries</code> within the User class. The ArrayList contains objects of class <code>WeightProgressEntry</code>.
</li> 

<li>
<h3>SetWeightCommand Component</h3>
The entry point for setting or updating weight. The <code>execute</code> method in this object calls <code>updateWeightAndWeightTracker</code> method in the User object initialised in the main file in order to update the user's weight and weight progress.
</li>

<li>
<h3>Storage Component</h3>
Weight progress entries are stored in a text file in the following format: <br/>

<code>WEIGHT | DATE(YYYY-MM-DD)</code> <br/>
Example: <code>100 | 2021-03-01</code><br/>

The weight progress storage file is updated every time the user sets or updates their weight for the day, as all storage files are updated at every iteration of the main loop using the <code>saveFitNus</code> method.<br/>
On startup, the storage file is  parsed and the corresponding WeightProgressEntry objects are created and loaded into the ArrayList.
</li>

<li>
<h3>User Component</h3>
<div>
How the User component works in the context of the weight tracker: <br/>
<ol>
<li>When the user inputs the weight setting command, User is called upon to execute the function to update the user's weight and weight tracker.</li>
<li>In all cases, the weight attribute of the initialised User object will be updated to the new weight inputted by the user.</li>
<li>If no weight progress entries were present in the storage text file, the tracker does not attempt to calculate the difference between the updated weight and the previous weight.</li>
<li>If the latest weight progress entry was recorded on the same day, that entry is updated with the new weight (that is, no new entry is added to the weight tracker). Otherwise, a new weight progress entry is created in the ArrayList with the current date and new weight.</li>
</ol>
</div>
</li>

<li>
<h3>View Diet Summary</h3>
The Summary class provides an overview of user's diet over the past week/month.

<h5>command format</h5>
<code>summary /week</code> or <code>summary /month</code>

<h5>implementation</h5>
<code>Summary</code> class provides two methods <code>generateWeekSummaryReport()</code> and <code>generateMonthSummaryReport()</code> to give the user weekly/monthly report of their diets.<br/>
<ul>
<li> <code>generateWeekSummaryReport()</code> shows weekly calorie intake trend graph, average daily calorie intake, and the most/least frequently eaten food over past 7 days.</li>
<li> <code>generateMonthSummaryReport()</code> shows average daily calorie intake, and the most/least frequently eaten food over this month.</li>
</ul>

<h5>UML Sequence Diagram</h5>
The following sequence diagram describes the operation of the <code>generateWeekSummary()</code> method.<br/>

![UML Sequence Diagram for generateWeekSummaryReport()](diagrams/weekly%20report.png) <br/>

The following sequence diagram describes the operation of the <code>generateMonthSummary()</code> method.

![UML Sequence Diagram for generateMonthSummaryReport()](diagrams/monthly%20report.png) <br/>

</li>

<li>
<h3>Storage</h3>
The Storage class reads and writes data to and from the text file.

<h5>Storage format</h5>

<div><strong>Every line in each text file represents one object / entry / item</strong></div>

- `FoodDatabase`: FOODNAME | CALORIE_VALUE

Example:
```
Nasi Lemak | 400
Ramen | 600
```

- `EntryDatabase`: MEALTYPE | FOODNAME | CALORIE_VALUE | DATE

Example:
```
Dinner | Ramen | 500 | 2021-10-20
Lunch | Fried rice | 600 | 2021-10-20
```

- `User`: CALORIE_GOAL | GENDER

Example:
```
1000 | 0
```

- `User weight`: WEIGHT | DATE

Example:
```
60.0 | 2021-07-20
59.0 | 2021-08-20
58.0 | 2021-09-20
45.0 | 2021-10-21
```


<h5>Implementation</h5>
<ol>
<li>
<div><strong>Saving to file</strong></div>
<code>FoodDatabase</code>, <code>EntryDatabase</code>, and <code>User</code> classes each have a method to convert
its data to String format. This String is then saved to the text file. <br/>
For instance, when saving the <code>FoodDatabase</code> data, <code>Storage</code> calls the <code>convertDatabaseToString()</code>
method to obtain the String representation of all the data within the `FoodDatabase`. This String is then written to the text file.
</li>


<li>
<div><strong>Loading from file </strong></div>
<code>Storage</code> makes use of the <code>BufferedReader</code> and <code>FileInputStream</code> provided  by <code>java.io</code> to access 
the contents of the storage text files. This is then passed to the respective objects for preloading. <br/>

For instance, when preloading the <code>FoodDatabase</code> data, <code>Storage</code> accesses the storage text file
and passes the file contents to the <code>preLoadDatabase()</code> method in ,<code>FoodDatabase</code> which populates
the ArrayList in <code>FoodDatabase</code>.
</li>

</ol>




<h5>UML Sequence Diagram </h5>
The following sequence diagram describes the operation of the <code>saveFoodDatabase()</code> operation.<br/>

![UML Sequence Diagram for Storage - saving data](diagrams/StorageSequenceUML.PNG)

</li>

<li>
<h3>Parser Component</h3>
<div>The parser component makes use of the user input String from the <code>fitNus</code> class to detect the type of <code>Command</code> object called.
It then returns a <code>Command</code> object that represents the type of command called through the input.</div>

<h5>The <code>Parser</code> component</h5>:
<ul>
<li>determines the type of <code>Command</code> object and returns it.</li>
<li>handles input exceptions and returns relevant <code>FitNusException</code> command.li>li
</ul>

<h5>Implementation</h5>
<ul>
<li><h6>Identifying type of method called</h6>

The <code>Parser</code> is invoked through the <code>parseCommandType()</code> method. The input is first split up by identifying a space character.
If no space character is detected, and the <code>help</code> or <code>exit</code> method was not called, a <code>FitNusException</code> is thrown. The first string element is 
then compared with default list of commands to determine the type of method called using if-else statements. </li>
</ul>
</li>

</ol>

<h2 id="instruction-for-manual-testing"> Instructions for manual testing</h2>

{Give instructions on how to do a manual product testing e.g., how to load sample data to be used for testing}



<h2 id="NF-Requirement"> Non-Functional Requirements </h2>
1. Data of users and foods should be stored and retrieved swiftly without delay, even for a long time user with very a big data set.
2. User's and food's data should be kept safely, and it is crashed, the program should be able to detect it.
