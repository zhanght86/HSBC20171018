<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//�������ƣ�RReportReplyInit.jsp
//�����ܣ�������鱨��ظ�
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

  //tProposalNo = request.getParameter("ProposalNo2");
  //tFlag = request.getParameter("Flag");


  //loggerDebug("BQRReportReplyInit","ProposalNo:"+tProposalNo);
  //loggerDebug("BQRReportReplyInit","Flag:"+tFlag);


%>            
<script language="JavaScript">


// �����ĳ�ʼ��������¼���֣�
function initInpBox()
{ 
try
  {                                   
    document.all('QContNo').value = '';
    document.all('QManagecom').value = '';
    document.all('QInsuredName').value = '';
    //document.all('Content').value = '';
    //document.all('ReplyResult').value = '';
  }
  catch(ex)
  {
    alert("��RReportQueryInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
  }   
}

function initForm()
{
  try
  {	
  	initInpBox();
	initQuestGrid();		
  }
  catch(re)
  {
    alert("UWSubInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
  }
}

// ������Ϣ�б�ĳ�ʼ��
function initQuestGrid()
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
      iArray[1][0]="������";    	//����
      iArray[1][1]="140px";            		//�п�
      iArray[1][2]=100;            			//�����ֵ
      iArray[1][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[2]=new Array();
      iArray[2][0]="Ͷ����ӡˢ��";    	//����
      iArray[2][1]="0px";            		//�п�
      iArray[2][2]=100;            			//�����ֵ
      iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[3]=new Array();
      iArray[3][0]="������";         			//����
      iArray[3][1]="80px";            		//�п�
      iArray[3][2]=100;            			//�����ֵ
      iArray[3][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[4]=new Array();
      iArray[4][0]="¼����";         			//����
      iArray[4][1]="80px";            		//�п�
      iArray[4][2]=60;            			//�����ֵ
      iArray[4][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
                           
      iArray[5]=new Array();
      iArray[5][0]="¼������";         			//����
      iArray[5][1]="80px";            		//�п�
      iArray[5][2]=60;            			//�����ֵ
      iArray[5][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[6]=new Array();
      iArray[6][0]="�ظ���";         			//����
      iArray[6][1]="80px";            		//�п�
      iArray[6][2]=60;            			//�����ֵ
      iArray[6][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[7]=new Array();
      iArray[7][0]="�ظ�����";         			//����
      iArray[7][1]="80px";            		//�п�
      iArray[7][2]=60;            			//�����ֵ
      iArray[7][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[8]=new Array();
      iArray[8][0]="�Ƿ�ظ�";         			//����
      iArray[8][1]="60px";            		//�п�
      iArray[8][2]=60;            			//�����ֵ
      iArray[8][3]=2;              			//�Ƿ���������,1��ʾ����0��ʾ������   
      iArray[8][10] = "ReplyFlag";
      iArray[8][11] = "0|^0|δ�ظ�^1|�ѻظ�";
      iArray[8][12] = "8";
      iArray[8][19] = "0";   
      
      iArray[9]=new Array();
      iArray[9][0]="��ˮ��";         			//����
      iArray[9][1]="0px";            		//�п�
      iArray[9][2]=60;            			//�����ֵ
      iArray[9][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������        
      
      iArray[10]=new Array();
      iArray[10][0]="missionid";         			//����
      iArray[10][1]="0px";            		//�п�
      iArray[10][2]=60;            			//�����ֵ
      iArray[10][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[11]=new Array();
      iArray[11][0]="submissionid";         			//����
      iArray[11][1]="0px";            		//�п�
      iArray[11][2]=60;            			//�����ֵ
      iArray[11][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������    
      
      iArray[12]=new Array();
      iArray[12][0]="activityid";         			//����
      iArray[12][1]="0px";            		//�п�
      iArray[12][2]=60;            			//�����ֵ
      iArray[12][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������   
 
      
      QuestGrid = new MulLineEnter( "fm" , "QuestGrid" ); 
      //��Щ���Ա�����loadMulLineǰ                            
      QuestGrid.mulLineCount = 5;
      QuestGrid.displayTitle = 1;
      QuestGrid.canSel = 1;
      QuestGrid.hiddenPlus = 1;
      QuestGrid.hiddenSubtraction = 1;
      QuestGrid.loadMulLine(iArray);
      
      QuestGrid. selBoxEventFuncName = "easyQueryChoClick";
      
      }
      catch(ex)
      {
        alert(ex);
      }
}


</script>


