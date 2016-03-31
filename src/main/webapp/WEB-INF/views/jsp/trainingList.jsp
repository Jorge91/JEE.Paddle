<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html lang="es">
<head>
<meta charset="utf-8">
<title>Trainings</title>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css" integrity="sha384-1q8mTJOASx8j1Au+a5WDVnPi2lkFfwwEAa8hDDdjZlpLegxhjVME1fgjWPGmkzs7" crossorigin="anonymous">
</head>

<body>
    <H1>Training list</H1>
    <div class="container">
    <div class="row">
    <div class="col-md-8">
	<table border="1" class="table table-striped">
		<thead>
			<tr>
				<th>Date</th>
				<th>Court</th>
				<th>Trainer</th>
				
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${trainings}" var="training">
				<tr>
					<td><fmt:formatDate value="${training.startDate.time}" type="date" pattern="dd/MM/yyyy" /></td>
					<td>${training.court.id}</td>
					<td>${training.trainer.username}</td>
				
				</tr>
			</c:forEach>
		</tbody>
	</table>
	
	<p><a href="<c:url value='/create-training'/>">Add training</a></p>
	<p><a href="<c:url value='/home'/>">Home</a></p>
</div>
</div>
</div>
</body>
</html>