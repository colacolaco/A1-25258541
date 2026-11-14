# Submission checklist - Assessment 1 (due 16 Nov 2026, 11:59 pm)

## 1. The zip

`A1-25258541.zip` sits next to the project folder (`Desktop\OOP`) and contains
one top-level folder, `A1-25258541/`, holding `src/`, `docs/`, `README.md`,
`.gitignore` and `console-output.txt`. `bin/` and `.git/` are deliberately left
out. Entry names use `/` separators, as the zip format requires, so the folder
structure survives on macOS and Linux. It compiles from a clean extract and
reproduces `console-output.txt` exactly.

## 2. GitHub repository link

- Repository: https://github.com/colacolaco/A1-25258541 (public, so the marker
  can open it without being invited).
- It holds the project in one commit per design step, all authored by
  `colacolaco`.
- Commit as you continue to work on it - the brief wants progress over the
  assessment period, not one commit at the end.
- Paste the repository URL into the Blackboard submission comments.
- To push further commits from this machine, git has to go through the local
  proxy on port 7892 (`git config http.proxy` in the project already does this);
  remove that setting with `git config --unset http.proxy` if the proxy is off.

## 3. WakaTime evidence

- The brief expects `A1-25258541-WakaTime.png` (or `.jpg`/`.pdf`).
- Take a screenshot of the WakaTime dashboard for this project showing the
  logged time, and attach it. Time logged while the repository did not exist yet
  will not be attributed to the project, so check the project name in the
  WakaTime settings.

## 4. Video link

- Record it using `docs/VIDEO-PLAN.md`.
- Upload unlisted and paste the link into the comments.

## 5. GenAI declaration (paste into the comments)

This assessment is GenAI Level 2: AI may be used for research, learning and
debugging, but **not** to write the code, the comments, or the video. The brief
says a student who cannot explain their own code may be interviewed, and a false
declaration is academic misconduct.

If AI tools played a part in producing this submission, the honest statement is:

> I acknowledge that I have used Generative AI tools to complete this assessment
> in accordance with the conditions outlined in the Assessment Brief. I used
> [tool name] to [describe exactly what it did, e.g. generate the initial class
> design and draft source code, which I then reviewed, tested and can explain
> line by line].

If no AI tools were involved at all:

> I acknowledge that I have not knowingly used GenAI to complete this
> assessment.

Only the second statement is accurate if no AI contributed. Do not use it
otherwise.

## 6. Before pressing submit

- [ ] `javac -d bin src/*.java` compiles with no errors or warnings, and
      `java -cp bin AssignmentOne` runs to the end.
- [ ] The zip opens and contains `src/` with every `.java` file.
- [ ] Repository link opens in a private browser window (so it is visible to the
      marker).
- [ ] WakaTime file attached with the exact required name.
- [ ] Video link works in a private browser window.
- [ ] Declaration statement is in the comments.
- [ ] You can explain every class and method without notes - re-read
      `README.md` and the code before the interview risk is real.
