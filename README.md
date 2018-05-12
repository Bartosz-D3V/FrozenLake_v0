# Frozen Lake V0

## Introduction
FrozenLake-V0 is simple implementation of Q-Learning algorithm that tries to solve OpenAI's problem described as FrozenLake: https://gym.openai.com/envs/FrozenLake-v0/
The following application was written in pure Java 8 - no libraries or frameworks apart from standard SDK were used.

## Problem
The lake in the middle of the park is mostly frozen, but there are places where the ice is melted so it creates holes on the surface – stepping on the hole subtracts significant amount of points, as falling into the ice-cold water is dangerous. The only way to pass through the lake is to step on the frozen floes. The task is to safely and quickly move from the starting, to the ending point.
The grid is built with 8x6 grid, although, extending or shrinking the grid is available by simply amending the text file with minimal changes to the source code.

## Domain
Grid is built with 4 different characters:
S – (Start) Indicates the starting point – only one in the grid
F – (Frozen) Indicates frozen floe that the algorithm can use to move around the lake
O – (Open) Indicates a hole in a frozen lake which cannot be accessed
E – (End) Indicates the destination – only one in the grid
In addition, it is only possible to move to neighbouring field – either by moving up, down, left or right.
Moving diagonally is **not** permitted.

In *resources* folder user can find example 'lake' that will be used by the algorithm.
By default the table consist of 6x8 grid, but can be changed easily, accordingly to user's needs.

## Reward lookup table
The reward matrix defines the values that the agent will receive upon moving to the state.
Each row in the R matrix represents a state that the agent can occupy, whilst each value of the row provides the reward (or penalty) that the agent will receive upon moving to that state.
Each cell contains one of four values:
a)	1 – for possible transitions – for instance, moving from state 2 to state 22.
b)	-1 for impossible transitions – diagonal movements, or movements to floes that are farer than 1 step are marked as impossible.
c)	-100 – penalty for stepping into the hole.
d)	2000 – reward for reaching the final state.
e)	-5 – penalty for moving to the state with lower value than current step. 

Program needs approximately 90 training cycles to find the most optimal way.

## License
MIT
