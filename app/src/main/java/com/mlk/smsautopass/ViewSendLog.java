package com.mlk.smsautopass;

import android.support.v7.app.AppCompatActivity;

/**
 * Created by mingeek on 2016-12-26.
 */

public class ViewSendLog extends AppCompatActivity {
//    private static final int DELETE_ALL = 2;
//    private static final int DELETE_SELECT = 1;
//    private ArrayList<SendLogInfo> arraySendLog = new ArrayList();
//    private Cursor cursor;
//    private DBSendLogAdapter dbSendLogAdapter;
//    private String isEnabled;
//    private ListAdapter listAdapter;
//    AdapterView.OnItemClickListener listClick = new AdapterView.OnItemClickListener() {
//        public void onItemClick(AdapterView<?> paramAnonymousAdapterView, View paramAnonymousView, int paramAnonymousInt, long paramAnonymousLong) {
//            ViewSendLog.this.listItemDialog(paramAnonymousInt);
//        }
//    };
//    private TextView listEmpty;
//    AdapterView.OnItemLongClickListener listLongClick = new AdapterView.OnItemLongClickListener() {
//        public boolean onItemLongClick(AdapterView<?> paramAnonymousAdapterView, View paramAnonymousView, int paramAnonymousInt, long paramAnonymousLong) {
//            ViewSendLog.this.dialogListLongClick(paramAnonymousInt);
//            return false;
//        }
//    };
//    private ListView listView;
//    private ArrayList<SendLogInfo> objects;
//    private ImageView pImgEnabled;
//    private TextView pPhoneNumber;
//    private TextView pProfileName;
//    private TextView pRecentTime;
//    private TextView pRules;
//    private String phoneNumber;
//    private int phoneNumberId;
//    private String profileName;
//    private String recentTime;
//    private String rules;
//    private TextView textCount;
//    private TextView textRule;
//    private TextView textSendSMS;
//    private TextView textSendTime;
//    View.OnTouchListener touch = new View.OnTouchListener() {
//        public boolean onTouch(View paramAnonymousView, MotionEvent paramAnonymousMotionEvent) {
//            switch (paramAnonymousMotionEvent.getAction()) {
//                default:
//                    return false;
//                case 0:
//                    paramAnonymousView.setBackgroundColor(Color.rgb(0, 156, 255));
//                    return false;
//            }
//            paramAnonymousView.setBackgroundColor(Color.alpha(0));
//            return false;
//        }
//    };
//
//    private void deleteCheckDialog(final int paramInt1, final int paramInt2) {
//        final Dialog localDialog = new Dialog(this, 2131034112);
//        View localView = getLayoutInflater().inflate(2130903042, null);
//        final TextView localTextView = (TextView) localView.findViewById(2131099649);
//        switch (paramInt2) {
//        }
//        for (; ; ) {
//            Button localButton1 = (Button) localView.findViewById(2131099652);
//            Button localButton2 = (Button) localView.findViewById(2131099653);
//            localButton1.setBackgroundColor(Color.alpha(0));
//            localButton2.setBackgroundColor(Color.alpha(0));
//            localButton1.setOnTouchListener(this.touch);
//            localButton2.setOnTouchListener(this.touch);
//            localButton1.setOnClickListener(new View.OnClickListener() {
//                public void onClick(View paramAnonymousView) {
//                    switch (paramInt2) {
//                    }
//                    for (; ; ) {
//                        localDialog.cancel();
//                        return;
//                        localTextView.setText("방금 선택한 전송 이력을\n정말로 삭제하시겠습니까?");
//                        ViewSendLog.this.dbSendLogAdapter.deleteLog(((SendLogInfo) ViewSendLog.this.objects.get(paramInt1)).getId());
//                        ViewSendLog.this.refreshList();
//                        continue;
//                        localTextView.setText("전체 규칙을\n정말로 삭제하시겠습니까?");
//                        ViewSendLog.this.dbSendLogAdapter.deleteAll();
//                        ViewSendLog.this.refreshList();
//                    }
//                }
//            });
//            localButton2.setOnClickListener(new View.OnClickListener() {
//                public void onClick(View paramAnonymousView) {
//                    localDialog.cancel();
//                }
//            });
//            localDialog.setContentView(localView);
//            localDialog.show();
//            return;
//            localTextView.setText("방금 선택한 전송 이력을\n정말로 삭제하시겠습니까?");
//            continue;
//            localTextView.setText("전체 규칙을\n정말로 삭제하시겠습니까?");
//        }
//    }
//
//    private void dialogListLongClick(final int paramInt) {
//        AlertDialog.Builder localBuilder = new AlertDialog.Builder(this);
//        localBuilder.setTitle("수행할 작업을 선택해주세요.");
//        DialogInterface.OnClickListener local8 = new DialogInterface.OnClickListener() {
//            public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt) {
//                switch (paramAnonymousInt) {
//                }
//                for (; ; ) {
//                    paramAnonymousDialogInterface.cancel();
//                    return;
//                    ViewSendLog.this.dbSendLogAdapter.deleteLog(((SendLogInfo) ViewSendLog.this.objects.get(paramInt)).getId());
//                    ViewSendLog.this.refreshList();
//                }
//            }
//        };
//        localBuilder.setSingleChoiceItems(new CharSequence[]{"삭제"}, -1, local8);
//        localBuilder.create().show();
//    }
//
//    private ListAdapter getSendLogList() {
//        this.arraySendLog.clear();
//        this.cursor = this.dbSendLogAdapter.fetchLog(this.phoneNumber.replace("-", ""));
//        if (this.cursor.getCount() != 0) {
//            if (!this.cursor.moveToNext()) {
//                this.listEmpty.setVisibility(4);
//                this.listAdapter = new ListAdapter(this, 2130903050, this.arraySendLog);
//                this.listAdapter.notifyDataSetChanged();
//            }
//        }
//        for (; ; ) {
//            this.cursor.close();
//            return this.listAdapter;
//            int i = this.cursor.getInt(0);
//            String str1 = this.cursor.getString(1);
//            String str2 = this.cursor.getString(2);
//            String str3 = this.cursor.getString(3);
//            String str4 = this.cursor.getString(4);
//            String str5 = this.cursor.getString(5);
//            this.arraySendLog.add(new SendLogInfo(i, str1, str2, str3, str4, str5, true));
//            break;
//            this.listEmpty.setVisibility(0);
//        }
//    }
//
//    private void listItemDialog(final int paramInt) {
//        final Dialog localDialog = new Dialog(this, 2131034112);
//        View localView = getLayoutInflater().inflate(2130903041, null);
//        new Button(getApplicationContext());
//        Button localButton1 = (Button) localView.findViewById(2131099654);
//        Button localButton2 = (Button) localView.findViewById(2131099655);
//        localButton1.setBackgroundColor(Color.alpha(0));
//        localButton2.setBackgroundColor(Color.alpha(0));
//        localButton1.setOnTouchListener(this.touch);
//        localButton2.setOnTouchListener(this.touch);
//        localButton1.setText("방금 선택한 이력 삭제");
//        localButton2.setText("전체 전송 이력 삭제");
//        localButton1.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View paramAnonymousView) {
//                ViewSendLog.this.deleteCheckDialog(paramInt, 1);
//                localDialog.cancel();
//            }
//        });
//        localButton2.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View paramAnonymousView) {
//                ViewSendLog.this.deleteCheckDialog(paramInt, 2);
//                localDialog.cancel();
//            }
//        });
//        localDialog.setContentView(localView);
//        localDialog.show();
//    }
//
//    private void refreshList() {
//        this.listView.setAdapter(getSendLogList());
//    }
//
//    @Override
//    protected void onCreate(Bundle paramBundle) {
//        super.onCreate(paramBundle);
//        setContentView(R.layout.sendlog_list);
//        paramBundle = getIntent();
//        this.phoneNumberId = Integer.parseInt(paramBundle.getStringExtra("id"));
//        this.phoneNumber = paramBundle.getStringExtra("phoneNumber");
//        this.profileName = paramBundle.getStringExtra("profileName");
//        this.rules = paramBundle.getStringExtra("rules");
//        this.isEnabled = paramBundle.getStringExtra("enabled");
//        this.recentTime = paramBundle.getStringExtra("recentTime");
//        this.dbSendLogAdapter = new DBSendLogAdapter(this);
//        this.dbSendLogAdapter.open();
//        this.pProfileName = ((TextView) findViewById(2131099673));
//        this.pPhoneNumber = ((TextView) findViewById(2131099674));
//        this.pImgEnabled = ((ImageView) findViewById(2131099677));
//        this.pRules = ((TextView) findViewById(2131099675));
//        this.pRecentTime = ((TextView) findViewById(2131099676));
//        if (Boolean.valueOf(this.isEnabled).booleanValue()) {
//            paramBundle = getResources().getDrawable(2130837522);
//            this.pImgEnabled.setImageDrawable(paramBundle);
//            if (this.rules == null) {
//                break label391;
//            }
//            paramBundle = "전달 규칙: " + this.rules;
//            label235:
//            if (this.recentTime == null) {
//                break label398;
//            }
//        }
//        label391:
//        label398:
//        for (String str = "최근 전달 내역: " + this.recentTime; ; str = "(최근 전달 내역 없음)") {
//            this.pProfileName.setText("@" + this.profileName);
//            this.pPhoneNumber.setText(this.phoneNumber);
//            this.pRules.setText(paramBundle);
//            this.pRecentTime.setText(str);
//            this.listView = ((ListView) findViewById(2131099685));
//            this.listView.setOnItemClickListener(this.listClick);
//            this.listEmpty = ((TextView) findViewById(2131099666));
//            this.listEmpty.setVisibility(0);
//            refreshList();
//            return;
//            paramBundle = getResources().getDrawable(2130837520);
//            this.pImgEnabled.setImageDrawable(paramBundle);
//            break;
//            paramBundle = "(전달 규칙 없음)";
//            break label235;
//        }
//    }
//
//    @Override
//    protected void onPause() {
//        super.onPause();
//        this.dbSendLogAdapter.close();
//    }
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//        this.dbSendLogAdapter.open();
//    }
//
//    private class ListAdapter extends ArrayAdapter {
//        public ListAdapter(Context paramContext, int paramInt, ArrayList paramArrayList) {
//            super(paramInt, paramArrayList);
//            ViewSendLog.this.objects = paramArrayList;
//        }
//
//        public View getView(int paramInt, View paramView, ViewGroup paramViewGroup) {
//            paramViewGroup = paramView;
//            paramView = paramViewGroup;
//            if (paramViewGroup == null) {
//                paramView = ((LayoutInflater) ViewSendLog.this.getSystemService("layout_inflater")).inflate(R.layout.sendlog_list_row, null);
//            }
//            paramViewGroup = (SendLogInfo) ViewSendLog.this.objects.get(paramInt);
//            if (paramViewGroup != null) {
//                ViewSendLog.this.textCount = ((TextView) paramView.findViewById(2131099686));
//                ViewSendLog.this.textRule = ((TextView) paramView.findViewById(2131099687));
//                ViewSendLog.this.textSendTime = ((TextView) paramView.findViewById(2131099688));
//                ViewSendLog.this.textSendSMS = ((TextView) paramView.findViewById(2131099689));
//                ViewSendLog.this.textCount.setText("[ " + (paramInt + 1) + " ]");
//                ViewSendLog.this.textRule.setText("��������:" + paramViewGroup.getThisRule());
//                ViewSendLog.this.textSendTime.setText(paramViewGroup.getSendTime().replace(") ", ")\n"));
//                ViewSendLog.this.textSendSMS.setText(paramViewGroup.getSendText());
//            }
//            return paramView;
//        }
//    }
}