const apiUrl = "";

function submit() {
	const text = scenarioInputCodeMirror.getValue();
	
	javaTestOutputCodeMirror.setValue("// loading...");

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

const examples = [
	'Definitions', [
		'Simple Definitions',
		'Complex Definitions',
	],
	'Testing', [
		'Expectations',
		'Diagrams',
	],
	'Methods', [
		'Calling',
	],
];

const exampleSelect = document.getElementById('exampleSelect');

for (let i = 0; i < examples.length; i += 2) {
	const groupName = examples[i];
	const groupItems = examples[i + 1];

	const optgroup = document.createElement("optgroup");
	optgroup.label = groupName;

	for (let groupItem of groupItems) {
		const option = document.createElement("option");
		
		option.value = groupName + "/" + groupItem.replace(" ", "");
		option.label = groupItem;
		option.innerText = groupItem;
		
		optgroup.appendChild(option);
	}

	exampleSelect.appendChild(optgroup);
}

function selectExample(value) {
	if (!value) {
		scenarioInputCodeMirror.setValue("// start typing your scenario or select an example using the dropdown above.");
		return;
	}

	const url = "examples/" + value + ".md";
	const request = new XMLHttpRequest();

	scenarioInputCodeMirror.setValue("// loading...");

    request.addEventListener("load", function () {
    	if (this.status == 200) {
    		const text = this.responseText;
        	scenarioInputCodeMirror.setValue(text);	
    	}
    	else {
    		scenarioInputCodeMirror.setValue("// failed to load " + url + ": " + this.status);
    	}
    });
    request.open("GET", url, true);
    request.send();
}

selectExample("")
