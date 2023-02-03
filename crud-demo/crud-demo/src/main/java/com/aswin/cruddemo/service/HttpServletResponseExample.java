package com.aswin.cruddemo.service;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Service;

@Service
@WebServlet(urlPatterns = "/httpservletresponse")
public class HttpServletResponseExample extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // Writing HTML
        PrintWriter printWriter = response.getWriter();

        // Headers
        response.setHeader("Header-Name", "Header Value");

        // Content-Type
        response.setContentType("text/html");

        // Content-Length
        response.setHeader("Content-Length", "31642");

        // Writing Binary Data
        OutputStream outputStream = response.getOutputStream();
        outputStream.write(123);

        //Redirecting to a Different URL
        response.sendRedirect("https://javaguides.net");

        printWriter.print("<html>");
        printWriter.print("<body>");
        printWriter.print("<h1>Hello World HttpServlet Class Example</h1>");
        printWriter.print("<a href=\"http://www.javaguides.net\">Java Guides</a>");
        printWriter.print("</body>");
        printWriter.print("</html>");
        printWriter.close();
    }
}