<%@ include file="common/header.jspf"%>
<%@ include file="common/navigation.jspf"%>

<div class="container">
    <H1>List of Users:</H1>

    <table border="1">
        <thead>
        <tr>
            <th>Id</th><th>Name</th><th>Surname</th><th>Email address</th><th>Phone number</th><th>Address</th><th>Password</th><th>UPDATE</th><th>DELETE</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${users}" var="user">
            <tr>
                <td>${user.id}</td>
                <td>${user.name}</td>
                <td>${user.surname}</td>
                <td>${user.emailAddress}</td>
                <td>${user.phoneNumber}</td>
                <td>${user.address}</td>
                <td>${user.password}</td>
                <td><a type="button" href="/update-user/${user.id}">UPDATE</a></td>
                <td><a type="button" href="/delete-user/${user.id}">DELETE</a></td>
            </tr>
        </c:forEach>

        </tbody>
    </table>

    <div>
        <a href="add-user">ADD User</a>
    </div>
</div>
<%@ include file="common/footer.jspf"%>