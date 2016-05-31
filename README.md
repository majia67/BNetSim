# BNetSim
Boolean Network Simulation of Biological Networks

## Installation
To compile the package, you need JAVA SE 7 or higher version. Simply clone the repository and enter the src folder.
You will find all source code and you can compile the module you want by using javac. In the future I will add 
auto-compile script and pre-compiled jar files.

## Usage
To generate network description file: modify two examples I gave (Budding_Yeast_Cell_Cycle.java & C_elegans_Cytokinesis.java). 
The file is self-explanatory.

To do network simulation: java StatePrediction NETWORK_FILE_NAME

To do robustness test: java RobustnessTest NETWORK_FILE_NAME

## Todo List
* Add auto-compile script and pre-compiled jar file
* Add comprehensive comments for source codes
* May add support for other models

## Report bugs or future improvements
If you find bugs or you want some functions to be added in this software package, please raise a issue on the GitHub page of this project. I'll respond as soon as possible.
