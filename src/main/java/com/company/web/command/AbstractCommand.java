package com.company.web.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public abstract class AbstractCommand implements Command {
    protected HttpServletRequest req;
    protected HttpServletResponse resp;

    public void init(HttpServletRequest req, HttpServletResponse res) {
        this.req = req;
        this.resp = res;
    }

}
