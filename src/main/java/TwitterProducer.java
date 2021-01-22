import com.google.common.collect.Lists;
//import com.sun.org.slf4j.internal.Logger;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
//import com.sun.org.slf4j.internal.LoggerFactory;
import org.slf4j.LoggerFactory;
import com.twitter.hbc.ClientBuilder;
import com.twitter.hbc.core.Client;
import com.twitter.hbc.core.Constants;
import com.twitter.hbc.core.Hosts;
import com.twitter.hbc.core.HttpHosts;
import com.twitter.hbc.core.endpoint.StatusesFilterEndpoint;
import com.twitter.hbc.core.processor.StringDelimitedProcessor;
import com.twitter.hbc.httpclient.auth.Authentication;
import com.twitter.hbc.httpclient.auth.OAuth1;

import java.util.List;
import java.util.Properties;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import static com.twitter.hbc.core.Client.*;

public class TwitterProducer {

    Logger logger = LoggerFactory.getLogger(TwitterProducer.class.getName());

    String ConsumerKey = "d8zfFrGSCEfyoVjxEtyyfw5li";
    String consumerSecret = "mKLsIbcTFzMVwdARn3DAyQ80lcJAvvbjvm4MKMgmoN6XfkrV90";
    //Bearer Token : AAAAAAAAAAAAAAAAAAAAAH6zLwEAAAAAN6kJhEtYNfcrjmiCX03nHX0HoCY%3DVrYx8tszxIF2q604lH5Pcf4PQtzCUX8eDLi7IeYZ856cLoTAuV
    String token = "1351520296055025665-bmuhYULhMD9rGkz2NARC8GdVrQpX0Y";
    String secret = "NTnZBtzXq98C9HutemZ2eXNdmu2p96mtUH0cNI4T6gb6D";

    public TwitterProducer() {}

    public static void main (String[]args){
        new TwitterProducer().run();
        }

        public void run () {

                logger.info("Setup");
                //** Set up your blocking queues: Be sure to size these properly based on expected TPS of your stream */
                BlockingQueue<String> msgQueue = new LinkedBlockingQueue<String>(1000);

                //create twitter client
                Client client=createTwitterClient(msgQueue);
                // Attempts to establish a connection.
                client.connect();


                //create a kafka producer
            KafkaProducer<String, String> = new createKafkaProducer;



                // loop to send tweets to kafka
                while (!client.isDone()) {
                    String msg = null;
                    try {
                        msg = msgQueue.poll(5, TimeUnit.SECONDS);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        client.stop();

                    }
                    if (msg != null){
                        logger.info(msg);
                        producer.send(new ProducerRecord<>)
                    }
                }
                logger.info("End of application");
            }

            public Client createTwitterClient(BlockingQueue<String> msgQueue){

                //Block/** Declare the host you want to connect to, the endpoint, and authentication (basic auth or oauth) */
                Hosts hosebirdHosts = new HttpHosts(Constants.STREAM_HOST);
                StatusesFilterEndpoint hosebirdEndpoint = new StatusesFilterEndpoint();

                // Optional: set up some followings and track terms
                // List<Long> followings = Lists.newArrayList(1234L, 566788L);
                List<String> terms = Lists.newArrayList("bitcoin");
                hosebirdEndpoint.trackTerms(terms);

                // These secrets should be read from a config file
                Authentication hosebirdAuth = new OAuth1(ConsumerKey, consumerSecret, token, secret);

                ClientBuilder builder = new ClientBuilder()
                        .name("Hosebird-Client-01")                              // optional: mainly for the logs
                        .hosts(hosebirdHosts)
                        .authentication(hosebirdAuth)
                        .endpoint(hosebirdEndpoint)
                        .processor(new StringDelimitedProcessor(msgQueue));

                Client hosebirdClient = builder.build();
                return hosebirdClient;



    } public KafkaProducer<String, String> createKafkaProducer(){

        String bootstrapServers = "127.0.0.1:9092" ;//create properties
        Properties properties  = new Properties();
        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        //create the new producer
        KafkaProducer<String, String> producer = new KafkaProducer<String, String>(properties);
        return producer;

            }





}
