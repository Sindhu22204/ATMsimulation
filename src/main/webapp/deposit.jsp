<%@ page import="AccountDAO" %>
<%
    int accountNumber = (int) session.getAttribute("accountNumber");
    double amount = Double.parseDouble(request.getParameter("amount"));
    AccountDAO accountDAO = new AccountDAO();
    accountDAO.updateBalance(accountNumber, amount);
    out.println("Deposit successful!");
%>