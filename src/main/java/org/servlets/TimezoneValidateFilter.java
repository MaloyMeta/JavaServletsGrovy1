package org.servlets;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import static org.servlets.ParseUTC.parseUTC;

@WebFilter("/time/*")
public class TimezoneValidateFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        ParseUTC parseUTC = new ParseUTC();
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
            throws IOException, ServletException {
        String notFormatedUTC = req.getParameter("timezone");

        if (notFormatedUTC != null){
            try{
                String formattedUTC = parseUTC(notFormatedUTC); //Форматуємо для правильного формату offset
                req.setAttribute("formattedUTC", formattedUTC); //передаємо на сервлет відформатований атрибут
                chain.doFilter(req,resp); // пропускаємо

        } catch(IllegalArgumentException e){
            HttpServletResponse httpResponse = (HttpServletResponse) resp;
            httpResponse.sendError(HttpServletResponse.SC_BAD_REQUEST, "Невірний UTC формат: " + e.getMessage());
        }
    }else{
            HttpServletResponse httpResponse = (HttpServletResponse) resp;
            httpResponse.sendError(HttpServletResponse.SC_BAD_REQUEST, "Обов'язковий параметр UTC!");
         }
    }

}
