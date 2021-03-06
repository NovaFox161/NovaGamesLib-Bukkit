# NovaGamesLib

[![Codacy Badge](https://api.codacy.com/project/badge/Grade/21cfa539a1474f59a53c7c15db9d738c)](https://www.codacy.com/app/NovaFox161/NovaGamesLib-Bukkit?utm_source=github.com&utm_medium=referral&utm_content=NovaFox161/NovaGamesLib-Bukkit&utm_campaign=badger)
[![CircleCI](https://circleci.com/gh/NovaFox161/NovaGamesLib-Bukkit.svg?style=svg)](https://circleci.com/gh/NovaFox161/NovaGamesLib-Bukkit)

## About
NovaGamesLib (Bukkit) is a version of the NovaGamesLib plugin which serves as a robust API for minigames on Bukkit and Spigot minecraft servers.

This plugin offers an extensive API and library for Bukkit minigame plugins. NGL handles all of the hard work involved in minigame coding, no longer do you need to spend months on end trying to code a minigame. NGL is designed to be used in any kind of minigame.

NGL is completely modular too! Don't want to use something? No problem, just don't hook into it!

Looking for NovaGamesLib for Bungee Servers? Don't worry! It's in development!

## Current Features
- Robust API for minigames to be built against
- Can handle almost all minigame types.
- Create teams, goals, functions, and more.
- Advanced arena regeneration support
- Fully automated system to offer built in arena restarts.
- Built in kit system/handling (with support for custom names and enchantments).
- Advanced scoreboard API with support for scoreboard animations.
- Built in support for join/quit/spectate signs.
- Enderchest backup support (player inventory backup support coming soon)
- Player minigame stats tracking (All stored in a MySQL database), per minigame.
- Handling of joining/quiting games
- Tons of custom events for you to listen to to control the game and the player. No longer do you need to implement tons of code for something simple, just listen to an NGL event. In fact, NGL events are just like Bukkit events!!!
- Fully fledged timer API for controlling wait delays, start delays, game length, and more.
- Built in rewards handling!! Economy through the Vault API and items (lore and custom values supported!)!!! NGL will cover the tough work for you! Just listen to the events and edit the values as needed, NGL will handle the rest.
- And much much more!!

### Stats Tracking
NovaGamesLib will handle all stats tracking by default if a database is set up reducing your workload. 
 
NGL handles all stats tracking internally! That means that you need not worry about it!!

With integrated stats tracking, NGL can provide and in depth look into how players perform in a minigame. NGL will also allow the lookup of these stats so that your plugin can display in game or, if desired, within a website.
<br> <br>
NGL currently tracks all of the following stats:
- Total kills
- Total deaths
- Total points scored
- Most kills in one game
- Most deaths in one game
- Most points scored in one game
- Least deaths in one game
- Wins
- Loses
- Total games played

## Planned and In-Progress Features
- Game goals and their functions
- Game Types and their functions
- CtF and Flag creation, support, and functionality.
- Player inventory back up (with exp saving)
- Arena GUI selector
- Kit GUI selector
- And more.

## Issues
Should you find an issue with NovaGamesLib, please create a new issue in the Issues pages on this repository or via Dev Bukkit with a proper ticket.

## Plugins Using NovaGamesLib
Add your plugin below or send us a DM letting us know about your awesome new Minigame plugin that utilizes NovaGamesLib!

## Use in Your Minigame Plugin
Implementing NGL is super simple!
Directions on how to use it are provided below (currently only Maven is supported).
### Maven
1. Import into pom.xml by inserting the following (where version is your target version):
    ```xml
       <repository>
           <id>jitpack.io</id>
           <url>https://jitpack.io</url>
       </repository>
    ```
    
    ```xml
       <dependency>
           <groupId>com.github.NovaFox161</groupId>
           <artifactId>NovaGamesLib-Bukkit</artifactId>
           <version>VERSION</version>
       </dependency>
    ```
2. Use the JavaDoc provided here: https://novafox161.github.io/NovaGamesLib-Bukkit/Javadocs/
3. In your main class, in `#OnEnable` add this code (extra code shown for extra help):
    ```java
       public class Main extends JavaPlugin {
           com.cloudcraftgaming.novagameslib.NovaGamesLib ngl = null;
           public void onEnable() {
               //Do stuff...
               
               //Add this code:
               com.cloudcraftgaming.novagameslib.NovaGamesLib novaGamesLib = plugin.getServer().getPluginManager().getPlugin("NovaGamesLib-Bukkit");
               if (novaGamesLib != null) {
                   if (novaGamesLib.getDescription().getVersion().equals("TARGET VERSION")) {
                       ngl = novaGamesLib;
                       //Do stuff.
                   }
               }
               //Do stuff...
           }
       }
    ```
4. And now you can start using NGL in your plugin. Just check the Javadoc for extra help!
   
### Examples and Tutorials
Example code, plugins, and more will soon be provided here.

## Contributing
1. Fork this repo and make changes in your own copy
2. Add a test if applicable and run the existing tests with `mvn clean test` to make sure they pass
3. Commit your changes and push to your fork `git push origin master`
4. Create a new pull request and submit it back to us!

### Deprecation Policy
Due to the way NovaGamesLib-Bukkit is built, the deprecation policy is as follows:
- For Alpha and Beta builds, deprecation may not follow this policy.
- Methods will be marked as deprecated and link to the suggested alternative. These methods will exist until the next Major update (x.y.y). All major versions will be compatible with minor and patch versions under the major.
- Class deprecation will follow the same format as method deprecation.
- All other deprecation is to be handled on a case by case basis.