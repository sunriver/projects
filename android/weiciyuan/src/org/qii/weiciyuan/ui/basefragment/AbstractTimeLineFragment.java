package org.qii.weiciyuan.ui.basefragment;

import android.os.Bundle;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.*;
import org.qii.weiciyuan.R;
import org.qii.weiciyuan.bean.ItemBean;
import org.qii.weiciyuan.bean.ListBean;
import org.qii.weiciyuan.support.asyncdrawable.TimeLineBitmapDownloader;
import org.qii.weiciyuan.support.error.WeiboException;
import org.qii.weiciyuan.support.lib.MyAsyncTask;
import org.qii.weiciyuan.support.lib.pulltorefresh.PullToRefreshBase;
import org.qii.weiciyuan.support.lib.pulltorefresh.PullToRefreshListView;
import org.qii.weiciyuan.support.settinghelper.SettingUtility;
import org.qii.weiciyuan.support.utils.Utility;
import org.qii.weiciyuan.ui.interfaces.AbstractAppActivity;
import org.qii.weiciyuan.ui.interfaces.AbstractAppFragment;

/**
 * User: qii
 * Date: 12-8-27
 */
public abstract class AbstractTimeLineFragment<T extends ListBean> extends AbstractAppFragment {


    protected PullToRefreshListView pullToRefreshListView;
    protected TextView empty;
    protected ProgressBar progressBar;

    protected BaseAdapter timeLineAdapter;

    protected View footerView;
    protected TimeLineBitmapDownloader commander;

    protected TimeLineGetNewMsgListTask newTask;
    protected TimeLineGetOlderMsgListTask oldTask;
    protected TimeLineGetMiddleMsgListTask middleTask;

    protected ActionMode mActionMode;

    public abstract T getList();

    public PullToRefreshListView getPullToRefreshListView() {
        return pullToRefreshListView;
    }

    public ListView getListView() {
        return pullToRefreshListView.getRefreshableView();
    }

    public BaseAdapter getAdapter() {
        return timeLineAdapter;
    }

    protected void refreshLayout(T bean) {
        if (bean != null && bean.getSize() > 0) {
//            empty.setVisibility(View.INVISIBLE);
            progressBar.setVisibility(View.INVISIBLE);
//            listView.setVisibility(View.VISIBLE);
        } else if (bean == null || bean.getSize() == 0) {
//            empty.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.INVISIBLE);
//            listView.setVisibility(View.VISIBLE);
        } else if (bean.getSize() == bean.getTotal_number()) {
//            empty.setVisibility(View.INVISIBLE);
            progressBar.setVisibility(View.INVISIBLE);
//            listView.setVisibility(View.VISIBLE);
        }
    }

    protected abstract void listViewItemClick(AdapterView parent, View view, int position, long id);

    protected void listViewFooterViewClick(View view) {
        if (Utility.isTaskStopped(oldTask)) {
            oldTask = new TimeLineGetOlderMsgListTask();
            oldTask.executeOnExecutor(MyAsyncTask.THREAD_POOL_EXECUTOR);
        }
    }

    public void loadMiddleMsg(String beginId, String endId, String endTag, int position) {
        if (Utility.isTaskStopped(middleTask)) {
            middleTask = new TimeLineGetMiddleMsgListTask(beginId, endId, endTag, position);
            middleTask.executeOnExecutor(MyAsyncTask.THREAD_POOL_EXECUTOR);
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (this.isVisible() && isVisibleToUser) {
//            getAdapter().notifyDataSetChanged();
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.listview_layout, container, false);
        empty = (TextView) view.findViewById(R.id.empty);
        progressBar = (ProgressBar) view.findViewById(R.id.progressbar);
        pullToRefreshListView = (PullToRefreshListView) view.findViewById(R.id.listView);

        pullToRefreshListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {

            @Override
            public void onRefresh(PullToRefreshBase<ListView> refreshView) {

                refresh();
            }
        });

        pullToRefreshListView.setOnLastItemVisibleListener(new PullToRefreshBase.OnLastItemVisibleListener() {
            @Override
            public void onLastItemVisible() {
                listViewFooterViewClick(null);
            }
        });

        getListView().setHeaderDividersEnabled(false);
        footerView = inflater.inflate(R.layout.listview_footer_layout, null);
        getListView().addFooterView(footerView);
        dismissFooterView();

        pullToRefreshListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                switch (scrollState) {

                    case AbsListView.OnScrollListener.SCROLL_STATE_IDLE:
                        if (!enableRefreshTime) {
                            enableRefreshTime = true;
                            getAdapter().notifyDataSetChanged();
                        }

                        break;


                    case AbsListView.OnScrollListener.SCROLL_STATE_FLING:

                        enableRefreshTime = false;
                        break;

                    case AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL:

                        enableRefreshTime = true;
                        break;


                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        }

        );

        pullToRefreshListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (mActionMode != null) {
                    getListView().clearChoices();
                    mActionMode.finish();
                    mActionMode = null;
                    return;
                }
                getListView().clearChoices();
                if (position - 1 < getList().getSize() && position - 1 >= 0) {
                    int index = position - 1;
                    ItemBean msg = getList().getItem(index);

                    if (msg != null) {
                        listViewItemClick(parent, view, index, id);

                    } else {
                        String beginId = getList().getItem(index - 1).getId();
                        String endTag = getList().getItem(index + 1).getId();
                        String endId = getList().getItem(index + 2).getId();

                        loadMiddleMsg(beginId, endId, endTag, index);

                        Toast.makeText(getActivity(), getString(R.string.loading_middle_msg), Toast.LENGTH_SHORT).show();
                    }

                } else if (position - 1 >= getList().getSize()) {

                    listViewFooterViewClick(view);
                }
            }
        });
        buildListAdapter();
        return view;
    }

    private void showFooterView() {
        TextView tv = ((TextView) footerView.findViewById(R.id.listview_footer));
        tv.setVisibility(View.VISIBLE);
        tv.setText(getString(R.string.loading));
        View view = footerView.findViewById(R.id.refresh);
        view.setVisibility(View.VISIBLE);
        view.startAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.refresh));
    }


    protected void dismissFooterView() {
        footerView.findViewById(R.id.refresh).setVisibility(View.GONE);
        footerView.findViewById(R.id.refresh).clearAnimation();
        footerView.findViewById(R.id.listview_footer).setVisibility(View.GONE);
    }


    private void showErrorFooterView() {
        TextView tv = ((TextView) footerView.findViewById(R.id.listview_footer));
        tv.setVisibility(View.VISIBLE);
        tv.setText(getString(R.string.click_to_load_older_message));
        View view = footerView.findViewById(R.id.refresh);
        view.clearAnimation();
        view.setVisibility(View.GONE);
    }

    public void clearActionMode() {
        if (mActionMode != null) {

            mActionMode.finish();
            mActionMode = null;
        }
        if (pullToRefreshListView != null && getListView().getCheckedItemCount() > 0) {
            getListView().clearChoices();
            if (getAdapter() != null) getAdapter().notifyDataSetChanged();
        }
    }

    protected abstract void buildListAdapter();

    @Override
    public void onDestroy() {
        super.onDestroy();
        Utility.cancelTasks(newTask, oldTask, middleTask);
    }


    protected boolean canSwitchGroup() {
        if (newTask != null && newTask.getStatus() != MyAsyncTask.Status.FINISHED) {
            return false;
        }
        if (oldTask != null && oldTask.getStatus() != MyAsyncTask.Status.FINISHED) {
            return false;
        }
        if (middleTask != null && middleTask.getStatus() != MyAsyncTask.Status.FINISHED) {
            return false;
        }
        return true;
    }


    public void refresh() {
        if (allowRefresh()) {

            newTask = new TimeLineGetNewMsgListTask();
            newTask.executeOnExecutor(MyAsyncTask.THREAD_POOL_EXECUTOR);
            ((AbstractAppActivity) getActivity()).getBitmapDownloader().totalStopLoadPicture();

        }

    }

    protected boolean allowRefresh() {
        return Utility.isTaskStopped(newTask) && getPullToRefreshListView().getVisibility() == View.VISIBLE;
    }


    @Override
    public void onResume() {
        super.onResume();
        getListView().setFastScrollEnabled(SettingUtility.allowFastScroll());
        getAdapter().notifyDataSetChanged();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        commander = ((AbstractAppActivity) getActivity()).getBitmapDownloader();

    }

    public void setmActionMode(ActionMode mActionMode) {
        this.mActionMode = mActionMode;
    }

    protected abstract void newMsgOnPostExecute(T newValue);

    protected abstract void oldMsgOnPostExecute(T newValue);

    public class TimeLineGetNewMsgListTask extends MyAsyncTask<Object, T, T> {
        WeiboException e;

        @Override
        protected void onPreExecute() {
            showListView();
            clearActionMode();
            Utility.stopListViewScrollingAndScrollToTop(getListView());
        }

        @Override
        protected T doInBackground(Object... params) {

            try {
                return getDoInBackgroundNewData();
            } catch (WeiboException e) {
                this.e = e;
                cancel(true);
            }
            return null;

        }

        @Override
        protected void onPostExecute(T newValue) {
            newMsgOnPostExecute(newValue);
            cleanWork();
            super.onPostExecute(newValue);
        }

        @Override
        protected void onCancelled(T messageListBean) {
            super.onCancelled(messageListBean);
            if (getActivity() != null) {
                if (this.e != null)
                    Toast.makeText(getActivity(), e.getError(), Toast.LENGTH_SHORT).show();
                cleanWork();
            }
        }

        private void cleanWork() {
            refreshLayout(getList());
            getPullToRefreshListView().onRefreshComplete();

        }
    }


    public class TimeLineGetOlderMsgListTask extends MyAsyncTask<Object, T, T> {

        WeiboException e;

        @Override
        protected void onPreExecute() {
            showListView();

            showFooterView();
            clearActionMode();
        }

        @Override
        protected T doInBackground(Object... params) {

            try {
                return getDoInBackgroundOldData();
            } catch (WeiboException e) {
                this.e = e;
                cancel(true);
            }
            return null;

        }

        @Override
        protected void onPostExecute(T newValue) {
            oldMsgOnPostExecute(newValue);

            cleanWork();
            super.onPostExecute(newValue);
        }

        @Override
        protected void onCancelled(T messageListBean) {
            super.onCancelled(messageListBean);
            if (getActivity() != null) {
                if (Utility.isAllNotNull(this.e, getActivity())) {
                    Toast.makeText(getActivity(), e.getError(), Toast.LENGTH_SHORT).show();
                    showErrorFooterView();
                    getPullToRefreshListView().onRefreshComplete();
                } else {
                    dismissFooterView();
                }
            }

        }

        private void cleanWork() {
            getPullToRefreshListView().onRefreshComplete();
            getAdapter().notifyDataSetChanged();
            dismissFooterView();
        }
    }


    public class TimeLineGetMiddleMsgListTask extends MyAsyncTask<Object, T, T> {

        WeiboException e;
        String beginId;
        String endId;
        String endTag;
        int position;

        public TimeLineGetMiddleMsgListTask(String beginId, String endId, String endTag, int position) {
            this.beginId = beginId;
            this.endId = endId;
            this.endTag = endTag;
            this.position = position;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            clearActionMode();
        }

        @Override
        protected T doInBackground(Object... params) {

            try {
                return getDoInBackgroundMiddleData(beginId, endId);
            } catch (WeiboException e) {
                this.e = e;
                cancel(true);
            }
            return null;

        }

        @Override
        protected void onPostExecute(T newValue) {
            middleMsgOnPostExecute(endTag, position, newValue);
            getAdapter().notifyDataSetChanged();
            super.onPostExecute(newValue);
        }

    }


    protected void middleMsgOnPostExecute(String endTag, int position, T newValue) {

        if (newValue == null)
            return;

        if (newValue.getSize() == 1) {
            getList().getItemList().remove(position);
            getAdapter().notifyDataSetChanged();
            return;
        }

        ItemBean lastItem = newValue.getItem(newValue.getSize() - 1);

        if (!lastItem.getId().equals(endTag)) {
            getList().getItemList().addAll(position, newValue.getItemList().subList(1, newValue.getSize()));
            getAdapter().notifyDataSetChanged();
            return;
        }

        if (lastItem.getId().equals(endTag)) {
            int nullIndex = position + newValue.getSize() - 1;
            getList().getItemList().addAll(position, newValue.getItemList().subList(1, newValue.getSize()));
            getList().getItemList().remove(nullIndex - 1);
            getList().getItemList().remove(nullIndex - 1);
            getAdapter().notifyDataSetChanged();
            return;
        }


    }

    protected void showListView() {
        progressBar.setVisibility(View.INVISIBLE);
    }

    protected void afterGetNewMsg() {

    }

    protected void afterGetOldMsg() {

    }

    protected abstract T getDoInBackgroundNewData() throws WeiboException;

    protected abstract T getDoInBackgroundOldData() throws WeiboException;

    protected abstract T getDoInBackgroundMiddleData(String beginId, String endId) throws WeiboException;


    private volatile boolean enableRefreshTime = true;

    public boolean isListViewFling() {
        return !enableRefreshTime;
    }

}


