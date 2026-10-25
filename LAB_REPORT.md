# Lab Report, Code Quality and Design Quality

Repository URL:

## Tools

| Item | Version or name |
| --- | --- |
| JDK | |
| Maven | |
| IDE | |
| SonarQube for IDE | |
| AI tool and exact model | |

## Task 1. SonarQube on the starter code

Commit of the starter, before any change: 8a9b43b 

Table 1. SonarQube issues in the starter code

| No. | Rule | File:line | Message |
| --- | --- | --- | --- |
| 1 | java:S106 | Main.java:9 | Replace this use of System.out by a logger. |
| 2 | java:S106 | Main.java:15 | Replace this use of System.out by a logger. |
| 3 | java:S106 | Main.java:17 | Replace this use of System.out by a logger. |
| 4 | java:S106 | Main.java:22 | Replace this use of System.out by a logger. |

## Task 2. AI review of the starter code

| Item | Value |
| --- | --- |
| Date | 2026-10-25 |
| AI tool and exact model | Codex: GPT-5.6 Sol |
| Commit reviewed | 8a9b43b |
| How the AI read the files (attached the files, opened the folder, or pasted the files) | Opened the project folder in VS Code; Codex accessed the Java source files in the workspace |
| Output file in the reviews folder | reviews/parcelpoint_ai_review.txt (to be created) |

Table 2. All findings on the starter code

| No. | File:line | Finding | Found by (SonarQube, AI or both) | SonarQube rule or course term | Correct? (yes or no, and why) |
| --- | --- | --- | --- | --- | --- |
| 1 | Main.java:8 | Scanner `in` is never closed | AI | Resource leak | Yes, the Scanner is created without being closed. |
| 2 | ParcelService.java:42 | Parcel codes are compared using `==` instead of `.equals()` | AI | Incorrect String comparison | Yes, `==` compares object references rather than String values, which can prevent a matching parcel from being found. |
| 3 | Main.java:9 | Uses `System.out.println` instead of a logger | SonarQube | java:S106 | Yes, the code prints directly to the console. However, console output may be appropriate for this small command-line program. |

2.1 Name two problems that only the AI review found.
Two problems found only by the AI review:
1. ParcelService.java:42 uses `==` instead of `.equals()` to compare Strings. This can cause the program to fail to find a parcel even when the codes match.
2. ParcelService.java:80 contains an empty catch block. This hides file-writing errors and makes problems difficult to detect.

2.2 Name one problem that only SonarQube found, or write none.
One problem found only by SonarQube:
SonarQube detected the use of `System.out.println()` instead of a logger in Main.java (java:S106, lines 9, 15, 17, and 22). The AI review did not mention this issue. However, console output may be appropriate because ParcelPoint is a small command-line application.

2.3 Name one AI finding that was wrong or too vague, and explain why. If every finding was right, explain how you checked them.
One vague AI finding:
The AI reported that the main method in Main.java:6 is relatively complex because it handles input parsing, command selection, and output. However, the AI did not provide a specific measurement of complexity. I checked the method manually and confirmed that it handles several responsibilities, but the severity of this issue is subjective.

## Task 3. Fix and check

Table 3. What you did with each SonarQube issue in Table 1

| No. in Table 1 | Fixed or kept | Commit, or the reason for keeping it |
| --- | --- | --- |
| 1 | | |

Number of SonarQube issues that remain after your fixes:

The two design problems that you fixed

| Design problem (course term) | What you changed | Commit |
| --- | --- | --- |
| | | |
| | | |

Output of the program after your fixes, for the commands `store P1 6501 2`, `pickup P1 5` and `staff`, and the content of parcels.csv:

```text

```

## Task 4. AI-generated poor and good designs

| Item | bikeshare-poor | bikeshare-good |
| --- | --- | --- |
| Date | | |
| AI tool and exact model | | |
| Prompt (B or C as given, or your changed prompt in full) | | |
| Builds with mvn package (yes or no) | | |
| Number of classes, interfaces, enums and records | | |
| SonarQube issues, total | | |
| SonarQube issues, by rule | | |
| AI review findings in Check 1 | | |
| AI review findings in Check 2 | | |
| Review output file in the reviews folder | | |

4.1 Did SonarQube report any cohesion or coupling problem in bikeshare-poor? Quote the rule and the message if it did.

4.2 Did the AI review tell the two versions apart? Give one finding for bikeshare-poor that does not appear for bikeshare-good.

4.3 Which version would you rather extend with a new member type, such as a visitor who pays 4 baht per minute, and why?

## Task 5. Reflection

5.1 What does SonarQube check, and what does it not check? Answer with examples from this lab.

5.2 When should you not trust the AI review? Give an example from this lab.

5.3 What will you do differently in Project Phase 2 after this lab?

## AI usage declaration

| AI tool and exact model | Purpose | Files or parts that it produced or changed | How you checked its output |
| --- | --- | --- | --- |
| | | | |
