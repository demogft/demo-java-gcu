package com.scalesec.vulnado;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.logging.Logger;

public class Cowsay {
    private Cowsay() {}

    public static String run(String input) {
        Logger logger = Logger.getLogger(Cowsay.class.getName());

        ProcessBuilder processBuilder = new ProcessBuilder();
        String cmd = "/usr/games/cowsay '" + input + "'";
        logger.info(cmd);
        processBuilder.command("bash", "-c", cmd);

        StringBuilder output = new StringBuilder();

        try {
            Process process = processBuilder.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line + "\n");
            }
        } catch (Exception e) {
            logger.severe(e.getMessage());
        }
        return output.toString();
    }
}