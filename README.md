# Candy Clean Live Streamings

## Build system (Ant)

Targets:

- `clean` Cleans all the unnecessary files.
- `execute` Compiles and executes the program.
- `test` Compiles and executes all the tests.
- `jar` Creates a jar file to share the game.
- `document` Generates the documentation.

## Ideas

- Break candies of the same color in area.
- Have special candies for breaking rows and columns.
- Have obstacles in the board.
- Diagonal special candies.
- Special candy to invert colors.

## Links

[Twitch](https://www.twitch.tv/samuelete_26)

## Todo

- Tests for the basic functionality.
- Graphical User Interface.

(MySQL)
- Database to store all movements, boards.
- User authentication system with encryption.
- Save all the history of the last 5 games.

## Done
- Secure method to exit the program. If pressed CTRL+C handle the SIGINT signal saving the data to the database.
