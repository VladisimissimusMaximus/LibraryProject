package com.company.web.command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface Command {

    void process() throws ServletException, IOException;

    void init(HttpServletRequest req, HttpServletResponse res);


}
