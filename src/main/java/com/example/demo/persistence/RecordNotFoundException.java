package com.example.demo.persistence;

public class RecordNotFoundException extends Exception {

  private static final long serialVersionUID = -2789559542028129297L;

  public RecordNotFoundException(String msg) {
    super(msg);
  }
}
