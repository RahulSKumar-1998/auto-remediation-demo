```diff
--- a/src/main/java/com/example/auth/UserAuth.java
+++ b/src/main/java/com/example/auth/UserAuth.java
@@ -39,12 +39,18 @@
 public class UserAuth {
     public boolean authenticate(String username, String password) {
         Connection connection = null;
-        Statement statement = null;
+        PreparedStatement preparedStatement = null; // Use PreparedStatement instead of Statement
         ResultSet resultSet = null;
         try {
             // Assume 'connection' is obtained here or passed in
             connection = DatabaseUtil.getConnection(); // Hypothetical connection acquisition
-            // Line 42: Vulnerable SQL query construction
-            String sql = "SELECT id FROM users WHERE username = '" + username + "' AND password = '" + password + "'";
-            statement = connection.createStatement();
-            resultSet = statement.executeQuery(sql);
+
+            // Line 42: Secure SQL query using placeholders
+            String sql = "SELECT id FROM users WHERE username = ? AND password = ?";
+            preparedStatement = connection.prepareStatement(sql);
+            preparedStatement.setString(1, username); // Set the first parameter (username)
+            preparedStatement.setString(2, password); // Set the second parameter (password)
+
+            resultSet = preparedStatement.executeQuery(); // Execute the prepared statement
             return resultSet.next();
         } catch (SQLException e) {
             e.printStackTrace();
@@ -52,7 +58,7 @@
         } finally {
             // Close resources in a real application
             try { if (resultSet != null) resultSet.close(); } catch (SQLException e) { /* log error */ }
-            try { if (statement != null) statement.close(); } catch (SQLException e) { /* log error */ }
+            try { if (preparedStatement != null) preparedStatement.close(); } catch (SQLException e) { /* log error */ }
             try { if (connection != null) connection.close(); } catch (SQLException e) { /* log error */ }
         }
     }
```

**Explanation:**
The fix replaces string concatenation with a `PreparedStatement`. This ensures that user-supplied inputs (`username` and `password`) are treated as literal data values and not as executable parts of the SQL query, effectively preventing SQL injection attacks. Placeholders (`?`) are used in the SQL string, and `setString()` methods safely bind the input values to these placeholders.