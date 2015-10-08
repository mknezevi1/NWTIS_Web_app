<%-- 
    Document   : pregledSvihMeteoPodataka
    Created on : May 31, 2014, 5:43:43 PM
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
        <title>Pregled svih meteo podataka</title>
    </head>
    <body>
        <h1>Pregled svih meteo podataka</h1>
        
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
            SELECT temperatura, vlaga, vjetar, tlak FROM mknezevi1_meteo
        </sql:query>

        <table border="1">
            <tr>
                <th>Temperatura</th>
                <th>Vlaga</th>
                <th>Vjetar</th>
                <th>Tlak</th>
            </tr>
            <c:forEach var="row" items="${ispis.rows}">
                <tr>
                    <td><c:out value="${row.temperatura}"/></td>
                    <td><c:out value="${row.vlaga}"/></td>
                    <td><c:out value="${row.vjetar}"/></td>
                    <td><c:out value="${row.tlak}"/></td>
                </tr>
            </c:forEach>
        </table>       
            
    </body>
</html>
