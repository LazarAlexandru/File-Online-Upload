<!DOCTYPE html>
<html>
<head>
	<link rel="stylesheet" type="text/css" href="style.css">
	<title>Register</title>
</head>
<body>
	<ul class="navbar">
		<li class="navbar"><a href="index.jsp" class="scale_transition home navbar">Home</a></li>
		<li class="navbar"><a href="login.jsp" class="scale_transition login navbar">Login</a></li>
	</ul>

	<div align="center" class="registerform">
		<p>Register</p>
		<form action="Register" method="post" >
			<table>
				<tr>
					<td>
						Name :
					</td>

					<td>
						<input class="input" type="text" placeholder="Name" name="name" required="required">
					</td>
				</tr>
				<tr>
					<td>
						Password :
					</td>
					<td>
						<input type="Password" class="input" placeholder="Password" name="password" required="required">
					</td>
				</tr>
				<tr>
					<td>
						Gender :
					</td>
					<td>
						<select name="gender" required="required">
						<option>Male
						<option>Female
					</select>
					</td>
				</tr>
				<tr>
					<td>
						Email :
					</td>
					<td>
						<input class="input" type="mail" placeholder="Email" name="email" required="required">
					</td>
				</tr>
			</table>
			<tr>
				<input type="submit" value="Submit" name="" class="submit">
			</tr>
		</form>
	</div>
</body>
</html>