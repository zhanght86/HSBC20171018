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
  String tGrpContNo = "";
  tContNo = request.getParameter("ContNo2");  
  tPrtNo = request.getParameter("PrtNo");  
  tMissionID = request.getParameter("MissionID");  
  tSubMissionID = request.getParameter("SubMissionID");
  tFlag = request.getParameter("Flag");
  tGrpContNo = request.getParameter("GrpContNo");
  

%>                            

<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<%
	//���ҳ��ؼ��ĳ�ʼ����
	GlobalInput globalInput = (GlobalInput)session.getValue("GI");
	
	if(globalInput == null) {
		out.println("session has expired");
		return;
	}
	
  String edorno = request.getParameter("EdorNo");
  String edortype = request.getParameter("EdorType");	
	
	String strOperator = globalInput.Operator;
	//loggerDebug("GrpUWManuRReportInit","1:"+strOperator);
%>

<script language="JavaScript">

var str = "";

// �����ĳ�ʼ��������¼���֣�
function initInpBox(tContNo)
{ 
try
  {                                   
    document.all('GrpContNo').value = tContNo;
    document.all('PrtNo').value = '<%= tPrtNo %>';
    document.all('Operator').value = '<%= strOperator %>';
    document.all('EdorNo').value = '<%= edorno %>';
    document.all('EdorType').value = '<%= edortype %>';
    //alert("conto="+document.all('ContNo').value+" prtno= "+document.all('PrtNo').value+"operator="+document.all('Operator').value+"edorno="+document.all('EdorNo').value+"edortype="+document.all('EdorType').value);
  }
  catch(ex)
  {
    alert("��PEdorUWManuRReportInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
  }   
}

// ������ĳ�ʼ��
function initSelBox()
{  
  try                 
  {
//    setOption("t_sex","0=��&1=Ů&2=����");      
//    setOption("sex","0=��&1=Ů&2=����");        
//    setOption("reduce_flag","0=����״̬&1=�����");
//    setOption("pad_flag","0=����&1=�潻");   
  }
  catch(ex)
  {
    alert("��PEdorUWManuRReportInit-->InitSelBox�����з����쳣:��ʼ���������!");
  }
}                                        

function initForm(tContNo,tPrtNo,tMissionID,tSubMissionID,tGrpContNo)
{
  var str = "";
  try
  {
	initInpBox(tGrpContNo);
  initCustomerNo(tPrtNo);
	initHide(tContNo,tMissionID,tPrtNo,tSubMissionID,tGrpContNo);
	initInvestigateGrid();
	//easyQueryClickSingle();
	
	//easyQueryClick();
  }
  catch(re)
  {
    alert("PEdorUWManuRReportInit-->InitForm�����з����쳣:��ʼ���������!");
  }
}

function initHide(tContNo,tMissionID,tPrtNo,tSubMissionID,tGrpContNo)
{ 
	document.all('ProposalNoHide').value=tContNo;
	document.all('MissionID').value=tMissionID;
	document.all('SubMissionID').value=tSubMissionID;	
	document.all('PrtNo').value=tPrtNo;
	document.all('Flag').value=<%=tFlag%>;
	document.all('ContNo').value = tContNo;
	
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
      
      iArray[3]=new Array();
      iArray[3][0]="��ע";         			//����
      iArray[3][1]="150px";            		//�п�
      iArray[3][2]=100;            			//�����ֵ
      iArray[3][3]=1;              			//�Ƿ���������,1��ʾ����0��ʾ������

      
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


