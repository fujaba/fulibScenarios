const apiUrl = "";

function submit() {
	const text = scenarioInputCodeMirror.getValue();

	api("POST", apiUrl + "/runcodegen", {
		scenarioText: text
	}, function(response) {
		console.log(response.output)
		console.log("exit code: " + response.exitCode)

		let javaCode = '';
		if (response.exitCode != 0) {
			javaCode += response.output.split("\n").map(function(line) {
            				return "// " + line;
            			}).join("\n") + '\n';
		}
		if (response.testMethods) {
			for (testMethod of response.testMethods) {
				javaCode += "// " + testMethod.name + '\n';
				javaCode += testMethod.body;
			}
		}
		javaTestOutputCodeMirror.setValue(javaCode);

		document.getElementById("objectDiagram").innerHTML = response.objectDiagram;
		document.getElementById("classDiagram").innerHTML = response.classDiagram;

		// TODO display errors
	});
}


function api(method, url, body, handler) {
	const requestBody = JSON.stringify(body);
    const request = new XMLHttpRequest();

    request.overrideMimeType("application/json");
    request.addEventListener("load", function () {
        handler(JSON.parse(this.responseText))
    });
    request.open("POST", url, true);
    request.setRequestHeader("Content-Type", "application/json;charset=UTF-8");
    request.send(requestBody);
}

var scenarioInput = document.getElementById("scenarioInput");
var scenarioInputCodeMirror = CodeMirror.fromTextArea(scenarioInput, {
	mode: 'markdown',
	lineNumbers: true,
	lineWrapping: true,
	styleActiveLine: true,
	extraKeys: {
		'Ctrl-Enter': submit,
		'Cmd-Enter': submit
	}
});

var javaTestOutput = document.getElementById("javaTestOutput");
var javaTestOutputCodeMirror = CodeMirror.fromTextArea(javaTestOutput, {
	lineNumbers: true,
	matchBrackets: true,
	readOnly: true,
	mode: 'text/x-java'
});
