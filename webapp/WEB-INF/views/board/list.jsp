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
				<form action="${pageContext.request.contextPath}/board/insert" method="post" enctype="multipart/form-data">
					<table>
						<tr>
							<td>제목</td><td><input type="text" name="title"></td>
						</tr>
						<tr>
							<td>첨부파일</td><td><input type="file" name="file"></td>
						</tr>
						<tr>
							<td colspan=2><textarea name="content" id="content"></textarea></td>
						</tr>
						<tr>
							<td colspan=4 align=right><input type="submit" VALUE=" 확인 "></td>
						</tr>
					</table>
				</form>
				<br>
				<form action="${pageContext.request.contextPath}/board/search" method="post">
					<table>
						<tr>
							<td>게시물 검색</td>
							<td>
								<select name="searchOption">
									<option value="both" selected="selected">제목+내용</option>
									<option value="title">제목</option>
									<option value="content">내용</option>
								</select>
								<input type="text" name="keyword">
								<input type="submit" value="찾기">
							</td>
						</tr>
					</table>
				</form>
				<br>
				<table>
					<tr>
						<th>글쓴이</th>
						<th>제목</th>
						<th>첨부파일</th>
						<th>조회수</th>
						<th>작성일</th>
					</tr>
				<c:forEach var="item" items="${ requestScope.list }">
					<tr>
						<td>${ pageScope.item.userName}</td>
						<td><a href="${pageContext.request.contextPath}/board/detail/${ pageScope.item.no}">${ pageScope.item.title}</a></td>
						<c:choose>
						<c:when test="${ not empty pageScope.item.fileName }">
							<td>첨부파일 있음</td>
						</c:when>
						<c:otherwise>
							<td>첨부파일 없음</td>
						</c:otherwise>
						</c:choose>
						<td>${ pageScope.item.clicks }</td>
						<td>${ pageScope.item.regDate}</td>
					</tr>
				</c:forEach>
				</table>
				<br>
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