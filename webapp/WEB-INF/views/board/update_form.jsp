<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.List" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!doctype html>
<html>
<head>
<title>mysite</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link href="${pageContext.request.contextPath}/assets/css/guestbook.css" rel="stylesheet" type="text/css">
</head>
<body>
	<div id="container">
		<div id="header">
			<jsp:include page="/WEB-INF/views/include/header.jsp" flush="false"/>
		</div>
		<div id="content">
			<div id="guestbook">
				<form action="${pageContext.request.contextPath}/board/update" method="post" enctype="multipart/form-data">
					<input type="hidden" name="no" value="${ requestScope.board.no }">
					<table>
						<tr>
							<td>제목</td><td><input name="title" value="${ requestScope.board.title }"></td>
						</tr>
						<tr>
							<td>첨부파일</td>
							<td>
							${ requestScope.board.fileName }<br>
							<input type="hidden" name="prevFileName" value="${ requestScope.board.fileName }">
							<input type="file" name="file"> 
							</td>
						</tr>
						<tr>
							<td colspan=2><textarea name="content" id="content">${ requestScope.board.content }</textarea></td>
						</tr>
						<tr>
							<td colspan=4 align=right>
								<input type="submit" value="수정 등록">
							</td>
						</tr>
					</table>
				</form>
			</div>
		</div>
		<div id="navigation">
			<c:import url="/WEB-INF/views/include/navigation.jsp">
				<c:param name="type" value="board"></c:param>
			</c:import>
		</div>
		<div id="footer">
			<p>(c)opyright 2014 </p>
		</div>
	</div>
</body>
</html>