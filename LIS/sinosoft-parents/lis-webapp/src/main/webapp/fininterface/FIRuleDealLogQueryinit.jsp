 <% 
//�������ƣ�FIRuleDealLogQueryinit.jsp
%>
<!--�û�У����-->
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%
     //���ҳ��ؼ��ĳ�ʼ����
%>                            

<script language="JavaScript">                           

function initInpBox()
{
  try
  {
  	fm.reset();
		document.all('StartDay').value = '';
		document.all('EndDay').value = '';
		document.all('VersionNo').value = VersionNo;
  }
  catch(ex)
  {
    alert("FIRuleDealLogInputInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
  }      
}
   

function initForm()
{
  try
  {
    initInpBox();
    initFIRuleDealLogGrid();
  }
  catch(re)
  {
    alert("FIRuleDealLogInputInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
  }
}

//
function initFIRuleDealLogGrid()
{                                  
	var iArray = new Array();      
	try
	{
      iArray[0]=new Array();
      iArray[0][0]="���";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
      iArray[0][1]="30px";            		//�п�
      iArray[0][2]=10;            			//�����ֵ
      iArray[0][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[1]=new Array();
      iArray[1][0]="�汾���";         		//����
      iArray[1][1]="0px";            		//�п�
      iArray[1][2]=170;            			//�����ֵ
      iArray[1][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[2]=new Array();
      iArray[2][0]="У�����κ���";         		//����
      iArray[2][1]="0px";            		//�п�
      iArray[2][2]=100;            			//�����ֵ
      iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[3]=new Array();
      iArray[3][0]="����Դ���κ���";         		//����
      iArray[3][1]="100px";            		//�п�
      iArray[3][2]=100;            			//�����ֵ
      iArray[3][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������ 
      
      iArray[4]=new Array();
      iArray[4][0]="У���¼���";         		//����
      iArray[4][1]="40px";            		//�п�
      iArray[4][2]=100;            			//�����ֵ
      iArray[4][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������ 
      
      iArray[5]=new Array();
      iArray[5][0]="У����";         		//����
      iArray[5][1]="40px";            		//�п�
      iArray[5][2]=100;            			//�����ֵ
      iArray[5][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������ 
      
      iArray[6]=new Array();
      iArray[6][0]="У����";         		//����
      iArray[6][1]="60px";            		//�п�
      iArray[6][2]=100;            			//�����ֵ
      iArray[6][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������ 
      
      iArray[7]=new Array();
      iArray[7][0]="У��ƻ�";         		//����
      iArray[7][1]="60px";            		//�п�
      iArray[7][2]=100;            			//�����ֵ
      iArray[7][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������ 
      
      iArray[8]=new Array();
      iArray[8][0]="У������";         		//����
      iArray[8][1]="80px";            		//�п�
      iArray[8][2]=100;            			//�����ֵ
      iArray[8][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������ 
    
      FIRuleDealLogGrid = new MulLineEnter( "document" , "FIRuleDealLogGrid" ); 
      FIRuleDealLogGrid.mulLineCount = 5;   
      FIRuleDealLogGrid.displayTitle = 1;
      FIRuleDealLogGrid.canSel=1;
      FIRuleDealLogGrid.locked = 1;	
			FIRuleDealLogGrid.hiddenPlus = 1;
			FIRuleDealLogGrid.hiddenSubtraction = 1;
      FIRuleDealLogGrid.loadMulLine(iArray);  
      FIRuleDealLogGrid.detailInfo="������ʾ��ϸ��Ϣ";
      

      //��Щ����������loadMulLine����
	}
	catch(ex)
	{
		alert(ex);
	}
}

</script>
