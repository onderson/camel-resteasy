package org.apache.camel.component.resteasy.servlet;


import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author : Roman Jakubco (rjakubco@redhat.com)
 */
@WebFilter("/*")
public class ResteasyResponseFilter implements Filter {

    @Override
    public void init(FilterConfig config) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        if (response.getCharacterEncoding() == null) {
            response.setCharacterEncoding("UTF-8"); // Or whatever default. UTF-8 is good for World Domination.
        }

        ResteasyHttpServletResponseWrapper responseCopier = new ResteasyHttpServletResponseWrapper((HttpServletResponse) response);

        try {
            chain.doFilter(request, responseCopier);

            responseCopier.flushBuffer();
        } finally {
            byte[] copy = responseCopier.getCopy();

            ((HttpServletResponse) response).setHeader("filter", "Filter Test");
//            System.out.println("!!!!!!!!!!! Response content filter logging:  " + new String(copy, response.getCharacterEncoding())); // Do your logging job here. This is just a basic example.
        }
    }

    @Override
    public void destroy() {

    }


}