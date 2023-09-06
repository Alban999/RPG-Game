# RPG-Game

This game is a role-playing game that consists of the evolution of a Polytechnique student within ULB (Université Libre de Bruxelles). The main character is a student who, through various peripeties (like taking exams, partying, etc.), will have to progress in order to obtain his degree from Polytechnique.

The architecture is composed of 4 Design Patterns:
- Controller: contains the Keyboard class which allows to create commands for the game.
- main: contains the MainStudent class, which enables us to launch the program.
- Model: contains all the classes and interfaces needed to run the game. We
include:
  - Classes:
    - MoveAuto (which animates the character when it moves)
    - Enemy
    - Game
    - GameObject
    - Loot
    - Player
    - Shoot
    - Shop
  - Interfaces:
    - Directable
    - Life
    - Positionable
    - Updatable
- View: is composed of classes that enable display:
  - Map: allows the display of maps
  - Window: allows the creating of a window
This architecture allows us to clearly differenciate the controls, the game, and the display.

For more information, see the following [report](https://drive.google.com/file/d/1DnKR6TKi2AORRhLP6Srtdrfdn0NDKQB1/view?usp=drive_link).

<img width="493" alt="Game1" src="https://github.com/Alban999/RPG-Game/assets/74149424/9835f88d-5ef6-4d7c-81dd-e0c9d9950c1f">
<img width="493" alt="Game2" src="https://github.com/Alban999/RPG-Game/assets/74149424/10fda25f-adbd-49ea-b12b-e858dfc1ae47">
<img width="493" alt="Game3" src="https://github.com/Alban999/RPG-Game/assets/74149424/88d59d02-243b-4d55-8f97-6380d99b1d3f">
<img width="488" alt="Game4" src="https://github.com/Alban999/RPG-Game/assets/74149424/f98853a3-3bb0-497b-92e3-786728d446bc">
<img width="487" alt="Game5" src="https://github.com/Alban999/RPG-Game/assets/74149424/1bb51350-958c-4994-9375-2f46761c84f8">
