package com.example.demo;

import javax.servlet.http.HttpServletResponse;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * A simple controller to handle error pages. This has some silly messages for a couple of basic
 * common errors. Obviously those kinds of messages wouldn't go to production...
 * 
 * @author Roy Cunningham
 *
 */
@Controller
public class ErrorPageController implements ErrorController {

  @Override
  public String getErrorPath() {
    return "/error";
  }

  @RequestMapping("/error")
  public String handleError(HttpServletResponse response) {

    String ret = "something has gone terribly wrong...  very sorry. HTTP ERROR CODE: " + response.getStatus();

    if (response.getStatus() == HttpStatus.NOT_FOUND.value()) {
      ret = "So sorry - can't find what you're looking for";
    }

    if (response.getStatus() == HttpStatus.BAD_REQUEST.value()) {
      ret = "looks like you've sent us some bad data there partner...";
    }

    return ret;
  }



}
