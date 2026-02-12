```java
// src/main/java/com/example/util/CommandRunner.java
// ... (imports and class definition)

    // ... other methods or code ...

    // Original vulnerable line (example, assuming it's within a method like executeCommand):
    // Process p = Runtime.getRuntime().exec(userSuppliedCommand); // Line 15

    // Secure fix for line 15:
    String[] commandParts = userSuppliedCommand.split("\\s+"); // Split by one or more spaces
    if (commandParts.length == 0) {
        throw new IllegalArgumentException("Command cannot be empty.");
    }
    // IMPORTANT: For full security, consider whitelisting allowed commands (commandParts[0])
    // and validating arguments (commandParts[1] onwards) if `userSuppliedCommand`
    // can originate from untrusted sources. For example:
    /*
    List<String> allowedCommands = Arrays.asList("ls", "grep", "cat"); // Define allowed executables
    if (!allowedCommands.contains(commandParts[0])) {
        throw new SecurityException("Unsupported command: " + commandParts[0]);
    }
    // Further validation on arguments might be needed depending on the command and expected input.
    */
    Process p = new ProcessBuilder(commandParts).start();
```

**Explanation of the fix:**

The original code used `Runtime.getRuntime().exec(String)`, which passes the entire command string to the system's shell for execution. This makes it vulnerable to OS command injection (CWE-78) if `userSuppliedCommand` contains shell metacharacters (like `;`, `|`, `&&`, etc.) that allow an attacker to execute arbitrary commands.

This fix replaces `Runtime.getRuntime().exec()` with `java.lang.ProcessBuilder`. `ProcessBuilder` does not invoke a shell by default; instead, it directly executes the first element of its command list as a program and passes subsequent elements as literal arguments to that program. By splitting the `userSuppliedCommand` into `commandParts`, we ensure that each part (command or argument) is treated as a distinct, literal token, preventing shell metacharacters from being interpreted as command separators or operators.

For robust security, especially if `userSuppliedCommand` comes from untrusted user input, it's crucial to implement **whitelisting** for the command (the first element, `commandParts[0]`) and **validation/sanitization** for all arguments. This ensures that only approved commands are executed and that arguments adhere to expected formats.