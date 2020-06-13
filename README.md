# Pathfinding_Mazes

There are no locks in the maze, the maze has no disconnected components which means every block can be reached
from every other block via a valid path
1. change the size variable to make the square maze bigger or smaller
size 5-50 is preferable but the algorithm works for any size value
2. player starts at top left and has to reach the bottom right
3. enter button will reset the player and generate a new maze
4. space bar shows the shortest path solution
5. displays game completed and you need to press enter to start new game

autoSolver: space bar will reset the player to starting position, show the solution and player will move on its on
till it reached the end position and stops. path is shown by simple dots which looks simplistic.

explore: it just lets you move the player and end position to explore the maze. can be also used as pvp.

mazeSolver: it is like autosolver except the player does not move on its own. path is shown by connecting lines which looks cooler.

mazes: it is the simplest version of mazeSolver in which solution path cannot be viewed.

#Updates
1. add end game condition in explore
2. add path correction in mazeSolve and autoSolver which will dynamically show the correct path if you deviate from the
path shown and in case of autoSolver, wont reset the player back to the original position.
3. needs some thought to make it an interesting pvp game.
4. customisation feature to allow users to select the start and end positions after starting every new game.
5. add a tree datatype for generated maze to make many operaions and functionality easier to implement.
6. solution path color will change as it gets closer to destination point.

