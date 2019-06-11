

var counter = 0;

const nextStep = function (event) {
        if (stepList.length > counter) {
            stepList[counter]();
        }
        counter++;
        document.getElementById('output').innerHTML = counter;

    }

document.getElementById('ui').addEventListener ('keyup',
    nextStep);

document.getElementById('ui').onclick = nextStep;
