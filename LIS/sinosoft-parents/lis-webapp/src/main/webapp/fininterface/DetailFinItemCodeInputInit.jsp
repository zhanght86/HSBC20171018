<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//�������ڣ�2008-08-11
//������  ��ZhongYan
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<!--�û�У����-->
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
             
<script language="JavaScript">

// �����ĳ�ʼ��������¼���֣�
function initInpBox()
{ 
  try
  {          	
  	fm.all('VersionNo').value=VersionNo;
  	//alert(fm.all('VersionNo').value);
  	fm.all('FinItemID').value=FinItemID;  	
  	//alert(fm.all('FinItemID').value);  	
  	fm.all('JudgementNo').value=JudgementNo;  	
  	//alert(fm.all('JudgementNo').value);  	  	  	
  	
  	//���汾״̬��Ϊ02-ά����ʱ����ɾ�İ�ťΪ��ɫ		
  	//alert(VersionState);
		if (VersionState == "01" || VersionState == "03" || VersionState == '' || VersionState == null )
		{
			fm.all('addbutton').disabled = true;				
			fm.all('updatebutton').disabled = true;		
			fm.all('deletebutton').disabled = true;	
		}                    
  	//fm.all('VersionNo').readOnly=false;       
  }
  catch(ex)
  {
    alert("��DetailFinItemCodeInputInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
  }      
}



function initSelBox()
{  
  try                 
  {

  }
  catch(ex)
  {
    alert("��DetailFinItemCodeInputInit.jsp-->InitSelBox�����з����쳣:��ʼ���������!");
  }
}                                   


function initForm()
{
  try
  {
    initInpBox(); 
    initSelBox();
		initDetailCodeGrid();    
  	initDetailCodeQuery();    
  }
  catch(re)
  {
    alert("��DetailFinItemCodeInputInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
  }
}


 function initDetailCodeGrid()
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
      iArray[1][0]="�汾���";    	//����
      iArray[1][1]="100px";            		//�п�
      iArray[1][2]=100;            			//�����ֵ
      iArray[1][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[2]=new Array();
      iArray[2][0]="��Ŀ���";         			//����
      iArray[2][1]="80px";            		//�п�
      iArray[2][2]=100;            			//�����ֵ
      iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
 
      iArray[3]=new Array();
      iArray[3][0]="�ж��������";         			//����
      iArray[3][1]="80px";            		//�п�
      iArray[3][2]=60;            			//�����ֵ
      iArray[3][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[4]=new Array();						//����ֶ���FIDetailFinItemDef����ϸ��Ŀ�ж�����������е�
      iArray[4][0]="�㼶�������";         			//����
      iArray[4][1]="90px";            		//�п�
      iArray[4][2]=60;            			//�����ֵ
      iArray[4][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������      
      
      iArray[5]=new Array();
      iArray[5][0]="�㼶���������ֵ";         			//����
      iArray[5][1]="90px";            		//�п�
      iArray[5][2]=60;            			//�����ֵ
      iArray[5][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[6]=new Array();
      iArray[6][0]="�㼶��Ŀ����";         		//����
      iArray[6][1]="80px";            		//�п�
      iArray[6][2]=100;            			//�����ֵ
      iArray[6][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������,2��ʾ��������
  
      iArray[7]=new Array();
      iArray[7][0]="�¼���Ŀ�ж�����";         		//����
      iArray[7][1]="80px";            		//�п�
      iArray[7][2]=100;            			//�����ֵ
      iArray[7][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������,2��ʾ��������

      iArray[8]=new Array();
      iArray[8][0]="���������ֵ����";         		//����
      iArray[8][1]="110px";            		//�п�
      iArray[8][2]=100;            			//�����ֵ
      iArray[8][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������,2��ʾ��������            
            
/*      
      iArray[4]=new Array();
      iArray[4][0]="�㼶���������ֵ";         			//����
      iArray[4][1]="90px";            		//�п�
      iArray[4][2]=60;            			//�����ֵ
      iArray[4][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[5]=new Array();
      iArray[5][0]="�㼶��Ŀ����";         		//����
      iArray[5][1]="60px";            		//�п�
      iArray[5][2]=100;            			//�����ֵ
      iArray[5][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������,2��ʾ��������
  
      iArray[6]=new Array();
      iArray[6][0]="�¼���Ŀ�ж�����";         		//����
      iArray[6][1]="60px";            		//�п�
      iArray[6][2]=100;            			//�����ֵ
      iArray[6][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������,2��ʾ��������

      iArray[7]=new Array();
      iArray[7][0]="���������ֵ����";         		//����
      iArray[7][1]="110px";            		//�п�
      iArray[7][2]=100;            			//�����ֵ
      iArray[7][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������,2��ʾ��������
*/     

      DetailCodeGrid = new MulLineEnter( "fm" , "DetailCodeGrid" ); 
      DetailCodeGrid.mulLineCount = 0;   
      DetailCodeGrid.displayTitle = 1;
      DetailCodeGrid.canSel=1;
      //ѡ��һ���Զ��������ֵ
      DetailCodeGrid.selBoxEventFuncName = "ShowDetailCode";			      
      DetailCodeGrid.locked = 1;	
			DetailCodeGrid.hiddenPlus = 1;
			DetailCodeGrid.hiddenSubtraction = 1;

      DetailCodeGrid.loadMulLine(iArray);  
      DetailCodeGrid.detailInfo="������ʾ��ϸ��Ϣ";
      //DetailCodeGrid.detailClick=reportDetailClick;     
      }
      
      catch(ex)
      {
        alert(ex);
      }
}

</script>
