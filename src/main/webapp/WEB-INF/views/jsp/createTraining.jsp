<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html>
<html lang="es">
<head>
<meta charset="utf-8">
<title>Create training</title>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css" integrity="sha384-1q8mTJOASx8j1Au+a5WDVnPi2lkFfwwEAa8hDDdjZlpLegxhjVME1fgjWPGmkzs7" crossorigin="anonymous">

</head>
<body>
	<h1>Create Training</h1>
	<div class="container">
		<div class="row">
			<div class="col-md-8">
	<form:form method="post" action="create-training" modelAttribute="training">
		<p>Court ID:
			<form:input class="form-control" path="court" placeholder="Court" />
		</p>
		<p>Trainer username:
			<form:input class="form-control" path="trainerName" placeholder="Trainer" />
		</p>
		<p>Start Date: (dd/MM/yyyy)
			<form:input class="form-control" path="startDate" placeholder="Start Date" />
		</p>
		
		<p><input class="btn btn-default" type="submit" value="Crear"></p>
	</form:form>
			</div>
		</div>
	</div>

	<a href="<c:url value="/home"/>">Home</a>

</body>
</html>