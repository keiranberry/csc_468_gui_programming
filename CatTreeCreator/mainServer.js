const express = require('express');
const formidable = require('formidable');
const fs = require('fs');
const bodyParser = require('body-parser');
const path = require('path');

const app = express();
const port = 8080;

app.use(bodyParser.json());
app.use(bodyParser.urlencoded({ extended: true }));

app.post(['/save', '/main.htmlsave'], (req, res) => {
    try {
        const jsonData = req.body;
        //save the json to the server file
        saveDataToFile(jsonData, (err) => {
            if (err) {
                console.error('Error saving data:', err);
                res.status(500).send('Failed to save data');
            } else {
                console.log('Data saved successfully!');
                res.status(200).send('Data saved successfully!');
            }
        });
    } catch (error) {
        console.error('Error saving data:', error);
        res.status(500).send('Failed to save data');
    }
});

app.get('/load', (req, res) => {
  const file = req.query.file;
  if (!file) {
      res.status(400).send('File parameter is missing');
      return;
  }

  try {
      const data = fs.readFileSync(path.join(__dirname, file), 'utf-8');
      //send the data from the file in the response
      res.send(data);
  } catch (err) {
      console.error('Error reading file:', err);
      res.status(500).send('Error reading file');
  }
});


app.post('/load-local', (req, res) => {
    const form = new formidable.IncomingForm();

    form.multiples = false; //single file upload
    form.maxFileSize = 50 * 1024 * 1024;
    form.uploadDir = __dirname; //save files to the current directory

    form.parse(req, (err, fields, files) => {
        if (err) {
            console.error('Error parsing form data:', err);
            res.status(500).send('Error parsing form data');
            return;
        }

        const uploadedFile = files.file[0]; //only one file so the first one is it 
        if (!uploadedFile) {
            res.status(400).send('File parameter is missing');
            return;
        }

        const oldPath = uploadedFile.filepath;
        const newPath = path.join(__dirname, uploadedFile.originalFilename);
        fs.rename(oldPath, newPath, function (err) {
            if (err) {
                console.error('Error moving file:', err);
                res.status(500).send('Error moving file');
                return;
            }
            const data = fs.readFileSync(newPath, 'utf-8'); //now read the data from the uploaded file
            res.send(data); //send the data in the response
        });
    });
});

app.get('/download', (req, res) => {
    const filePath = path.join(__dirname, 'data.json');

    //see if the file exists
    if (!fs.existsSync(filePath)) {
        return res.status(404).send('File not found');
    }

    //set headers
    res.setHeader('Content-disposition', 'attachment; filename=data.json');
    res.setHeader('Content-type', 'application/json');

    //make a read stream and pipe it to the response
    const fileStream = fs.createReadStream(filePath);
    fileStream.pipe(res);
});



//serve the static files
app.use(express.static(path.join(__dirname)));

//root path should send you to main page
app.get('/', (req, res) => {
    res.sendFile(path.join(__dirname, 'main.html'));
});

function saveDataToFile(data, callback) {
    const filePath = 'data.json';
    fs.writeFile(filePath, JSON.stringify(data), callback);
}

app.listen(port, () => {
    console.log(`Server is listening on port ${port}`);
});