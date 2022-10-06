# SwordParry
Lets you parry (and block) melee attacks and projectiles with sword.
Shields can parry too.

Block:

When your off hand is empty, you can use sword to block attacks.

Blocking with sword isn't as effective as blocking with shield:

you take only 50% less damage from melee attacks and 10% less damage from projectiles when blocking
succesfull block (or parry) interrupts block action and sets 1 second of cooldown
explosives can't be blocked (or parried) with sword
 

Parry:

Using block right before taking a hit counts as parry:

parrying attack or projectile negates damage
parrying melee attack knocks back and slows enemy
exlposives can't be parried
 

The 5-tick block delay from vanilla is removed in this mod (first 5 ticks of block now count as parry)

 

Cooldown:

There is 1 second cooldown when you interrupt block action.

------------------------------------------------------------------------------------------------------------

!!! Blocking works only for swords (instanceof SwordItem) !!!

How to add blocking animation for sword from your mod:

In resources/assets/modid/models/item/ add a file containing:
```
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
--------------------------------------------------------------
Then, in your swords file, add this:
```
  "overrides": [
    {
      "predicate": {
        "parry": 1
      },
      "model": "modid:item/name_of_file_youve_created"
    }
  ]
  ```
--------------------------------------------------------------
Your file should look like this (copper_sword.json from Frycmod):
```
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
---------------------------------------------------------------
That's all. Your sword should now have the blocking animation.
