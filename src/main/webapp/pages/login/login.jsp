<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>Login - Fintrack</title>
<link rel="stylesheet" href="login.css" />
</head>
<body>
	<div class="login-container">
		<div class="login-box">
			<h1>Welcome To Fintrack!</h1>
			<p class="divider">Log in with your email</p>

			<form action="<%= request.getContextPath() %>/LoginServlet"
				method="post">
				<input type="email" placeholder="Email" id="username"
					name="username" required />
				<div class="password-container">
					<input type="password" placeholder="Password" id="password"
						name="password" required /> <span id="togglePassword"><ion-icon
							class="login-icon" name="eye-outline"></ion-icon></span>
				</div>
				
				<button type="submit" class="login-btn">Login</button>
				  <%
					    String errorMessage = (String) session.getAttribute("errorMessage");
					    if (errorMessage != null) {
					        out.print("<p class='error' style='color:red'>" + errorMessage + "</p>");
					        session.removeAttribute("errorMessage");
					    }
					%>
			</form>

			<a href="../forgot-password/forgot-password.html" class="forgot-password">Forgot password?</a>

			<p class="do-not-have-account">Don't have an account?</p>
			<button onclick="redirectToSignup()" class="login-btn">Sign up</button>

			<script>
				function redirectToSignup() {
					window.location = "<%= request.getContextPath() %>/pages/signup/signup.jsp";
				}
			</script>

			<div class="back">
				<a href="<%= request.getContextPath() %>/pages/index.jsp">

					<div>
						<ion-icon class="backTohome-icon" name="arrow-back-circle-outline"></ion-icon>
					</div>
					<div class="back-to-home">Back To Home</div>
				</a>
			</div>
		</div>
	</div>
	</div>
	<script>
		document.getElementById("togglePassword").addEventListener("click",
				function() {
					const passwordField = document.getElementById("password");
					if (passwordField.type === "password") {
						passwordField.type = "text";
					} else {
						passwordField.type = "password";
					}
				});
	</script>

	<script type="module"
		src="https://unpkg.com/ionicons@7.1.0/dist/ionicons/ionicons.esm.js"></script>
	<script nomodule
		src="https://unpkg.com/ionicons@7.1.0/dist/ionicons/ionicons.js"></script>
</body>
</html>
