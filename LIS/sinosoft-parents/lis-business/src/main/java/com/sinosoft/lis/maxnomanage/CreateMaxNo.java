package com.sinosoft.lis.maxnomanage;

import java.util.Hashtable;

public interface CreateMaxNo {
//public abstract String getMaxNo(MaxNoElement maxNoElement);
//public abstract String getMaxNo(String cNoType,Hashtable tOthers);
//生成号段
public abstract String getMaxNo(String cNoType);
//获取号段预览
public abstract String getPreviewMaxNo(String cNoType);

//设置号段生成需要的管理机构
public abstract void setManageCom(String tManageCom);
}
