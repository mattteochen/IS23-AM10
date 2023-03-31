# IS23-AM10
Progetto Ingegneria del Software 2023

[![Actions Status](https://github.com/mattteochen/IS23-AM10/actions/workflows/CI.yml/badge.svg)](https://github.com/mattteochen/IS23-AM10/actions)

![Coverage](.github/badges/jacoco.svg)

## Implemented functionalities status
| Functionality | Status | 
| --- | --- |
| Basic rules | :white_check_mark: |
| Complete rules | :white_check_mark: |
| Socket connection | :construction: |
| RMI connection | :construction: |
| CLI | :ballot_box_with_check: |
| GUI | :ballot_box_with_check: |
| Multiple games | :construction: |
| Persistence | :x: |
| Resilience | :x: |
| Chat | :ballot_box_with_check: |

### Legend
| Symbol | Functionality status |
| --- | --- |
| :white_check_mark: | Completed |
| :ballot_box_with_check: | On the watch, but not started yet | 
| :construction: | Work in progress |
| :x: | Not planned to be implemented |

## Setup
*Note, development outside the docker container is not guaranteed to be working!*

- Open the project with VSCode Developer Docker [Container](https://code.visualstudio.com/docs/devcontainers/containers), hence build the container (for MacOS users `cmd+shift+p` and type "Build Container").
- There are two options to run tests:
  - Use `Java Test Runner` extension: Enable Junit testing with `Java Test Runner` extension that you will find in the tool bar, it will ask you to choose the version to download, select `JUnit Jupiter`.
  - Use Maven test runner embedded in the available plugin.

## Code format
Red Hat auto code formatter is available. Please run `cmd+shift+p` + `Format document with` and select `Red Hat` option before committing.
Universal code format is required to maintain an unique format style.

You can also enable live format compliance check with the `Checkstyle` [extension](https://marketplace.visualstudio.com/items?itemName=shengchen.vscode-checkstyle).
Configuration:
- `cmd+shift+p` + `Set the Checkstyle Version` -> select `built-in`
- `cmd+shift+p` + `Set the Checkstyle Configuration File` -> select `Google's Style`
`Checkstyle` should live check you code format.

## Live bug checks
Sonar lint extensions is available (auto enabled) to detect [issues](https://marketplace.visualstudio.com/items?itemName=SonarSource.sonarlint-vscode) during the development.
Please follow all the best practices.

## Generate Jacoco test coverage report
To generate test coverage report launch JaCoCo with the following command from `<REPOSITORY_DIRECTORY>/is23am10`:
```
mvn clean jacoco:prepare-agent install jacoco:report
```
or use the button under "Favorites" in your Maven left-side panel.
