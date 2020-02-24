<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="entities.Payment"%>
<%@ page import="java.util.ArrayList"%>

<!doctype html>
<html lang="it">
<jsp:include page="fragments/head.jsp">
	<jsp:param name="titlePage" value="Metodi di Pagamento" />
</jsp:include>
<body>

	<jsp:include page="fragments/header.jsp"></jsp:include>
	<jsp:include page="fragments/message.jsp"></jsp:include>

	<section class="card welcome_section">
		<div class="card-body">
			<h1>I miei metodi di pagamento</h1>
		</div>
	</section>

	<section class="card" style="padding: 5px;">
		<a href="Payment" style="margin-top: 5px;"><button class="btn btn-outline-primary">
				<i class="fas fa-plus-circle"></i> Aggiungi nuovo metodo
			</button></a>
	</section>

	<section class="card product_section">

		<%
			@SuppressWarnings("unchecked")
			ArrayList<Payment> payments = (ArrayList<Payment>) request.getAttribute("payments");
			if (payments != null || payments.size() > 0) {
				for (Payment payment : payments) {
		%>


		<div class="card product_div">
			<div class="card-body">
				<p>
					<%
						String details = "" + "Intestatario: " + payment.getName() + " " + payment.getSurname() + "<br>"
										+ "Numero carta:<br>" + payment.getCardNumber().substring(0, 4) + " **** **** "
										+ payment.getCardNumber().substring(payment.getCardNumber().length() - 5,
												payment.getCardNumber().length() - 1)
										+ "<br>" + "Scadenza carta:<br>" + payment.getExpiryDate();
								out.print(details);
					%>
				</p>
				<a
					href="Remove?id=<%out.print(payment.getId());%>&type=Payment&prev=MyPayments"><button
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
				href="MyPayments?page=<%out.print((pag) - 1);%>&min=<%out.print(((Integer) request.getAttribute("min")) - 20);%>">
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
				href="MyPayments?page=<%out.print((pag) + 1);%>&min=<%out.print(((Integer) request.getAttribute("min")));%>">
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