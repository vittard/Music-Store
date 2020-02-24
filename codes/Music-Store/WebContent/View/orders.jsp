<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="entities.Order"%>
<%@ page import="java.util.ArrayList"%>

<!doctype html>
<html lang="it">
<jsp:include page="fragments/head.jsp">
	<jsp:param name="titlePage" value="Ordini" />
</jsp:include>
<body>

	<jsp:include page="fragments/header.jsp"></jsp:include>
	<jsp:include page="fragments/message.jsp"></jsp:include>

	<section class="card welcome_section">
		<div class="card-body">
			<h1>Elenco Ordini Effettuati</h1>
		</div>
	</section>

	<section class="card">
		<table class="table admin_table">
			<thead>
				<tr class="table-primary">
					<th scope="col">ID</th>
					<th scope="col">Stato</th>
					<th scope="col">Data</th>
					<th scope="col">Operazioni</th>
				</tr>
			</thead>
			<%
				@SuppressWarnings("unchecked")
				ArrayList<Order> orders = (ArrayList<Order>) request.getAttribute("orders");
				if (orders != null) {
					for (Order o : orders) {
			%>


			<tbody>
				<tr>
					<th scope="row">
						<%
							out.print(o.getId());
						%>
					</th>
					<td>
						<%
							out.print(o.getState());
						%>
					</td>
					<td>
						<%
							out.print(o.getDate());
						%>
					</td>
					<td>
						<a href="Order?id=<% out.print(o.getId()); %>">
							<button>
								<i class="fas fa-eye"> Visualizza Ordine</i>
							</button>
						</a>
					</td>
				</tr>

				<%
					}
					}
				%>

			</tbody>
		</table>
		<div>
			<%
				int pag = (Integer) request.getAttribute("page");
				if (pag > 1) {
			%>
			<a
				href="MyOrders?page=<%out.print((pag) - 1);%>&min=<%out.print(((Integer) request.getAttribute("min")) - 20);%>">
				<button>
					<i class="fas fa-arrow-circle-left"></i>
				</button>
			</a>
			<%
				}
			%>
			<p style="text-align: center">
				<%out.print(pag);%>
			</p>


			<%
				if (request.getAttribute("next") != null) {
			%>
			<a
				href="MyOrders?page=<%out.print((pag) + 1);%>&min=<%out.print(((Integer) request.getAttribute("min")));%>">
				<button>
					<i class="fas fa-arrow-circle-right"></i>
				</button>
			</a>
			<%
				}
			%>

		</div>

	</section>

	<jsp:include page="fragments/footer.jsp"></jsp:include>


	<jsp:include page="fragments/defaultScripts.jsp"></jsp:include>
</body>
</html>