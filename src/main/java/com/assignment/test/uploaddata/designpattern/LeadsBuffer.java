package com.assignment.test.uploaddata.designpattern;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents the shared buffer where the producer adds
 * data, and the consumer removes data.
 */
public class LeadsBuffer {
    private final List<Lead> leads;
    private final int capacity;
    public LeadsBuffer(int capacity){
        this.capacity = capacity;
        this.leads  = new ArrayList<>();
    }
    public synchronized void produce(Lead lead) throws  InterruptedException{
        while (leads.size() == capacity){
            wait();
        }
        leads.add(lead);
        notifyAll();
    }
    public synchronized Lead consume() throws InterruptedException{
        while (leads.isEmpty()){
            wait();
        }
        Lead lead = leads.remove(0);
        notifyAll();
        return lead;
    }
}
