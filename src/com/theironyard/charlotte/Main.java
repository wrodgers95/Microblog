package com.theironyard.charlotte;

import spark.ModelAndView;
import spark.Spark;
import spark.template.mustache.MustacheTemplateEngine;

import java.util.ArrayList;
import java.util.HashMap;

public class Main {

    static User user;
    static ArrayList<String> messages = new ArrayList<>();
    static HashMap m = new HashMap();

    public static void main(String[] args) {

        Spark.init();

        Spark.get(

                "/",
                ((request, response) -> {
                    if (user == null) {
                        return new ModelAndView(m, "create-user.html");

                    } else {
                        m.put("userName", user.name);
                        return new ModelAndView(m, "messages.html");
                    }
                }),
                new MustacheTemplateEngine()
        );

        Spark.post(
                "/create-user",
                ((request, response) -> {
                    String name = request.queryParams("userName");
                    String password = request.queryParams("password");
                    user = new User(name, password);
                        response.redirect("/");
                        return "";
                })
        );

        Spark.post(
                "/messages",
                ((request, response) -> {
                    messages.add(request.queryParams("message"));
                    m.put("messageList", messages);
                    response.redirect("/");
                    return "";
                })
        );
    }
}
