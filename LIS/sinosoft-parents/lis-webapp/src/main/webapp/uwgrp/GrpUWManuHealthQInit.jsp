<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//�������ƣ�UWManuHealthInit.jsp
//�����ܣ���ȫ�˹��˱��������¼��
//�������ڣ�2002-06-19 11:10:36
//������  ��WHN
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<!--�û�У����-->
  <%@page import="java.util.*"%>
  <%@page import="java.lang.Math.*"%>
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.tbgrp.*"%>
  <%@page import="com.sinosoft.lis.cbcheckgrp.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
<%
  String tContNo = "";
  
  String tFlag = "";
  String tUWIdea = "";  
  String tPrtNo = "";
  Date today = new Date();
  today = PubFun.calDate(today,15,"D",null);
  String tday = UWPubFun.getFixedDate(today);
  tContNo = request.getParameter("ContNo");
  
  tPrtNo = request.getParameter("PrtNo");
 %>                            

<script language="JavaScript">


// �����ĳ�ʼ��������¼���֣�
function initInpBox()
{ 
try
  {                                   
	
    fm.all('ContNo').value = '';
   
  }
  catch(ex)
  {
    alert("��UWManuHealthInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
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
    alert("��UWSubInit.jsp-->InitSelBox�����з����쳣:��ʼ���������!");
  }
}                                        

function initForm(tContNo,tPrtNo)
{
  try
  {
	initInpBox();
	
	initUWHealthGrid();
	
	initMainUWHealthGrid();
	
	initDisDesbGrid();
	
	initHide(tContNo,tPrtNo);
	
	initInsureNo(tPrtNo);
	
 }
  catch(re) {
    alert("UWManuHealthInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
  }
}


// ������Ϣ�б�ĳ�ʼ��
function initMainUWHealthGrid()
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
      iArray[1][0]="��ͬ��";    	//����
      iArray[1][1]="120px";            		//�п�
      iArray[1][2]=100;            			//�����ֵ
      iArray[1][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      

      iArray[2]=new Array();
      iArray[2][0]="��ˮ��";         			//����
      iArray[2][1]="120px";            		//�п�
      iArray[2][2]=100;            			//�����ֵ
      iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

              

      iArray[3]=new Array();
      iArray[3][0]="���ͻ���";         			//����
      iArray[3][1]="120px";            			//�п�
      iArray[3][2]=100;            			//�����ֵ
      iArray[3][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[4]=new Array();
      iArray[4][0]="���������";         			//����
      iArray[4][1]="120px";            			//�п�
      iArray[4][2]=100;            			//�����ֵ
      iArray[4][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[5]=new Array();
      iArray[5][0]="���ʱ��";         			//����
      iArray[5][1]="120px";            			//�п�
      iArray[5][2]=100;            			//�����ֵ
      iArray[5][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[6]=new Array();
      iArray[6][0]="���ʱ��";         			//����
      iArray[6][1]="120px";            			//�п�
      iArray[6][2]=100;            			//�����ֵ
      iArray[6][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[7]=new Array();
      iArray[7][0]="��ӡ״̬";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
      iArray[7][1]="60px";         			//�п�
      iArray[7][2]=10;          			//�����ֵ
      iArray[7][3]=2; 
      iArray[7][10] = "ifPrint";
      iArray[7][11] = "0|3^0|δ��ӡ|^1|�Ѵ�ӡ|^2|�ѻظ�|";  
      iArray[7][12] = "7";
      iArray[7][13] = "0";
      iArray[7][14] = "N";
     
      
      
      MainHealthGrid = new MulLineEnter( "fm" , "MainHealthGrid" ); 
      //��Щ���Ա�����loadMulLineǰ                            
      MainHealthGrid.mulLineCount = 0;
      MainHealthGrid.displayTitle = 1;
      MainHealthGrid.canChk = 0;
      MainHealthGrid.canSel =1
      MainHealthGrid.loadMulLine(iArray); 
      MainHealthGrid.hiddenPlus = 1;
      MainHealthGrid.hiddenSubtraction = 1;
      MainHealthGrid. selBoxEventFuncName = "easyQueryClick";
      //��Щ����������loadMulLine����
      //HealthGrid.setRowColData(1,1,"asdf");
      }
      catch(ex)
      {
        alert(ex);
      }
}


// ������Ϣ�б�ĳ�ʼ��
function initUWHealthGrid()
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
      iArray[1][1]="120px";            		//�п�
      iArray[1][2]=100;            			//�����ֵ
      iArray[1][3]=2;              			//�Ƿ���������,1��ʾ����0��ʾ������
      iArray[1][4] = "HealthCode";             			//�Ƿ���������,1��ʾ����0��ʾ������
      iArray[1][5]="1|2";     //���ô����Ӧ�ڼ��У�'|'Ϊ�ָ��
      iArray[1][6]="0|1";    //��������з������ô����еڼ�λֵ
      iArray[1][18] = 500;

      iArray[2]=new Array();
      iArray[2][0]="�����Ŀ����";         			//����
      iArray[2][1]="120px";            		//�п�
      iArray[2][2]=100;            			//�����ֵ
      iArray[2][3]=1;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[3]=new Array();
      iArray[3][0]="�Ƿ����";         			//����
      iArray[3][1]="120px";            			//�п�
      iArray[3][2]=100;            			//�����ֵ
      iArray[3][3]=2;              			//�Ƿ���������,1��ʾ����0��ʾ������
      iArray[3][10] = "IfCheck";
      iArray[3][11] = "0|2^Y|���|^N|�����|";
      iArray[3][12] = "3";
      iArray[3][13] = "0";
      iArray[3][14] = "N";

      
      HealthGrid = new MulLineEnter( "fm" , "HealthGrid" ); 
      //��Щ���Ա�����loadMulLineǰ                            
      HealthGrid.mulLineCount = 0;
      HealthGrid.displayTitle = 1;
      HealthGrid.canChk = 0;
      HealthGrid.hiddenPlus = 1;
      HealthGrid.hiddenSubtraction = 1;
      HealthGrid.loadMulLine(iArray);  
      
      //��Щ����������loadMulLine����
      //HealthGrid.setRowColData(1,1,"asdf");
      }
      catch(ex)
      {
        alert(ex);
      }
}


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
     

      DisDesbGrid = new MulLineEnter( "fm" , "DisDesbGrid" ); 
      //��Щ���Ա�����loadMulLineǰ                            
      DisDesbGrid.mulLineCount = 0;
      DisDesbGrid.displayTitle = 1;
      DisDesbGrid.canChk = 0;
      DisDesbGrid.loadMulLine(iArray);  
      
      //��Щ����������loadMulLine����
      //DisDesbGrid.setRowColData(1,1,"asdf");
      }
      catch(ex)
      {
        alert(ex);
      }
}

function initHide(tContNo,tPrtNo)
{
	fm.all('ContNO').value = tContNo;
    
	fm.all('PrtNo').value = tPrtNo ;
	
}


</script>


