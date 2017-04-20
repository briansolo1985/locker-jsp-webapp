<%@ page contentType="text/html" pageEncoding="UTF-8"
	errorPage="error.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<jsp:useBean id="lockerControl"
	class="com.epam.tutorial.lockerapp.control.LockerControl" />

<html>
<head>
<title>Locker Application</title>
<link href="css/design.css" rel="stylesheet" type="text/css" />
</head>
<body>
	<c:choose>
		<%-- LOGIN PART --%>
		<c:when test="${empty sessionScope.loginUser}">
			<form action="LockerServlet">
				<div class="loginWindow">
					<div class="loginWindow-title">Login Window</div>
					<div class="loginWindow-content">
						Login name: <input type="hidden" name="funcName" value="login" />
						<input type="text" name="funcParam" />
					</div>
					<div class="loginWindow-buttonbar">
						<input type="submit" value="Sign In" class="simpleButton" />
					</div>
					<div class="loginWindow-highlight">
						Available users:
						<c:forEach items="${lockerControl.users}" var="user"
							varStatus="sts">
							<c:if test="${not user.loggedIn}"> 
								${user.id}<c:if test="${not sts.last}">, </c:if>
							</c:if>
						</c:forEach>
					</div>
				</div>

			</form>
		</c:when>
		<%-- LOGGED IN PART --%>
		<c:otherwise>
			<%-- SESSION FUNCTIONS --%>
			<div class="headerBar">
				<div class="headerBar-title">Greetings,
					${sessionScope.loginUser.name}!</div>
				<div class="headerBar-banner">Locker Manager Application</div>	
				<div class="headerBar-buttonContainer">	
					<form action="LockerServlet">
						<input type="hidden" name="funcName" value="logout" /> <input
							type="submit" value="Sign Out" class="headerButton" />
					</form>
				</div>
				<div class="headerBar-buttonContainer">
					<form action="index.jsp">
						<input type="submit" value="Refresh" class="headerButton" />
					</form>
				</div>
			</div>

			<%-- LIST OF LOCKERS --%>
			<c:forEach items="${lockerControl.lockers}" var="locker">
				<%-- DEFAULT VALUES --%>
				<c:set var="lockerOwnerPlateClass" value="locker-ownerPlate-open"
					scope="request" />
				<c:set var="lockerText" value="FREE" scope="request" />
				<c:set var="funcName" value="reserve" scope="request" />
				<c:set var="funcParam" value="${locker.id}" scope="request" />
				<c:set var="submitValue" value="Reserve" scope="request" />
				<c:set var="submitVisib" value="true" scope="request" />

				<%-- RESERVE COLOR --%>
				<c:if test="${locker.reserved}">
					<c:set var="lockerOwnerPlateClass" value="locker-ownerPlate-closed"
						scope="request" />
					<c:set var="lockerText" value="${locker.owner}" scope="request" />
					<%-- MY LOCKER --%>
					<c:if test="${sessionScope.loginUser.locker.id == locker.id}">
						<c:set var="lockerOwnerPlateClass" value="locker-ownerPlate-mine"
							scope="request" />
						<c:set var="lockerText" value="MINE" scope="request" />
					</c:if>
				</c:if>

				<%-- SUBMIT BUTTON NAME AND VALUE AND VISIBILITY --%>
				<c:if test="${locker.reserved}">
					<c:set var="submitVisib" value="false" scope="request" />
					<%-- MY LOCKER --%>
					<c:if test="${sessionScope.loginUser.locker.id == locker.id}">
						<c:set var="funcName" value="release" scope="request" />
						<c:set var="funcParam" value="${locker.id}" scope="request" />
						<c:set var="submitValue" value="Release" scope="request" />
						<c:set var="submitVisib" value="true" scope="request" />
					</c:if>
				</c:if>

				<%-- FORM ASSEMBLER --%>
				<form action="LockerServlet">
					<div class="locker">
						<div class="locker-namePlate">${locker.name}</div>
						<div class="${lockerOwnerPlateClass}">${lockerText}</div>
						<c:if test="${submitVisib}">
							<div class="locker-buttonContainer">
								<input type="hidden" name="funcName" value="${funcName}" /> <input
									type="hidden" name="funcParam" value="${funcParam}" /> <input
									type="submit" value="${submitValue}" class="simpleButton" />
							</div>
						</c:if>
					</div>
				</form>
			</c:forEach>
		</c:otherwise>
	</c:choose>
</body>
</html>