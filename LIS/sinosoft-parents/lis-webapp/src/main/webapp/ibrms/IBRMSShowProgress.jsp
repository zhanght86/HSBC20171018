<%@page contentType="text/html;charset=GBK"%>
<%@include file="../common/jsp/Log4jUI.jsp"%>
<%
	//程序名称：	IBRMSShowProgress.jsp
	//程序功能：	生成进度页面
	//创建日期：	2012-01-11
	//创建人  ：	HuangLiang
	//更新记录：  更新人    更新日期     更新原因/内容
%>
<%@page import="com.sinosoft.lis.pubfun.GlobalInput"%>
<%@page import="com.sinosoft.utility.StrTool"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.HashMap"%>
<%@page import="net.sf.json.JSONObject"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.service.BusinessDelegate"%>
<%
	/*接收参数*/
	String Params = request.getParameter("parameters");
	String aContent = StrTool.unicodeToGBK(request
	.getParameter("content"));
	String aSteps = request.getParameter("steps");
	String aDisplay = request.getParameter("display");

	/*处理接收到的参数*/
	String tRuleName = Params.split("::")[0].split(":")[1];//规则名称
	String aAction = Params.split("::")[1].split(":")[1];//

	/*全局变量*/
	GlobalInput tG = (GlobalInput) session.getValue("GI");
	String busiName = "TaskMonitorUI";
	String tAction = "action";//后台参数

	/*全部任务内容等，按照表格行列*/
	String[][] strContent = new String[4][3];
	strContent[0][0] = "任务ID";	strContent[0][1] = "任务名称";	strContent[0][2] = "任务进度";
	strContent[1][0] = "ExcelR:" + tRuleName;	strContent[1][1] = "按行读取上传数据，并去重复";		strContent[1][2] = "";
	strContent[2][0] = "ExcelADD:" + tRuleName;	strContent[2][1] = "增量导入：读取库中原有数据";		strContent[2][2] = "";
	strContent[3][0] = "ExcelW:" + tRuleName;	strContent[3][1] = "写入数据库";	strContent[3][2] = "";

	/*定义显示的列*/
	int[] showCols = { 1, 2 };//
	int progressCol = 2;//显示进度的列

	/*根据参数筛选任务内容*/
	VData commitContent = new VData();
	if ("all".equalsIgnoreCase(aAction)) {//全量导入
		commitContent.add(strContent[0]);
		commitContent.add(strContent[1]);
		commitContent.add(strContent[3]);
	} else if ("add".equalsIgnoreCase(aAction)) {//增量导入
		commitContent.add(strContent[0]);
		commitContent.add(strContent[1]);
		commitContent.add(strContent[2]);
		commitContent.add(strContent[3]);
	}
	int aSize = commitContent.size();
	VData tData = new VData();
	VData tVData = new VData();
	for (int i = 1; i < aSize; i++) {
		tVData.add(((String[]) commitContent.get(i))[0]);
	}
	//准备传输数据 VData
	tData.add(tG);
	tData.add(tVData);
	if ("init".equalsIgnoreCase(aSteps)) {
		//初始化
		String initAction = "initial";
		submitData((VData)tData.clone(), initAction, busiName);
		aSteps="inited";
	}
	Map aMapResult = submitData(tData, tAction, busiName);//提交后台
	//获得任务进度数据VData
	tVData = (VData) aMapResult.get("Result");
	tData = (VData) tVData.get(0);
	aSize = tData.size();

	Map tMapContent = dealTableData(commitContent, tData, showCols,
	progressCol);//用于生成要传送的内容

	for(int i=aSize-1;i>=0;i--){
		String aSignal = (String) tData.get(i);
		if (aSignal == "completed" && aSteps != "init" && i==(aSize-1)) {
			aSteps = "completed";//结束信号
			break;
		}else if(aSignal == "completed" && aSteps != "init"){
			int k = i+1;
			aSteps = "step" + k;
			break;
		}else if(aSignal != "" && aSignal !="completed"){
			int k = i+1;
			aSteps = "step" + k;
			break;
		}
	}
	/*传送数据*/
	Map map = new HashMap();
	map.put("steps", aSteps);//进行步骤，开始为init，结束为completed
	map.put("parameters", Params);//自定义参数
	map.put("display", false);//使用默认显示
	map.put("content", tMapContent);//显示进度内容
	JSONObject ja = JSONObject.fromObject(map);
	out.println(ja);
%>
<%!/**调用后台java程序，返回操作结果集*/
	public Map submitData(VData aVData, String aAction, String aBusiName) {
		String FlagStr = "";
		String Content = "";
		VData tData = aVData;
		String busiName = aBusiName;
		String tAction = aAction;
		BusinessDelegate tBusinessDelegate = BusinessDelegate
				.getBusinessDelegate();
		if (!tBusinessDelegate.submitData(tData, tAction, busiName)) {
			Content = " 操作失败，原因是: "
					+ tBusinessDelegate.getCErrors().getFirstError();
			FlagStr = "Fail";
		} else {
			Content = " 操作成功! ";
			FlagStr = "Succ";
			tData.clear();
		}
		Map tMap = new HashMap();
		tMap.put("Content", Content);
		tMap.put("FlagStr", FlagStr);
		tMap.put("Result", tBusinessDelegate.getResult());
		tMap.put("CErros", tBusinessDelegate.getCErrors());
		return tMap;
	}

	/**处理传入的数据，生成表格内容，需要传入执行任务，返回结果，显示列，进度列*/
	public Map dealTableData(VData aCommit, VData aResult, int[] aShowCols,
			int aProgressCol) {
		Map tMapContent = new HashMap();
		Map tMapTitle = new HashMap();
		String[] aTitle = (String[]) aCommit.get(0);
		int aSize = aShowCols.length;
		int aColNum = 0;
		int tempInt = 0;
		for (int i = 0; i < aSize; i++) {
			tempInt = i + 1;
			aColNum = aShowCols[i];
			tMapTitle.put("col" + tempInt, aTitle[aColNum]);
		}
		tMapContent.put("title", tMapTitle);
		tMapContent.put("colSize", aSize);
		int tProgressColNum = 1;
		for (int i = 0; i < aSize; i++) {
			if (aProgressCol > aShowCols[i])
				tProgressColNum++;
		}
		tMapContent.put("progressCol", tProgressColNum);

		aSize = aResult.size();
		for (int i = 0; i < aSize; i++) {//修改结果显示
			String row = (String) aResult.get(i);
			if ("NOFOUND".equalsIgnoreCase(row)) {
				aResult.set(i, "");//未开始任务
			} else {
				String[] row2 = row.split("/");
				if (row2.length == 2) {
					if (row2[0].equalsIgnoreCase(row2[1])) {
						aResult.set(i, "completed");
					} else {
						for (i = i + 1; i < aSize; i++) {
							aResult.set(i, "");//检测到正在进行的任务，将剩余任务标记为空
						}
						break;
					}
				}
			}
		}
		aSize = aResult.size();
		for (int i = 0; i < aSize; i++) {//将结果放入map中
			Map tMapRow = new HashMap();
			String[] row = (String[]) aCommit.get(i + 1);
			int rowL = aShowCols.length;//
			for (int j = 0; j < rowL; j++) {
				tempInt = j + 1;
				aColNum = aShowCols[j];//要显示的列
				if (aColNum != aProgressCol)
					tMapRow.put("col" + tempInt, row[aColNum]);
				else
					tMapRow.put("col" + tempInt, aResult.get(i));//显示进度
			}
			rowL = i + 1;
			tMapContent.put("row" + rowL, tMapRow);
		}
		tMapContent.put("rowSize", aSize);
		return tMapContent;
	}%>
