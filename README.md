# Study-vs-Scroll-Tracker
A Java-based productivity tracker that helps students monitor their study habits by comparing the time spent studying and doomscrolling.

## Problem
Many students get distracted from studying by doomscrolling through social media. Students may plan to study for a certain amount of time but end up spending a significant portion of that time doomscrolling. This project aims to help students become more aware of their habits by tracking how much time they actually spend studying versus scrolling.

## How It Works
Students enter their desired study duration in minutes and use three timers to track their productivity:

1. Overall Timer: Counts down continuously from the user's desired duration, regardless of whether they are studying or doomscrolling. Once it reaches zero, the program records how much of the originally planned duration was spent studying versus scrolling.
2. Study Timer: Counts down from the user's desired duration but pauses whenever they switch to scrolling mode. It resumes when the user returns to studying and determines when the session is complete.
3. Scrolling Timer: Counts up whenever the user switches to scrolling mode and pauses when they return to studying. This timer has no predetermined time limit.

When the Study Timer reaches zero, the program displays two productivity reports:

- Planned Duration Report: Shows the minutes and percentages spent studying versus doomscrolling during the originally planned study duration.
- Overall Report: Shows the total minutes and percentages spent studying versus doomscrolling throughout the entire session, including time beyond the originally planned duration.


![System diagram](FlowChart.png)

## Tech Stack
- Language: Java
- Libraries: java.util.Scanner, java.util.Timer, java.util.TimerTask
- Development Environment: IntelliJ IDEA
- Data Storage: In-memory storage using variables (no persistent storage)

## Setup
Prerequisites:
Before running the program, download and install the following: 
1. Java Development Kit (JDK): Click this [link](https://www.oracle.com/java/technologies/downloads/) below to download the appropriate version for your computer
2. IntelliJ IDEA: Click this [link](https://www.jetbrains.com/idea/?source=google&medium=cpc&campaign=AMER_en_US_IDEA_Google_Branded&term=intellij%20idea&content=829313526414&gad_source=1&gad_campaignid=9730672721&gbraid=0AAAAADloJziRbHD3P7GGkbA6GoJNd7OLN&gclid=CjwKCAiAzp_YBhAJEiwASf8kHRsJlOuVkK-sqs87xcLDxhfErm-p-tOkfWTsXHyDW5zAhePM1hICmBoCOMAQAvD_BwE) to download and IntelliJ IDEA
3. Git: Click this [link](https://git-scm.com/install/windows) to download and install the appropriate version of git for your operating system


Installation and Setup:
1. Open Terminal and clone the repository: git clone https://github.com/rebeccasandrugu14/Study-vs-Scroll-Tracker.git
2. Navigate to the project directory: cd Study-vs-Scroll-Tracker
3. Open IntelliJ IDEA.
4. Select File, then Open, and choose the Study-vs-Scroll-Tracker folder.
5. Ensure that the installed JDK is selected as the project's SDK
    - Go to File → Project Structure → Project
    - Select the installed JDK and click Apply, then OK
6. Mark the src folder as the Sources Root if it’s not already marked
    - Right-click the src folder
    - Select Mark Directory as
    - Select Sources Root (src folder should appear blue)
7. Open the src folder and locate Main.java
8. Click the green Run button next to the main() method to start the program


## Usage
1. Enter the date & the desired study duration in minutes.
2. Type start to begin the study session.
3. Begin studying while the Overall Timer and Study Timer count down.
4. Switch to scrolling mode by typing B when taking a break. The study timer pauses, while the Scrolling Timer begins tracking time spent doomscrolling. The Overall Timer continues counting down until the planned duration is reached
5. Switch back to studying mode by typing S to resume the Study Timer and pause the Scrolling Timer.
6. Continue until the Study Timer reaches zero.
7. View the Planned Duration Report and Overall Report at the end of the session.
8. Type next to continue to the productivity reflection
9. Enter Yes or No to indicate whether you are satisfied with your productivity scores.
10. Receive a supportive message before the program ends.


Example:
```text
How long are you studying in (minutes): 
1 (user input)
To start timer, type start
start (user input)
Type S to resume studying or B to take scroll break
B (user input)
Scrolling break.
Type S to resume studying or B to take scroll break
S (user input)
Studying resumed.
Type S to resume studying or B to take scroll break
...

Study session completed!


---Planned Duration Report---
Desired duration: 1.00 minutes
Time spent studying: 0.70 minutes
Time spent scrolling: 0.30 minutes
Study percentage: 70.00%
Scroll percentage: 30.00%


---Overall Report---
Total Duration: 1.30 minutes
Time spent studying 1.00 minutes
Time spent scrolling 0.30 minutes
Study percentage: 76.92%
Scroll percentage: 23.08%


Type 'next' to continue to your productivity reflection.
next (user input)


Are you happy with your productivity scores? (Yes or No)
yes
Amazing job! Keep up your positive study habits!
Thanks for using the Study vs. Scroll Tracker!
```

## Roadmap

### Completed Features

- [x] Implement user input for desired study duration
- [x] Create the Overall Timer
- [x] Create the Study Timer
- [x] Create the Scrolling Timer
- [x] Implement pause and resume functionality
- [x] Implement synchronization between timers
- [x] Generate the Planned Duration Report
- [x] Generate the Overall Report
- [x] Implement productivity reflection and supportive messages
- [x] Implement input validation and error handling

### Future Development

- [ ] Save study session history using File I/O
- [ ] Allow users to set scrolling time limits
- [ ] Display productivity trends over time

## Demo

[Video](https://drive.google.com/file/d/1Kgb40yP4PWe8bJru1x6pDlmpMVO0RLF4/view?usp=sharing)


## Author
Rebecca Sandrugu: [GitHub Link](https://github.com/rebeccasandrugu14)
