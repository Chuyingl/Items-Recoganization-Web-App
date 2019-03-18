package rpc;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import db.DBConnection;
import db.DBConnectionFactory;
import entity.Item;

/**
 * Servlet implementation class login
 */
@WebServlet("/login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Login() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		DBConnection conn = DBConnectionFactory.getConnection();
		try {
			// the parameter false, if there is a session return, is not, return null
			JSONObject obj =new JSONObject();
			HttpSession session =request.getSession(false);
			if(session!=null) {
                String userId=session.getAttribute("user_id").toString();
                obj.put("status","OK").put("user_id",userId).put("name",conn.getFullname(userId));
			}else {
				response.setStatus(403);
				obj.put("status","Session Invalid");
			}
			
			RpcHelper.writeJsonObject(response, obj);
		} catch (JSONException e) {
			e.printStackTrace();
		} finally {
			conn.close();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		DBConnection connection = DBConnectionFactory.getConnection();
		 try {
	  		 JSONObject input = RpcHelper.readJSONObject(request);
	  		 String userId= input.getString("user_id");
	  		 String password =input.getString("password");
	  		 JSONObject obj =new JSONObject();
	  		 if(connection.verifyLogin(userId, password)){
	  			 //create new session
	  			 HttpSession session= request.getSession();
	  			 session.setAttribute("user_id", userId);
	  			 session.setMaxInactiveInterval(600);
	  			 obj.put("status", "OK").put("user_id", userId).put("name", connection.getFullname(userId));
	  		 }
	  		 else {
	  			 response.setStatus(401);
	  			 obj.put("status", "User Doesn't Exists");
	  		 }
	  		 RpcHelper.writeJsonObject(response, obj);
	  		
	  	 } catch (Exception e) {
	  		 e.printStackTrace();
	  	 } finally {
	  		 connection.close();
	  	 }
	}

}
