<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//�������ƣ�RReportQueryInit.jsp
//�����ܣ�������鱨���ѯ
//�������ڣ�2002-06-19 11:10:36
//������  ��WHN
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<!--�û�У����-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.tbgrp.*"%>
  <%@page import="com.sinosoft.lis.cbcheckgrp.*"%>

<script language="JavaScript">


// �����ĳ�ʼ��������¼���֣�
function initInpBox(tContNo,tFlag)
{ 
try
  {                                   
    
  }
  catch(ex)
  {
    alert("��RReportQueryInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
  }   
}

function initForm(tContNo,tPrtSeq,tMissionID,tSubMissionID)
{
  try
  {	
	initQuestGrid();		
	
	initRReportGrid();

	initRReportResultGrid();
	
	initHide(tContNo,tPrtSeq,tMissionID,tSubMissionID);
	
	
	
	if(Flag == "1")
	{
	divOperation.style.display = "";
	divRReportButton.style.display = "none";

	divMainHealth.style.display = "";
	
		easyQueryClick(tContNo);
		
	}
	
if(Flag == "2")
{	

easyQueryClickItem();
}

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
      iArray[2][0]="���������";         			//����
      iArray[2][1]="100px";            		//�п�
      iArray[2][2]=100;            			//�����ֵ
      iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[3]=new Array();
      iArray[3][0]="¼����";         			//����
      iArray[3][1]="100px";            		//�п�
      iArray[3][2]=60;            			//�����ֵ
      iArray[3][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
                           
      iArray[4]=new Array();
      iArray[4][0]="¼������";         			//����
      iArray[4][1]="100px";            		//�п�
      iArray[4][2]=60;            			//�����ֵ
      iArray[4][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[5]=new Array();
      iArray[5][0]="�ظ���";         			//����
      iArray[5][1]="100px";            		//�п�
      iArray[5][2]=60;            			//�����ֵ
      iArray[5][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[6]=new Array();
      iArray[6][0]="�ظ�����";         			//����
      iArray[6][1]="100px";            		//�п�
      iArray[6][2]=60;            			//�����ֵ
      iArray[6][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[7]=new Array();
      iArray[7][0]="�Ƿ�ظ�";         			//����
      iArray[7][1]="100px";            		//�п�
      iArray[7][2]=60;            			//�����ֵ
      iArray[7][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������      
      
      iArray[8]=new Array();
      iArray[8][0]="��ˮ��";         			//����
      iArray[8][1]="0px";            		//�п�
      iArray[8][2]=60;            			//�����ֵ
      iArray[8][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������   
      
      iArray[9]=new Array();
      iArray[9][0]="����ԭ��";         			//����
      iArray[9][1]="60px";            		//�п�
      iArray[9][2]=60;            			//�����ֵ
      iArray[9][3]=2;              			//�Ƿ���������,1��ʾ����0��ʾ������  
      iArray[9][4] = "rreportreason";             			//�Ƿ���������,1��ʾ����0��ʾ������
      iArray[9][5]="8|9";     //���ô����Ӧ�ڼ��У�'|'Ϊ�ָ��
      iArray[9][6]="0|1";    //��������з������ô����еڼ�λֵ
      iArray[9][18] = 500;         
      
      QuestGrid = new MulLineEnter( "fm" , "QuestGrid" ); 
      //��Щ���Ա�����loadMulLineǰ                            
      QuestGrid.mulLineCount = 1;
      QuestGrid.displayTitle = 1;
      QuestGrid.canChk = 0;
      QuestGrid.hiddenPlus = 1;
      QuestGrid.hiddenSubtraction = 1;
      QuestGrid.canSel =1
      QuestGrid.loadMulLine(iArray);
      
      
      QuestGrid. selBoxEventFuncName = "easyQueryChoClick";
      
      }
      catch(ex)
      {
        alert(ex);
      }
}

// ������Ϣ�б�ĳ�ʼ��
function initRReportGrid()
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
      iArray[2][1]="120px";            		//�п�
      iArray[2][2]=100;            			//�����ֵ
      iArray[2][3]=1;              			//�Ƿ���������,1��ʾ����0��ʾ������

      
      RReportGrid = new MulLineEnter( "fm" , "RReportGrid" ); 
      //��Щ���Ա�����loadMulLineǰ                            
      RReportGrid.mulLineCount = 0;
      RReportGrid.hiddenPlus = 1;
      RReportGrid.hiddenSubtraction = 1;
      RReportGrid.displayTitle = 1;
      RReportGrid.canChk = 0;
      RReportGrid.loadMulLine(iArray);  
      
      //��Щ����������loadMulLine����
      //HealthGrid.setRowColData(1,1,"asdf");
      }
      catch(ex)
      {
        alert(ex);
      }
}

// ������Ϣ�б�ĳ�ʼ��
function initRReportResultGrid()
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
      iArray[1][0]="�������";         		//����
      iArray[1][1]="260px";            		//�п�
      iArray[1][2]=60;            			//�����ֵ
      iArray[1][3]=1;              			//�Ƿ���������,1��ʾ����0��ʾ������
      iArray[1][4]="ICDName";
      iArray[1][9]="��������|len<=120";
      iArray[1][18]=300;

      iArray[2]=new Array();
      iArray[2][0]="ICD����";         		//����
      iArray[2][1]="100px";            		//�п�
      iArray[2][2]=60;            			//�����ֵ
      iArray[2][3]=2;              			//�Ƿ���������,1��ʾ����0��ʾ������
      iArray[2][4]="ICDCode";
      iArray[2][9]="ICD����|len<=20";
      iArray[2][15]="ICDName";
      iArray[2][17]="2";
      iArray[2][18]=700;
     

      RReportResultGrid = new MulLineEnter( "fm" , "RReportResultGrid" ); 
      //��Щ���Ա�����loadMulLineǰ                            
      RReportResultGrid.mulLineCount = 0;
      RReportResultGrid.displayTitle = 1;
      RReportResultGrid.canChk = 0;
      RReportResultGrid.loadMulLine(iArray);  
      
      //��Щ����������loadMulLine����
      //DisDesbGrid.setRowColData(1,1,"asdf");
      }
      catch(ex)
      {
        alert(ex);
      }
}

function initHide(tContNo,tPrtSeq,tMissionID,tSubMissionID)
{
	fm.all('ContNo').value=tContNo;		
	fm.all('PrtSeq').value =tPrtSeq;
	fm.all('MissionID').value = tMissionID;
	fm.all('SubMissionID').value = tSubMissionID;
	
}



</script>


