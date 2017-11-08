<%@ page language="java" import="java.util.* " pageEncoding="GBK"%>
<%@page contentType="text/html;charset=GBK"%>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.utility.*"%>
<%@ page import="java.util.*"%>
<%@page import="com.sinosoft.service.*"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<title>
规则测试
</title>

<link rel="stylesheet" type="text/css"
	href="./resources/css/ext-all.css" />
<link rel="stylesheet" type="text/css" href="./resources/css/rule.css" />
<link rel="stylesheet" type="text/css" href="./resources/css/wizard.css" />

<script type="text/javascript" src="./baseLib/ext-base.js"></script>
<script type="text/javascript" src="./baseLib/ext-all.js"></script>
<script type="text/javascript" src="./baseLib/ext-lang-zh_CN.js"></script>

<script type="text/javascript"
	src="../common/easyQueryVer3/EasyQueryVer3.js"></script>
<LINK href="../common/css/Project.css" rel=stylesheet type=text/css />
<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css />


<%
    String tLanguage = "zh";
	//IbrmsTestService testService = new IbrmsTestService();
	//testService.setLanguage(tLanguage);
	//testService.getRuleInfo(request);
	
  
    
    VData sVData = new VData();
    sVData.add(0,request);
    sVData.add(1,tLanguage);
	BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
    tBusinessDelegate.submitData(sVData, "", "IbrmsTestServiceUI");
   
    VData resVData = new VData();	
    resVData = tBusinessDelegate.getResult();
    TransferData sTransferData=new TransferData();
    sTransferData = (TransferData)resVData.get(0);
    
    String writeTestScript = (String)sTransferData.getValueByName("writeTestScript");
	String writeWinInfo = (String)sTransferData.getValueByName("writeWinInfo");
	String writeStep = (String)sTransferData.getValueByName("writeStep");
	String writeRuleInfo = (String)sTransferData.getValueByName("writeRuleInfo");
	String writeFormStepInfo = (String)sTransferData.getValueByName("writeFormStepInfo");
	
%>

<script type="text/javascript">
	 var mLanguage = '<%=tLanguage%>';
	  var maxSteps = 3; //最大步骤数
	  var globalStores = [] ; //所有的combo的store的数组，如果在编辑页面和grid中都有该item的store，需要共享
      Ext.onReady(function(){
    	  Ext.QuickTips.init();
        // NOTE: This is an example showing simple state management. During development,
        // it is generally best to disable state management as dynamically-generated ids
        // can change across page loads, leading to unpredictable results.  The developer
        // should ensure that stable state ids are set for stateful components in real apps.
        Ext.state.Manager.setProvider(new Ext.state.CookieProvider());
        
         var viewport = new Ext.Viewport({
            layout:'border',
            items:[
                new Ext.BoxComponent({ // raw
                    region:'north',
                    el: 'north',
                    height:32
                }),{
                    region:'west',
                    id:'west-panel',
                    title:'步骤',
                    split:true,
                    width: 200,
                    minSize: 175,
                    maxSize: 400,
                    collapsible: true,
                    margins:'0 0 0 5',
                    layout:'accordion',
                    layoutConfig:{
                        animate:true
                    },
                    items: [{
                        contentEl: 'TreeMenu',
                        title:'Navigation',
                        border:false,
                        iconCls:'nav'
                    }]
                },
                new Ext.Panel({
                    region:'center',
                    autoScroll :true,
                    id:'center-panel',
                    deferredRender:false,
                    contentEl:'center2'
                })
             ]
        });
     
      <%=writeTestScript%>

      
                
        
   // simple.render(document.body);
        Ext.getDom("buttonPrev").disabled=true;
        gotoStep(1);
    });
    
    var currentStep = -1;
    
  
    
    function gotoStep(step){
        if(currentStep != step){
            if(currentStep!=-1){
               Ext.get("step"+currentStep).removeClass("wizardCurrentStep");
               Ext.getDom("formStep"+currentStep).style.display="none";
            }
            Ext.get("step"+step).addClass("wizardCurrentStep");
            Ext.getDom("formStep"+step).style.display="block";

            var container = Ext.getDom("formStep"+step);
            var bomName = container.getAttribute("bomName");
            
       /*     if(bomName && !container.initBomComponent)
            {
                if(container.getAttribute("isArray"))
                    initBomComponent("grid_"+bomName);
                else
                	initBomComponent(bomName);
            	container.initBomComponent = true;
            }*/
            
            currentStep = step;
            if(step==1)
               Ext.getDom("buttonPrev").disabled=true;
            else
               Ext.getDom("buttonPrev").disabled=false;
               
            if(step==maxSteps)
               Ext.getDom("buttonNext").disabled=true;
            else
               Ext.getDom("buttonNext").disabled= false;

            if(step==maxSteps)
                completeTest();              
        }
    }
    
    function gotoPrevStep(){
          if(currentStep>1)
              gotoStep(currentStep-1);
    }
 
    function gotoNextStep(){
          if(currentStep<maxSteps)
              gotoStep(currentStep+1);
    }

    function gotoLastStep(){
    	gotoStep(maxSteps);
    }

    var json = {};
    function completeTest(){
       	json = {};
    	for(var i=2;i<maxSteps;i++){
            var objDiv = document.getElementById("formStep"+i);
            if(objDiv.getAttribute("bomName"))
            	objDiv.objBom = null;
    	}
    	for(var i=2;i<maxSteps;i++){
            var objDiv = document.getElementById("formStep"+i);
            if(objDiv.getAttribute("bomName"))
            {    
                var bomName = objDiv.getAttribute("bomName");
                  var realObj = {};
                  if(objDiv.objBom)
                      realObj = objDiv.objBom;
                  else{
                  	var obj = {};
                  	realObj = obj;
                     if(objDiv.getAttribute("isArray")){ //是数组
                        var grid = objDiv.grid;
                        var store = grid.getStore();
                        if(store.getCount()==0)  
                            obj = makeEmptyBomJson(objDiv.getAttribute("bomName"));
                        else{
                            obj = recordToJson(grid,objDiv.fields,bomName);
                        }
                                              
                     }
                     objDiv.objBom = realObj;
                     var fatherBom =	objDiv.getAttribute("fatherBom");
                     
                     if(fatherBom)
                  	   makeObjBomAncestor(objDiv,obj);
                     else        
                  	   json[objDiv.getAttribute("bomName")]=obj;
                  }
                  
                  if(!objDiv.getAttribute("isArray")){
                      var objs = objDiv.getElementsByTagName("*");
                      for(var j=0;j<objs.length;j++){
                          if(objs[j].getAttribute("bomItem") )
                          {   
                              
                              var value;
                              var obj = objs[j];
                              var notNull = false;
                              if(obj.dataType=="date"){
                                 value = obj.value;
                                 if(value)
                                    notNull = true; 
                              }
                              else{
                                 if(obj.formField) 
                                     value = obj.formField.getValue();
                                 else
                                     value = obj.value;
                                
                           	 
                           	     if(value!=undefined && value!=null){
                           		    value = new String(value);
                           		    if(value!="")
                           		        notNull = true;
                           	    }
                              }
                              if(notNull){
                                  //  alert(value);
                        	       realObj[objs[j].getAttribute("itemName")]=""+value+"";
                        	      
                              }
                          }
                      }
                     
                 }
                  
            } 
       }
    
       post = "javaClass="+Ext.getDom("javaClass").value;
       post += "&method="+Ext.getDom("method").value;
       post += "&template="+Ext.getDom("template").value;
       post += "&json="+Ext.util.JSON.encode(json);
      // alert(Ext.util.JSON.encode(json));
 	   post = encodeURI(post);
	   post = encodeURI(post); //两次，很关键
	   Ext.Ajax.request({
		   url: 'runRuleTest.jsp?Lan='+mLanguage,
		   method:'post',
		   success: function(response,options){handleSuccess(Ext.decode(response.responseText));},
		   failure:function(response,options){},
		   headers: {
			   "Content-Type":"application/x-www-form-urlencoded",
			   "cache-control":"no-cache"
		   },
		    		   
		   params: post
		});
         
    }

    function handleSuccess(jsonObj){
       var resultSummary = Ext.getDom("testSummary") ;

			 //tongmeng 2010-12-29 add
			 //根据计算型规则和判断型规则展现不同的结论
			 var calflag = jsonObj.CalFlag;
       var summary = "<div>  规则执行的开始时间是"+jsonObj.startTime+",结束时间是"+jsonObj.endTime
                    +",执行的时间间隔为"+jsonObj.duration+"毫秒</div>";
       //alert('calflag:'+calflag);
       if(calflag=='0')
       {
       	  summary += "<div>共有"+jsonObj.results+"条没有通过规则测试！</div>";
       }

       resultSummary.innerHTML = summary;             
       
			var Result;
			var tTitle;
 			var tHeader;
 			 if(calflag=='0')
       {
       	 tTitle = '测试结论';
       	 tHeader = "结论";
       }
       else
       {
       	 tTitle = '计算结果';
       	 tHeader = "结果";
       }
			 if(calflag=='0')
       {
 	    		Result = Ext.data.Record.create([
 	   
                                           {name: 'result', type: 'string'},
 	                                       {name: 'rule', type: 'string'},
 	                                       {name: 'bomsDesc', type: 'string'}
 	                                  ]);
 	                                  
 	                                   var resultReader = new Ext.data.JsonReader({
 	                                        totalProperty: "results",
 	                                        root: "result",
 	                                        id: "id"
 	                                    }, Result);
      
 	   var store = new Ext.data.Store({
 		   reader: resultReader,
 	        // load using script tags for cross domain, if the data in on the same domain as
 	        // this page, an HttpProxy would be better
 	        proxy:  new Ext.data.MemoryProxy(jsonObj)
 	        
 	    });

 			
 	    var grid = new Ext.grid.GridPanel({
 	        el:'testResult',
 	        width:1000,
 	        height:500,
 	        
 	        title:tTitle,
 	        store: store,
 	       
 	        

 	        // grid columns
 	        columns:[{
 	            id: 'result', // id assigned so we can apply custom css (e.g. .x-grid-col-topic b { color:#333 })
 	            header: tHeader,
 	            dataIndex: 'result',
 	           renderer: renderRule,
 	            width: 200
 	        },{
     	        id:"rule",
 	            header: "使用的规则",
 	            dataIndex: 'rule',
 	            renderer: renderRule,
 	            width: 400
            },
            {
     	        id:"bomsDesc",
 	            header: "所使用的BOM数据",
 	            dataIndex: 'bomsDesc',
 	            renderer: renderRule,
 	            width: 200
            }
            ]
	     
 	    });

 	    // render it
 	    grid.render();
 	    store.load();
 	     }
 	     else
 	     {
 	     	 	    		Result = Ext.data.Record.create([
 	   
                                           {name: 'result', type: 'string'},
 	                                       {name: 'rule', type: 'string'},
 	                                       {name: 'calrule', type: 'string'},
 	                                       {name: 'bomsDesc', type: 'string'}
 	                                  ]);
 	                                  
 	                                   var resultReader = new Ext.data.JsonReader({
 	                                        totalProperty: "results",
 	                                        root: "result",
 	                                        id: "id"
 	                                    }, Result);
      
 	   var store = new Ext.data.Store({
 		   reader: resultReader,
 	        // load using script tags for cross domain, if the data in on the same domain as
 	        // this page, an HttpProxy would be better
 	        proxy:  new Ext.data.MemoryProxy(jsonObj)
 	        
 	    });

 			
 	    var grid = new Ext.grid.GridPanel({
 	        el:'testResult',
 	        width:1000,
 	        height:500,
 	        
 	        title:tTitle,
 	        store: store,
 	       
 	        

 	        // grid columns
 	        columns:[{
 	            id: 'result', // id assigned so we can apply custom css (e.g. .x-grid-col-topic b { color:#333 })
 	            header: tHeader,
 	            dataIndex: 'result',
 	           renderer: renderRule,
 	            width: 100
 	        },{
     	        id:"rule",
 	            header: "使用的规则",
 	            dataIndex: 'rule',
 	            renderer: renderRule,
 	            width: 200
            },
            {
     	        id:"rule",
 	            header: "计算逻辑",
 	            dataIndex: 'calrule',
 	            renderer: renderRule,
 	            width: 200
            },
            {
     	        id:"bomsDesc",
 	            header: "所使用的BOM数据",
 	            dataIndex: 'bomsDesc',
 	            renderer: renderRule,
 	            width: 200
            }
            ]
	     
 	    });
	 	 grid.render();
 	   store.load();
 	     }
 	                                  
 	  
    }

    
    function renderRule(value,p,record){ //自动换行
        p.attr = 'ext:qtip="'+value+'"';

        return value;
    }

    function renderBoms(value){
        
    }
    
    function complete(){
    	 
   
    }

    function makeEmptyBomJson(bomName){
        var json = [];
        json[0] ={};
        var winContainer = Ext.getDom("win_"+bomName);
        var childBoms =  winContainer.getAttribute("childBoms");
    	if(childBoms!="")
    	{
    	   	var arrBoms = childBoms.split(",");
    	   	for(var i=0;i<arrBoms.length;i++){
        	   	json[0][arrBoms[i]] = makeEmptyBomJson(arrBoms[i]);
    	   	}
    	}
    	return json;
        
    }

    function makeFormTextField(itemNameWithBom,itemName){
    	var field =new Ext.form.TextField({name: itemNameWithBom,renderTo:"col."+itemNameWithBom ,
        	id:itemNameWithBom,width:250});
    	var control = Ext.getDom(itemNameWithBom);
    	control.setAttribute("bomItem",true);
    	control.setAttribute("itemName",itemName);
    	control.dataType="string";
    	control.formField = field;
    }

    function makeFormNumberField(itemNameWithBom,itemName){
    	var field =new Ext.form.NumberField({name: itemNameWithBom,renderTo:"col."+itemNameWithBom ,
        	id:itemNameWithBom,width:250});
    	var control = Ext.getDom(itemNameWithBom);
    	control.setAttribute("bomItem",true);
    	control.setAttribute("itemName",itemName);
      	control.dataType="number";
    	control.formField = field;
    }

    function makeFormDateField(itemNameWithBom,itemName){
    	var field =new Ext.form.DateField({name: itemNameWithBom,renderTo:"col."+itemNameWithBom ,
        	id:itemNameWithBom, format: 'Y-m-d H:i:s',width:250	});
    	var control = Ext.getDom(itemNameWithBom);
    	control.setAttribute("bomItem",true);
    	control.setAttribute("itemName",itemName);
      	control.dataType="date";
    	control.formField = field;
    }   

    function makeFormComboField(itemNameWithBom,itemName,sql){
       
        
        var store = globalStores[itemNameWithBom] ;

        if(!store){
          store = new Ext.data.JsonStore({
            fields: ['code', 'name'],
            root: "rows",
            url   : "getComboxData.jsp?sql="+sql
         //   data : BOMArray 
           });
          globalStores[itemNameWithBom] = store;
        }
    	var combo= new Ext.form.ComboBox({
      	  fieldLabel : "name",
    	  valueField : "code",
    	  store: store,
    	  autoWidth : false,
    	  width:250,
          displayField:'name',
          typeAhead: true,
          mode: 'remote',
          lazyInit: true,
          forceSelection: true,
          triggerAction: 'all',
          emptyText:'',
          selectOnFocus:true,
          renderTo:'col.'+itemNameWithBom
      
       });

       combo.addListener( "change", function(obj,newValue,oldValue){Ext.getDom(itemNameWithBom).value=newValue;
       	//alert('newValue'+newValue+":"+Ext.getDom(itemNameWithBom).value);
       	}); 
        var control = Ext.getDom(itemNameWithBom);
     	control.dataType="code";
     	control.store = store;
    	control.formField = combo;
    }   

    function makeGridComboField(sql,bomName,itemName,colIndex){
    	var container = Ext.getDom("grid_"+bomName);
    	var itemNameWithBom = bomName+"."+itemName;

     	var store = globalStores[itemNameWithBom] ;

        if(!store){
          store = new Ext.data.JsonStore({
            fields: ['code', 'name'],
            root: "rows",
            url   : "getComboxData.jsp?sql="+sql
         //   data : BOMArray 
           });
          globalStores[itemNameWithBom] = store;
        }
        
        if(!container.comboStores){
        	container.comboStores =[];
        	container.comboLoads  =[];
        }
        container.comboStores[colIndex] = store;
        container.comboLoads[colIndex]  = false;
    	var combo= new Ext.form.ComboBox({
      	  
    	  store: store,
    	  width:100,
    	  valueField : "code",
    	  displayField:'name',
    	  
     	  typeAhead: true,
     	 mode: 'remote',
         lazyInit: true,
          forceSelection: true,
          triggerAction: 'all',
          emptyText:'',
          listClass: 'x-combo-list-small'
    
      
       });

       return combo;

    }   

    function renderComboDisplayText(value,metaData,record,rowIndex,colIndex,store){

         if(value=="")
            return value;
       
        var bomName = store.bomName;
      
        var container = Ext.getDom("grid_"+bomName);
        var store1 = container.comboStores[colIndex-2];
        
        if(typeof store1=="undefined"){
        	return "";
        }
        
         for(var i=0;i<store1.getCount();i++){
            var record1 = store1.getAt(i);
            
            if(record1.get('code')==value)
                return record1.get('name');
        }
        return value;
        
    }

 
    //构造祖先的bom对象，以便能形成有层次关系的对象
    
    function makeObjBomAncestor(objDiv,objBom){
        
        var fatherBom =	objDiv.getAttribute("fatherBom");
        var elem = getElementByBomName(fatherBom);
        if(elem!=null){
        	 if(elem.objBom)
                 elem.objBom[objDiv.getAttribute("bomName")]=objBom;
        	 else{
        		 var obj = {};
                 var realObj = obj;
                 if(elem.getAttribute("isArray")) //是否需要构造成数组
                 {
                     obj =[];
                     realObj = {};
                     obj[0] = realObj;                        
                 } 
                 elem.objBom = realObj;  
                 elem.json = obj;  
                 if(elem.getAttribute(fatherBom)) //递归调用该方法
                 {
                	 makeObjBomAncestor(elem,realObj);
                 }else
                	 json[elem.getAttribute("bomName")]=obj;
        	 }
       	}
    }

    //根据bomName找到在第几个formStep中
    function getElementByBomName(bomName){
        for(var i=2;i<maxSteps-1;i++){
            var objDiv = document.getElementById("formStep"+i);
            if(objDiv.getAttribute("bomName") && objDiv.getAttribute("bomName")==bomName )
                return objDiv;
        }
    }

    function makeGrid(fields,columns,gridRenderTo,title,gridContainer,bomName){
    	var rm=new Ext.grid.RowNumberer();
    	var sm=new Ext.grid.CheckboxSelectionModel();

            
        var cmColumns = [rm,sm];
    	    
    	for(var i=0;i<columns.length;i++)
    	        cmColumns[2+i] = columns[i];	                                      
    	    	
    	var cm = new Ext.grid.ColumnModel(cmColumns);

    	var record = Ext.data.Record.create(fields);
        
        var recordReader = new Ext.data.JsonReader({
              totalProperty: "results",
              root: "rows",
              id: "id"
          }, record);

      // create the Data Store
        var  store = new Ext.data.Store({
       
          proxy: new Ext.data.MemoryProxy({}),
          // the return will be XML, so lets set up a reader
          reader: recordReader

      });
       store.bomName = bomName;

      // create the editor grid
      var grid = new Ext.grid.EditorGridPanel({
  
          store: store,
          sm:sm,
          cm: cm,
          renderTo: gridRenderTo,
          width:1000,
          height:550,
          title:title,
          frame:true,
          clicksToEdit:1,

          tbar: [{
              text: '增加',
              handler : function(){
                  var p = new Ext.data.Record({
                                    
                  });
                  for(var i=0;i<fields.length;i++){
                      if(fields[i].type=="date")
                          continue;
                      if(fields[i].type=="float"){
                     //     p.set(fields[i].name,0);
                      }
                      else
                          p.set(fields[i].name,"");
                  }
                 
                  grid.stopEditing();
                  store.add( p);
                  grid.startEditing(0, 0);
                  store.load();
                  var winContainer = Ext.getDom("win_"+grid.bomName);
                  winContainer.add = true;
                  handleRowDblClick(grid,store.getCount()-1);
              }
          },{
          	 text: "删除",
          	 handler : function(){
          	 	    var records = sm.getSelections();
          	 	    for(var i=0;i<records.length;i++){
          	 	    	 store.remove(records[i]);
          	 	    }
          	 	    grid.reconfigure(store,cm);
          	  }
          	}]
      });
      
    
      
      grid.addListener("rowdblclick",handleRowDblClick);
      grid.bomName = bomName ; //引用bom的名称

      var container = Ext.getDom(gridContainer);
      if(container)
      {
          container.fields = fields;
          container.grid   = grid;
      }

    
      	
    }


    function handleRowDblClick(grid,rowIndex,e){
  	  
   	  var winContainer = Ext.getDom("win_"+grid.bomName);
   	  winContainer.rowIndex = rowIndex;
  	  var win = winContainer.win;
  	  if(!win){
          win = new Ext.Window({
              applyTo     : 'win_'+grid.bomName,
              layout      : 'fit',
              width       : 1000,
              height      : 600,
              closable    : false,
              autoScroll :true,
              hideParent : true,
              closeAction :'hide',
              modal       : true,         
              plain       : true,
              items       : new Ext.TabPanel({
                  applyTo        : 'tabs_'+grid.bomName,
                  autoTabs       : true,
                  activeTab      : 0,
                  autoScroll     : true,
                  deferredRender : false,
                  border         : false
              }),

              buttons: [{
                  text     : '确定',
                  handler  : function(){
                       handleSubmit(grid.bomName);
                  }
              },{
                  text     : '取消',
                  handler  : function(){
            	      handleCancel(grid,grid.bomName,win);
                  }
              }]
          });
          winContainer.win = win;
          var tabPanel = win.items.itemAt(0);
         // alert(tabPanel.items[0]);
          handleActiveTab(tabPanel,tabPanel.items.itemAt(0));
          tabPanel.on("tabchange",handleActiveTab);
         // alert(win.items.itemAt(0).getActiveTab(0));    
      
      }
      record = grid.getStore().getAt(rowIndex);
      
    //  grid.getStore
      winContainer.record = record;
      fillValue(grid.bomName,record);
      win.center();
      win.show();
  	
  }

    function handleActiveTab(tabPanel,tab){
      //   alert(Ext.getDom(tab.getEl()).id  );
    }
  

    function handleCancel(grid,bomName,win){
   	  var winContainer = Ext.getDom("win_"+bomName);
   	  if(winContainer.add) //增加操作，需要将新增的一行删掉
   	  {
   	   	  grid.getStore().remove(grid.getStore().getAt(winContainer.rowIndex));
   	      grid.getStore().load();
   	  }
   	  winContainer.add = false;
   	  win.hide();
  
    	
    }
  
  function handleSubmit(bomName){
 	  var winContainer = Ext.getDom("win_"+bomName);
 	  var record = winContainer.record;
 	  var win    = winContainer.win;
 	  var objs = Ext.getDom("tab_"+bomName).getElementsByTagName("*");

 	  record.beginEdit();
	  for(var i=0;i<objs.length;i++){
	  	
	 	   var obj = objs[i];
	 	   var tid = obj.getAttribute("id");
	 	   var tName =  obj.getAttribute("itemName");
	 	   var tbomItem = obj.getAttribute("bomItem");
	// 	   alert(tid+":"+tbomItem+":"+tName);
	 	   if(obj.getAttribute("bomItem")){
		 	   
		 	   var value = obj.value;
		 	   if(obj.formField && obj.dataType!="date" )
			 	   value = obj.formField.getValue();
		 	  var notNull = false;
           	 
       	      if(value!=undefined && value!=null){
       		    value = new String(value);
       		    if(value!="")
       	 	        notNull = true;
       	       }
		 	   if(!notNull)
			 	   value = "";
		 	    if(obj.dataType=="date")
			 	    value = Date.parseDate(value,'Y-m-d H:i:s');
		 /*	    else if(obj.dataType=="code" && value!=""){
			 	    var container = Ext.getDom("grid_"+bomName);
			 	    var itemName = obj.getAttribute("itemName");
			 	    
			 	    for(var j=0;j<container.columns.length;j++){
				 	  
				 	    if(container.columns[j].dataIndex== itemName){ //找到对应的columns,设置store的属性，以便在grid中能够对代码进行转换
                           
                            if(!container.comboLoads[j]){
                                 container.comboLoads[j] = true;
                            	 container.comboStores[j] = obj.store;
                            	 
                             }
                             break;
					    }
			 	    }
			    }*/
		 	    record.set(obj.getAttribute("itemName"),value);
	 	    }
 	 }
	var childBoms =  Ext.getDom("win_"+bomName).getAttribute("childBoms");
	if(childBoms!="")
	{
	   	var arrBoms = childBoms.split(",");
	   	record.boms=[];
	   	for(var i=0;i<arrBoms.length;i++){
	   	 
		   	var gridContainer = Ext.getDom("tab_grid_"+arrBoms[i]);
		   	var grid = gridContainer.grid;
		   	var store = grid.getStore();
		   	var fields = gridContainer.fields;
		   	
		   	var json = recordToJson(grid,fields,arrBoms[i]);
		   	record.boms[i] = arrBoms[i];
		   	record[arrBoms[i]] = json;
	
	   	}
	 
	    
	}
	record.endEdit();
	winContainer.add = false;
	win.hide();
  	
  }
  
  function fillValue(bomName,record){
  	// alert(record.get("botanical"));
  	// alert(record.bomInsurces);
  	 var objs = Ext.getDom("tab_"+bomName).getElementsByTagName("*");
  	 for(var i=0;i<objs.length;i++){
  	 	   var obj = objs[i];
  	 	   var itemName = obj.getAttribute("itemName");
  	 	   var value = record.get(itemName)
  	 	   var notNull = false;
  	 	   if(obj.dataType=="date")
  	 	   {
  	  	 	   if(value)
  	  	  	 	   notNull = true;
  	  	   }else{
      	 
  	          if(value!=undefined && value!=null){
  		          value = new String(value);
  		          if(value!="")
  		            notNull = true;
  	          }
  	          if(!notNull)
  	  	          value = "";
  	  	   }
  	 	   
  	 	   if( obj.getAttribute("bomItem")){
  	  	 	   if(obj.formField)
  	 		     obj.formField.setValue(value);
  	 	        
  	 	        if(obj.dataType=="date"){
  	  	 	        if(notNull)
  	  	 	            obj.value = value.format("Y-m-d H:i:s");
  	  	 	        else
  	  	  	 	        obj.value = "";    
  	 	        }else{
  	 	        	obj.value = value;
  	 	        }
  	 	        
  	 	   }
  	/* 	 if(!record.get(itemName) && obj.getAttribute("bomItem")){
	  	 	   if(obj.formField)
	 		     obj.formField.setValue(record.get(itemName));
	 	        
	 	        if(obj.dataType=="date"){
	  	 	        obj.value = "";
	 	        }else{
	 	        	obj.value = "";
	 	        }
	 	        
	 	   }*/
  	}
   	var childBoms = Ext.getDom("win_"+bomName).getAttribute("childBoms");
   	if(childBoms=="")
   	   	return;
   	var arrBoms = childBoms.split(",");
   	for(var i=0;i<arrBoms.length;i++){
   	 
	   	var gridContainer = Ext.getDom("tab_grid_"+arrBoms[i]);
	   	var grid = gridContainer.grid;
	   	var fields = gridContainer.fields;
	   	var store = grid.getStore();
	   	store.removeAll();
	  	if(!record[arrBoms[i]])
   	   	   	continue;

	   	var childRecords = record[arrBoms[i]];   
	   		
	  	for(var j=0;j<childRecords.length;j++){
		  	
		  	var childRecord = new  Ext.data.Record(childRecords[j]);
		  	for(var k =0;k<fields.length;k++){
                if(fields[k].type=="date" || childRecord.get(fields[k].name))
                    continue;
                if(fields[k].type=="float"){
                	
                	//childRecord.set(fields[k].name,0);
                }
                else
                	childRecord.set(fields[k].name,"");			  	
		  	}
		  	for(var name in childRecord.data ){
			  	if(childRecord.data[name].indexOf)
			  		childRecord[name] = childRecord.data[name];
		  	}
	  		store.add(childRecord);
	  	}
   	}
 
  }

    function recordToJson(childGrid,childFields,bomName){
  	 var store = childGrid.getStore();
   	 var json = [];
   	 for(var i=0;i<store.getCount();i++){
   	 	    var record = store.getAt(i);
   	 	    json[i] = {};
   	 	    for(var j=0;j<childFields.length;j++){
   	 	    	   
   	 	    	   if(childFields[j].type=="date"){
   	 	    	    	if(!record.get(childFields[j].name))
    	 	    	       continue;
   	 	    	       json[i][childFields[j].name] =record.get(childFields[j].name).format( 'Y-m-d  H:i:s');
   	 	    	   }
   	 	    	   else    {
   	   	 	    	    var value = record.get(childFields[j].name);
   	   	 	    	      	   	 	    	    
                        var notNull = false;
                  	 
                  	     if(value!=undefined && value!=null){
                  		    value = new String(value);
                  		    if(value!="")
                  		        notNull = true;
                  	    }
                   	    if(notNull){
                       	   // alert(value);
   	 	    	          	json[i][childFields[j].name] = ""+ value+"";
                   	    }
   	 	    	   }
   	 	    }
   	 	    if(record.boms){  //通过提交得到的
   	   	 	    for(var j=0;j<record.boms.length;j++)
   	   	   	 	    json[i][record.boms[j]] =  record[record.boms[j]];
   	 	    }else{
   	   	 	     var childBoms = Ext.getDom("win_"+bomName).getAttribute("childBoms");
   	          	 if(childBoms!="")
   	          	 {
   	             	var arrBoms = childBoms.split(",");
   	    	        for(var j=0;j<arrBoms.length;j++){
   	   	    	        json[i][arrBoms[j]]= makeEmptyBomJson(arrBoms[j]);
   	 	            }
   	          	 }
   	 	    }
    	}
 	    if(store.getCount()==0)
 	 	    json = makeEmptyBomJson(bomName);

    	return json;
   }

    function formatDate(value){
   
        return value ? value.dateFormat('Y-m-d H:i:s') : '';
    };
    
	</script>




</head>
<body>
<input id="jostr" name="jostr" type="hidden" />
<input id="javaClass" type="hidden" name="javaClass"
	value='<%=request.getParameter("javaClass")%>' />
<input id="method" type="hidden" name="method"
	value='<%=request.getParameter("method")%>' />
<input id="template" type="hidden" name="template"
	value='<%=request.getParameter("template")%>' />
<!-- 弹出窗口的html -->
<%=writeWinInfo%>
<!-- 左边的菜单栏 -->
<div id="TreeMenu">
<div class="wizardSteps"><%=writeStep%></div>
</div>

<div id="north">
<p>规则测试--输入完需要的数据后可以测试规则的有效性</p>
</div>

<div id="center2" style="overflow-y: scroll">

<div id="formStep1"><%=writeRuleInfo%></div>
<%=writeFormStepInfo%>

<br>
<div id="button" align="left"><!--<input class="cssButton" type="button"
	id="buttonPrev" value="上一步" onclick="gotoPrevStep()" title="进行到下一步">
<input class="cssButton" type="button" id="buttonNext" value="下一步"
	onclick="gotoNextStep()" title="进行到下一步"> <input class="cssButton" type="button"
	value="完 成" onclick="gotoLastStep();" title="完成输入并且进行测试">-->
    <a href="javascript:void(0);" id="buttonPrev" title="进行到下一步" class="button" onClick="gotoPrevStep();">上一步</a>
    <a href="javascript:void(0);" id="buttonNext" title="进行到下一步" class="button" onClick="gotoNextStep();">下一步</a>
    <a href="javascript:void(0);" class="button" title="完成输入并且进行测试" onClick="gotoLastStep();">完    成</a>
    
    </div>

</div>

</body>
</html>
