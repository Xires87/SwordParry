# SwordParry
Lets you parry (and block) melee attacks and projectiles with sword or axe.
Shields can parry too.


# [Page on CurseForge](https://www.curseforge.com/minecraft/mc-mods/sword-parry)

------------------------------------------------------------------------------------------------------------

# How to add blocking animation for sword or axe from your mod:


<b>In resources/assets/<i>modid</i>/models/item/ add a json file containing:</b>
```json
{
  "parent": "modid:item/item_youre_adding_animation_for",
  "display": {
    "firstperson_righthand": {
      "rotation": [ -40, -100, 25 ],
      "translation": [ -3.13, 3.2, 1.13 ],
      "scale": [ 0.68, 0.68, 0.68 ]
    }
  }
}
```

<b>Then, in your weapons model file, add one of these:</b>

For swords:
```json
  "overrides": [
    {
      "predicate": {
        "parry": 1
      },
      "model": "modid:item/name_of_file_youve_created"
    }
  ]
  ```
For axes:
```json
  "overrides": [
    {
      "predicate": {
        "axeparry": 1
      },
      "model": "modid:item/name_of_file_youve_created"
    }
  ]
  ```
  
  ----
<b>Your file should look like this (copper_sword.json from Frycmod):</b>
```json
{
  "parent": "item/handheld",
  "textures": {
    "layer0": "frycmod:item/copper_sword"
  },
  "overrides": [
    {
      "predicate": {
        "parry": 1
      },
      "model": "frycmod:item/copper_sword_parry"
    }
  ]
}
```

That's all. Your weapon should now have the blocking animation.
