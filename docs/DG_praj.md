## Parser Component

**API: `Parser.java`**

The parser component makes use of the user input String from the `fitNus` class to detect the type of `Command` called. 
It then returns a `Command` object that represents the type of command called through the input.

The `Parser` component:

- determines the type of `Command` object and returns it.
- handles input exceptions and returns relevant `FitNusException` command.

### Implementation

