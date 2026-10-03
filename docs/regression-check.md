# Dependency upgrade regression check

This page shows that the toolchain upgrade on `chore/recruiter-ready` did not break any
behavior that the original suite on `main` verified.

Checked on 2026-10-03, Windows 11, JDK 21, Chrome stable, against the
live demo at <https://jqueryui.com/datepicker/>.

## How the evidence was collected

| Side | Source | How it was obtained |
| --- | --- | --- |
| Baseline (`main` @ `7f59dcf`) | Fresh clone, its own wrapper (Gradle 8.13) | `./gradlew clean test aggregate` with JDK 21; JUnit XML + Serenity JSON |
| Baseline history | GitHub Actions on `main` | `gh run list --branch main`: 11 runs, the last 2 green; the 5 failures were all workflow-file edits (`Creacion yml`, JDK switch, etc.), not test failures. Raw logs have expired (HTTP 410), so the outcome per scenario comes from the Serenity report published to GitHub Pages by run `22206908866` (report generated 2026-02-20 01:03): 3 of 3 passing. The report committed under `target/` (2025-09-26) also shows 3 of 3 passing. |
| Upgraded (`chore/recruiter-ready` @ `32b54ab`) | Local clone, run twice | `./gradlew clean test aggregate` with JDK 21 |
| Upgraded CI | GitHub Actions run `37139543725` | 13 passed (6 unit tests + 7 scenarios) |

## Baseline results (original code)

| # | Original scenario | Tag | Local JDK 21 run | Last published CI report |
| --- | --- | --- | --- | --- |
| B1 | Booking an appointment by selecting a date on the current months calendar | `@DataPicker15` | PASS (5.4 s) | PASS |
| B2 | Booking an appointment by selecting a specific date in a different month | `@MonthDifferent` | PASS (3.6 s) | PASS |
| B3 | Blocked field validation in the jQuery calendar datepicker | `@CampBlocked` | PASS (3.1 s) | PASS |

The old setup does run on JDK 21: it compiles with `sourceCompatibility = 16`, and the JUnit 4
`@RunWith(CucumberWithSerenity)` runner runs through the JUnit Vintage engine.

## Upgraded results

| Test | Local run 1 | Local run 2 | CI `37139543725` |
| --- | --- | --- | --- |
| Example #1.1: Selecting today's date | PASS | PASS | PASS |
| Example #1.2: Selecting a mid-month day in the current month | PASS | PASS | PASS |
| Example #1.3: Selecting a day in the next month | PASS | PASS | PASS |
| Example #1.4: Selecting a day in the previous month | PASS | PASS | PASS |
| Example #1.5: Selecting the last day of the current month (month edge) | PASS | PASS | PASS |
| Example #1.6: Selecting the first day of next January (year boundary) | PASS | PASS | PASS |
| Typing a date moves the calendar to that date | PASS | PASS | PASS |
| `CalendarDatesTest` (6 unit tests) | 6/6 PASS | 6/6 PASS | 6/6 PASS |

Both local runs: `BUILD SUCCESSFUL`, 13 tests, 0 failures, 0 errors, 0 skipped. No test was flaky.

## Mapping: original behavior to new coverage

| Original | What it actually asserted | New scenario / example | Baseline | Upgraded |
| --- | --- | --- | --- | --- |
| B1 | Open the field, click day `15` of the shown month; the field equals `MM/15/yyyy` of the current month | Outline example "a mid-month day in the current month" (`current`, `15`): equivalent steps (open the field, page to the month, click the day), same assertion | PASS | PASS |
| B2 | Click "Next", click day `10`; the field equals `MM/10/yyyy` for the next month | Outline example "a day in the next month" (`next`, `10`): equivalent steps (open the field, page to the month, click the day), same assertion | PASS | PASS |
| B3 | See below | Outline current-month examples (`15`, `today`, `last`) plus `@TypeDate` | PASS | PASS |

### What the old "blocked field" scenario (B3) really checked

- **When** "the user enters characters in the calendar field": `SelectDateCalendarDatePickerTask.ingressDate()`
  is a `Task.where(...)` with **no actions**. It types nothing in the browser. It stores
  `datepickerWriteAllowed = false` on a **new, separate** `Actor.named("user")`, not on the actor in the spotlight.
- The check `seeThat(CampDatePickerCalendar.inputDateTexts(), equalTo(false))` reads
  `datepickerWriteAllowed` from the spotlight actor. That actor never stored it, so it gets `null` and returns
  `false`. The assertion always passes and never touches the page.
- **Then** "only the date can be selected from the calendar": it clicks day `17` of the current month and
  checks that the field equals `MM/17/yyyy`. This is the only real browser check in B3.

So B3 never proved that the field is blocked. It cannot prove it either: the jQuery UI demo field accepts
typed input. The upgraded `@TypeDate` scenario types `02/29/2028` and checks that the calendar moves to
February 2028 and highlights day 29.

**Replacement.** The real B3 behavior (pick a current-month day, the field shows it as `MM/dd/yyyy`) is
covered by the current-month Outline examples. They use a different day number (`15`, today, last day)
but the same steps and the same assertion. The trivially-true "blocked" assertion was dropped on purpose,
and `@TypeDate` replaces it with a real check of what the field does with typed input. **No behavior that
the old suite actually verified is left uncovered.**

### Old latent bug, fixed by the new suite

`DateCalculator.dateWithMontDifferent` built the expected date as `LocalDate.of(today.getYear(), today.getMonth().plus(1), day)`.
In December, `plus(1)` gives January but the year stays the same, so B2 would have failed every December.
The new `CalendarDates` uses `plusMonths(1)`, and `CalendarDatesTest.nextMonthCrossesTheYearBoundaryInDecember()`
covers this. The "first day of next January (year boundary)" example covers it in the browser.

## Version changes

| Component | `main` (before) | `chore/recruiter-ready` (after) |
| --- | --- | --- |
| Java (build target) | `sourceCompatibility`/`targetCompatibility` 16 | Toolchain 21 |
| Java (CI) | Temurin 17 | Temurin 21 |
| Gradle wrapper | 8.13 | 8.14.5 |
| Serenity Gradle plugin | 4.2.1 (`buildscript` classpath) | 5.3.9 (`plugins {}` block) |
| serenity-core / -cucumber / -screenplay / -screenplay-webdriver / -ensure | 4.2.1 | 5.3.11 |
| cucumber-junit-platform-engine | 7.16.1 | 7.34.2 |
| junit-platform-launcher | 1.11.0 (`implementation`) | 6.0.3 via `junit-bom` (`testRuntimeOnly`) |
| junit-platform-suite | 1.11.0 | 6.0.3 via `junit-bom` |
| junit-jupiter-engine | 5.11.0 | 6.0.3 via `junit-bom` |
| junit-vintage-engine | 5.11.0 | removed (no JUnit 4 runner left) |
| logback-classic | 1.2.10 | 1.5.38 |
| assertj-core | 3.23.1 | removed (unused; assertions use Serenity `Ensure`) |
| Gradle plugins `eclipse`, `idea` | applied | removed |
| `mavenLocal()` repository | present | removed |
| Test runner | JUnit 4 `@RunWith(CucumberWithSerenity)` | JUnit Platform `@Suite` + `@IncludeEngines("cucumber")` |
| Serenity reporter | Implicit (JUnit 4 runner) | `SerenityReporterParallel` plugin |

## Verdict

No regressions. Every behavior the original suite verified still passes after the upgrade.
