package com.lambdaschool.school;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableWebMvc
@EnableJpaAuditing
@SpringBootApplication
public class SchoolApplication
{

private static final Logger logger = LoggerFactory.getLogger(SchoolApplication.class);
private static boolean stop = false;

@Autowired
private static Environment env;

private static void checkEnvironmentVariable(String envvar)
{
    if (System.getenv(envvar) == null)
    {
        logger.error("Environment Variable " + envvar + " missing");
        stop = true;
    }
}

    ///youre going to want to capture the application context here
    //needs to be spring framework context
    ///allows us yo do things to the spring application once it runs

    public static void main(String[] args) {
        checkEnvironmentVariable("OAUTHCLIENTID");
        checkEnvironmentVariable("OAUTHCLIENTSECRET");

        if (!stop) {

            ApplicationContext ctx = SpringApplication.run(SchoolApplication.class, args);

            //servlet is a piece of code running on backend that handles web communications for us
            //provides a service
            ///only time we will interact with dispatcherservlet
            DispatcherServlet dispatcherServlet = (DispatcherServlet) ctx.getBean("dispatcherServlet");
            ///wanting to find the dispatcherServlet bean
            //got to typecast to a dispatcherServlet
            dispatcherServlet.setThrowExceptionIfNoHandlerFound(true);
        }
    }

}
