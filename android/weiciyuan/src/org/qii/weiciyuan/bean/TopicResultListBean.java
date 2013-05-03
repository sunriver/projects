package org.qii.weiciyuan.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * User: qii
 * Date: 12-9-26
 */
public class TopicResultListBean extends ListBean<MessageBean,TopicResultListBean> {


    private List<MessageBean> statuses=new ArrayList<MessageBean>();

    @Override
    public int getSize() {
        return statuses.size();
    }


    @Override
    public MessageBean getItem(int position) {
        return statuses.get(position);
    }

    @Override
    public List<MessageBean> getItemList() {
        return statuses;
    }


    public List<MessageBean> getStatuses() {
        return statuses;
    }

    public void setStatuses(List<MessageBean> statuses) {
        this.statuses = statuses;
    }

    @Override
    public void addNewData(TopicResultListBean newValue) {
        if (newValue != null && newValue.getSize() > 0) {

            this.getItemList().clear();
            this.getItemList().addAll(newValue.getItemList());
            this.setTotal_number(newValue.getTotal_number());


        }
    }
    @Override
    public void addOldData(TopicResultListBean oldValue) {
        if (oldValue != null && oldValue.getSize() > 0) {
            getItemList().addAll(oldValue.getItemList());
            setTotal_number(oldValue.getTotal_number());

        }
    }
}
