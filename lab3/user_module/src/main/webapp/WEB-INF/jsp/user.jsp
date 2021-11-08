<%@ include file="common/header.jspf"%>
<%@ include file="common/navigation.jspf"%>

<div class="container">
    <p>Add new user:</p>
    <form:form method="post" modelAttribute="user">

        <form:input path="id" type="hidden" required="required" />
        <form:errors path="id" />

        <form:label path="name">Name</form:label>
        <br/>
        <form:input path="name" type="text" required="required" />
        <form:errors path="name" />
        <br/>
        <br/>
        <form:label path="surname">Surname</form:label>
        <br/>
        <form:input path="surname" type="text" required="required" />
        <form:errors path="surname" />
        <br/>
        <br/>
        <form:label path="emailAddress">Email address</form:label>
        <br/>
        <form:input path="emailAddress" type="text" required="required" />
        <form:errors path="emailAddress" />
        <br/>
        <br/>
        <form:label path="phoneNumber">Phone number</form:label>
        <br/>
        <form:input path="phoneNumber" type="text" required="required" />
        <form:errors path="phoneNumber" />
        <br/>
        <br/>
        <form:label path="address">Address</form:label>
        <br/>
        <form:input path="address" type="text" required="required" />
        <form:errors path="address" />
        <br/>
        <br/>
        <form:label path="password">Password</form:label>
        <br/>
        <form:input path="password" type="text" required="required" />
        <form:errors path="password" />

        <br/>
        <br/>
        ${error}
        <br/>
        <br/>
        <button type="submit">OK</button>
    </form:form>
</div>
<%@ include file="common/footer.jspf"%>