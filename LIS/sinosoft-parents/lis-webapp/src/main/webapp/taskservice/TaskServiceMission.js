//�������ƣ�TaskService.js
//�����ܣ�
//�������ڣ�2004-12-15 
//������  ��ZhangRong
//���¼�¼��  ������    ��������     ����ԭ��/����

var showInfo;
var arrResult;
var mDebug = "0";
var mOperate = "";
var mAction = "";
var tMaxProcessValue = 0 ;
var tCurrentTitle = "";
//var mSwitch = parent.VD.gVSwitch;
var mySql = new SqlClass();
var k = 0;
/*********************************************************************
 *  �ύ
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function submitForm()
{
	//var showStr="���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
	//var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
  
	if( mAction == "")
		return;

	//showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
	document.getElementById("fm").submit(); //�ύ
}

function startEngine()
{
	mAction = "START";
	document.all( 'fmAction' ).value = mAction;
	submitForm();
}

function stopEngine()
{
	mAction = "STOP";
	document.all( 'fmAction' ).value = mAction;
	submitForm();
}


/*********************************************************************
 *  �������Ͷ�������ύ��Ĳ���,���������ݷ��غ�ִ�еĲ���
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function afterSubmit( FlagStr, content )
{
	try{
		showInfo.close();
	}
	catch(e)
	{
		}
	if( FlagStr == "Fail" )
	{             
		var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;  
		//showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");   
		var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
		var iWidth=550;      //�������ڵĿ��; 
		var iHeight=250;     //�������ڵĸ߶�; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
	}
	else
	{ 
		var urlStr = "../common/jsp/MessagePage.jsp?picture=S&content=" + content ;  
		//showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");   
		var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
		var iWidth=550;      //�������ڵĿ��; 
		var iHeight=250;     //�������ڵĸ߶�; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
	}
	
	mAction = ""; 
	//queryTaskPlanInfo();
	//queryTaskInfo();
}

/*********************************************************************
 *  ��ʾdiv
 *  ����  ��  ��һ������Ϊһ��div�����ã��ڶ�������Ϊ�Ƿ���ʾ�����Ϊ"true"����ʾ��������ʾ
 *  ����ֵ��  ��
 *********************************************************************
 */
function showDiv(cDiv,cShow)
{
	if( cShow == "true" )
		cDiv.style.display = "";
	else
		cDiv.style.display = "none";  
}


function initAllTabs()
{
	//��ȡ��Ҫ��ʼ����tab
	lockPage("���ڻ�ȡ����������ı�ǩ...........");
  jQuery.post(
  	"./InitTaskTab.jsp",
  	//params,
  	function(data) {
  		unLockPage();
  		lockPage("���ڼ�������������ı�ǩ...........");
  		$('#initProcessBar').show();
			TabsMapArray = data;
			if(TabsMapArray!=null)
			{
			var tMax = TabsMapArray.length;
			var tCurrent = 0;
			for(i=0;i<TabsMapArray.length;i++)
			{
				tCurrent = i;
				var tPercent = ((tCurrent+1)*100)/tMax;
				tMaxProcessValue = tPercent;				
				dealProcessBar();
			 	var tTaskTabID = TabsMapArray[i].TaskTabID ;
    		var tTaskTabName = TabsMapArray[i].TaskTabName ;
    		var tType = TabsMapArray[i].Type ;
    		var tOtherProperty = TabsMapArray[i].OtherProperty ;
    		var tTabSrc = TabsMapArray[i].TabSrc ;
    		var tNote = TabsMapArray[i].Note ;
    		var tIFrameID = TabsMapArray[i].IFrameID ;
    	
    	 var content = "<div id="+tTaskTabID+"><iframe id=\""+tIFrameID+"\" "+tOtherProperty+" src=\""+tTabSrc+"\"></iframe>";
				$('#TaskTab').tabs('add',{
					id:tTaskTabID,
			    title:tTaskTabName,
			    content:content,
			    fit:true,
			    closable:false
		    });
		    
		    $('#TaskTab').tabs('select',TabsMapArray[0].TaskTabName);
			}		
			
				$('#TaskTab').tabs({ 
						onSelect:function(title){ 
				 			refresh_tab();
				} 
			});
		}

		},"json" 
	);
}

/*
	��ʼ��button,ֻ��ӵ���������й����ǩҳ���û��ſ��Բ���������ֹͣ���水ť.
*/
function initButton()
{
	mySql = new SqlClass();
	mySql.setResourceName("taskservice.TaskServiceMissionSql");
	mySql.setSqlId("TaskServiceMissionSql1");  
	mySql.addSubPara(mOperator); 
	//mySql.addSubPara('002'); 
	var ttemp = easyExecSql(mySql.getString());
	//alert(ttemp);
	if(ttemp!=null&&ttemp=='1')
	{
		$('#startButton').removeAttr("disabled");
		$('#stopButton').removeAttr("disabled");
	}
}

function dealProcessBar()
{
	var value = $('#initProcessBar').progressbar('getValue');
			if (value < tMaxProcessValue){
				value += Math.floor(Math.random() * 15);
				$('#initProcessBar').progressbar('setValue', value);
				setTimeout(arguments.callee, 200);
			}
			if(value==100)
			{
				$('#initProcessBar').hide();
				unLockPage();
			}
}

function refresh_tab()
{
			var refresh_tab = $('#TaskTab').tabs('getSelected');
			if(refresh_tab && refresh_tab.find('iframe').length > 0){
					var _refresh_ifram = refresh_tab.find('iframe')[0];
					
					//ֻ��iframe ��refreshFlag ��������Ϊ1ʱ����ˢ��
					if(_refresh_ifram.refreshFlag!=null&&_refresh_ifram.refreshFlag=='1')
					{
						lockPage('����ˢ��,���Ժ�...');
						var _Src = _refresh_ifram.src;
						var refresh_url = _Src;
						_refresh_ifram.contentWindow.location.href=refresh_url;
						unLockPage();	
					}
			}
}