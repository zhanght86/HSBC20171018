//"use strict"; 因为使用eval，导致该页面无法使用JS严格模式
function readWF(evt, flowId,flowVersion,mainMissionId) {
	var vmlXML = null;
	var activities = null;
	if(_workflowAction == 'rebuild')
		vmlXML = _getRebuildFlow(flowId,flowVersion);
	else if(_workflowAction == 'status'){
		if(flowVersion == null || "" == flowVersion || "null" == flowVersion){
			//兼容状态查看现有代码没有传Version的状况
			flowVersion = _getMissionVersion(mainMissionId);
		}
		vmlXML = _getFlow(flowId,flowVersion); 
		missionId = mainMissionId;
		activities = _getStatus(mainMissionId,flowVersion);
		if(!activities){
			alert("读取节点状态失败");
		}
	}
	else {
		vmlXML = _getFlow(flowId,flowVersion);
	}
	if (vmlXML == null || vmlXML  == "")
		return;
	readXML(evt,vmlXML,activities);
}

function _getMissionVersion(mainMissionId){	
	   var action="QUERY";
	   var actionId="STRING";
	   var para="select max(version) from(select max(version) version from lwmission where mainMissionId='"+mainMissionId+"' union select max(version) version from lbmission where mainMissionId='"+mainMissionId+"') t";
	   var url="./interact.jsp";
	   
	   var AjaxRequestObj = InitXMLHttp();
	   if (AjaxRequestObj != null) {
		   AjaxRequestObj.open("POST", url, false);
		   AjaxRequestObj.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
		   var data="Action="+action+"&ActionId="+actionId+"&Para="+para;
		   AjaxRequestObj.send(data);
		   eval(AjaxRequestObj.responseText);
		   if( _S !='') {
			   return _S;
		   } else {
			   alert('查询工作流版本失败: mainMissionId='+mainMissionId);  
			   return ""; 
		   }  
	   }             
	}

function _getFlow(flowId,flowVersion){	
   var action="QUERY";
   var actionId="BLOB";
   var para="WFXML::LWPROCESSXML:: and processid='"+flowId+"' and version='"+flowVersion+"'";
   var url="./interact.jsp";
   
   var AjaxRequestObj = InitXMLHttp();
   if (AjaxRequestObj != null) {
	   AjaxRequestObj.open("POST", url, false);
	   AjaxRequestObj.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
	   var data="Action="+action+"&ActionId="+actionId+"&Para="+para;
	   AjaxRequestObj.send(data);
	   eval(AjaxRequestObj.responseText);
	   if( _XML !='') {
		   return _XML;
	   } else {
		   alert('查询工作流失败: flowId='+flowId+",version="+flowVersion);  
		   return ""; 
	   }  
   }             
}

function _getRebuildFlow(flowId,flowVersion){
	var action="QUERY";
	var actionId="REBUILD";
	var para=flowId+"::"+flowVersion;
    var url="./interact.jsp";
    
    var AjaxRequestObj = InitXMLHttp();
    if (AjaxRequestObj != null) {  
    	AjaxRequestObj.open("POST", url, false);
    	AjaxRequestObj.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
    	var data="Action="+action+"&ActionId="+actionId+"&Para="+para;
    	AjaxRequestObj.send(data);
	    eval(AjaxRequestObj.responseText);
	    if(_XML!='') {   		
	    	return _XML;	  
	    } else {
	    	alert('查询工作流失败: flowId='+flowId+",version="+flowVersion);
	    	return "";
	    }
    }
	    
}

function saveToDB(evt) {
	if(!_checkCanSave()) {
	     return ;
	}
	var flowxml = _saveWF(evt);
	var XMLHEAD = '<?xml version="1.0" encoding="GBK"?>';
	var action="INSERT";
	var actionId = "SaveFlow";
	if(_workflowAction == 'rebuild') {
		actionId="RebuildFlow";
	}
	var para=XMLHEAD+flowxml+"::"+window.parent.Operator+"::"+window.parent.ManageCom;
	var url="./interact.jsp";    
   
	window.parent.showTip("正在保存,请稍候...");  
   
	var AjaxRequestObj = InitXMLHttp();
	if (AjaxRequestObj != null) {  
		AjaxRequestObj.onreadystatechange = function () {
			if (AjaxRequestObj.readyState == 4 && AjaxRequestObj.status == 200) {  
				if(AjaxRequestObj.responseText) {
				   	eval(AjaxRequestObj.responseText);
				   	if(result=="success") {
				   		window.parent.showTip("保存成功!");
	 	  	  	        alert('点击\'确定\'关闭窗口');
	 	  	  	        window.parent.hideTip();
	 	  	  	        window.parent.window.close();
				   	} else{
				   		window.parent.showTip("保存失败!");
				   	}	 	 
				} else {
	 	  	  		 	window.parent.showTip("保存失败!");
				}
			}
		};
		AjaxRequestObj.open("POST", url, true);
		AjaxRequestObj.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
		var data="Action="+action+"&ActionId="+actionId+"&Para="+para;
		AjaxRequestObj.send(data);
	}
}

function _checkCanSave(){
	
	if(svg.attr("id") == null || svg.attr("id") == ""){
		 alert("没有创建新流程");
		 return false;   
	}
	
	//先判断有没有开始结束
    if(!svg.select("#start")) {
        alert("没有开始节点");
        return false;       	
    }
    
    if(!svg.select("#end")) {
        alert("没有结束节点");
   	 	return false;       	
    }
    
    var allLines =  svg.selectAll("polyline");
    if(allLines.length == 0) {
    	alert("没有任何联线");
   	 	return false;       	
    }
    
    var notFound = true;
    for(var i=0;i < allLines.length; i++ ){
    	if(allLines[i].attr("start-id") == 'start'){
    		notFound = false;
    		break;
    	}
    }
    if(notFound) {
        alert("“开始”节点是孤立的");
        return false;       	
    }
    
    notFound = true;
    for(var i=0;i < allLines.length; i++ ){
    	if(allLines[i].attr("end-id") == 'end'){
    		notFound = false;
    		break;
    	}
    }
    if(notFound) {
        alert("“结束”节点是孤立的");
        return false;       	
    }
    
    //判断有没有孤立节点
 	var allNodes = svg.selectAll("rect");
 	if(allNodes.length == 0){
 		alert("没有过程节点");
   	 	return false;
 	}
 	for(var n=0;n < allNodes.length; n++ ){
 		var nodeId = allNodes[n].attr("id");
 		if(nodeId == "BackDrop") continue;
 		var notFoundStart = true;
 		var notFoundEnd = true;
 		 for(var i=0;i < allLines.length; i++ ){
 	    	if(allLines[i].attr("start-id") == nodeId){
 	    		notFoundStart = false;
 	    	}
 	    	if(allLines[i].attr("end-id") == nodeId){
 	    		notFoundEnd = false;
 	    	}
 	    	if(!notFoundStart && !notFoundEnd) break;
 	    }  
 		 if(notFoundStart || notFoundEnd) {
 	        alert("节点“"+allNodes[n].attr("name")+"”是孤立的");
 	        return false;       	
 	    }
    }
 	
      //判断必须节点
     //此处原始代码被被注释，不再实现
     return true;   
}

function _getStatus(MainMissionId,version) {
	var action="QUERY";
	var actionId="ARRAY";                                      //这个地方还要统一
	var para="select '1',activityid from lwmission where MainMissionId='"+MainMissionId+"' and version = '"+version+"' union select '2',activityid from lbmission where MainMissionId='"+MainMissionId+"' and version = '"+version+"' ";
	var url="./interact.jsp";
	
	var AjaxRequestObj = InitXMLHttp();
	if (AjaxRequestObj != null) {  
		AjaxRequestObj.open("POST", url, false);
		AjaxRequestObj.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
		var data="Action="+action+"&ActionId="+actionId+"&Para="+para;
		AjaxRequestObj.send(data);
		if(AjaxRequestObj.responseText) {
			eval(AjaxRequestObj.responseText);
			return _A;	
	    } else {
	    	alert('查询工作流失败: MainMissionId='+MainMissionId);  
			return null; 
	    }    	      
	}       
}

function _getNodeHis(MainMissionId,ActivityId) { 
	var action="QUERY";
	var actionId="ARRAY";                                      //这个地方还要统
	var para="select indate,intime,outdate,outtime,defaultoperator from lbmission where MainMissionId='"+MainMissionId+"' and activityid='"+ActivityId+"' union select indate,intime,null,'',defaultoperator from lwmission where MainMissionId='"+MainMissionId+"' and activityid='"+ActivityId+"'";
	var url="./interact.jsp";
    
	var AjaxRequestObj = InitXMLHttp();
	if (AjaxRequestObj != null){  
		AjaxRequestObj.open("POST", url, false);
		AjaxRequestObj.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
		var data="Action="+action+"&ActionId="+actionId+"&Para="+para;
		AjaxRequestObj.send(data);
	    	 
		if(AjaxRequestObj.responseText){
			eval(AjaxRequestObj.responseText);
			return  _A; 	
		} else {
			alert('查询工作流失败: MainMissionId='+MainMissionId+",ActivityId="+ActivityId);  
			return null;                             
		}    	      
	}      
}