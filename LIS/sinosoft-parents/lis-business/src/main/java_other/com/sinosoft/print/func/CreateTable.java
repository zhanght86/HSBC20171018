package com.sinosoft.print.func;
import org.apache.log4j.Logger;

import java.util.ArrayList;

import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;

/**
 * 
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * ClassName: CreateTable
 * </p>
 * <p>
 * Description: 根据配置信息生成测试页面
 * </p>
 * @author guoxiang
 * @version 1.0
 * @modify 2011-3-21
 * @depict 人生五十載，去事恍如夢幻，普天之下，豈有長生不滅者！
 */
public class CreateTable
{
private static Logger logger = Logger.getLogger(CreateTable.class);
	
	/**
	 * 生成Table方法，把xml标签生成Table
	 * @param cTempleteID 
	 */
	public String buildTable(String cTempleteID)
	{
		String tTable = "";
		ArrayList tOne = new ArrayList();//存放一层标签
		ArrayList tMul = new ArrayList();//存放两层或三层标签
		ArrayList tBar = new ArrayList();//存放条形码对象标签
		ArrayList tFil = new ArrayList();//存放文件合并的标签
		ArrayList tDis = new ArrayList();//存放展现控制的标签
		String tPaNode = "";//父节点
		ExeSQL tExe = new ExeSQL();
		String tSql = "select distinct a.Description,a.Xmllevel from Lprtxmlstyle  a where a.templeteid  ='?cTempleteID?' order by a.Xmllevel , a.Description";
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		sqlbv.sql(tSql);
		sqlbv.put("cTempleteID", cTempleteID);
		SSRS tResult = tExe.execSQL(sqlbv);
		if (tResult != null && tResult.getMaxRow() > 0)
		{
			for(int i = 1 ;i<= tResult.getMaxRow();i++)
			{
				//如果标签为一层
		        if(tResult.GetText(i, 2).equals("1"))
		        {
		        	tOne.add(tResult.GetText(i, 1));
		        }
				//如果标签为六层 新增2011-8-23
		        else if(tResult.GetText(i, 2).equals("6"))
		        {
		        	tDis.add(tResult.GetText(i, 1));
		        }
		        //如果标签为两层或三层
		        else if(tResult.GetText(i, 2).equals("2")||tResult.GetText(i, 2).equals("3"))
		        {
		        	//把标签以"/"拆分成字符串
		        	String tempResult = tResult.GetText(i, 1);
		        	String[] tNode = tempResult.substring(1).split("/");
		        	if(tPaNode.equals(""))
		        	{
		        		//第一个父节点
		        		tPaNode = tNode[0];
		        		tMul = new ArrayList();
		        		tMul.add(tempResult);
		        	}
		        	else if(!tPaNode.equals(tNode[0]))
		        	{
		        		//把集合第一个标签以"/"拆分成字符串
			        	String[] tThrNode =((String)tMul.get(0)).substring(1).split("/");
						if(tThrNode.length == 3)
						{
							tTable =tTable +"<table><tr><td class=common><img src=../common/images/butExpand.gif style=cursor:hand>\n</td><td class=titleImg>"+tPaNode+"标签</td><td><input type=button class=cssbutton onclick=clickx('"+tThrNode[0]+"','"+tThrNode[1]+"'); value=添加"+tPaNode+"标签></td></tr></table>";
						}
						else
						{
							tTable =tTable +"<table><tr><td class=common><img src=../common/images/butExpand.gif style=cursor:hand>\n</td><td class=titleImg>"+tPaNode+"标签</td></tr></table>";
						}
						//新父节点
		        		tPaNode = tNode[0];
		        		if(tMul.size()>0)
		        		{
		        			tTable =tTable + buildTags(tMul);
		        		}
		        		//清空缓存
	    				tMul = new ArrayList();
		        		//增加新的孩子
	    				tMul.add(tempResult);
		        	}
		        	else
		        	{
		        		//增加新的孩子
		        		tMul.add(tempResult);
		        	}
		        }
		        //如果为条形码标签
		        else if(tResult.GetText(i, 2).equals("4"))
		        {
		        	tBar.add(tResult.GetText(i, 1));
		        }
		        //如果为文件合并标签
		        else if(tResult.GetText(i, 2).equals("5"))
		        {
		        	tFil.add(tResult.GetText(i, 1));
		        }
			}
			//如果存放多层标签的集合还有缓存就做如下处理
			if(tMul.size()>0)
    		{
				//把集合第一个标签以"/"拆分成字符串
	        	String[] tNode =((String)tMul.get(0)).substring(1).split("/");
				if(tNode.length == 3)
				{
					tTable =tTable +"<table><tr><td class=common><img src=../common/images/butExpand.gif style=cursor:hand>\n</td><td class=titleImg>"+tPaNode+"标签</td><td><input type=button class=cssbutton onclick=clickx('"+tNode[0]+"','"+tNode[1]+"'); value=添加"+tPaNode+"标签></td></tr></table>";
				}
				else
				{
					tTable =tTable +"<table><tr><td class=common><img src=../common/images/butExpand.gif style=cursor:hand>\n</td><td class=titleImg>"+tPaNode+"标签</td></tr></table>";
				}
				tTable =tTable + buildTags(tMul);
    		}
			//生成table的所有一层标签，每行有三列
			if(tOne.size()>0)
			{
				tTable =tTable +"<table><tr><td class=common><img src=../common/images/butExpand.gif style=cursor:hand>\n</td><td class=titleImg>单节点标签</td></tr></table>";
				tTable =tTable + buildTags(tOne);
			}
			//如果条形码标签存在做如下处理
			if(tBar.size()>0)
    		{
				tTable =tTable +"<table><tr><td class=common><img src=../common/images/butExpand.gif style=cursor:hand>\n</td><td class=titleImg>条形码标签</td></tr></table>";
    			tTable =tTable + buildTags(tBar);
    		}
			//如果文件合并的标签存在做如下处理
			if(tFil.size()>0)
    		{
				tTable =tTable +"<table><tr><td class=common><img src=../common/images/butExpand.gif style=cursor:hand>\n</td><td class=titleImg>文件合并标签</td></tr></table>";
    			tTable =tTable + buildTags(tFil);
    		}
			//如果展现控制的标签存在做如下处理
			if(tDis.size()>0)
    		{
				tTable =tTable +"<table><tr><td class=common><img src=../common/images/butExpand.gif style=cursor:hand>\n</td><td class=titleImg>展现控制标签</td></tr></table>";
    			tTable =tTable + buildTags(tDis);
    		}
		}
		logger.debug("tTable"+tTable);
		return tTable;
		
	}
	/**
	 * 生成标签方法
	 * @param cArr
	 */
	private String buildTags(ArrayList cArr)
	{
		ArrayList tArr = cArr;
		String tTableTag ="";
		//把集合里第一个元素截掉第一个字符后，再以"/"拆分成字符串数组
		String[] tCol = ((String)tArr.get(0)).substring(1).split("/");
		if(tCol.length == 3)
		{
			tTableTag = "<span id="+tCol[0]+" name="+tCol[0]+"><table class=common id="+tCol[1]+">";
		}
		else
		{
			tTableTag = "<table class=common >";
		}
		int count = 0;//列计数器
		String tInfo ="";//标签内容
		String tName ="";//标签名
		//生成每个列,如"td>AppntSex</td><td><input name =/AppntSex></td>"为一个列
		for(int j =1 ;j<=tArr.size();j++)
		{
			//如果列数为0，就加上一个行开始符"<tr>"
			if(count == 0)
        	{
				tTableTag = tTableTag + "<tr>";
        	}
			//标签内容
			tInfo = ((String)tArr.get(j-1));
			//如果是条形码标签
			if(tInfo.indexOf("#")==0)
			{
				String[] tBar = tInfo.substring(1).split("#");
				tName = tBar[0];
				tTableTag = tTableTag+"<td class=title>"+ tName+"</td><td class=input><input class=common name ="+tInfo+"></td>";
			}
			//如果是文件合并的标签
			else if(tInfo.indexOf("@")==0)
			{
				String[] tCom = tInfo.substring(1).split("-");
				tName = tCom[0];
				tTableTag = tTableTag+"<td class=title>"+tCom[1]+"</td><td class=input><input class=common name ="+tInfo+" value ="+tName+" ></td>";
			}
			else
			{
				tName = tInfo.substring(tInfo.lastIndexOf("/")+1);
	        	//标签名
				if(tInfo.indexOf("|") >=0)
				{
					tInfo = tInfo.substring(0,tInfo.indexOf("|"));
				}
				tTableTag = tTableTag+"<td class=title>"+ tName+"</td><td class=input><input class=common name ="+tInfo+"></td>";
			}
        	
        	count++;
        	//如果列数为3，就加上一个行结束符"</tr>"
        	if(count == 3)
        	{
        		tTableTag = tTableTag + "</tr>";
        		count =0;
        	}
		}
//		//如果是条形码标签,需要增加一个隐藏的input，值为条形码数
//		if(tInfo.indexOf("#")==0)
//		{
//			tTableTag = tTableTag+"<input type =hidden name =BarCodeCount"+" value ="+tArr.size()+" >";
//		}
//		//如果是文件合并的标签,需要增加一个隐藏的input，值为合并文件数
//		else if(tInfo.indexOf("@")==0)
//		{
//			tTableTag = tTableTag+"<input type =hidden name =FileCount"+" value ="+tArr.size()+">";
//		}
		//如果最后一行列数不为0，则补上空列，再加上一个行结束符"</tr>"
		if(count!=0)
		{
			for(int i = 1;i<=3-count;i++)
			{
				tTableTag = tTableTag + "<td class= title></td><td class= input></td>";
			}
			tTableTag = tTableTag + "</tr>";
		}
		tTableTag = tTableTag +"</table>";
		//如果拆分后的字符数组长度为3，即它是三层标签，需要增加一个"</span>"
		if(tCol.length == 3)
		{
			tTableTag = tTableTag +"</span>";
		}
		return tTableTag;
	}
	public static void main(String[] args)
	{
	      CreateTable tC = new CreateTable();
	      logger.debug(tC.buildTable("000001"));
	}
}
