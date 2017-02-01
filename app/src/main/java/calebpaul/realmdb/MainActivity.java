package calebpaul.realmdb;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import calebpaul.realmdb.model.Person;
import io.realm.Realm;
import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity {

    private EditText editTextName;
    private EditText editTextAge;
    private Button buttonSave;
    private TextView textViewLog;
    private MainActivity mContext;
    private Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContext = MainActivity.this;
        editTextName = (EditText) findViewById(R.id.editTextName);
        editTextAge = (EditText) findViewById(R.id.editTextAge);
        buttonSave = (Button) findViewById(R.id.buttonSave);
        textViewLog = (TextView) findViewById(R.id.textViewLog);

        realm = Realm.getDefaultInstance();

        buttonSave.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //TODO: Add Validation, video ad 6:10
                save_into_database(editTextName.getText().toString().trim(), Integer.parseInt(editTextAge.getText().toString().trim()));
                refresh_views();
            }
        });

        refresh_views();

    }

    private void refresh_views() {
        //Queries
        RealmResults<Person> personRealmResults = realm.where(Person.class).findAll();
        String output = "";
        for (Person person : personRealmResults) {
            output += person.toString() + " \n";
        }
        textViewLog.setText(output);
    }

    private void save_into_database(final String name, final int age) {
        //Writes Asynchronous Transactions
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm bgRealm) {
                Person person = bgRealm.createObject(Person.class);
                person.setName(name);
                person.setAge(age);
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                Log.v("DB", ">>>input stored<<<");
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                Log.v("DB", ">>> "+error.getMessage()+" <<<");
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.close();
    }
}
