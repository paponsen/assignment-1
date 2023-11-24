package com.assignment.test.uploaddata.designpattern;

/**
 * Consumes data from the shared buffer and processes them.
 */
public class LeadConsumer implements Runnable{
    private final LeadsBuffer leadsBuffer;
    public LeadConsumer(LeadsBuffer leadsBuffer){
        this.leadsBuffer = leadsBuffer;
    }
    @Override
    public void run(){
        try {
           while (true){
               Lead lead = leadsBuffer.consume();
               processLead(lead);
           }
        } catch (InterruptedException e){
            Thread.currentThread().interrupt();
        }
    }
    private void processLead(Lead lead){
        System.out.println("Processing lead:"+lead.getData());
    }
}
