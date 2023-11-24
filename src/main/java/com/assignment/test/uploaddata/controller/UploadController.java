package com.assignment.test.uploaddata.controller;

import com.assignment.test.uploaddata.designpattern.LeadConsumer;
import com.assignment.test.uploaddata.designpattern.LeadProducer;
import com.assignment.test.uploaddata.designpattern.LeadsBuffer;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

/**
 * Handles the web request for uploading data. It creates a shared
 * buffer, starts a lead producer thread, and multiple lead consumer threads.
 */
@RestController
@RequestMapping("/api")
public class UploadController {
    @PostMapping("/upload-data")
    public String uploadData(@RequestParam("file")MultipartFile file) throws IOException{
        if (file.getOriginalFilename() != null && file.getOriginalFilename().endsWith(".csv")){
            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()));
                LeadsBuffer leadsBuffer = new LeadsBuffer(100000);

                int numberOfConsumers = 10;
                Thread producerThread = new Thread(new LeadProducer(reader, leadsBuffer));
                Thread[] consumerThreads = new Thread[numberOfConsumers];

                //start lead producer thread
                producerThread.start();
                for (int i=0; i<numberOfConsumers; i++){
                    consumerThreads[i] = new Thread(new LeadConsumer(leadsBuffer));
                    //start consumer thread
                    consumerThreads[i].start();
                }

                //wait for the producer to finish
                try {
                    producerThread.join();
                } catch (InterruptedException e){
                    e.printStackTrace();
                }

                //wait for the consumer to finish
                Arrays.stream(consumerThreads).forEach(thread-> {
                    try {
                        thread.join();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                });

                return "Data uploaded successfully";

            } catch (IOException e){
                e.printStackTrace();
            }
        } else {
            return "Invalid file";
        }
        return null;
    }
}
