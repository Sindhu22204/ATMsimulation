<%@ page import="AccountDAO" %>
<%
    int accountNumber = (int) session.getAttribute("accountNumber");
    AccountDAO accountDAO = new AccountDAO();
    double balance = accountDAO.getBalance(accountNumber);
%>
<html>
<head>
    <title>ATM Simulation</title>
</head>
<body>
    <h1>Welcome to ATM</h1>
    <h2>Account Number: <%= accountNumber %></h2>
    <h2>Balance: <%= balance %></h2>
    <form action="withdraw.jsp" method="post">
        Amount to Withdraw: <input type="text" name="amount"><br>
        <input type="submit" value="Withdraw">
    </form>
    <form action="deposit.jsp" method="post">
        Amount to Deposit: <input type="text" name="amount"><br>
        <input type="submit" value="Deposit">
    </form>
    <form action="logout.jsp">
        <input type="submit" value="Logout">
    </form>
</body>
</html>