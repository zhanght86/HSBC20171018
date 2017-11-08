<%@page contentType="text/html;charset=GBK"%>
<%@include file="../common/jsp/Log4jUI.jsp"%>
<%
	//�������ƣ�	IBRMSShowProgress.jsp
	//�����ܣ�	���ɽ���ҳ��
	//�������ڣ�	2012-01-11
	//������  ��	HuangLiang
	//���¼�¼��  ������    ��������     ����ԭ��/����
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
	/*���ղ���*/
	String Params = request.getParameter("parameters");
	String aContent = StrTool.unicodeToGBK(request
	.getParameter("content"));
	String aSteps = request.getParameter("steps");
	String aDisplay = request.getParameter("display");

	/*������յ��Ĳ���*/
	String tRuleName = Params.split("::")[0].split(":")[1];//��������
	String aAction = Params.split("::")[1].split(":")[1];//

	/*ȫ�ֱ���*/
	GlobalInput tG = (GlobalInput) session.getValue("GI");
	String busiName = "TaskMonitorUI";
	String tAction = "action";//��̨����

	/*ȫ���������ݵȣ����ձ������*/
	String[][] strContent = new String[4][3];
	strContent[0][0] = "����ID";	strContent[0][1] = "��������";	strContent[0][2] = "�������";
	strContent[1][0] = "ExcelR:" + tRuleName;	strContent[1][1] = "���ж�ȡ�ϴ����ݣ���ȥ�ظ�";		strContent[1][2] = "";
	strContent[2][0] = "ExcelADD:" + tRuleName;	strContent[2][1] = "�������룺��ȡ����ԭ������";		strContent[2][2] = "";
	strContent[3][0] = "ExcelW:" + tRuleName;	strContent[3][1] = "д�����ݿ�";	strContent[3][2] = "";

	/*������ʾ����*/
	int[] showCols = { 1, 2 };//
	int progressCol = 2;//��ʾ���ȵ���

	/*���ݲ���ɸѡ��������*/
	VData commitContent = new VData();
	if ("all".equalsIgnoreCase(aAction)) {//ȫ������
		commitContent.add(strContent[0]);
		commitContent.add(strContent[1]);
		commitContent.add(strContent[3]);
	} else if ("add".equalsIgnoreCase(aAction)) {//��������
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
	//׼���������� VData
	tData.add(tG);
	tData.add(tVData);
	if ("init".equalsIgnoreCase(aSteps)) {
		//��ʼ��
		String initAction = "initial";
		submitData((VData)tData.clone(), initAction, busiName);
		aSteps="inited";
	}
	Map aMapResult = submitData(tData, tAction, busiName);//�ύ��̨
	//��������������VData
	tVData = (VData) aMapResult.get("Result");
	tData = (VData) tVData.get(0);
	aSize = tData.size();

	Map tMapContent = dealTableData(commitContent, tData, showCols,
	progressCol);//��������Ҫ���͵�����

	for(int i=aSize-1;i>=0;i--){
		String aSignal = (String) tData.get(i);
		if (aSignal == "completed" && aSteps != "init" && i==(aSize-1)) {
			aSteps = "completed";//�����ź�
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
	/*��������*/
	Map map = new HashMap();
	map.put("steps", aSteps);//���в��裬��ʼΪinit������Ϊcompleted
	map.put("parameters", Params);//�Զ������
	map.put("display", false);//ʹ��Ĭ����ʾ
	map.put("content", tMapContent);//��ʾ��������
	JSONObject ja = JSONObject.fromObject(map);
	out.println(ja);
%>
<%!/**���ú�̨java���򣬷��ز��������*/
	public Map submitData(VData aVData, String aAction, String aBusiName) {
		String FlagStr = "";
		String Content = "";
		VData tData = aVData;
		String busiName = aBusiName;
		String tAction = aAction;
		BusinessDelegate tBusinessDelegate = BusinessDelegate
				.getBusinessDelegate();
		if (!tBusinessDelegate.submitData(tData, tAction, busiName)) {
			Content = " ����ʧ�ܣ�ԭ����: "
					+ tBusinessDelegate.getCErrors().getFirstError();
			FlagStr = "Fail";
		} else {
			Content = " �����ɹ�! ";
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

	/**����������ݣ����ɱ�����ݣ���Ҫ����ִ�����񣬷��ؽ������ʾ�У�������*/
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
		for (int i = 0; i < aSize; i++) {//�޸Ľ����ʾ
			String row = (String) aResult.get(i);
			if ("NOFOUND".equalsIgnoreCase(row)) {
				aResult.set(i, "");//δ��ʼ����
			} else {
				String[] row2 = row.split("/");
				if (row2.length == 2) {
					if (row2[0].equalsIgnoreCase(row2[1])) {
						aResult.set(i, "completed");
					} else {
						for (i = i + 1; i < aSize; i++) {
							aResult.set(i, "");//��⵽���ڽ��е����񣬽�ʣ��������Ϊ��
						}
						break;
					}
				}
			}
		}
		aSize = aResult.size();
		for (int i = 0; i < aSize; i++) {//���������map��
			Map tMapRow = new HashMap();
			String[] row = (String[]) aCommit.get(i + 1);
			int rowL = aShowCols.length;//
			for (int j = 0; j < rowL; j++) {
				tempInt = j + 1;
				aColNum = aShowCols[j];//Ҫ��ʾ����
				if (aColNum != aProgressCol)
					tMapRow.put("col" + tempInt, row[aColNum]);
				else
					tMapRow.put("col" + tempInt, aResult.get(i));//��ʾ����
			}
			rowL = i + 1;
			tMapContent.put("row" + rowL, tMapRow);
		}
		tMapContent.put("rowSize", aSize);
		return tMapContent;
	}%>
