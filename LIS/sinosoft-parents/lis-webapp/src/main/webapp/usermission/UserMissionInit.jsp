<%
//�������ƣ�UserMissionInit.jsp
//�����ܣ�
//�������ڣ�2011-05-19
//������  ��sunsheng
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<!--�û�У����-->

<%@include file="../common/jsp/UsrCheck.jsp"%>

<%
     //���ҳ��ؼ��ĳ�ʼ����
%>                            
<script language="JavaScript">                                                                        
function initForm()
{
  try
  {  
    //initPersonalWorkPool();
    //initOneselfGrpGrid();
    //initPublicGrpGrid(); 
   // easyQueryClickOneself();
    //easyQueryClickPublic();
    console.log(_DBT);
  }
  catch(re)
  {
    alert("������! "+re);
  }
}
//function initSelfGrpGrid()
//{                               
//var iArray = new Array();
//  
//  try
//  {
//  iArray[0]=new Array();
//  iArray[0][0]="���";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
//  iArray[0][1]="30px";            		//�п�
//  iArray[0][2]=10;            			//�����ֵ
//  iArray[0][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
//
//  iArray[1]=new Array();
//  iArray[1][0]="��������";         		//����
//  iArray[1][1]="150px";            		//�п�
//  iArray[1][2]=100;            			//�����ֵ
//  iArray[1][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
//
//  iArray[2]=new Array();
//  iArray[2][0]="���˴�����������";         		//����
//  iArray[2][1]="150px";            		//�п�
//  iArray[2][2]=100;            			//�����ֵ
//  iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
//
//  iArray[3]=new Array();
//  iArray[3][0]="����������������";         		//����
//  iArray[3][1]="150px";            		//�п�
//  iArray[3][2]=200;            			//�����ֵ
//  iArray[3][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
//
//  iArray[4]=new Array();
//  iArray[4][0]="URL";         		//����
//  iArray[4][1]="0px";            		//�п�
//  iArray[4][2]=200;            			//�����ֵ
//  iArray[4][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
//  
//  iArray[5]=new Array();
//  iArray[5][0]="nodecode";         		//����
//  iArray[5][1]="0px";            		//�п�
//  iArray[5][2]=20;            			//�����ֵ
//  iArray[5][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
//  
//  iArray[6]=new Array();
//  iArray[6][0]="parentnode";         	//����
//  iArray[6][1]="0px";            		//�п�
//  iArray[6][2]=20;            			//�����ֵ
//  iArray[6][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
//  
//  SelfGrpGrid = new MulLineEnter( "fm" , "SelfGrpGrid" ); 
//  SelfGrpGrid.mulLineCount = 5;   
//  SelfGrpGrid.displayTitle = 1;
//  SelfGrpGrid.locked = 1;
//  SelfGrpGrid.canSel = 1;
//  SelfGrpGrid.hiddenPlus = 1;
//  SelfGrpGrid.hiddenSubtraction = 1;
//  SelfGrpGrid.selBoxEventFuncName = "initMenu";
//  SelfGrpGrid.loadMulLine(iArray);  
//  
//  }
//  catch(ex)
//  {
//    alert(ex);
//  }
//}

//���˹�����
function initOneselfGrpGrid()
{                               
  var iArray = new Array();
    
    try
    {
    iArray[0]=new Array();
    iArray[0][0]="���";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
    iArray[0][1]="30px";            		//�п�
    iArray[0][2]=100;            			//�����ֵ
    iArray[0][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

    iArray[1]=new Array();
    iArray[1][0]="processid";         		//����
    iArray[1][1]="80px";            		//�п�
    iArray[1][2]=100;            			//�����ֵ
    iArray[1][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������
    
    iArray[2]=new Array();
    iArray[2][0]="ҵ������";         		//����
    iArray[2][1]="200px";            		//�п�
    iArray[2][2]=100;            			//�����ֵ
    iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

    iArray[3]=new Array();
    iArray[3][0]="ActivityID";         		//����
    iArray[3][1]="80px";            		//�п�
    iArray[3][2]=100;            			//�����ֵ
    iArray[3][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������
    
    iArray[4]=new Array();
    iArray[4][0]="��������";         		//����
    iArray[4][1]="200px";            		//�п�
    iArray[4][2]=100;            			//�����ֵ
    iArray[4][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������


    iArray[5]=new Array();
    iArray[5][0]="ҵ�����";         		//����
    iArray[5][1]="200px";            		//�п�
    iArray[5][2]=100;            			//�����ֵ
    iArray[5][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
    
    iArray[6]=new Array();                                                 
    iArray[6][0]="���ȼ���";         		//����                             
    iArray[6][1]="150px";            		//�п�                               
    iArray[6][2]=100;            			//�����ֵ                           
    iArray[6][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
    
    iArray[7]=new Array();
    iArray[7][0]="���ȼ�������";         		//����
    iArray[7][1]="200px";            		//�п�
    iArray[7][2]=100;            			//�����ֵ
    iArray[7][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
    
    iArray[8]=new Array();
    iArray[8][0]="��ɫID";         		//����
    iArray[8][1]="80px";            		//�п�
    iArray[8][2]=200;            			//�����ֵ
    iArray[8][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������
    
    iArray[9]=new Array();
    iArray[9][0]="MissionID";         		//����
    iArray[9][1]="80px";            		//�п�
    iArray[9][2]=100;            			//�����ֵ
    iArray[9][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������
    
    iArray[10]=new Array();
    iArray[10][0]="MissionID";         		//����
    iArray[10][1]="80px";            		//�п�
    iArray[10][2]=100;            			//�����ֵ
    iArray[10][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������
                                                                           
    OneselfGrpGrid = new MulLineEnter( "fm" , "OneselfGrpGrid" ); 
    OneselfGrpGrid.mulLineCount = 1;   
    OneselfGrpGrid.displayTitle = 1;
    OneselfGrpGrid.locked = 1;
    OneselfGrpGrid.canSel = 1;
    OneselfGrpGrid.hiddenPlus = 1;
    OneselfGrpGrid.hiddenSubtraction = 1;
    OneselfGrpGrid.selBoxEventFuncName = "QueryMenuInitOneSelf";
    OneselfGrpGrid.loadMulLine(iArray); 
    OneselfGrpGrid.colorNum = 8;
    } 
    catch(ex)
    {
      alert(ex);
    }
}
//����������
function initPublicGrpGrid()
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
        iArray[1][0]="processid";         		//����
        iArray[1][1]="150px";            		//�п�
        iArray[1][2]=100;            			//�����ֵ
        iArray[1][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������
        
        iArray[2]=new Array();
        iArray[2][0]="ҵ������";         		//����
        iArray[2][1]="200px";            		//�п�
        iArray[2][2]=100;            			//�����ֵ
        iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

        iArray[3]=new Array();
        iArray[3][0]="ActivityID";         		//����
        iArray[3][1]="100px";            		//�п�
        iArray[3][2]=100;            			//�����ֵ
        iArray[3][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������
        
        iArray[4]=new Array();
        iArray[4][0]="��������";         		//����
        iArray[4][1]="200px";            		//�п�
        iArray[4][2]=100;            			//�����ֵ
        iArray[4][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������


        iArray[5]=new Array();
        iArray[5][0]="���ȼ���";         		//����
        iArray[5][1]="150px";            		//�п�
        iArray[5][2]=200;            			//�����ֵ
        iArray[5][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
        
        
        iArray[6]=new Array();                                                 
        iArray[6][0]="���ȼ�������";         		//����                             
        iArray[6][1]="200px";            		//�п�                               
        iArray[6][2]=200;            			//�����ֵ                           
        iArray[6][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
        
        
        iArray[7]=new Array();                                                 
        iArray[7][0]="��ɫID";         		//����                             
        iArray[7][1]="150px";            		//�п�                               
        iArray[7][2]=200;            			//�����ֵ                           
        iArray[7][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������
        
        iArray[8]=new Array();                                                 
        iArray[8][0]="�������";         		//����                             
        iArray[8][1]="150px";            		//�п�                               
        iArray[8][2]=200;            			//�����ֵ                           
        iArray[8][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
                                                                         
        PublicGrpGrid = new MulLineEnter( "fm" , "PublicGrpGrid" ); 
        PublicGrpGrid.mulLineCount = 0;   
        PublicGrpGrid.displayTitle = 1;
        PublicGrpGrid.locked = 1;
        PublicGrpGrid.canSel = 1;
        PublicGrpGrid.hiddenPlus = 1;
        PublicGrpGrid.hiddenSubtraction = 1;
        PublicGrpGrid.selBoxEventFuncName = "QueryMenuInitPublic";
        PublicGrpGrid.loadMulLine(iArray);
        PublicGrpGrid.colorNum = 7;
    }
    catch(ex)
    {
      alert(ex);
    }
}
</script>
