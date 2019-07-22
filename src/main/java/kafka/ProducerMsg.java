package kafka;

import org.apache.commons.cli.*;

import java.util.Properties;

/**
 * Created by sponge on 2017/3/25 0025.
 */
public class ProducerMsg {

    private static String brokers;
    private static String groupId;
    private static String securityProtocol;


    public static void main(String[] args) {

        Options opts = new Options();
        opts.addOption("t",  true, "Topic name");
        opts.addOption("b",  true, "bootstrap.servers");
        opts.addOption("g",  true, "group.id");
        opts.addOption("s",  true, "security.protocol");
        opts.addOption("n",  true, "Numbers events");
        opts.addOption("h", false, "Help message");
        CommandLineParser parser = new DefaultParser();
        CommandLine cl;
        try {
            cl = parser.parse(opts, args);
            if (cl.getOptions().length > 0 ) {
                if (cl.hasOption('h')) {
                    HelpFormatter hf = new HelpFormatter();
                    hf.printHelp("May Options", opts);
                } else {
                    String topic = cl.getOptionValue("t");
                    String number = cl.getOptionValue("n");

                    brokers = cl.getOptionValue("b");
                    groupId = cl.getOptionValue("g", "consumer");
                    securityProtocol = cl.getOptionValue("s", "PLAINTEXT");


                    if (topic == null || brokers == null ) {
                        System.out.println("Please input -t -b ");
                        return;
                    }

                    Properties props = new Properties();
                    props.put(org.apache.kafka.clients.consumer.ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, brokers);
                    props.put(org.apache.kafka.clients.consumer.ConsumerConfig.GROUP_ID_CONFIG, groupId);
                    props.put("security.protocol", securityProtocol);

                    ProducerThread producer2 = new ProducerThread(topic, props, Integer.parseInt(number));
                    producer2.start();

                }
            } else {
                HelpFormatter hf = new HelpFormatter();
                hf.printHelp("May Options", opts);
            }
        } catch (ParseException e) {
            e.printStackTrace();
            HelpFormatter hf = new HelpFormatter();
            hf.printHelp("May Options", opts);
        }
    }
}
