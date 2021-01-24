import org.apache.commons.lang3.StringUtils;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.consumer.listener.MessageListenerOrderly;
import org.apache.rocketmq.common.protocol.heartbeat.MessageModel;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @author zhangchangyong
 * @date 2020-12-16 18:11
 */
public class DefaultRMQPushConsumer extends DefaultMQPushConsumer implements FactoryBean<DefaultMQPushConsumer>, InitializingBean, DisposableBean {

    private DefaultMQPushConsumer consumer = new DefaultMQPushConsumer();
    private Map<String, String> topics = new HashMap<String, String>();

    public DefaultRMQPushConsumer() {
    }

    @Override
    public DefaultMQPushConsumer getObject() throws Exception {
        return this;
    }

    @Override
    public Class<?> getObjectType() {
        return DefaultRMQPushConsumer.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        String group = System.getenv("GROUP");
        boolean grayFlag = false;
        if (StringUtils.isNotBlank(group)) {
            grayFlag = true;
        }
        // 设置集群消费模式
        consumer.setMessageModel(MessageModel.CLUSTERING);
        this.consumer.setNamesrvAddr(this.getNamesrvAddr());
        if (grayFlag) {
            this.consumer.setConsumerGroup(this.getConsumerGroup() + "_" + group);
        } else {
            this.consumer.setConsumerGroup(this.getConsumerGroup());
        }

        Assert.hasText(this.getNamesrvAddr(), "[Assertion failed] - namesrvAddr must have text; it must not be null, empty, or blank");
        Assert.hasText(this.getConsumerGroup(), "[Assertion failed] - consumerGroup must have text; it must not be null, empty, or blank");
        Assert.notEmpty(this.topics, "[Assertion failed] - topics must have text; it must not be null, empty, or blank");
        Assert.notNull(this.getMessageListener(), "[Assertion failed] - messageListener must have text; it must not be null, empty, or blank");
        this.consumer.setAdjustThreadPoolNumsThreshold(this.getAdjustThreadPoolNumsThreshold());
        this.consumer.setAllocateMessageQueueStrategy(this.getAllocateMessageQueueStrategy());
        this.consumer.setClientCallbackExecutorThreads(this.getClientCallbackExecutorThreads());
        this.consumer.setClientIP(this.getClientIP());
        this.consumer.setConsumeConcurrentlyMaxSpan(this.getConsumeConcurrentlyMaxSpan());
        this.consumer.setConsumeFromWhere(this.getConsumeFromWhere());
        this.consumer.setConsumeMessageBatchMaxSize(this.getConsumeMessageBatchMaxSize());
        this.consumer.setConsumeThreadMax(this.getConsumeThreadMax());
        this.consumer.setConsumeThreadMin(this.getConsumeThreadMin());
        this.consumer.setConsumeTimestamp(this.getConsumeTimestamp());
        this.consumer.setHeartbeatBrokerInterval(this.getHeartbeatBrokerInterval());
        this.consumer.setInstanceName(this.getInstanceName());
        this.consumer.setMessageListener(this.getMessageListener());
        this.consumer.setMessageModel(this.getMessageModel());
        this.consumer.setOffsetStore(this.getOffsetStore());
        this.consumer.setPersistConsumerOffsetInterval(this.getPersistConsumerOffsetInterval());
        this.consumer.setPollNameServerInterval(this.getPollNameServerInterval());
        this.consumer.setPostSubscriptionWhenPull(this.isPostSubscriptionWhenPull());
        this.consumer.setPullBatchSize(this.getPullBatchSize());
        this.consumer.setPullInterval(this.getPullInterval());
        this.consumer.setPullThresholdForQueue(this.getPullThresholdForQueue());
        this.consumer.setSubscription(this.getSubscription());
        this.consumer.setUnitMode(this.isUnitMode());
        this.consumer.setVipChannelEnabled(false);
        Iterator var3 = this.topics.keySet().iterator();

        while(var3.hasNext()) {
            String topic = (String)var3.next();
            if (grayFlag) {
                this.consumer.subscribe(topic + "_" + group, (String)this.topics.get(topic));
            } else {
                this.consumer.subscribe(topic, (String)this.topics.get(topic));
            }
        }

        if (this.getMessageListener() instanceof MessageListenerConcurrently) {
            this.consumer.registerMessageListener((MessageListenerConcurrently)this.getMessageListener());
        } else {
            this.consumer.registerMessageListener((MessageListenerOrderly)this.getMessageListener());
        }

        this.consumer.start();
    }

    @Override
    public void destroy() throws Exception {
        consumer.shutdown();
    }
}
