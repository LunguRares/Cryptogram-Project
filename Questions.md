# User Stories

### As a user, I want the game to have the choice between number and letter mappings so that I can play with both my keypad and the letter keyboard
    -User picks mapping at start of game
    -The user can swich mapping during the game

### As a user, I want the game to have a hint button so that I can get help when I get stuck.
    -The hint doesn't replace an already selected letter chosen by the user
    -The hint button does not have a limit on the number of times used
    
### As the product owner, I want the puzzle to have 15 diffrerent cryptograms so that the users have a diverse playing experience.
    -The cryptograms will be of varying character lengths 
    -The cryptogram given to the player will be selected at random
    -The user won't be able to add more cryptograms themselves
    
### As a user, I want to be able to log in, so that I can look at my statistics and keep track of my progress.
    -User creates username before the start of the game
    -Each username can only be used once or is invalid
    -Can't play game as 'guest'
    
### As a user, I want to be able to save the progress between session so that I can keep playing even when I quit the game.
    -Progress will only be saved for one cryptogram
    -User can choose the cryptogram to be saved
    -progress is saved with relevant username
    
### As a user, I want to be able to tell when I have mapped a letter twice so that It is easier to spot my mistakes.
    -The invalid selection will be circled to show the user
    -The user won't be made aware if the letter has been mapped wrong
    
### As a product owner, I want the game to have a random mapping for the letters between games, so that the game remains unique. 
    - There will be a mapping for each letter, and this will be made random between games.
    - Letter can't be mapped to itself
    - User won't be able to see mapping
    
### As a user, I want to have a "Show solution button" so that If I get completely stuck, I can simply start a new puzzle.
    -All remaining letters will be solved if this option is selected
    -Does not contribute to stats on leaderboard
    
### As a user, I would like the game to have a leaderboard so that I can compare my score with other players.
    -The leaderboard will be sorted by successful cryptogram completions
    -User names will be shown on the leaderboard
    -The leader board will be kept up to date and consistent
    
### As a product owner, I don't want the game to have a 'reset statistic' button so that the users cannot reset their scores.
    -Users stats remain forever
    -Users can't edit their own stats in any way
    
### As a developer, I want the user data and cryptograms to be saved as a text file so that the information is stored between sessions.
    - The cryptogram progress will be saved after every move in an external text file.
    - The cryptogram progress will be loaded upon signin so that the user can proceed with their progress from their last session.
    
### As a user, I would like to be notified if the puzzle is correct when I input the last letter, so that I know when the game is completed.
    - Meesage appears on screen telling the user they have successfully completed the puzzle
    - No message if the mapping is wrong i.e fill in all letters and senetence is incorrect
    - This will only occur when the last letter has been input and there is no blank spaces left on the board.




### As a gamer, I would like the game to have a timer so that I can keep track of my scores.
- The game will have a timer.
- The timer will start at 00:00.
- The timer will begin when the puzzle is first loaded.
- The timer will keep counting in seconds until the puzzle is solved.

### As a visually impaired person, I would like the game to be accesible so that I can play easily.

- The game will feature two font sizes.
    - The font will be able to be changed using the game's menu

### As a user I would like the game to be intuitive so I dont have to waste my time figuring out how it works.


    



   - At the end of every game, the user will have a chance to input their username
   - This username will be shown on a leaderboard, alongside their time and the mode they were playing in
   - This leaderboard will be consistent between games, and locally updated after every game.
    
### As a developer in the game, we want the game to be easy to maintain so that we can push updates faster and easier.

- The game will have a maintainable codebase.
- The game will have a thorough documentation.
- The game will have easy-to-read code.
    
### As a stakeholder, I would like the game to be accessible to all ages so that the customer base can be as large as possible.

   - The game will have a variety of difficulty levels
   - The game will contain no vulgar and profane language so that people of all ages can play.
   - The game will contain no gore and violence so that it it accessible for everyone.
    
### As a person with sensitive eyes, I would like the game to have a dark mode so that I don't get eye strain.
- The game will have a "dark" user interface
- The game will have a light mode for those that want to use it.

### As a busy woman I would like the game to be quick to play so that I can play on my busy commute to work.
- The game will be quick to load
- The game will be quick to start once the menu loads
    
### As an avid bookreader, I would like the game to have some quotes from my favourite authors so I can relate to the game more
   - The game will have a "category" option
   - The category option will be able to be accessed through the menu.
   - This category option will restrict what puzzles appear upon playing the game.
    
###  As a stakeholder in "Rosanne English Gaming corp🌹" I would like the game to be easy to run so that more people will play the game.
   - The game will run as an executable file
   - The game will load quickly.
    
### As a stakehodler in "Rosanne English Gaming corp🌹" I need the game to be GDPR compliant so that the company does not get sued.
   - The game will have an encrypted database so that users cannot access other user's data
   - The game will notify the user that their usernames are being stored.
   - The dialog will have a consent button, 
      - If the user consents, they can proceed.
      - If the user does not consent, the leaderboard features will be turned off. 

### As a gamer, I would like the game to have a "Hard Mode" So that I can have a challenge.

- The game will have a "hard mode" that will:
    - Change the timer settings so that the game counts down, instead of up
    - If the puzzle is completed, the user will have to "check" for completion themselves as opposed to the computer checking completion after every move.


   - The game will have a GUI.
   - The GUI will have a menu that is accessible throughout the game
   - The GUI will be written in English
   - The GUI will have an "exit" option, that will exit the current game
1.
