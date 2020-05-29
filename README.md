# BucikTitles
[![Codacy Badge](https://app.codacy.com/project/badge/Grade/9d033d4b159d40c6a359a9f53ebc8778)](https://www.codacy.com/manual/workonfire/BucikTitles?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=workonfire/BucikTitles&amp;utm_campaign=Badge_Grade)
![Last Commit](https://img.shields.io/github/last-commit/workonfire/BucikTitles)
![License](https://img.shields.io/github/license/workonfire/BucikTitles)
![Spigot Rating](https://img.shields.io/spiget/rating/79009)

A very simple plugin for showing titles on chat above players heads that depends on [TAB](https://github.com/NEZNAMY/TAB).

BucikTitles supports multiple GUI pages. One page can contain up to 28 titles.

## Features
-   Titles above player heads (above or below nickname)
-   Chat prefixes
-   [PlaceholderAPI](https://www.spigotmc.org/resources/placeholderapi.6245/) support
-   Custom permissions
-   GUI pages
-   Textured heads as GUI elements
-   Temporary titles (title removal on player quit)

## Permissions
-   `bucik.titles.reload`
-   `bucik.titles.open`
-   `bucik.titles.debug`
-   `bucik.titles.get`
-   `bucik.titles.clear`

## Commands
`/titles [clear|get|info|reload]`

## Screenshots
![Screenshot 1](https://i.imgur.com/qu5rhT3.png)
![Screenshot 2](https://i.imgur.com/98AMdNY.png)
![Screenshot 3](https://media.discordapp.net/attachments/710596678486327298/713148507078524969/ezgif.com-video-to-gif_1.gif)
![Screenshot 4](https://i.imgur.com/Ct4Kfen.png)
![Screenshot 5](https://i.imgur.com/Qe52DAq.png)

### Example titles setup (titles.yml)
```yaml
titles:
  pages:
    1:
      wzium:
        title: "&9[&3&l&oW&b&lZ&f&l&oIU&b&l&oU&3&l&oM&9]"
        permission: "bucik.titles.title.wzium"
        gui-item:
          material: FEATHER
          amount: 5
          lore:
          - "&6Róbmy wzium!"
      testowy:
        title: "&6[&e&l&oTestowy&6]"
        permission: "bucik.titles.title.testowy"
        gui-item:
          material: BEDROCK
    2:
      animated:
        title: "%animations_Rainbow_Hello!%"
        permission: "bucik.titles.title.animated"
        gui-item:
          material: WOODEN_AXE
          lore:
          - "Animated!"
      test_title:
        title: "&9[&f&l&oTestTitle&9]"
        permission: "bucik.titles.title.test_title"
        gui-item:
          material: PLAYER_HEAD
          name: "&9[&f&l&oDIFFERENT NAME&9]"
          texture: "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNDA4MGJiZWZjYTg3ZGMwZjM2NTM2YjY1MDg0MjVjZmM0Yjk1YmE2ZThmNWU2YTQ2ZmY5ZTljYjQ4OGE5ZWQifX19"
```
You can customize more options such as language and title placement in [config.yml](src/main/resources/config.yml)

This is one of my first plugins, so feel free to create a pull request if you think something can be done better.
