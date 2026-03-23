# Contributing to General Mechanics

Thank you for considering contributing to General Mechanics! Contributions are what make the modding community such an amazing place to learn, inspire, and create. Any contributions you make are **greatly appreciated**.

To maintain a consistent and high-quality codebase, we ask that you follow these guidelines.

## Table of Contents
- [How Can I Contribute?](#how-can-i-contribute)
    - [Reporting Bugs](#reporting-bugs)
    - [Suggesting Enhancements](#suggesting-enhancements)
    - [Pull Requests](#pull-requests)
- [Building the Mod](#building-the-mod)
- [Code Style](#code-style)
- [Localization](#localization)

## How Can I Contribute?

### Reporting Bugs

If you encounter any issues, please check the existing issues to ensure it hasn't already been reported. If you are not on the latest version, it may have already been fixed.

When reporting a bug, please include:
- A clear and concise description of the issue.
- Steps to reproduce the behavior.
- The version of General Mechanics you are using.
- The version of Minecraft and NeoForge you are using.
- Screenshots or logs if applicable.

You can report issues [here](https://github.com/iso2t/General-Mechanics/issues/new/choose).

### Suggesting Enhancements

If you have a great idea for a new feature or improvement, feel free to open an issue to discuss it first. We want to avoid wasting your time on something that might not align with the project's goals.

### Pull Requests

1. **Open an Issue**: Before adding any major changes, please open an issue to discuss them first.
2. **Fork the Repository**: Create your own fork of the project.
3. **Create a Branch**: Use a descriptive branch name (e.g., `feature/awesome-new-feature` or `fix/bug-description`).
4. **Implement Your Changes**: Write clean, efficient code that follows the existing style.
5. **Test Your Changes**: Ensure everything works as expected.
6. **Submit a PR**: Provide a clear description of what your PR does and link to any relevant issues.

## Building the Mod

To set up the development environment and build the mod:
1. Clone the repository: `git clone https://github.com/iso2t/General-Mechanics.git`
2. Build the project using:
   ```cmd
   gradlew runData build
   ```
   The built jar will be located in `build/libs`.

## Code Style

Consistency is key! Please ensure your code matches the existing style of the project. We use Lombok to reduce boilerplate.

- Use meaningful variable and method names.
- Keep methods concise and focused on a single task.
- Follow standard Java naming conventions.
- Please read the [official styleguide](/STYLE_GUIDE.md)

## Localization

General Mechanics uses a string format for localization. If you'd like to help translate, you can find the project on [Crowdin](https://crowdin.com/project/General-Mechanicsmod).

**Note:** Any placeholders must be in the format `%s`. If you encounter one, keep it in the translation.