```java
// src/main/java/com/example/controller/SearchController.java
import org.springframework.web.util.HtmlUtils; // Add this import if not present

// ...

// Inside the appropriate method, e.g., a GET mapping for search
// At or around line 85, where 'query' (user input) is being processed:
// Replace the vulnerable line(s) with the following:
String encodedQuery = HtmlUtils.htmlEscape(query);
model.addAttribute("searchTerm", encodedQuery);
```

**Explanation:**
The fix applies HTML encoding to the user-supplied `query` parameter using `HtmlUtils.htmlEscape()` before it is added to the `Model`. This prevents malicious scripts embedded in the `query` from being executed when the `searchTerm` is rendered in an HTML context, effectively mitigating the reflected Cross-Site Scripting (XSS) vulnerability.