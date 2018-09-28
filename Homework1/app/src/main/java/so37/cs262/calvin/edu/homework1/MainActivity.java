package so37.cs262.calvin.edu.homework1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.view.View.OnClickListener;
import android.widget.AdapterView.OnItemSelectedListener;

public class MainActivity extends AppCompatActivity  {
    double valueone;
    double valuetwo;
    EditText valone, valtwo;
    TextView resultval;
    Button cal_button;
    Spinner spinner;
    String operator;

    private OnClickListener myClickListener = new OnClickListener() {
        public void onClick(View v) {
            operator = spinner.getSelectedItem().toString();
            valueone = Double.parseDouble(valone.getText().toString());
            valuetwo = Double.parseDouble(valtwo.getText().toString());
            if (operator == "+") {
                resultval.setText((valueone + valuetwo) + "");
            } else if (operator == "-") {
                resultval.setText((valueone - valuetwo) + "");
            } else if (operator == "%") {
                resultval.setText((valueone / valuetwo) + "");
            } else if (operator == "x") {
                resultval.setText((valueone * valuetwo) + "");
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spinner = findViewById(R.id.operatorSpinner);

        resultval = findViewById(R.id.result);
        cal_button = findViewById(R.id.button);
        cal_button.setOnClickListener(myClickListener);
        valone = findViewById(R.id.valoneText);
        valtwo = findViewById(R.id.valtwoText);

        ArrayAdapter<CharSequence> adapter= ArrayAdapter.createFromResource(this,
                R.array.operators, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);


    }
}