# PlayerVsPlayer Mindustry Client Mod
This is one out of three respositories: [PlayerVsPlayer Mindustry Server Plugin](https://github.com/0xPuddi/PlayerVsPlayer-Mindustry-Server-Plugin), PlayerVsPlayer Mindustry Client Mod and [PlayerVsPlayer Smart Contracts](https://github.com/0xPuddi/PlayerVsPlayer-Smart-Contracts).

This project offers the possibility for players to connect on PvP Mindustry matches and to bet on them.

This project has been developed using a Java Mindustry mod template that works on Android and PC. The Kotlin version of this mod can be seen [here](https://github.com/Anuken/MindustryKotlinModTemplate).

[Metamask does not yet support ERC-681](https://github.com/MetaMask/metamask-mobile/issues/8308): Project development is paused.

## PlayerVsPlayer Mindustry Client Mod Documentation

The project build can be found inside `./build/libs`. To add it to a Mindustry client version simply move it inside `~/Library/Application Support/Mindustry/mods` for Mac and for Windows `%HOMEPATH%\AppData\Roaming\Mindustry\mods`, then restart the game and it should load with the mod installed.

The Client mod uses as entry `./src/PlayerVsPlayer.java`.

All ui components and Menus are stored inside `/ui`, networking with the server is handled inside `/net` and `/blockchain` has the logic to interact with the PlayerVsPlayer Smart Contract.

### Usage

Be sure to Install JDK **17** and make it the current java path.

To build the project run:

```bash
make gbuild
```

or

```bash
./gradlew jar
```

Your mod jar will be in the `build/libs` directory.

To refresh gradle dependencies:

```bash
make grefresh
```

To check gradle dependencies:

```bash
make gcheck
```
