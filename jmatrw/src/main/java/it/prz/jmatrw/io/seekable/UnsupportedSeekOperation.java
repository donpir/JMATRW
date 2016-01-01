package it.prz.jmatrw.io.seekable;

import java.io.IOException;

public class UnsupportedSeekOperation extends IOException {
	private static final long serialVersionUID = 1L;

	public UnsupportedSeekOperation() {
		super();
	}

	public UnsupportedSeekOperation(String message, Throwable cause) {
		super(message, cause);
	}

	public UnsupportedSeekOperation(String message) {
		super(message);
	}

	public UnsupportedSeekOperation(Throwable cause) {
		super(cause);
	}

}//EndClass.
