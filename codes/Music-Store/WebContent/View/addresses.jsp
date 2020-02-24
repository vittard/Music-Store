<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="entities.Address"%>
<%@ page import="java.util.ArrayList"%>

<!doctype html>
<html lang="it">
<jsp:include page="fragments/head.jsp">
	<jsp:param name="titlePage" value="Indirizzi" />
</jsp:include>
<body>

	<jsp:include page="fragments/header.jsp"></jsp:include>
	<jsp:include page="fragments/message.jsp"></jsp:include>

	<section class="card welcome_section">
		<div class="card-body">
			<h1>Elenco Indirizzi</h1>
		</div>
	</section>
	<section class="card" style="padding: 5px;">
		
		<a href="Address" style="margin-top: 5px;"><button
				class="btn btn-outline-primary">
				<i class="fas fa-plus-circle"></i> Aggiungi nuovo indirizzo
			</button></a>
	</section>

	<section class="card product_section">

		<%
			@SuppressWarnings("unchecked")
			ArrayList<Address> addresses = (ArrayList<Address>) request.getAttribute("addresses");
			if (addresses != null || addresses.size() > 0) {
				for (Address address : addresses) {
		%>


		<div class="card product_div">
			<div class="card-body">
				<p>
					Destinatario: <span> <%
							out.print(address.getName() + " " + address.getSurname());
						%>
					</span>
				</p>
				<p>
					Indirizzo:<br>
					<%
						out.print(address.getStreet());
					%><br>
					<%
						out.print(address.getCity());
					%>
					(
					<%
						out.print(address.getProvince());
					%>),
					<%
						out.print(address.getZipCode());
					%>
				</p>
				<a href="Address?id=<%out.print(address.getId());%>"><button
						class="btn btn-outline-primary">Visualizza</button></a> 
				<a href="Remove?id=<%out.print(address.getId());%>&type=Address&prev=MyAddresses"><button
						class="btn btn-outline-primary">Rimuovi</button></a>
			</div>
		</div>

		<%
			}
			}
		%>
		<div>
			<%
				int pag = (Integer) request.getAttribute("page");
				if (pag > 1) {
			%>
			<a
				href="MyAddresses?page=<%out.print((pag) - 1);%>&min=<%out.print(((Integer) request.getAttribute("min")) - 20);%>">
				<button>
					<i class="fas fa-arrow-circle-left"></i>
				</button>
			</a>
			<%
				}
			%>
			<p style="text-align: center">
				<%
					out.print(pag);
				%>
			</p>


			<%
				if (request.getAttribute("next") != null) {
			%>
			<a
				href="MyAddresses?page=<%out.print((pag) + 1);%>&min=<%out.print(((Integer) request.getAttribute("min")));%>">
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