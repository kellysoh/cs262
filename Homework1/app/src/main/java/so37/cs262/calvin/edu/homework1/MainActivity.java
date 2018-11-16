package so37.cs262.calvin.edu.homework1;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity  {
    double valueone;
    double valuetwo;
    EditText valone, valtwo;
    TextView resultval;

    Spinner spinner;
    String operator;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        spinner = findViewById(R.id.operatorSpinner);
        ArrayAdapter<CharSequence> adapter= ArrayAdapter.createFromResource(this,
                R.array.operators, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

    }
    //Calculate function activates when the calculate button is clicked.
    public void calculate(View v) {
        valone = findViewById(R.id.valoneText);
        valtwo = findViewById(R.id.valtwoText);
        resultval = findViewById(R.id.result);
        spinner = findViewById(R.id.operatorSpinner);
        operator = spinner.getSelectedItem().toString();
        valueone = Double.parseDouble(valone.getText().toString());
        valuetwo = Double.parseDouble(valtwo.getText().toString());
        //According to the operator type, get the right result.
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
}
