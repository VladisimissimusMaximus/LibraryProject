package com.company.web;

import com.company.web.command.Command;
import com.company.web.command.ShowHomeCommand;
import com.company.web.command.book.DeleteBookCommand;
import com.company.web.command.operation.*;
import com.company.web.command.user.*;
import com.company.web.command.book.ShowCatalogueCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import java.util.function.Supplier;


public class FrontControllerServlet extends HttpServlet {

    private static final Logger logger = LoggerFactory.getLogger(FrontControllerServlet.class);

    private static final Map<Endpoint, Supplier<Command>> commands = Map.ofEntries(
            Map.entry(new Endpoint(Uri.CATALOGUE, HttpMethod.GET), ShowCatalogueCommand::new),
            Map.entry(new Endpoint(Uri.DELETE_BOOK, HttpMethod.GET), DeleteBookCommand::new),
            Map.entry(new Endpoint(Uri.HOME, HttpMethod.GET), ShowHomeCommand::new),
            Map.entry(new Endpoint(Uri.LOGIN, HttpMethod.GET), ShowLoginCommand::new),
            Map.entry(new Endpoint(Uri.LOGIN, HttpMethod.POST), SubmitLoginCommand::new),
            Map.entry(new Endpoint(Uri.REGISTER, HttpMethod.GET), ShowRegisterCommand::new),
            Map.entry(new Endpoint(Uri.REGISTER, HttpMethod.POST), SubmitRegisterCommand::new),
            Map.entry(new Endpoint(Uri.LOGOUT, HttpMethod.POST), LogoutCommand::new),
            Map.entry(new Endpoint(Uri.PROFILE, HttpMethod.GET), ShowProfileCommand::new),
            Map.entry(new Endpoint(Uri.UPDATE_PROFILE, HttpMethod.GET), ShowProfileUpdateCommand::new),
            Map.entry(new Endpoint(Uri.UPDATE_PROFILE, HttpMethod.POST), SubmitProfileUpdateCommand::new),
            Map.entry(new Endpoint(Uri.UPDATE_USER, HttpMethod.GET), ShowUserUpdateCommand::new),
            Map.entry(new Endpoint(Uri.UPDATE_USER, HttpMethod.POST), SubmitUserUpdateCommand::new),
            Map.entry(new Endpoint(Uri.USERS, HttpMethod.GET), ShowUsersCommand::new),
            Map.entry(new Endpoint(Uri.DELETE_USER, HttpMethod.GET), DeleteUserCommand::new),
            Map.entry(new Endpoint(Uri.READ, HttpMethod.GET), ReadCommand::new),
            Map.entry(new Endpoint(Uri.SUBSCRIBE, HttpMethod.GET), ShowSubscribeCommand::new),
            Map.entry(new Endpoint(Uri.SUBSCRIBE, HttpMethod.POST), SubmitSubscribeCommand::new),
            Map.entry(new Endpoint(Uri.APPROVE, HttpMethod.GET), ApproveOrderCommand::new),
            Map.entry(new Endpoint(Uri.OPERATION, HttpMethod.GET), ShowOperationsCommand::new)
    );

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processCommand(req, resp);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processCommand(req, resp);
    }

    private void processCommand(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.info("start processing command {}", req.getRequestURI() + req.getQueryString());
        Endpoint endpoint = Endpoint.fromRequest(req);
        Command command = commands.get(endpoint).get();
        command.init(req, resp);
        command.process();

    }


}
