# Lab Report, Code Quality and Design Quality

Repository URL: https://github.com/wafurinnginkeebonlangka-cell/itcs382-code-design-quality-lab

## Tools

| Item | Version or name |
| --- | --- |
| JDK | Eclipse Temurin 25 |
| Maven | Apache Maven 3.10.0 |
| IDE | Visual Studio Code 1.141.0 (Universal) |
| SonarQube for IDE | 6.2.0 |
| AI tool and exact model | Codex: GPT-5.6 Sol (ParcelPoint review; confirm models used for BikeShare) |

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
| 1 | Kept | Console output is intentional in this command-line application. |
| 2 | Kept | Console output is needed to display the result of storing a parcel. |
| 3 | Kept | Console output is needed to display the pickup fee. |
| 4 | Kept | Console output is needed to display the number of free lockers. |

Number of SonarQube issues that remain after your fixes: 11

The two design problems that you fixed

| Design problem (course term) | What you changed | Commit |
| --- | --- | --- |
| Control coupling | Replaced printParcels(boolean) with separate printStudentParcels() and printStaffParcels() methods, removing the boolean control flag. | 34394f0 |
| Encapsulation | Changed getLockers() to return an unmodifiable list instead of exposing the mutable internal collection. | 34394f0 |

Output of the program after your fixes, for the commands `store P1 6501 2`, `pickup P1 5` and `staff`, and the content of parcels.csv:
Commands: store <code> <studentId> <size 1-3>, pickup <code> <days>, list, staff, quit
store P1 6501 2
SMS to 6501: parcel P1 is in locker 2
Locker 2
pickup P1 5
Fee: 30.0
staff
P1 6501 PICKED_UP
Free lockers: 3
quit

Contents of parcels.csv:
P1,PICKED_UP

```text

```

## Task 4. AI-generated poor and good designs

| Item | bikeshare-poor | bikeshare-good |
| --- | --- | --- |
| Date | 2026-10-25 | 2026-10-25 |
| AI tool and exact model | Codex: GPT-5.6 Sol (confirm) | Codex: GPT-5.6 Sol (confirm) |
| Prompt (B or C as given, or your changed prompt in full) | Prompt B as given, with the BikeShare specification inserted | Prompt C as given, with the BikeShare specification inserted |
| Builds with mvn package (yes or no) | Yes | Yes |
| Number of classes, interfaces, enums and records | 1 class, 0 interfaces, 0 enums, 0 records | 8 classes, 1 interface, 1 enum, 0 records |
| SonarQube issues, total | 12 observed | 2 observed |
| SonarQube issues, by rule | java:S2386 (2), java:S1172 (1), java:S106 (9) | java:S1172 (1), java:S106 (1) |
| AI review findings in Check 1 | 4 | 2 |
| AI review findings in Check 2 | 10 | 7 |
| Review output file in the reviews folder | reviews/bikeshare_bad_ai_review.txt | reviews/bikeshare_good_ai_review.txt |

*Note: SonarQube counts are based on the findings visible in VS Code. The good-design project was not fully recognized by the Java extension, so its count may be incomplete.*

### 4.1 Did SonarQube report any cohesion or coupling problem in bikeshare-poor? Quote the rule and the message if it did.

No. SonarQube reported issues such as `java:S2386` ("Make this member 'protected'") and `java:S106` ("Replace this use of System.out by a logger"), but it did not directly report the poor cohesion or control coupling in the program. The AI review identified these design problems instead.

### 4.2 Did the AI review tell the two versions apart? Give one finding for bikeshare-poor that does not appear for bikeshare-good.

Yes. The AI review reported 14 findings for the poor design and 9 for the good design. For example, the poor design had public static fields that created common coupling because different parts of the program could directly access and modify shared data. This problem did not appear in the good design because its fields were private.

### 4.3 Which version would you rather extend with a new member type, such as a visitor who pays 4 baht per minute, and why?

I would choose bikeshare-good because it uses an enum for member types and separates the fee rules from the menu and file saving. I could add a visitor member type and its fee rate without modifying unrelated parts of the program. In the poor design, member types use integer codes, so I might need to update several places and could easily make mistakes.

## Task 5. Reflection

5.1 What does SonarQube check, and what does it not check? Answer with examples from this lab.
SonarQube checks code quality issues using predefined rules, such as unused fields, unused imports, and using System.out instead of a logger. For example, it detected the unused maxParcels field in ParcelPoint and public static mutable fields in BikeShare. However, it did not directly identify some design problems, such as poor cohesion and control coupling caused by using a boolean parameter to change a method's behavior.

5.2 When should you not trust the AI review? Give an example from this lab.
I should not completely trust the AI review without checking the code myself because some findings can be subjective or incorrect. For example, the AI review of the good BikeShare version reported the unused args parameter in main() as a code quality issue, even though the parameter is part of the standard Java entry-point signature. Therefore, I should verify AI findings before deciding whether to fix them.

5.3 What will you do differently in Project Phase 2 after this lab?
In Project Phase 2, I will pay more attention to software design before implementing the code. I will try to give each class a clear responsibility, keep fields private, and reduce unnecessary dependencies between classes. I will also use SonarQube and AI reviews to identify possible problems, but I will manually check their suggestions before making changes.

## AI usage declaration

## AI usage declaration

| AI tool and exact model | Purpose | Files or parts that it produced or changed | How you checked its output |
| --- | --- | --- | --- |
| Codex: GPT-5.6 Sol | Review ParcelPoint code quality and design quality using Prompt A | reviews/parcelpoint_ai_review.txt | Compared the findings with the Java source code and SonarQube results |
| Codex: [confirm exact model] | Generate a poorly designed BikeShare implementation using Prompt B | bikeshare_bad/pom.xml and bikeshare_bad/src/main/java/bikeshare/Main.java | Ran mvn package and tested renting, returning, fee calculation, and CSV saving |
| Codex: [confirm exact model] | Generate a well-designed BikeShare implementation using Prompt C | bikeshare_good/pom.xml and Java files under bikeshare_good/src/main/java/bikeshare/ | Ran mvn clean package and manually checked student and staff fee calculations and rides.csv |
| Codex: [confirm exact model] | Review both BikeShare implementations using Prompt A | reviews/bikeshare_bad_ai_review.txt and reviews/bikeshare_good_ai_review.txt | Compared the AI findings between versions and checked SonarQube results |
| ChatGPT: GPT-6 | Help interpret lab instructions, guide fixes, and draft report answers | LAB_REPORT.md | Checked the suggested answers against the observed lab results and source code findings |
