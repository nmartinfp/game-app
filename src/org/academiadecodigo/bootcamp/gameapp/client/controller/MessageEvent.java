package org.academiadecodigo.bootcamp.gameapp.client.controller;

import javafx.event.Event;
import javafx.event.EventType;

/**
 * Created by codecadet on 28/06/17.
 */
// TODO: 01/07/17 this class isn't a controller
public class MessageEvent extends Event {

    public static final EventType<MessageEvent> type = new EventType<MessageEvent>(Event.ANY, "type");

    private String message;

    public MessageEvent(String message) {
        super(type);
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
