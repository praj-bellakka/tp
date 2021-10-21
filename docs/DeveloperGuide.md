### Add Food Entry Feature
The add food entry mechanism is facilitated by <code>AddFoodEntryCommand</code>. 
It extends <code>Command</code> and stores the data internally into <code>EntryDatabase</code> 
and <code>FoodDatabase</code>. 
Additionally, it implements the following operations:
- <code>EntryDatabase#addEntry(Entry)</code> -- Adds a new entry into the entry database
- <code>FoodDatabase#addFood</code> -- Adds a new food into the food database

![AddFoodEntrySeqDiagram](AddFoodEntry.png "AddFoodEntry Sequence Diagram")
