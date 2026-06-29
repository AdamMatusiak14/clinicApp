package ad.clinic.security;

import java.io.IOException;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import ad.clinic.service.CombinedUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@Component
public class JwtFilter extends OncePerRequestFilter {

  @Autowired
    private JwtTokenProvider jwtTokenProvider;
    private CombinedUserDetailsService userDetailsService;
   

    public JwtFilter(JwtTokenProvider jwtTokenProvider, CombinedUserDetailsService userDetailsService) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.userDetailsService = userDetailsService;
       
      
    }   

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        System.out.println("Nagłówki: " + Collections.list(request.getHeaderNames()));
        String authHeader = request.getHeader("Authorization");

      

        try{
        // If Authorization header is missing or doesn't contain a Bearer token, skip
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            logger.warn("Brak lub nieprawidłowy nagłówek Authorization");
            filterChain.doFilter(request, response);
            return;
        }
        if (authHeader == null) {
            logger.warn("Brak nagłówka Authorization");
            filterChain.doFilter(request, response);
            return;
        }

        String token = authHeader.substring(7); // usuwa "Bearer "

        if(!jwtTokenProvider.validateToken(token)){
            logger.warn("Invalid Token");
           filterChain.doFilter(request, response);
            return;
        }

         String username = jwtTokenProvider.extractUsername(token);

        if(username == null){
            logger.warn("Nie można uzyskać nazyw użytkownika z tokena");
            filterChain.doFilter(request, response);
            return;
        }

        UserDetails userDetails = null;

            try{
                userDetails = userDetailsService.loadUserByUsername(username);
            }
            catch (UsernameNotFoundException e){
                
            {
                    logger.warn("Username not found in CombinedUserDetailsService: " + username);   
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    return; 
                }
            }
    
         

            if(userDetails == null){ 
                logger.warn("UserDetails could not be loaded for username: " + username);
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }

            if(SecurityContextHolder.getContext().getAuthentication() == null){
                UsernamePasswordAuthenticationToken auth =
                        new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities());                    
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        }

        catch (NullPointerException ex){
            logger.error("Null pointer exception: " + ex.getMessage());
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        catch (Exception e){
            logger.error("Exception while loading user details: " + e.getMessage());
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

     
   

        filterChain.doFilter(request, response); 
      

}
}
    

