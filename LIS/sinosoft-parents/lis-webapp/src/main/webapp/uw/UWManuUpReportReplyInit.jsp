<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//�������ƣ�UWManuRReportInit.jsp
//�����ܣ�����Լ�˹��˱�������鱨��¼��
//�������ڣ�2002-06-19 11:10:36
//������  ��WHN
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
  String tPrtNo = "";
  String tMissionID = "";
  String tSubMissionID = "";
  String tFlag = "";
  tContNo = request.getParameter("ContNo");  
   tPrtNo = request.getParameter("PrtNo");  
  tMissionID = request.getParameter("MissionID");  
  tSubMissionID = request.getParameter("SubMissionID");
  tFlag = request.getParameter("Flag");
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
	//loggerDebug("UWManuUpReportReplyInit","1:"+strOperator);
%>

<script language="JavaScript">

var str = "";

// �����ĳ�ʼ��������¼���֣�
function initInpBox(tPrtNo)
{ 
try
  {                                   
     document.all('PrtNo').value=tPrtNo;
  }
  catch(ex)
  {
    alert("��UWManuUpReportReplyInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
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
    alert("��UWManuUpReportReplyInit-->InitSelBox�����з����쳣:��ʼ���������!");
  }
}                                        

function initForm(tContNo,tPrtNo,tMissionID,tSubMissionID)
{
  var str = "";
  try
  {
 
	initInpBox(tPrtNo);

	initHide(tContNo,tMissionID,tContNo,tSubMissionID);

	initTraceGrid();
	querytrace();
	easyQueryClick();
	
	
	

  }
  catch(re)
  {
    alert("UWManuUpReportReplyInit-->InitForm�����з����쳣:��ʼ���������!");
  }
}

function initHide(tContNo,tMissionID,tContNo,tSubMissionID)
{

	document.all('ProposalNoHide').value=tContNo;
	document.all('MissionID').value=tMissionID;
	document.all('SubMissionID').value=tSubMissionID;	
	document.all('ContNo').value=tContNo;
	document.all('Flag').value=<%=tFlag%>;
	
}


function initTraceGrid()
  {                               
    var iArray = new Array();
      
      try
      {
      iArray[0]=new Array();
      iArray[0][0]="���";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
      iArray[0][1]="30px";            		//�п�
      iArray[0][2]=30;            			//�����ֵ
      iArray[0][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[1]=new Array();
      iArray[1][0]="�ʱ�ԭ��";         		//����
      iArray[1][1]="100px";            		//�п�
      iArray[1][2]=100;            			//�����ֵ
      iArray[1][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[2]=new Array();
      iArray[2][0]="�ٱ�����";         		//����
      iArray[2][1]="150px";            		//�п�
      iArray[2][2]=100;            			//�����ֵ
      iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[3]=new Array();
      iArray[3][0]="ԭ������";         		//����
      iArray[3][1]="100px";            		//�п�
      iArray[3][2]=100;            			//�����ֵ
      iArray[3][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[4]=new Array();
      iArray[4][0]="�ٱ���ע";         		//����
      iArray[4][1]="120px";            		//�п�
      iArray[4][2]=100;            			//�����ֵ
      iArray[4][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������      

      iArray[5]=new Array();
      iArray[5][0]="����ʱ��";         		//����
      iArray[5][1]="80px";            		//�п�
      iArray[5][2]=100;            			//�����ֵ
      iArray[5][3]=8;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[6]=new Array();
      iArray[6][0]="������";         		//����
      iArray[6][1]="60px";            		//�п�
      iArray[6][2]=100;            			//�����ֵ
      iArray[6][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[7]=new Array();
      iArray[7][0]="�ظ�ʱ��";         		//����
      iArray[7][1]="80px";            		//�п�
      iArray[7][2]=100;            			//�����ֵ
      iArray[7][3]=8;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[8]=new Array();
      iArray[8][0]="�ظ���";         		//����
      iArray[8][1]="60px";            		//�п�
      iArray[8][2]=100;            			//�����ֵ
      iArray[8][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
        
           
      TraceGrid = new MulLineEnter( "fm" , "TraceGrid" ); 
      //��Щ���Ա�����loadMulLineǰ
      TraceGrid.mulLineCount = 1;   
      TraceGrid.displayTitle = 1;
      TraceGrid.locked = 1;
      TraceGrid.canSel = 0;
      TraceGrid.hiddenPlus = 1;
      TraceGrid.hiddenSubtraction = 1;
      TraceGrid.loadMulLine(iArray);     
      
    //  RiskGrid. selBoxEventFuncName = "showRiskResult";
      
      //��Щ����������loadMulLine����
      //PolGrid.setRowColData(1,1,"asdf");
      }
      catch(ex)
      {
        alert(ex);
      }
}




</script>


