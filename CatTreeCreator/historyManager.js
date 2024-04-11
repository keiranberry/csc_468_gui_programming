// Building off of the undo/redo code provided by Dr. Rebenitsch in class
// JavaScript Document//helper class to handle the current location in the undo/redo list
//GRADING: MANAGE
class HistoryManager {
	constructor(){
		this.UndoRedos =[];
		this.index = 0;
	}
	
	
	//new UndoRedo, remove everything after the current UndoRedo index
	//and append the new function
	//
	executeAction(cmd){
		this.UndoRedos.length = this.index; //trims length from 0 to index
		this.UndoRedos.push(cmd);			//GRADING: ACTION
		this.index = this.UndoRedos.length
		
		//run the UndoRedo and update
		cmd.exec();
		updateUI();
	}
	
	
	//undo called. Call the undo functin on the current UndoRedo and move back one
	undoCmd(){
		if(this.index > 0)
		{
			var cmd = this.UndoRedos[this.index-1];
			cmd.undo();
			this.index= this.index - 1;
			updateUI();
		}
	}
	
	//redo called. Call the execution function on the current UndoRedo and move forward one
	redoCmd(){
		if(this.index < this.UndoRedos.length)
		{
			var cmd = this.UndoRedos[this.index];
			cmd.exec();
			this.index = this.index + 1;
			updateUI();
		}
	}
	
	
	//is undo available
	canUndo(){
		return this.index != 0;
	}
	
	//is redo available
	canRedo(){
		return this.index < this.UndoRedos.length;
	}
}