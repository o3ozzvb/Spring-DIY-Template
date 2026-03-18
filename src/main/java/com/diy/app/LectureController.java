package com.diy.app;

import com.diy.framework.web.mvc.controller.Controller;
import com.diy.framework.web.mvc.view.JspView;
import org.jetbrains.annotations.NotNull;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LectureController implements Controller {
    private final LectureRepository lectureRepository;

    public LectureController(LectureRepository lectureRepository) {
        this.lectureRepository = lectureRepository;
    }

    @Override
    public void handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String method = request.getMethod();
        String override = request.getParameter("_method");
        if (override != null) method = override.toUpperCase();

        switch (method) {
            case "GET" -> handleGet(request, response);
            case "POST" -> handlePost(request, response);
            case "PUT" -> handlePut(request, response);
            case "DELETE" -> handleDelete(request, response);
        }
    }

    private void handleGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getRequestURI().contains("/edit")) {
            Long id = Long.valueOf(request.getParameter("id"));
            request.setAttribute("lecture", lectureRepository.findById(id));
            new JspView("lecture-edit.jsp").render(request, response);
        } else {
            request.setAttribute("lectures", lectureRepository.values());
            new JspView("lecture-list.jsp").render(request, response);
        }
    }

    private void handlePost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String title = request.getParameter("title");
        lectureRepository.save(new Lecture(lectureRepository.nextId(), title));
        response.sendRedirect("/lectures");
    }

    private void handlePut(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Long id = Long.valueOf(request.getParameter("id"));
        String title = request.getParameter("title");
        Lecture lecture = lectureRepository.findById(id);
        lecture.setTitle(title);
        lectureRepository.save(lecture);
        response.sendRedirect("/lectures");
    }

    private void handleDelete(HttpServletRequest request, HttpServletResponse response) {

    }
}
