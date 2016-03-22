package org.igov.service.business.debug;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.igov.util.JSON.JsonRestUtils;

import com.pb.ksv.msgcore.data.MAttrs;
import com.pb.ksv.msgcore.data.MsgAttr;
import com.pb.ksv.msgcore.user.Msg;

public class MessageImpl implements Message {
    private String sHead = null;
    private String sBody = null;
    private String sError = null;
    private String sKey = null;
    private Long nID_Subject = null;
    private Long nID_Server = null;

    private String smData = null;
    private List<String> asParam = null;
    private String sDate = null;

    private String smDataMisc = null;

    public MessageImpl(String sType, String sFunction) {
	if (sType == null || sFunction == null) {
	    throw new IllegalArgumentException("Constructor parameters: sType=" + sType + ", sFunction=" + sFunction);
	}
	this.sKey = sType + sFunction;
    }

    public Message addsHead(String sHead) {
	this.sHead = sHead;
	return this;
    }

    public Message addsBody(String sBody) {
	this.sBody = sBody;
	return this;
    }

    public Message addsError(String sError) {
	this.sError = sError;
	return this;
    }

    public Message addnID_Subject(Long nID_Subject) {
	this.nID_Subject = nID_Subject;
	return this;
    }

    public Message addnID_Server(Long nID_Server) {
	this.nID_Server = nID_Server;
	return this;
    }

    @SuppressWarnings("unchecked")
    public Message addsmData(String smData) {
	String sResponseMessage = null;
	String sResponseCode = null;
	String soResponseData = null;
	StringBuffer smDataMisc = new StringBuffer();
	if (smData != null) {
	    asParam = new LinkedList<String>();

	    Map<String, Object> moData = JsonRestUtils.readObject(smData, Map.class);
	    if (moData != null) {
		if (moData.containsKey("asParam")) {
		    asParam = (List<String>) moData.get("asParam");
		}
		if (moData.containsKey("oResponse")) {
		    Map<String, Object> moResponse = (Map<String, Object>) moData.get("oResponse");
		    if (moResponse != null) {
			if (moResponse.containsKey("sMessage")) {
			    sResponseMessage = (String) moResponse.get("sMessage");
			    smDataMisc.append(sResponseMessage);
			}
			if (moResponse.containsKey("sCode")) {
			    sResponseCode = (String) moResponse.get("sCode");
			    smDataMisc.append(sResponseCode);
			}
			if (moResponse.containsKey("soData")) {
			    soResponseData = (String) moResponse.get("soData");
			    smDataMisc.append(soResponseData);
			}
			this.smDataMisc = smDataMisc.toString();
		    }
		}
		if (moData.containsKey("sDate")) {
		    sDate = (String) moData.get("sDate");
		}
	    }
	}

	this.smData = smData;
	return this;
    }

    @Override
    public String save() {
	String clientIP = null;
	String id = null;

	MAttrs mAttrs = new MAttrs();

	addAttr(mAttrs, "sHead", sHead);
	addAttr(mAttrs, "sBody", sBody);
//	addAttr(mAttrs, "sError", sError);
	addAttr(mAttrs, "sKey", sKey);
	addAttr(mAttrs, "nID_Subject", nID_Subject);
	addAttr(mAttrs, "nID_Server", nID_Server);
	addAttr(mAttrs, "smData", smData);
	addAttr(mAttrs, "sDate", sDate);

	if (asParam != null) {
	    StringBuffer asParamSb = new StringBuffer();
	    for (String param : asParam) {
		asParamSb.append(param);
	    }
	    addAttr(mAttrs, "asParam", asParamSb.toString());
	}

	addAttr(mAttrs, "smDataMisc", smDataMisc);
	
	return Msg.store(id, clientIP, sError, mAttrs);
    }

    private void addAttr(MAttrs mAttrs, String title, Object o) {
	if (o != null) {
	    mAttrs.add(title, o.toString());
	}
    }

}
