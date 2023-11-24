# assignment-1
upload data from a CSV file

Java multi-thread is used to parallelly read data and process data. 
And producer-consumer design pattern has been used to structure code.

LeadsBuffer Class: This represents the shared buffer where the producer adds 
leads, and the consumer removes leads. It uses explicit synchronization 
using wait() and notifyAll().

LeadProducer Class: Reads leads from the CSV file and produces them into 
the shared LeadsBuffer.

LeadConsumer Class: Consumes leads from the shared LeadsBuffer and 
processes them.

UploadController Class: Handles the web request for uploading leads. It 
creates a shared LeadsBuffer, starts a lead producer thread, 
and multiple lead consumer threads.


