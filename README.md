# fulibScenarios -- ⚠️ DEPRECATED ⚠️

[![Java CI](https://github.com/fujaba/fulibScenarios/workflows/Java%20CI/badge.svg)](https://github.com/fujaba/fulibScenarios/actions)

> [!WARNING]
> fulibScenarios is **deprecated**.
> It only receives security updates because it is an important part of the fulibGradle > fulibScenarios > fulib chain.
> Without it, fulib's `ClassModelDecorator` would not be called.
> A future version of fulib will include the necessary glue to find and call these decorators.

A compiler for textual scenarios.

## Links

- [Web App](https://fulib.org) -- no longer includes fulibScenarios playground

Development
- [Source Code](https://github.com/fujaba/fulibScenarios)
- [Issue Tracker](https://github.com/fujaba/fulibScenarios/issues)

Documentation
- ~~[Language Reference](https://fujaba.gitbook.io/fulib-scenarios/)~~ -- no longer available
- [Docs](https://fulib.org/docs/fulibScenarios/README.md) / [Alternative](https://github.com/fujaba/fulibScenarios/tree/master/docs)

Related Projects
- [fulib.org](https://github.com/fujaba/fulib.org)
- [fulib](https://github.com/fujaba/fulib)
- [fulibTools](https://github.com/fujaba/fulibTools)
- [fulibGradle](https://github.com/fujaba/fulibGradle)

## Installation

> [!NOTE]
> The following snippets are all intended to be placed in your `build.gradle` script.

Install the [fulibGradle plugin](https://github.com/fujaba/fulibGradle):

```groovy
plugins {
    id 'java'
    // https://plugins.gradle.org/plugin/org.fulib.fulibGradle
    id 'org.fulib.fulibGradle' version '0.5.0'

    // ...
}
```

Specify the version by adding the `fulibScenarios` dependency.
Pattern matching requires `fulibTables`.

```groovy
repositories {
   // ...
   mavenCentral()
}

dependencies {
    // ...

    // https://mvnrepository.com/artifact/org.fulib/fulibScenarios
    fulibScenarios group: 'org.fulib', name: 'fulibScenarios', version: '1.7.1'

    // optional (required for pattern matching):
    // https://mvnrepository.com/artifact/org.fulib/fulibTables
    testImplementation group: 'org.fulib', name: 'fulibTables', version: '1.4.0'
}
```

## Usage

Scenario files are intended to be placed in the `src/main/scenarios` or `src/test/scenarios` directories, with the `.md` extension.

The `generateScenarioSource` task will process scenarios in `src/main/scenarios`.
Generated model classes will be placed in `src/main/java`, while generated test classes will be placed in `src/test/java`.

The task `generateTestScenarioSource` processes scenarios in `src/test/scenarios`.
Both model and test classes will be placed in `src/test/java`.

> ⓘ Standard Gradle build tasks have dependencies on these tasks set up.
> As such, you do not have to trigger them manually, running `gradle build` or triggering a build via your IDE is sufficient.

In both cases, the package name depends on the directory the scenario file was in.
For example, if you have the file `src/main/java/org/example/MyScenario.md`, generated classes and tests will use the package `org.example`.

> ⚠︎ If you have scenarios in both `src/main/java` and `src/test/java`, make sure to place them in distinct packages, e.g. `org.example.model` and `org.example.testmodel`.

## License

[MIT](LICENSE.md)
