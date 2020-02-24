<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="entities.*"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.HashMap"%>
<%@ page import="entities.Product"%>
<%@ page import="java.util.Iterator"%>
<%@ page import="java.util.Map"%>
<!doctype html>
<html lang="it">
<jsp:include page="fragments/head.jsp">
	<jsp:param name="titlePage" value="Check Out" />
</jsp:include>
<body>

	<jsp:include page="fragments/header.jsp"></jsp:include>
	<jsp:include page="fragments/message.jsp"></jsp:include>


	<section class="card welcome_section">
		<div class="card-body">
			<h1>Checkout</h1>
		</div>
	</section>

	<section class="card">
		
		
		<% 
			@SuppressWarnings("unchecked")
			ArrayList<Address> addresses = (ArrayList<Address>) request.getAttribute("addresses");
			@SuppressWarnings("unchecked")
			ArrayList<Payment> payments = (ArrayList<Payment>) request.getAttribute("payments");
			Cart cart = (Cart) session.getAttribute("cart");
		%>
		
	<form class="px-4 py-3" action="Success" method="post">
		<div class="form-group">
			<select name="address">
				<% for (Address address : addresses) { %>
				<option value="<% out.print(address.getId()); %>"><% out.print(address.getName() + " " + address.getSurname() + "<br>" + address.getStreet() + ", " + address.getCity() + " " + address.getProvince() + address.getZipCode()); %></option>
				<% } %>
			</select>
		</div>
			<div class="form-group">
			<select name="payment">
				<% for (Payment payment : payments) { %>
				<option value="<% out.print(payment.getId()); %>"><% 
				String details = "" + payment.getCardNumber().substring(0, 4) + " **** **** "
						+ payment.getCardNumber().substring(payment.getCardNumber().length() - 5, payment.getCardNumber().length() - 1);
		 		out.print(payment.getName() + " " + payment.getSurname() + "<br>" + details); %></option>
				<% } %>
			</select>
		</div>
		
		<table class="table admin_table">
			<thead>
				<tr class="table-primary">
					<th scope="col">Immagine</th>
					<th scope="col">Nome</th>
					<th scope="col">Prezzo</th>
					<th scope="col">Prezzo di spedizione</th>
					<th scope="col">Quantità</th>
				</tr>
			</thead>
			<%
			
				double sumPrice = 0;
				double sumShippingPrice = 0;
			
				HashMap<Product, Integer> products = cart.getProducts();
				Iterator<Map.Entry<Product, Integer>> it = products.entrySet().iterator();
				while (it.hasNext()) {
					Map.Entry<Product, Integer> e = it.next();
					Product product = e.getKey();
					Integer quantity = e.getValue();
			%>


			<tbody>
				<tr>
					<td>
						<img style="max-height: 150px;max-width: 150px;" src="<% out.print(product.getImagePath()); %>">
					</td>
					<th scope="row">
						<%
							out.print(product.getName());
						%>
					</th>
					<td>&euro; 
						<%
							out.print(product.getPrice());
							sumPrice = sumPrice + ( product.getPrice() * quantity );
						%>
					</td>
					<td>&euro; 
						<%
							out.print(product.getShippingPrice());
							sumShippingPrice = sumShippingPrice + ( product.getShippingPrice() * quantity);
						%>
					</td>
					<td>
						<%
						out.print(quantity);
						%>
					</td>
				</tr>

				<%
					}
					
				%>

			</tbody>
			<tfoot>
				<tr class="table-primary">
					<th scope="col">Totale</th>
					<th scope="col"></th>
					<th scope="col">Prezzo</th>
					<th scope="col">Prezzo di spedizione</th>
					<th scope="col">Prezzo + Spedizione</th>
				</tr>
				<tr>
					<th scope="row"></th>
					<th scope="row"></th>
					<th scope="row">&euro; <% out.print(sumPrice); %></th>
					<th scope="row">&euro; <% out.print(sumShippingPrice); %></th>
					<th scope="row">&euro; <% out.print(sumPrice + sumShippingPrice); %></th>
				</tr>
			</tfoot>
		</table>
		
		<section class="card">
		<button type="submit" class="btn btn-outline-primary">Conferma Ordine</button>
	</section>
		
	</form>
		

	</section>

	<jsp:include page="fragments/footer.jsp"></jsp:include>
	<jsp:include page="fragments/defaultScripts.jsp"></jsp:include>

</body>
</html>