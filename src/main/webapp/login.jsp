<%@ page import="AccountDAO" %>
<%
    int accountNumber = Integer.parseInt(request.getParameter("accountNumber"));
    String pin = request.getParameter("pin");
    AccountDAO accountDAO = new AccountDAO();
    boolean authenticated = accountDAO.authenticate(accountNumber, pin);
    if (authenticated) {
        session.setAttribute("accountNumber", accountNumber);
        response.sendRedirect("atm.jsp");
    } else {
        out.println("Invalid account number or PIN");
    }
%>