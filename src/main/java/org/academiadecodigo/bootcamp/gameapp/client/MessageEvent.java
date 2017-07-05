package org.academiadecodigo.bootcamp.gameapp.client;

import javafx.event.Event;
import javafx.event.EventType;

/**
 * A/C: Bootcamp8
 * 2nd group project - Game App Platform
 * Authors: Cyrille Feijó, João Fernandes, Hélder Matos, Nelson Pereira, Tiago Santos
 */
// TODO: 01/07/17 this class see a possible deletion :)
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
