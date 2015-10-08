<%-- 
    Document   : pregledAdresa
    Created on : May 31, 2014, 6:00:12 PM
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
        <title>Pregled adresa</title>
    </head>
    <body>
        <h1>Pregled adresa</h1>
        
        <a href="../index.jsp">Pocetna</a><br/>
        <a href="">Pregled adresa</a><br/>
        <a href="pregledSvihMeteoPodataka.jsp">Pregled meteo podataka</a><br/>
        <a href="naredbeServeru.jsp">Naredbe serveru</a><br/>
        
        <form action="TestZaREST" method="post">
            Adresa: <input type="text" name="adresa" />
            <input type="submit" name="gumb2" value="GEO" />
            <input type="submit" name="gumb3" value="BAZA" />
            <input type="submit" name="gumb1" value="METEO" /> 
        </form>

        <sql:setDataSource
            var="adrese_podaci"
            driver="${applicationScope.BP_Konfig.driver_database}"
            url="${applicationScope.BP_Konfig.server_database}${applicationScope.BP_Konfig.user_database}"
            user="${applicationScope.BP_Konfig.user_username}"
            password="${applicationScope.BP_Konfig.user_password}"
            />

        <sql:query dataSource="${adrese_podaci}" var="ispis">
            SELECT idAdresa, adresa, latitude, longitude FROM adrese
        </sql:query>


        <table border="1">
            <tr>
                <th>idAdresa</th>
                <th>adresa</th>
                <th>latitude</th>
                <th>longitude</th>
            </tr>
            <c:forEach var="row" items="${ispis.rows}">
                <tr>
                    <td><c:out value="${row.idAdresa}"/></td>
                    <td><c:out value="${row.adresa}"/></td>
                    <td><c:out value="${row.latitude}"/></td>
                    <td><c:out value="${row.longitude}"/></td>
                </tr>
            </c:forEach>
        </table>   

    </body>
</html>
