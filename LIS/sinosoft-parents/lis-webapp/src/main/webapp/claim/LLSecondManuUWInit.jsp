<%
//�������ƣ�LLSecondManuUWInit.jsp
//�����ܣ������˹��˱�
//�������ڣ�2002-09-24 11:10:36
//������  ��zhangxing
//���¼�¼��  ������    ��������     ����ԭ��/����
%> 
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<!--�û�У����-->
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<script language="JavaScript">

// ���մ�������
function initParm()
{
	 document.all('Operator').value =nullToEmpty("<%=tGI.Operator%>"); //��¼����Ա
     document.all('ComCode').value =nullToEmpty("<%=tGI.ComCode%>"); //��¼��½����
     document.all('ManageCom').value =nullToEmpty("<%=tGI.ManageCom%>"); //��¼�������
    
   	 document.all('tCaseNo').value =nullToEmpty("<%=tCaseNo%>"); //�ⰸ��		
     document.all('tBatNo').value =nullToEmpty("<%=tBatNo%>"); //���κ�
     document.all('tInsuredNo').value =nullToEmpty("<%=tInsuredNo%>"); //�����˿ͻ��� 
     document.all('tClaimRelFlag').value =nullToEmpty("<%=tClaimRelFlag%>"); //�ⰸ��ر�־ 	
    
	 document.all('tMissionid').value =nullToEmpty("<%=tMissionid%>");   //����ID 
	 document.all('tSubmissionid').value =nullToEmpty("<%=tSubmissionid%>"); //������ID 
	 document.all('tActivityid').value =nullToEmpty("<%=tActivityid%>"); //�ڵ�� 	

}
//��null���ַ���תΪ��
function nullToEmpty(string)
{
	if ((string == "null") || (string == "undefined"))
	{
		string = "";
	}
	return string;
}


function initForm()
{
  try
  {
	 	 initLLCUWBatchGrid();
		 initLLCUWSubGrid(); 	 //���˺�ͬ����˱��˱��켣��
	     initParm();
	     initLLCUWBatchGridQuery();
	     initInsuredGrid();
	     if(LLCUWBatchGrid.canSel==1&&LLCUWBatchGrid.mulLineCount>1)
	     {
	     			//alert('@@');
	     			document.all('LLCUWBatchGridSel')[0].checked=true;
	     			LLCUWBatchGridClick();
	     			//eval("document.all('MultMainRiskGridSel')[" + MultMainRiskGrid.mulLineCount + "-1].checked=true");
	     			//getInsuredDetail();
	     }
	     if(LLCUWBatchGrid.canSel==1&&LLCUWBatchGrid.mulLineCount==1)
	     {
	     			//alert('@@');
	     			document.all('LLCUWBatchGridSel').checked=true;
	     			LLCUWBatchGridClick();
	     			//eval("document.all('MultMainRiskGridSel')[" + MultMainRiskGrid.mulLineCount + "-1].checked=true");
	     			//getInsuredDetail();
	     }
  }
  catch(re)
  {
    alert("��LLSecondManuUWInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
  }
}

function initLLCUWBatchGrid()
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
		iArray[1][0]="���κ�";         		//����
		iArray[1][1]="100px";            		//�п�
		iArray[1][2]=100;            			//�����ֵ
		iArray[1][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
	
		iArray[2]=new Array();
		iArray[2][0]="������";         		//����
		iArray[2][1]="120px";            		//�п�
		iArray[2][2]=100;            			//�����ֵ
		iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
			
		iArray[3]=new Array();
		iArray[3][0]="������";         		    //����
		iArray[3][1]="120px";            		//�п�
		iArray[3][2]=100;            			//�����ֵ
		iArray[3][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
		
		iArray[4]=new Array();
		iArray[4][0]="Ͷ���˿ͻ���";         		//����
		iArray[4][1]="100px";            		//�п�
		iArray[4][2]=200;            			//�����ֵ
		iArray[4][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
			
		iArray[5]=new Array();
		iArray[5][0]="Ͷ��������";         		//����
		iArray[5][1]="100px";            		//�п�
		iArray[5][2]=200;            			//�����ֵ
		iArray[5][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
		
		iArray[6]=new Array();
		iArray[6][0]="�����˿ͻ���";
		iArray[6][1]="100px";
		iArray[6][2]=60;
		iArray[6][3]=0;
	
		iArray[7]=new Array();
		iArray[7][0]="����������";
		iArray[7][1]="100px";
		iArray[7][2]=120;
		iArray[7][3]=0;

		iArray[8]=new Array();
		iArray[8][0]="��������";
		iArray[8][1]="100px";
		iArray[8][2]=120;
		iArray[8][3]=0;

		iArray[9]=new Array();
		iArray[9][0]="������Ч����";
		iArray[9][1]="100px";
		iArray[9][2]=120;
		iArray[9][3]=0;
		
		iArray[10]=new Array();
		iArray[10][0]="�������";
		iArray[10][1]="80px";
		iArray[10][2]=120;
		iArray[10][3]=0;
		
		iArray[11]=new Array();
		iArray[11][0]="�ⰸ��ر�־";
		iArray[11][1]="80px";
		iArray[11][2]=120;
		iArray[11][3]=0;

		iArray[12]=new Array();
		iArray[12][0]="����״̬";
		iArray[12][1]="80px";
		iArray[12][2]=120;
		iArray[12][3]=0;

		LLCUWBatchGrid = new MulLineEnter( "fm" , "LLCUWBatchGrid" ); 
		//��Щ���Ա�����loadMulLineǰ
		LLCUWBatchGrid.mulLineCount = 1;   
		LLCUWBatchGrid.displayTitle = 1;
		LLCUWBatchGrid.locked = 1;
		LLCUWBatchGrid.canSel = 1;
		LLCUWBatchGrid.hiddenPlus = 1;
		LLCUWBatchGrid.hiddenSubtraction = 1;
		LLCUWBatchGrid.selBoxEventFuncName = "LLCUWBatchGridClick";
		LLCUWBatchGrid.loadMulLine(iArray);       
		
	}
	catch(ex)
	{
		alert(ex);
	}
}

//���˺�ͬ����˱��˱��켣��
function initLLCUWSubGrid()
{                               
	var iArray = new Array();
	try
	{
		iArray[0]=new Array();
		iArray[0][0]="���";         			//���� 
		iArray[0][1]="30px";            		//�п�
		iArray[0][2]=30;            			//�����ֵ
		iArray[0][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������		 
		
		iArray[1]=new Array();
		iArray[1][0]="�ְ���";         		 
		iArray[1][1]="0px";            		 
		iArray[1][2]=100;            			 
		iArray[1][3]=3;              			 
		
		iArray[2]=new Array();
		iArray[2][0]="���κ�";         		 
		iArray[2][1]="120px";            		 
		iArray[2][2]=100;            			 
		iArray[2][3]=0;              			 
		
		iArray[3]=new Array();
		iArray[3][0]="��ͬ����";         		 
		iArray[3][1]="120px";            		 
		iArray[3][2]=100;            			 
		iArray[3][3]=0;              			 
		
		iArray[4]=new Array();
		iArray[4][0]="˳���";         		 
		iArray[4][1]="50px";            		 
		iArray[4][2]=100;            			 
		iArray[4][3]=0;              			       
		
		iArray[5]=new Array();
		iArray[5][0]="�˱���־";         		 
		iArray[5][1]="50px";            		 
		iArray[5][2]=100;            			 
		iArray[5][3]=0;              			 
		
		iArray[6]=new Array();
		iArray[6][0]="�˱�����";         		 
		iArray[6][1]="80px";            		 
		iArray[6][2]=100;            			 
		iArray[6][3]=0;              			 
		
		iArray[7]=new Array();
		iArray[7][0]="�˱����";         		 
		iArray[7][1]="200px";            		 
		iArray[7][2]=100;            			 
		iArray[7][3]=0;              			 
		
		iArray[8]=new Array();
		iArray[8][0]="����Ա";         		 
		iArray[8][1]="80px";            		 
		iArray[8][2]=100;            			 
		iArray[8][3]=0;              			 
		
		iArray[9]=new Array();
		iArray[9][0]="�˱�����";         		 
		iArray[9][1]="80px";            		 
		iArray[9][2]=100;            			 
		iArray[9][3]=0;         
		
		iArray[10]=new Array();
		iArray[10][0]="�˱�ʱ��";         		 
		iArray[10][1]="80px";            		 
		iArray[10][2]=100;            			 
		iArray[10][3]=0;        			 
	
		LLCUWSubGrid = new MulLineEnter( "fm","LLCUWSubGrid" ); 
		//��Щ���Ա�����loadMulLineǰ
		LLCUWSubGrid.mulLineCount = 0;   
		LLCUWSubGrid.displayTitle = 1;
		LLCUWSubGrid.locked = 1;
		LLCUWSubGrid.canSel = 0;
		LLCUWSubGrid.hiddenPlus = 1;
		LLCUWSubGrid.hiddenSubtraction = 1;
		LLCUWSubGrid.loadMulLine(iArray);     
	}
	catch(ex)
	{
		alert(ex);
	}
}

/*=======================================================================
 * �޸�ԭ����ӱ�������Ϣ
 * �޸��ˣ�  �����
 * �޸�ʱ�䣺2005/12/15 16:30
 =======================================================================
 */
function initInsuredGrid()
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
	      iArray[1][1]="140px";            		//�п�
	      iArray[1][2]=100;            			//�����ֵ
	      iArray[1][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
	
	
	      iArray[2]=new Array();
	      iArray[2][0]="����";         		    //����
	      iArray[2][1]="100px";            		//�п�
	      iArray[2][2]=100;            			//�����ֵ
	      iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
	      
	      iArray[3]=new Array();
	      iArray[3][0]="�Ա�";         		    //����
	      iArray[3][1]="100px";            		//�п�
	      iArray[3][2]=100;            			//�����ֵ
	      iArray[3][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

	      iArray[4]=new Array();
	      iArray[4][0]="����";         		    //����
	      iArray[4][1]="100px";            		//�п�
	      iArray[4][2]=100;            			//�����ֵ
	      iArray[4][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

	      iArray[5]=new Array();
	      iArray[5][0]="��Ͷ���˹�ϵ";         		    //����
	      iArray[5][1]="100px";            		//�п�
	      iArray[5][2]=100;            			//�����ֵ
	      iArray[5][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
		  	
	      iArray[6]=new Array();
	      iArray[6][0]="���������˹�ϵ";         		//����
	      iArray[6][1]="60px";            		//�п�
	      iArray[6][2]=200;            			//�����ֵ
	      iArray[6][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
	
	      iArray[7]=new Array();
	      iArray[7][0]="����";         		//����
	      iArray[7][1]="120px";            		//�п�
	      iArray[7][2]=200;            			//�����ֵ
	      iArray[7][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
	
	      
	      InsuredGrid = new MulLineEnter( "fm" , "InsuredGrid" ); 
	      //��Щ���Ա�����loadMulLineǰ
	      InsuredGrid.mulLineCount      = 0;   
	      InsuredGrid.displayTitle      = 1;
	      InsuredGrid.locked            = 1;
	      InsuredGrid.canSel            = 1;
	      InsuredGrid.hiddenPlus        = 1;
	      InsuredGrid.hiddenSubtraction = 1;
	      InsuredGrid.selBoxEventFuncName = "getInsuredDetail"; 
	      InsuredGrid.loadMulLine(iArray);   
		   
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
      iArray[3][0]="����";      	   		//����
      iArray[3][1]="80px";            			//�п�
      iArray[3][2]=20;            			//�����ֵ
      iArray[3][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
   
     	iArray[4]=new Array();
      iArray[4][0]="����";         		//����
      iArray[4][1]="40px";            		//�п�
      iArray[4][2]=100;            			//�����ֵ
      iArray[4][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������ 

  		iArray[5]=new Array();
      iArray[5][0]="����";         		//����
      iArray[5][1]="40px";            		//�п�
      iArray[5][2]=100;            			//�����ֵ
      iArray[5][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[6]=new Array();
      iArray[6][0]="�����ڼ�";         		//����
      iArray[6][1]="60px";            		//�п�
      iArray[6][2]=200;            			//�����ֵ
      iArray[6][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������ 
	
     	iArray[7]=new Array();
      iArray[7][0]="�����ڼ�";         		//����
      iArray[7][1]="60px";            		//�п�
      iArray[7][2]=200;            			//�����ֵ
      iArray[7][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������ 

    	iArray[8]=new Array();
      iArray[8][0]="����Ƶ��";         		//����
      iArray[8][1]="40px";            		//�п�
      iArray[8][2]=200;            			//�����ֵ
      iArray[8][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������ 
	


      iArray[9]=new Array();
      iArray[9][0]="��׼����";         		//����
      iArray[9][1]="40px";            		//�п�
      iArray[9][2]=200;            			//�����ֵ
      iArray[9][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[10]=new Array();
      iArray[10][0]="ְҵ�ӷ�";         		//����
      iArray[10][1]="40px";            		//�п�
      iArray[10][2]=200;            			//�����ֵ
      iArray[10][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[11]=new Array();
      iArray[11][0]="�����ӷ�";         		//����
      iArray[11][1]="40px";            		//�п�
      iArray[11][2]=200;            			//�����ֵ
      iArray[11][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[12]=new Array();
      iArray[12][0]="���ֺ�";         		//����
      iArray[12][1]="0px";            		//�п�
      iArray[12][2]=200;            			//�����ֵ
      iArray[12][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[13]=new Array();
      iArray[13][0]="��ס�ؼӷ�";         		//����
      iArray[13][1]="60px";            		//�п�
      iArray[13][2]=200;            			//�����ֵ
      iArray[13][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[14]=new Array();
      iArray[14][0]="���üӷ�";         		//����
      iArray[14][1]="40px";            		//�п�
      iArray[14][2]=200;            			//�����ֵ
      iArray[14][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[15]=new Array();
	  iArray[15][0]="��������";         	//����
	  iArray[15][1]="80px";            		//�п�
	  iArray[15][2]=80;            			//�����ֵ
	  iArray[15][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

	  iArray[16]=new Array();
	  iArray[16][0]="����ֹ��";         		//����
	  iArray[16][1]="80px";            		//�п�
	  iArray[16][2]=100;            			//�����ֵ
	  iArray[16][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
	  
	  iArray[17]=new Array();
	  iArray[17][0]="���Ѽ��(��)";         		//����
	  iArray[17][1]="80px";            		//�п�
	  iArray[17][2]=100;            			//�����ֵ
	  iArray[17][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
	  
	  iArray[18]=new Array();
	  iArray[18][0]="��������";         		//����
	  iArray[18][1]="80px";            		//�п�
	  iArray[18][2]=100;            			//�����ֵ
	  iArray[18][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
	  
	  iArray[19]=new Array();
	  iArray[19][0]="����״̬";         		//����
	  iArray[19][1]="80px";            		//�п�
	  iArray[19][2]=100;            			//�����ֵ
	  iArray[19][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������
	  
	  iArray[20]=new Array();
	  iArray[20][0]="�˱�����";         		//����
	  iArray[20][1]="80px";            		//�п�
	  iArray[20][2]=100;            			//�����ֵ
	  iArray[20][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
	  
	  iArray[21]=new Array();
	  iArray[21][0]="ԭ�˱�����";         		//����
	  iArray[21][1]="80px";            		//�п�
	  iArray[21][2]=100;            			//�����ֵ
	  iArray[21][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
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
