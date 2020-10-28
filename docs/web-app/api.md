---
description: This page lists the various API endpoints provided by the fulib.org web app.
---

# API

## `POST /runcodegen` - Compile and Run a Scenario

This endpoint generated the test methods, class model and object diagram for a given scenario. Request and response bodies are JSON objects containing the keys shown below.

### Request

```javascript
{
  "scenarioFileName": "string", // The file name to use for the scenario. Must have the ".md" extension.
  "packageName": "string", // The package name to use for the scenario file. Must be a dot-separated string of identifiers.
  "scenarioText": "string", // The full scenario text, including headings. Will become the content of the .md file named by the scenarioFileName.
}
```

### Response

```javascript
{
  // non-zero indicates a failure
  // somewhere in the compiler pipeline
  "exitCode": 0,
  // includes output from scenarioc, javac,
  // and junit (test run)
  "output": "",
  "classDiagram": "<svg ...>...</svg>",
  "objectDiagrams": [
    {
      "name": "diagram1.svg",
      // svgs and other text formats are pasted as is
      "content": "<svg ...>...</svg>"
    },
    {
      "name": "diagram2.png",
      // pngs are base64-encoded
      "content": "iVBORw0KGgoAAAANS..."
    }
  ],
  "testMethods": [
    {
      "className": "org.example.MyScenario",
      // includes return type and parameters
      "name": "void test()",
      "body": "Player player = ...;\n...;",
    },
    // also includes user-defined methods
    // in model classes
    {
      "className": "org.example.Player",
      "name": "void init()",
      "body": "..."
    }
  ]
}
```

## `POST /projectzip` - Pack a Project as a Zip File

Packages a scenario and all required files to build a ready-made Gradle project into a Zip file.

### Request

```javascript
{
  "scenarioFileName": "string" // Same as in POST /runcodegen.
  "packageName": "string" // Same as in POST /runcodegen. Also doubles as the group name in build.gradle.
  "projectName": "string" // The name to use for the Gradle project. Will end up in settings.gradle.
  "projectVersion": "string" // The version to use for the Gradle project, will be written to build.gradle.
  "scenarioText": "string" // Same as in POST /runcodegen	
}
```

### Response

A zip file with the following files:

```
.gitignore
gradlew
gradlew.bat
gradle/wrapper/gradle-wrapper.jar
gradle/wrapper/gradle-wrapper.properties
settings.gradle
build.gradle
src/main/scenarios/<packageName>/<scenarioFileName>
```
