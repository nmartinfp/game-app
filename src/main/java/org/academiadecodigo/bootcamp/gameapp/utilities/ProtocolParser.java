package org.academiadecodigo.bootcamp.gameapp.utilities;

import org.academiadecodigo.bootcamp.gameapp.client.ClientHandler;
import org.academiadecodigo.bootcamp.gameapp.server.ServerParser;

/**
 * Created by Cyrille on 04/07/17.
 */
public final class ProtocolParser {

    //Parser for Client
    public static void clientProtocolHandler(ClientHandler clientHandler, String message) {

        String[] protocol = message.split(" ");

        String cleanMessage = concatMessage(protocol);

        System.out.println("client parser " + protocol[0]);
        System.out.println("client message " + cleanMessage);

        switch (protocol[ProtocolConfig.PROTOCOL]) {

            case ProtocolConfig.SERVER_LOGIN:
                clientHandler.userLogin(protocol[ProtocolConfig.MESSAGE]);
                break;

            case ProtocolConfig.SERVER_REGISTER:
                clientHandler.registryMessage(protocol[ProtocolConfig.MESSAGE]);
                break;

            case ProtocolConfig.SERVER_LOBBY:

                break;

            case ProtocolConfig.SERVER_GAME:

                break;

            case ProtocolConfig.CLIENT_CHAT:
                clientHandler.receivedMessage(cleanMessage);
                break;

            default:
                clientHandler.receivedMessage("MESSAGE FAILURE TRY AGAIN");
        }
    }

    //Parser for Server
    public static void serverProtocolHandler(ServerParser serverParser, String message) {

        String[] protocol = message.split(" ");

        String cleanMessage = concatMessage(protocol);
        System.out.println("\n" + protocol[0]);
        System.out.println(cleanMessage);

        switch (protocol[ProtocolConfig.PROTOCOL]) {
            case ProtocolConfig.SERVER_LOGIN:
                serverParser.loginUser(protocol);
                break;
            case ProtocolConfig.SERVER_REGISTER:
                serverParser.registerUSer(protocol);
                break;
            case ProtocolConfig.SERVER_LOBBY:
                serverParser.rpsGame(protocol);
                break;
            case ProtocolConfig.SERVER_GAME:
                throw new UnsupportedOperationException();
            case ProtocolConfig.CLIENT_CHAT:
                serverParser.clientComm(cleanMessage);
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
