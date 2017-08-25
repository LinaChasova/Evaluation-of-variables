# Evaluation-of-variables
Data structure: GRAPH;
Algorithm: Topological sort

You are given a file with a collection of equalities (line by line). Some of them are in a form of pure assignments X=integer_number, others are in form of binary operation X=Y*Z or even X=Y+integer_number (* or + are allowed only). Your task is to compute the value of variable R and print in to output.txt. If this is impossible - print ERROR. 

NB R not necessarily implicitly depend on all variables. 

Of course there are multiple ways to do this! We want you to use topological sort. Assume variables are vertices. If X=Y+Z this means X depends on both Y and Z (you should firstly compute Y and Z). 
We suggest:
Build a graph of dependencies.
Perform topological sort of the graph (until it is needed for computation).
Compute variable values.
PROFIT!!!
