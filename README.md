# Super Crafting

A Minecraft NeoForge mod that adds 5 fusion tiers of super-powered tools, armor, and foods.

## Tiers

| Tier | Level | Color |Durability | Key Feature |
|---|---|---|---|---|
| Iron+ | I | Gray | 500 | Balanced workhorse |
| Gold+ | II | Gold | 500 | Fastest speed (12.0), best enchantability (25) |
| Diamond+ | III | Blue | 1561 | Heavy hitter |
| Netherite+ | IV | Purple | 2031 | Endgame standard |
| ULTIMATE | V | Magenta | 3500 | Transcendent power + special effects |

## Items (55 total)
- 30 Tools (5 tiers × 6 types: sword, pickaxe, axe, shovel, hoe)
- 20 Armor (5 tiers × 4 types: helmet, chestplate, leggings, boots)
- 5 Foods (Super Apple, Super Carrot, Super Melon, Super Chorus, Ultimate Feast)

## Ultimate Effects
- ⚡ Sword: 30% lightning strike
- 💎 Pickaxe: 50% bonus XP
- 🚀 Axe: launches entities
- 🏔 Shovel: silverfish spawn
- 🐔 Hoe: transforms mobs into chickens
- 👁 Helmet: reveals nearby mobs
- 🛡 Chestplate: 25% damage reflect
- 🏃 Leggings: Speed II
- 🌈 Boots: rainbow particle trail

## Auto-Enchant
Crafting any super item automatically applies enchantments:
- Armor: Protection IV, Unbreaking III, Mending
- Tools: Efficiency V, Unbreaking III, Fortune III, Mending

## KubeJS Compatibility
- **50 datapack recipes** (`data/super_crafting/recipe/`) — fully modifiable via KubeJS
- **13 item tags** — reference items by `#super_crafting:iron_plus_tools`, etc.
- Recipes are datapack-only (no Java duplication) — `event.remove()` works correctly

Example KubeJS script:
```javascript
ServerEvents.recipes(event => {
  event.remove({ id: 'super_crafting:fusion_iron_sword' })
  event.shapeless('super_crafting:super_iron_sword', ['#super_crafting:iron_plus_tools', 'minecraft:stick'])
})
```

## Commands (via core-lib)
- `/stats` — view player stats
- `/time` — time manipulation
- `/challenge` — challenge system
- `/kit` — kit management

## Dependencies
- **core-lib** (required) — shared library
- Minecraft 1.21.1, NeoForge


## 🎛️ BikiniConfig Options
Accessible via `/bn` in-game:
- **Ultimate Effects**: Toggle special effects on Ultimate tier items
- **Auto-Enchant on Craft**: Toggle automatic enchantment application when crafting super items

