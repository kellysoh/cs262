package so37.cs262.calvin.edu.homework1;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Spinner;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.view.View.OnClickListener;




public class MainActivity extends AppCompatActivity {
    Spinner spinner = (Spinner) findViewById(R.id.operator_spinner);
    double valueone;
    double valuetwo;
    EditText valone, valtwo;
    TextView resultval;
    Button cal_button;

    private OnClickListener myClickListener = new OnClickListener()
    {
        public void onClick(View v) {
            String operator = spinner.getSelectedItem().toString();
            valueone = Double.parseDouble(valone.getText().toString());
            valuetwo = Double.parseDouble(valtwo.getText().toString());
            if (operator == "+" ) {
                resultval.setText(valueone + valuetwo +"");}
            else if (operator == "-") {
                resultval.setText(valueone - valuetwo+""); }
            else if (operator == "%") {
                resultval.setText(valueone / valuetwo+""); }
            else if (operator == "x") {
                resultval.setText(valueone * valuetwo+""); }
        }
    };
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ArrayAdapter<CharSequence> adaptor = ArrayAdapter.createFromResource(this,
                R.array.operators, android.R.layout.simple_spinner_item);
        adaptor.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adaptor);

        valone =  findViewById(R.id.valoneText);
        valtwo = findViewById(R.id.valtwoText);
        resultval =  findViewById(R.id.result);
        cal_button = findViewById(R.id.button);
        cal_button.setOnClickListener(myClickListener);


    }



}


