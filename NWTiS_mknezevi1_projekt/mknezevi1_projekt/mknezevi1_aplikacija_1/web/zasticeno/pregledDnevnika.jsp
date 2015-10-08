<%-- 
    Document   : pregledDnevnika
    Created on : Jun 7, 2014, 9:02:24 AM
    Author     : Matija
--%>

<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="java.io.*,java.util.*,java.sql.*"%>
<%@ page import="javax.servlet.http.*,javax.servlet.*" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Pregled dnevnika naredbi serveru</title>
    </head>
    <body>
        <h1>Pregled dnevnika naredbi serveru</h1>
        
        <a href="../index.jsp">Pocetna</a><br/>
        <a href="pregledAdresa.jsp">Pregled adresa</a><br/>
        <a href="">Pregled meteo podataka</a><br/>
        <a href="naredbeServeru.jsp">Naredbe serveru</a><br/>
        
        <sql:setDataSource
            var="meteo_podaci"
            driver="${applicationScope.BP_Konfig.driver_database}"
            url="${applicationScope.BP_Konfig.server_database}${applicationScope.BP_Konfig.user_database}"
            user="${applicationScope.BP_Konfig.user_username}"
            password="${applicationScope.BP_Konfig.user_password}"
            />

        <sql:query dataSource="${meteo_podaci}" var="ispis">
            SELECT id, naredba, vrijeme FROM dnevnik
        </sql:query>

        <table border="1">
            <tr>
                <th>ID</th>
                <th>vrijeme</th>
                <th>naredba</th>
            </tr>
            <c:forEach var="row" items="${ispis.rows}">
                <tr>
                    <td><c:out value="${row.id}"/></td>
                    <td><c:out value="${row.vrijeme}"/></td>
                    <td><c:out value="${row.naredba}"/></td>
                </tr>
            </c:forEach>
        </table>       
            
    </body>
</html>
