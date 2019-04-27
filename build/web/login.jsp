<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
	<link rel="stylesheet" type="text/css" href="style.css">
	<title>Login</title>
</head>
<body>
	<ul class="navbar">
		<li class="navbar"><a href="index.jsp" class="scale_transition home navbar">Home</a></li>
		<li class="navbar"><a href="register.jsp" class="scale_transition register navbar">Register</a></li>
	</ul>

	<div align="center" class="loginform">
		<p>Login</p>
		<form action="Login" method="post">
			<table>
				<tr>
					<td>
						Username:
					</td>
					<td>
						<input type="text" placeholder="Username" name="name">
					</td>
				</tr>

				<tr>
					<td>
						Password:
					</td>
					<td>
						<input type="Password" placeholder="Password" name="password">
					</td>
				</tr>

				<tr>
					<td>
						<input type="submit" value="Login" name="">
					</td>

					<td>
						<label for="checkbox"><font size="4px">Keep me logged in</label></font><input type="checkbox" name="">
					</td>
				</tr>
			</table>
	
        <p id="login_fail">${message}</p>
        </div>
</body>
</html>