package com.mlk.smsautopass;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.mlk.smsautopass.sql.DBSendLogAdapter;
import com.mlk.smsautopass.vo.SendLogInfo;

import java.util.ArrayList;

/**
 * Created by mingeek on 2016-12-26.
 */

public class ViewSendLog extends AppCompatActivity {
    private ArrayList<SendLogInfo> arraySendLog = new ArrayList();
    private Cursor cursor;
    private DBSendLogAdapter dbSendLogAdapter;
    private String isEnabled, profileName, recentTime, rules;
    private ListAdapter listAdapter;
    AdapterView.OnItemClickListener listClick = new AdapterView.OnItemClickListener() {
        public void onItemClick(AdapterView<?> paramAnonymousAdapterView, View paramAnonymousView, int paramAnonymousInt, long paramAnonymousLong) {
            listItemDialog(paramAnonymousInt);
        }
    };
    private TextView listEmpty;
    AdapterView.OnItemLongClickListener listLongClick = new AdapterView.OnItemLongClickListener() {
        public boolean onItemLongClick(AdapterView<?> paramAnonymousAdapterView, View paramAnonymousView, int paramAnonymousInt, long paramAnonymousLong) {
            dialogListLongClick(paramAnonymousInt);
            return false;
        }
    };
    private ListView listView;
    private ArrayList<SendLogInfo> objects;
    private TextView pPhoneNumber, pProfileName, pRecentTime, pRules, pImgEnabled;
    private String phoneNumber;
    private int phoneNumberId;
    private TextView textCount, textRule, textSendSMS, textSendTime;

    View.OnTouchListener touch = new View.OnTouchListener() {
        public boolean onTouch(View paramAnonymousView, MotionEvent paramAnonymousMotionEvent) {
            switch (paramAnonymousMotionEvent.getAction()) {
                case 0:
                    paramAnonymousView.setBackgroundColor(Color.rgb(0, 156, 255));
                    return false;
                default:
                    paramAnonymousView.setBackgroundColor(Color.alpha(0));
                    return false;
            }
        }
    };

    private void deleteCheckDialog(final int paramInt1, final int paramInt2) {
        final Dialog localDialog = new Dialog(this);
        View localView = getLayoutInflater().inflate(R.layout.deletecheck_dialog, null);
        final TextView localTextView = (TextView) localView.findViewById(R.id.deletecheck_dialog_title);
        if (paramInt2 == 1) {
            localTextView.setText("방금 선택한 전송 이력을\n정말로 삭제하시겠습니까?");
        } else {
            localTextView.setText("전체 규칙을\n정말로 삭제하시겠습니까?");
        }
        Button localButton1 = (Button) localView.findViewById(R.id.deletecheck_dialog_btn_ok);
        Button localButton2 = (Button) localView.findViewById(R.id.deletecheck_dialog_btn_cancel);
        localButton1.setBackgroundColor(Color.alpha(0));
        localButton2.setBackgroundColor(Color.alpha(0));
        localButton1.setOnTouchListener(this.touch);
        localButton2.setOnTouchListener(this.touch);
        localButton1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View paramAnonymousView) {
                switch (paramInt2) {
                    case 1:
                        dbSendLogAdapter.deleteLog(((SendLogInfo) objects.get(paramInt1)).getId());
                        refreshList();
                        break;
                    case 2:
                        dbSendLogAdapter.deleteAll();
                        refreshList();
                        break;
                    default:
                        break;
                }
                localDialog.cancel();
            }
        });
        localButton2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View paramAnonymousView) {
                localDialog.cancel();
            }
        });
        localDialog.setContentView(localView);
        localDialog.show();
    }

    private void dialogListLongClick(final int paramInt) {
        AlertDialog.Builder localBuilder = new AlertDialog.Builder(this);
        localBuilder.setTitle("수행할 작업을 선택해주세요.");
        DialogInterface.OnClickListener local8 = new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt) {
                dbSendLogAdapter.deleteLog(((SendLogInfo) objects.get(paramInt)).getId());
                refreshList();
                paramAnonymousDialogInterface.cancel();
            }
        };
        localBuilder.setSingleChoiceItems(new CharSequence[]{"삭제"}, -1, local8);
        localBuilder.create().show();
    }

    private ListAdapter getSendLogList() {
        this.arraySendLog.clear();
        this.cursor = this.dbSendLogAdapter.fetchLog(this.phoneNumber.replace("-", ""));
        if (this.cursor.moveToFirst()) {
            do {
                int i = this.cursor.getInt(0);
                String str1 = this.cursor.getString(1);
                String str2 = this.cursor.getString(2);
                String str3 = this.cursor.getString(3);
                String str4 = this.cursor.getString(4);
                String str5 = this.cursor.getString(5);
                this.arraySendLog.add(new SendLogInfo(i, str1, str2, str3, str4, str5, true));
            } while (this.cursor.moveToNext());

            this.listEmpty.setVisibility(View.GONE);
            this.listAdapter = new ListAdapter(this, R.layout.sendlog_list_row, this.arraySendLog);
            this.listAdapter.notifyDataSetChanged();
        } else {
            this.listEmpty.setVisibility(View.VISIBLE);
        }
        this.cursor.close();
        return this.listAdapter;
    }

    private void listItemDialog(final int paramInt) {
        final Dialog localDialog = new Dialog(this);
        View localView = getLayoutInflater().inflate(R.layout.delete_dialog, null);
        new Button(getApplicationContext());
        Button localButton1 = (Button) localView.findViewById(R.id.delete_dialog_btn1);
        Button localButton2 = (Button) localView.findViewById(R.id.delete_dialog_btn2);
        localButton1.setBackgroundColor(Color.alpha(0));
        localButton2.setBackgroundColor(Color.alpha(0));
        localButton1.setOnTouchListener(this.touch);
        localButton2.setOnTouchListener(this.touch);
        localButton1.setText("방금 선택한 이력 삭제");
        localButton2.setText("전체 전송 이력 삭제");
        localButton1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View paramAnonymousView) {
                ViewSendLog.this.deleteCheckDialog(paramInt, 1);
                localDialog.cancel();
            }
        });
        localButton2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View paramAnonymousView) {
                ViewSendLog.this.deleteCheckDialog(paramInt, 2);
                localDialog.cancel();
            }
        });
        localDialog.setContentView(localView);
        localDialog.show();
    }

    private void refreshList() {
        this.listView.setAdapter(getSendLogList());
    }

    @Override
    protected void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);
        setContentView(R.layout.sendlog_list);

        this.phoneNumberId = getIntent().getIntExtra("id", 0);
        this.phoneNumber = getIntent().getStringExtra("phoneNumber");
        this.profileName = getIntent().getStringExtra("profileName");
        this.rules = getIntent().getStringExtra("rules");
        this.isEnabled = getIntent().getStringExtra("enabled");
        this.recentTime = getIntent().getStringExtra("recentTime");

        this.dbSendLogAdapter = new DBSendLogAdapter(this);
        this.dbSendLogAdapter.open();

        this.pProfileName = ((TextView) findViewById(R.id.activity_sendlog_list_text_profile));
        this.pPhoneNumber = ((TextView) findViewById(R.id.activity_sendlog_list_text_phonenumber));
        this.pImgEnabled = ((TextView) findViewById(R.id.activity_sendlog_list_img_enable));
        this.pRules = ((TextView) findViewById(R.id.activity_sendlog_list_text_rules));
        this.pRecentTime = ((TextView) findViewById(R.id.activity_sendlog_list_text_recenttime));

        if (Boolean.valueOf(this.isEnabled).booleanValue()) {
            this.pImgEnabled.setText("사용함");

        } else {
            this.pImgEnabled.setText("사용 안함");
        }

        this.pProfileName.setText("@" + this.profileName);

        this.pPhoneNumber.setText(this.phoneNumber);

        String rule;
        if (this.rules != null) {
            rule = "전달 규칙: " + this.rules;
        } else {
            rule = "(전달 규칙 없음)";
        }
        this.pRules.setText(rule);

        String str;
        if (this.pRecentTime != null) {
            str = "최근 전달 내역: " + this.recentTime;
        } else {
            str = "(최근 전달 내역 없음)";
        }
        this.pRecentTime.setText(str);


        this.listView = ((ListView) findViewById(R.id.activity_sendlog_list_listview));
        this.listView.setOnItemClickListener(this.listClick);
        this.listEmpty = ((TextView) findViewById(R.id.activity_sendlog_list_text_listempty));
        this.listEmpty.setVisibility(View.GONE);
        refreshList();
    }

    @Override
    protected void onPause() {
        super.onPause();
        this.dbSendLogAdapter.close();
    }

    @Override
    protected void onResume() {
        super.onResume();
        this.dbSendLogAdapter.open();
    }

    private class ListAdapter extends ArrayAdapter {
        public ListAdapter(Context paramContext, int paramInt, ArrayList paramArrayList) {
            super(paramContext, paramInt, paramArrayList);
            objects = paramArrayList;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            if (view == null) {
                view = getLayoutInflater().inflate(R.layout.sendlog_list_row, null);
            }
            SendLogInfo sendLogInfo = (SendLogInfo) objects.get(i);
            if (sendLogInfo != null) {
                textCount = ((TextView) view.findViewById(R.id.sendlog_list_row_count));
                textRule = ((TextView) view.findViewById(R.id.sendlog_list_row_rule));
                textSendTime = ((TextView) view.findViewById(R.id.sendlog_list_row_sendtime));
                textSendSMS = ((TextView) view.findViewById(R.id.sendlog_list_row_sendsms));
                textCount.setText("[ " + (i + 1) + " ]");
                textRule.setText("적용규칙:" + sendLogInfo.getThisRule());
                textSendTime.setText(sendLogInfo.getSendTime().replace(") ", ")\n"));
                textSendSMS.setText(sendLogInfo.getSendText());
            }
            return view;
        }
    }
}