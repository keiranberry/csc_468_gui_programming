var hist = new HistoryManager();

function setTheme(theme) {
    document.documentElement.className = theme;
    //save the theme so it doesnt change between pages
    localStorage.setItem('theme', theme);
}

function getTheme() {
    return localStorage.getItem('theme');
}

function applyThemeOnLoad() {
    const theme = getTheme();
    if (theme) {
        setTheme(theme);
    }
}

function createButton(text, className) {
    var button = document.createElement("button");
    button.textContent = text;
    button.classList.add(className);
    button.addEventListener("click", function() {
        var command = new ButtonChangeCommand(button);
        hist.executeAction(command);
    });
    return button;
}


//GRADING: COMMAND
class ButtonChangeCommand {
    constructor(button) {
        this.button = button;
        this.oldText = button.textContent;
        this.oldClassName = button.className;
        this.newText = this.getNewText();
        this.newClassName = this.getNewClassName();
    }

    getNewText() {
        switch (this.oldText) {
            case "Toy":
                return "Pole";
            case "Pole":
                return "House";
            case "House":
                return "Empty";
            case "Empty":
                return "Ramp";
            case "Ramp":
                return "Bed";
            case "Bed":
                return "Toy";
        }
    }

    getNewClassName() {
        switch (this.oldText) {
            case "Toy":
                return "pole";
            case "Pole":
                return "house";
            case "House":
                return "empty";
            case "Empty":
                return "ramp";
            case "Ramp":
                return "bed";
            case "Bed":
                return "toy";
        }
    }

    exec() {
        this.button.textContent = this.newText;
        this.button.className = this.newClassName;
    }

    undo() {
        this.button.textContent = this.oldText;
        this.button.className = this.oldClassName;
    }
}

function addRow() {
    var command = new AddRowCommand();
    hist.executeAction(command);
}

//GRADING: COMMAND
class AddRowCommand {
    constructor() {
        this.treeSimArea = document.getElementById("tree-sim-area");
        this.addedButtons = [];
    }

    exec() {
        for (let i = 0; i < 3; i++) {
            var button = createButton("Empty", "empty");
            this.treeSimArea.appendChild(button);
            this.addedButtons.push(button);
        }
    }

    undo() {
        for (let i = 0; i < 3; i++) {
            var button = this.addedButtons.pop();
            this.treeSimArea.removeChild(button);
        }
    }
}

function deleteRow() {
    var command = new DeleteRowCommand();
    hist.executeAction(command);
}

//GRADING: COMMAND
class DeleteRowCommand {
    constructor() {
        this.treeSimArea = document.getElementById("tree-sim-area");
        this.removedButtons = [];
    }

    exec() {
        for (let i = 0; i < 3; i++) {
            const button = this.treeSimArea.lastChild;
            this.treeSimArea.removeChild(button);
            this.removedButtons.push(button);
        }
    }

    undo() {
        for (let i = 0; i < 3; i++) {
            const button = this.removedButtons.pop();
            this.treeSimArea.appendChild(button);
        }
    }
}


function reset() {
    var command = new ResetCommand();
    hist.executeAction(command);
}

//GRADING: COMMAND
class ResetCommand {
    constructor() {
        this.treeSimArea = document.getElementById("tree-sim-area");
        this.removedButtons = Array.from(this.treeSimArea.children);
    }

    exec() {
        this.treeSimArea.innerHTML = "";
        for (let i = 0; i < 3; i++) {
            var button = createButton("Empty", "empty");
            this.treeSimArea.appendChild(button);
        }
    }

    undo() {
        this.treeSimArea.innerHTML = "";
        for (let button of this.removedButtons) {
            this.treeSimArea.appendChild(button);
        }
    }
}

function allPoles() {
    var command = new AllPolesCommand();
    hist.executeAction(command);
}

//GRADING: COMMAND
class AllPolesCommand {
    constructor() {
        this.treeSimArea = document.getElementById("tree-sim-area");
        this.removedButtons = Array.from(this.treeSimArea.children);
    }

    exec() {
        this.treeSimArea.innerHTML = "";
        for (let i = 0; i < 6; i++) {
            var button = createButton("Pole", "pole");
            this.treeSimArea.appendChild(button);
        }
    }

    undo() {
        this.treeSimArea.innerHTML = "";
        for (let button of this.removedButtons) {
            this.treeSimArea.appendChild(button);
        }
    }
}

function mixed() {
    var command = new MixedCommand();
    hist.executeAction(command);
}

//GRADING: COMMAND
class MixedCommand {
    constructor() {
        this.treeSimArea = document.getElementById("tree-sim-area");
        this.removedButtons = Array.from(this.treeSimArea.children);
    }

    exec() {
        this.treeSimArea.innerHTML = "";
        this.treeSimArea.appendChild(createButton("Empty", "empty"));
        this.treeSimArea.appendChild(createButton("Empty", "empty"));
        this.treeSimArea.appendChild(createButton("Pole", "pole"));
        this.treeSimArea.appendChild(createButton("House", "house"));
        this.treeSimArea.appendChild(createButton("Empty", "empty"));
        this.treeSimArea.appendChild(createButton("House", "house"));
        this.treeSimArea.appendChild(createButton("Pole", "pole"));
        this.treeSimArea.appendChild(createButton("House", "house"));
        this.treeSimArea.appendChild(createButton("Pole", "pole"));
    }

    undo() {
        this.treeSimArea.innerHTML = "";
        for (let button of this.removedButtons) {
            this.treeSimArea.appendChild(button);
        }
    }
}

function saveToServer(data) {
    var saveUrl = window.location.href + 'save';

    fetch(saveUrl, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(data), //send data as json to be saved
    })
    .then(response => {
        if (!response.ok) {
            throw new Error('Failed to save data');
        }
        alert('Data saved successfully!');
    })
    .catch(error => {
        console.error('Error saving data:', error);
    });
}

function getPageData() {
    var buttons = document.querySelectorAll('#tree-sim-area button');
    //make an array from whats on the page
    var buttonData = Array.from(buttons).map(button => ({
        text: button.textContent,
        className: button.className,
    }));
    return buttonData;
}

function save() {
    var pageData = getPageData();
    saveToServer(pageData);
}

function updateUI() {
    document.getElementById("undo-btn").disabled = !hist.canUndo();
    document.getElementById("redo-btn").disabled = !hist.canRedo();
}

function removeURLParameters() {
    const urlWithoutParams = window.location.pathname;
    history.pushState({}, document.title, urlWithoutParams); //update the url to have no params
}

function getServerFile() {
    const fileParam = 'data.json';
    fetch(`/load?file=${encodeURIComponent(fileParam)}`, {
        cache: 'no-store'
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('Failed to load data from server');
            }
            return response.text(); //get response data
        })
        .then(data => {
            //send user to main with data in the url
            const mainHtmlURL = `/main.html?data=${encodeURIComponent(data)}`;
            window.location.href = mainHtmlURL;
        })
        .catch(error => {
            console.error('Error loading data from server:', error);
        });
}

function loadLocalFile() {
    const filePicker = document.getElementById('file-picker');
    const file = filePicker.files[0];
    if (!file) {
        console.error('No file selected');
        return;
    }

    const formData = new FormData();
    formData.append('file', file); //append the file to formdata

    fetch('/load-local', {
        method: 'POST',
        body: formData 
    })
    .then(response => {
        if (!response.ok) {
            throw new Error('Failed to load file from server');
        }
        return response.text(); //get data from response
    })
    .then(data => {
        //send user to main with data in url
        const mainHtmlURL = `/main.html?data=${encodeURIComponent(data)}`;
        window.location.href = mainHtmlURL;
    })
    .catch(error => {
        console.error('Error loading file from server:', error);
    });
}

function downloadServerFile() {
    window.location.href = '/download';
}


//get params from url
const urlParams = new URLSearchParams(window.location.search);
const data = urlParams.get('data');

document.addEventListener('DOMContentLoaded', function () {
    if (data) { //if there is data to fill the page with
        const parsedData = JSON.parse(data); //convert the json to an array
        const treeSimArea = document.getElementById('tree-sim-area');
        treeSimArea.innerHTML = ''; // empty the sim area
        parsedData.forEach(button => { // fill in the sim area
            const buttonElement = createButton(button.text, button.className);
            treeSimArea.appendChild(buttonElement);
        });
    }

    if (window.location.pathname == "/files.html"){
        const filePicker = document.getElementById("file-picker");

        //prevent default behavior for drag and drop
        ['dragenter', 'dragover', 'dragleave', 'drop'].forEach(eventName => {
            document.body.addEventListener(eventName, preventDefaults, false);
            filePicker.addEventListener(eventName, preventDefaults, false);
        });

        //file drop
        filePicker.addEventListener('drop', handleDrop, false);

        function preventDefaults(e) {
            e.preventDefault();
            e.stopPropagation();
        }

        function handleDrop(e) {
            const dataTransfer = e.dataTransfer;
            const files = dataTransfer.files;

            filePicker.files = files;
        }
    }

    removeURLParameters();
});

document.addEventListener('DOMContentLoaded', applyThemeOnLoad);


window.onload = function() {
    if ((window.location.pathname == "/" || window.location.pathname == "/main.html")
        && !(data)){

        var command = new AddRowCommand(); 
        hist.executeAction(command);
        document.getElementById("undo-btn").onclick = hist.undoCmd.bind(hist);
        document.getElementById("redo-btn").onclick = hist.redoCmd.bind(hist);
        updateUI();
    }
}