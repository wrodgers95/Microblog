package com.theironyard.charlotte;

import spark.ModelAndView;
import spark.Session;
import spark.Spark;
import spark.template.mustache.MustacheTemplateEngine;

import java.util.HashMap;

public class Main {

    public static HashMap<String, User> users = new HashMap<>();

    public static void main ( String [] args) {

        Spark.init();

        Spark.get(

                // initial screen
                "/",

                ((request, response) -> {

                    // getting current user from users HashMap

                    Session session = request.session();

                    String name = session.attribute("loginName");

                    User user = users.get(name);

                    HashMap m = new HashMap<>();

                    // if no user exists, redirect to login screen else to go messages screen
                    if (user == null) {

                        return new ModelAndView(m, "login.html");

                    } else {

                        m.put("name", user.name);

                        m.put("messages", user.messages);

                        return new ModelAndView(m, "home.html");
                    }
                }),

                new MustacheTemplateEngine()
        );

        Spark.post(
                // login screen
                "/login",

                ((request, response) -> {

                    String name = request.queryParams("loginName");

                    String password = request.queryParams("loginPassword");

                    User user = users.get(name);

                    if (user == null) {

                        user = new User(name, password);

                        users.put(name, user);
                    }
                    if (users.get(name).password != password) {

                        Session session = request.session();
                        session.attribute("loginName", name);

                        session.invalidate();
                    }

                    Session session = request.session();

                    session.attribute("loginName", name);

                    response.redirect("/");

                    return "";
                })
        );

        Spark.post(

                // home & messages screen
                "/home",

                ((request, response) -> {

                    // getting current user

                    Session session = request.session();

                    String name = session.attribute("loginName");

                    User user = users.get(name);

                    // requesting input (message)

                    user.messages.add(request.queryParams("message"));

                    // staying on home (messages) screen

                    response.redirect("/");

                    return "";
                })
        );

        Spark.post(

                // logout button

                "/logout",

                ((request, response) -> {

                    Session session = request.session();

                    // ending current session
                    session.invalidate();

                    // return home
                    response.redirect("/");

                    return "";
                })

        );

        Spark.post(

                "/delete",

                ((request, response) -> {

                    Session session = request.session();

                    String name = session.attribute("loginName");

                    int i = (Integer.valueOf(request.queryParams("messageDelete")) - 1);

                    User user = users.get(name);

                    user.messages.remove(i);

                    response.redirect("/");

                    return "";

                }));

        Spark.post(

                "/edit",

                ((request, response) -> {

                    Session session = request.session();

                    String name = session.attribute("loginName");

                    int update = (Integer.valueOf(request.queryParams("number")) - 1);

                    String text = request.queryParams("edit");

                    User user = users.get(name);

                    user.messages.set(update, text);

                    response.redirect("/");

                    return "";
                })
        );
    }
}