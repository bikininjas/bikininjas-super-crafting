# Super Crafting — Documentation du Mod

## Concept

**Fusion craft :** combine 9 items identiques d'un même type pour créer une version
"super" améliorée du même item. Progression en 5 tiers.

---

## Hiérarchie des Tiers

| Tier | Matériau Source | Durabilité | Dégâts Bonus | Speed | EnchantValue |
|------|----------------|------------|-------------|-------|-------------|
| Iron Plus | Fer | 500 | 4.0 | 7.0 | 15 |
| Gold Plus | Or | 750 | 5.5 | 8.0 | 20 |
| Diamond Plus | Diamant | 1200 | 7.0 | 9.0 | 18 |
| Netherite Plus | Netherite | 1800 | 9.0 | 10.0 | 22 |
| Ultimate | Bloc de Netherite | 3000 | 12.0 | 12.0 | 25 |

---

## Items du Mod

### 30 Outils / Armes (5 tiers × 6 types)

Chaque type existe en 5 tiers :

- **Épée** (`super_iron_sword`, `super_gold_sword`, ..., `ultimate_sword`)
- **Pioche** (`super_iron_pickaxe`, ..., `ultimate_pickaxe`)
- **Hache** (`super_iron_axe`, ..., `ultimate_axe`)
- **Pelle** (`super_iron_shovel`, ..., `ultimate_shovel`)
- **Houe** (`super_iron_hoe`, ..., `ultimate_hoe`)

Chaque item a :
- Statistiques du tier correspondant (via `SuperTier`)
- `isFoil() = true` (effet enchanté)
- `.fireResistant()` (sauf Ultimate qui l'a aussi)

### 20 Armures (5 tiers × 4 types)

- Casque, Plastron, Jambières, Bottes pour chaque tier
- Matériau d'armure (`SuperArmorMaterial`) avec défenses progressives
- Son `ARMOR_EQUIP_NETHERITE` pour tous

Défenses totales par tier : Iron=15, Gold=20, Diamond=18, Netherite=22, Ultimate=31

### 5 Nourritures (recettes exotiques)

| Item | Nutrition | Sat | Effets | Tier |
|------|-----------|-----|--------|------|
| Super Apple | 8 | 1.2 | Régénération II, Absorption I | Iron |
| Super Carrot | 10 | 1.6 | Night Vision, Speed | Gold |
| Super Melon | 12 | 2.0 | Absorption III, Résistance | Diamond |
| Super Chorus | 8 | 1.0 | Slow Falling II, Régénération III | Netherite |
| Ultimate Feast | 20 | 4.0 | Régénération V, Résistance III, Fire Res, Absorption V | Ultimate |

---

## Crafting

### Fusion (9→1) — Tous outils/armes

Registrées via `FusionRecipeManager` :
- **Tier 0** : 9 items vanilla → 1 item Super du même type
  - Ex: 9 épées en fer → 1 Super Iron Sword
- **Tier N→N+1** : 9 items du tier N → 1 item du tier N+1
  - Ex: 9 Super Iron Swords → 1 Super Gold Sword

Soit 40 recettes shapeless (8 chaînes × 5 tiers).

### Exotiques (shaped 3×3) — Nourritures

Registrées via `ExoticRecipeManager` :
- Super Apple : pommes + nether star + larmes de ghast
- Super Carrot : carottes + perle de l'Ender + œil d'araignée
- Super Melon : melon + éclat de prismarine + membrane de phantom
- Super Chorus : chorus + blaze powder + obsidienne
- Ultimate Feast : tout ce qui précède + bloc de netherite

### Enchantements au craft

`FusionRecipeManager.registerOne()` applique via `ModItems.createEnchantedStack()` :
- Épées : Sharpness, Fire Aspect, Looting, Sweeping Edge (progressifs)
- Outils : Efficiency, Fortune, Unbreaking (progressifs)
- Armures : Protection, Fire/Blast/Projectile Protection (progressifs)
- Les enchantements ne s'appliquent PAS dans la recette shapeless elle-même
  (impossible via vanilla recipe). Appliqués PAR LE CODE au craft via
  `CraftingEvent` / `PlayerEvent.ItemCraftedEvent`.

---

## Structure du Code

```
mod-super-crafting/
├── FEATURES.md                    ← ce fichier
├── build.gradle
├── settings.gradle
├── gradle.properties
├── neoforge.mods.toml
├── src/
│   ├── main/
│   │   ├── java/com/bikininjas/supercrafting/
│   │   │   ├── SuperCraftingMod.java          @Mod class
│   │   │   ├── item/
│   │   │   │   ├── SuperTier.java             enum 5 tiers
│   │   │   │   ├── SuperArmorMaterial.java    ArmorMaterial × 5
│   │   │   │   ├── ModItems.java              30 outils + 20 armures
│   │   │   │   ├── ModArmor.java              (optional, split)
│   │   │   │   └── ModFoods.java              5 nourritures
│   │   │   └── recipe/
│   │   │       ├── FusionRecipeManager.java   40 recettes fusion
│   │   │       └── ExoticRecipeManager.java   5 recettes exotiques
│   │   ├── resources/
│   │   │   ├── assets/super_crafting/
│   │   │   │   ├── models/item/*.json         modèles 50 items + 5 foods
│   │   │   │   └── textures/                  (utilisation textures vanilla)
│   │   │   └── data/super_crafting/
│   │   │       └── recipes/                   (généré par code)
│   │   └── templates/META-INF/neoforge.mods.toml
│   └── test/java/com/bikininjas/supercrafting/unit/
│       └── SuperTierTests.java
```

---

## Problèmes Connus

1. **MappedRegistry.bindTags NPE** — Les items sans tags font planter NeoForge
   au chargement de l'écran Singleplayer.
   → Solution : générer des fichiers de tags via datagen ou corriger l'init.

2. **DeferredRegister dupliqué pour ITEM** — `ModItems.ITEMS` et `ModFoods.FOODS`
   sont deux `DeferredRegister<Item>` distincts. Fonctionnel mais à simplifier.

3. **Modèles texture** — Tous les items pointent vers des textures vanilla
   (`minecraft:item/iron_sword`, etc.). Pas de textures customs.

---

## À Faire (refactor)

- [ ] Consolider en UN SEUL `DeferredRegister<Item>` (fusionner ModFoods → ModItems)
- [ ] Ajouter datagen pour les tags d'item (vide si nécessaire)
- [ ] Vérifier la compatibilité du `ServerAboutToStartEvent` pour les recettes
- [ ] Ajouter textures customs (optionnel)
- [ ] Ajouter un son custom pour le craft fusion
- [ ] Ajouter une progression (advancement) pour chaque tier
