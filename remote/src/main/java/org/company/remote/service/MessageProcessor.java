package org.company.remote.service;

/**
 * Интерфейс обработчика сообщений.
 */
public interface MessageProcessor {

  boolean processMessage(String message);

}
