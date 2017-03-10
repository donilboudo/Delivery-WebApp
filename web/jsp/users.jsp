<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Utilisateurs</title>
        <%@include file="resources/bootstrap_depandencies"%>
    </head>
    <body onload="activeMenu()">
        <%@include file="resources/menu.html"%>

        <div align="right" style="margin-right: 25px;"><button type="button" class="btn btn-primary btn-md" onclick="openNewUserPage()">Nouveau Utilisateur</button></div>
        <div class="container">
            <h2 align="center">Utilisateurs</h2>
            <c:if test="${users.size() > 0}">
                <table class="table table-hover">
                    <thead>
                    <tr>
                        <th>Prenom</th>
                        <th>Nom</th>
                        <th>Email</th>
                        <th>Telephone</th>
                        <th>Nom d'utilisateur</th>
                        <th>Mot de passe</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${users}" var="user">
                        <tr>
                            <td>${user.firstName}</td>
                            <td>${user.lastName}</td>
                            <td>${user.email}</td>
                            <td>${user.phone}</td>
                            <td>${user.login}</td>
                            <td>${user.password}</td>
                            <td>
                                <div class="btn-group">
                                    <button type="button" class="btn btn-primary">Mise a jour</button>
                                    <button type="button" class="btn btn-danger">Supprimer</button>
                                </div>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </c:if>
        </div>

        <script type="text/javascript">
            function openNewUserPage() {
                window.location.href = '/addUser';
            }

            function deleteUser(id) {
                window.location.href = '/deleteUser/' + id;
            }

            function updateUser(id) {
                window.location.href = '/updateUser/' + id;
            }

            function activeMenu()
            {
                document.getElementById("users").className = "active"
            }
        </script>

    </body>
</html>
