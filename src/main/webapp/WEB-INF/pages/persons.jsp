<?xml version="1.0" encoding="UTF-8" ?>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="from" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page session="false" %>
<html>
<head>
    <title>Persons</title>

    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css"
          integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
    <style type="text/css">
        * {
            margin: 0;
            padding: 0;
        }

        html,
        body {
            height: 100%;
        }

        .wrapper {
            position: relative;
            min-height: 100%;
        }

        .content {
            padding-bottom: 90px;
        }

        .footer {
            position: absolute;
            left: 0;
            bottom: 0;
            width: 100%;
            height: 80px;
        }
    </style>
</head>
<body>
<div class="container">
    <div class="wrapper">
        <div class="content">
            <a href="../../index.jsp">Back to main menu </a>
            <br/>
            <br/>

            <h3><a href="../persons"><spring:message code="person.personregister"/></a></h3>
            <br/>
            <p><spring:message code="person.message"/>: ${inn}</p>
            <br/>
            <c:if test="${!empty listPersons}">
                <table class="table table-striped table-sm">
                    <tr>
                        <th><spring:message code="person.id"/></th>
                        <th><spring:message code="person.dateBirth"/></th>
                        <th><spring:message code="person.surname"/></th>
                        <th><spring:message code="person.name"/></th>
                        <th><spring:message code="person.patronymic"/></th>
                        <th><spring:message code="person.edit"/></th>
                        <th><spring:message code="person.delete"/></th>
                        <th><spring:message code="person.innfl"/></th>
                        <th><spring:message code="person.innresult"/></th>
                    </tr>
                    <c:forEach items="${listPersons}" var="person">
                        <tr>
                            <td>${person.id}</td>
                            <td>
                                <fmt:formatDate value="${person.personDateBirth}" pattern="dd.MM.yyyy"/>
                            </td>
                            <td>${person.personSurname}</td>
                            <td>${person.personName}</td>
                            <td>${person.personPatronymic}</td>
                            <td><a href="<c:url value='/edit/${person.id}'/>"><spring:message code="person.edit"/></a>
                            </td>
                            <td><a href="<c:url value='/remove/${person.id}'/>"><spring:message
                                    code="person.delete"/></a></td>
                            <td><c:if test="${person.personIdRequest == null}">
                                <a href="<c:url value='/inn/${person.id}'/>">
                                    <input type="submit" class="btn btn-outline-primary btn-sm"
                                           value="<spring:message code="person.request"/>"></a>
                            </c:if>
                                <c:if test="${person.personIdRequest != null}">
                                    <c:if test="${person.personInn == null}"><a href="../persons">
                                        <input type="submit" class="btn btn-outline-warning btn-sm"
                                               value="<spring:message code="person.processing"/>"></a></c:if>
                                    <c:if test="${person.personInn != null}"><input type="submit"
                                                                                    class="btn btn-outline-success btn-sm"
                                                                                    value="<spring:message code="person.ready"/>"></c:if>
                                </c:if>
                            </td>
                            <td>${person.personInn}</td>
                        </tr>
                    </c:forEach>
                </table>
            </c:if>
            <br/>
            <br/>

            <%--Форма для редактирования/добавления человека--%>
            <div class="row">
                <div class="col-md-4">
                    <h3><spring:message code="person.addperson"/></h3>
                    <br>
                    <c:url var="addAction" value="/persons/add"/>
                    <form:form action="${addAction}" commandName="person">

                        <table class="table table-striped table-sm">
                            <c:if test="${!empty person.personDateBirth}">
                                <tr>
                                    <td>
                                        <form:label path="id">
                                            <spring:message code="person.id"/>
                                        </form:label>
                                    </td>
                                    <td>
                                        <form:input path="id" readonly="true" size="8" disabled="true"
                                                    class="form-control"/>
                                        <form:hidden path="id"/>
                                    </td>
                                </tr>
                            </c:if>
                            <tr>
                                <td>
                                    <form:label path="personDateBirth">
                                        <spring:message code="person.dateBirth"/>
                                    </form:label>
                                </td>
                                <td>
                                    <form:input path="personDateBirth" type="date" class="form-control"/>
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <form:label path="personSurname">
                                        <spring:message code="person.surname"/>
                                    </form:label>
                                </td>
                                <td>
                                    <form:input path="personSurname" class="form-control"/>
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <form:label path="personName">
                                        <spring:message code="person.name"/>
                                    </form:label>
                                </td>
                                <td>
                                    <form:input path="personName" class="form-control"/>
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <form:label path="personPatronymic">
                                        <spring:message code="person.patronymic"/>
                                    </form:label>
                                </td>
                                <td>
                                    <form:input path="personPatronymic" class="form-control"/>
                                </td>
                            </tr>

                            <tr>
                                <td colspan="2">
                                    <c:if test="${!empty person.personDateBirth}">
                                        <input type="submit" class="btn btn-outline-primary btn-sm"
                                               value="<spring:message code="person.editperson"/>"/>
                                    </c:if>

                                    <c:if test="${empty person.personDateBirth}">
                                        <input type="submit" class="btn btn-outline-primary btn-sm"
                                               value="<spring:message code="person.addperson"/>"/>
                                    </c:if>
                                </td>
                            </tr>

                        </table>
                    </form:form>
                </div>
            </div>
        </div>


        <div class="footer">
            © 2018 Copyright «FBochkarev» / Project: maven - spring - JPA(hibernate) - postgresSQL application v1.2
        </div>
    </div>
</div>
</div>
</body>
</html>
