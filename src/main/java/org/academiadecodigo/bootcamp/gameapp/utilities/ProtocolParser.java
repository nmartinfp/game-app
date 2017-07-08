package org.academiadecodigo.bootcamp.gameapp.utilities;

import org.academiadecodigo.bootcamp.gameapp.client.ServerHandler;

/**
 * Created by Cyrille on 04/07/17.
 */
public final class ProtocolParser {

    //Parser for Client
    public static void clientProtocolHandler(ServerHandler serverHandler, String message) {

        String[] protocol = message.split(";");

        switch (protocol[ProtocolConfig.PROTOCOL]) {

            case ProtocolConfig.SERVER_LOGIN:
                serverHandler.userLogin(protocol[ProtocolConfig.MESSAGE]);
                break;

            case ProtocolConfig.SERVER_REGISTER:
                serverHandler.registryMessage(protocol[ProtocolConfig.MESSAGE]);
                break;

            case ProtocolConfig.SERVER_CREATE_ROOM:
                serverHandler.createRoom(protocol[ProtocolConfig.MESSAGE]);
                break;
            case ProtocolConfig.SERVER_REGISTER_ROOM:
                serverHandler.changeRoomName(protocol[ProtocolConfig.MESSAGE]);
                break;
            case ProtocolConfig.SERVER_JOIN_ROOM:
                serverHandler.addToRoom(protocol[ProtocolConfig.MESSAGE]);
                break;
            case ProtocolConfig.SERVER_GAME:
                serverHandler.resultGame(protocol[ProtocolConfig.MESSAGE]);
                break;
            case ProtocolConfig.SERVER_OTHER_HAND:
                serverHandler.showOtherHand(protocol[ProtocolConfig.MESSAGE]);
            case ProtocolConfig.SERVER_CHAT:
                serverHandler.receivedMessage(protocol[ProtocolConfig.MESSAGE]);
                break;
            case ProtocolConfig.SERVER_CHAT_ROOM:
                serverHandler.receivedMessageRoom(protocol[ProtocolConfig.MESSAGE]);
                break;
            default:
                serverHandler.receivedMessage("MESSAGE FAILURE TRY AGAIN");
        }
    }

    //concat message to send client or server
    public static String[] splitMessage(String message) {

        return message.split(";");
    }
}
