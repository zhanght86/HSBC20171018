<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//�������ƣ�UnderwriteInit.jsp
//�����ܣ������˹��˱�
//�������ڣ�2002-09-24 11:10:36
//������  ��WHN
//���¼�¼��  ������    ��������     ����ԭ��/����
%> 
<%@page import="com.sinosoft.lis.pubfun.*"%>
<!--�û�У����-->
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<%
	//���ҳ��ؼ��ĳ�ʼ����
	GlobalInput globalInput = (GlobalInput)session.getValue("GI");
	
	if(globalInput == null) {
		out.println("session has expired");
		return;
	}
	
	String strOperator = globalInput.Operator;
%>                            

<script language="JavaScript">

// �����ĳ�ʼ��������¼���֣�
function initInpBox()
{ 
   
//tSql = " select HighAmntFlag('<%=request.getParameter("ContNo")%>') from dual";
tSql = " select '' from dual"; //��ʱ����
		  var HighAmntFlag=easyExecSql(tSql,1,0);
      
  try
  {                                  
	// ������ѯ����
    document.all('QPrtNo').value = '';
    document.all('QManageCom').value = '';
    document.all('QAppntName').value = '';
    document.all('QOperator').value = operator;
    document.all('QComCode').value = comcode;
    document.all('State').value = '1';
    document.all('QProposalNo').value = '';
    //document.all('QRiskVersion').value = '';
    codeSql="code";
    document.all('HighAmntFlag').value = HighAmntFlag;
  }
  catch(ex)
  {
    alert("��PManuUWInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
  }      
}

// ����������Ϣ��ʾ��ĳ�ʼ��������¼���֣�
function initPolBox()
{ 

  try
  {                                   
	// ������ѯ����
  document.all('ManageCom').value = '';
   //document.all('RiskCode').value = '';
   //document.all('RiskVersion').value = '';
   document.all('AppntNo').value = '';
   document.all('AppntName').value = '';
   //document.all('InsuredNo').value = '';
   //document.all('InsuredName').value = '';
   //document.all('InsuredSex').value = '';
   //document.all('Mult').value = '';
   //document.all('Prem').value = '';
   //document.all('Amnt').value = '';
   document.all('AppGrade').value = '';
   document.all('UWGrade').value = '';
   document.all('Operator').value = '<%= strOperator %>';
   document.all('UWPopedomCode').value = '';
   //document.all('UWDelay').value = '';
   document.all('UWIdea').value = '';
   document.all('uwState').value = '';
    codeSql="code";
 
  }
  catch(ex)
  {
    alert("����PManuUWInit.jsp-->InitPolBox�����з����쳣:��ʼ���������!");
  }      
}

function initForm()
{
  try
  {

		initUW();
	
		
  }
  catch(re)
  {
    //alert("��PManuUWInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
  
  }
}

// ������Ϣ�б�ĳ�ʼ��
function initPolGrid()
  {                               
    var iArray = new Array();
      
      try
      {
      iArray[0]=new Array();
      iArray[0][0]="���";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
      iArray[0][1]="30px";            		//�п�
      iArray[0][2]=30;            			//�����ֵ
      iArray[0][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[1]=new Array();
      iArray[1][0]="ӡˢ��";         		//����
      iArray[1][1]="0px";            		//�п�
      iArray[1][2]=100;            			//�����ֵ
      iArray[1][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[2]=new Array();
      iArray[2][0]="Ͷ������";         		//����
      iArray[2][1]="160px";            		//�п�
      iArray[2][2]=100;            			//�����ֵ
      iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[3]=new Array();
      iArray[3][0]="Ͷ����";         		//����
      iArray[3][1]="80px";            		//�п�
      iArray[3][2]=100;            			//�����ֵ
      iArray[3][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[4]=new Array();
      iArray[4][0]="������";         		//����
      iArray[4][1]="80px";            		//�п�
      iArray[4][2]=100;            			//�����ֵ
      iArray[4][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[5]=new Array();
      iArray[5][0]="�������";         		//����
      iArray[5][1]="80px";            		//�п�
      iArray[5][2]=200;            			//�����ֵ
      iArray[5][3]=2;              			//�Ƿ���������,1��ʾ����0��ʾ������
      iArray[5][4]="station";              	        //�Ƿ����ô���:null||""Ϊ������
      iArray[5][5]="3";              	                //���ô����Ӧ�ڼ��У�'|'Ϊ�ָ��
      iArray[5][9]="�������|code:station&NOTNULL";
      iArray[5][18]=250;
      iArray[5][19]= 0 ;

      iArray[6]=new Array();
      iArray[6][0]="�����������";         		//����
      iArray[6][1]="0px";            		//�п�
      iArray[6][2]=200;            			//�����ֵ
      iArray[6][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������ 
      
      iArray[7]=new Array();
      iArray[7][0]="�������������";         		//����
      iArray[7][1]="0px";            		//�п�
      iArray[7][2]=200;            			//�����ֵ
      iArray[7][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������ 
      
      PolGrid = new MulLineEnter( "fm" , "PolGrid" ); 
      //��Щ���Ա�����loadMulLineǰ
      PolGrid.mulLineCount = 3;   
      PolGrid.displayTitle = 1;
      PolGrid.locked = 1;
      PolGrid.canSel = 1;
      PolGrid.hiddenPlus = 1;
      PolGrid.hiddenSubtraction = 1;
      PolGrid.loadMulLine(iArray);     
      
      PolGrid. selBoxEventFuncName = "easyQueryAddClick";
      
      //��Щ����������loadMulLine����
      //PolGrid.setRowColData(1,1,"asdf");
      }
      catch(ex)
      {
        alert(ex);
        
      }
}

function initPolAddGrid()
  {                               
    var iArray = new Array();
      
      try
      {
      iArray[0]=new Array();
      iArray[0][0]="���";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
      iArray[0][1]="30px";            		//�п�
      iArray[0][2]=30;            			//�����ֵ
      iArray[0][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[1]=new Array();
      iArray[1][0]="�ͻ�����";         		//����
      iArray[1][1]="60px";            		//�п�
      iArray[1][2]=100;            			//�����ֵ
      iArray[1][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������


      iArray[2]=new Array();
      iArray[2][0]="����";         		//����
      iArray[2][1]="60px";            		//�п�
      iArray[2][2]=100;            			//�����ֵ
      iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
   
     	iArray[3]=new Array();
      iArray[3][0]="�Ա�";         		//����
      iArray[3][1]="40px";            		//�п�
      iArray[3][2]=100;            			//�����ֵ
      iArray[3][3]=2;              			//�Ƿ���������,1��ʾ����0��ʾ������ 
	  	iArray[3][4]="sex";              	        //�Ƿ����ô���:null||""Ϊ������
    	iArray[3][5]="3";              	                //���ô����Ӧ�ڼ��У�'|'Ϊ�ָ��

  		iArray[4]=new Array();
      iArray[4][0]="����";         		//����
      iArray[4][1]="40px";            		//�п�
      iArray[4][2]=100;            			//�����ֵ
      iArray[4][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[5]=new Array();
      iArray[5][0]="��Ͷ���˹�ϵ";         		//����
      iArray[5][1]="60px";            		//�п�
      iArray[5][2]=200;            			//�����ֵ
      iArray[5][3]=2;              			//�Ƿ���������,1��ʾ����0��ʾ������ 
	  	iArray[5][4]="relation";              	        //�Ƿ����ô���:null||""Ϊ������
    	iArray[5][5]="3";              	                //���ô����Ӧ�ڼ��У�'|'Ϊ�ָ��

     	iArray[6]=new Array();
      iArray[6][0]="���������˹�ϵ";         		//����
      iArray[6][1]="60px";            		//�п�
      iArray[6][2]=200;            			//�����ֵ
      iArray[6][3]=2;              			//�Ƿ���������,1��ʾ����0��ʾ������ 
	  	iArray[6][4]="relation";              	        //�Ƿ����ô���:null||""Ϊ������
    	iArray[6][5]="3";              	                //���ô����Ӧ�ڼ��У�'|'Ϊ�ָ��

    	iArray[7]=new Array();
      iArray[7][0]="����";         		//����
      iArray[7][1]="40px";            		//�п�
      iArray[7][2]=200;            			//�����ֵ
      iArray[7][3]=2;              			//�Ƿ���������,1��ʾ����0��ʾ������ 
	  	iArray[7][4]="nativeplace";              	        //�Ƿ����ô���:null||""Ϊ������
    	iArray[7][5]="3";              	                //���ô����Ӧ�ڼ��У�'|'Ϊ�ָ��



      iArray[8]=new Array();
      iArray[8][0]="֤������";         		//����
      iArray[8][1]="0px";            		//�п�
      iArray[8][2]=200;            			//�����ֵ
      iArray[8][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[9]=new Array();
      iArray[9][0]="֤������";         		//����
      iArray[9][1]="0px";            		//�п�
      iArray[9][2]=200;            			//�����ֵ
      iArray[9][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      
      PolAddGrid = new MulLineEnter( "fm" , "PolAddGrid" ); 
      //��Щ���Ա�����loadMulLineǰ
      PolAddGrid.mulLineCount = 3;   
      PolAddGrid.displayTitle = 1;
      PolAddGrid.locked = 1;
      PolAddGrid.canSel = 1;
      PolAddGrid.hiddenPlus = 1;
      PolAddGrid.hiddenSubtraction = 1;
      PolAddGrid.loadMulLine(iArray);       
   //   PolAddGrid.selBoxEventFuncName = "showInsuredInfo";
      PolAddGrid.selBoxEventFuncName ="getInsuredDetail" ;     //���RadioBoxʱ��Ӧ��JS����
      //��Щ����������loadMulLine����
      //PolAddGrid.setRowColData(1,1,"asdf");
      }
      catch(ex)
      {
        alert(ex);
      }
}

// �Զ��˱���Ϣ��ʾ
function initUWErrGrid()
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
      iArray[1][1]="0px";            		//�п�
      iArray[1][2]=100;            			//�����ֵ
      iArray[1][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[2]=new Array();
      iArray[2][0]="�������˿ͻ���";    	//����
      iArray[2][1]="0px";            		//�п�
      iArray[2][2]=100;            			//�����ֵ
      iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[3]=new Array();
      iArray[3][0]="��������";    	//����
      iArray[3][1]="50px";            		//�п�
      iArray[3][2]=100;            			//�����ֵ
      iArray[3][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[4]=new Array();
      iArray[4][0]="���ֱ���";    	//����
      iArray[4][1]="0px";            		//�п�
      iArray[4][2]=100;            			//�����ֵ
      iArray[4][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
     
      iArray[5]=new Array();
      iArray[5][0]="��������";    	//����
      iArray[5][1]="100px";            		//�п�
      iArray[5][2]=100;            			//�����ֵ
      iArray[5][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[6]=new Array();
      iArray[6][0]="�Ժ�δͨ��ԭ��";    	//����
      iArray[6][1]="300px";            		//�п�
      iArray[6][2]=100;            			//�����ֵ
      iArray[6][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[7]=new Array();
      iArray[7][0]="�˱�����";    	//����
      iArray[7][1]="100px";            		//�п�
      iArray[7][2]=100;            			//�����ֵ
      iArray[7][3]=8;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[8]=new Array();
      iArray[8][0]="����״̬";    	//����
      iArray[8][1]="50px";            		//�п�
      iArray[8][2]=100;            			//�����ֵ
      iArray[8][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[9]=new Array();
      iArray[9][0]="Ͷ������";    	//����
      iArray[9][1]="0px";            		//�п�
      iArray[9][2]=100;            			//�����ֵ
      iArray[9][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[10]=new Array();
      iArray[10][0]="��ˮ��";    	//����
      iArray[10][1]="0px";            		//�п�
      iArray[10][2]=100;            			//�����ֵ
      iArray[10][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
    
//��ͬ��,������,���ֱ���,��������,�˱���Ϣ,�޸�ʱ��,�Ƿ�����,
//Ͷ������,��ˮ��,�˱����к�,��ͬ���ֱ��,Ͷ������

      iArray[11]=new Array();
      iArray[11][0]="�˱�˳���";         			//����
      iArray[11][1]="60px";            		//�п�
      iArray[11][2]=100;            			//�����ֵ
      iArray[11][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[12]=new Array();
      iArray[12][0]="��ͬ���ֱ��";         		//����
      iArray[12][1]="0px";            		//�п�
      iArray[12][2]=100;            			//�����ֵ
      iArray[12][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[13]=new Array();
      iArray[13][0]="Ͷ������";         		//����
      iArray[13][1]="0px";            		//�п�
      iArray[13][2]=100;            			//�����ֵ
      iArray[13][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      UWErrGrid = new MulLineEnter( "fm" , "UWErrGrid" ); 
      //��Щ���Ա�����loadMulLineǰ
      UWErrGrid.mulLineCount = 0;   
      UWErrGrid.displayTitle = 1;
      UWErrGrid.canChk = 1;
      UWErrGrid.daiplayCanChkAll = 1;
      UWErrGrid.locked = 1;
      UWErrGrid.hiddenPlus = 1;
      UWErrGrid.hiddenSubtraction = 1;
      UWErrGrid.loadMulLine(iArray);  
      }
      
      catch(ex)
      {
        alert(ex);
      }
}

function initPolRiskGrid()
  {                               
    var iArray = new Array();
      
      try
      {
      iArray[0]=new Array();
      iArray[0][0]="���";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
      iArray[0][1]="30px";            		//�п�
      iArray[0][2]=30;            			//�����ֵ
      iArray[0][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[1]=new Array();
      iArray[1][0]="���ֱ���";         		//����
      iArray[1][1]="60px";            		//�п�
      iArray[1][2]=100;            			//�����ֵ
      iArray[1][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[2]=new Array();
      iArray[2][0]="��������";         		//����
      iArray[2][1]="100px";            		//�п�
      iArray[2][2]=100;            			//�����ֵ
      iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
   
     	iArray[3]=new Array();
      iArray[3][0]="����";         		//����
      iArray[3][1]="40px";            		//�п�
      iArray[3][2]=100;            			//�����ֵ
      iArray[3][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������ 

  		iArray[4]=new Array();
      iArray[4][0]="����";         		//����
      iArray[4][1]="40px";            		//�п�
      iArray[4][2]=100;            			//�����ֵ
      iArray[4][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[5]=new Array();
      iArray[5][0]="�����ڼ�";         		//����
      iArray[5][1]="60px";            		//�п�
      iArray[5][2]=200;            			//�����ֵ
      iArray[5][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������ 
	
     	iArray[6]=new Array();
      iArray[6][0]="�����ڼ�";         		//����
      iArray[6][1]="60px";            		//�п�
      iArray[6][2]=200;            			//�����ֵ
      iArray[6][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������ 

    	iArray[7]=new Array();
      iArray[7][0]="����Ƶ��";         		//����
      iArray[7][1]="40px";            		//�п�
      iArray[7][2]=200;            			//�����ֵ
      iArray[7][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������ 
	


      iArray[8]=new Array();
      iArray[8][0]="��׼����";         		//����
      iArray[8][1]="40px";            		//�п�
      iArray[8][2]=200;            			//�����ֵ
      iArray[8][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[9]=new Array();
      iArray[9][0]="ְҵ�ӷ�";         		//����
      iArray[9][1]="40px";            		//�п�
      iArray[9][2]=200;            			//�����ֵ
      iArray[9][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[10]=new Array();
      iArray[10][0]="�����ӷ�";         		//����
      iArray[10][1]="40px";            		//�п�
      iArray[10][2]=200;            			//�����ֵ
      iArray[10][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[11]=new Array();
      iArray[11][0]="���ֺ�";         		//����
      iArray[11][1]="0px";            		//�п�
      iArray[11][2]=200;            			//�����ֵ
      iArray[11][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[12]=new Array();
      iArray[12][0]="�����ӷ�";         		//����
      iArray[12][1]="60px";            		//�п�
      iArray[12][2]=200;            			//�����ֵ
      iArray[12][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[13]=new Array();
      iArray[13][0]="���üӷ�";         		//����
      iArray[13][1]="0px";            		//�п�
      iArray[13][2]=200;            			//�����ֵ
      iArray[13][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[14]=new Array();
      iArray[14][0]="����";         		//����
      iArray[14][1]="40px";            		        //�п�
      iArray[14][2]=60;            			//�����ֵ
      iArray[14][3]=2;              			//�Ƿ���������,1��ʾ����0��ʾ������ 
      iArray[14][4]="Currency";              	        //�Ƿ����ô���:null||""Ϊ������
      iArray[14][9]="����|code:Currency";
      
      PolRiskGrid = new MulLineEnter( "fm" , "PolRiskGrid" ); 
      //��Щ���Ա�����loadMulLineǰ
      PolRiskGrid.mulLineCount = 3;   
      PolRiskGrid.displayTitle = 1;
      PolRiskGrid.locked = 1;
      PolRiskGrid.canSel = 0;
      PolRiskGrid.hiddenPlus = 1;
      PolRiskGrid.hiddenSubtraction = 1;
      PolRiskGrid.loadMulLine(iArray);       
   //   PolAddGrid.selBoxEventFuncName = "showInsuredInfo";
   //   PolRiskGrid.selBoxEventFuncName ="getInsuredDetail" ;     //���RadioBoxʱ��Ӧ��JS����
      //��Щ����������loadMulLine����
      //PolAddGrid.setRowColData(1,1,"asdf");
      }
      catch(ex)
      {
        alert(ex);
      }
}

</script>
