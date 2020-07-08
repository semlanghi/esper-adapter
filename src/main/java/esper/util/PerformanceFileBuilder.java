package esper.util;

import com.opencsv.CSVWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class PerformanceFileBuilder {

    private CSVWriter writer;
    private String platform;
    private int parallelism;

    public PerformanceFileBuilder(String fileName, String platform, int parallelism) {
        try {
            File file = new File(fileName);
            if(!file.exists()){
                this.writer = new CSVWriter(new FileWriter(file, true));
                String[] firstRow = new String[]{"Type", "Experiment-Name","Parallelism", "Platform", "Throughput", "OnCluster"};
                this.writer.writeNext(firstRow);
            }
            this.writer = new CSVWriter(new FileWriter(file, true));
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.platform = platform;
        this.parallelism = parallelism;
    }

    public void register(String expType, double throughput, String expName, boolean cluster, long inputSize){
        String[] row = new String[]{expType, expName, String.valueOf(parallelism), platform, String.valueOf(throughput), String.valueOf(cluster), String.valueOf(inputSize)};
        writer.writeNext(row);
        try {
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void close(){
        try {
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}