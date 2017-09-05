package com.zhaoxiang.rocketmq.batch;

import org.apache.rocketmq.common.message.Message;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Author: RiversLau
 * Date: 2017/9/5 11:50
 */
public class ListSplitter implements Iterator<List<Message>> {

    private final int  SIZE_LIMIT = 1000 * 1000;
    private final List<Message> messages;
    private int currentIndex;

    public ListSplitter(List<Message> messages) {
        this.messages = messages;
    }

    public boolean hasNext() {

        return currentIndex < messages.size();
    }

    public List<Message> next() {

        int nextIndex = currentIndex;
        int totalSize = 0;
        for (; nextIndex < messages.size(); nextIndex++) {
            Message message = messages.get(nextIndex);
            int tmpSize = message.getTopic().length() + message.getBody().length;
            Map<String, String> properties = message.getProperties();
            for (Map.Entry<String, String> entry : properties.entrySet()) {
                tmpSize += entry.getKey().length() + entry.getValue().length();
            }
            tmpSize += 20;
            if (tmpSize > SIZE_LIMIT) {
                if (nextIndex - currentIndex == 0) {
                    nextIndex++;
                }
                break;
            }
            if (tmpSize + totalSize > SIZE_LIMIT) {
                break;
            } else {
                totalSize += tmpSize;
            }
        }

        List<Message> subList = messages.subList(currentIndex, nextIndex);
        currentIndex = nextIndex;
        return subList;
    }

    public void remove() {
        // do nothing
    }
}
