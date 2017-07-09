package org.academiadecodigo.bootcamp.gameapp.utilities.logging;

/**
 * Created by neper on 2017/7/9.
 */

import org.academiadecodigo.bootcamp.gameapp.utilities.AppConfig;

import java.io.*;
import java.util.Calendar;

/**
 * This class has the ability to write and search our log based on a priority level.
 */
public final class Logger {
    private BufferedWriter writer;
    private File file;

    private static Logger instance = null;

    private Logger() {
    }


    /**
     * Creates if not exists an instance of Logger class. Synchronized class.
     * @return the Logger instance
     */
    public static Logger getInstance() {

        if (instance == null) {
            synchronized (Logger.class) {

                if (instance == null) {
                    instance = new Logger();
                }
            }
        }

        return instance;
    }


    /**
     * Setups our BufferedWriter and BufferedReader streams, if the file is not found a new one
     * will be created.
     */
    public void init(String filePath) {

        file = new File(filePath);

        try {

            if (file.isDirectory()) {
                throw new IOException("Cannot create logger here, " + AppConfig.LOG_FILE + " is a directory");
            }

            if (!file.exists()) {
                file.createNewFile();
            }

            writer = new BufferedWriter(new FileWriter(file, true));
            System.out.println("File was created in path: " + file.getAbsolutePath());

        } catch (IOException e1) {
            System.err.println("Couldn't create new file in path" + file.getAbsolutePath());
            e1.printStackTrace();
        }
    }


    /**
     * Writes an occurrence to the log file, the occurrence will have the following format:
     * Timestamp - Priority: HIGH Message: Example message.
     *
     * @param priorityLevel - {@link PriorityLevel}
     * @param message       - the message that will be written to the log file
     * @throws IllegalStateException if you cannot write to the file due to stream not being initialized or OS
     *                               permissions
     */
    public void log(PriorityLevel priorityLevel, String message) {
        if (!file.canWrite() || writer == null) {
            throw new IllegalStateException("Cannot write to this file...");
        }

        try {
            writer.append(Calendar.getInstance().getTime() +
                    " - Priority: " + priorityLevel.toString() +
                    " Message: " + message + "\n");
            writer.flush();

        } catch (IOException e) {
            System.err.println("Couldn't write to log file");
            try {
                if (writer != null) {
                    writer.close();
                }
            } catch (IOException ioException) {
                System.err.println(ioException.getMessage());
            }
        }
    }


    /**
     * Reads our log file and filters it based on PriorityLevel
     *
     * @param priorityLevel - {@link PriorityLevel}
     * @return a String with the occurrences in the log based on arg PriorityLevel.
     * @throws IllegalStateException if you cannot read from the file due to OS permissions.
     */
    public String read(PriorityLevel priorityLevel) { // TODO: 2017/7/9 - This method isn't invoked!!!
        if (!file.canRead()) {
            throw new IllegalStateException("Cannot read from this file...");
        }

        String result = "";
        String line;
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(file));
            while ((line = reader.readLine()) != null) {
                if (line.contains("Priority: " + priorityLevel.toString())) {
                    result += line + "\n";
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    System.err.println("Couldn't close stream properly");
                    e.printStackTrace();
                }
            }
        }

        return result;
    }


    /**
     * Closes the BufferedWriter and BufferedReader streams.
     */
    public void close() {
        try {
            writer.close();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
}
