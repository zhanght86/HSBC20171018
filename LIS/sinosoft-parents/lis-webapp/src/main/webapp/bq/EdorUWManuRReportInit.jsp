<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//�������ƣ�EdorUWManuRReportInit.jsp
//�����ܣ���ȫ�˹��˱�������鱨��¼��
//�������ڣ�2005-07-14 18:10:36
//������  ��liurx
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<!--�û�У����-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.tb.*"%>
  <%@page import="com.sinosoft.lis.cbcheck.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
<%
  String tContNo = "";
  String tEdorNo = "";
  String tPrtNo = "";
  String tMissionID = "";
  String tSubMissionID = "";
  tContNo = request.getParameter("ContNo");  
  tPrtNo = request.getParameter("PrtNo");  
  tMissionID = request.getParameter("MissionID");  
  tSubMissionID = request.getParameter("SubMissionID");
  tEdorNo = request.getParameter("EdorNo");
%>                            

<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<%
	//���ҳ��ؼ��ĳ�ʼ����
	GlobalInput globalInput = (GlobalInput)session.getValue("GI");
	
	if(globalInput == null) {
		out.println("session has expired");
		return;
	}
	
	
	String strOperator = globalInput.Operator;
	//loggerDebug("EdorUWManuRReportInit","1:"+strOperator);
%>

<script language="JavaScript">


// �����ĳ�ʼ��������¼���֣�
function initInpBox(tContNo,tEdorNo)
{ 
try
  {                                   
    document.all('ContNo').value = tContNo;
    document.all('EdorNo').value = tEdorNo;
    document.all('Operator').value = '<%= strOperator %>';
    document.all('RReportReason').value = "";
    document.all('InsureNo').value = "";
    document.all('InsureNoname').value = "";
    document.all('RReportReasonname').value = "";
    document.all('Contente').value = "";
  }
  catch(ex)
  {
    alert("��EdorUWManuRReportInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
  }   
}

// ������ĳ�ʼ��
function initSelBox()
{  
  try                 
  {

  }
  catch(ex)
  {
    alert("��EdorUWManuRReportInit-->InitSelBox�����з����쳣:��ʼ���������!");
  }
}                                        

function initForm(tContNo,tEdorNo,tPrtNo,tMissionID,tSubMissionID)
{
  try
  { //alert(83);
	initInpBox(tContNo,tEdorNo);
	initHide(tMissionID,tPrtNo,tSubMissionID);
	initInvestigateGrid();
	
	easyQueryClickSingle();
    initInsureNo(tContNo,tEdorNo);

  }
  catch(re)
  {
    alert("EdorUWManuRReportInit-->InitForm�����з����쳣:��ʼ���������!");
  }
}

function initHide(tMissionID,tPrtNo,tSubMissionID)
{

	document.all('MissionID').value=tMissionID;
	document.all('SubMissionID').value=tSubMissionID;	
	document.all('PrtNo').value=tPrtNo;

	
}



function initInvestigateGrid()
{
    var iArray = new Array();
      
      try
      {
      iArray[0]=new Array();
      iArray[0][0]="���";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
      iArray[0][1]="30px";         			//�п�
      iArray[0][2]=10;          			//�����ֵ
      iArray[0][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[1]=new Array();
      iArray[1][0]="������Ŀ���";    	//����
      iArray[1][1]="120px";            		//�п�
      iArray[1][2]=100;            			//�����ֵ
      iArray[1][3]=2;              			//�Ƿ���������,1��ʾ����0��ʾ������
      iArray[1][4] = "RReportCode1";             			//�Ƿ���������,1��ʾ����0��ʾ������
      iArray[1][5]="1|2";     //���ô����Ӧ�ڼ��У�'|'Ϊ�ָ��
      iArray[1][6]="0|1";    //��������з������ô����еڼ�λֵ
      iArray[1][18] = 500;

      iArray[2]=new Array();
      iArray[2][0]="������Ŀ����";         			//����
      iArray[2][1]="150px";            		//�п�
      iArray[2][2]=100;            			//�����ֵ
      iArray[2][3]=1;              			//�Ƿ���������,1��ʾ����0��ʾ������

      
      InvestigateGrid = new MulLineEnter( "fm" , "InvestigateGrid" ); 
      //��Щ���Ա�����loadMulLineǰ                            
      InvestigateGrid.mulLineCount = 0;
      InvestigateGrid.displayTitle = 1;
      InvestigateGrid.canChk = 0;
      InvestigateGrid.loadMulLine(iArray);  
      
      //��Щ����������loadMulLine����
      //HealthGrid.setRowColData(1,1,"asdf");
      }
      catch(ex)
      {
        alert(ex);
      }
}
</script>


