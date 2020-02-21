---
description: This page lists the various API endpoints provided by the fulib.org web app.
---

# API

{% api-method method="post" host="https://www.fulib.org" path="/runcodegen" %}
{% api-method-summary %}
Compile and Run a Scenario
{% endapi-method-summary %}

{% api-method-description %}
This endpoint generated the test methods, class model and object diagram for a given scenario. Request and response bodies are JSON objects containing the keys shown below.
{% endapi-method-description %}

{% api-method-spec %}
{% api-method-request %}
{% api-method-body-parameters %}
{% api-method-parameter name="scenarioFileName" type="string" required=true %}
The file name to use for the scenario. Must have the ".md" extension.
{% endapi-method-parameter %}

{% api-method-parameter name="packageName" type="string" required=true %}
The package name to use for the scenario file. Must be a dot-separated string of identifiers.
{% endapi-method-parameter %}

{% api-method-parameter name="scenarioText" type="string" required=true %}
The full scenario text, including headings. Will become the content of the .md file named by the scenarioFileName.
{% endapi-method-parameter %}
{% endapi-method-body-parameters %}
{% endapi-method-request %}

{% api-method-response %}
{% api-method-response-example httpCode=200 %}
{% api-method-response-example-description %}
The response for a valid scenario.
{% endapi-method-response-example-description %}

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
{% endapi-method-response-example %}

{% api-method-response-example httpCode=400 %}
{% api-method-response-example-description %}
\(Future\) The response for a scenario containing a syntax error.
{% endapi-method-response-example-description %}

```javascript
{
  "exitCode": 4,
  "output": "...",
  "errors": [
    {
      "startPosition": 25,
      "endPosition": 30,
      "lineNumber": 5,
      "columnNumber": 10,
      "code": "syntax.there",
      "message": "invalid symbol, name expected",
    }
  ]
}
```
{% endapi-method-response-example %}
{% endapi-method-response %}
{% endapi-method-spec %}
{% endapi-method %}

{% api-method method="post" host="https://www.fulib.org" path="/projectzip" %}
{% api-method-summary %}
Pack a Project as a Zip File
{% endapi-method-summary %}

{% api-method-description %}
Packages a scenario and all required files to build a ready-made Gradle project into a Zip file.
{% endapi-method-description %}

{% api-method-spec %}
{% api-method-request %}
{% api-method-body-parameters %}
{% api-method-parameter name="scenarioFileName" type="string" required=true %}
Same as in POST /runcodegen.
{% endapi-method-parameter %}

{% api-method-parameter name="packageName" type="string" required=true %}
Same as in POST /runcodegen. Also doubles as the group name in build.gradle.
{% endapi-method-parameter %}

{% api-method-parameter name="projectName" type="string" required=true %}
The name to use for the Gradle project. Will end up in settings.gradle.
{% endapi-method-parameter %}

{% api-method-parameter name="projectVersion" type="string" required=true %}
The version to use for the Gradle project, will be written to build.gradle.
{% endapi-method-parameter %}

{% api-method-parameter name="scenarioText" type="string" required=true %}
Same as in POST /runcodegen
{% endapi-method-parameter %}
{% endapi-method-body-parameters %}
{% endapi-method-request %}

{% api-method-response %}
{% api-method-response-example httpCode=200 %}
{% api-method-response-example-description %}
The result of this endpoint is a project folder archived as a zip file. The following files will be present:
{% endapi-method-response-example-description %}

{% code title="project.zip" %}
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
{% endcode %}
{% endapi-method-response-example %}
{% endapi-method-response %}
{% endapi-method-spec %}
{% endapi-method %}



