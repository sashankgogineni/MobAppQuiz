
        package com.example.jacobevans.mymobilepopquiz;

        import android.content.Context;
        import android.content.DialogInterface;
        import android.content.Intent;
        import android.database.Cursor;
        import android.database.sqlite.SQLiteDatabase;
        import android.os.Bundle;
        import android.support.annotation.Nullable;
        import android.support.v7.app.AlertDialog;
        import android.support.v7.app.AppCompatActivity;
        import android.view.View;
        import android.widget.Button;
        import android.widget.TextView;
        import android.widget.Toast;

/**
 * Created by swethagatty on 15/11/16.
 */

public class Parents extends AppCompatActivity {

    private TextView nametv,scoretv;
    private Intent i;
    private SQLiteDatabase db=null;
    private Login_Preference l;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_parent);
        nametv  =(TextView)findViewById(R.id.nametabletxt);
        scoretv=(TextView)findViewById(R.id.scoretabletxt);
        TextView lgtv = (TextView) findViewById(R.id.logtxt);
        Button qbtn = (Button) findViewById(R.id.qbutton);
        l=new Login_Preference(getApplicationContext());
        i=getIntent();
        db=openOrCreateDatabase("MyMobilePopQuiz.db", Context.MODE_PRIVATE,null);
        drawtable();
        lgtv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                l.logout();
                i=new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(i);
            }
        });
        qbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }
    public void drawtable(){
        try {
            String names = "", scores = "";
            Cursor c = db.rawQuery("SELECT USERNAME,NAME,PREVBEST FROM user_table ORDER BY PREVBEST DESC LIMIT 10", null);
            if (c != null && c.getCount() != 0) {
                if (c.moveToFirst()) {
                    do {
                        names += c.getString(c.getColumnIndex("NAME")) + "\n";
                        scores += c.getString(c.getColumnIndex("PREVBEST")) + "\n";
                    } while (c.moveToNext());
                }
                nametv.setText(names);
                scoretv.setText(scores);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @Override
    public void onBackPressed(){
        new AlertDialog.Builder(this).setIcon(android.R.drawable.ic_dialog_alert).setTitle("Exit!")
                .setMessage("Are you sure?")
                .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Intent intent = new Intent(Intent.ACTION_MAIN);
                        intent.addCategory(Intent.CATEGORY_HOME);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish();
                    }
                }).setNegativeButton("no", null).show();
    }
}
