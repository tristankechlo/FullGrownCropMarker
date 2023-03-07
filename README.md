# Full Grown Crop Marker

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
    - all crops that extend `net.minecraft.world.level.block.CropsBlock`

### Credits

This mod is based on the `Fully Aged Crop Marker` by [Vanilla Tweaks](https://vanillatweaks.net/)  
Instead of adding custom json-models for vanilla crops, this mod adds a marker to all (vanilla/modded) crops that are at
their maximum age.
