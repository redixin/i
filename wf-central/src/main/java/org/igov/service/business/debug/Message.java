package org.igov.service.business.debug;

public interface Message {
    public Message addsHead(String sHead);
    public Message addsBody(String sBody);
    public Message addsError(String sError);
    public Message addnID_Subject(Long nID_Subject);
    public Message addnID_Server(Long nID_Server);   
    public Message addsmData(String smData);   
    public String save();
}
