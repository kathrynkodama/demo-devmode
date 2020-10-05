package io.openliberty.sample.system;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 *
 * @author Ivar Grimstad (ivar.grimstad@eclipse-foundation.org)
 */
@WebServlet(name = "hello", urlPatterns = {"/hello"})
public class SimpleHello  extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=UTF-8");
        resp.getWriter().println( "Hello Jakarta EE 8!");
    }
}