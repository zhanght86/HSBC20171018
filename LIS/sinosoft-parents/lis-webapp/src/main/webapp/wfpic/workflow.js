/*
*@author:lk
*@date:20080904 
*/

//  �ƶ�����   �Ƿ���      �����       ���յ�      �Ҽ�����  ѡ�ж���
var flagMove = flagAddLine = oLineStart = oLineEnd = flagRight=flagSelect=null;

var iniFlowXML="";
var iniSvgXML="";



//�Ƿ����ع����棬������ع���ֻ��xml
var isRebuild=false;

var mainMissionId="";

var minigroupdispaly="block";
var count=0;
var nodeaction = "";
var panelpointX=1500;
var panelpointY=1200;

var FlowConfig=
{
   	//��̬����
   	nodeLeft      : 10,        //x
	  nodeTop       : 10,        //y
	  nodeId        : "",        //
	  nodeName      : "",        //
	  nodeFlowType  : "",        //
	  nodeTime      : "",        //ʱЧ
	  nodeTimeType  : "",        //ʱЧ����	  
	  lineName      : "",        //��������
	  lineCondition : "",        //��ת����
	  //lineConditiondis : = "",   //��ת��������
	  //lineConditiontype : = "",   //ת������

	  
	  //����
	  colorSelect   : "#8E83F5", //ѡ��ɫ
	  colorSelLine  : "#FF0000", //����ѡ��ɫ
	  colorFill     : "#EEEEEE", //δѡ��ɫ  fillcolor
	  colorLine     : "#000000", //������ɫ
	  colorStroke   : "#333333", //�߿���ɫ strokecolor
	  colorFont     : "black",   //������ɫ
	  colorShadow   : "black",   //��Ӱ��ɫ
	  colorLight    : "red",     //������ɫ��ǰ
	  colorLightHis : "blue",    //������ɫ�켣
	  isShadow      : "T",       //�Ƿ���Ӱ
	  nodeWeight    : "1pt",     //�߿���
	  lineWeight    : "1pt",     //�߿�
	  fontSize      : "10pt",    //�����С
	  fontFace      : "����",    //����
	  scale         : 1,         //���� ,���ڿ����ã�ÿ�ζ���������С10%�������ڣ�������洢��ʱ�򣬻��Ǵ洢��ʼ���� Ҳ����1
	  //lineConditions : "",        //��ת����
	  flowId        : "", //������id
	  flowVersion        : "", //�������汾
	  flowName      : "",  //����������
	  flowType      : "",  //ҵ������  
	  timeId        : ""  //ʱЧID
}
function initArgs()
{
	  FlowConfig.nodeTop=10;	
	  FlowConfig.nodeLeft=10;
	  FlowConfig.nodeId="";
	  FlowConfig.nodeName="";	  
	  FlowConfig.nodeFlowType="";
   	FlowConfig.nodeTime="";
   	FlowConfig.nodeTimeType="";	  
   	FlowConfig.lineName="";
   	FlowConfig.lineCondition="";
   	   	
   	//����
   //	flagAddLine=null;
}
function initAll()
{
   	//��̬����
   	FlowConfig.nodeLeft      = 10;        //x
	  FlowConfig.nodeTop       = 10;        //y
	  FlowConfig.nodeId        = "";        //
	  FlowConfig.nodeName      = "";        //
	  FlowConfig.nodeFlowType  = "";        //
	  nodeTime                 = "",        //ʱЧ
	  nodeTimeType             = "",        //ʱЧ����	 	  
	  FlowConfig.lineName      = "";        //��������
	  FlowConfig.lineCondition = "";        //��ת����
	 
	  //����       
	  FlowConfig.colorSelect   = "#8E83F5"; //ѡ��ɫ
	  FlowConfig.colorSelLine  = "#FF0000"; //����ѡ��ɫ
	  FlowConfig.colorFill     = "#EEEEEE"; //δѡ��ɫ  fillcolor
	  FlowConfig.colorLine     = "#000000"; //������ɫ
	  FlowConfig.colorStroke   = "#333333"; //�߿���ɫ strokecolor
	  FlowConfig.colorFont     = "black";   //������ɫ
	  FlowConfig.colorShadow   = "black";   //��Ӱ��ɫ
	  FlowConfig.colorLight    = "red";     //������ɫ
	  FlowConfig.colorLightHis = "blue";    //������ɫ�켣
	  FlowConfig.isShadow      = "T";       //�Ƿ���Ӱ
	  FlowConfig.nodeWeight    = "1pt";     //�߿���
	  FlowConfig.lineWeight    = "1pt";     //�߿�
	  FlowConfig.fontSize      = "10pt";    //�����С
	  FlowConfig.fontFace      = "����";    //����
	  FlowConfig.scale         = 1;         //����       
	  FlowConfig.flowId        = ""; //������id
	  FlowConfig.flowName      = "";  //����������
	  FlowConfig.flowType      = "";  //ҵ������
	  FlowConfig.flowTypename  = "";  //ҵ����������
      FlowConfig.timeId        = "";  //ʱЧID
      //zhaojiawei
      FlowConfig.flowVersion   = "";  //�������汾
      //FlowConfig.lineConditions = "";        //��ת����
      //FlowConfig.lineConditiondis  = "";    //��ת��������
	  //FlowConfig.lineConditiontype  = "";   //ת������

      isRebuild=false;    //�Ƿ��ع�
	  
	  //��������ϵ
	  chart.coordsize.x=800;
	  chart.coordsize.y=500;
	  miniChart.coordsize.x=1500;
	  miniChart.coordsize.y=1200;
	  miniRect.coordsize.x=1500;
	  miniRect.coordsize.y=1200;
}

function initFlowXML()
{ //zhaojiawei
    iniFlowXML=	'<?xml version="1.0" encoding="GBK"?><WorkFlow>'+
                   '<FlowConfig>'+
                      '<BaseProperties flowId="'+FlowConfig.flowId+'" version="'+FlowConfig.flowVersion+'" flowName="'+FlowConfig.flowName+'" flowType="'+FlowConfig.flowType +'"/>'+
                      '<VMLProperties nodeTextColor="'+FlowConfig.colorFont+'" nodeFillColor="'+FlowConfig.colorFill+'" nodeStrokeColor="'+FlowConfig.colorStroke+'" nodeShadowColor="'+FlowConfig.colorShadow+'" nodeFocusedColor="'+FlowConfig.colorSelect+'" nodeStrokeWeight="'+FlowConfig.nodeWeight+'" isNodeShadow="'+FlowConfig.isShadow+'" lineStrokeColor="'+FlowConfig.colorLine+'" lineFocusedColor="'+FlowConfig.colorSelLine+'" lineStrokeWeight="'+FlowConfig.lineWeight+'" fontColor="'+FlowConfig.colorFont+'" fontSize="'+FlowConfig.fontSize+'" fontFace="'+FlowConfig.fontFace+'" scale="1"/>'+
                      '<FlowProperties timeId="'+FlowConfig.timeId+'"/>'+
                   '</FlowConfig>'+
                   '<Nodes />'+
                   '<Lines />'+
                 '</WorkFlow>'; 
   document.all.FlowXML.value=iniFlowXML;              
}

function iniWindow()
{
             
   //opener = window.dialogArguments;
   //url = opener.dialogURL;
   //action = url.indexOf('?action=')<0?'':url.slice(url.indexOf('?action=')+8,url.indexOf('&flowId='));
   //flowId= url.indexOf('&flowId=')<0?'':url.slice(url.indexOf('&flowId=')+8,url.length);
   try
   {   
        switch(action)
        {   
           case 'new': 
           
                NewFlow(); 
                enableButton(img11);//����
                enableButton(img7);//����
                enableButton(img1);//��ʼ
                enableButton(img4);//����
                enableButton(img2);//����
                enableButton(img5);//����
                enableButton(img20);//����ͼƬ
                break;
           case 'query':      
                getFlow(flowId,Version);               
                LoadFromNaviFlow(flowId,Version);               
                isRebuild=true;              
                disableButton(img11);//����
                enableButton(sltDegree);//����
                enableButton(img20);//����ͼƬ
                break;
           case 'update':     
         
                getFlow(flowId,Version);
               
                LoadFromNaviFlow(flowId,Version); 
                enableButton(img11);//����  
                enableButton(img7);//���� 
                enableButton(img1);//��ʼ
                enableButton(img4);//����
                enableButton(img2);//����
                enableButton(img5);//����   
                enableButton(img20);//����ͼƬ
                break;                
           case 'copy': 
          
                getFlow(flowId,Version);
                LoadFromNaviFlow(flowId,Version); 
                SaveAsFlow();
                enableButton(img20);//����ͼƬ          
                break;          
           case 'rebuild': 
          
                getRebuildFlow(flowId,Version);
                showRebuildFlow(flowId,Version);
                isRebuild=true;          
                enableButton(img11);//����
                enableButton(img20);//����ͼƬ
                
                break;                       
           default :
                break;	
        } 
       chalkminiline();  
   }
   catch(e)
   {
      alert('�򿪹��̶���Ի���ʱ����');
	    window.close();
   }
}
//��ӽڵ����
function AddFlow(iNo)
{
		//ChangeImgColor(6) 
		
		switch (iNo) 
		{
			case 1 : //<!-- ���̽ڵ㣺 ��ʼ --> 	
			    if(document.all.begin){alert("�ڵ��Ѿ����ڣ�");return;}
					chart.insertAdjacentHTML('beforeEnd','<v:oval id="begin" flowType="begin" class="drag" lines=0 style="position:absolute;left:'+FlowConfig.nodeLeft+';top:'+FlowConfig.nodeTop+';width:50;height:40;"  title="��ʼ"   strokeweight='+FlowConfig.nodeWeight+' StrokeColor='+FlowConfig.colorStroke+' fillcolor='+FlowConfig.colorFill+'  > <v:shadow on = "'+FlowConfig.isShadow+'" type = "perspective" color = "'+FlowConfig.colorShadow+'" opacity = "19660f" matrix = "" offset = "2pt,3pt"/> <v:TextBox inset="1pt,5pt,1pt,1pt" style="FONT-FAMILY:'+FlowConfig.fontFace+'; color:'+FlowConfig.colorFont+';font-size:'+FlowConfig.fontSize+';" class="hand">��ʼ</v:TextBox></v:oval>')
				    //jiyongtian ����ͼ����ʼ�ڵ�  	
					miniChart.insertAdjacentHTML('beforeEnd','<v:oval id="begin" flowType="begin" class="drag" lines=0 style="position:absolute;left:'+FlowConfig.nodeLeft+';top:'+FlowConfig.nodeTop+';width:50;height:40;"  title="��ʼ"   strokeweight='+FlowConfig.nodeWeight+' StrokeColor='+FlowConfig.colorStroke+' fillcolor='+FlowConfig.colorFill+'  > <v:shadow on = "'+FlowConfig.isShadow+'" type = "perspective" color = "'+FlowConfig.colorShadow+'" opacity = "19660f" matrix = "" offset = "1pt,1.5pt"/> <v:TextBox inset="0.5pt,2pt,0.5pt,1pt" style="FONT-FAMILY:'+FlowConfig.fontFace+'; color:'+FlowConfig.colorFont+';font-size:4px;" class="hand">��ʼ</v:TextBox></v:oval>');                   
					//ԭͼ��ʼ�ڵ����Ƶ�����������ű����ı�
					scaleFont();								
					initArgs();
					//getChartXML();
					break;
			case 2 : //<!-- ���̽ڵ㣺 ���� -->
					chart.insertAdjacentHTML('beforeEnd','<v:RoundRect id="'+FlowConfig.nodeId+'" flowType="course"  class="drag" lines=0 timeType="'+FlowConfig.nodeTimeType+'" time="'+FlowConfig.nodeTime+'"  style="position:absolute;left:'+FlowConfig.nodeLeft+';top:'+FlowConfig.nodeTop+';width:100;height:50px;"    title="����"    strokeweight='+FlowConfig.nodeWeight+' StrokeColor='+FlowConfig.colorStroke+'  fillcolor='+FlowConfig.colorFill+'  > <v:shadow on = "'+FlowConfig.isShadow+'" type = "perspective" color = "'+FlowConfig.colorShadow+'" opacity = "19660f" matrix = "" offset = "2pt,3pt"/> <v:TextBox inset="10pt,5pt,1pt,1pt" style="FONT-FAMILY:'+FlowConfig.fontFace+';color:'+FlowConfig.colorFont+';font-size:'+FlowConfig.fontSize+';"  class="hand">'+FlowConfig.nodeName+'</v:TextBox></v:RoundRect>')
					initArgs();
					//getChartXML();
					break;
			case 3 : //<!-- ���̽ڵ㣺 ���� -->
					chart.insertAdjacentHTML('beforeEnd','<v:PolyLine id="'+FlowConfig.nodeId+'"  flowType="judge" class="drag"  lines=0 Points="0,30 50,60 100,30 50,0 0,30" style="position:absolute;left:'+FlowConfig.nodeLeft+';top:'+FlowConfig.nodeTop+';width:100;height:50px;"  title="����"    strokeweight='+FlowConfig.nodeWeight+' StrokeColor='+FlowConfig.colorStroke+'  fillcolor='+FlowConfig.colorFill+' ><v:shadow on = "'+FlowConfig.isShadow+'" type = "perspective" color = "'+FlowConfig.colorShadow+'" opacity = "19660f" matrix = "" offset = "2pt,3pt"/><v:TextBox inset="15pt,15pt,1pt,1pt" style="FONT-FAMILY:'+FlowConfig.fontFace+';color:'+FlowConfig.colorFont+';font-size:'+FlowConfig.fontSize+';"  class="hand">'+FlowConfig.nodeName+'</v:TextBox></v:PolyLine>')
					initArgs();
					//getChartXML();
					break;
			case 4 : //<!-- ���̽ڵ㣺 ���� -->
			    if(document.all.end){alert("�ڵ��Ѿ����ڣ�");return;}
					chart.insertAdjacentHTML('beforeEnd','<v:oval id="end" flowType="end"   class="drag" lines=0 style="position:absolute;left:'+FlowConfig.nodeLeft+';top:'+FlowConfig.nodeTop+';width:50;height:40"  title="����"  strokeweight='+FlowConfig.nodeWeight+'  StrokeColor='+FlowConfig.colorStroke+' fillcolor='+FlowConfig.colorFill+'  > <v:shadow on = "'+FlowConfig.isShadow+'" type = "perspective" color = "'+FlowConfig.colorShadow+'" opacity = "19660f" matrix = "" offset = "2pt,3pt"/> <v:TextBox inset="1pt,5pt,1pt,1pt" style="FONT-FAMILY:'+FlowConfig.fontFace+';color:'+FlowConfig.colorFont+';font-size:'+FlowConfig.fontSize+';" class="hand">����</v:TextBox></v:oval>')
				    //jiyongtian ����ͼ�������ڵ�
					miniChart.insertAdjacentHTML('beforeEnd','<v:oval id="end" flowType="end"   class="drag" lines=0 style="position:absolute;left:'+FlowConfig.nodeLeft+';top:'+FlowConfig.nodeTop+';width:50;height:40"  title="����"  strokeweight='+FlowConfig.nodeWeight+'  StrokeColor='+FlowConfig.colorStroke+' fillcolor='+FlowConfig.colorFill+'  > <v:shadow on = "'+FlowConfig.isShadow+'" type = "perspective" color = "'+FlowConfig.colorShadow+'" opacity = "19660f" matrix = "" offset = "1pt,1.5pt"/> <v:TextBox inset="0.5pt,2pt,0.5pt,1pt" style="FONT-FAMILY:'+FlowConfig.fontFace+';color:'+FlowConfig.colorFont+';font-size:4px;" class="hand">����</v:TextBox></v:oval>');
					// ԭͼ�����ڵ����Ƶ�����������ű����ı�
					if(action == "new"){scaleFont();}
					else if (action == "update")
					{
						//�½�ʱ��λ��Ϊ1���޸�ʱ��λΪ1px��
						if(FlowConfig.nodeLeft != 10 && FlowConfig.nodeTop != 10)
						{    
							 var countendx = FlowConfig.nodeLeft.indexOf("px");							
							 var countendy = FlowConfig.nodeTop.indexOf("px");
							 var newendx =FlowConfig.nodeLeft;
							 var newendy =FlowConfig.nodeTop; 
							 var newendx1 = newendx.substring(0,countendx);
							 var newendy1 = newendy.substring(0,countendy); 
							    //����ڵ��x��y������һ�������߽� 20120531
							 if(Math.abs(newendx1)>1500||Math.abs(newendy1)>1200)
							  {   
							     if(panelpointX <newendx1||panelpointY<newendy1)
							     {   
							        panelpointX=newendx1;
							        panelpointY=newendy1;
							        changediv();								 
							     }    	
							 }	
						 }				
					}			
					initArgs();
					//getChartXML();
					break;
			case 5 : //<!-- ���̽ڵ㣺 ���� -->
					//ChangeImgColor(5)
					AddLine();
					break;
			case 6 : //�ƶ� 
			        initArgs();
					break;
		/*	case 7 : //���̽ڵ㣺 �ۺ� jiyongtian 
			        if(initConfig())
			        { 
			          if(document.getElementById(FlowConfig.nodeId)){alert("�ڵ��Ѿ����ڣ�");return;}		        
					  chart.insertAdjacentHTML('beforeEnd','<v:RoundRect id="'+FlowConfig.nodeId+'" flowType="course"  class="drag" lines=0 timeType="'+FlowConfig.nodeTimeType+'" time="'+FlowConfig.nodeTime+'"  style="position:absolute;left:'+FlowConfig.nodeLeft+';top:'+FlowConfig.nodeTop+';width:100;height:50px;"    title="����"    strokeweight='+FlowConfig.nodeWeight+' StrokeColor='+FlowConfig.colorStroke+'  fillcolor='+FlowConfig.colorFill+'  > <v:shadow on = "'+FlowConfig.isShadow+'" type = "perspective" color = "'+FlowConfig.colorShadow+'" opacity = "19660f" matrix = "" offset = "2pt,3pt"/> <v:TextBox inset="10pt,5pt,1pt,1pt" style="FONT-FAMILY:'+FlowConfig.fontFace+';color:'+FlowConfig.colorFont+';font-size:'+FlowConfig.fontSize+';"  class="hand">'+FlowConfig.nodeName+'</v:TextBox></v:RoundRect>');
					  miniChart.insertAdjacentHTML('beforeEnd','<v:RoundRect id="'+FlowConfig.nodeId+'" flowType="course"  class="drag" lines=0 timeType="'+FlowConfig.nodeTimeType+'" time="'+FlowConfig.nodeTime+'"  style="position:absolute;left:'+FlowConfig.nodeLeft+';top:'+FlowConfig.nodeTop+';width:100;height:50px;"    title="����"    strokeweight='+FlowConfig.nodeWeight+' StrokeColor='+FlowConfig.colorStroke+'  fillcolor='+FlowConfig.colorFill+'  > <v:shadow on = "'+FlowConfig.isShadow+'" type = "perspective" color = "'+FlowConfig.colorShadow+'" opacity = "19660f" matrix = "" offset = "2pt,3pt"/> <v:TextBox inset="2pt,1pt,1pt,1pt" style="FONT-FAMILY:'+FlowConfig.fontFace+';color:'+FlowConfig.colorFont+';font-size:5pt;"  class="hand">'+FlowConfig.nodeName+'</v:TextBox></v:RoundRect>');	
					}
					initArgs();
					//getChartXML();
					break;
		*/
			default :
				  return false;
		}
}
function who()
{
	   var srcEle=event.srcElement;

     if(srcEle.tagName.toLowerCase() == 'textbox')
     {
       srcEle = event.srcElement.parentElement;
     }     
     
     return 	srcEle;
}
//ѡ��
function doSelect(iNode)
{
	 if(flagSelect)
	 {
       if(flagSelect.flowType=="connect")
       {
          	flagSelect.StrokeColor=FlowConfig.colorLine;
          	flagSelect.style.zIndex=-99;
       }	
       else
       {
            flagSelect.fillcolor=FlowConfig.colorFill;
       }	 
	 }
   flagSelect=iNode;
   if(flagSelect.flowType=="connect")
   {
      	flagSelect.StrokeColor=FlowConfig.colorSelLine;
      	flagSelect.style.zIndex=-10;
   }	
   else
   {
        flagSelect.fillcolor=FlowConfig.colorSelect;
   }
}

//�����
function FindObj()
{
	if (!document.all)	{return;}
	//���
	
	doSelect(who());
	
	if(event.button == 1)
	{
		  if(flagAddLine == null)
		  {	
         	flagMove=who();
         	
         	if(flagMove.flowType=="connect")
         	{
         		 flagMove=null;
         	}
          else
          {	
      		   flagMove.setCapture();
      		   //x = event.offsetX;
      		   //y = event.offsetY;
      		   
      	     temp1 = flagMove.style.pixelLeft;
      		   temp2 = flagMove.style.pixelTop;
      		   x = event.clientX;
      		   y = event.clientY;	
      		   
      		   //document.title.value=" "+temp1+":"+temp2+"-----"+x+":"+y;	
      	  }   
		  }
    	if(flagAddLine == 1)
    	{
        oLineStart=who();
        if(oLineStart.id=="")
        {
           alert("���ȶ���ýڵ�");
           flagAddLine = null;
           oLineStart=null;
    	   ChangeImgColor(6);
           return;
        }
    		flagAddLine = 2;
    		return;
    	}
    	//���յ�
    	if(flagAddLine == 2)
    	{
//    		if(oLineStart != event.srcElement)
//    		{     
           oLineEnd=who();
           if(oLineEnd.id=="")
           {
              alert("���ȶ���ýڵ�");
              flagAddLine = null;
              oLineStart=null;
              oLineEnd=null;
    		      ChangeImgColor(6);
              return;
           }
    			 
    			if(!document.all[oLineStart.id + "and" + oLineEnd.id])
    			{   
//    				   if(checkConnect(oLineStart.id,oLineEnd.id))    //֮ǰ�߼����ȶ�����ת�������ٶ�����ת��������������ת�����Ƿ��Ѷ�����жϣ��ָ���Ϊ������ת��ͬʱ��֮�����������������ж���Ҫȥ�������� 2010-9-3
//    				   {   
    				   	  FlowConfig.lineCondition=getCondition(oLineStart.id,oLineEnd.id);
    				      LineMove('PolyLine',oLineStart,oLineEnd,'',1);
//    				   }
//    				   else
//    				   {
//    				      alert("����֮�䲻������!");	  
//    				   }
    			}
//    		}
//    		else
//    	  {
//
//    		}
    	//	flagAddLine = 1;
    		flagAddLine = null;
    	    ChangeImgColor(6);
    		getChartXML();
    	}	  
		
		
		
	}
  else if(event.button == 2)  //�Ҽ�
  {   
      showContextMenu();
  }
  else
	{
	   	alert("��֧�ִ�������¼�");
	}
}
// �޸�����ͼ����
function ChangeMiniWCS(flagMove)
{   
	var nodeX=flagMove.style.pixelLeft;
	var nodeY=flagMove.style.pixelTop;
	var nodeX2 = nodeY*(1500/1200);
	var nodeY2 = nodeX*(1200/1500);
	var newminicoordsizeX;
	var newminicoordsizeY;
	if(nodeX>=nodeX2)
	{
		newminicoordsizeX=nodeX;
		newminicoordsizeY=nodeY2;
	}
	else
	{
		newminicoordsizeX=nodeX2;
		newminicoordsizeY=nodeY;		
	}
	if(newminicoordsizeX > 1500)
	{   
		var countnew=CheckChart(newminicoordsizeX,newminicoordsizeY);
		if(countnew <= 1)
		{   
			miniChart.coordsize.x=newminicoordsizeX + 100;
			miniChart.coordsize.y=newminicoordsizeY + 80;
			miniRect.coordsize.x=newminicoordsizeX + 100;
			miniRect.coordsize.y=newminicoordsizeY + 80;
		}
	}	
}
//����ƶ�
function MoveObj()
{
	if(flagMove != null)
	{   
		var scrollTopbegin = document.getElementById('divWorkChart').scrollTop;     
	    var scrollLeftbegin = document.getElementById('divWorkChart').scrollLeft;     
		flagMove.style.pixelLeft = (event.clientX- x )*FlowConfig.scale +temp1;		
		flagMove.style.pixelTop = (event.clientY - y)*FlowConfig.scale+temp2;
		ChangeMiniWCS(flagMove);
		if(flagMove.style.pixelLeft < 0)flagMove.style.pixelLeft = 0;
		if(flagMove.style.pixelTop < 0)flagMove.style.pixelTop = 0;
		//move lines
		Lines();
		MiniMove(flagMove ,"move");//2012-3-19 jiyongtian
		// 
		var scrollTopend = document.getElementById('divWorkChart').scrollTop;     
	    var scrollLeftend = document.getElementById('divWorkChart').scrollLeft;
		if(scrollTopend != scrollTopbegin || scrollLeftend != scrollLeftbegin)
		{
			scrollit();
			if(flagMove.style.pixelLeft > 800 || flagMove.style.pixelTop > 500)
			{   
				if(scrollTopend == 0)
				{
					document.getElementById('divRect').style.posTop=74;
				}
				else if(scrollLeftend == 0)
				{
					document.getElementById('divRect').style.posRight=28;
				}
				else if(scrollTopend ==0 && scrollLeftend == 0)
			    {   
					miniChart.coordsize.x=1500;
					miniChart.coordsize.y=1200;
					miniRect.coordsize.x=1500;
					miniRect.coordsize.y=1200;					
					if (switchPoint.innerText==4)
					{  				     
					   changeminiline();
					}
					else
					{   
						chalkminiline();
					}
				}				
			}
	    }
	}
}
//����ԭͼ�� У���������³����߽�Ľڵ������������1��������ͼ�����겻�ı�
function CheckChart(newminicoordsizeX,newminicoordsizeY)
{   
	var newcount=0;
	for(var i = 0 ; i < miniChart.childNodes.length; i++)
    {    
	     var tNode = chart.childNodes[i];
	     var tNodeX = tNode.style.pixelLeft;
	     var tNodeY = tNode.style.pixelTop;  
         if(tNodeY >= newminicoordsizeY || tNodeX>= newminicoordsizeX)
	     {          	         	       	 
        	 newcount ++;	
//        	 if(tNodeY>=2500) alert("i   "+newcount);     
         }				  
    } 
	return newcount;
}
//���ƶ�
function Lines()
{
	if(flagMove != null)
	{ 
     var begin;
     var end;  		
		aLines = document.getElementsByTagName('PolyLine');
		
		for( var i = 0; i < aLines.length ; i ++ )
		{
			
			if(aLines[i].flowType=="connect")
			{
         begin = aLines[i].beginNode;
         end = aLines[i].endNode;
         
         
         if ((begin != null) && (end != null)&&(flagMove.id==begin||flagMove.id==end))
         {
         	  
            oLineStart = document.getElementById(begin);
            oLineEnd = document.getElementById(end);
            
            if ((oLineStart == null) || (oLineEnd == null)) continue;

			      LineMove('PolyLine',oLineStart,oLineEnd,aLines[i],2)
			   } 
		   }
		}
	}
}
//����
function LineMove(lineType,oLineStart,oLineEnd,aLines,TypeNo)
{  
   if (oLineStart==null || oLineEnd==null) return '';
   var yy   = getProcessTrans(FlowConfig.flowId,oLineStart.id,oLineEnd.id,FlowConfig.flowVersion);
   /*if(yy==null||typeof(yy)=="undefined"||yy=="undefined") 
   {
   	 yy=new Array();
   	 yy[0]='';
   	 yy[1]='';
   }
   if(yy[0]==null||typeof(yy[0])=="undefined"||yy[0]=="undefined")    
   {
   		yy[0] = '';
   	}  
   	if(yy[1]==null||typeof(yy[0])=="undefined"||yy[1]=="undefined")    
   {
   		yy[1] = '';
   	}*/                   
   var lineHTML = '';      
  // alert(oLineStart.id+":"+oLineEnd.id+":"+yy);           
   var lineConditions = yy [0];//ת������
   var lineConditiontype = yy [1];//ת����������
   var lineCondDesc = yy[2];
   
  // alert('@@:'+lineCondDesc+":"+lineConditions);     
   
   var oLineStartWidth = parseInt(oLineStart.style.width);
   var oLineStartHeight = parseInt(oLineStart.style.height);
   var oLineEndWidth = parseInt(oLineEnd.style.width);
   var oLineEndHeight = parseInt(oLineEnd.style.height);
   var oLineStartX = parseInt(oLineStart.style.left);
   var oLineStartY = parseInt(oLineStart.style.top);
   var oLineEndX = parseInt(oLineEnd.style.left);
   var oLineEndY = parseInt(oLineEnd.style.top);

   //oLineStart Center X,Y
   startCenterX = oLineStartX + parseInt(oLineStartWidth/2);
   startCenterY = oLineStartY + parseInt(oLineStartHeight/2);
   //oLineEnd Center X,Y
   endCenterX = oLineEndX + parseInt(oLineEndWidth/2);
   endCenterY = oLineEndY + parseInt(oLineEndHeight/2);
   
   if(lineType=='Line' && oLineStart!=oLineEnd)
   {//������Line�Ļ����㷨
     //oLineEnd�����϶���
     oLineEndX1 = oLineEndX;
     oLineEndY1 = oLineEndY;
     //oLineEnd�����϶���
     oLineEndX2 = oLineEndX + oLineEndWidth;
     oLineEndY2 = oLineEndY;
     //oLineEnd�����¶���
     oLineEndX3 = oLineEndX;
     oLineEndY3 = oLineEndY + oLineEndHeight;
     //oLineEnd�����¶���
     oLineEndX4 = oLineEndX + oLineEndWidth;
     oLineEndY4 = oLineEndY + oLineEndHeight;
   
     //���oLineEnd��oLineStart�����·�����ȡoLineEnd�����϶���
     if (oLineEndX>oLineStartX && oLineEndY>oLineStartY) {endX = oLineEndX1;endY = oLineEndY1;}
     //���oLineEnd��oLineStart�����·�����ȡoLineEnd�����϶���
     if (oLineEndX<oLineStartX && oLineEndY>oLineStartY) {endX = oLineEndX2;endY = oLineEndY2;}
     //���oLineEnd��oLineStart�����Ϸ�����ȡoLineEnd�����¶���
     if (oLineEndX>oLineStartX && oLineEndY<oLineStartY) {endX = oLineEndX3;endY = oLineEndY3;}
     //���oLineEnd��oLineStart�����Ϸ�����ȡoLineEnd�����¶���
     if (oLineEndX<oLineStartX && oLineEndY<oLineStartY) {endX = oLineEndX4;endY = oLineEndY4;}


			if (TypeNo==1)
			{
				if(FlowConfig.lineName=="")
				{
				   FlowConfig.lineName=FindChildElement(oLineStart,'TextBox').innerText+' �� '+FindChildElement(oLineEnd,'TextBox').innerText;
				}				
			  chart.insertAdjacentHTML('beforeEnd','<v:line  flowType="connect" filled="false" style="position:absolute;z-index:-99"   strokeweight='+FlowConfig.lineWeight+' StrokeColor='+FlowConfig.colorLine+' id="' + oLineStart.id + 'and' + oLineEnd.id + '" beginNode="'+oLineStart.id+'" beginNodeName="'+FindChildElement(oLineStart,'TextBox').innerText+'" endNode="'+oLineEnd.id+'" endNodeName="'+FindChildElement(oLineEnd,'TextBox').innerText+'"conditions="'+lineConditions+'"conditiontype="'+lineConditiontype+'"conddesc="'+lineCondDesc+'"text="'+FlowConfig.lineName+'"Operator="'+Operator+'"flowid="'+FlowConfig.flowId+'"flowVersion="'+FlowConfig.flowVersion+'"flowtype="'+FlowConfig.flowType+'" flowTypename="'+FlowConfig.flowTypename+'" start="'+startX+','+startY+'" end="'+endX+','+endY+'" ><v:stroke StartArrow="Diamond" EndArrow="Classic"   /></v:PolyLine>');
			  oLineStart.lines=parseInt(oLineStart.lines)+1;
			  oLineEnd.lines=parseInt(oLineEnd.lines)+1;
			  initArgs();
			}

			if (TypeNo==2)
			{
				aLines.start.value='"'+startX+','+startY+'"';
				aLines.end.value='"'+endX+','+endY+'"' ;
			}
   }
   else
   {//������PolyLine�Ļ����㷨

      if(oLineStart!=oLineEnd)
      {
	       //�ڵ��Ƿ��ص�: �ص��Ͳ�������
	       if ((oLineStartX + oLineStartWidth >= oLineEndX) && (oLineStartY + oLineStartHeight >= oLineEndY) && (oLineEndX + oLineEndWidth >= oLineStartX) && (oLineEndY + oLineEndHeight >= oLineStartY)) 
	       {
		 	      return "";
	       } 

	       point2X = startCenterX;
	       point2Y = endCenterY;
	       
	       if (endCenterX > startCenterX) 
	       {		  
		        absY = endCenterY>=startCenterY?endCenterY-startCenterY:startCenterY-endCenterY;
		        if (parseInt(oLineStartHeight/2)>=absY) 
		        {
		   	     point1X = oLineStartX + oLineStartWidth;
		   	     point1Y = endCenterY;
		   	     point2X = point1X;
		   	     point2Y = point1Y;
		        }
		        else
		        {
		   	     point1X = startCenterX;
		   	     point1Y = startCenterY<endCenterY?oLineStartY+oLineStartHeight:oLineStartY;
		        }
		        absX = endCenterX-startCenterX;
		        if (parseInt(oLineStartWidth/2)>=absX) 
		        {
		   	     point3X = startCenterX;
		   	     point3Y = startCenterY<endCenterY?oLineEndY:oLineEndY+oLineEndHeight;
		   	     point2X = point3X;
		   	     point2Y = point3Y;
		        }
		        else
		        {
		   	     point3X = oLineEndX;
		   	     point3Y = endCenterY;
		        }		   
	       }
	       if (endCenterX < startCenterX) 
	       {
		          absY = endCenterY>=startCenterY?endCenterY-startCenterY:startCenterY-endCenterY;
		          if (parseInt(oLineStartHeight/2)>=absY)
		          {
		   	       point1X = oLineStartX;
		   	       point1Y = endCenterY;
		   	       point2X = point1X;
		   	       point2Y = point1Y;
		          }
		          else
		          {
		   	       point1X = startCenterX;
		   	       point1Y = startCenterY<endCenterY?oLineStartY+oLineStartHeight:oLineStartY;
		          }
		          absX = startCenterX-endCenterX;
		          if (parseInt(oLineStartWidth/2)>=absX) 
		          {
		   	       point3X = startCenterX;
		   	       point3Y = startCenterY<endCenterY?oLineEndY:oLineEndY+oLineEndHeight;
		   	       point2X = point3X;
		   	       point2Y = point3Y;
		          }
		          else
		          {
		   	       point3X = oLineEndX + oLineEndWidth;
		   	       point3Y = endCenterY;
		          }		   
	        }
	        if (endCenterX == startCenterX) 
	        {
		        point1X = startCenterX;
		        point1Y = startCenterY>endCenterY?oLineStartY:oLineStartY+oLineStartHeight;
		        point3X = startCenterX;
		        point3Y = startCenterY>endCenterY?oLineEndY+oLineEndHeight:oLineEndY;
		        point2X = point3X;point2Y = point3Y;
	        }
	        if (endCenterY == startCenterY) 
	        {
		        point1X = startCenterX>endCenterX?oLineStartX:oLineStartX+oLineStartWidth;
		        point1Y = startCenterY;
		        point3Y = startCenterY;
		        point3X = startCenterX>endCenterX?oLineEndX+oLineEndWidth:oLineEndX;
		        point2X = point3X;point2Y = point3Y;
	        }	   
          
	        points = point1X+','+point1Y+' '+point2X+','+point2Y+' '+point3X+','+point3Y;	   
	   }
	   else
	   {
	       var constLength = 10;
	       point0X = oLineStartX+oLineStartWidth-constLength;
	       point0Y = oLineStartY+oLineStartHeight;
	       point1X = point0X;
	       point1Y = point0Y+constLength;
	       point2X = oLineStartX+oLineStartWidth+constLength;
	       point2Y = point1Y;
	       point3X = point2X;
	       point3Y = point0Y-constLength;
	       point4X = oLineStartX+oLineStartWidth;
	       point4Y = point3Y;
        
	       points = point0X+','+point0Y+' '+point1X+','+point1Y+' '+point2X+','+point2Y+' '+point3X+','+point3Y+' '+point4X+','+point4Y;
	   }
	   if (TypeNo==1)
	   {
	   	 if(FlowConfig.lineName=="")
	   	 {
	   	    FlowConfig.lineName=FindChildElement(oLineStart,'TextBox').innerText+' �� '+FindChildElement(oLineEnd,'TextBox').innerText;
	   	 }

	   	 //chart.insertAdjacentHTML('beforeEnd','<v:PolyLine  flowType="connect" filled="false" style="position:absolute;z-index:-99"   strokeweight='+FlowConfig.lineWeight+' StrokeColor='+FlowConfig.colorLine+' id="' + oLineStart.id + 'and' + oLineEnd.id + '" beginNode="'+oLineStart.id+'" endNode="'+oLineEnd.id+'" text="'+FlowConfig.lineName+'" condition="'+FlowConfig.lineCondition+'" Points="'+points+'"><v:stroke StartArrow="Diamond" EndArrow="Classic"   /><v:TextBox inset="'+10+'pt,'+10+'pt,150pt,150pt" style="FONT-FAMILY:����;color:'+FlowConfig.colorLine+';font-size:12;"  class="hand">'+FlowConfig.lineCondition+'</v:TextBox></v:PolyLine>');
	   	 chart.insertAdjacentHTML('beforeEnd','<v:PolyLine  flowType="connect" filled="false" style="position:absolute;z-index:-99"   strokeweight='+FlowConfig.lineWeight+' StrokeColor='+FlowConfig.colorLine+' id="' + oLineStart.id + 'and' + oLineEnd.id + '" beginNode="'+oLineStart.id + '" beginNodeName="'+FindChildElement(oLineStart,'TextBox').innerText +'" endNode="'+oLineEnd.id+'" endNodeName="'+FindChildElement(oLineEnd,'TextBox').innerText+'" text="'+FlowConfig.lineName+'" flowtype="'+FlowConfig.flowType+'" condition="'+lineConditions+'"Operator="'+Operator+'" conditiontype="'+lineConditiontype+'"conddesc="'+lineCondDesc+'"flowid="'+FlowConfig.flowId+'"flowVersion="'+FlowConfig.flowVersion+'" flowTypename="'+FlowConfig.flowTypename+'" Points="'+points+'"><v:stroke StartArrow="Diamond" EndArrow="Classic"   /></v:PolyLine>');
	     /*  2012-3-20 jiyongtian  ����ͼ����*/
	   	 miniChart.insertAdjacentHTML('beforeEnd','<v:PolyLine  flowType="connect" filled="false" style="position:absolute;z-index:-99"   strokeweight='+FlowConfig.lineWeight+' StrokeColor='+FlowConfig.colorLine+' id="' + oLineStart.id + 'and' + oLineEnd.id + '" beginNode="'+oLineStart.id+'" endNode="'+oLineEnd.id+'" text="'+FlowConfig.lineName+'" condition="'+FlowConfig.lineCondition+'"conddesc="'+lineCondDesc+'" Points="'+points+'"><v:stroke StartArrow="Diamond" EndArrow="Classic" /></v:PolyLine>');   
	     oLineStart.lines=parseInt(oLineStart.lines)+1;
	     oLineEnd.lines=parseInt(oLineEnd.lines)+1;
	     initArgs();
	   }

	   if (TypeNo==2)
	   {
	    	aLines.Points.value=points;
	   }
   }
}
//�۽�
function ChkMoveObj()
{
	if(flagMove != null)
	{ 
		//flagMove.style.left = (parseInt(flagMove.style.left)/100)*100  + parseInt(flagMove.style.width)/2
		//flagMove.style.top  = (parseInt(flagMove.style.top)/100)*100 + parseInt(flagMove.style.height)/2 
		GridNo=parseInt(sltGrid.value);
		flagMove.style.left = Math.abs(FormatNumberDemo((parseInt(flagMove.style.left)+parseInt(flagMove.style.width)/2)/GridNo)*GridNo - parseInt(flagMove.style.width)/2)
		flagMove.style.top  = Math.abs(FormatNumberDemo((parseInt(flagMove.style.top)+parseInt(flagMove.style.height)/2)/GridNo)*GridNo - parseInt(flagMove.style.height)/2 )
		/*2012-3-21 ����ͼ����Ӧ�ĸı�*/
		MiniMove(flagMove,"move");
	}
}
//��굯��
function ReleaseObj()
{
	if(flagMove != null)
	{
		flagMove.releaseCapture();
		//�ж��Ƿ��Զ���������
		if(sltGrid.value!="0")
		{
			ChkMoveObj()
			Lines()
		}
		flagMove = null;
		ChangeImgColor(6);
	}
}
function MiniMove(flagMove,nodeaction)
{ 
  var tID=flagMove.id;
  if(tID !=""&& tID !=null)
  {
     var node = checMiniNode(tID); 
     if(nodeaction == "move")
     {   	
    	 // scrollTop = 550   scrollLeft = 663  ʱ�ڵ�������ͼ�����½�
         //���ȳ�ʼ��������������ֵ
//      var scrollTopbegin = document.getElementById('divWorkChart').scrollTop;     
//      var scrollLeftbegin = document.getElementById('divWorkChart').scrollLeft;
//      if(scrollTop!=0||scrollLeft!=0)
//      {
//    	  
//      }
      var oldX = flagMove.style.pixelLeft;
	  var oldY = flagMove.style.pixelTop;  
      /*����ͼ�ڵ������ֱ�ӵ���ԭͼ�����꣬������FlowConfig.scale������*/
  	   node.style.pixelLeft = oldX;
   	   node.style.pixelTop = oldY;
     }
     else if(nodeaction == "delete")
     {
       node.outerHTML='';
     }
  }
}
//����ԭͼ�ĸı䣨����ڵ�򳤿�����ı䣩�޸�����ͼ
function change(obj)
{
 var tid=obj.id;
 var node=checMiniNode(obj.id);

    node.style.width=obj.style.width;
    node.style.height = obj.style.height;
    node.style.node = obj.style.node;
    node.style.top =obj.style.top;     
  //  opener.FindChildElement(obj,'TextBox').style.fontSize=TabPage2.contentObject().document.all.textWeight.value ;
    node.strokeweight = obj.strokeweight;
    node.timeType = obj.timeType;
    node.timeType = obj.time;
    
}
//����ԭͼ�ڵ�id��ȡ����ͼ�ڵ� 2012-3-20
function checMiniNode(tID)
{ 
   for(var i = 0 ; i < miniChart.childNodes.length; i++)
   { 
	  var tNode = miniChart.childNodes[i];
  	  if(tNode.id==tID)
  	  {   		 
  		 return tNode;

  	   } 		      
   } 
}
//������
function AddLine()
{
	flagAddLine = 1;
}

//ɾ����
function DelLine(lineId)
{
	  if(window.confirm("ȷ��Ҫɾ����������"))
	  {
       var obj=document.getElementById(lineId);
       document.getElementById(obj.beginNode).lines=parseInt(document.getElementById(obj.beginNode).lines)-1;
       document.getElementById(obj.endNode).lines=parseInt(document.getElementById(obj.endNode).lines)-1;             
       obj.outerHTML='';
       /*jiyongtian ����ͼɾ����*/	
       MiniMove(obj,"delete");
    }
    getChartXML();
}
//ɾ���ڵ�
function DelProcess(processId)
{
		if(window.confirm("ȷ��Ҫɾ���ò�����"))
	  {
	  	var Nodes = document.all.chart.childNodes;
      var delNodes=new Array();
      var count=0;
      for ( i = 0;i < Nodes.length;i++ ) 
      {
          Node = Nodes.item(i);
          
          if(Node.flowType=="connect")
          {
          	if(Node.beginNode==processId||Node.endNode==processId)
          	{
             	 delNodes[count]=Node;
             	 count++;
            }
          }                 
      }
      for(i = 0;i < delNodes.length;i++ )
      {
      	  document.getElementById(delNodes[i].beginNode).lines=parseInt(document.getElementById(delNodes[i].beginNode).lines)-1;
          document.getElementById(delNodes[i].endNode).lines=parseInt(document.getElementById(delNodes[i].endNode).lines)-1;             

      	  delNodes[i].outerHTML='';
      	 /* 2012-3-20 jiyongtian ����ͼɾ����*/
      	  MiniMove(delNodes[i],"delete");
      }
      
      var obj=document.getElementById(processId);
      obj.outerHTML='';	
      /* 2012-3-26 jiyongtian ����ͼɾ��δ���ߵĽڵ�*/
       MiniMove(obj,"delete");
    }
    getChartXML();     
}

//�����
function EditLine(lineId,action)
{
       var obj=document.getElementById(lineId);
       workFlowDialog(lineId,obj.flowType,action);
}
//ɾ���ڵ�
function EditProcess(processId,action)
{
      var obj=document.getElementById(processId);

      workFlowDialog(processId,obj.flowType,action);
}

function getChartXML()
{
	xmlDoc = new ActiveXObject('MSXML2.DOMDocument');
    xmlDoc.async = false;
    xmlDoc.loadXML(document.all.FlowXML.value);
    
	  var xmlRoot = xmlDoc.documentElement;
	  var lineXML="<Lines>";
	  var nodeXML="<Nodes>";
    var allNodes=document.all.chart.childNodes;
    for(var i=0;i<allNodes.length;i++)
    {
    	  node=allNodes.item(i);
    	  
    	  if(node.flowType=="connect")//����
    	  {  	 
    	  	  childNode=FindChildElement(node,'stroke'); 
    	     	lineXML=lineXML+
    	     	        '<line>'+
    	     	           '<BaseProperties id="'+node.id+'" lineName="'+node.text+'" lineType="'+node.tagName+'" from="'+node.beginNode+'" to="'+node.endNode+'" />'+
    	     	           '<VMLProperties startArrow="'+childNode.StartArrow+'" endArrow="'+childNode.EndArrow+'" />'+
    	     	           '<FlowProperties flowType="'+node.flowType+'" conditon="'+node.condition+'"/>'+
    	     	        '</line>';   
    	  }
    	  else
       	{
       		  if(node.id!="")   //û�б༭���ܱ���
       		  {
       		      childNode=FindChildElement(node,'TextBox');     
    	     	    nodeXML+='<node>'+
    	                     '<BaseProperties id="'+node.id+'" nodeName="'+childNode.innerText+'" nodeType="'+node.tagName+'" />'+
    	                     '<VMLProperties width="'+node.style.width+'" height="'+node.style.height+'" x="'+node.style.left+'" y="'+node.style.top+'" />';
    	          if(node.flowType=="begin"||node.flowType=="end")
    	          {
    	          	  nodeXML+='<FlowProperties flowType="'+node.flowType+'"/>';
    	          }
    	          else
    	        	{
    	          	  nodeXML+='<FlowProperties flowType="'+node.flowType+'" timeType="'+node.timeType+'" time="'+node.time+'"/>';    	        	
    	        	}
    	          nodeXML+='</node>';        
    	     }      	      	    
       	}
    }
    

    if(nodeXML!="<Nodes>")
    {   	
    	nodeXML+="</Nodes>"
    	var xmlDoc2 = new ActiveXObject('MSXML2.DOMDocument');
      xmlDoc2.async = false;
      xmlDoc2.loadXML(nodeXML);   
      
      xmlRoot.replaceChild(xmlDoc2.documentElement,xmlRoot.getElementsByTagName("Nodes").item(0)); 
    }
    if(lineXML!='<Lines>')
    {
    	lineXML+="</Lines>"
    	var xmlDoc3 = new ActiveXObject('MSXML2.DOMDocument');
      xmlDoc3.async = false;
      xmlDoc3.loadXML(lineXML);     
      xmlRoot.replaceChild(xmlDoc3.documentElement,xmlRoot.getElementsByTagName("Lines").item(0)); 
    }    
    document.all.FlowXML.value = xmlRoot.xml;   
    redrawTree();
}

//���ļ��Ի���
function chooseFolder()
{
  	  var savePath;
      var objSrc=new ActiveXObject("Shell.Application").BrowseForFolder(0,'��ѡ���ļ�Ŀ¼:',0,'');
      
      if(objSrc!=null)
      {
        savePath=objSrc.self.Path;
      }
      else
    	{
    		savePath='';
    		}

     return savePath;
}

//����xml
function SaveToXML()
{
	getChartXML();
  if(document.all.FlowXML.value=='') 
  {
	   alert('���ȴ��������̣�');
	   return;
  }
  var path=chooseFolder();
  if(path=="")
  {
     return ;	
  }

  var xmlDoc = new ActiveXObject('MSXML2.DOMDocument');
  xmlDoc.async = false;
  xmlDoc.loadXML(document.all.FlowXML.value);
  var xmlRoot = xmlDoc.documentElement;
  var Flow = xmlRoot.getElementsByTagName("FlowConfig").item(0);
  filename = Flow.getElementsByTagName("BaseProperties").item(0).getAttribute("flowId")+'.xml';

  if(confirm("���������ļ�Ϊ "+filename+" ��"))
  {
	    var fso = new ActiveXObject("Scripting.FileSystemObject");
      var f = fso.CreateTextFile(path+'/'+filename);
	    var XMLHEAD = '<?xml version="1.0" encoding="GBK"?>';
      f.WriteLine(XMLHEAD+FlowXML.value);
      f.Close();
  }
}
//����ͼ����
function ExportImage(){
   	getChartXML();
	if(document.all.FlowXML.value=='') 
	{
	   alert('���ȴ��������̣�');
	   return;
	}
	var XMLHEAD = '<?xml version="1.0" encoding="GBK"?>';
	var action=""; 
	var para= document.all.FlowXML.value;
	var url="./importWorkflow.jsp";    
	document.all.Tip.innerHTML='<span class="Loading">���ڵ���,���Ժ�...</span>';
	showTip() ;     
    var AjaxRequestObj = new ActiveXObject("Microsoft.XMLHTTP");
	AjaxRequestObj.open("POST", url, true);
	AjaxRequestObj.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
	var parat = encodeURI(encodeURI(para));
	var data="&Para="+parat;
	AjaxRequestObj.onreadystatechange = function(){
        if ( AjaxRequestObj.readyState == 4 &&  AjaxRequestObj.status == 200){
             var result =  AjaxRequestObj.responseText;
	         var realresult = "http://"+window.location.host+"/ui/"+result;
	         window.open(realresult);        
            }
      }
      	AjaxRequestObj.send(data);
	
}       

//�Ӵ��̶�ȡ
function LoadFromXML()
{
	document.all.ofName.click();
	var filename=document.all.ofName.value;
	if(filename=="") return ;
  var   type=filename.match(/^(.*)(\.)(.{1,8})$/)[3];   
  type=type.toUpperCase();   

  if(type=="XML" )
  {   
  	   
  	   
       var xmlDoc = new ActiveXObject('MSXML2.DOMDocument');
       xmlDoc.async = false;
       var flag = xmlDoc.load(filename);
       if (!flag) 
       {
         alert('�ļ�����ʧ��');return;
       }
       
       
       ClearFlow();
       
       var xmlRoot = xmlDoc.documentElement;  
       FlowXML.value = xmlRoot.xml;
       
       redrawFlow();
       redrawTree();
       enableButton(img11);//����
  }   
  else
  {   
     alert("�ļ���������");   
     return   false;   
  }   
}
//������ť���̴�
function LoadFromNaviFlow(flowId,flowVersion)
{    
	  ClearFlow();
	   
	  for(var i=0;i<ArrFlowXML.length;i++)
    { 
        if(ArrFlowXML[i][0]==flowId||ArrFlowXML[i][2]==flowVersion)	
        { 
        	  FlowXML.value= ArrFlowXML[i][1];
        	  break;
        }
    }
    if(FlowXML.value!=iniFlowXML) 
    {   
       redrawFlow();
        
       redrawTree();
    }
    else
    {   
       alert("��ʧ��");     
    }   
}


//�ع����̴�
function showRebuildFlow(flowId,flowVersion)
{
	//jiyongtian ��ʼ��һЩ����2012-7-12
	ClearFlow();
	//end 
    for(var i=0;i<ArrRebuildFlow.length;i++)
    {
        if(ArrRebuildFlow[i][0]==flowId||ArrRebuildFlow[i][2]==flowVersion)	
        {
        	  FlowXML.value=ArrRebuildFlow[i][1];
        }
    }	  

    if(FlowXML.value!=iniFlowXML) 
    {  
       
       redrawFlow();
       redrawTree();
    }
    else
    {   
       alert("��ʧ��");     
    }   
}
//״̬���̴�
function showStatus(MainMissionId,flowId,flowVersion)
{
	  ClearFlowView();
	  for(var i=0;i<ArrFlowXML.length;i++)
    {
        if(ArrFlowXML[i][0]==flowId||ArrFlowXML[i][2] ==flowVersion)	
        {
        	  FlowXML.value= ArrFlowXML[i][1];
        	  break;
        }
    }
    if(FlowXML.value!=iniFlowXML) 
    {  
       redrawFlow();
       var arr=getStatus(MainMissionId);
       highLight(arr);
       mainMissionId=MainMissionId;
    }
    else
    {   
       alert("��ʧ��");     
    }   
}
function highLight(arr)
{
	  if(arr==null)
	  {
	     return ;
	  }
	  for(var i=0;i<arr.length;i++)
	  {
	  	  if(arr[i][0]=="2")
	  	  {
	  	    var lightnode=document.getElementById(arr[i][1]);
	        if(lightnode)
	        {
	        	   lightnode.StrokeColor=FlowConfig.colorLightHis;	
	        }
	      }	
	  }
	  for(var i=0;i<arr.length;i++)
	  {
	  	  if(arr[i][0]=="1")
	  	  {	  	
	  	     var lightnode=document.getElementById(arr[i][1]);
	         if(lightnode)
	         {

	          	 lightnode.StrokeColor=FlowConfig.colorLight;
           }
	      }	
	  }	  
}


//�½�����
function NewFlow()
{
   	 workFlowDialog('','flow','new');
}
//�༭����
function EditFlow(flowId,action)
{
   	 workFlowDialog(flowId,'flow',action);
}

//�½�����
function SaveAsFlow()
{
	
	  if(!checkCanSave())
	  {
	     return ;
	  }
	  
   	workFlowDialog('','flow','SaveAs');
}

//�������
function ClearFlow()
{
	   document.all.chart.innerHTML='';
	   //jiyongtian �������ͼ
	   document.all.miniChart.innerHTML='';
	   initAll();
	   initFlowXML();
	   iframeTree.clearTree();
}
//�������
function ClearFlowView()
{
	   document.all.chart.innerHTML='';
	   //jiyongtian �������ͼ
	   document.all.miniChart.innerHTML='';
	   initAll();
	   initFlowXML();
}

function ShowNodeHis()
{
	  var hisNode=who();
	  var isHis=false;
	  if(hisNode.flowType=="course")
	  {	  	 
	  	 if(!Util.isEmpty(ArrStatusNode))
	     { 
          for(var i=0;i<ArrStatusNode.length;i++)
          {
              if(ArrStatusNode[i][0]==mainMissionId)	
              {
              	  for(var j=0;j<ArrStatusNode[i][1].length;j++)
              	  {
              	  	  if(ArrStatusNode[i][1][j][1]==hisNode.id)
              	  	  {  
              	  	     	isHis=true;
              	  	     	break;
              	  	  }
              	  }
              }
          }
    	 } 
	  	 else
	  	 {
	  		   return;	
	  	 }
	  	 
	  	 if(isHis)
	  	 {
	        var arr=getNodeHis(mainMissionId,hisNode.id);
	        editNodeHis(arr);
	        showHis(parseInt(hisNode.style.left),parseInt(hisNode.style.top));
	     }
	    else
	    	{
	    		hideHis();
	    	}	
	  }
	else
		{
			hideHis();
			}
}

function changeDegree()
{  //zhaojiawei
   var degreeXML=getDegreeFlow(FlowConfig.flowId,sltDegree.value,FlowConfig.flowVersion);
   if(degreeXML!=iniFlowXML) 
   {  
   	   ClearFlow();
   	   FlowXML.value=degreeXML;
       redrawFlow();
       redrawTree();
       //disableButton(img11);//����
   }
   else
   {   
      alert("�任ʧ��");     
   }      
}
//�ػ�����
function redrawFlow()
{
	  
     var xmlDoc = new ActiveXObject('MSXML2.DOMDocument');
     xmlDoc.async = false;
     
     xmlDoc.loadXML(FlowXML.value);
     var xmlRoot = xmlDoc.documentElement;  
     //����ȫ�ֱ���
 
     var flowConfig = xmlRoot.getElementsByTagName("FlowConfig").item(0);
  
     var flowBase=flowConfig.getElementsByTagName("BaseProperties").item(0);
  
     FlowConfig.flowId = flowBase.getAttribute("flowId");
     //zhaojiawei
     FlowConfig.flowVersion = flowBase.getAttribute("version");
     
     FlowConfig.flowName = flowBase.getAttribute("flowName");
  
     FlowConfig.flowType = flowBase.getAttribute("flowType");
 
     var flowVML=flowConfig.getElementsByTagName("VMLProperties").item(0);
   
     FlowConfig.colorFont = flowVML.getAttribute("nodeTextColor");
 
     FlowConfig.fontFace = flowVML.getAttribute("fontFace");
  
     FlowConfig.fontSize = flowVML.getAttribute("fontSize");
     FlowConfig.colorStroke = flowVML.getAttribute("nodeStrokeColor");
     FlowConfig.nodeWeight = flowVML.getAttribute("nodeStrokeWeight");  
     FlowConfig.colorFill = flowVML.getAttribute("nodeFillColor");  
     FlowConfig.colorShadow = flowVML.getAttribute("nodeShadowColor");
     FlowConfig.colorSelect = flowVML.getAttribute("nodeFocusedColor");
     FlowConfig.isShadow = flowVML.getAttribute("isNodeShadow");
     
     FlowConfig.colorLine = flowVML.getAttribute("lineStrokeColor");
     FlowConfig.colorSelLine = flowVML.getAttribute("lineFocusedColor");
     FlowConfig.lineWeight = flowVML.getAttribute("lineStrokeWeight");  
     
     FlowConfig.scale = flowVML.getAttribute("scale");  
     
     var Nodes = xmlRoot.getElementsByTagName("Nodes").item(0).childNodes;
     for ( var i = 0;i < Nodes.length;i++ )
     {
     	  Node = Nodes.item(i);
     	  nodeBase = Node.getElementsByTagName("BaseProperties").item(0);
        nodeVML = Node.getElementsByTagName("VMLProperties").item(0);
        nodeFlow = Node.getElementsByTagName("FlowProperties").item(0);
        
        //���ýڵ����
        FlowConfig.nodeId=nodeBase.getAttribute("id");
        FlowConfig.nodeName=nodeBase.getAttribute("nodeName");    
	    FlowConfig.nodeTop=nodeVML.getAttribute("y");   
	    FlowConfig.nodeLeft=nodeVML.getAttribute("x"); 
        FlowConfig.nodeFlowType=nodeFlow.getAttribute("flowType"); 
        FlowConfig.nodeTimeType=nodeFlow.getAttribute("timeType"); 
        FlowConfig.nodeTime=nodeFlow.getAttribute("time"); 
 
        switch(FlowConfig.nodeFlowType)
        {
           case 'begin': 
                AddFlow(1);
                break;
           case 'course': 
                AddFlow(2);
             // jiyongtian 2012-3-26
                rectChart(Node);
                break;
           case 'judge': 
                AddFlow(3);
                break;                
           case 'end':
                AddFlow(4);
                break;                
           default :
                break;	
        } 
     }
    
     var Lines = xmlRoot.getElementsByTagName("Lines").item(0).childNodes;
     for ( var i = 0;i < Lines.length;i++ )
     {   
     	  Line = Lines.item(i);
     	  lineBase = Line.getElementsByTagName("BaseProperties").item(0);
        //lineVML = Line.getElementsByTagName("VMLProperties").item(0);
        lineFlow = Line.getElementsByTagName("FlowProperties").item(0);
        
        //���ýڵ����
        FlowConfig.lineName=lineBase.getAttribute("lineName");    
	    from=lineBase.getAttribute("from");
	    to=lineBase.getAttribute("to");    
	    FlowConfig.lineCondition=lineFlow.getAttribute("conditon");        
       // LineMove('PolyLine',document.getElementById(from),document.getElementById(to),'',1);
        LineMove('PolyLine',document.getElementById(from),document.getElementById(to),'',1);
     
     }        
}

//������ͼ
function ZoomOut(mulriple)
{   
    chart.coordsize.x = "800";
    chart.coordsize.y = "500";
    miniRect.coordsize.x = "1500";
    miniRect.coordsize.y = "1200";
    FlowConfig.scale ="1"; 
    FlowConfig.fontSize="10pt";
	FlowConfig.scale=parseFloat(FlowConfig.scale)*mulriple;
	var newx=parseInt(chart.coordsize.x)/mulriple;
	var newy=parseInt(chart.coordsize.y)/mulriple;
	chart.coordsize.x=parseInt(newx);
	chart.coordsize.y=parseInt(newy);
    var tfontSize=parseFloat(FlowConfig.fontSize)*mulriple;  
	changeFontSize(tfontSize);	
	changeminiRect(mulriple);	
}
function changeminiRect(mulriple)
{  
    var miniRCSizeX = "1500";
    var miniRCSizeY = "1200";
    if(mulriple>="0.75")
    { 
       var newx2=parseInt(miniRCSizeX)*mulriple;
	   var newy2=parseInt(miniRCSizeY)*mulriple;
	   miniRect.coordsize.x=parseInt(newx2);
	   miniRect.coordsize.y=parseInt(newy2);
	}
    else
    {  
       mulriple="0.75";
       var newx1=parseInt(miniRCSizeX)*mulriple;
  	   var newy1=parseInt(miniRCSizeY)*mulriple;
  	   miniRect.coordsize.x=parseInt(newx1);
  	   miniRect.coordsize.y=parseInt(newy1);
    }
}
/*//��С��ͼ    ��ʱȥ��
function ZoomIn(mulriple)
{     
	FlowConfig.scale=parseFloat(FlowConfig.scale)*1.1;
	var newx=parseInt(chart.coordsize.x)*1.1;
	var newy=parseInt(chart.coordsize.y)*1.1;
	chart.coordsize.x=parseInt(newx);
	chart.coordsize.y=parseInt(newy);
	FlowConfig.fontSize=parseFloat(FlowConfig.fontSize)/1.1;
	var tfontSize=parseFloat(FlowConfig.fontSize)/1.1;
    changeFontSize(tfontSize);
}
*/
// 2012-3-15
function changeFontSize(tfontSize) 
{ 
  for(var i = 0 ; i < chart.childNodes.length; i++)
  { 
	var node = chart.childNodes[i];
    if(node.tagName == "RoundRect" )  //���̽ڵ� �ı������С��λ��
  	{
  		for(var j = 0 ; j < node.childNodes.length; j++)
  		{
	   		var tagText=node.childNodes[j];
	   		if (tagText.tagName=="TextBox") 
	   		{  
		       tagText.style.fontSize=tfontSize+"pt";
		       if(tfontSize=="5")
		       {
		       tagText.inset="2pt,2pt,1pt,1pt";
		       }
		        else if(tfontSize=="2.5")
		       {
		       tagText.inset="1pt,1pt,1pt,1pt";
		       }
		        else if(tfontSize>="7.5")
		       {
		        tagText.inset="5pt,5pt,1pt,1pt";
		       }
	   		}
	   }	   
	}
	if(node.tagName == "oval")   //��ʼ�����ڵ� �ı������С��λ��
  	{
  		for(var j = 0 ; j < node.childNodes.length; j++)
  		{
	   		var tagText=node.childNodes[j];
	   		if (tagText.tagName=="TextBox") 
	   		{	
	   		  tagText.style.fontSize=tfontSize+"pt"; 	     
		      if(tfontSize=="5"||tfontSize=="2.5")
		      {		     
		      tagText.inset= "1pt,1pt,1pt,1pt";	
		      }	
		      else if(tfontSize>="7.5")
		      {
		      tagText.inset= "1pt,5pt,1pt,1pt";
		      }		            
	   		}
	   }	   
	}
  }  
}
//  jiyongtian 2012-3-19 �½�ʱ          ����ͼ��ʾ���̽ڵ�
function rectMiniChart(obj,tText)
{  
   miniChart.insertAdjacentHTML('beforeEnd','<v:RoundRect id="'+obj.id+'" flowType="course"  class="drag" lines=0 timeType="'+obj.timeType+'" time="'+obj.time+'"  style="position:absolute;left:'+obj.style.left+';top:'+obj.style.top+';width:'+obj.style.width +';height:'+obj.style.height+ ';"    title="����"    strokeweight='+FlowConfig.nodeWeight+' StrokeColor='+FlowConfig.colorStroke+'  fillcolor='+FlowConfig.colorFill+'  > <v:shadow on = "'+FlowConfig.isShadow+'" type = "perspective" color = "'+FlowConfig.colorShadow+'" opacity = "19660f" matrix = "" offset = "1pt,1.5pt"/> <v:TextBox inset="0.5pt,1pt,0.5pt,1pt" style="FONT-FAMILY:'+FlowConfig.fontFace+';color:'+FlowConfig.colorFont+';font-size:5px;"  class="hand">'+tText+'</v:TextBox></v:RoundRect>');
   if(obj.style.left>1500||obj.style.top>1200)
   {
	   if(panelpointX<obj.style.left||panelpointY<obj.style.top)
	   {
		   panelpointX=obj.style.left;
       	   panelpointY=obj.style.top;
       	   changediv();
	   }
   }
}
//�޸�ʱ          ����ͼ��ʾ���̽ڵ�
function rectChart(Node)
{   
	
    nodeBase = Node.getElementsByTagName("BaseProperties").item(0);
    nodeVML = Node.getElementsByTagName("VMLProperties").item(0);
    nodeFlow = Node.getElementsByTagName("FlowProperties").item(0);       
    miniChart.insertAdjacentHTML('beforeEnd','<v:RoundRect id="'+nodeBase.getAttribute("id")+'" flowType="course"  class="drag" lines=0 timeType="'+nodeFlow.getAttribute("timeType")+'" time="'+nodeFlow.getAttribute("time")+'"  style="position:absolute;left:'+nodeVML.getAttribute("x")+';top:'+nodeVML.getAttribute("y")+';width:100px;height:50px;"    title="����"    strokeweight='+FlowConfig.nodeWeight+' StrokeColor='+FlowConfig.colorStroke+'  fillcolor='+FlowConfig.colorFill+'  > <v:shadow on = "'+FlowConfig.isShadow+'" type = "perspective" color = "'+FlowConfig.colorShadow+'" opacity = "19660f" matrix = "" offset = "1pt,1.5pt"/> <v:TextBox inset="1pt,1pt,0.5pt,0.5pt" style="FONT-FAMILY:'+FlowConfig.fontFace+';color:'+FlowConfig.colorFont+';font-size:5px;"  class="hand">'+nodeBase.getAttribute("nodeName")+'</v:TextBox></v:RoundRect>');
    var countx = nodeVML.getAttribute("x").indexOf("px");
    var county = nodeVML.getAttribute("y").indexOf("px");
    var newx =nodeVML.getAttribute("x");
    var newy =nodeVML.getAttribute("y"); 
    var newx1 = newx.substring(0,countx);
    var newy1 = newy.substring(0,county); 
    //����ڵ��x��y������һ�������߽� 20120531
    if(Math.abs(newx1)>1500||Math.abs(newy1)>1200)
    {   
        //���panelpointX<new2 ��panerlpointY<newy2 ��new2��newy2��ֵ��panelpointX��panelpointY
        if(panelpointX <newx1||panelpointY<newy1)
        {   
        	panelpointX=newx1;
        	panelpointY=newy1;
        	changediv();
        }    	
    }
}
//������ͼ�����߽�ʱ������div��coordsize
function changediv()
{  
	var x1=0;
	var y1=0;
	var x2=0;
	var y2=0;
	// ��������ͼԭ���������������������ϵ��xֵΪpanelpointXʱ��yֵ �� ������ϵ��yֵΪpanelpointYʱ��xֵ
	if(panelpointX >1500)
	{   	
		 x1= panelpointX*(1.0);
		 y1= panelpointX*(1200/1500);		
	}
	if(panelpointY >1200)
    {  		
		y2 = panelpointY*(1.0);	
		x2 = panelpointY*(1500/1200);
    }
	//�Ƚ���������ϵ��xֵ��ȡ�ϴ�ֵ��������ϵ
	if(x1>=x2)
	{   
		miniChart.coordsize.x=x1+100;
		miniChart.coordsize.y=y1+80;
		miniRect.coordsize.x=x1+100;
		miniRect.coordsize.y=y1+80;
		panelpointX=x1+100;
		panelpointY=y1+80;
	}
	else
	{    
		miniChart.coordsize.x=x2+100;		
		miniChart.coordsize.y=y2+80;	 
		miniRect.coordsize.x=x2+100;	
		miniRect.coordsize.y=y2+80;
		panelpointX=x2+100;
		panelpointY=y2+80;		
	}
}
//jiyongtian 2012-3-21 �Ƿ���ʾ����ͼ
function hideminiChart()
{   
	if( divFlow.style.display=="none")
	{
	     	     divFlow.style.display="block"; 
	     if(minigroupdispaly == "block")
	     {   
	    	 divMiniGroup.style.display = "block";
	    	 divRect.style.display="block";
	     }
	     else
	     {
	    	 divRect.style.display="none";
	    	 divMiniGroup.style.display = "none";
	     }
	}
	else
	{   		
		divFlow.style.display="none";    
	    divRect.style.display="none";
	    divMiniGroup.style.display="none";
	}
}
//����ͼ  ��С����ť��ʵ��
function minimizetabMiniAture()
{
	divMiniGroup.style.display="none";
	divRect.style.display="none";
    minigroupdispaly="none";
}
//����ͼ  ��󻯰�ť��ʵ��
function maximizetabMiniAture()
{
	divMiniGroup.style.display="block";
	divRect.style.display="block";
	minigroupdispaly="block";
}
//����ͼ  �رհ�ť��ʵ��
function closetabMiniAture()
{    
    divMiniGroup.style.display="none";
	divFlow.style.display="none";    
    divRect.style.display="none";
}
//���� ���̵���ͼʱ ����ͼ�ĺ��
function chalkminiline()
{  
   document.all.miniRect.innerHTML='';
   var firstpointx="0";
   var firstpointy="0";
   var secendpointx="825";
   var secendpointy="0";
   var thirdpointx="825";
   var thirdpointy="640";
   var fourthpointx="0";
   var fourthpointy="640";
   var poinccts = firstpointx+','+firstpointy+' '+secendpointx+','+secendpointy+' '+thirdpointx+','+thirdpointy+' '+fourthpointx+','+fourthpointy+' '+firstpointx+','+firstpointy;
   miniRect.insertAdjacentHTML('beforeEnd','<v:PolyLine  filled="false" style="position:absolute;z-index:100"   strokeweight="1pt" StrokeColor="red" id="firstline" Points="'+poinccts+'"></v:PolyLine>'); 
} 
//�ر� ���̵���ͼʱ ����ͼ�ĺ��
function changeminiline()
{  
   document.all.miniRect.innerHTML='';
   var firstpointx="0";
   var firstpointy="0";
   var secendpointx="950";
   var secendpointy="0";
   var thirdpointx="950";
   var thirdpointy="640";
   var fourthpointx="0";
   var fourthpointy="640";
   var poinccts = firstpointx+','+firstpointy+' '+secendpointx+','+secendpointy+' '+thirdpointx+','+thirdpointy+' '+fourthpointx+','+fourthpointy+' '+firstpointx+','+firstpointy;
   miniRect.insertAdjacentHTML('beforeEnd','<v:PolyLine  filled="false" style="position:absolute;z-index:100"   strokeweight="1pt" StrokeColor="red" id="firstline" Points="'+poinccts+'"></v:PolyLine>'); 
}
//�½�ʱ���ָ����ű���
function recoverInit()
{
	sltMagnifyGrid.value='100';
	MagnifyGrid();
	panelpointX=0;
	panelpointY=0;
	var tdivWorkChart=document.getElementById('divWorkChart');
	var tdivRect=document.getElementById('divRect');
	tdivRect.style.posTop=74;
	tdivRect.style.posRight=28;
	//��ȱ�ٻָ�ԭͼ�ĵط��������ع�������
}
//�ȶ������ű�����Ȼ�����ڵ㣬�����ĵ������ڵ����ı��������
function scaleFont()
{
	MagnifyGrid(); 
}
//��ͬ���ű����������ƶ�����
function Speed()
{
//ԭͼ����Ϊ(800,500),����ͼ����Ϊ(1500,1200)���ٶ�Ϊ 0.203
  var tSPeed="0.203";
  var  chartcoordsizex=chart.coordsize.x;
//  alert(minicoordsizex);
  switch(chartcoordsizex)
  { 
    case 1600:        
               tSPeed=tSPeed*2;
               break;
    case 1400:        
               tSPeed=tSPeed*1.75;
               break;
    case 1200:
               tSPeed=tSPeed*1.5;
               break;
    case 1000:
               tSPeed=tSPeed*1.25;
               break;
     case 800:
               tSPeed=tSPeed*1;
               break;
     case 600:
               tSPeed=tSPeed*0.75;
               break;
     case 400:
               tSPeed=tSPeed*0.5;
               break;
     case 200: 
               tSPeed=tSPeed*0.25; 
               break;
     default :
	           tSPeed=tSPeed*(chartcoordsizex/800);
               break;
 }
 var miniChartcoordsizex= miniChart.coordsize.x;
 switch(miniChartcoordsizex)
 {
    case 1500:
	            tSPeed=tSPeed*1;
	            break;
    default:
	            tSPeed=tSPeed/(miniChartcoordsizex/1500);
                break;
 }
 return tSPeed;
}