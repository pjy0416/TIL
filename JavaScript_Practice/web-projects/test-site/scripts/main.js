let myImage = document.querySelector('img');

myImage.onclick = () => {
    let mySrc = myImage.getAttribute('src');
    if(mySrc === 'images/Liverpool.jpg') { myImage.setAttribute('src', 'images/Gerrard.jpg'); }
    else { myImage.setAttribute('src', 'images/Liverpool.jpg'); }
}

let myBtn = document.querySelector('button');
let myHeading = document.querySelector('h1');

function setUserName() {
    let myName = prompt('Please enter your name.');
    if(!myName || myName === null) {
    	setUserName();
  	} else {
    	localStorage.setItem('name', myName);
    	myHeading.textContent = 'Liverpool KOP, ' + myName;
    }
}

if(!localStorage.getItem('name')) {
    setUserName();
} else {
    let storedName = localStorage.getItem('name');
    myHeading.textContent = 'Liverpool KOP, ' + storedName;
}

myBtn.onclick = () => { setUserName(); }