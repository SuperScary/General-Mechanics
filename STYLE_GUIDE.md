# Code Style Guide

To maintain a clean and consistent codebase, please adhere to the following code style guidelines.

## General Principles

- **Clarity over Conciseness**: Code should be easy to read and understand.
- **Consistency**: Follow the existing patterns in the codebase.
- **Lombok**: Use Lombok to reduce boilerplate (e.g., `@Getter`, `@Setter`, `@NoArgsConstructor`).

## Java Style

### Indentation and Spacing
- Use **4 spaces** for indentation.
- Use a single space between keywords and parentheses (e.g., `if (condition)`).
- Use a single space before an opening brace `{`.
- **Vertical Alignment**: In some cases, such as field declarations or registry entries, vertical alignment is used for better readability:
  ```java
  public static final BlockDefinition<MyBlock> MY_BLOCK_BLOCK_DEFINITION = register("...", ...);
  public static final BlockDefinition<ADiffernedBlock> MY_OTHER_BLOCK_DEFINITION   = register("...", ...);
  ```

### Naming Conventions
- **Classes**: `PascalCase` (e.g., `NWallBlock`).
- **Methods**: `camelCase` (e.g., `registerRecipe`).
- **Variables**: `camelCase` (e.g., `resourceFriendly`).
- **Constants/Static Finals**: `UPPER_SNAKE_CASE` (e.g., `REGISTRY`).
- **Mod Prefixes**: Many custom classes use the `N` prefix (e.g., `NBlocks`, `NItems`, `NTab`).

### Formatting
- **Braces**: Use Egyptian brackets (opening brace on the same line as the statement).
- **Blank Lines**:
    - One blank line between methods.
    - Group related fields together, and use blank lines to separate different sections.
- **Imports**: Avoid wildcard imports when possible, except for large libraries where it is already common (e.g., `net.minecraft.world.level.block.*`).

### Annotations
- Use `@Override` when overriding methods.
- Use JetBrains annotations for nullability (`@NotNull`, `@Nullable`) where it helps clarity or is required by the framework.

## Minecraft / NeoForge Specifics

- **Registries**: Use `DeferredRegister` for all registry entries.
- **ResourceLocations**: Use the `GM.getResource()` or `GM.MODID` constants instead of hardcoding the mod ID string.
- **Data Generation**: Follow the established pattern for `IRecipeProvider`, `ITagProvider`, etc.

## Documentation
- Use KDoc/Javadoc for public APIs or complex logic.
- Keep comments meaningful and up-to-date.