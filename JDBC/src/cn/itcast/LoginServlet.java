package cn.itcast;
import cn.itcast.dao.AdminsDao;
import cn.itcast.dao.UsersDao;
import cn.itcast.domain.Admin;
import cn.itcast.domain.User;
import cn.itcast.utils.JDBCUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
@WebServlet(name = "LoginServlet",urlPatterns = "/LoginServlet")
public class LoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse
            response)throws ServletException, IOException {
        //设置请求编码、响应方式和编码方式
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        Connection conn = null;
        Statement st = null;
        ResultSet rs = null;
        PreparedStatement ptst = null;
        //获取登录页面提交的数据
        String loginName = request.getParameter("username");
        String loginPassword = request.getParameter("password");
        String userType = request.getParameter("userType"); // 获取用户类型

        try {
            if ("user".equals(userType)) {
                // 普通用户登录
                UsersDao usersDao = new UsersDao();
                User user = usersDao.findUserByUsernameAndPassword(loginName, loginPassword);

                if (user != null) {
                    out.println("<h2>登录成功，欢迎普通用户!</h2>");
                    response.sendRedirect(request.getContextPath() + "/showBooksServlet");
                } else {
                    out.println("<h3 style='color: red;'>用户名或密码错误！</h3>");
                }
            } else if ("admin".equals(userType)) {
                // 管理员登录
                AdminsDao adminDao = new AdminsDao();
                Admin admin = adminDao.findAdminByUsernameAndPassword(loginName, loginPassword);

                if (admin != null) {
                    out.println("<h2>登录成功，欢迎管理员!</h2>");
//                    response.sendRedirect(request.getContextPath() + "/adminDashboard.jsp");
                    response.sendRedirect(request.getContextPath() + "/showBooksServlet");
                } else {
                    out.println("<h3 style='color: red;'>用户名或密码错误！</h3>");
                }
            } else {
                out.println("<h3 style='color: red;'>未知用户类型！</h3>");
            }
        } catch (Exception e) {
            e.printStackTrace();
            out.println("<h3 style='color: red;'>系统异常，请稍后再试！</h3>");
        } finally {
            out.flush();
            out.close();
        }
    }
}
