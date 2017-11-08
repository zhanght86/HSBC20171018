package com.sinosoft.easyscan5.core.service.clientupload;

import com.sinosoft.easyscan5.base.service.BaseService;
import com.sinosoft.easyscan5.core.vo.easyscan.UploadIssuePrepareVo;


public interface UploadPrepareIssueService extends BaseService{

	public String uploadIssuePrepare(UploadIssuePrepareVo uploadIssuePrepareVo) throws Exception;
	public String[] getServerUrl();
}
