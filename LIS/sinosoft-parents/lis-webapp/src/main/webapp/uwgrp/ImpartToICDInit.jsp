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
    fm.all('InsureNo').value = '';
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
	initHide(tContNo,tPrtNo);
	initInsureNo(tPrtNo);
	initImpartDetailGrid();
	initDisDesbGrid();
	//alert(1);
	//easyQueryClickSingle();
	//easyQueryClick();
 }
  catch(re) {
    alert("UWManuHealthInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
  }
}

// ��֪��ϸ��Ϣ�б�ĳ�ʼ��
function initImpartDetailGrid() {                               
    var iArray = new Array();
      
    try {
      iArray[0]=new Array();
      iArray[0][0]="���";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
      iArray[0][1]="30px";            		//�п�
      iArray[0][2]=10;            			//�����ֵ
      iArray[0][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[1]=new Array();
      iArray[1][0]="��֪���";         		//����
      iArray[1][1]="60px";            		//�п�
      iArray[1][2]=60;            			//�����ֵ
      iArray[1][3]=2;              			//�Ƿ���������,1��ʾ����0��ʾ������
      iArray[1][4]="ImpartVer";
      iArray[1][9]="��֪���|len<=5";
      iArray[1][18]=300;

      iArray[2]=new Array();
      iArray[2][0]="��֪����";         		//����
      iArray[2][1]="60px";            		//�п�
      iArray[2][2]=60;            			//�����ֵ
      iArray[2][3]=2;              			//�Ƿ���������,1��ʾ����0��ʾ������
      iArray[2][4]="ImpartCode";
      iArray[2][5]="2|3";
      iArray[2][6]="0|1";
      //iArray[2][7]="getImpartCode";
      iArray[2][9]="��֪����|len<=4";
      iArray[2][15]="ImpartVer";
      iArray[2][17]="1";
      iArray[2][18]=700;

      iArray[3]=new Array();
      iArray[3][0]="��֪����";         		//����
      iArray[3][1]="250px";            		//�п�
      iArray[3][2]=200;            			//�����ֵ
      iArray[3][3]=1;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[4]=new Array();
      iArray[4][0]="��������";         		//����
      iArray[4][1]="250px";            		//�п�
      iArray[4][2]=200;            			//�����ֵ
      iArray[4][3]=1;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[5]=new Array();
      iArray[5][0]="��ʼʱ��";         		//����
      iArray[5][1]="150px";            		//�п�
      iArray[5][2]=150;            			//�����ֵ
      iArray[5][3]=1;              			//�Ƿ���������,1��ʾ����0��ʾ������
      iArray[5][9]="��ʼʱ��|date";

      iArray[6]=new Array();
      iArray[6][0]="����ʱ��";         		//����
      iArray[6][1]="90px";            		//�п�
      iArray[6][2]=90;            			//�����ֵ
      iArray[6][3]=1;              			//�Ƿ���������,1��ʾ����0��ʾ������
      iArray[6][9]="����ʱ��|date";
      
      iArray[7]=new Array();
      iArray[7][0]="֤��������ҽ��";         		//����
      iArray[7][1]="90px";            		//�п�
      iArray[7][2]=90;            			//�����ֵ
      iArray[7][3]=1;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[8]=new Array();
      iArray[8][0]="Ŀǰ�������";         		//����
      iArray[8][1]="90px";            		//�п�
      iArray[8][2]=90;            			//�����ֵ
      iArray[8][3]=1;              			//�Ƿ���������,1��ʾ����0��ʾ������


      iArray[9]=new Array();
      iArray[9][0]="�ܷ�֤��";         		//����
      iArray[9][1]="90px";            		//�п�
      iArray[9][2]=90;            			//�����ֵ
      iArray[9][3]=2;              			//�Ƿ���������,1��ʾ����0��ʾ������
      iArray[9][4]="yesno";
    
 
      ImpartDetailGrid = new MulLineEnter( "fm" , "ImpartDetailGrid" ); 
      //��Щ���Ա�����loadMulLineǰ
      ImpartDetailGrid.mulLineCount = 0;   
      ImpartDetailGrid.displayTitle = 1;
      ImpartDetailGrid.loadMulLine(iArray);  
      
    }
    catch(ex) {
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
	//alert("pol:"+tContNo);
}

</script>


