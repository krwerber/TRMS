package dev.werber.exceptions;

@SuppressWarnings("serial")
public class NonUniqueUsernameException extends Exception {
	public NonUniqueUsernameException () {
		super("The requested username is taken.");
	}
}
