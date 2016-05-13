package com.smkpgri2.alaska;


import android.app.Application;
import android.util.Log;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.path.android.jobqueue.JobManager;
import com.path.android.jobqueue.config.Configuration;


/**
 * Created by smkpgri2 on 13/05/16.
 */
public class AlaskaApplication extends Application {
    private static AlaskaApplication instance;
    private static ObjectMapper objectMapper;
    private ObjectMapper jsonMapper;
    private JobManager jobManager;


    public AlaskaApplication() {
        instance = this;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        Configuration configuration = new Configuration.Builder(this)
                .minConsumerCount(1) //always keep at least one consumer alive
                .maxConsumerCount(3) //up to 3 consumers at a time
                .loadFactor(3) //3 jobs per consumer
                .consumerKeepAlive(120) //wait 2 minute
                .build();

        jobManager = new JobManager(this, configuration);
        jsonMapper = objectMapper;

        Log.d(getClass().getName(), "onCreate");
    }


    public static AlaskaApplication getInstance() {
        return instance;
    }

    public static void setInstance(AlaskaApplication instance) {
        AlaskaApplication.instance = instance;
    }

    public static ObjectMapper getObjectMapper() {
        return objectMapper;
    }

    public static void setObjectMapper(ObjectMapper objectMapper) {
        AlaskaApplication.objectMapper = objectMapper;
    }

    public ObjectMapper getJsonMapper() {
        return jsonMapper;
    }

    public void setJsonMapper(ObjectMapper jsonMapper) {
        this.jsonMapper = jsonMapper;
    }

    public JobManager getJobManager() {
        return jobManager;
    }

    public void setJobManager(JobManager jobManager) {
        this.jobManager = jobManager;
    }
}
