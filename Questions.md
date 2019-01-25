# General Questions
Is there a limit to the project size, i.e. memory or line numbers?
Do we need to write J-unit testing?
What is the most important features that we need to meet the minimum?

## Must Have
What are some of the key features that we cannot deliver the product without
What are key defining features of the program that must be implemented?
After the required specifications are met, what are some of the key features that should be implemented as a second stage?

## Should Have
Should the program keep track of the time of the game?
Should the program have a leaderboard?
In the cryptogram, what is the minimum amount of puzzles that the game must have?

## Could Have
Do we need to have a feature that has letters remaining displayed? 
Do we need to allow users to be able to add their own sentences?
If a leaderboard is implemented, is it important for the end user to not be able to edit the leaderboard?

🌹🌹

# User Stories
5    As a competitive gamer, I would like the game to have a timer so that I can keep track of my scores.
- The timer will start at 00:00.
- The timer will begin when the puzzle is first loaded.
- The timer will keep counting in seconds until the puzzle is solved.
5    As an elderly person, I would like the game to have a large font so that I can play the game without the glasses.
- The game will feature two font sizes.
    - The font will be able to be changed using the game's menu
5    As a 10 year old kid I would like the game to be easy to play so that I can play against my friends.
    - The game will have a GUI.
    - The GUI will have a menu that is accessible throughout the game
    - The GUI will be written in English
    - The GUI will have an "exit" option, that will exit the current game
    
4    As a hardcore gamer, I would like the game to have a "Hard Mode" So that I can compete at the highest levels.
- The game will have a "hard mode" that will:
    - Change the timer settings so that the game counts down, instead of up
    - If the puzzle is completed, the user will have to "check" for completion themselves as opposed to the computer checking completion after every move.
4    As a hardcore gamer, I would like the game to have a leaderboard so that I know when I am improving.
    - At the end of every game, the user will have a chance to input their username
    - This username will be shown on a leaderboard, alongside their time and the mode they were playing in
    - This leaderboard will be consistent between games, and locally updated after every game.
4    As a developer in the game, we want the game to be easy to maintain so that we can push updates faster and easier.
    - The game will have a maintainable codebase.
    - The game will have a thorough documentation.
    - The game will have easy-to-read code.
4    As a stakeholder, I would like the game to be accessible to all ages so that the customer base can be as large as possible.
    - The game will have a variety of difficulty levels
    - The game will contain no vulgar and profane language so that people of all ages can play.
    - The game will contain no gore and violence so that it it accessible for everyone.
3    As a person with sensitive eyes, I would like the game to have a dark mode so that I don't get eye strain.
    - The game will have a "dark" user interface
    - The game will have a light mode for those that want to use it.
3    As a busy woman I would like the game to be quick to play so that I can play on my busy commute to work.
    - The game will be quick to load
    - The game will be 
3    As an avid bookreader, I would like the game to have some quotes from my favourite authors so I can relate to the game more
    - The game will have a "category" option
    - The category option will be able to be accessed through the menu.
    - This category option will restrict what puzzles appear upon playing the game.
2    As a stakeholder in "Rosanne English Gaming corp🌹" I would like the game to be easy to run so that more people will play the game.
    - The game will run as an executable file
    - The game will load quickly.
1    As a stakehodler in "Rosanne English Gaming corp🌹" I need the game to be GDPR compliant so that the company does not get sued.
    - The game will have an encrypted database so that users cannot access other user's data
    - The game will notify the user that their usernames are being stored.
    - The dialog will have a consent button, 
        - If the user consents, they can proceed.
        - If the user does not consent, the leaderboard features will be turned off. 
