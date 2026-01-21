# Minesweeper

Implementation of the classic **Minesweeper** game in **pure Java 8**, without frameworks and without a graphical user interface (UI).

The focus of this project is not the game itself, but the **applied study of algorithms, data structures, and asymptotic complexity**, using a simple problem to practice technical decisions that frequently appear in **exams, interviews, and real-world systems**.

## Project Objective

This project was created as an **intentional, hands-on exercise** to:

* Review and deepen knowledge of **data structures**
* Apply appropriate data structures (Set, Queue)
* Work with **matrices and 8-direction neighborhood**
* Apply **traversal algorithms (BFS)**
* Analyze and reduce **asymptotic complexity**
* Avoid common pitfalls such as **stack overflow** and **unnecessary scans**

The core idea is to show that **simple problems can (and should) be solved with engineering rigor**.

## Technical Motivation

### Why Minesweeper?

Minesweeper is an excellent didactic problem because it involves:

* Matrix representation
* Neighborhood calculation
* Expansion of connected regions (flood fill)
* Control of visited states
* Trade-offs between simplicity and efficiency

All of these frequently appear in algorithm exams and technical interviews.

## Why Java 8?

The choice of **Java 8 was intentional**.

* It is the most common version in **exam platforms and technical interviews**
* It avoids dependency on modern language features
* It keeps the focus on **logic, algorithms, and data structures**, not syntactic sugar

If a solution is clear, efficient, and safe in Java 8, it easily adapts to newer versions.

## Solution Overview

The game is modeled using two main structures:

* int[][] fields — represents the board  
  * -1 indicates a bomb  
  * values 0+ indicate the number of adjacent bombs  

* boolean[][] revealed — controls which positions have already been revealed

The game logic is divided into two well-defined phases:

1. Initialization (bomb generation and adjacency calculation)
2. Execution (cell revealing and game state control)

## Strategies and Technical Decisions

### Bomb Generation with Set<Position>

* Bombs are generated randomly and stored in a HashSet:
  * guarantees uniqueness without extra logic
  * avoids manual collisions
  * expected complexity O(k), where k is the number of bombs.

This approach simplifies the code and avoids repeated scans of the board.

### Adjacency Preprocessing

* After bomb generation, the board is preprocessed:
  * bombs are marked with -1
  * adjacent cells are incremented only once

This clearly separates:
* the game construction phase
* from the execution (reveal) phase

And avoids recalculating neighborhoods on every move.

### Flood Fill with BFS (Breadth-First Search)

To reveal empty areas (cells with value 0), a BFS-based approach is used, with a Queue (ArrayDeque).

#### Why BFS and not recursive DFS?

* The traditional recursive approach can cause:
  * StackOverflowError on large boards
  * loss of depth control

* With BFS:
  * the call stack does not grow
  * control is explicit
  * each cell is processed at most once

This is a common decision in real systems, where stability and predictability are just as important as correctness.

### Localized Processing (no global scan)

* During flood fill:
  * only cells reachable from the starting position are processed
  * already revealed cells are ignored

This ensures that the algorithm cost is proportional only to the area that is actually revealed.

### Explicit Game State Control

* The game state is controlled by a GameState:
  * RUNNING
  * WON
  * LOST

Invalid states are handled with exceptions, making failures explicit and preventing inconsistent transitions.

## Complexity Considerations

* Board initialization: O(n × m)
* BFS reveal: O(k), where k is the number of cells actually visited
* No need to reprocess already evaluated cells

In practice, this means the algorithm scales better than naive solutions.

## Project Structure
```
src/
├── Main.java
├── Minesweeper.java
├── GameState.java
├── Position.java
.gitignore
README.md
```

* Main — application entry point  
* Minesweeper — core game logic  
* GameState — explicit game state control  
* Position — coordinate abstraction (row/column)

## How to Run

```
git clone https://github.com/FernandoBittencourt/Minesweeper.git
cd Minesweeper
javac -d out src/*.java
java -cp out Main
```
Use **JDK 8** for full compatibility.

## How to Use This Project for Study

This project is recommended for those who want to practice:

* matrices and neighborhood logic
* BFS / flood fill
* visited state control
* complexity analysis
* clean code without frameworks

A good suggestion is:

1. Implement a simple version
2. Compare it with this approach
3. Evaluate gains in readability and efficiency

## Technical Evolution & Lessons Learned
This project wasn't built in its current state from day one. It underwent several refactors as I identified bottlenecks and applied better data structures:

1. From Global Scanning to Targeted Processing
   
  **Initial approach:**  
  A naive solution that scanned the entire grid multiple times to calculate bomb proximity.

  **The pivot:**  
  I replaced global scans with a `Set<Position>` of bombs and pre-calculated adjacency. This reduced initialization overhead and simplified the logic.

2. Solving the Stack Overflow Risk (DFS to BFS)
 
  **Initial approach:**  
  I started with a recursive DFS (Depth-First Search) for the flood-fill (revealing empty cells).

  **The pivot:**  
  Recognizing that recursion could lead to a StackOverflowError on larger boards, I refactored the core logic to an iterative BFS (Breadth-First Search) using a Queue (ArrayDeque). This made the expansion predictable and safe regardless of board size.

3. The "Object vs. Primitive" Trade-off
 
  **Discussion:**  
  I considered refactoring the two parallel arrays (int[][] fields and boolean[][] revealed) into a single Cell[][] board object matrix.

  **Decision:**  
  I decided to stick with primitive arrays. Using Cell[][] would require an extra initialization step (filling the matrix with objects to avoid NullPointerException) and would increase memory overhead. Keeping them separate allowed me to focus purely on algorithmic efficiency and matrix manipulation, which was the primary goal of this study.

## License

This project is licensed under the **MIT** License.

Feel free to study, adapt, and evolve it.
