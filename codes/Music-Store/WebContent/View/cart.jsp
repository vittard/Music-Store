<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="entities.Cart"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.HashMap"%>
<%@ page import="entities.Product"%>
<%@ page import="java.util.Iterator"%>
<%@ page import="java.util.Map"%>
<!doctype html>
<html lang="it">
<jsp:include page="fragments/head.jsp">
	<jsp:param name="titlePage" value="Carrello" />
</jsp:include>
<body>

	<jsp:include page="fragments/header.jsp"></jsp:include>
	<jsp:include page="fragments/message.jsp"></jsp:include>


	<section class="card welcome_section">
		<div class="card-body">
			<h1>Carrello</h1>
		</div>
	</section>

	<section class="card">
		<table class="table admin_table">
			<thead>
				<tr class="table-primary">
					<th scope="col">Immagine</th>
					<th scope="col">Nome</th>
					<th scope="col">Prezzo</th>
					<th scope="col">Prezzo di spedizione</th>
					<th scope="col">Quantità</th>
					<th scope="col">Operazioni</th>
				</tr>
			</thead>
			<%
			
				double sumPrice = 0;
				double sumShippingPrice = 0;
			
				@SuppressWarnings("unchecked")
				HashMap<Product, Integer> products = (HashMap<Product, Integer>) request.getAttribute("products");
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
					<td>
						<form style="display: contents;" action="CartOp" method="post">
							<input type="hidden" style="display:none;" name="id" value="<% out.print(product.getId());%>"/>
							<input type="hidden" style="display:none;" name="operation" value="add" />
							<button type="submit" class="btn btn-outline-primary">
							<i class="fas fa-plus-circle"></i>
							</button>
						</form>
						<form style="display: contents;" action="CartOp" method="post">
							<input type="hidden" style="display:none;" name="id" value="<% out.print(product.getId());%>"/>
							<input type="hidden" style="display:none;" name="operation" value="rem" />
							<button type="submit" class="btn btn-outline-primary">
								<i class="fas fa-minus-circle"></i>
							</button>
						</form>
						<form style="display: contents;" action="CartOp" method="post">
							<input type="hidden" style="display:none;" name="id" value="<% out.print(product.getId());%>"/>
							<input type="hidden" style="display:none;" name="operation" value="remAll" />
							<button type="submit" class="btn btn-outline-primary">
							<i class="fas fa-trash-alt"></i>
							</button>
						</form>
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
					<th scope="col"></th>
				</tr>
				<tr>
					<th scope="row"></th>
					<th scope="row"></th>
					<th scope="row">&euro; <% out.print(sumPrice); %></th>
					<th scope="row">&euro; <% out.print(sumShippingPrice); %></th>
					<th scope="row">&euro; <% out.print(sumPrice + sumShippingPrice); %></th>
					<th scope="row"></th>
				</tr>
			</tfoot>
		</table>

		

	</section>
	
	<section class="card">
		<a href="Checkout"><button class="btn btn-outline-primary">Checkout</button></a>
	</section>

	<jsp:include page="fragments/footer.jsp"></jsp:include>
	<jsp:include page="fragments/defaultScripts.jsp"></jsp:include>
</body>
</html>