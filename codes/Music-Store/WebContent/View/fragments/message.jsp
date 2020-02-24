
<%
  	boolean error = false;
	if (request.getAttribute("error")!=null) {
		error = (boolean) request.getAttribute("error");
	} else {
		error = false;
	}
	String message = (String) request.getAttribute("message"); 
	if(message!=null && !message.equals("")) {
%>
<section class="card welcome_section">
	<div class="card-body">
		<h3  id="message" <% if (error) out.print("style=\"color:red;\""); else out.print("style=\"color:green;\""); %>>
			<%
					out.print(message);
				%>
		</h3>
	</div>
</section>
<% } else { %>
<section class="card welcome_section" style="display: none;" id="sectionMessage">
	<div class="card-body">
		<h3  id="message" >		</h3>
	</div>
</section>
<% } %>