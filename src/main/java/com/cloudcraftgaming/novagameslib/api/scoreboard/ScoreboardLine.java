package com.cloudcraftgaming.novagameslib.api.scoreboard;

/**
 * Created by Nova Fox on 11/17/16.
 * Website: www.cloudcraftgaming.com
 * For Project: NovaGamesLib
 */

@SuppressWarnings("WeakerAccess")
public class ScoreboardLine {
	private final int index;

	private String currentText;
	private String previousText;

	/**
	 * Constructor for ScoreboardLine.
	 * @param _index The index of the line.
	 */
	public ScoreboardLine(int _index) {
		index = _index;
		currentText = String.valueOf(index);
		previousText = String.valueOf(index);
	}

	//Booleans/Checkers
	/**
	 * Checks if the displayed text has been changed.
	 * @return <code>true</code> if it has changed, else <code>false</code>.
	 */
	public Boolean hasChanged() {
		return currentText.equals(previousText);
	}

	//Getters
	/**
	 * Gets the index of this line (The score on the scoreboard).
	 * @return The line's index.
	 */
	public int getIndex() {
		return index;
	}

	/**
	 * Gets the currently displayed text.
	 * @return The currently displayed text.
	 */
	public String getCurrentText() {
		return currentText;
	}

	/**
	 * Gets the next before the last change.
	 * @return The previously displayed text.
	 */
	public String getPreviousText() {
		return previousText;
	}

	//Setters
	/**
	 * Sets the text currently being displayed.
	 * This has no effect on the actual scoreboard.
	 * @param _newText The text to display.
	 */
	public void setCurrentText(String _newText) {
		previousText = currentText;
		currentText = _newText;
	}
}