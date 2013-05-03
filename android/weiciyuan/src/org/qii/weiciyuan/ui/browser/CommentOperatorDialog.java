package org.qii.weiciyuan.ui.browser;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import org.qii.weiciyuan.R;
import org.qii.weiciyuan.bean.CommentBean;
import org.qii.weiciyuan.support.utils.GlobalContext;
import org.qii.weiciyuan.ui.send.WriteReplyToCommentActivity;

/**
 * User: qii
 * Date: 12-8-28
 */
public class CommentOperatorDialog extends DialogFragment {

    private CommentBean bean;

    public CommentOperatorDialog() {

    }

    public CommentOperatorDialog(CommentBean bean) {
        this.bean = bean;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("msg", bean);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        if (savedInstanceState != null)
            bean = (CommentBean) savedInstanceState.getSerializable("msg");

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        String[] items = {getString(R.string.reply)};
        builder.setTitle(getString(R.string.and_then));
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0:
                        Intent intent = new Intent(getActivity(), WriteReplyToCommentActivity.class);
                        intent.putExtra("msg", bean);
                        intent.putExtra("token", GlobalContext.getInstance().getSpecialToken());
                        startActivity(intent);
                        break;
                    case 1:
                        break;
                }

            }
        });

        return builder.create();

    }
}
