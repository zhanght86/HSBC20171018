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
  	document.all('VersionNo').value=VersionNo;
  	//alert(document.all('VersionNo').value);
  	document.all('FinItemID').value=FinItemID;  	
  	//alert(document.all('FinItemID').value);  	
  	
  	//���汾״̬��Ϊ02-ά����ʱ����ɾ�İ�ťΪ��ɫ		
  	//alert(VersionState);
		if (VersionState == "01" || VersionState == "03" || VersionState == '' || VersionState == null )
		{
			document.all('addbutton').disabled = true;				
			document.all('updatebutton').disabled = true;		
			document.all('deletebutton').disabled = true;	
		}                    
  	//document.all('VersionNo').readOnly=false;       
  }
  catch(ex)
  {
    alert("��DetailFinItemDefInputInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
  }      
}



function initSelBox()
{  
  try                 
  {

  }
  catch(ex)
  {
    alert("��DetailFinItemDefInputInit.jsp-->InitSelBox�����з����쳣:��ʼ���������!");
  }
}                                   


function initForm()
{
  try
  {    
    initInpBox();    
    initSelBox();
		initDetailDefGrid();    
  	initDetailDefQuery();    
  }
  
  catch(re)
  {
    alert("��DetailFinItemDefInputInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
  }
}


 function initDetailDefGrid()
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
      
      iArray[4]=new Array();
      iArray[4][0]="�㼶�������";         			//����
      iArray[4][1]="90px";            		//�п�
      iArray[4][2]=60;            			//�����ֵ
      iArray[4][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[5]=new Array();
      iArray[5][0]="�״������жϱ�־";         		//����
      iArray[5][1]="80px";            		//�п�
      iArray[5][2]=100;            			//�����ֵ
      iArray[5][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������,2��ʾ��������
  
      iArray[6]=new Array();
      iArray[6][0]="����";         		//����
      iArray[6][1]="120px";            		//�п�
      iArray[6][2]=100;            			//�����ֵ
      iArray[6][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������,2��ʾ��������
      

      DetailDefGrid = new MulLineEnter( "document" , "DetailDefGrid" ); 
      DetailDefGrid.mulLineCount = 5;   
      DetailDefGrid.displayTitle = 1;
      DetailDefGrid.canSel=1;
      //ѡ��һ���Զ��������ֵ
      DetailDefGrid.selBoxEventFuncName = "ShowDetailDef";			      
      DetailDefGrid.locked = 1;	
			DetailDefGrid.hiddenPlus = 1;
			DetailDefGrid.hiddenSubtraction = 1;

      DetailDefGrid.loadMulLine(iArray);  
      DetailDefGrid.detailInfo="������ʾ��ϸ��Ϣ";
      //DetailDefGrid.detailClick=reportDetailClick;     
      }
      
      catch(ex)
      {
        alert(ex);
      }
}

</script>
