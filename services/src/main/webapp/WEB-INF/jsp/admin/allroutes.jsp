<!DOCTYPE html>
<%@ include file="/WEB-INF/jsp/include.jsp"%>
<html>
<head>

<meta http-equiv="content-type" content="text/html; charset=UTF-8">

<title>Admin - Routes</title>

<link rel="stylesheet" href="/bootstrap-3.3.0/css/bootstrap.min.css">
<link rel="stylesheet" href="/css/admin.css">

<script type="text/javascript" src="/jquery/jquery-1.11.1.min.js"></script>
<script type="text/javascript" src="/jquery-ui-1.11.2.custom/jquery-ui.min.js"></script>
<script type="text/javascript" src="/bootstrap-3.3.0/js/bootstrap.min.js"></script>

</head>
<body>

    <%@ include file="/WEB-INF/jsp/admin/admin-top-bar.jsp"%>

	<div align="center" class="container">

        <h2>Routes</h2>

		<table class="table table-striped table-condensed table-bordered table-hover">
			<thead>
				<tr>
					<th>Id</th>
					<th>Route Name</th>
					<th># of Stops</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${routes}" var="route">
					<tr>
						<td align="center"><c:out value="${route.id}" />&nbsp&nbsp<a href="/admin/routes/${route.id}">Edit</a></td>
						<td align="center"><c:out value="${route.name}" /></td>
						<td align="center"><c:out value="${fn:length(route.routeStops)}" /></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>

	</div>

</body>
</html>