package patrick.notes;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.database.Cursor;

public class NotesActivity extends AppCompatActivity {

    DatabaseHelper mDatabaseHelper;
    private Button btnSave, btnRestoreData, btnNew;
    private TextView tv;

    String newEntry;

    /**
     * btnSave it will save the data form the TextView in the database (new and update)
     * btnRestoreData it will change the activiti to one to select data and delet data
     * btnNew creates a new data on the database
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);
        tv = (TextView) findViewById(R.id.editNote);
        btnRestoreData = (Button) findViewById(R.id.btnRestore);
        btnSave = (Button) findViewById(R.id.btnSave);
        btnNew = (Button) findViewById(R.id.btnNew);
        mDatabaseHelper = new DatabaseHelper(this);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onInsert();
            }
        });

        btnRestoreData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //onRestore();
                Intent intent = new Intent(NotesActivity.this, SavesActivity.class);
                startActivity(intent);
            }
        });

        btnNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //onInsert();
                Background.text = "";
                Background.ids = 0;
                tv.setText("");
            }
        });
        getData();
    }

    /**
     * gets data and prints it on the TextView
     */
    protected void getData() {
        Cursor newEntry2 = mDatabaseHelper.getData2();
        while (newEntry2.moveToNext()) {
            tv.setText(newEntry2.getString(1));
        }
    }

    /**
     * if its the first time for opening the App it will create a nwe data on the database
     * if its a selected data from the database it will update the data
     */
    protected void onInsert() {
        if (Background.ids <= 0) {
            newEntry = String.valueOf(tv.getText());
            mDatabaseHelper.addData(newEntry);
        } else {
            Background.text = String.valueOf(tv.getText());
            mDatabaseHelper.getUpdate();
        }
    }
}
