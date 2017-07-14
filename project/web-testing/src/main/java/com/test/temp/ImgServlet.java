package com.test.temp;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ImgServlet
 * "/getQRCodeImg"
 */
public class ImgServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ImgServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    
	    ServletOutputStream os = response.getOutputStream();
	    
	    response.setContentType("image/jpeg");
	    InputStream is = new FileInputStream(new File("C:\\Users\\wangxiaoxian.ASPIRE\\Pictures\\1.jpg"));
	    
	    byte[] cache = new byte[4096];
	    int size = is.read(cache);
	    while (size != -1) {
	        os.write(cache, 0, size);
	        size = is.read(cache);
        }
	    is.close();
	    os.flush();
	    os.close();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    doGet(request, response);
	}

}
