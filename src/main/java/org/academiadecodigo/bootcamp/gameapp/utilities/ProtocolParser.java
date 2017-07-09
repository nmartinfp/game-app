package org.academiadecodigo.bootcamp.gameapp.utilities;

import org.academiadecodigo.bootcamp.gameapp.client.ServerHandler;

/**
 * A/C: Bootcamp8
 * 2nd group project - GameName App Platform
 * Authors: Cyrille Feijó, João Fernandes, Hélder Matos, Nelson Pereira, Tiago Santos
 */

public final class ProtocolParser {

    //Client Parser
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

                break;

            case ProtocolConfig.SERVER_CHAT:
                serverHandler.receivedMessage(protocol[ProtocolConfig.MESSAGE]);
                break;

            default:
                serverHandler.receivedMessage("MESSAGE FAILURE TRY AGAIN");
        }
    }


    //Message concatenation to send client or server
    public static String[] splitMessage(String message) {

        return message.split(";");
    }
}
