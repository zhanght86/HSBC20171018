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
function initInpBox()
{ 
try
  {                                   
    fm.all('PEAddress').value = '';
    fm.all('PEDoctor').value = '';
    fm.all('PEDate').value = '';
    fm.all('REPEDate').value = '';
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
	initInpBox();		
	initContGrid();
	initHealthGrid();
	initDisDesbGrid();
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
function initHealthGrid()
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
      iArray[1][0]="�����Ŀ���";    	//����
      iArray[1][1]="0px";            		//�п�
      iArray[1][2]=100;            			//�����ֵ
      iArray[1][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[2]=new Array();
      iArray[2][0]="�����Ŀ����";         			//����
      iArray[2][1]="150px";            		//�п�
      iArray[2][2]=100;            			//�����ֵ
      iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[3]=new Array();
      iArray[3][0]="�����Ŀ����";         			//����
      iArray[3][1]="550px";            		//�п�
      iArray[3][2]=100;            			//�����ֵ
      iArray[3][3]=1;              			//�Ƿ���������,1��ʾ����0��ʾ������

      
      HealthGrid = new MulLineEnter( "fm" , "HealthGrid" ); 
      //��Щ���Ա�����loadMulLineǰ                            
      HealthGrid.mulLineCount = 3;
      HealthGrid.hiddenPlus = 1;
      HealthGrid.hiddenSubtraction = 1;
      HealthGrid.displayTitle = 1;
      HealthGrid.canChk = 0;
      HealthGrid.loadMulLine(iArray);  
      
      //��Щ����������loadMulLine����
      //HealthGrid.setRowColData(1,1,"asdf");
      }
      catch(ex)
      {
        alert(ex);
      }
}

// ������Ϣ�б�ĳ�ʼ��
function initDisDesbGrid()
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
      iArray[1][0]="����֢״";    	//����
      iArray[1][1]="260px";            		//�п�
      iArray[1][2]=100;            			//�����ֵ
      iArray[1][3]=1;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[2]=new Array();
      iArray[2][0]="��������";         		//����
      iArray[2][1]="260px";            		//�п�
      iArray[2][2]=60;            			//�����ֵ
      iArray[2][3]=1;              			//�Ƿ���������,1��ʾ����0��ʾ������
      iArray[2][4]="ICDName";
      iArray[2][9]="��������|len<=120";
      iArray[2][18]=300;

      iArray[3]=new Array();
      iArray[3][0]="ICD����";         		//����
      iArray[3][1]="100px";            		//�п�
      iArray[3][2]=60;            			//�����ֵ
      iArray[3][3]=2;              			//�Ƿ���������,1��ʾ����0��ʾ������
      iArray[3][4]="ICDCode";
      iArray[3][9]="ICD����|len<=20";
      iArray[3][15]="ICDName";
      iArray[3][17]="2";
      iArray[3][18]=700;
      iArray[3][3]=1;  
     

      DisDesbGrid = new MulLineEnter( "fm" , "DisDesbGrid" ); 
      //��Щ���Ա�����loadMulLineǰ                            
      DisDesbGrid.mulLineCount = 0;
      DisDesbGrid.displayTitle = 1;
      DisDesbGrid.canChk = 0;
      DisDesbGrid.hiddenPlus = 1;
      DisDesbGrid.hiddenSubtraction = 1;
      DisDesbGrid.loadMulLine(iArray);  
      
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


