package org.academiadecodigo.bootcamp.gameapp.client.controller;

import javafx.event.Event;
import javafx.event.EventTarget;
import javafx.event.EventType;

/**
 * Created by codecadet on 28/06/17.
 */
public class MyEvent extends Event {

    private String message;

    public static final EventType<MyEvent> WRITE_LABEL = new EventType<>(Event.ANY, "WRITE_LABEL");

    public MyEvent() {
        super(WRITE_LABEL);

    }

    public MyEvent(Object source, EventTarget target){
        super(source, target, WRITE_LABEL);

        this.message = "oi";

    }

    public String getMessage() {
        return message;
    }

    public static EventType<MyEvent> getWriteLabel() {
        return WRITE_LABEL;
    }
}
