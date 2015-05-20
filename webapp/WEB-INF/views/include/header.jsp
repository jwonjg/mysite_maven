<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
	<h1>MySite</h1>
	<ul>
		<c:choose>
			<c:when test="${ empty sessionScope.authMember }">
				<li><a href="${pageContext.request.contextPath}/member/login">로그인</a><li>
				<li><a href="${pageContext.request.contextPath}/member/join">회원가입</a><li>
			</c:when>
			<c:otherwise>
				<li><a href="${pageContext.request.contextPath}/member/logout">로그아웃</a><li>
				<li><a href="${pageContext.request.contextPath}/member/memberinfo">회원정보수정</a><li>
				<li>${ sessionScope.authMember.name }님 안녕하세요 ^^;</li>
			</c:otherwise>
		</c:choose>
	</ul>