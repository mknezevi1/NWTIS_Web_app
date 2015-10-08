<%-- 
    Document   : naredbeServeru
    Created on : May 31, 2014, 6:12:49 PM
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
        <title>Naredbe serveru</title>
    </head>
    <body>
        <h1>Naredbe serveru</h1>
        
        <a href="../index.jsp">Pocetna</a><br/>
        <a href="pregledAdresa.jsp">Pregled adresa</a><br/>
        <a href="pregledSvihMeteoPodataka.jsp">Pregled meteo podataka</a><br/>
        <a href="pregledDnevnika.jsp">Pregled dnevnika naredbi serveru</a><br/>
        <a href="">Naredbe serveru</a><br/><br/>
        
        <form action="TestZaREST" method="post">
            Naredba: <input type="text" name="adresa" />
            <input type="submit" name="gumb5" value="NAREDBA" /> 
        </form>
        
</body>
</html>
