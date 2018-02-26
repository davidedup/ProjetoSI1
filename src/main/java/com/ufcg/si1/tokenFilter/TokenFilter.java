package com.ufcg.si1.tokenFilter;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureException;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class TokenFilter extends GenericFilterBean {


    @Override
    public void doFilter(ServletRequest servletRequest,
                         ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {

        String token = this.getToken(servletRequest);
        this.NullTokenTest(token);
        this.ValidTokenTest(token);
        filterChain.doFilter(servletRequest, servletResponse);
    }

    private String getToken(ServletRequest servletRequest) {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String header = request.getHeader("Authorization");
        String token = header;

        return token;
    }

    private void NullTokenTest(String token) throws ServletException {
        if (token == null) {
            throw new ServletException("Token inexistente ou inválido");
        }
    }

    private void ValidTokenTest(String token) throws ServletException {
        try {
            Jwts.parser().setSigningKey("pastel").parseClaimsJws(token).getBody();
        } catch (SignatureException e) {
            throw new ServletException("Token inválido");
        }
    }
}
