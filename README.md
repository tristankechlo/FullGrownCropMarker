# Full Grown Crop Marker
[![Curseforge](https://img.shields.io/curseforge/dt/834457?style=for-the-badge&logo=curseforge&color=e05d44)](https://www.curseforge.com/minecraft/mc-mods/full-grown-crop-marker)&nbsp;
[![Modrinth](https://img.shields.io/modrinth/dt/zB8NzHon?style=for-the-badge&logo=modrinth&color=e05d44)](https://modrinth.com/mod/full-grown-crop-marker)&nbsp;
[![Discord](https://img.shields.io/discord/639540436524072970?style=for-the-badge&logo=discord&logoColor=fff&label=%20&color=0a48c4)](https://discord.gg/bhUaWhq)

Adds a little marker over all crops that are at their maximum age!

## Features

- adds a marker to all crops that are at their maximum age
- each crop can have its own marker with different options
    - configurable marker position
        - currently supported: `top`, `none`
    - configurable marker color
        - currently supported: `green`, `red`, `blue`, `yellow`, `orange`, `purple`
    - option to make the marker animated (WIP)

## Screenshots

![FullGrownCropMarker](https://i.ibb.co/gD7GDSB/Full-Grown-Crop-Marker.png)

## Supported Crops

- Vanilla:
    - `minecraft:wheat`
    - `minecraft:carrots`
    - `minecraft:potatoes`
    - `minecraft:beetroots`
    - `minecraft:nether_wart`
- Modded:
    - all crops that extend `net.minecraft.block.CropsBlock`

### Credits

This mod is based on the `Fully Aged Crop Marker` by [Vanilla Tweaks](https://vanillatweaks.net/)  
Instead of adding custom json-models for vanilla crops, this mod adds a marker to all (vanilla/modded) crops that are at
their maximum age.
