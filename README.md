# IS23-AM10
Progetto Ingegneria del Software 2023

[![Actions Status](https://github.com/mattteochen/IS23-AM10/actions/workflows/CI.yml/badge.svg)](https://github.com/mattteochen/IS23-AM10/actions)

## Setup
*Note, development outside the docker container is not guaranteed to be working!*

- Open the project with VSCode Developer Docker [Container](https://code.visualstudio.com/docs/devcontainers/containers), hence build the container (for MacOS users `cmd+shift+p` and type "Build Container").
- Enable Junit testing with Test runner extension that you will find in the tool bar, it will ask you to choose the verion to download, select `JUnit Jupiter`.
	- Configure the `settings.json` adding the folloing in `.vscode/setting.json` (this local setting will overwrite your global setting.json)
	```
	"java.checkstyle.configuration": "/google_checks.xml",
	"java.configuration.updateBuildConfiguration": "interactive",
	"java.project.sourcePaths": [
        "project/src"
    ],
    "java.project.outputPath": "bin",
    "java.project.referencedLibraries": [
        "lib/**/*.jar"
    ],
    "java.test.config": {
        
    },
    "redhat.telemetry.enabled": false
	```

## Code format
Red Hat auto code formatter is available. Plese run `cmd+shift+p` + `Format document with` and select `Red Hat` option before committing.
Universal code format is required to maintain an unique format style.

You can also enable live format compliance check with the `Checkstyle` [extension](https://marketplace.visualstudio.com/items?itemName=shengchen.vscode-checkstyle).
Configuration:
- `cmd+shift+p` + `Set the Checkstyle Version` -> select `built-in`
- `cmd+shift+p` + `Set the Checkstyle Configuration File` -> select `Google's Style`
`Checkstyle` should live check you code format.

## Live bug checks
Sonar lint extensions is available (auto enabled) to detect [issues](https://marketplace.visualstudio.com/items?itemName=SonarSource.sonarlint-vscode) during the development.
Please follow all the best practises.