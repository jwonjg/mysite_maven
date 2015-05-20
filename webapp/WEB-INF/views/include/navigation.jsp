<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<ul>
	<c:choose>
		<c:when test="${ param.type eq 'board' }">
			<li>정진원</li>
			<li><a href="${pageContext.request.contextPath}/guestbook">방명록</a></li>
			<li class="selected"><a href="${pageContext.request.contextPath}/board">게시판</a></li>
		</c:when>
		<c:when test="${ param.type eq 'guestbook' }">
			<li>정진원</li>
			<li class="selected"><a href="${pageContext.request.contextPath}/guestbook">방명록</a></li>
			<li><a href="${pageContext.request.contextPath}/board">게시판</a></li>
		</c:when>
		<c:otherwise>
			<li class="selected">정진원</li>
			<li><a href="${pageContext.request.contextPath}/guestbook">방명록</a></li>
			<li><a href="${pageContext.request.contextPath}/board">게시판</a></li>
		</c:otherwise>
	</c:choose>
</ul>