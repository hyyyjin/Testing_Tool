package com.mir.webserverCheck;

import java.util.HashMap;


import java.util.concurrent.ConcurrentHashMap;

import com.automark.OFResult;
import com.mir.gbn.server.GBNResult;
import com.mir.sr.server.SR_Result;
import com.mir.webserverCheck.DB.StuClientRESTAPIInfo;
import com.mir.webserverCheck.DB.StuUDPLog;
import com.mir.webserverCheck.DB.StuUDPResult;

public class global {
	
	
	public static ConcurrentHashMap<String, Boolean> passwordCheck = new ConcurrentHashMap<String, Boolean>();
	public static String password = "COMNET2018";
	
	public static HashMap<String, StuInfo> stuinfo = new HashMap<String, StuInfo>();
	public static HashMap<String, Integer> statusMap = new HashMap<String, Integer>();

	public static HashMap<String, StuClientResult> webCliStuInfo = new HashMap<String, StuClientResult>();

	public static HashMap<String, StuUDPResult> udpStuInfo = new HashMap<String, StuUDPResult>();
	public static HashMap<String, StuUDPLog> udpStuLog = new HashMap<String, StuUDPLog>();

	//go-back-n
	public static ConcurrentHashMap<String,GBNResult> gbnStuInfo = new ConcurrentHashMap<String, GBNResult>();
//	public static HashMap<String, StuUDPLog> gbnStuLog = new HashMap<String, StuUDPLog>();
//	
	//selective Repeat 
	public static HashMap<String, SR_Result> srStuInfo = new HashMap<String, SR_Result>();
//	public static HashMap<String, StuUDPLog> srStuLog = new HashMap<String, StuUDPLog>();
	
	//OpenFLow
	// need to modify data structure 
	public static HashMap<String, OFResult> ofStuInfo = new HashMap<String, OFResult>();
//	public static HashMap<String, StuUDPLog> srStuLog = new HashMap<String, StuUDPLog>();
//	openflow server hashmap 
	public static HashMap<String, OFResult> keyValue = new HashMap<String, OFResult>();


	 
	
	public static String[] imageArr = { "mirimage1.jpg", "ODLimage1.jpg", "onosimage1.jpg", "SmartGrid.jpg",
			"hanyangimage1.jpg" };
	

	public static HashMap<String, StuClientRESTAPIInfo> clientRestAPIInfo = new HashMap<String, StuClientRESTAPIInfo>();
	
	

	public static String[] topologyMN_Arr = { "3.mn", "4.mn", "5.mn", "6.mn", "7.mn", "8.mn" };

	public static String[] topologyIMAGE_Arr = { "TOPOLOGY3.jpg", "TOPOLOGY4.jpg", "TOPOLOGY5.jpg", "TOPOLOGY6.jpg",
			"TOPOLOGY7.jpg", "TOPOLOGY8.jpg" };

	public static String indexPath = "C:\\Users\\HYUNJIN PARK\\workspace\\Com_net_Auto_WebClient\\templated-workspace\\";
}
