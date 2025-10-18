<p align="center"><img src="https://raw.githubusercontent.com/SuperScary/General-Mechanics/refs/heads/master/src/main/resources/logo.png" alt="Logo"></p>

<h1 align="center">General Mechanics</h1>
<p align="center">General Mechanics: Realistic technology for the modern era.</p>

## Developers
To add General Mechanics to your project as a dependency, add the following to your `build.gradle`:
```groovy
repositories {
    maven {
        name = 'GENLAB Maven'
        url = 'https://maven.genlab.com'
        content {
            includeGroup 'general.mechanics'
        }
    }
}
```
Then, you can add it as a dependency, with `${mc_version}` being your Minecraft version target and `${gtm_version}` being the version of General Mechanics you want to use.
```groovy
dependencies {
	// NeoForge
	implementation "general.mechnics:gm-${mc_version}:${version}"

	// Architectury
	modImplementation "general.mechnics:gm-${mc_version}:${version}"
}
```

### IDE Requirements (it is recommended to use IntelliJ IDEA Community Edition or higher)
- Oracle JDK 21
- [Project Lombok plugin](https://plugins.jetbrains.com/plugin/6317-lombok)
- [Minecraft Development plugin](https://plugins.jetbrains.com/plugin/8327-minecraft-development) is recommended.