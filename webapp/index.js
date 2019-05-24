const apiUrl = "";

function submit() {
	const text = scenarioInputCodeMirror.getValue();

	api("POST", apiUrl + "/runcodegen", {
		scenarioText: text
	}, function(response) {
		console.log(response.output)
		console.log("exit code: " + response.exitCode)

		javaTestOutputCodeMirror.setValue(response.testMethods[0].body);
		document.getElementById("objectDiagram").innerHTML = response.objectDiagram;
		document.getElementById("classDiagram").innerHTML = response.classDiagram;

		// TODO support multiple test methods
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
