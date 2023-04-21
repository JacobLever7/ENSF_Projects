

var width = 4; //length of word
var height = 4; //#of guesses

var row = 0;
var col = 0;
var wordList = [];
var word;
var hint;

var gameOver = false;


window.onload = function() {
    initialize();
}

const getDict = async () => {
    let res = await fetch("https://api.masoudkf.com/v1/wordle", {
        headers: {
        "x-api-key": "sw0Tr2othT1AyTQtNDUE06LqMckbTiKWaVYhuirv",
        }
    });
    let json = await res.json();

    let wordList =[];
    let hintList = [];
    
    for (var i = 0; i < json.dictionary.length; i++){
        wordList.push(json.dictionary[i].word)
        hintList.push(json.dictionary[i].hint)
    }
    return {wordList, hintList};
}

function reset() {
    //remove winscreen
    let winscreen = document.getElementById("winner");
    winscreen.innerHTML = "";

    //remove announcement + styling
    announcement.classList = "";

    //enable table
    let board = document.getElementById("board");
    board.classList.remove("remove")
    
    for (let r = 0; r < height; r++){
        for (let c = 0; c < width; c++){
            // <span id="0-0" class="tile"></span>
            let currentTile = document.getElementById(r.toString() + "-" + c.toString());
            currentTile.innerText = "";

            if (currentTile.classList.contains("darkmode")){
                currentTile.classList.remove()
                //remove style classes
                currentTile.className = "tile darkmode";

            }
            else{
                currentTile.classList.remove()
                //remove style classes
                currentTile.className = "tile";
            }
            document.getElementById("button").blur();
        }
    }
    //reset game vars
    gameOver = false;
    row = 0;
    col = 0;

    //get new word
    randomIndex = Math.floor(Math.random() * wordList.length);
        word = wordList[randomIndex].toUpperCase();
        hint = hintList[randomIndex];
        console.log(word);
        
 };

function winscreen() {
    //remove gameboard
    let board = document.getElementById("board");
    board.classList.add("remove")

    //create image
    var image = document.createElement('img');
    image.src = "https://res.cloudinary.com/mkf/image/upload/v1675467141/ENSF-381/labs/congrats_fkscna.gif";
    image.setAttribute("id", "win-img");
    document.getElementById("winner").appendChild(image);

    //add anouncement
    var announcement = document.getElementById("announcement");
    announcement.textContent = "You correctly guessed the word " + word + "!";
    announcement.classList.remove("hint-style");
    announcement.classList.remove("fail-style");
    announcement.classList.add("win-style");
    announcement.classList.add("visible");
}

function failScreen() {
    //add announcement
    var announcement = document.getElementById("announcement");
    announcement.textContent = "You failed to guess the word " + word ;
    announcement.classList.remove("hint-style");
    announcement.classList.remove("win-style");
    announcement.classList.add("fail-style");
    announcement.classList.add("visible");
}

function hintScreen() {
    //add announcemnent
    var announcement = document.getElementById("announcement");
    announcement.textContent = "HINT: " + hint;
    announcement.classList.remove("win-style");
    announcement.classList.remove("fail-style");
    announcement.classList.add("hint-style");
    announcement.classList.toggle("visible");

    //blur button
    document.getElementById("hint").blur();
}

function instructionScreen() {
    var instructions = document.getElementById("instructions");
    instructions.classList.toggle("hidden");

    //blur button
    document.getElementById("instructions-button").blur();
}

function initialize() {
    
    //game board

    for (let r = 0; r < height; r++){
        for (let c = 0; c < width; c++){
            // <span id="0-0" class="tile"></span>
            let tile = document.createElement("span");
            tile.id = r.toString() + "-" + c.toString()
            tile.classList.add("tile");
            tile.innerText = "";
            document.getElementById("board").appendChild(tile)
        }
    }
    
    var startOverButton = document.getElementById("button");
    startOverButton.disabled = true;
    startOverButton.innerText = "Loading...";

    //initialize word
    getDict().then(result => {
        wordList = result.wordList;
        hintList = result.hintList;
        
        //initalize word
        let randomIndex = Math.floor(Math.random() * wordList.length);
        word = wordList[randomIndex].toUpperCase();
        hint = hintList[randomIndex];

        //enable button
        startOverButton.disabled = false;
        startOverButton.innerText = "Start Over";
    });

    //darkmode toggle listener
    const themeToggleButton = document.getElementById("theme_toggle");
    const themeElement1 = document.getElementById("body");
    const themeElementsH1 = document.getElementsByClassName("center");
    const themeElementsIcon = document.getElementsByClassName("invis-clickable");
    const themeElementsFooter = document.getElementsByClassName("footer");
    const themeElementRestartButton = document.getElementById("button");
    const themeElementsSpan = document.getElementsByTagName("span");
    

    themeToggleButton.addEventListener("click", () => {
        themeElement1.classList.toggle("darkmode");
        themeElementRestartButton.classList.toggle("darkmode");

        for(let i = 0; i < themeElementsH1.length; i++){
            themeElementsH1[i].classList.toggle("darkmode")
        }
        for(let i = 0; i < themeElementsIcon.length; i++){
            themeElementsIcon[i].classList.toggle("darkmode")
        }
        for(let i = 0; i < themeElementsFooter.length; i++){
            themeElementsFooter[i].classList.toggle("darkmode")
        }
        for(let i = 0; i < themeElementsSpan.length; i++){
            themeElementsSpan[i].classList.toggle("darkmode")
        }

        //blur button
        document.getElementById("theme_toggle").blur();
    });
    // listen for keypress
    document.addEventListener("keyup", (e) => {
        
        if (gameOver) return;
        //validate input
       
        if ("KeyA" <= e.code && e.code <= "KeyZ"){
            if(col < width){
                let currTile = document.getElementById(row.toString() + "-" + col.toString());
                if (currTile.innerText == ""){
                    currTile.innerText= e.code[3];
                    col += 1;
                }
            }
        }
        else if (e.code == "Backspace"){
            if (0 < col && col <= width){
                col -= 1;
            }
            let currTile = document.getElementById(row.toString() + "-" + col.toString());
            currTile.innerText = "";
        }
        else if (e.code == "Enter"){
            update();
        }

        if (!gameOver && row == height){
            gameOver = true;
            
            //remove announcement + styling
            announcement.classList = "";
            failScreen();
        }
    })
}

function update() {
    let guess = "";

    //make the guess word from input
    for (let c = 0; c < width; c++) {
        let currTile = document.getElementById(row.toString() + '-' + c.toString());
        let letter = currTile.innerText;
        guess += letter;
    }
    
    //alert and return if word is not complete
    if (guess.length != width){
        window.alert("please complete the word");
        return;
    }

    //win condition
    if (guess == word){
        winscreen();
        gameOver = true;
        return;
    }

    //start proccessing
    let correct = 0;
    let letterCount = {};
    //fill map
    for (let i = 0; i < word.length; i++){
        let letter = word[i];
        if (letterCount[letter]){
            letterCount[letter] += 1;
        }
        else {
            letterCount[letter] = 1;
        }
    }
    
    
    for (let c = 0; c < width; c++){
        let currTile = document.getElementById(row.toString() + "-" + c.toString());
        let letter = currTile.innerText;

        //in the correct position?
        if (word[c] == letter) {
            currTile.classList.add("correct");
            correct += 1;
            letterCount[letter] -= 1;
         }
    
        if (correct == width){
            gameOver = true;
        }
    }

    for (let c = 0; c < width; c++){
        let currTile = document.getElementById(row.toString() + "-" + c.toString());
        let letter = currTile.innerText;

        if (!currTile.classList.contains("correct")){
            
            //in the word?
            if (word.includes(letter) && letterCount[letter] > 0){
                currTile.classList.add("within");
                letterCount[letter] -= 1;
            }
            //not in the word
            else {
                currTile.classList.add("missing")
            }
        }
    }
    //next guess
    row += 1;
    col = 0;
}