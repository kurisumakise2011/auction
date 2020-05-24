package com.auction.game.controller;

import com.auction.game.model.ErrorModel;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

@Controller
public class ErrorControllerPage implements ErrorController {

    @RequestMapping("/error")
    public String handleError(HttpServletRequest request, Model model) {
        ErrorModel errorModel = new ErrorModel();
        errorModel.setStatus((Integer) request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE));
        errorModel.setMessage((String) request.getAttribute(RequestDispatcher.ERROR_MESSAGE));
        errorModel.setRequestURI((String) request.getAttribute(RequestDispatcher.ERROR_REQUEST_URI));

        if (errorModel.getStatus() == null) {
            errorModel.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        }

        errorModel.setTitle(HttpStatus.valueOf(errorModel.getStatus()).getReasonPhrase());

        model.addAttribute("error", errorModel);
        return "error";
    }

    @Override
    public String getErrorPath() {
        return "/error";
    }
}
