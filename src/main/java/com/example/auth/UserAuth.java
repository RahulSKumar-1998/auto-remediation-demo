```java
// src/main/java/com/example/auth/UserAuth.java

// ... other imports and class definition ...

// Assume 'connection' is an existing java.sql.Connection object
// Assume 'username' and 'password' are String inputs from the user

// OLD VULNERABLE CODE (conceptual, around line 42):
// String query = "SELECT id FROM users WHERE username = '" + username + "' AND password = '" + password + "'";
// Statement stmt = connection.createStatement();
// ResultSet rs = stmt.executeQuery(query);

// NEW SECURE CODE:
String sql = "SELECT id FROM users WHERE username = ? AND password = ?";
try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
    pstmt.setString(1, username);
    pstmt.setString(2, password);
    ResultSet rs = pstmt.executeQuery();
    // ... further processing of ResultSet ...
}
// ... rest of the method or class ...
```

**Explanation:**
The fix replaces string concatenation for building the SQL query with a `PreparedStatement`. User inputs (`username` and `password`) are now passed as parameters to `pstmt.setString()`, which ensures they are treated as literal values, not executable SQL code. This prevents SQL injection attacks by separating the SQL command from user-supplied data.