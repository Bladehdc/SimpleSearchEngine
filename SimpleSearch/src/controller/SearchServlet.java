package controller;

import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.search.highlight.InvalidTokenOffsetsException;

import javafx.util.Pair;
import service.BuildIndex;
/**
 * Servlet implementation class SearchServlet
 */
@WebServlet("/SearchServlet")
public class SearchServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private BuildIndex ab = new BuildIndex();
	private String[] result= null;
	private String oldsearch= null;
	private long timecount = 0; 
	private long pagecount = 0;
	private int pageitem = 0;
	private String[] tempstore = new String[10]; 
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SearchServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	*/
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		// doGet(request, response);
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		// 输入联想
		String addon = (String)request.getParameter("type");
		System.out.println(addon);
		if(addon!=null) {
			request.setAttribute("success", 121);
			PrintStream out = new PrintStream(response.getOutputStream());  
		    response.setContentType("text/html;charSet=utf-8");  
		    out.print("请正常打开此页"); 
		    return;
		}
		
		// 对翻页动作的反应
		String searchString=(String)request.getParameter("searchString");
		String pagestr = request.getParameter("page");
		
		int page = 1;
		if(searchString == "") {
			request.getRequestDispatcher("main.jsp").forward(request, response);
			return;
		}
		if(pagestr != null && searchString.equalsIgnoreCase(oldsearch)) {
			page = Integer.parseInt(pagestr);
			pageitem = 0;
			for(int i=0;i<10;i++) {
				if((page-1)*10+i>=result.length) {
					break;
				}
				tempstore[i] = result[(page-1)*10+i];
				pageitem++;
			}
			request.setAttribute("totalnum", result.length);
			request.setAttribute("times", timecount);
			request.setAttribute("result", tempstore);
			request.setAttribute("pageitem", pageitem);
			request.setAttribute("search", searchString);
			request.setAttribute("totalpage", pagecount);
			request.setAttribute("page", page);
			request.getRequestDispatcher("result.jsp").forward(request, response);
			return;
		}
		
		//对查询动作的反应
		List<Pair<String,String>> ans = null;
		oldsearch = searchString;
		long l1 = System.currentTimeMillis();
		try {
			ans = ab.query(searchString);
		} catch (ParseException | InvalidTokenOffsetsException e) {
			e.printStackTrace();
		}
		
		int totalnum = ab.totalnum;
		result = new String[totalnum];
		int i=0;
		for(i=0;i<totalnum;i++) {
			int id = i;
			String http = "http://www.baidu.com/link?url=1gzANuCS_PqbAVmdXDWg-4qvYJeJN5qdOd4bOboaEXHJ-K_aN0OYPqjQXIkcMXWE8G69lsbPHPMr7MUSTYhZCK&amp;wd=&amp;eqid=e0854c1b002308f6000000035cfa4d02";
			String date = "2019年4月1日";
			String titleinfo = ans.get(i).getKey();
			String detail = ans.get(i).getValue();
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
			result[i]=content;
		}
		long l2 = System.currentTimeMillis();
		// 打包结果
		timecount = l2-l1;
		pagecount = (result.length%10>0)? result.length/10+1:result.length/10;
		pageitem = (result.length>10) ? 10: result.length;
		request.setAttribute("totalnum", result.length);
		request.setAttribute("times", timecount);
		request.setAttribute("pageitem", pageitem);
		request.setAttribute("result", result);
		request.setAttribute("search", searchString);
		request.setAttribute("totalpage", pagecount);
		request.setAttribute("page", page);
		request.getRequestDispatcher("result.jsp").forward(request, response);
	}

}
