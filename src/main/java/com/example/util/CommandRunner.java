```java
// src/main/java/com/example/util/CommandRunner.java
import java.io.IOException; // Required for ProcessBuilder.start()

public class CommandRunner {

    // ... existing code ...

    /**
     * Executes a command with a user-supplied argument securely.
     * Replaces vulnerable Runtime.exec() calls.
     *
     * @param userControlledInput An argument provided by the user, treated as a literal string.
     * @return The Process object representing the external command.
     * @throws IOException If an I/O error occurs during command execution.
     */
    public Process runSecureCommand(String userControlledInput) throws IOException {
        // OLD (VULNERABLE) line 15 might have looked like:
        // Process process = Runtime.getRuntime().exec("some_fixed_command --option " + userControlledInput);

        // FIX (Line 15): Replace Runtime.getRuntime().exec() with ProcessBuilder
        // Each argument is passed as a distinct string, preventing shell metacharacter injection.
        Process process = new ProcessBuilder("some_fixed_command", "--option", userControlledInput).start();
        
        // Optional: Redirect error stream for easier debugging/logging
        // pb.redirectErrorStream(true); 
        // process = pb.start();

        return process;
    }

    // ... other methods ...
}
```

**Explanation of the fix:**

The original code likely used `Runtime.getRuntime().exec()` with string concatenation, allowing `userControlledInput` to introduce shell metacharacters and execute arbitrary commands. The fix replaces this with `java.lang.ProcessBuilder`.

By using `new ProcessBuilder("some_fixed_command", "--option", userControlledInput).start();`, the application treats `userControlledInput` as a literal argument to `some_fixed_command`, rather than part of the command string to be parsed by a shell. This prevents OS command injection as malicious characters within `userControlledInput` are no longer interpreted as commands.