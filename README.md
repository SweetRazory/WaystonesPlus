# WaystonesPlus

Welcome to WaystonesPlus's Github page!

## Table of Contents

- [Getting Started](#getting-started)
  - [Prerequisites](#prerequisites)
  - [Installation](#installation)
- [Usage](#usage)
- [Supporting](#supporting)
- [Bug Reports](#bug-reports)
- [Contact](#contact)

## Getting Started

To get started with WaystonesPlus, follow these instructions to install and configure the plugin on your Minecraft server.

### Prerequisites

- Spigot/Bukkit Server (version 1.19.4-1.20.1)

### Installation

1. Download the latest version of WaystonesPlus from [Spigot](https://www.spigotmc.org/resources/110762/), [Modrinth](https://modrinth.com/plugin/waystonesplus) or [BuiltByBit](https://builtbybit.com/resources/29787).
2. Copy the downloaded JAR file into the `plugins` folder of your Minecraft server.
3. Restart the server to load the plugin.

## Usage

### Commands
- /wsp help
- /wsp get [Waystone type] [Name] (Requires waystonesplus.command.get permission)

### Spawn item
Just place it anywhere in the world. (Requires waystonesplus.placewaystone permission)
If you want to place it less than 50 blocks from eachother you need permission for that. (waystonesplus.distanceoverride)


### Waystone
To interact with the Waystone, just right click on any solid blocks the Waystone has. (Requires waystonesplus.interact)
Choose any of the 3 elements from the menu:
- Waystones list, where the player can see GLOBAL and PUBLIC Waystones (To enable seeing PRIVATE Waystones, you need a permission waystonesplus.teleport.private)
- Open settings to the current Waystone (Not implemented yet)
- Change visibility for other players. (Only the Waystone owner, and OP players can do this)

### Permissions

WaystonesPlus utilizes the following permissions:

- `waystonesplus.teleport`: Toggles if the player can teleport to another Waystone from the GUI.
- `waystonesplus.teleport.private`: Allows the player to teleport to Private Waystones.
- `waystonesplus.command.get`: Gives access to the `/waystonesplus` or `/wsp get` command.
- `waystonesplus.rename`: Gives access to rename the Waystone spawner item in an anvil.
- `waystonesplus.breakwaystone`: Gives access for the player to break a Waystone.
- `waystonesplus.breakwaystone.private`: Allows the player to break someone else's Waystone.
- `waystonesplus.craft`: Gives access for the player to craft a Waystone.
- `waystonesplus.interact`: Gives access for the player to interact with a Waystone with left-click, bringing up the Waystone list GUI.
- `waystonesplus.interact.private`: Allows the player to interact with Private Waystones.
- `waystonesplus.placewaystone`: Gives access for the player to place a Waystone.
- `waystonesplus.distanceoverride`: Removes the 50 block radius boundary from the player, allowing them to place multiple Waystones within 50 blocks of each other.

Please make sure to configure these permissions accordingly in your Minecraft server's permission system.

## Supporting
I have a Buy me a Coffee page. If you like my work, and want to support me, you can di that [here](https://www.buymeacoffee.com/sweetrazory).

## Bug Reports

If you encounter any bugs or issues while using WaystonesPlus, we encourage you to report them to us. You can do so by joining our Discord server and posting a bug report in the appropriate channel.

To report a bug, please provide the following information:
- Minecraft version
- WaystonesPlus version
- Steps to reproduce the bug
- Expected behavior
- Any relevant error messages or stack traces

[Discord Link](https://discord.gg/SXAjY6kdDC)

## Contact

If you have any questions, suggestions, or feedback, you can reach out to us on Discord or through the following channels:

- Discord: @sweetrazory

We appreciate your support and thank you for using WaystonesPlus!

