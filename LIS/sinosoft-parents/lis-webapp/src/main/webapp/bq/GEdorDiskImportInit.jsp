<%
/*******************************************************************************
* <p>Title: ��ȫ-�ŵ����̵���</p>
* <p>Description: �ŵ����̵���js�ļ�</p>
* <p>Copyright: Copyright (c) 2005 Sinosoft, Co.,Ltd. All Rights Reserved</p>
* <p>Company: �п���Ƽ��ɷ����޹�˾</p>
* <p>WebSite: http://www.sinosoft.com.cn</p>
*
* @todo     : ��ȫ-�ŵ����̵���
* @author   : zhangtao
* @version  : 1.00
* @date     : 2006-11-24
* @modify   : 2006-11-25
******************************************************************************/
%> 
<!--�û�У����-->
<script language="JavaScript">
function initInpBox()
{
	try
	{
		fm.GrpContNo.value="<%=request.getParameter("grpcontno")%>";
 
		document.all('FileName').value = '';
		document.all('EdorType').value = top.opener.document.all('EdorType').value;
		document.all('EdorValiDate').value = top.opener.document.all('EdorValiDate').value;
		document.all('EdorNo').value = top.opener.document.all('EdorNo').value;
		document.all('EdorAcceptNo').value = top.opener.document.all('EdorAcceptNo').value;
		
	}
	catch(ex)
	{
		alert("GEdorDiskImportInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
	}
}

function initForm()
{
	try
	{
		initInpBox();
		initDataGrid(); //����mileline����
	}
	catch(re)
	{
		alert("GEdorDiskImportInit.jsp-->InitForm���������쳣:��ʼ���������!");
	}
}

/********************************************
 *��ʼ��mileLine�ؼ�
 *******************************************/
function initDataGrid()
{
	var iArray = new Array();                           //������, ���ظ� MultiLine ���

	try
	{
		iArray[0] = new Array();
		iArray[0][0] = "���";                          //����(˳���, ������)
		iArray[0][1] = "30px";                          //�п�
		iArray[0][2] = 30;                              //�����ֵ
		iArray[0][3] = 0;                               //�Ƿ�����༭: 0��ʾ������; 1��ʾ����

		iArray[1] = new Array();
		iArray[1][0] = "���屣����";
		iArray[1][1] = "90px";
		iArray[1][2] = 100;
		iArray[1][3] = 0;

		iArray[2] = new Array();
		iArray[2][0] = "��ȫ�����";
		iArray[2][1] = "100px";
		iArray[2][2] = 100;
		iArray[2][3] = 0;
		
		iArray[3] = new Array();
		iArray[3][0] = "��������";
		iArray[3][1] = "60px";
		iArray[3][2] = 60;
		iArray[3][3] = 2;
    iArray[3][4] = "gedortype";
    iArray[3][18] = 236;
    
		iArray[4] = new Array();
		iArray[4][0] = "�������κ�";
		iArray[4][1] = "80px";
		iArray[4][2] = 50;
		iArray[4][3] = 0;

		iArray[5] = new Array();
		iArray[5][0] = "ҵ�����";
		iArray[5][1] = "60px";
		iArray[5][2] = 80;
		iArray[5][3] = 0;
		
		iArray[6] = new Array();
		iArray[6][0] = "������Ϣ";
		iArray[6][1] = "200px";
		iArray[6][2] = 200;
		iArray[6][3] = 0;
		
		iArray[7] = new Array();
		iArray[7][0] = "����Ա";
		iArray[7][1] = "70px";
		iArray[7][2] = 100;
		iArray[7][3] = 0;
		
		iArray[8] = new Array();
		iArray[8][0] = "��������";
		iArray[8][1] = "70px";
		iArray[8][2] = 100;
		iArray[8][3] = 0;
		
		iArray[9] = new Array();
		iArray[9][0] = "����ʱ��";
		iArray[9][1] = "70px";
		iArray[9][2] = 150;
		iArray[9][3] = 0;


	}
	catch (ex)
	{
		alert("�� GEdorDiskImportInit.jsp --> initDataGrid() �����з����쳣: ��ʼ���������");
	}

	try
	{
		DataGrid = new MulLineEnter("fm", "DataGrid");
		DataGrid.mulLineCount = 5;
		DataGrid.displayTitle = 1;
		DataGrid.locked = 1;
		DataGrid.hiddenPlus = 1;
		DataGrid.hiddenSubtraction = 1;
		DataGrid.canChk = 0;
		DataGrid.canSel = 0;
		DataGrid.chkBoxEventFuncName = "";
		DataGrid.selBoxEventFuncName = "";		//ֵΪ��radio��ѡ��ʱ�����¼��ĺ�����
		//�������Ա����� MulLineEnter loadMulLine ֮ǰ
		DataGrid.loadMulLine(iArray);
	}
	catch (ex)
	{
		alert("�� GEdorDiskImportInit.jsp --> initDataGrid() �����з����쳣: ��ʼ���������");
	}
}
</script>
