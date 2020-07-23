<h3>Active Learning : 간단한 달력 만들기</h3>

[링크]([https://developer.mozilla.org/ko/docs/Learn/JavaScript/Building_blocks/%EC%A1%B0%EA%B1%B4%EB%AC%B8](https://developer.mozilla.org/ko/docs/Learn/JavaScript/Building_blocks/조건문))

```javascript
var select = document.querySelector('select');
var list = document.querySelector('ul');
var h1 = document.querySelector('h1');

select.onchange = function() {
    var choice = select.value;
	let days = 31;
	// ADD CONDITIONAL HERE
    if(choice === "February") { days = 28; }
    else if(choice === "April" || choice === "June" || choice === "September" || choice === "November") { days = 30; }
        
    createCalendar(days, choice);
}

function createCalendar(days, choice) {
	list.innerHTML = '';
	h1.textContent = choice;
	for (var i = 1; i <= days; i++) {
		var listItem = document.createElement('li');
        listItem.textContent = i;
    	list.appendChild(listItem);
	}
}

createCalendar(31,'January');
```

```javascript
var select = document.querySelector('select');
var html = document.querySelector('.output');
const white = "white";
const black = "black";
const purple = "purple";
const yellow = "yellow";
const psychedelic = "psychedelic";
const lime = "lime";
const darkGary = "darkgray";

select.onchange = function() {
    var choice = select.value;
    
    // ADD SWITCH STATEMENT
    switch(choice) {
        case white :
            update(white,black);
            break;
        case black :
            update(black,white);
            break;
        case purple :
            update(purple,white);
            break;
        case yellow :
            update(yellow,darkgray);
            break;
        case psychedelic :
            update(lime,purple);
            break;
    }
}

function update(bgColor, textColor) {
    html.style.backgroundColor = bgColor;
    html.style.color = textColor;
}
```

