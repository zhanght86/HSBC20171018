<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//�������ƣ�BackPolQueryInit.jsp
//�����ܣ����������ѯ
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
<%
  String tProposalNo = "";
  String tFlag = "";

  tProposalNo = request.getParameter("ProposalNo2");
  tFlag = request.getParameter("Flag");


  loggerDebug("BackPolQueryInit","ProposalNo:"+tProposalNo);
  loggerDebug("BackPolQueryInit","Flag:"+tFlag);


%>            
<script language="JavaScript">


// �����ĳ�ʼ��������¼���֣�
function initInpBox(tProposalNo,tFlag)
{ 
try
  {                                   
    document.all('ProposalNo').value = tProposalNo;
    document.all('IfReply').value = '';
    if (tFlag == "5")
    {
    	document.all('BackObj').value = '1';
    }
    else
    {
    	document.all('BackObj').value = '';
    }
    
    document.all('ManageCom').value = '';
    document.all('OManageCom').value = '';
    
    if (tFlag == "5")
    {
    	document.all('OperatePos').value = '5';
    }
    else if(tFlag == "3"||tFlag == "1")
    {
    	document.all('OperatePos').value = '1';	    
    }
    else if(tFlag == "4")
    {
    	document.all('OperatePos').value = '1';
    }
    else
    {
    	document.all('OperatePos').value = '';
    }
    document.all('Content').value = '';
  }
  catch(ex)
  {
    alert("��RReportQueryInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
  }   
}

function initForm(tProposalNo)
{
  try
  {	
	initBackPolGrid();		
	initHide(tProposalNo);
	easyQueryClick(tProposalNo);	
	

  }
  catch(re)
  {
    alert("UWSubInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
  }
}

// ������Ϣ�б�ĳ�ʼ��
function initBackPolGrid()
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
      iArray[1][0]="����Ͷ������";    	//����
      iArray[1][1]="0px";            		//�п�
      iArray[1][2]=100;            			//�����ֵ
      iArray[1][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[2]=new Array();
      iArray[2][0]="ӡˢ��";         			//����
      iArray[2][1]="180px";            		//�п�
      iArray[2][2]=100;            			//�����ֵ
      iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[3]=new Array();
      iArray[3][0]="����˵��";         			//����
      iArray[3][1]="180px";            		//�п�
      iArray[3][2]=100;            			//�����ֵ
      iArray[3][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
                           
      iArray[4]=new Array();
      iArray[4][0]="������";         			//����
      iArray[4][1]="100px";            		//�п�
      iArray[4][2]=60;            			//�����ֵ
      iArray[4][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[5]=new Array();
      iArray[5][0]="��������";         			//����
      iArray[5][1]="100px";            		//�п�
      iArray[5][2]=60;            			//�����ֵ
      iArray[5][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
                  
      BackPolGrid = new MulLineEnter( "fm" , "BackPolGrid" ); 
      //��Щ���Ա�����loadMulLineǰ                            
      BackPolGrid.mulLineCount = 1;
      BackPolGrid.displayTitle = 1;
      BackPolGrid.canSel = 1;
      BackPolGrid.loadMulLine(iArray);
      
      BackPolGrid. selBoxEventFuncName = "easyQueryChoClick";
      
      }
      catch(ex)
      {
        alert(ex);
      }
}

function initHide(tProposalNo)
{
	document.all('ProposalNoHide').value=tProposalNo;		
}

</script>


