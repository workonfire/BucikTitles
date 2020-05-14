# BucikTitles

A very simple plugin for showing titles above players heads.
This plugin depends on [TAB](https://github.com/NEZNAMY/TAB).

This plugin supports multiple GUI pages. One page can contain up to 28 titles.
You can set custom permissions for certain titles as well.

### Permissions:
- `bucik.titles.reload`
- `bucik.titles.open`

### Screenshots
![Screenshot 1](https://i.imgur.com/tJ3vFps.png)
![Screenshot 2](https://i.imgur.com/98AMdNY.png)

### Example titles setup
```yaml
titles:
  pages:
    1:
      wzium:
        title: "&9[&3&l&oW&b&lZ&f&l&oIU&b&l&oU&3&l&oM&9]"
        permission: "bucik.titles.title.wzium"
        gui-item:
          material: FEATHER
          name: "&9[&3&l&oW&b&lZ&f&l&oIU&b&l&oU&3&l&oM&9]"
          amount: 1
          lore:
          - "&6Róbmy wzium!"
      testowy:
        title: "&6[&e&l&oTestowy&6]"
        permission: "bucik.titles.title.testowy"
        gui-item:
          material: BEDROCK
          name: "&6[&e&l&oTestowy&6]"
          amount: 1
    2:
      test_title:
        title: "&9[&f&l&oTestTitle&9]"
        permission: "bucik.titles.title.test_title"
        gui-item:
          material: WOODEN_AXE
          name: "&9[&f&l&oTestTitle&9]"
          amount: 2
```
You can customize more options such as language in [config.yml](src/main/resources/config.yml)

This is one of my first plugins, so feel free to create a pull request if you think something can be done better.
