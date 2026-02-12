```java
// src/main/java/com/example/controller/SearchController.java
import org.springframework.web.util.HtmlUtils; // Import required for HtmlUtils

// ... existing imports ...

public class SearchController {

    // ... existing methods and code ...

    // Example method where user input might be reflected
    // Assuming 'query' is the user-supplied input parameter
    public String search(@RequestParam("query") String query, Model model) {
        // ... potentially other logic ...

        // Line 85: Encode user-supplied data before adding it to the model for rendering
        model.addAttribute("searchQuery", HtmlUtils.htmlEscape(query)); 

        // ... rest of the method ...
        return "search_results"; // Assuming this forwards to a view
    }

    // ... rest of the class ...
}
```

**Explanation:**
The fix involves using `HtmlUtils.htmlEscape()` from Spring Framework's `org.springframework.web.util` package to encode the user-supplied `query` parameter. This ensures that any malicious characters in the input are converted into their HTML entity equivalents before being added to the model, preventing them from being interpreted as active content when rendered in the HTML context. This addresses the reflected XSS vulnerability by properly escaping user input.