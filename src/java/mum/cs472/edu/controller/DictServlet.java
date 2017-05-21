/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mum.cs472.edu.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import mum.cs472.edu.DB.DbConnection;
import org.json.simple.JSONObject;
import org.json.simple.JSONArray;

/**
 *
 * @author Chandara Leang
 */
public class DictServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */

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
        JSONObject dictionaryData = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        
        Connection conn = null;
        DbConnection dbConn = new DbConnection();
        String term = request.getParameter("term");
        try {
            conn = dbConn.openConnection();
            String sql = "SELECT * FROM entries WHERE word LIKE ? ORDER BY word";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1,term);
            ResultSet result = ps.executeQuery();
            
            while (result.next()) {
                JSONObject obj = new JSONObject();
                obj.put("word", result.getString("word"));
                obj.put("wordType", result.getString("wordtype"));
                obj.put("definition", result.getString("definition"));
                jsonArray.add(obj);
            }
            
            result.close();
            ps.close();
            dbConn.closeConnection(conn);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        dictionaryData.put("dictionaryData",jsonArray);
        PrintWriter out = response.getWriter();
        out.println(dictionaryData);
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
