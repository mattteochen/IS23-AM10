# MyShelfie - IS23AM10
[![Actions Status](https://github.com/mattteochen/IS23-AM10/actions/workflows/CI.yml/badge.svg)](https://github.com/mattteochen/IS23-AM10/actions) ![Coverage](.github/badges/jacoco.svg)

![MyShelfie](.github/img/myshelfie_logo.png)

## The project
This Java project is a porting of the boardgame *MyShelfie* by [Cranio Creations](https://www.craniocreations.it/prodotto/my-shelfie). It was realized as final project of the *Software Engineering* course at [Politecnico di Milano](https://www.polimi.it). 

### The team
**Group 10 (Prof. Alessandro Margara)**
- Alessandro Amandonico ([@aleama01](https://github.com/aleama01))
- Francesco Buccoliero ([@frbuccoliero](https://github.com/frbuccoliero))
- Kaixi Matteo Chen ([@mattteochen](https://github.com/mattteochen))
- Lorenzo Cavallero ([@locavallero](https://github.com/locavallero))

---

## Status of the work
| Functionality | Status | 
| --- | --- |
| Basic rules | :white_check_mark: |
| Complete rules | :white_check_mark: |
| Socket connection | :white_check_mark: |
| RMI connection | :white_check_mark: |
| CLI | :white_check_mark: |
| GUI | :white_check_mark: |
| Multiple games | :white_check_mark: |
| Persistence | :x: |
| Resilience | :white_check_mark: |
| Chat | :white_check_mark: |

**Legend**
| Symbol | Functionality status |
| --- | --- |
| :white_check_mark: | Completed |
| :ballot_box_with_check: | Planned, not started yet | 
| :construction: | Work in progress |
| :x: | Not planned to be implemented |

---

## How to Play

![MyShelfie Box](.github/img/myshelfie_box.png)

Run the app with the following command line arguments:

- `--is-server` to launch the app in server mode. (Default: `false`)
- `--show-gui` to launch the client in GUI mode. If false launches the client in CLI mode. (Default: `false`) 🔶
- `--use-rmi` to launch the client using RMI connection over Socket. (Default: `false`) 🔶
- `--debug` to get verbose output in Client mode. Server mode always has verbose logging. (Default: `false`) 🔶
- `--address [ipv4]` the address where client should look for a server. (Default: `localhost`) 🔶
- `--socket-port [1024-65535]` the port where server should expose the socket/client should connect to. (Default: `9001`)
- `--rmi-port [1024-65535]` the port where server should expose the rmi registry/client should retrieve the stub. (Default: `9002`)
- `--max-connections [0-10]` the maximum number of clients who can connect to the server. (Default: `8`) 🔷
- `--keep-alive [bool]` to set socket keepalive function. (Default: `true`) 🔷
- `--max-player-inactive-ms` to set the max time in milliseconds after a player gets kicked out for inactivity (Default: `120000` =2 min ) 🔷

🔶 = Command only works in client mode. Will be ignored if launched in server mode.

🔷 = Command only works in server mode. Will be ignored if launched in client mode.

---

## Development
Project works fine as a stand-alone Maven project.

### Generate Jacoco test coverage report
To generate test coverage report launch JaCoCo with the following command from `<REPOSITORY_DIRECTORY>/is23am10`:
```
mvn clean jacoco:prepare-agent install jacoco:report
```
or use the button under "Favorites" in your Maven left-side panel if you use the VSCode dev container.

### Generate Javadoc 

Use the command `mvn javadoc:javadoc` to generate the HTML. You'll find the artifact in `target/site/apidocs`.

## Legal

My Shelfie è un gioco da tavolo sviluppato ed edito da Cranio Creations Srl. I contenuti grafici di questo progetto riconducibili al prodotto editoriale da tavolo sono utilizzati previa approvazione di Cranio Creations Srl a solo scopo didattico. È vietata la distribuzione, la copia o la riproduzione dei contenuti e immagini in qualsiasi forma al di fuori del progetto, così come la redistribuzione e la pubblicazione dei contenuti e immagini a fini diversi da quello sopracitato. È inoltre vietato l'utilizzo commerciale di suddetti contenuti.

The software is released under MIT License, see [LICENSE](LICENSE). 

