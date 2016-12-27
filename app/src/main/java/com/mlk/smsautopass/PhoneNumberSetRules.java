package com.mlk.smsautopass;

import android.app.Dialog;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.mlk.smsautopass.sql.DBPhoneNumberAdapter;

import java.util.ArrayList;

/**
 * Created by mingeek on 2016-12-26.
 */

public class PhoneNumberSetRules extends AppCompatActivity {
    private ArrayList<String> arrayRules = new ArrayList();
    private Button btnRule;
    View.OnClickListener click = new View.OnClickListener() {
        public void onClick(View paramAnonymousView) {
            if (rules != null) {
                if (editRule.getText() != null) {
                    rules = (rules + "," + editRule.getText().toString());
                }
            } else {
                rules = editRule.getText().toString();
            }
            editRule.setText("");
            dbPhoneNumberAdapter.updateRules(phoneNumberId, rules);
            refreshList();
        }
    };
    private Cursor cursor;
    private DBPhoneNumberAdapter dbPhoneNumberAdapter;
    private EditText editRule;
    private String isEnabled;
    private ListAdapter listAdapter;
    AdapterView.OnItemClickListener listClick = new AdapterView.OnItemClickListener() {
        public void onItemClick(AdapterView<?> paramAnonymousAdapterView, View paramAnonymousView, int paramAnonymousInt, long paramAnonymousLong) {
            listItemDialog(paramAnonymousInt);
        }
    };
    private TextView listEmpty;
    private ListView listView;
    private TextView pPhoneNumber, pImgEnabled;
    private TextView pProfileName;
    private TextView pRecentTime;
    private TextView pRules;
    private String phoneNumber;
    private int phoneNumberId;
    private String profileName;
    private String recentTime;
    private String rules;
    private TextView textCount;
    private TextView textRule;
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
        TextView title = (TextView) localView.findViewById(R.id.deletecheck_dialog_title);

        if (paramInt2 == 1) {
            title.setText("방금 선택한 규칙을\n정말로 삭제하시겠습니까?");
        } else {
            title.setText("전체 규칙을\n정말로 삭제하시겠습니까?");
        }

        Button btnOk = (Button) localView.findViewById(R.id.deletecheck_dialog_btn_ok);
        Button btnCancel = (Button) localView.findViewById(R.id.deletecheck_dialog_btn_cancel);
//        btnOk.setBackgroundColor(Color.alpha(0));
//        btnCancel.setBackgroundColor(Color.alpha(0));
        btnOk.setOnTouchListener(this.touch);
        btnCancel.setOnTouchListener(this.touch);
        btnOk.setOnClickListener(new View.OnClickListener() {
            public void onClick(View paramAnonymousView) {
                switch (paramInt2) {
                    case 1:
                        if (paramInt1 == 0) {
                            rules = rules.replace((CharSequence) arrayRules.get(paramInt1), "");
                            rules = rules.substring(1, rules.length());
                        } else if (arrayRules.size() == paramInt1) {
                            rules = rules.replace((CharSequence) arrayRules.get(paramInt1), "");
                            rules = rules.substring(0, rules.length() - 1);
                        } else {
                            rules = rules.replace((CharSequence) arrayRules.get(paramInt1), "");
                            rules = rules.replace(",,", ",");
                        }
                        break;
                    default:
                        rules = null;
                        break;
                }
                dbPhoneNumberAdapter.updateRules(phoneNumberId, rules);
                refreshList();
                localDialog.cancel();
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View paramAnonymousView) {
                localDialog.cancel();
            }
        });
        localDialog.setContentView(localView);
        localDialog.show();

    }

    private ListAdapter getPhoneNumberRules() {
        this.arrayRules.clear();
        this.cursor = this.dbPhoneNumberAdapter.fetchPhoneNumber(this.phoneNumberId);
        if (this.cursor.moveToFirst()) {
            String rules = this.cursor.getString(4);
            if (rules != null) {
                this.rules = rules;
                String[] rule = this.rules.split(",");
                for (int i = 0; i < rule.length; i++) {
                    this.listEmpty.setVisibility(View.GONE);
                    this.pRules.setText("전달 규칙: " + this.rules);
                    this.arrayRules.add(rule[i]);
                }
            } else {
                this.listEmpty.setVisibility(View.VISIBLE);
                this.pRules.setText("(전달 규칙 없음)");
            }
        }

        this.listAdapter = new ListAdapter(this, R.layout.rules_list_row, this.arrayRules);
        this.listAdapter.notifyDataSetChanged();

        return this.listAdapter;
    }

    private void listItemDialog(final int paramInt) {
        final Dialog localDialog = new Dialog(this);
        View localView = getLayoutInflater().inflate(R.layout.delete_dialog, null);

        Button localButton1 = (Button) localView.findViewById(R.id.delete_dialog_btn1);
        Button localButton2 = (Button) localView.findViewById(R.id.delete_dialog_btn2);
//        localButton1.setBackgroundColor(Color.alpha(0));
//        localButton2.setBackgroundColor(Color.alpha(0));
        localButton1.setOnTouchListener(this.touch);
        localButton2.setOnTouchListener(this.touch);
        localButton1.setText("방금 선택한 규칙 삭제");
        localButton2.setText("전체 규칙 삭제");
        localButton1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View paramAnonymousView) {
                deleteCheckDialog(paramInt, 1);
                localDialog.cancel();
            }
        });
        localButton2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View paramAnonymousView) {
                deleteCheckDialog(paramInt, 2);
                localDialog.cancel();
            }
        });
        localDialog.setContentView(localView);
        localDialog.show();
    }

    private void refreshList() {
        this.listView.setAdapter(getPhoneNumberRules());
    }

    @Override
    protected void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);
        setContentView(R.layout.activity_rules_list);

        this.phoneNumberId = getIntent().getIntExtra("id", 0);
        this.phoneNumber = getIntent().getStringExtra("phoneNumber");
        this.profileName = getIntent().getStringExtra("profileName");
        this.isEnabled = getIntent().getStringExtra("enabled");
        this.recentTime = getIntent().getStringExtra("recentTime");

        this.dbPhoneNumberAdapter = new DBPhoneNumberAdapter(this);
        this.dbPhoneNumberAdapter.open();

        this.pProfileName = ((TextView) findViewById(R.id.activity_rules_list_text_profile));
        this.pPhoneNumber = ((TextView) findViewById(R.id.activity_rules_list_text_phonenumber));
        this.pImgEnabled = ((TextView) findViewById(R.id.activity_rules_list_img_enable));
        this.pRules = ((TextView) findViewById(R.id.activity_rules_list_text_rules));
        this.pRecentTime = ((TextView) findViewById(R.id.activity_rules_list_text_recenttime));

        if (this.recentTime == null) {
            recentTime = "최근 전달 내역: " + this.recentTime;
        } else {
            recentTime = "(최근 전달 내역 없음)";
        }
        this.pRecentTime.setText(recentTime);

        this.pProfileName.setText("@" + this.profileName);
        this.pPhoneNumber.setText(this.phoneNumber);

        if (this.isEnabled.equals("true")) {
            this.pImgEnabled.setText("사용함");
        } else {
            this.pImgEnabled.setText("사용 안함");
        }

        this.listEmpty = ((TextView) findViewById(R.id.activity_rules_list_text_listempty));
        this.listEmpty.setVisibility(View.VISIBLE);
        this.listView = ((ListView) findViewById(R.id.activity_rules_list_text_listview));
        this.listView.setOnItemClickListener(this.listClick);
        refreshList();

        this.editRule = ((EditText) findViewById(R.id.activity_rules_list_edit_rules));
        this.btnRule = ((Button) findViewById(R.id.activity_rules_list_button));
        this.btnRule.setOnClickListener(this.click);

    }

    @Override
    protected void onPause() {
        super.onPause();
        this.dbPhoneNumberAdapter.close();
    }

    @Override
    protected void onResume() {
        super.onResume();
        this.dbPhoneNumberAdapter.open();
        refreshList();
    }

    private class ListAdapter extends ArrayAdapter {
        public ListAdapter(Context paramContext, int paramInt, ArrayList paramArrayList) {
            super(paramContext, paramInt, paramArrayList);
            arrayRules = paramArrayList;
        }

        public View getView(int paramInt, View paramView, ViewGroup paramViewGroup) {
            View v = paramView;
            if (v == null) {
                v = getLayoutInflater().inflate(R.layout.rules_list_row, null);
            }
            String rules = arrayRules.get(paramInt);
            if (rules != null) {
                textCount = ((TextView) v.findViewById(R.id.rules_list_row_text_count));
                textRule = ((TextView) v.findViewById(R.id.rules_list_row_text_rule));
                textCount.setText(paramInt + 1 + " : ");
                textRule.setText(rules);
            }
            return v;
        }
    }
}