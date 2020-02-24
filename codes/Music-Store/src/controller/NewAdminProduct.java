package controller;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import entities.Product;
import entities.User;
import manager.DriverManagerConnectionPool;
import manager.LoginManager;
import manager.ProductManager;

/**
 * Servlet implementation class NewAdminProduct
 */
@WebServlet("/NewAdminProduct")
public class NewAdminProduct extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public NewAdminProduct() {
        super();
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		HttpSession session = request.getSession();
		if (session.getAttribute("user")!=null) { // an unser is logged
			User user = (User) session.getAttribute("user");
			LoginManager manager = new LoginManager(user);
			if (manager.checkCatalogAdmin()) { // if user is an Catalog Manager
				UploadProduct upload = new UploadProduct(request, response);
				
				Product product = upload.doUpload();
				
				System.out.println(product);
				
				if (product != null) {

					DriverManagerConnectionPool dmcp = new DriverManagerConnectionPool();
					ProductManager productManager = new ProductManager(dmcp);
					try {
						productManager.doSave(product);
						request.setAttribute("message", "Categoria aggiunta con successo");
					} catch (SQLException e) {
						e.printStackTrace();
						request.setAttribute("message", "Impossibile aggiungere la categoria");
						request.setAttribute("error", true);
					}
					RequestDispatcher rd = getServletContext().getRequestDispatcher("/AdminProducts");
					rd.forward(request, response);

				} else {
					request.setAttribute("message", "Alcuni dei parametri insierti non sono validi");
					request.setAttribute("error", true);

					RequestDispatcher rd = getServletContext().getRequestDispatcher("/AdminProduct");
					rd.forward(request, response);
				}
			} else {	//this user isn't an CatalogManager
				request.setAttribute("message", "Devi essere uno Gestore Catalogo per accedere a quella pagina");
				request.setAttribute("error", true);
				RequestDispatcher rd = getServletContext().getRequestDispatcher("/Logout");
				rd.forward(request, response);

			}
		} else {	//there isn't an user logged
			request.setAttribute("message", "Alcuni dei parametri insierti non sono validi");
			request.setAttribute("error", true);

			RequestDispatcher rd = getServletContext().getRequestDispatcher("/Logout");
			rd.forward(request, response);

		}
	}

	private boolean isValid(String s) {
		if ( s!=null && !s.equals("") ) {
			if ( !s.contains("<") || s.contains(">") || s.contains("$") || s.contains("?") ) {
				return true;
			}
		}
		return false;
	}
	
	public class UploadProduct {
		HttpServletRequest request; 
		HttpServletResponse response;
		private Product product;
		
		public UploadProduct(HttpServletRequest request, HttpServletResponse response) {
			this.request = request;
			this.response = response;
		};
		
		public Product doUpload() throws IOException {
			product = null;
			String file_name = null;
			response.setContentType("text/html");
			boolean isMultipartContent = ServletFileUpload.isMultipartContent(request);
			if (!isMultipartContent) {
				System.out.println("No multipart");
				return null;
			}
			FileItemFactory factory = new DiskFileItemFactory();
			ServletFileUpload upload = new ServletFileUpload(factory);
			try {
				List < FileItem > fields = upload.parseRequest(request);
				Iterator < FileItem > it = fields.iterator();
				if (!it.hasNext()) {
					System.out.print("No next");
					return null;
				}
				while (it.hasNext()) {
					FileItem fileItem = it.next();
					boolean isFormField = fileItem.isFormField();
					if (isFormField) {
						if (file_name == null) {
							if (fileItem.getFieldName().equals("name") && isValid(fileItem.getString("name")) ) {
								if (product == null ) {
									product = new Product();
								}
								product.setName(fileItem.getString());
								System.out.println(product.getName());
								
							} else if (fileItem.getFieldName().equals("description") && isValid(fileItem.getString("description"))) {
								if (product == null ) {
									product = new Product();
								}
								product.setDescription(fileItem.getString());
								
							} else if (fileItem.getFieldName().equals("price") && isValid(fileItem.getString("price"))) {
								if (product == null ) {
									product = new Product();
								}
								product.setPrice( Double.valueOf( fileItem.getString("price") ) );
								
							} else if (fileItem.getFieldName().equals("shippingPrice") && isValid(fileItem.getString("shippingPrice"))) {
								if (product == null ) {
									product = new Product();
								}
								product.setShippingPrice( Double.valueOf( fileItem.getString("shippingPrice") ) );
								
							} else if (fileItem.getFieldName().equals("disponibility") && isValid(fileItem.getString("disponibility"))) {
								if (product == null ) {
									product = new Product();
								}
								product.setDisponibility( Integer.valueOf( fileItem.getString("disponibility") ) );
								
							} else if (fileItem.getFieldName().equals("disponibility") && isValid(fileItem.getString("id"))) {
								if (product == null ) {
									product = new Product();
								}
								product.setShippingPrice( Integer.valueOf( fileItem.getString("disponibility") ) );
								
							} else if (fileItem.getFieldName().equals("id") && isValid(fileItem.getString("id")) ) {
								if (product == null ) {
									product = new Product();
								}
								product.setShippingPrice( Integer.valueOf( fileItem.getString("id") ) );
								
							} else if (fileItem.getFieldName().equals("imagePath") && isValid(fileItem.getString("imagePath"))) {
								if (product == null ) {
									product = new Product();
								}
								product.setImagePath(fileItem.getString("imagePath"));
								file_name = product.getImagePath();
							}
						}
					} else {
						if (fileItem.getSize() > 0) {
							fileItem.write(new File("E:\\Users\\Vittorio\\eclipse-workspace\\Music-Store\\WebContent\\productImage\\" + fileItem.getName()));
							System.out.println("Ci sono");
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
			}
		
		return product;
		}
	}

}
