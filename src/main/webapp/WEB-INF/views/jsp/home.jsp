<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html>
<html lang="es">
<head>
<meta charset="utf-8">
<title>Home</title>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css" integrity="sha384-1q8mTJOASx8j1Au+a5WDVnPi2lkFfwwEAa8hDDdjZlpLegxhjVME1fgjWPGmkzs7" crossorigin="anonymous">

</head>
<body>
	<h1>Paddle Menu</h1>
	<br>
	<div class="container">
		<div class="row">
			<div class="col-md-4">
				<table border="1" class="table table-striped">
					<tbody>
							<tr>
								<td><a href="<c:url value='/users' />">Users</a></td>
							</tr>
							<tr>
								<td><a href="<c:url value='/courts' />">Courts</a></td>
							</tr>
							<tr>
								<td><a href="<c:url value='/trainings' />">Trainings</a></td>
							</tr>
						
					</tbody>
				</table>
			</div>
		</div>
	</div>

</body>
</html>