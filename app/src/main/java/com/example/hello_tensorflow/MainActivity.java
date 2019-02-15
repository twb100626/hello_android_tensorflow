package com.example.hello_tensorflow;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.view.View;
import org.tensorflow.contrib.android.TensorFlowInferenceInterface;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText editText_one, editText_two, editText_three;
    private TextView text_one, text_two;

    private TensorFlowInferenceInterface infer_Interface;
    private String MODEL_FILE = "file:///android_asset/optimized_android_tf.pb";

    private String INPUT_NODE = "I";
    private String OUTPUT_NODE = "O";

    static {
        System.loadLibrary("tensorflow_inference");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText_one = (EditText)findViewById(R.id.edittext_one);
        editText_two = (EditText)findViewById(R.id.edittext_two);
        editText_three = (EditText)findViewById(R.id.edittext_three);
        text_one = (TextView)findViewById(R.id.text1);
        text_two = (TextView)findViewById(R.id.text2);
        findViewById(R.id.button).setOnClickListener(this);

        infer_Interface = new TensorFlowInferenceInterface(getAssets(),MODEL_FILE);
    }

    @Override
    public void onClick(View v) {
        float f1 = Float.valueOf(editText_one.getText().toString());
        float f2 = Float.valueOf(editText_two.getText().toString());
        float f3 = Float.valueOf(editText_three.getText().toString());
        float[] inputFloats = {f1,f2,f3};
        infer_Interface.feed(INPUT_NODE,inputFloats,1,3);

        float[] run_result = new float[2];
        infer_Interface.run(new String[]{OUTPUT_NODE});
        infer_Interface.fetch(OUTPUT_NODE,run_result);
        text_one.setText(String.valueOf(run_result[0]));
        text_two.setText(String.valueOf(run_result[1]));
    }
}
