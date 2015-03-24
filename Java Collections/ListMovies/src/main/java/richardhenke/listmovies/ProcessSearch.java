/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package richardhenke.listmovies;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import static java.lang.System.out;
import java.net.URL;
import java.util.List;
import java.util.Map;
/**
 *
 * @author richard
 */
@WebServlet(name = "ProcessSearch", urlPatterns = {"/ProcessSearch"})
public class ProcessSearch extends HttpServlet {
    
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        URL url = new URL("http://www.omdbapi.com/?s=" + request.getParameter("title").replace(" ", "+"));
        
        
        ObjectMapper mapper = new ObjectMapper();
        
        Map<String, Object> map = mapper.readValue(url, Map.class);
        
        List list = (List)map.get("Search");
        
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet ProcessSearch</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Search Results</h1>");
            
            out.println("<ul>");
            for (Object item : list)
            {
                Map<String, Object> innerMap = (Map<String, Object>)item;
                out.println("<h3>");
                out.println("<a href='http://www.imdb.com/title/" + innerMap.get("imdbID") + "' target='_blank'>");
                out.println(innerMap.get("Title"));
                out.println("</a><h3>");
                for (String key : innerMap.keySet())
                {
                    //out.println("<li>");
                    out.println(key + ": " + innerMap.get(key));
                    //out.println("</li>");
                }
            }
            out.println("</ul>");
            out.println("</body>");
            out.println("</html>");
        }
    }
    
    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
    
    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
    
    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
    
}
