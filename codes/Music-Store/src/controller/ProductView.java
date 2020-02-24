package controller;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import entities.Product;
import manager.DriverManagerConnectionPool;
import manager.ProductManager;

/**
 * Servlet implementation class ProductView
 */
@WebServlet("/ProductView")
public class ProductView extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ProductView() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
    	String code = request.getParameter("id");
    	if ( code != null ) {
			
			DriverManagerConnectionPool dmcp = new DriverManagerConnectionPool();
			
			ProductManager productManager = new ProductManager(dmcp);
			
			Product product = null;
			
			try {
				
				product = productManager.doRetrieveByKey(code);
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			if ( product!=null ) {
				request.setAttribute("product", product);
				RequestDispatcher rd = getServletContext().getRequestDispatcher("/View/product.jsp");
				rd.forward(request, response);
			} else {
				request.setAttribute("error", "Impossibile raggiungere il prodotto richiesto");
			}
			
    		
    	} else {
    		request.setAttribute("error", "Impossibile raggiungere il prodotto richiesto");
    	}
    	
    	
    	RequestDispatcher rd = getServletContext().getRequestDispatcher("/View/product.jsp");
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
