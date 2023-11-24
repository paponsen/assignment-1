package com.assignment.test.uploaddata.designpattern;
import java.io.BufferedReader;
import java.io.IOException;

/**
 * Reads data from the CSV file and produces them into the shared buffer
 */

public class LeadProducer implements Runnable{
    private final BufferedReader reader;
    private final LeadsBuffer leadsBuffer;

    public LeadProducer(BufferedReader reader, LeadsBuffer leadsBuffer){
        this.reader = reader;
        this.leadsBuffer=leadsBuffer;
    }

    @Override
    public void run(){
        try{
            String line;
            while ((line = reader.readLine()) != null){
                Lead lead = new Lead(line);
                leadsBuffer.produce(lead);
            }
        } catch (IOException | InterruptedException e){
            e.printStackTrace();;
        }
    }
}
