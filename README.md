kafka read
===========================
### 编译
    mvn package

### 执行
    解压后到 bin 目录下面执行
    kinit -kt /etc/security/keytabs/kafka.service.keytab kafka/dn02.hadoop.citic@HADOOP.CITIC
    cd /usr/hdp/current/kafka-broker
    ./bin/kafka-topics.sh --zookeeper ck1.centos74.com:2181,ck2.centos74.com:2181,ck3.centos74.com:2181 --create --topic topic1 --partitions 3 --replication-factor 2
    ./bin/kafka-topics.sh --zookeeper ck1.centos74.com:2181,ck2.centos74.com:2181,ck3.centos74.com:2181  --describe --topic topic1
    
    
### 程序解释
    run_send_msg.sh 发送-n 条数据到-t
    run_read_msg.sh 读取数据 (-n 表示读取的线程数量，每隔20秒会停止一个线程)
    
    在读取启用kerberos的kafka，需要配置kafka_client_jaas_key.conf 文件
    
