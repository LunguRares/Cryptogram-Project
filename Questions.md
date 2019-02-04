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



