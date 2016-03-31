<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html>
<html lang="es">
<head>
<meta charset="utf-8">
<title>Courts</title>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css" integrity="sha384-1q8mTJOASx8j1Au+a5WDVnPi2lkFfwwEAa8hDDdjZlpLegxhjVME1fgjWPGmkzs7" crossorigin="anonymous">
</head>

<body>
    <H1>Court list</H1>
    <div class="container">
    <div class="row">
    <div class="col-md-8">
	<table border="1" class="table table-striped">
		<thead>
			<tr>
				<th>Court</th>
				<th>Status</th>
				<th></th>
				
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${courts}" var="court">
				<tr>
					<td>${court.courtId}</td>
					<c:if test="${court.active}"><td class="success">Active</td></c:if>
					<c:if test="${!court.active}"><td class="danger">Inactive</td></c:if>
					<td>
						<form:form action="courts/${court.courtId}/changestatus">
							<input class="btn btn-default" type="submit" value="Change status">
						</form:form>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	
	
	<form:form action="courts">
		<input class="btn btn-default" type="submit" value="Add new court">
	</form:form>
	<br><br>
	
	
	<p><a href="<c:url value='/home'/>">Home</a></p>
</div>
</div>
</div>
</body>
</html>