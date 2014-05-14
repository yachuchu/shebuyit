
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<c:choose>
  <c:when test="${sessionScope.GLOBAL_LANGUAGE==null||sessionScope.GLOBAL_LANGUAGE==''}">
     <fmt:setLocale value="zh"/>
  </c:when>
  <c:otherwise>
     <fmt:setLocale value="${sessionScope.GLOBAL_LANGUAGE}"/>
  </c:otherwise>
</c:choose>
<fmt:setBundle basename="message" />

