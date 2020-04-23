package main.answers;

import java.io.Serializable;

public abstract class Answer implements Serializable {
	protected String answer;

	public Answer(String answer) {
		this.answer = answer;
	}

	public abstract void logAnswer();
	public abstract void printAnswer();
}
