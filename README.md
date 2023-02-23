# IS23-AM10
Progetto Ingegneria del Software 2023

## Setup
- Open the project with VSCode Developer Docker [Container](https://code.visualstudio.com/docs/devcontainers/containers), hence build the container (for MacOS users `cmd+shift+p` and type "Build Container").
- Configure the `Checkstyle` extensions following [this](https://marketplace.visualstudio.com/items?itemName=shengchen.vscode-checkstyle).
- Enable Junit testing with Test runner extension that you will find in the tool bar, it will ask you to choose the verion to download, select `JUnit`.
	- Configure the `settings.json` adding the folloing
	```
    "java.project.sourcePaths": ["src"],
    "java.project.outputPath": "bin",
    "java.project.referencedLibraries": [
        "lib/**/*.jar"
    ]
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