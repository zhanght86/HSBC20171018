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
  <%@page import="com.sinosoft.lis.tb.*"%>
  <%@page import="com.sinosoft.lis.cbcheck.*"%>

<script language="JavaScript">


// �����ĳ�ʼ��������¼���֣�
function initInpBox(tContNo,tFlag)
{ 
try
  {                                   
    
  }
  catch(ex)
  {
    alert("��GrpRReportResultInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
  }   
}

function initForm(tGrpContNo,tPrtSeq,tMissionID,tSubMissionID)
{
  try
  {	
	//initQuestGrid();		
	initContGrid();
	initRReportGrid();
	initRReportResultGrid();
	QueryCont(tGrpContNo);
	

	
	
	//initHide(tContNo,tPrtSeq,tMissionID,tSubMissionID);
	


  }
  catch(re)
  {
    alert("GrpRReportResultInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
  }
}

// ������Ϣ�б�ĳ�ʼ��
function initContGrid()
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
		iArray[1][0]="������ͬ��";
		iArray[1][1]="120px";
		iArray[1][2]=170;
		iArray[1][3]=0;
		

		iArray[2]=new Array();
		iArray[2][0]="��ӡ��ˮ��";
		iArray[2][1]="120px";
		iArray[2][2]=100;
		iArray[2][3]=0;

		iArray[3]=new Array();
		iArray[3][0]="�����˱���";
		iArray[3][1]="80px";
		iArray[3][2]=200;
		iArray[3][3]=0;

		iArray[4]=new Array();
		iArray[4][0]="¼��ʱ��";         	
		iArray[4][1]="100px";           
		iArray[4][2]=100;            	
		iArray[4][3]=0; 
      
      ContGrid = new MulLineEnter( "fm" , "ContGrid" ); 
      //��Щ���Ա�����loadMulLineǰ                            
      ContGrid.mulLineCount = 4;
      ContGrid.locked = 1;
      ContGrid.displayTitle = 1;
      ContGrid.canChk = 0;
      ContGrid.hiddenPlus = 1;
      ContGrid.hiddenSubtraction = 1;
      ContGrid.canSel =1
      ContGrid.loadMulLine(iArray);
      
      
      ContGrid. selBoxEventFuncName = "ReportInfoClick";
      
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
      
      iArray[3]=new Array();
      iArray[3][0]="��ע";         			//����
      iArray[3][1]="120px";            		//�п�
      iArray[3][2]=100;            			//�����ֵ
      iArray[3][3]=1;              			//�Ƿ���������,1��ʾ����0��ʾ������

      
      RReportGrid = new MulLineEnter( "fm" , "RReportGrid" ); 
      //��Щ���Ա�����loadMulLineǰ                            
      RReportGrid.mulLineCount = 3;
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
	document.all('ContNo').value=tContNo;		
	document.all('PrtSeq').value =tPrtSeq;
	document.all('MissionID').value = tMissionID;
	document.all('SubMissionID').value = tSubMissionID;
	
}



</script>


