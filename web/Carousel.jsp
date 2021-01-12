<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.apex.form.DataAccess" %>
<%@ page import="com.google.gson.Gson" %>
<%@ page import="com.google.gson.JsonArray" %>
<%@ page import="com.google.gson.JsonParser" %>
<%@ page import="java.sql.*" %>
<%@ page import="com.apex.bank.sftp.DB2Handle" %>
<!DOCTYPE html>
<%
	String id = request.getParameter("id");//用request得到
	String interval = request.getParameter("FInterval");//用request得到
	if(interval==null||interval==""){
		interval="4000";
	}
	Connection connection = null;
	String jsonstring="";
	try {
		//Establish connection
		//connection = DataAccess.getDataSource().getConnection();
		connection = DB2Handle.getConnection();
		List<Map<String, Object>> urlList = new ArrayList<Map<String, Object>>();
		String sql = "select * from DC_Carousel_FsrcLink WHERE DC_CAROUSEL_ID="+id;
		Statement ps = connection.createStatement();

		ps.executeQuery(sql);
		ResultSet rs = (ResultSet) ps.getResultSet();
		ResultSetMetaData md = rs.getMetaData();
		int columnCount = md.getColumnCount();
		while (rs.next()) {
			Map<String, Object> rowData = new HashMap<String, Object>();
			for (int i = 1; i <= columnCount; i++) {
				rowData.put(md.getColumnName(i), rs.getObject(i));
			}
			urlList.add(rowData);
		}

		 jsonstring = new Gson().toJson(urlList);
	}catch (SQLException e) {
		e.printStackTrace();
	}finally{
		if(connection!=null){
			System.out.println("Connected successfully.");
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}


%>
<html lang="zh">
<head>
	<meta charset="UTF-8" />
	<meta name="viewport" content="width=device-width, initial-scale=1.0" />
	<meta http-equiv="X-UA-Compatible" content="ie=edge" />
	<script type="text/javascript" src="js/jquery-1.8.3.js"></script>
<%--	<script type="text/javascript" src="js/js.js"  charset="utf-8"></script>
	<link rel="stylesheet" type="text/css" href="css/css.css"/>--%>
	<script type="text/javascript" src="lunbo/js.js"  charset="utf-8"></script>
	<link rel="stylesheet" type="text/css" href="lunbo/css.css"/>
	<title>js幻灯片轮播</title>
</head>
<body>
	<div id="boxhdp">
		<ul id="img">
			<%--<li class="current"><iframe src="/plug-in/bank/dsc/cockpit/chart.jsp?chart=5" width="100%" height="380px" scrolling="no"  frameborder=0></iframe></li>
			<li><iframe src="/plug-in/bank/dsc/cockpit/chart.jsp?chart=6" width="100%" height="380px" scrolling="no"  frameborder=0></iframe></li>--%>
		</ul>
		<ul id="li">
			<li class="on"></li>
			<li></li>
		</ul>
		<div style="clear: both;"></div>
	</div>	
	<script type="text/javascript">
		function CreateCarousel(dataArry) {
			var carouselHtml='';
			var carousefootlHtml='';

			for(i=0;i<dataArry.length;i++){
				var list= dataArry[i];
				var srcUrl=list["FSRCURL"];
				var width =list['FWIDTH'];
				var height=list['FHEIGHT'];
				carouselHtml=carouselHtml+'<li><iframe src="'+srcUrl+'"  width="'+width+'" height="'+height+'"  scrolling="no"  frameborder=0></iframe></li>';
				if(i==0){
				 carouselHtml='<li class="current"><iframe src="'+srcUrl+'"  width="'+width+'" height="'+height+'"  scrolling="no"  frameborder=0></iframe></li>';
				}

				carousefootlHtml=carousefootlHtml+'<li></li>';
				if(i==0){
					carousefootlHtml='<li class="on"></li>';
				}
			}
			$("#img").html(carouselHtml);
			$("#li").html(carousefootlHtml);
		}

		$(document).ready(function () {
			var dataArry=<%=jsonstring%>;
			CreateCarousel(dataArry)

			hdp({
				li:"li",	//默认值：li;默认用li包裹
				boxid:"boxhdp",	//最外面div  id
				imgid:"img",	//图片外面id
				optid:"li",	//opt外面id
				an:"an",		//左右按钮id，用于移上显示和隐藏
				prev:"prev",	//左边箭头id
				next:"next",	//右边箭头id
				ms: <%=interval%>		//多少毫秒切换一张,默认800毫秒
			});
		});


	</script>
</body>
</html>