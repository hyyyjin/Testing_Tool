<%@ page language="java" contentType="text/html; charset=EUC-KR"
	pageEncoding="EUC-KR"%>
<%@ page import="java.sql.*"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="Welcome to MIR Lab">
<meta name="author" content="webThemez.com">
<title>Welcome to MIR Lab</title>
<link rel="favicon" href="assets/images/favicon.png">
<link rel="stylesheet" media="screen"
	href="http://fonts.googleapis.com/css?family=Open+Sans:300,400,700">
<link rel="stylesheet" href="assets/css/bootstrap.min.css">
<link rel="stylesheet" href="assets/css/font-awesome.min.css">
<!-- Custom styles for our template -->
<link rel="stylesheet" href="assets/css/bootstrap-theme.css"
	media="screen">
<link rel="stylesheet" href="assets/css/style.css">
<!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
<!--[if lt IE 9]>
	<script src="assets/js/html5shiv.js"></script>
	<script src="assets/js/respond.min.js"></script>
	<![endif]-->
</head>

<body>

	<%
		Class.forName("com.mysql.jdbc.Driver");
		String url = "jdbc:mysql://localhost:3306/board"; //ip address and DBName
		String id = "root"; // DB USER
		String pass = "1129"; // DB Password

		int total = 0;

		try {
			Connection conn = DriverManager.getConnection(url, id, pass);
			Statement stmt = conn.createStatement();

			String sqlCount = "SELECT COUNT(*) FROM board";
			ResultSet rs = stmt.executeQuery(sqlCount);

			if (rs.next()) {
				total = rs.getInt(1);
			}
			rs.close();

			String sqlList = "SELECT NUM, USERNAME, TITLE, TIME, HIT from board order by NUM DESC";
			rs = stmt.executeQuery(sqlList);
	%>


	<!-- Fixed navbar -->
	<div class="navbar navbar-inverse">
		<div class="container">
			<div class="navbar-header">
				<!-- Button for smallest screens -->
				<button type="button" class="navbar-toggle" data-toggle="collapse"
					data-target=".navbar-collapse">
					<span class="icon-bar"></span><span class="icon-bar"></span><span
						class="icon-bar"></span>
				</button>
				<a class="navbar-brand" href="index.html"> <img
					src="assets/images/logo.png" alt="Techro HTML5 template"></a>
			</div>
			<div class="navbar-collapse collapse">
				<ul class="nav navbar-nav pull-right mainNav">
					<li><a href="index.html">Home</a></li>
					<li class="active"><a href="main_table_list.jsp">TIP
							SUMMER 2018</a></li>

				</ul>
			</div>
			<!--/.nav-collapse -->
		</div>
	</div>
	<!-- /.navbar -->

	<header id="head" class="secondary">
	<div class="container">
		<div class="row">
			<div class="col-sm-8">
				<h1>TIP Summer 2018</h1>
			</div>
		</div>
	</div>
	</header>

	<!-- container -->
	<section class="container">
	<div class="row">
		<!-- main content -->
		<section class="col-sm-8 maincontent">



		<table width="100%" cellpadding="0" cellspacing="0" border="0">


			<tr height="5">
				<td width="5"></td>
			</tr>
			<tr
				style="background: url('img/table_mid.gif') repeat-x; text-align: center;">
				<td width="5"><img src="img/table_left.gif" width="5"
					height="30" /></td>
				<td width="73">No</td>
				<td width="379">Subject</td>
				<td width="73">Name</td>
				<td width="164">Date</td>
				<td width="58">Views</td>
				<td width="7"><img src="img/table_right.gif" width="5"
					height="30" /></td>
			</tr>

			<%
				if (total == 0) {
			%>
			<tr align="center" bgcolor="#FFFFFF" height="30">
				<td colspan="6">There is nothing to view</td>
			</tr>
			<%
				} else {

						while (rs.next()) {
							int idx = rs.getInt(1);
							String name = rs.getString(2);
							String title = rs.getString(3);
							String time = rs.getString(4);
							int hit = rs.getInt(5);
			%>

			<tr height="25" align="center">
				<td>&nbsp;</td>
				<td><%=idx%></td>
				<td align="left"><a href="main_table_view.jsp?idx=<%=idx%>"><%=title%></a></td>
				<td align="center"><%=name%></td>
				<td align="center"><%=time%></td>
				<td align="center"><%=hit%></td>
				<td>&nbsp;</td>
			</tr>
			<tr height="1" bgcolor="#D2D2D2">
				<td colspan="6"></td>
			</tr>

			<%
				}
					}
					rs.close();
					stmt.close();
					conn.close();
				} catch (SQLException e) {
					out.println(e.toString());
				}
			%>



			<tr height="1" bgcolor="#82B5DF">
				<td colspan="6" width="752"></td>
			</tr>


		</table>

		<table width="100%" cellpadding="0" cellspacing="0" border="0">
			<tr>
				<td colspan="4" height="5"></td>
			</tr>
			<tr align="center">
				<td><input type=button value="Write"
					OnClick="window.location='main_table_write.jsp'"></td>
			</tr>
		</table>



		</section>
		<!-- /main -->

		<!-- Sidebar -->
		<aside class="col-sm-4 sidebar sidebar-right">

		<div class="panel">
			<h4>Publication(mqtt,coap)</h4>
			<ul class="list-unstyled list-spaces">
				<li><a href="downloads/Publication/coap1.pdf">스마트_에너지_IoT를_위한_CoAP_기반_Lightweight_OpenADR2.0b_프로토콜의_구현_및_분석</a><br></li>
				<li><a href="downloads/Publication/coapPush.pdf">에너지_IoT_환경에서_실시간_수요반응_서비스를_위한_CoAP_Observe_기반_Push_Mechanism_설계_및_분석</a><br></li>

				<li><a href="downloads/Publication/mqtt1.pdf">MQTT_기반의_스마트홈에서_실시간_수요반응_게이트웨이_설계_및_구현</a><br></li>

				<li><a href="downloads/Publication/needPush.pdf">스마트_에너지_IoT_환경에서_OpenADR_2.0b_수요반응의_Push_메카니즘_필요성_연구</a><br></li>

				<li><a href="downloads/Publication/ictTech.pdf">주거_공간_에너지_관리_ICT_기술</a><br></li>

			</ul>



			<h4>Chapter 3</h4>
			<ul class="list-unstyled list-spaces">
				<li><a
					href="downloads/TIPS_3_Chapter/TIPS_3_cmd_commands v2.txt">TIPS_3_cmd_commands
						v2.txt(Modified)</a><br></li>
				<li><a href="downloads/TIPS_3_Chapter/putty(32bit).exe">putty(32bit).exe</a><br></li>
				<li><a href="downloads/TIPS_3_Chapter/putty(64bit).exe">putty(64bit).exe</a><br></li>

			</ul>

			<h4>Chapter 4</h4>
			<ul class="list-unstyled list-spaces">
				<li><a
					href="downloads/TIPS_4_Chapter/TIPS_4_cmd_commands v3.txt">TIPS_4_cmd_commands
						v3.txt(Modified)</a><br></li>
				<li><a href="downloads/TIPS_4_Chapter/client.cfg">client.cfg</a><br></li>
				<li><a href="downloads/TIPS_4_Chapter/mqttClient_final.tar.gz">MQTT
						Programmin_통합압축</a><br></li>
				<li><a href="downloads/TIPS_4_Chapter/mqttClient1.cpp">mqttClient1.cpp</a><br></li>
				<li><a href="downloads/TIPS_4_Chapter/mqttClient2.cpp">mqttClient2.cpp</a><br></li>
				<li><a href="downloads/TIPS_4_Chapter/v1.4.10.tar.gz">v1.4.10.tar.gz</a><br></li>

			</ul>

			<h4>Chapter 6</h4>
			<ul class="list-unstyled list-spaces">
				<li><a
					href="downloads/TIPS_6_Chapter/TIPS_6_cmd_commands v1.txt">TIPS_6_cmd_commands
						v1.txt</a><br></li>
			</ul>

			<h4>Chapter 7</h4>
			<ul class="list-unstyled list-spaces">
				<li><a
					href="downloads/TIPS_7_Chapter/TIPS_7_cmd_commands v2.txt">TIPS_7_cmd_commands
						v2.txt(Modified)</a><br></li>
			</ul>

			<h4>Chapter 8(Rasp_LED_Control)</h4>
			<ul class="list-unstyled list-spaces">
				<li><a
					href="downloads/TIPS_8_Chapter(Rasp_LED_Control)/TIPS_8_cmd_commands v2.txt">TIPS_8_cmd_commands
						v2.txt(Modified)</a><br></li>
				<li><a
					href="downloads/TIPS_8_Chapter(Rasp_LED_Control)/led_control_final.tar.gz">LED_Control_통합압축</a><br></li>
				<li><a
					href="downloads/TIPS_8_Chapter(Rasp_LED_Control)/led_control1.cpp">led_control1.cpp</a><br></li>
				<li><a
					href="downloads/TIPS_8_Chapter(Rasp_LED_Control)/led_control2.cpp">led_control2.cpp</a><br></li>
				<li><a
					href="downloads/TIPS_8_Chapter(Rasp_LED_Control)/led_control3.cpp">led_control3.cpp</a><br></li>
				<li><a
					href="downloads/TIPS_8_Chapter(Rasp_LED_Control)/led_control4.cpp">led_control4.cpp</a><br></li>
				<li><a
					href="downloads/TIPS_8_Chapter(Rasp_LED_Control)/rasp.cfg">rasp.cfg</a><br></li>
				<li><a
					href="downloads/TIPS_8_Chapter(Rasp_LED_Control)/v1.4.10.tar.gz">v1.4.10.tar.gz</a><br></li>

			</ul>

			<h4>Chapter 8(Rasp_Ven)</h4>
			<ul class="list-unstyled list-spaces">
				<li><a
					href="downloads/TIPS_8_Chapter(Rasp_Ven)/TIPS_8_cmd_commands v2.txt">TIPS_8_cmd_commands
						v2.txt(Modified)</a><br></li>
				<li><a
					href="downloads/TIPS_8_Chapter(Rasp_Ven)/sample_r2.tar.gz">Rasp_Ven_통합압축(new)</a><br></li>

				<li><a href="downloads/TIPS_8_Chapter(Rasp_Ven)/ven.cfg">ven.cfg</a><br></li>
				<li><a href="downloads/TIPS_8_Chapter(Rasp_Ven)/libs.tar.gz">libraries.tar.gz</a><br></li>

			</ul>
		</div>

		</aside>
		<!-- /Sidebar -->


	</div>
	</section>

	<footer id="footer">
	<div class="container">
		<div class="social text-center">
			<a href="#"><i class="fa fa-twitter"></i></a> <a href="#"><i
				class="fa fa-facebook"></i></a> <a href="#"><i
				class="fa fa-dribbble"></i></a> <a href="#"><i class="fa fa-flickr"></i></a>
			<a href="#"><i class="fa fa-github"></i></a>
		</div>

		<div class="clear"></div>
		<!--CLEAR FLOATS-->
	</div>
	<div class="footer2">
		<div class="container">
			<div class="row">

				<div class="col-md-6 panel">
					<div class="panel-body">
						<p class="simplenav">
							<a href="index.html">Home</a> | <a href="main_table_list.jsp">TIP
								SUMMER 2018</a> |

						</p>
					</div>
				</div>

				<div class="col-md-6 panel">
					<div class="panel-body">
						<p class="text-right">Copyright &copy; 2016. MIR Lab All
							rights reserved.</p>
					</div>
				</div>

			</div>
			<!-- /row of panels -->
		</div>
	</div>
	</footer>

	<!-- JavaScript libs are placed at the end of the document so the pages load faster -->
	<script src="assets/js/modernizr-latest.js"></script>
	<script type='text/javascript' src='assets/js/jquery.min.js'></script>
	<script type='text/javascript'
		src='assets/js/fancybox/jquery.fancybox.pack.js'></script>

	<script type='text/javascript'
		src='assets/js/jquery.mobile.customized.min.js'></script>
	<script type='text/javascript' src='assets/js/jquery.easing.1.3.js'></script>
	<script type='text/javascript' src='assets/js/camera.min.js'></script>
	<script src="assets/js/bootstrap.min.js"></script>
	<script src="assets/js/custom.js"></script>
	<script>
		jQuery(function() {

			jQuery('#camera_wrap_4').camera({
				height : '600',
				loader : 'bar',
				pagination : false,
				thumbnails : false,
				hover : false,
				opacityOnGrid : false,
				imagePath : 'assets/images/'
			});

		});
	</script>

</body>
</html>
