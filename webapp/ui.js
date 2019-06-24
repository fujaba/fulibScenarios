

var counter = 0;

const nextStep = function (event) {
        if (event.type === 'keyup') {
           counter --;
        }
        if (stepList.length > counter) {
            stepList[counter]();
        }
        if (event.type !== 'keyup') {
           counter ++;
        }
        console.log (counter);
        // document.getElementById('output').innerHTML = counter;
    }


document.getElementById('ui').addEventListener ('keyup',
    nextStep);

document.getElementById('ui').onclick = nextStep;
