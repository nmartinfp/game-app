package org.academiadecodigo.bootcamp.gameapp.utilities;

import org.academiadecodigo.bootcamp.gameapp.client.ClientHandler;
import org.academiadecodigo.bootcamp.gameapp.server.ServerHandler;
import org.academiadecodigo.bootcamp.gameapp.server.deleteclass.ServerParser;

/**
 * Created by Cyrille on 04/07/17.
 */
public final class ProtocolParser {

    //Parser for Client
    public static void clientProtocolHandler(ClientHandler clientHandler, String message) {

        String[] protocol = message.split(";");

        String cleanMessage = concatMessage(protocol);

        switch (protocol[ProtocolConfig.PROTOCOL]) {

            case ProtocolConfig.SERVER_LOGIN:
                clientHandler.userLogin(protocol[ProtocolConfig.MESSAGE]);
                break;

            case ProtocolConfig.SERVER_REGISTER:
                clientHandler.registryMessage(protocol[ProtocolConfig.MESSAGE]);
                break;

            case ProtocolConfig.SERVER_LOBBY:

                break;

            case ProtocolConfig.SERVER_ROOM:

                break;
            case ProtocolConfig.SERVER_GAME:

                break;

            case ProtocolConfig.SERVER_CHAT:
                clientHandler.receivedMessage(cleanMessage);
                break;

            default:
                clientHandler.receivedMessage("MESSAGE FAILURE TRY AGAIN");
        }
    }

    //Parser for Server
    // TODO: 07/07/17 change message " " to ";" used in the regex
    public static void serverProtocolHandler(ServerHandler serverHandler, String message) {

        String[] protocol = message.split(";");

        switch (protocol[ProtocolConfig.PROTOCOL]) {
            case ProtocolConfig.CLIENT_LOGIN:
                serverHandler.authenticate(protocol[1], protocol[2]);
                break;
            case ProtocolConfig.CLIENT_REGISTER:
                serverHandler.createUser(protocol[1], protocol[2], protocol[3]);
                break;
            case ProtocolConfig.CLIENT_LOBBY:
                break;
            case ProtocolConfig.CLIENT_ROOM:

                break;
            case ProtocolConfig.CLIENT_GAME:
                throw new UnsupportedOperationException();
            case ProtocolConfig.CLIENT_CHAT:

                break;
        }
    }

    //concat message to send client or server
    private static String concatMessage(String[] protocolMessages) {

        String message = "";

        for (int i = 1; i < protocolMessages.length; i++) {
            message += protocolMessages[i] + " ";
        }
        return message;
    }
}
