package com.mlk.smsautopass;

import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.mlk.smsautopass.sql.DBPhoneNumberAdapter;
import com.mlk.smsautopass.sql.DBSendLogAdapter;
import com.mlk.smsautopass.util.LogUtil;
import com.mlk.smsautopass.vo.PhoneNumberInfo;

import java.util.ArrayList;

/**
 * Created by mingeek on 2016-12-26.
 */

public class MainActivity extends AppCompatActivity {
    private ArrayList<PhoneNumberInfo> arrayPhoneNumber = new ArrayList();
    private Cursor cursor;
    private DBPhoneNumberAdapter dbPhoneNumberAdapter;
    private DBSendLogAdapter dbSendLogAdapter;
    private EditText editPhoneNumber, editProfileName;
    private ListAdapter listAdapter;
    AdapterView.OnItemClickListener listClick = new AdapterView.OnItemClickListener() {
        public void onItemClick(AdapterView<?> paramAnonymousAdapterView, View paramAnonymousView, int paramAnonymousInt, long paramAnonymousLong) {
            listItemDialog(paramAnonymousInt);
        }
    };
    private TextView listEmpty;
    private ListView listView;
    private ArrayList<PhoneNumberInfo> objects;
    private TextView textPhoneNumber, textProfileName, textRecentTime, textRules, imgEnabled;

    View.OnTouchListener touch = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            switch (motionEvent.getAction()) {
                case 0:
                    view.setBackgroundColor(Color.rgb(0, 156, 255));
                    return false;
                default:
                    view.setBackgroundColor(Color.alpha(0));
                    return false;
            }
        }
    };

    private void addPhoneNumberDialog(final int paramInt1, final int paramInt2, String paramString1, String paramString2) {
        LogUtil.d("");
        final Dialog localDialog = new Dialog(this);
        View localView = getLayoutInflater().inflate(R.layout.add_dialog, null);
        String str2 = null;
        String str1 = null;
        this.editProfileName = ((EditText) localView.findViewById(R.id.add_dialog_edit_profile_name));
        this.editPhoneNumber = ((EditText) localView.findViewById(R.id.add_dialog_edit_phone_number));

        if (paramInt1 == 1) {
            str2 = "SMS를 전달할 전화번호를\n입력해주세요.";
            str1 = "등록";
        } else if (paramInt1 == 2) {
            str2 = "SMS를 전달할 전화번호를\n수정해주세요.";
            str1 = "수정";
            this.editProfileName.setText(paramString2);
            this.editPhoneNumber.setText(paramString1);
        }

        ((TextView) localView.findViewById(R.id.add_dialog_title)).setText(str2);
        Button btnOk = (Button) localView.findViewById(R.id.add_dialog_btn_ok);
        Button btnCancel = (Button) localView.findViewById(R.id.add_dialog_btn_cancel);
        btnOk.setText(str1);
//        btnOk.setBackgroundColor(Color.alpha(0));
//        btnCancel.setBackgroundColor(Color.alpha(0));
        btnOk.setOnTouchListener(this.touch);
        btnCancel.setOnTouchListener(this.touch);
        btnOk.setOnClickListener(new View.OnClickListener() {
            public void onClick(View paramAnonymousView) {
                if (paramInt1 == 1) {
                    dbPhoneNumberAdapter.insertPhoneNumber(editPhoneNumber.getText().toString(), editProfileName.getText().toString());
                } else if (paramInt1 == 2) {
                    dbPhoneNumberAdapter.updatePhoneNumber(paramInt2, editPhoneNumber.getText().toString(), editProfileName.getText().toString());
                }
                refreshList();
                localDialog.cancel();
                return;
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View paramAnonymousView) {
                localDialog.cancel();
            }
        });
        localDialog.setContentView(localView);
        localDialog.show();
        return;
    }

    private void deleteCheckDialog(final int paramInt) {
        final Dialog localDialog = new Dialog(this);
        View localView = getLayoutInflater().inflate(R.layout.deletecheck_dialog, null);
        ((TextView) localView.findViewById(R.id.deletecheck_dialog_title)).setText("해당 전화번호 정보를\n정말로 삭제하시겠습니까?");
        Button localButton1 = (Button) localView.findViewById(R.id.deletecheck_dialog_btn_ok);
        Button localButton2 = (Button) localView.findViewById(R.id.deletecheck_dialog_btn_cancel);
//        localButton1.setBackgroundColor(Color.alpha(0));
//        localButton2.setBackgroundColor(Color.alpha(0));
        localButton1.setOnTouchListener(this.touch);
        localButton2.setOnTouchListener(this.touch);
        localButton1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View paramAnonymousView) {
                dbPhoneNumberAdapter.deletePhoneNumber(paramInt);
                refreshList();
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

    private String formatPhoneNumber(String paramString) {
        if (paramString.length() == 10) {
            return new StringBuilder(String.valueOf(new StringBuilder(String.valueOf(new StringBuilder(String.valueOf(new StringBuilder(String.valueOf("")).append(paramString.substring(0, 3)).toString())).append("-").toString())).append(paramString.substring(3, 6)).toString())).append("-").toString() + paramString.substring(6, 10);
        } else if (paramString.length() == 11) {
            return new StringBuilder(String.valueOf(new StringBuilder(String.valueOf(new StringBuilder(String.valueOf(new StringBuilder(String.valueOf("")).append(paramString.substring(0, 3)).toString())).append("-").toString())).append(paramString.substring(3, 7)).toString())).append("-").toString() + paramString.substring(7, 11);
        }
        return paramString;
    }

    private ListAdapter getPhoneNumberList() {
        this.arrayPhoneNumber.clear();
        this.cursor = this.dbPhoneNumberAdapter.fetchAll();
        if (this.cursor.moveToFirst()) {
            do {
                int i = this.cursor.getInt(0);
                String str1 = this.cursor.getString(1);
                String str2 = this.cursor.getString(2);
                boolean bool = Boolean.valueOf(this.cursor.getString(3)).booleanValue();
                String str3 = this.cursor.getString(4);
                String str4 = this.cursor.getString(5);
                this.arrayPhoneNumber.add(new PhoneNumberInfo(i, str1, str2, bool, str3, str4));
            } while (this.cursor.moveToNext());

            this.listAdapter = new ListAdapter(this.arrayPhoneNumber);

            this.listEmpty.setVisibility(View.GONE);
        } else {
            this.listEmpty.setVisibility(View.VISIBLE);
        }
        return this.listAdapter;
    }

    private void listItemDialog(final int paramInt) {
        final Dialog localDialog = new Dialog(this);
        View localView = getLayoutInflater().inflate(R.layout.listmenu_dialog, null);
        new Button(getApplicationContext());
        final Button localButton1 = (Button) localView.findViewById(R.id.listmenu_dialog_btn1);
        Button localButton2 = (Button) localView.findViewById(R.id.listmenu_dialog_btn2);
        Button localButton3 = (Button) localView.findViewById(R.id.listmenu_dialog_btn3);
        Button localButton4 = (Button) localView.findViewById(R.id.listmenu_dialog_btn4);
        Button localButton5 = (Button) localView.findViewById(R.id.listmenu_dialog_btn5);
        final boolean bool = ((PhoneNumberInfo) this.objects.get(paramInt)).isEnabled();
        if (bool) {
            localButton1.setText("사용하지 않음");
        } else {
            localButton1.setText("사용함");
        }

//        localButton1.setBackgroundColor(Color.alpha(0));
//        localButton2.setBackgroundColor(Color.alpha(0));
//        localButton3.setBackgroundColor(Color.alpha(0));
//        localButton4.setBackgroundColor(Color.alpha(0));
//        localButton5.setBackgroundColor(Color.alpha(0));
        localButton1.setOnTouchListener(this.touch);
        localButton2.setOnTouchListener(this.touch);
        localButton3.setOnTouchListener(this.touch);
        localButton4.setOnTouchListener(this.touch);
        localButton5.setOnTouchListener(this.touch);

        localButton1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View paramAnonymousView) {
                int i = ((PhoneNumberInfo) objects.get(paramInt)).getId();
                if (bool) {
                    dbPhoneNumberAdapter.updateEnabled(i, String.valueOf(false));
                } else {
                    dbPhoneNumberAdapter.updateEnabled(i, String.valueOf(true));
                }
                refreshList();
                localDialog.cancel();
            }
        });

        localButton2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View paramAnonymousView) {
                Intent intent = new Intent(MainActivity.this, PhoneNumberSetRules.class);
                intent.putExtra("id", ((PhoneNumberInfo) objects.get(paramInt)).getId());
                intent.putExtra("phoneNumber", formatPhoneNumber(((PhoneNumberInfo) objects.get(paramInt)).getPhoneNumber()));
                intent.putExtra("profileName", ((PhoneNumberInfo) objects.get(paramInt)).getProfileName());
                intent.putExtra("enabled", String.valueOf(((PhoneNumberInfo) objects.get(paramInt)).isEnabled()));
                intent.putExtra("rules", ((PhoneNumberInfo) objects.get(paramInt)).getRules());
                intent.putExtra("recentTime", ((PhoneNumberInfo) objects.get(paramInt)).getRecentTime());
                startActivity(intent);
                localDialog.cancel();
            }
        });

        localButton3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View paramAnonymousView) {
                Intent intent = new Intent(MainActivity.this, ViewSendLog.class);
                intent.putExtra("id", ((PhoneNumberInfo) objects.get(paramInt)).getId());
                intent.putExtra("phoneNumber", formatPhoneNumber(((PhoneNumberInfo) objects.get(paramInt)).getPhoneNumber()));
                intent.putExtra("profileName", ((PhoneNumberInfo) objects.get(paramInt)).getProfileName());
                intent.putExtra("enabled", String.valueOf(((PhoneNumberInfo) objects.get(paramInt)).isEnabled()));
                intent.putExtra("rules", ((PhoneNumberInfo) objects.get(paramInt)).getRules());
                intent.putExtra("recentTime", ((PhoneNumberInfo) objects.get(paramInt)).getRecentTime());
                startActivity(intent);
                localDialog.cancel();
            }
        });

        localButton4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View paramAnonymousView) {
                addPhoneNumberDialog(2, objects.get(paramInt).getId(), objects.get(paramInt).getPhoneNumber(), objects.get(paramInt).getProfileName());
                refreshList();
                localDialog.cancel();
            }
        });

        localButton5.setOnClickListener(new View.OnClickListener() {
            public void onClick(View paramAnonymousView) {
                deleteCheckDialog(((PhoneNumberInfo) objects.get(paramInt)).getId());
                localDialog.cancel();
            }
        });

        localDialog.setContentView(localView);
        localDialog.show();
        return;
    }

    private void refreshList() {
        LogUtil.d("");
        this.listView.setAdapter(getPhoneNumberList());
    }

    @Override
    protected void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);
        setContentView(R.layout.activity_phonenumber_list);

        this.dbPhoneNumberAdapter = new DBPhoneNumberAdapter(this);
        this.dbPhoneNumberAdapter.open();

        this.dbSendLogAdapter = new DBSendLogAdapter(this);
        this.dbSendLogAdapter.open();

        this.listEmpty = ((TextView) findViewById(R.id.activity_phonenumber_list_empty));
        this.listEmpty.setVisibility(View.GONE);

        this.listView = ((ListView) findViewById(R.id.activity_phonenumber_list_listview));
        this.listView.setOnItemClickListener(this.listClick);
        refreshList();
    }

    public boolean onCreateOptionsMenu(Menu paramMenu) {
        paramMenu.add(0, 2, 0, "전화번호 추가");
        paramMenu.add(0, 3, 0, "설정(준비중)");
        paramMenu.add(0, 4, 0, "사용법(준비중)");
        return super.onCreateOptionsMenu(paramMenu);
    }

    public boolean onOptionsItemSelected(MenuItem paramMenuItem) {
        addPhoneNumberDialog(1, 0, null, null);
        return super.onOptionsItemSelected(paramMenuItem);
    }

    @Override
    protected void onPause() {
        super.onPause();
        this.dbPhoneNumberAdapter.close();
        this.dbSendLogAdapter.close();
    }

    @Override
    protected void onResume() {
        super.onResume();
        this.dbPhoneNumberAdapter.open();
        refreshList();
    }

    private class ListAdapter extends BaseAdapter {

        public ListAdapter(final ArrayList<PhoneNumberInfo> list) {
            objects = list;
        }

        @Override
        public int getCount() {
            return objects.size();
        }

        @Override
        public Object getItem(int i) {
            return objects.get(i);
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            LogUtil.d("");
            if (view == null) {
                view = getLayoutInflater().inflate(R.layout.phonenumber_list_row, null);
            }
            Object localObject = getItem(i);
            if (localObject != null) {
                LogUtil.d("");
                textProfileName = ((TextView) view.findViewById(R.id.phonenumber_list_row_text_profilename));
                textPhoneNumber = ((TextView) view.findViewById(R.id.phonenumber_list_row_text_phonenumber));

                imgEnabled = ((TextView) view.findViewById(R.id.phonenumber_list_row_text_enable));

                textRules = ((TextView) view.findViewById(R.id.phonenumber_list_row_text_rules));
                textRecentTime = ((TextView) view.findViewById(R.id.phonenumber_list_row_text_recenttime));

                textProfileName.setText("@" + ((PhoneNumberInfo) localObject).getProfileName());

                String phoneNumber = formatPhoneNumber(((PhoneNumberInfo) localObject).getPhoneNumber());
                textPhoneNumber.setText(phoneNumber);

                if (((PhoneNumberInfo) localObject).isEnabled()) {
                    imgEnabled.setText("사용함");
                } else {
                    imgEnabled.setText("사용하지 않음");
                }

                String rules;
                if (((PhoneNumberInfo) localObject).getRules() == null) {
                    rules = "(전달 규칙 없음)";
                } else {
                    rules = "전달 규칙: " + ((PhoneNumberInfo) localObject).getRules();
                }
                textRules.setText(rules);

                String recentTime;
                if (((PhoneNumberInfo) localObject).getRecentTime() != null) {
                    recentTime = "최근 전달 내역: " + ((PhoneNumberInfo) localObject).getRecentTime();
                } else {
                    recentTime = "(최근 전달 내역 없음)";
                }
                textRecentTime.setText(recentTime);
            }
            return view;
        }
    }
}
