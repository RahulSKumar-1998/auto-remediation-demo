package com.example.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SearchController {

    public void handleSearch(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String query = request.getParameter("q");
        
        // VULNERABILITY: Reflected XSS
        // The user input 'query' is reflected back in the response without encoding.
        response.setContentType("text/html");
        response.getWriter().println("<h1>Search Results for: " + query + "</h1>");
    }
}

// Auto-generated update: 1770871950