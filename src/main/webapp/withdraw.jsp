<%@ page import="AccountDAO" %>
<%
    int accountNumber = (int) session.getAttribute("accountNumber");
    double amount = Double.parseDouble(request.getParameter("amount"));
    AccountDAO accountDAO = new AccountDAO();
    double balance = accountDAO.getBalance(accountNumber);
    if (balance >= amount) {
        accountDAO.updateBalance(accountNumber, -amount);
        out.println("Withdrawal successful!");
    } else {
        out.println("Insufficient balance");
    }
%>