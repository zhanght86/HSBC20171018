<%@page contentType="text/html;charset=GBK"%>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<% 
//�������ƣ�FinInterfaceCheckInit.jsp
%>
<!--�û�У����-->
<%
     //���ҳ��ؼ��ĳ�ʼ����
%>                            

<script language="JavaScript">

function initInpBox()
{
  try
  {
	//��ѯ�����ÿ�
	document.all('checkType').value = '';
	document.all('checkTypeName').value = '';
	document.all('StartDay').value = '';
	document.all('EndDay').value = '';
  }
  catch(ex)
  {
    alert("TestInterfaceNotRunInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
  }      
}
                            

function initForm()
{
  try
  {
    initInpBox();
    initCodeQuery();
    initCheckQueryDataGrid();  //��ʼ����������
  }
  catch(re)
  {
    alert("TestInterfaceNotRunInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
  }
}

function initCodeQuery(){
	
		var strQueryResultL = easyQueryVer3("select distinct codealias,Codename from FICodeTrans where codetype = 'BigCertificateID' order by to_number(substr(codealias,2))",1,1,1);
		var	tArrayL = decodeEasyQueryResult(strQueryResultL);
		var tDS = "0|^";
		for(i=0;i<tArrayL.length;i++){
			tDS =tDS + tArrayL[i][0] + "|" + tArrayL[i][1] + "|M";
			if((i+1)!=tArrayL.length){
				tDS = tDS + "^";
			}
		}
		document.all("SClassType").CodeData = tDS;
}

function initCheckQueryDataGrid()
{                                  
	var iArray = new Array();      
	try
	{
      iArray[0]=new Array();
      iArray[0][0]="���";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
      iArray[0][1]="38px";            		//�п�
      iArray[0][2]=10;            			//�����ֵ
      iArray[0][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[1]=new Array();
      iArray[1][0]="���κ�";         		//����
      iArray[1][1]="140px";            		//�п�
      iArray[1][2]=170;            			//�����ֵ
      iArray[1][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[2]=new Array();
      iArray[2][0]="����";         		//����
      iArray[2][1]="65px";            		//�п�
      iArray[2][2]=100;            			//�����ֵ
      iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[3]=new Array();
      iArray[3][0]="�����־";         		//����
      iArray[3][1]="50px";            		//�п�
      iArray[3][2]=100;            			//�����ֵ
      iArray[3][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������ 
      
      iArray[4]=new Array();
      iArray[4][0]="��Ŀ��Ϣ";         		//����
      iArray[4][1]="120px";            		//�п�
      iArray[4][2]=100;            			//�����ֵ
      iArray[4][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������ 
      
      iArray[5]=new Array();
      iArray[5][0]="ҵ����Ϣ";         		//����
      iArray[5][1]="120px";            		//�п�
      iArray[5][2]=100;            			//�����ֵ
      iArray[5][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������ 

      iArray[6]=new Array();
      iArray[6][0]="����";         		//����
      iArray[6][1]="60px";            		//�п�
      iArray[6][2]=100;            			//�����ֵ
      iArray[6][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������ 
      
      iArray[7]=new Array();
      iArray[7][0]="����";         		//����
      iArray[7][1]="60px";            		//�п�
      iArray[7][2]=100;            			//�����ֵ
      iArray[7][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������ 
      
      iArray[8]=new Array();
      iArray[8][0]="ҵ������";         		//����
      iArray[8][1]="80px";            		//�п�
      iArray[8][2]=100;            			//�����ֵ
      iArray[8][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������ 
      
      iArray[9]=new Array();
      iArray[9][0]="��������";         		//����
      iArray[9][1]="120px";            		//�п�
      iArray[9][2]=100;            			//�����ֵ
      iArray[9][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������ 
      
      iArray[10]=new Array();
      iArray[10][0]="��ϸ";         		//����
      iArray[10][1]="60px";            		//�п�
      iArray[10][2]=100;            			//�����ֵ
      iArray[10][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������   
      
      iArray[11]=new Array();
      iArray[11][0]="Ԥ����";         		//����
      iArray[11][1]="60px";            		//�п�
      iArray[11][2]=100;            			//�����ֵ
      iArray[11][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������        
      
      
      iArray[12]=new Array();
      iArray[12][0]="�ɱ�����";         		//����
      iArray[12][1]="60px";            		//�п�
      iArray[12][2]=100;            			//�����ֵ
      iArray[12][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������         
      
      iArray[13]=new Array();
      iArray[13][0]="���";         		//����
      iArray[13][1]="80px";            		//�п�
      iArray[13][2]=100;            			//�����ֵ
      iArray[13][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������   
      
      iArray[14]=new Array();
      iArray[14][0]="ƾ֤����";         		//����
      iArray[14][1]="60px";            		//�п�
      iArray[14][2]=100;            			//�����ֵ
      iArray[14][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[15]=new Array();
      iArray[15][0]="��������";         		//����
      iArray[15][1]="60px";            		//�п�
      iArray[15][2]=100;            			//�����ֵ
      iArray[15][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[16]=new Array();
      iArray[16][0]="ҵ�����";         		//����
      iArray[16][1]="120px";            		//�п�
      iArray[16][2]=100;            			//�����ֵ
      iArray[16][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
          
      CheckQueryDataGrid = new MulLineEnter( "document" , "CheckQueryDataGrid" ); 
      //��Щ���Ա�����loadMulLineǰ
      CheckQueryDataGrid.mulLineCount = 10;   
      CheckQueryDataGrid.displayTitle = 1;
      CheckQueryDataGrid.locked = 1;
      CheckQueryDataGrid.canSel = 1;
      CheckQueryDataGrid.canChk = 1;
      //FinDayTestGrid.hiddenPlus = 1;
      //FinDayTestGrid.hiddenSubtraction = 1;        
      CheckQueryDataGrid.loadMulLine(iArray);  
	}
	catch(ex)
	{
		alert(ex);
	}
}
</script>
