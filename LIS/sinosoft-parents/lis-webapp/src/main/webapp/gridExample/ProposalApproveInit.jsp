<%
//�������ƣ�ProposalApproveInit.jsp
//�����ܣ�
//�������ڣ�2002-06-19 11:10:36
//������  ��CrtHtml���򴴽�
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<!--�û�У����-->
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<%
     //���ҳ��ؼ��ĳ�ʼ����
%>                            

<script language="JavaScript">

// �����ĳ�ʼ��������¼���֣�
function initInpBox()
{ 

  try
  {                                   
	// ������ѯ����
    document.all('ProposalNo').value = '';
    document.all('ManageCom').value = '';
    document.all('AgentCode').value = '';
    //document.all('AgentGroup').value = '';
  }
  catch(ex)
  {
    alert("��ProposalApproveInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
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
    alert("��ProposalApproveInit.jsp-->InitSelBox�����з����쳣:��ʼ���������!");
  }
}                                        

function initForm()
{
	initInpBox();  
	initSelBox();       
	initPolGrid();
	initSelfPolGrid();	
}

// ������Ϣ�б�ĳ�ʼ��
function initPolGrid()
  {                               
    
    bg = new CGrid("document", "PolGrid", turnPage1);
  	bg.setActivityId("0000001001");
  	bg.setProcessId("0000000003");
  	bg.addColumn("ContNo,PrtNo,AppntName,Operator,MakeDate");
  	bg.addColumn([{name:'Missionid',readonly:'3'},{name:'submissionid',readonly:'3'},{name:'activityid',readonly:'3'}]);
  	bg.addColumn("ManageCom,AgentCode");
  	bg.addParam("ManageCom","86",'like');
  	bg.setDefaultOperator(null);
  	bg.initGrid();
  	PolGrid = bg.grid;
}

// ���˱�����Ϣ�б�ĳ�ʼ��
function initSelfPolGrid()
{                               
	cg = new CGrid("document", "SelfPolGrid", turnPage2);
	cg.setActivityId("0000001001");
	cg.setProcessId("0000000003");
	cg.addColumn("ContNo,PrtNo,AppntName,Operator,MakeDate");
	cg.addColumn([{name:'Missionid',readonly:'3'},{name:'submissionid',readonly:'3'},{name:'activityid',readonly:'3'}]);
	cg.addColumn("ManageCom,AgentCode");
	cg.setRadioFunction("InitshowApproveDetail");
	cg.showEmergency(1, 1, 1);
	cg.initGrid();
	cg.queryData();
	SelfPolGrid = cg.grid;
}


</script>
