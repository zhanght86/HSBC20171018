<%@include file="/i18n/language.jsp"%>

<%
	//Creator :�ű�
	//Date :2006-08-21
%>
<!--�û�У����-->

<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.utility.*"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<%
	GlobalInput tG = new GlobalInput();
	tG = (GlobalInput) session.getAttribute("GI");
	String Operator = tG.Operator;
	String Comcode = tG.ManageCom;
	String CurrentDate = PubFun.getCurrentDate();
	String tCurrentYear = StrTool.getVisaYear(CurrentDate);
	String tCurrentMonth = StrTool.getVisaMonth(CurrentDate);
	String tCurrentDate = StrTool.getVisaDay(CurrentDate);
	String tCodeType = request.getParameter("CodeType");
	String tSerialNo = request.getParameter("SerialNo");
%>
<script type="text/javascript">
	var x = 0;
	var y = 0;
	var com = new Array(y);
	var line = new Array(x);

	function initInpBox() {
		try {
			fm.all("OperateNo").value =<%=request.getParameter("OperateNo")%>;
			fm.all("CodeType").value =<%=tCodeType%>;

			fm.all("AccumulateDefNO").value = "";
			fm.all("RIPreceptNo").value = "";
			fm.all("SerialNo").value =<%=tSerialNo%>;

			if (<%=request.getParameter("reCountType")%> == "1") {
				fm.all("PType").value = "01";
				boCode.style.display = "";
				boName.style.display = "";
			} else if (<%=request.getParameter("reCountType")%>== "2") {
				fm.all("PType").value = "03";
			}

			if (fm.all("CodeType").value == "05") {
				divTempCessConclusion1.style.display = "";
			} else if (fm.all("CodeType").value == "01") {
				divTempCessConclusion1.style.display = "";
			} else {
				fm.all("RIContNo").value ="<%=request.getParameter("ReContCode")%>"; 
				//�ٱ���ͬ���ReContCode�Ǻ�ͬ��ŵ�id
				divTempCessConclusion1.style.display = "none";
			}
			divHidden.style.display = "none";
		} catch (ex) {
			myAlert("���г�ʼ���ǳ��ִ��󣡣�����");
		}
	}

	// ������ĳ�ʼ��
	function initSelBox() {
		try {
		} catch (ex) {
			myAlert("��RISchemaInit.jsp-->"+"InitSelBox�����з����쳣:��ʼ���������!");
		}
	}

	function initForm() {
		try {
			initInpBox();
			initSelBox();
			intPreceptSearchGrid();
			initContCessGrid();
			initScaleLineGrid();
			initCessScaleGrid();
			initFeeRateGrid();
			initFactorGrid();
			queryClick();
			browseForm();

		} catch (re) {
			myAlert("RISchemaInit.jsp-->"+"InitForm�����з����쳣:��ʼ���������!");
		}
	}

	//�ٱ�������ѯ mulline��ʼֵ�趨
	function intPreceptSearchGrid() {
		var iArray = new Array();
		try {
			iArray[0] = new Array();
			iArray[0][0] = "���"; //����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
			iArray[0][1] = "30px"; //�п�
			iArray[0][2] = 40; //�����ֵ
			iArray[0][3] = 0; //�Ƿ���������,1��ʾ����0��ʾ������

			iArray[1] = new Array();
			iArray[1][0] = "�ٱ���ͬ����";
			iArray[1][1] = "90px";
			iArray[1][2] = 100;
			iArray[1][3] = 0;

			iArray[2] = new Array();
			iArray[2][0] = "�ٱ���������";
			iArray[2][1] = "90px";
			iArray[2][2] = 100;
			iArray[2][3] = 0;

			iArray[3] = new Array();
			iArray[3][0] = "�ٱ���������";
			iArray[3][1] = "260px";
			iArray[3][2] = 200;
			iArray[3][3] = 0;

			iArray[4] = new Array();
			iArray[4][0] = "�ֳ����α���";
			iArray[4][1] = "100px";
			iArray[4][2] = 100;
			iArray[4][3] = 0;

			iArray[5] = new Array();
			iArray[5][0] = "�ֳ���������";
			iArray[5][1] = "0px";
			iArray[5][2] = 20;
			iArray[5][3] = 3;

			iArray[6] = new Array();
			iArray[6][0] = "�ֱ���˾��";
			iArray[6][1] = "70px";
			iArray[6][2] = 100;
			iArray[6][3] = 0;

			iArray[7] = new Array();
			iArray[7][0] = "������";
			iArray[7][1] = "70px";
			iArray[7][2] = 100;
			iArray[7][3] = 0;

			iArray[8] = new Array();
			iArray[8][0] = "�ֱ��㷨ID";
			iArray[8][1] = "0px";
			iArray[8][2] = 0;
			iArray[8][3] = 3;

			iArray[9] = new Array();
			iArray[9][0] = "�ֱ��㷨����";
			iArray[9][1] = "0px";
			iArray[9][2] = 20;
			iArray[9][3] = 3;

			iArray[10] = new Array();
			iArray[10][0] = "�ֱ��������ʹ���"; //01����ͬ�ֱ���02����ʱ�ֱ�
			iArray[10][1] = "0px";
			iArray[10][2] = 100;
			iArray[10][3] = 3;

			iArray[11] = new Array();
			iArray[11][0] = "�ֱ���������";
			iArray[11][1] = "100px";
			iArray[11][2] = 100;
			iArray[11][3] = 0;

			iArray[12] = new Array();
			iArray[12][0] = "��������";
			iArray[12][1] = "0px";
			iArray[12][2] = 20;
			iArray[12][3] = 3;

			iArray[13] = new Array();
			iArray[13][0] = "������������";
			iArray[13][1] = "0px";
			iArray[13][2] = 20;
			iArray[13][3] = 3;

			iArray[14] = new Array();
			iArray[14][0] = "���㷽ʽ";
			iArray[14][1] = "0px";
			iArray[14][2] = 20;
			iArray[14][3] = 3;

			iArray[15] = new Array();
			iArray[15][0] = "���㷽ʽ����";
			iArray[15][1] = "0px";
			iArray[15][2] = 20;
			iArray[15][3] = 3;

			iArray[16] = new Array();
			iArray[16][0] = "����״̬";
			iArray[16][1] = "80px";
			iArray[16][2] = 100;
			iArray[16][3] = 0;

			iArray[17] = new Array();
			iArray[17][0] = "����״̬��"; //01 ��Ч  02 δ��Ч  
			iArray[17][1] = "0px";
			iArray[17][2] = 20;
			iArray[17][3] = 3;

			iArray[18] = new Array();
			iArray[18][0] = "�ֱ�����"; //01 ��Ч  02 δ��Ч  
			iArray[18][1] = "0px";
			iArray[18][2] = 20;
			iArray[18][3] = 3;

			iArray[19] = new Array();
			iArray[19][0] = "�ֱ���ʽ����"; //01 ��Ч  02 δ��Ч  
			iArray[19][1] = "0px";
			iArray[19][2] = 20;
			iArray[19][3] = 3;

			iArray[20] = new Array();
			iArray[20][0] = "�ֱ���ʽ����"; //01 ��Ч  02 δ��Ч  
			iArray[20][1] = "0px";
			iArray[20][2] = 20;
			iArray[20][3] = 3;

			iArray[21] = new Array();
			iArray[21][0] = "��ֵ���ͱ���"; //01 ��Ч  02 δ��Ч  
			iArray[21][1] = "0px";
			iArray[21][2] = 20;
			iArray[21][3] = 3;

			iArray[22] = new Array();
			iArray[22][0] = "��ֵ��������"; //01 ��Ч  02 δ��Ч  
			iArray[22][1] = "0px";
			iArray[22][2] = 20;
			iArray[22][3] = 3;

			iArray[23] = new Array();
			iArray[23][0] = "test "; //01 ��Ч  02 δ��Ч  
			iArray[23][1] = "0px";
			iArray[23][2] = 20;
			iArray[23][3] = 3;

			iArray[24] = new Array();
			iArray[24][0] = "�ֱ�����"; //01 �������շֱ�  
			iArray[24][1] = "0px";
			iArray[24][2] = 20;
			iArray[24][3] = 3;

			iArray[25] = new Array();
			iArray[25][0] = "�ֱ���������";
			iArray[25][1] = "0px";
			iArray[25][2] = 20;
			iArray[25][3] = 3;

			iArray[26] = new Array();
			iArray[26][0] = "���������ٱ�����";
			iArray[26][1] = "0px";
			iArray[26][2] = 50;
			iArray[26][3] = 3;

			iArray[27] = new Array();
			iArray[27][0] = "aaa���������ٱ���������";
			iArray[27][1] = "0px";
			iArray[27][2] = 50;
			iArray[27][3] = 3;

			iArray[28] = new Array();
			iArray[28][0] = "�ֱ��㷨";
			iArray[28][1] = "70px";
			iArray[28][2] = 50;
			iArray[28][3] = 0;
			
			iArray[29] = new Array();
			iArray[29][0] = "�������ͱ���";
			iArray[29][1] = "0px";
			iArray[29][2] = 50;
			iArray[29][3] = 3;
			
			iArray[30] = new Array();
			iArray[30][0] = "��������";
			iArray[30][1] = "70px";
			iArray[30][2] = 50;
			iArray[30][3] = 0;

			PreceptSearchGrid = new MulLineEnter("fm", "PreceptSearchGrid");
			//��Щ���Ա�����loadMulLineǰ
			PreceptSearchGrid.mulLineCount = 10;
			PreceptSearchGrid.displayTitle = 1;
			PreceptSearchGrid.locked = 0;
			PreceptSearchGrid.canSel = 1; // 1 ��ʾ ��0 ���أ�ȱʡֵ��
			PreceptSearchGrid.selBoxEventFuncName = "showPrecept"; //��Ӧ�ĺ���������������   
			PreceptSearchGrid.hiddenPlus = 1;
			PreceptSearchGrid.hiddenSubtraction = 1;
			PreceptSearchGrid.loadMulLine(iArray);
		} catch (ex) {
			myAlert(ex);
		}
	}

	// ����������б�ĳ�ʼ��
	function initContCessGrid() {
		var iArray = new Array();
		try {
			iArray[0] = new Array();
			iArray[0][0] = "���"; //����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
			iArray[0][1] = "30px"; //�п�
			iArray[0][2] = 40; //�����ֵ
			iArray[0][3] = 0; //�Ƿ���������,1��ʾ����0��ʾ������   			     

			iArray[1] = new Array();
			iArray[1][0] = "�㼶";
			iArray[1][1] = "50px";
			iArray[1][2] = 100;
			iArray[1][3] = 0;

			iArray[2] = new Array();
			iArray[2][0] = "��ֵ����";
			iArray[2][1] = "0px";
			iArray[2][2] = 100;
			iArray[2][3] = 3;

			iArray[3] = new Array();
			iArray[3][0] = "��ֵ";
			iArray[3][1] = "80px";
			iArray[3][2] = 10;
			iArray[3][3] = 1;
			iArray[3][9] = "��ֵ|notnull";

			iArray[4] = new Array();
			iArray[4][0] = "���������";
			iArray[4][1] = "100px";
			iArray[4][2] = 100;
			iArray[4][3] = 2;
			//iArray[4][4]="test"; 
			iArray[4][4] = "ridivtype"; //���ô��� 
			iArray[4][5] = "4|5"; //���ô����Ӧ�ڼ��У�'|'Ϊ�ָ��
			iArray[4][6] = "1|0"; //��������з������ô����еڼ�λֵ,��:�����ֵ��λ��
			iArray[4][18] = "150";
			iArray[4][19] = 1;

			iArray[5] = new Array();
			iArray[5][0] = "��������ʹ���";
			iArray[5][1] = "0px";
			iArray[5][2] = 100;
			iArray[5][3] = 3;
			iArray[5][9] = "���������|notnull";

			iArray[6] = new Array();
			iArray[6][0] = "��̬�޶�ϵ��";
			iArray[6][1] = "80px";
			iArray[6][2] = 100;
			iArray[6][3] = 2;
			iArray[6][10] = "ChangeRate"; //���ô��룺"AccType"Ϊ�������ݵ�����,�ÿؼ�������  
			iArray[6][11] = "0|^|^ChangeRate|ת������";
			iArray[6][12] = "6|7"; //���ô����Ӧ�ڼ��У�'|'Ϊ�ָ��
			iArray[6][13] = "0|1"; //��������з������ô����еڼ�λֵ,��:�����ֵ��λ��
			iArray[6][18] = "150";
			iArray[6][19] = 1;

			iArray[7] = new Array();
			iArray[7][0] = "��̬ϵ������";
			iArray[7][1] = "80px";
			iArray[7][2] = 100;
			iArray[7][3] = 3;

			iArray[8] = new Array();
			iArray[8][0] = "���ֱ���";
			iArray[8][1] = "70px";
			iArray[8][2] = 100;
			iArray[8][3] = 2;
			iArray[8][4] = "currency";
			iArray[8][5] = "8|9"; //�����ô���ֱ���ڵ�1��2
			iArray[8][6] = "0|1";
			iArray[8][19] = 1;

			iArray[9] = new Array();
			iArray[9][0] = "��������";
			iArray[9][1] = "70px";
			iArray[9][2] = 100;
			iArray[9][3] = 0;

/*			iArray[10] = new Array();
			iArray[10][0] = "��̬�޶�ϵ��";
			iArray[10][1] = "100px";
			iArray[10][2] = 100;
			iArray[10][3] = 2;
			iArray[10][4] = "changerate"; //���ô��� 
			iArray[10][5] = "10|11"; //���ô����Ӧ�ڼ��У�'|'Ϊ�ָ��
			iArray[10][6] = "1|0"; //��������з������ô����еڼ�λֵ,��:�����ֵ��λ��
			iArray[10][18] = "150";
			iArray[10][19] = 1;

			iArray[11] = new Array();
			iArray[11][0] = "��̬ϵ������";
			iArray[11][1] = "20px";
			iArray[11][2] = 100;
			iArray[11][3] = 3;
			*/
			ContCessGrid = new MulLineEnter("fm", "ContCessGrid");
			//��Щ���Ա�����loadMulLineǰ
			ContCessGrid.mulLineCount = 0;
			ContCessGrid.displayTitle = 1;
			ContCessGrid.locked = 0;
			ContCessGrid.canSel = 0; // 1 ��ʾ ��0 ���أ�ȱʡֵ��
			//ContCessGrid.selBoxEventFuncName ="CardInfoGridClick"; //��Ӧ�ĺ���������������   
			ContCessGrid.hiddenPlus = 1;
			ContCessGrid.hiddenSubtraction = 1;
			ContCessGrid.loadMulLine(iArray);
		} catch (ex) {
			myAlert(ex);
		}
	}

	// �ֱ������������б�ĳ�ʼ��
	function initScaleLineGrid() {
		var iArray = new Array();
		try {
			iArray[0] = new Array();
			iArray[0][0] = "���"; //����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
			iArray[0][1] = "30px"; //�п�
			iArray[0][2] = 40; //�����ֵ
			iArray[0][3] = 0; //�Ƿ���������,1��ʾ����0��ʾ������   			     

			iArray[1] = new Array();
			iArray[1][0] = "��˾����";
			iArray[1][1] = "110px";
			iArray[1][2] = 100;
			iArray[1][3] = 2;
			iArray[1][4] = "ricompanycode";
			iArray[1][5] = "1|2"; //�����ô���ֱ���ڵ�1��2
			iArray[1][6] = "0|1";
			iArray[1][19] = 1;

			iArray[2] = new Array();
			iArray[2][0] = "��˾����";
			iArray[2][1] = "210px";
			iArray[2][2] = 100;
			iArray[2][3] = 1;
			iArray[2][9] = "��˾����|notnull";

			iArray[3] = new Array();
			iArray[3][0] = "�ֱ���˾����";
			iArray[3][1] = "150px";
			iArray[3][2] = 100;
			iArray[3][3] = 2;
			iArray[3][9] = "�ֱ���˾����|notnull";
			iArray[3][4] = "ricalflag"; //���ô��룺"AccType"Ϊ�������ݵ�����,�ÿؼ�������
			iArray[3][5] = "3|4"; //���ô����Ӧ�ڼ��У�'|'Ϊ�ָ��
			iArray[3][6] = "1|0"; //��������з������ô����еڼ�λֵ,��:�����ֵ��λ��
			iArray[3][18] = "150";
			iArray[3][19] = 1;

			iArray[4] = new Array();
			iArray[4][0] = "�ֱ���˾���ʹ���";
			iArray[4][1] = "0px";
			iArray[4][2] = 100;
			iArray[4][3] = 3;
			iArray[4][9] = "�ֱ���˾���ʹ���|notnull";

			iArray[5] = new Array();
			iArray[5][0] = "��ֹ������ʧ";
			iArray[5][1] = "0px";
			iArray[5][2] = 100;
			iArray[5][3] = 3;
			iArray[5][14] = "��ֹ������ʧ";

			ScaleLineGrid = new MulLineEnter("fm", "ScaleLineGrid");
			//��Щ���Ա�����loadMulLineǰ
			ScaleLineGrid.mulLineCount = 0;
			ScaleLineGrid.displayTitle = 1;
			ScaleLineGrid.locked = 0;
			ScaleLineGrid.canSel = 0; // 1 ��ʾ ��0 ���أ�ȱʡֵ��
			//ScaleLineGrid.selBoxEventFuncName ="CardInfoGridClick"; //��Ӧ�ĺ���������������   
			ScaleLineGrid.hiddenPlus = 1;
			ScaleLineGrid.hiddenSubtraction = 1;
			ScaleLineGrid.loadMulLine(iArray);
		} catch (ex) {
			myAlert(ex);
		}
	}

	//�ֱ��������ó�ʼ��
	function initCessScaleGrid() {
		var iArray = new Array();
		try {
			iArray[0] = new Array();
			iArray[0][0] = "���"; //����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
			iArray[0][1] = "30px"; //�п�
			iArray[0][2] = 40; //�����ֵ
			iArray[0][3] = 3; //�Ƿ���������,1��ʾ����0��ʾ������   			     

			iArray[1] = new Array();
			iArray[1][0] = "�㼶";
			iArray[1][1] = "160px";
			iArray[1][2] = 40;
			iArray[1][3] = 0;

			//alert("init:  line"+line.length+"  com:"+com.length);
			for ( var i = 1; i <= com.length; i++) {
				iArray[i + 1] = new Array();
				iArray[i + 1][0] = com[i - 1]; //��˾����
				iArray[i + 1][1] = "140px";
				iArray[i + 1][2] = 100;
				iArray[i + 1][3] = 1;
				iArray[i + 1][9] = "�ֱ���������,ÿ�е�ֵ |notnull&num";
			}
			CessScaleGrid = new MulLineEnter("fm", "CessScaleGrid");
			//��Щ���Ա�����loadMulLineǰ
			CessScaleGrid.mulLineCount = 0;
			CessScaleGrid.displayTitle = 1;
			CessScaleGrid.locked = 0;
			CessScaleGrid.canSel = 0; // 1 ��ʾ ��0 ���أ�ȱʡֵ��
			//CessScaleGrid.selBoxEventFuncName ="CardInfoGridClick"; //��Ӧ�ĺ���������������   
			CessScaleGrid.hiddenPlus = 1;
			CessScaleGrid.hiddenSubtraction = 1;
			CessScaleGrid.loadMulLine(iArray);
		} catch (ex) {
			myAlert(ex);
		}
	}

	function initFeeRateGrid() {
		var iArray = new Array();
		try {
			iArray[0] = new Array();
			iArray[0][0] = "���"; //����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
			iArray[0][1] = "40px"; //�п�
			iArray[0][2] = 40; //�����ֵ
			iArray[0][3] = 0; //�Ƿ���������,1��ʾ����0��ʾ������

			iArray[1] = new Array();
			iArray[1][0] = "�ٱ���˾����";
			iArray[1][1] = "100px";
			iArray[1][2] = 100;
			iArray[1][3] = 0;

			iArray[2] = new Array();
			iArray[2][0] = "�ٱ���˾����";
			iArray[2][1] = "140px";
			iArray[2][2] = 100;
			iArray[2][3] = 1;

			iArray[3] = new Array();
			iArray[3][0] = "��������";
			iArray[3][1] = "70px";
			iArray[3][2] = 100;
			iArray[3][3] = 1;

			iArray[4] = new Array();
			iArray[4][0] = "��������";
			iArray[4][1] = "70px";
			iArray[4][2] = 100;
			iArray[4][3] = 1;

			iArray[5] = new Array();
			iArray[5][0] = "������";
			iArray[5][1] = "60px";
			iArray[5][2] = 100;
			iArray[5][3] = 1;

			iArray[6] = new Array();
			iArray[6][0] = "�ֱ����ʱ���";
			iArray[6][1] = "110px";
			iArray[6][2] = 100;
			iArray[6][3] = 2;
			iArray[6][4] = "rifeerate";
			iArray[6][5] = "6|7";
			iArray[6][6] = "0|1";
			iArray[6][9] = "�ֱ����ʱ�|notnull";
			iArray[6][19] = 1;

			iArray[7] = new Array();
			iArray[7][0] = "�ֱ����ʱ�����";
			iArray[7][1] = "160px";
			iArray[7][2] = 100;
			iArray[7][3] = 0;

			iArray[8] = new Array();
			iArray[8][0] = "�ֱ�Ӷ���ʱ���";
			iArray[8][1] = "110px";
			iArray[8][2] = 100;
			iArray[8][3] = 2;
			iArray[8][4] = "rifeerate";
			iArray[8][5] = "8|9";
			iArray[8][6] = "0|1";
			iArray[8][19] = 1;

			iArray[9] = new Array();
			iArray[9][0] = "�ֱ�Ӷ���ʱ�����";
			iArray[9][1] = "160";
			iArray[9][2] = 100;
			iArray[9][3] = 0;

			iArray[10] = new Array();
			iArray[10][0] = "remark";
			iArray[10][1] = "0px";
			iArray[10][2] = 100;
			iArray[10][3] = 3;

			FeeRateGrid = new MulLineEnter("fm", "FeeRateGrid");
			//��Щ���Ա�����loadMulLineǰ
			FeeRateGrid.mulLineCount = 0;
			FeeRateGrid.displayTitle = 1;
			FeeRateGrid.locked = 0;
			FeeRateGrid.canSel = 1; // 1 ��ʾ ��0 ���أ�ȱʡֵ��
			FeeRateGrid.hiddenPlus = 1;
			FeeRateGrid.hiddenSubtraction = 1;
			FeeRateGrid.loadMulLine(iArray);

		} catch (ex) {
			myAlert(ex);
		}
	}

	// ��֤��Ϣ�б�ĳ�ʼ��
	function initFactorGrid() {
		var contNo = fm.RIContNo.value;
		var iArray = new Array();
		try {
			iArray[0] = new Array();
			iArray[0][0] = "���"; //����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
			iArray[0][1] = "40px"; //�п�
			iArray[0][2] = 40; //�����ֵ
			iArray[0][3] = 0; //�Ƿ���������,1��ʾ����0��ʾ������   			     

			iArray[1] = new Array();
			iArray[1][0] = "�ٱ���˾";
			iArray[1][1] = "100px";
			iArray[1][2] = 100;
			iArray[1][3] = 2;
			iArray[1][4] = "ricompanyname";
			iArray[1][5] = "1|2";
			iArray[1][6] = "0|1";
			iArray[1][9] = "�ٱ���˾|notnull";
			iArray[1][15] = "ripreceptno";
			iArray[1][16] = fm.RIPreceptNo.value;
			iArray[1][19] = 1;

			iArray[2] = new Array();
			iArray[2][0] = "�ٱ���˾����";
			iArray[2][1] = "140px";
			iArray[2][2] = 100;
			iArray[2][3] = 1;

			iArray[3] = new Array();
			iArray[3][0] = "���ֱ���";
			iArray[3][1] = "0px";
			iArray[3][2] = 100;
			iArray[3][3] = 3;
			iArray[3][9] = "����|notnull";

			iArray[4] = new Array();
			iArray[4][0] = "��������";
			iArray[4][1] = "90px";
			iArray[4][2] = 100;
			iArray[4][3] = 2;
			iArray[4][4] = "currency";
			iArray[4][5] = "4|3"; //�����ô���ֱ���ڵ�1��2
			iArray[4][6] = "1|0";
			iArray[4][19] = 1;

			iArray[5] = new Array();
			iArray[5][0] = "Ҫ�����";
			iArray[5][1] = "0px";
			iArray[5][2] = 100;
			iArray[5][3] = 3;
			iArray[5][9] = "Ҫ�����|notnull";

			iArray[6] = new Array();
			iArray[6][0] = "Ҫ�������";
			iArray[6][1] = "90px";
			iArray[6][2] = 100;
			iArray[6][3] = 2;
			iArray[6][4] = "rifactortype";
			iArray[6][5] = "6|5"; //�����ô���ֱ���ڵ�1��2
			iArray[6][6] = "1|0";
			iArray[6][19] = 1;

			iArray[7] = new Array();
			iArray[7][0] = "Ҫ������";
			iArray[7][1] = "90px";
			iArray[7][2] = 100;
			iArray[7][3] = 2;
			iArray[7][4] = "rifactor";
			iArray[7][5] = "7|8"; //�����ô���ֱ���ڵ�1��2
			iArray[7][6] = "0|1";
			iArray[7][19] = 1;
			iArray[7][9] = "Ҫ������|notnull";
			
			iArray[8] = new Array();
			iArray[8][0] = "Ҫ�ش���";
			iArray[8][1] = "0px";
			iArray[8][2] = 100;
			iArray[8][3] = 3;

			iArray[9] = new Array();
			iArray[9][0] = "��ֵ����";
			iArray[9][1] = "80px";
			iArray[9][2] = 100;
			iArray[9][3] = 2;
			iArray[9][4] = "rivaluetype";
			iArray[9][5] = "9|12"; //�����ô���ֱ���ڵ�1��2
			iArray[9][6] = "1|0";
			iArray[9][9] = "��ֵ����|notnull";
			iArray[9][19] = 1;
			
			iArray[10] = new Array();
			iArray[10][0] = "Ҫ��ֵ";
			iArray[10][1] = "110px";
			iArray[10][2] = 100;
			iArray[10][3] = 1;
			iArray[10][9] = "Ҫ��ֵ|notnull";
			
			iArray[11] = new Array();
			iArray[11][0] = "Ҫ��ֵ˵��";
			iArray[11][1] = "150px";
			iArray[11][2] = 100;
			iArray[11][3] = 1;
			
			iArray[12] = new Array();
			iArray[12][0] = "ValueType";
			iArray[12][1] = "0px";
			iArray[12][2] = 100;
			iArray[12][3] = 3;
			
			iArray[13] = new Array();
			iArray[13][0] = "Ҫ�ر�ʶ";
			iArray[13][1] = "80px";
			iArray[13][2] = 100;
			iArray[13][3] = 1;
			
			iArray[14] = new Array();
			iArray[14][0] = "SerialNo";
			iArray[14][1] = "0px";
			iArray[14][2] = 100;
			iArray[14][3] = 3;
			
			iArray[15] = new Array();
			iArray[15][0] = "�����������";
			iArray[15][1] = "80px";
			iArray[15][2] = 100;
			iArray[15][3] = 2;
			iArray[15][4] = "riparatype";
			iArray[15][5] = "15|16"; //�����ô���ֱ���ڵ�1��2
			iArray[15][6] = "1|0";
			iArray[15][9] = "�����������|notnull";
			iArray[15][19] = 1;
			
			iArray[16] = new Array();
			iArray[16][0] = "�������ʹ���";
			iArray[16][1] = "0px";
			iArray[16][2] = 100;
			iArray[16][3] = 3;

			FactorGrid = new MulLineEnter("fm", "FactorGrid");
			//��Щ���Ա�����loadMulLineǰ
			FactorGrid.mulLineCount = 0;
			FactorGrid.displayTitle = 1;
			FactorGrid.locked = 0;
			FactorGrid.canSel = 0; // 1 ��ʾ ��0 ���أ�ȱʡֵ��
			//FactorGrid.selBoxEventFuncName ="CardInfoGridClick"; //��Ӧ�ĺ���������������   
			FactorGrid.hiddenPlus = 0;
			FactorGrid.hiddenSubtraction = 0;
			FactorGrid.loadMulLine(iArray);

		} catch (ex) {
			myAlert(ex);
		}
	}
</script>