package net.vatri.rusit;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class App extends Application {

	private TextArea textArea = new TextArea();
	
	private String russianAlphabet = "абвгдежзийклмнопрстуфхцчшщъыьэюяё";

	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {

		primaryStage.setTitle(Config.appTitle);

		Button btnCopy = new Button("COPY");
		applyCommonFontStyle(btnCopy);
		btnCopy.setOnAction(h -> {
			final Clipboard clipboard = Clipboard.getSystemClipboard();
	        final ClipboardContent content = new ClipboardContent();
	        content.putString(textArea.getText());
//	        content.putHtml("<b>Some</b> text");
	        clipboard.setContent(content);
		});

		GridPane root = new GridPane();
		root.setAlignment(Pos.CENTER);
		root.setHgap(10);
		root.setVgap(10);
		root.setPadding(new Insets(25, 25, 25, 25));
		
		GridPane gridButtons = new GridPane();
		root.add(gridButtons, 0, 0);
		createCharButtonsLayout(gridButtons);

		GridPane gridSpecialButtons = new GridPane();
		createSpecialButtons(gridSpecialButtons);
		root.add(gridSpecialButtons, 0, 1);
		
		GridPane gridTools = new GridPane();
		gridTools.add(textArea, 0, 5);
		gridTools.add(btnCopy, 0, 6);
		root.add(gridTools, 0, 2);
		
		primaryStage.setScene(new Scene(root, Config.windowW  , Config.windowH));
		primaryStage.show();
		
	}
	
	
	private void createSpecialButtons(GridPane grid) {
		String specialButtons = "-.?!\"'";
		
		int lastIdx = 0;
		
		for(int i = 0; i < specialButtons.length(); i++) {
			
			Button btn = buildCharButton(specialButtons.substring(i, i+1));
			grid.add(btn, i, 0);
			
			lastIdx = i;
		}
		
		lastIdx++;
		Button btn = new Button("_"); // SPACE
		applyCommonFontStyle(btn);
		grid.add(btn, lastIdx, 0);
		btn.setOnAction(h -> {
			addToTextarea("SPACE");
		});
		
		lastIdx++;
		btn = new Button("\u23CE"); // ENTER
		applyCommonFontStyle(btn);
		btn.setOnAction(h -> {
			addToTextarea("RETURN");
		});
		grid.add(btn, lastIdx, 0);
		
		lastIdx++;
		btn = new Button("\u232B");// BACKSPACE
		applyCommonFontStyle(btn);
		btn.setOnAction(h -> {
			addToTextarea("BACKSPACE");
		});

		grid.add(btn, lastIdx, 0);

	}
	
	private void applyCommonFontStyle(Button btn) {
		btn.setFont( new Font(Config.fontSize) );
	}

	private void createCharButtonsLayout(GridPane grid) {
		int col = 0;
		int row = 0;
		for(int i = 0; i < russianAlphabet.length(); i++) {
			
			Button btn = buildCharButton(russianAlphabet.substring(i, i+1));
			
			grid.add(btn, col, row);

			col++;
			
			if(col > 10) {
				col = 0;
				row++;
			}

		}
	}

	private Button buildCharButton(String inChar) {
		
		Button btn  = new Button(inChar);
		btn.setOnAction(h -> {
			addToTextarea(inChar);
			textArea.requestFocus();
		});
		applyCommonFontStyle(btn);
		return btn;
	
	}

	private void addToTextarea(String inChar) {
		
		StringBuffer sbText = new StringBuffer(textArea.getText());
		int caretPos = textArea.getCaretPosition(); 

		
		if(inChar.equals("BACKSPACE")){
			sbText.deleteCharAt(caretPos-1);
			caretPos--;
		} else {
			if(inChar.equals("RETURN")) { inChar = "\n"; } 
			else if(inChar.equals("SPACE")){ inChar = " "; }
			sbText.insert(caretPos, inChar);
			caretPos++;
		}
		textArea.setText(sbText.toString());
		textArea.positionCaret(caretPos); // make sure this is called AFTER setText !!
		textArea.requestFocus();

	}

}
