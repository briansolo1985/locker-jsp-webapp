<%@page isErrorPage="true"%>
<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
<head>
<title>Locker Application</title>
<link href="css/design.css" rel="stylesheet" type="text/css" />
</head>
<body>
	<%-- ERROR WINDOW PART --%>
	<form action="index.jsp">
		<div class="errorWindow">
			<div class="errorWindow-title">Error Summary</div>
			<div class="errorWindow-content">
				<c:if test="${not empty pageContext.exception.message}">
					${pageContext.exception.message}
				</c:if>
				<c:if test="${not empty error}">
					${error}
				</c:if>
			</div>
			<div class="errorWindow-buttonbar">
				<input type="submit" value="Back" class="simpleButton" />
			</div>
		</div>
	</form>
</body>
</html>