package controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class SearchServlet
 */
@WebServlet("/SearchServlet")
public class SearchServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SearchServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}*/

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		// doGet(request, response);
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		// 预备工作
		
		// 查询开始
		long l1 = System.currentTimeMillis();
		String searchString=(String)request.getParameter("searchString");
		String pagestr = request.getParameter("page");
		
		int page = 1;
		if(pagestr != null) {
			page = Integer.parseInt(pagestr);
		}
		
		int id = 1;
		String http = "http://www.baidu.com/link?url=1gzANuCS_PqbAVmdXDWg-4qvYJeJN5qdOd4bOboaEXHJ-K_aN0OYPqjQXIkcMXWE8G69lsbPHPMr7MUSTYhZCK&amp;wd=&amp;eqid=e0854c1b002308f6000000035cfa4d02";
		String date = "2019年4月1日";
		String titleinfo = "<em>java</em>\n" + 
				"web 与\n" + 
				"<em>jsp</em>\n" + 
				"页面的\n" + 
				"<em>交互</em>\n" + 
				"流程 (初次接触时写) - lin_zone - 博客园";
		String detail = "<em>java</em>\r\n" + 
				"web 与\r\n" + 
				"<em>jsp</em>\r\n" + 
				"页面的\r\n" + 
				"<em>交互</em>\r\n" + 
				"流程 \r\n" + 
				"<em>java</em>\r\n" + 
				"web项目目录 1. \r\n" + 
				"<em>java</em>\r\n" + 
				"web项目的一般目录:  2. \r\n" + 
				"<em>jsp</em>\r\n" + 
				"页面一般情况下放在 top(前台页面) back(后台页面) 3. 后台代码 放...";
		String[] result= new String[11];
		String content = "<div class=\"result\" id="+ id 
				+"\" data-click=\"{&quot;rsv_bdr&quot;:&quot;0&quot;,&quot;p5&quot;:1}\">"
				+"<h3 class=\"t\">"
				+"<a href="+ http +" target=\"_blank\">"
				+titleinfo+"</a></h3>"
				+"<div class=\"ablock\">\r\n" 
				+"<span class=\"newTimeFactor_before_abs m\">"+date+"&nbsp;-&nbsp;</span>"
				+detail+"</div>"
				+"<div class=\"f13\">\n" 
				+"<a href=\"http://www.baidu.com/link?url=1gzANuCS_PqbAVmdXDWg-4qvYJeJN5qdOd4bOboaEXHJ-K_aN0OYPqjQXIkcMXWE8G69lsbPHPMr7MUSTYhZCK\" class=\"showurl\" style=\"text-decoration:none;\">\n" 
				+"https://www.cnblogs.com/zhuche...&nbsp;\n"
				+"</a>\n"
				+"</div>\n"
				+"</div>"; 
		for(int i=0;i<11;i++) {
			result[i]=content;
		}
		long l2 = System.currentTimeMillis();
		// 打包结果
		request.setAttribute("totalnum", result.length);
		request.setAttribute("times", l2-l1);
		request.setAttribute("result", result);
		request.setAttribute("search", searchString);
		request.setAttribute("page", page);
		request.getRequestDispatcher("result.jsp").forward(request, response);
	}

}
