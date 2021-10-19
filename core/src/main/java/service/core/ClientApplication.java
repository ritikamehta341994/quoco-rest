package service.core;

import java.util.ArrayList;

public class ClientApplication {

    private int applicationNumber;
    private ClientInfo clientInfo;
    private ArrayList<Quotation> quotations;

    public ClientApplication(){}

    public int getApplicationNumber() {
        return applicationNumber;
    }

    public void setApplicationNumber(int applicationNumber) {
        this.applicationNumber = applicationNumber;
    }

    public ClientInfo getClientInfo() {
        return clientInfo;
    }

    public void setClientInfo(ClientInfo clientInfo) {
        this.clientInfo = clientInfo;
    }

    public ArrayList<Quotation> getQuotations() {
        return quotations;
    }

    public void setQuotations(ArrayList<Quotation> quotations) {
        this.quotations = quotations;
    }
}
