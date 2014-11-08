<!DOCTYPE html>
<%@ include file="/WEB-INF/jsp/include.jsp"%>
<html>
<head>

<meta http-equiv="content-type" content="text/html; charset=UTF-8">

<title>Admin - All Tracks</title>

<link rel="stylesheet" href="/bootstrap-3.3.0/css/bootstrap.min.css">

<script type="text/javascript" src="/jquery/jquery-1.11.1.min.js"></script>
<script type="text/javascript" src="/jquery-ui-1.11.2.custom/jquery-ui.min.js"></script>
<script type="text/javascript" src="/bootstrap-3.3.0/js/bootstrap.min.js"></script>

</head>
<body>

	<div align="center" class="container">

        <h2>All Tracks</h2>

		<table>
			<thead>
				<tr>
					<th>Id</th>
					<th>User Id</th>
					<th>Name</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${tracks}" var="track">
					<tr>
						<td align="center"><c:out value="${track.id}" /></td>
						<td align="center"><c:out value="${track.userId}" /></td>
						<td align="center"><c:out value="${track.name}" /></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>

	</div>

</body>
</html>