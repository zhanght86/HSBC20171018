<%
/*******************************************************************************
* <p>Title: �ۺϲ�ѯ-���屣ȫ��ѯ</p>
* <p>Description: ���屣ȫ��ѯ-init��ʼ��ҳ��</p>
* <p>Copyright: Copyright (c) 2006 Sinosoft, Co.,Ltd. All Rights Reserved</p>
* <p>Company: �п���Ƽ��ɷ����޹�˾</p>
* <p>WebSite: http://www.sinosoft.com.cn</p>
*
* @todo     : ��ȫ-VIP�ͻ���ѯ
* @author   : liuxiaosong
* @version  : 1.00
* @date     : 2006-10-16
* ���¼�¼��  ������    ��������     ����ԭ��/����
******************************************************************************/
%>

<%@ page import="com.sinosoft.lis.pubfun.*" %>

<%
	//��õ�ǰ��½�Ĺ������
	String sYesterday = PubFun.calDate(PubFun.getCurrentDate(), -1, "D", null);
	GlobalInput tGlobalInput = new GlobalInput();
	tGlobalInput = (GlobalInput)session.getValue("GI");
	String scurManageCom = tGlobalInput.ManageCom;
	tGlobalInput = null;
%>

<script language="JavaScript">

/**
 *��ʼ��ҳ��
 */
function initForm()
{
	try
	{   
		initInpBox();  //��ʼ��ҳ������ֶ�
		initPolGrid();  //���ɲ�ѯ�������mulLine�ؼ�
	}
	catch(ex)
	{
		alert("AllGBqQueryInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
	}
} //end function initForm

/**
 *��ʼ���������Լ�������
 */
function initInpBox()
{
	try
	{
		document.all('EdorAcceptNo').value = ''; //��ȫ�����
		document.all('OtherNo').value = '';  //���屣��/����ͻ���
		document.all('OtherNoType').value = ''; //���� = �����屣��������ͻ�
		document.all('EdorAppName').value = ''; //����������
		document.all('AppType').value = ''; //���뷽ʽ
		document.all('EdorAppDate').value = ''; //����ʱ��
		document.getElementsByName("LoginManageCom")[0].value = "<%=scurManageCom%>"; //��ǰ�������
	}
	catch(ex)
	{
		alert("��AllGBqQueryInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
	}
} // end function initInpBox


/**
 *�����б�����MilLine����
 */
function initPolGrid()
{
	var iArray = new Array();

	try
	{
		iArray[0]=new Array();
		iArray[0][0]="���";                  //����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
		iArray[0][1]="30px";                  //�п�
		iArray[0][2]=10;                      //�����ֵ
		iArray[0][3]=0;                       //�Ƿ���������,1��ʾ����0��ʾ������

		iArray[1]=new Array();
		iArray[1][0]="�������";
		iArray[1][1]="145px";
		iArray[1][2]=100;
		iArray[1][3]=0;
		
		iArray[2]=new Array();
		iArray[2][0]="��������";
		iArray[2][1]="145px";
		iArray[2][2]=100;
		iArray[2][3]=0;

		iArray[3]=new Array();
		iArray[3][0]="�ͻ�/������";
		iArray[3][1]="120px";
		iArray[3][2]=100;
		iArray[3][3]=0;

		iArray[4]=new Array();
		iArray[4][0]="��������";
		iArray[4][1]="80px";
		iArray[4][2]=100;
		iArray[4][3]=0;

		iArray[5]=new Array();
		iArray[5][0]="����������";
		iArray[5][1]="0px";
		iArray[5][2]=100;
		iArray[5][3]=3;

		iArray[6]=new Array();
		iArray[6][0]="��/�˷ѽ��";
		iArray[6][1]="80px";
		iArray[6][2]=100;
		iArray[6][3]=0;

		iArray[7]=new Array();
		iArray[7][0]="������";
		iArray[7][1]="0px";
		iArray[7][2]=100;
		iArray[7][3]=3;

		iArray[8]=new Array();
		iArray[8][0]="ȷ����";
		iArray[8][1]="0px";
		iArray[8][2]=100;
		iArray[8][3]=3;

		iArray[9]=new Array();
		iArray[9][0]="����״̬";
		iArray[9][1]="80px";
		iArray[9][2]=100;
		iArray[9][3]=0;

		iArray[10]=new Array();
		iArray[10][0]="������λ";
		iArray[10][1]="120px";
		iArray[10][2]=100;
		iArray[10][3]=0;

		iArray[11]=new Array();
		iArray[11][0]="����Ա";
		iArray[11][1]="80px";
		iArray[11][2]=100;
		iArray[11][3]=0;

		iArray[12]=new Array();
		iArray[12][0]="��������";
		iArray[12][1]="80px";
		iArray[12][2]=100;
		iArray[12][3]=0;
		
		iArray[13]=new Array();
		iArray[13][0]="��ȫ��Ч����";
		iArray[13][1]="80px";
		iArray[13][2]=100;
		iArray[13][3]=0;

		iArray[14]=new Array();
		iArray[14][0]="�������";
		iArray[14][1]="0px";
		iArray[14][2]=100;
		iArray[14][3]=3;

		iArray[15]=new Array();
		iArray[15][0]="���ʱ��";
		iArray[15][1]="0px";
		iArray[15][2]=100;
		iArray[15][3]=3;

		iArray[16]=new Array();
		iArray[16][0]="�������";
		iArray[16][1]="140px";
		iArray[16][2]=100;
		iArray[16][3]=0;

		iArray[17]=new Array();
		iArray[17][0]="EdorNo";
		iArray[17][1]="0px";
		iArray[17][2]=100;
		iArray[17][3]=3;
		
		PolGrid = new MulLineEnter("fm", "PolGrid");
		//��Щ���Ա�����loadMulLineǰ
		PolGrid.mulLineCount = 10;
		PolGrid.displayTitle = 1;
		PolGrid.hiddenSubtraction=1;
		PolGrid.hiddenPlus = 1;
		PolGrid.locked = 1;
		PolGrid.canSel = 1;
		PolGrid.loadMulLine(iArray);
		//PolGrid. selBoxEventFuncName = "PBqQueryClick";
	}
	catch(ex)
	{
		alert(ex);
	}
} // end function initPolGrid

</script>
