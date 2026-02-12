package com.example.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class CommandRunner {

    public String runSystemCommand(String userInput) {
        StringBuilder output = new StringBuilder();
        try {
            // VULNERABILITY: OS Command Injection
            // User input is directly passed to the shell.
            String command = "ping -c 4 " + userInput;

            Process p = Runtime.getRuntime().exec(command);
            p.waitFor();

            BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line + "\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return output.toString();
    }
}

// Auto-generated update: 1770873415