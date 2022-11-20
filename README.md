# SwordParry
Lets you parry (and block) melee attacks and projectiles with sword or axe.
Shields can parry too.


https://www.curseforge.com/minecraft/mc-mods/sword-parry
------------------------------------------------------------------------------------------------------------

# How to add blocking animation for sword from your mod:

In resources/assets/modid/models/item/ add a json file containing:
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

Then, in your swords file, add this:
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

Your file should look like this (copper_sword.json from Frycmod):
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

That's all. Your sword should now have the blocking animation.
For axe do the same thing, but under "predicate" add "axeparry" instead of "parry"
