<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//�������ƣ�QuestQueryInit.jsp
//�����ܣ��������ѯ
//�������ڣ�2002-09-24 11:10:36
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
  String tContNo = "";
  String tFlag = "";
  String tMissionID = request.getParameter("MissionID");
  String tSubMissionID = request.getParameter("SubMissionID");
  tContNo = request.getParameter("ContNo");
  tFlag = request.getParameter("Flag");


  loggerDebug("QuestEndInit","ContNo:"+tContNo);
  loggerDebug("QuestEndInit","Flag:"+tFlag);


%>            
<script language="JavaScript">


// �����ĳ�ʼ��������¼���֣�
function initInpBox(tContNo,tFlag,tMissionID,tSubMissionID)
{ 
try
  {                 
    document.all('MissionID').value = tMissionID;
    document.all('SubMissionID').value = tSubMissionID;                  
    document.all('ContNo').value = tContNo;
    document.all('IfReply').value = '';
    if (tFlag == "5")
    {
    	document.all('BackObj').value = '';
    }
    else
    {
    	document.all('BackObj').value = '';
    }
    
    document.all('ManageCom').value = '';
    document.all('OManageCom').value = '';
    
    if (tFlag == "5")
    {
    	document.all('OperatePos').value = '';
    }
    else if(tFlag == "3"||tFlag == "1")
    {
    	document.all('OperatePos').value = '';	    
    }
    else if(tFlag == "4")
    {
    	document.all('BackObj').value = '4';
    	document.all('OperatePos').value = '';
    }
    else
    {
    	document.all('OperatePos').value = '';
    }
    document.all('Content').value = '';
    document.all('ReplyResult').value = '';
  }
  catch(ex)
  {
    alert("��UWManuDateInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
  }   
}

function initContent()
{
    document.all('Content').value = '';
    document.all('ReplyResult').value = '';
}

function initForm(tContNo,tFlag,tMissionID,tSubMissionID)
{
  try
  {
	initInpBox(tContNo,tFlag,tMissionID,tSubMissionID);
	initQuestGrid();
	initHide(tContNo,tFlag);	
	QuestQuery(tContNo,tFlag);

	
	if (tFlag == "5"||tFlag == "1")
	{
		showDiv(divButton,false);
	}
	else
	{
		showDiv(divButton,"true");
	}
	
	if (tFlag == "1")
	{
		showDiv(divModiButton,"true");
	}
	else
	{
		showDiv(divModiButton,"false");
	}

	initCodeData(tContNo,tFlag);
	query();

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
      iArray[1][0]="Ͷ������";    	//����
      iArray[1][1]="120px";            		//�п�
      iArray[1][2]=100;            			//�����ֵ
      iArray[1][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[2]=new Array();
      iArray[2][0]="�������";         			//����
      iArray[2][1]="50px";            		//�п�
      iArray[2][2]=50;            			//�����ֵ
      iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[3]=new Array();
      iArray[3][0]="��������";         			//����
      iArray[3][1]="150px";            		//�п�
      iArray[3][2]=60;            			//�����ֵ
      iArray[3][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
                           
      iArray[4]=new Array();
      iArray[4][0]="�ظ�����";         			//����
      iArray[4][1]="70px";            		//�п�
      iArray[4][2]=60;            			//�����ֵ
      iArray[4][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[5]=new Array();
      iArray[5][0]="¼����";         			//����
      iArray[5][1]="50px";            		//�п�
      iArray[5][2]=50;            			//�����ֵ
      iArray[5][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[6]=new Array();
      iArray[6][0]="¼������";         			//����
      iArray[6][1]="70px";            		//�п�
      iArray[6][2]=60;            			//�����ֵ
      iArray[6][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[7]=new Array();
      iArray[7][0]="����λ��";         			//����
      iArray[7][1]="50px";            		//�п�
      iArray[7][2]=50;            			//�����ֵ
      iArray[7][3]=2;              			//�Ƿ���������,1��ʾ����0��ʾ������
      iArray[7][10] = "OperateLocation";
      iArray[7][11] = "0|^0|�µ�¼��/�����޸�/�����޸�^1|�˹��˱�^5|�µ�����";
      iArray[7][12] = "7";
      iArray[7][13] = "1";

      iArray[8]=new Array();
      iArray[8][0]="���ض���";         			//����
      iArray[8][1]="50px";            		//�п�
      iArray[8][2]=50;            			//�����ֵ
      iArray[8][3]=2;              			//�Ƿ���������,1��ʾ����0��ʾ������
      iArray[8][10] = "ReturnToObject";
      iArray[8][11] = "0|^1|����Ա^2|ҵ��Ա^3|����^4|����";
      iArray[8][12] = "8";
      iArray[8][13] = "1";
      
      iArray[9]=new Array();
      iArray[9][0]="�Ƿ���Ҫ��ӡ";         			//����
      iArray[9][1]="50px";            		//�п�
      iArray[9][2]=50;            			//�����ֵ
      iArray[9][3]=2;              			//�Ƿ���������,1��ʾ����0��ʾ������
      iArray[9][10] = "NeedPrint";
      iArray[9][11] = "0|^Y|��Ҫ��ӡ^N|����Ҫ��ӡ";
      iArray[9][12] = "9";
      iArray[9][13] = "0";   
      
      iArray[10]=new Array();
      iArray[10][0]="��ˮ��";         			//����
      iArray[10][1]="0px";            		//�п�
      iArray[10][2]=50;            			//�����ֵ
      iArray[10][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������         
      
      QuestGrid = new MulLineEnter( "fm" , "QuestGrid" ); 
      //��Щ���Ա�����loadMulLineǰ                            
      QuestGrid.mulLineCount = 1;
      QuestGrid.displayTitle = 1;
      QuestGrid.canSel = 1;
      QuestGrid.hiddenPlus = 1;
      QuestGrid.hiddenSubtraction = 1;
      QuestGrid.loadMulLine(iArray); 
      
      QuestGrid. selBoxEventFuncName = "queryone";
      
      }
      catch(ex)
      {
        alert(ex);
      }
}

function initHide(tContNo,tFlag)
{
	document.all('ContNoHide').value=tContNo;
	document.all('Flag').value=tFlag;
	document.all('HideOperatePos').value="";
	document.all('HideQuest').value="";
	document.all('HideSerialNo').value = "";
	//alert("pol:"+tContNo);
}

function initCodeData(tContNo,tFlag)
{

}

</script>


