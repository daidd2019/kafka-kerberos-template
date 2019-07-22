package kafka;


import org.apache.commons.cli.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;


/**
 * Created by sponge on 2017/3/25 0025.
 */
public class ConsumerMsg {

    private static String topic;
    private static String brokers;
    private static String groupId;
    private static String securityProtocol;
    private static int number;

    public static void main(String[] args) {

        Options opts = new Options();
        opts.addOption("t",  true, "Topic name");
        opts.addOption("b",  true, "bootstrap.servers");
        opts.addOption("g",  true, "group.id");
        opts.addOption("s",  true, "security.protocol");
        opts.addOption("n",  true, "threads");
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
                    topic = cl.getOptionValue("t");
                    brokers = cl.getOptionValue("b");
                    groupId = cl.getOptionValue("g", "consumer");
                    securityProtocol = cl.getOptionValue("s", "PLAINTEXT");
                    number = Integer.parseInt(cl.getOptionValue("n", "1"));

                    if (topic == null || brokers == null ) {
                        System.out.println("Please input -t -b ");
                        return;
                    }
                    consumerMsg();
                }
            } else {
                HelpFormatter hf = new HelpFormatter();
                hf.printHelp("May Options", opts);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        catch (ParseException e) {
            e.printStackTrace();
            HelpFormatter hf = new HelpFormatter();
            hf.printHelp("May Options", opts);
        }
    }
    private static void consumerMsg() throws InterruptedException {

        // consumer message
        Properties props = new Properties();
        props.put(org.apache.kafka.clients.consumer.ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, brokers);
        props.put(org.apache.kafka.clients.consumer.ConsumerConfig.GROUP_ID_CONFIG, groupId);
        props.put("security.protocol", securityProtocol);
        List<ConsumerThread> lst = new ArrayList<>();

        System.out.println("broker: " + brokers);
        System.out.println("topic: " + topic);
        System.out.println("groupid: " + groupId);
        System.out.println("security.protocol: " + securityProtocol);
        System.out.println("threads : " + number);

        for(int i = 0; i < number; i++) {
            ConsumerThread thrd = new ConsumerThread(topic, props, "consumter-" + i);
            thrd.start();
            lst.add(thrd);
        }

        for (ConsumerThread thrd : lst) {
            Thread.sleep(20000);
            System.out.println("Interrupt " + thrd.getName());
            thrd.interrupt();
        }
    }
}