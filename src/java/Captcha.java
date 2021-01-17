/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import javax.servlet.http.HttpSession;

/**
 *
 * @author fatma
 */
@WebServlet(urlPatterns = {"/Captcha"})
public class Captcha extends HttpServlet {

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

        response.setContentType("image/jpg");

        int iTotalChar = 6, iHeight = 40, iWidth = 150, iCircle = 15;

        Font style1 = new Font("Arial", Font.BOLD, 30);
        Font style2 = new Font("Verdana", Font.BOLD, 20);

        Random randchar = new Random();
        String sImageCode = (Long.toString(Math.abs(randchar.nextLong()), 36).substring(0, iTotalChar));
        BufferedImage biImage = new BufferedImage(iWidth, iHeight, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2dImage = (Graphics2D) biImage.getGraphics();

        for (int i = 0; i < iCircle; i++) {
            g2dImage.setColor(new Color(randchar.nextInt(225), randchar.nextInt(225), randchar.nextInt(225)));
            int iRadius = (int) (Math.random() * iHeight / 0.2);
            int iX = (int) (Math.random() * iWidth - iRadius);
            int iY = (int) (Math.random() * iHeight - iRadius);
        }

        g2dImage.setFont(style1);

        for (int i = 0; i < iTotalChar; i++) {
            g2dImage.setColor(new Color(randchar.nextInt(225), randchar.nextInt(225), randchar.nextInt(225)));
            if (i % 2 == 0) {
                g2dImage.drawString(sImageCode.substring(i, i + 1), 25 * i, 24);
            } else {
                g2dImage.drawString(sImageCode.substring(i, i + 1), 25 * i, 35);
            }
        }

        OutputStream osImage = response.getOutputStream();
        ImageIO.write(biImage, "jpeg", osImage);

        g2dImage.dispose();
        HttpSession s = request.getSession();
        s.setAttribute("captcha", sImageCode);

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
