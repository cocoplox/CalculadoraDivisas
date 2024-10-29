package ivan.vega.divisas;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import ivan.vega.divisas.R;

import androidx.activity.EdgeToEdge;
import androidx.annotation.IdRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    double tasaCambio = 1.0;
    EditText inputValor;
    TextView resultado;
    RadioGroup redioGroupDivisas;


    RadioButton radioButtonDOL;
    RadioButton radioButtonYEN;
    RadioButton radioButtonLB;
    RadioButton radioButtonYU;



    public static final Double EUR_TO_DOLL = 1.08;
    public static final Double EUR_TO_YUAN = 7.67;
    public static final Double EUR_TO_YEN = 164.38;
    public static final Double EUR_TO_LIB = 0.83;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);


        //Vamos al lio, objetivo : Hacerlo a tiempo real


        //Atentos al fumadote para concatenar los numeros
        //Esta vaina les da un onClickListener, que lo que hace es añadir el valor al final del
        //input,
        inputValor = findViewById(R.id.editTextNumberDecimal); // Lo que ingresa el usuario
        resultado = findViewById(R.id.textView2);
        redioGroupDivisas = findViewById(R.id.radioGroup);

        configurarBotonNumerico(R.id.button10,"0",inputValor);
        configurarBotonNumerico(R.id.button1,"1",inputValor);
        configurarBotonNumerico(R.id.button2,"2",inputValor);
        configurarBotonNumerico(R.id.button3,"3",inputValor);
        configurarBotonNumerico(R.id.button4,"4",inputValor);
        configurarBotonNumerico(R.id.button5,"5",inputValor);
        configurarBotonNumerico(R.id.button6,"6",inputValor);
        configurarBotonNumerico(R.id.button7,"7",inputValor);
        configurarBotonNumerico(R.id.button8,"8",inputValor);
        configurarBotonNumerico(R.id.button9,"9",inputValor);

         radioButtonDOL = findViewById(R.id.radioButtonDolar);
         radioButtonYEN = findViewById(R.id.radioButtonYen);
         radioButtonLB = findViewById(R.id.radioButtonLibra);
         radioButtonYU = findViewById(R.id.radioButtonYuan);


         radioButtonDOL.toggle();
        //La coma funciona algo diferente

        Button comaButton = findViewById(R.id.button11);
        comaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Pillamos el valor actual
                String valorActual = inputValor.getText().toString();
                //Comprovamos si ya existe una coma
                if(!valorActual.contains(".")){
                    inputValor.append(".");
                }
            }
        });


        //Aqui realmente viene la chicha

        inputValor.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //No lo vamos a usar
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //Cada vez que el texto cambio, queremos que pille el imput, lo convierta y lo muestre

                resultado.setText(realizarCambioConversion(s.toString()));

            }

            @Override
            public void afterTextChanged(Editable s) {
                //No lo vamos a usar
            }
        });

        redioGroupDivisas.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                String valorTexto = inputValor.getText().toString();
                if(!valorTexto.isEmpty()){
                    resultado.setText(realizarCambioConversion(valorTexto));
                }

            }
        });

        Button ceButton = findViewById(R.id.button12);
        ceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Cuando se pulse este boton, el imput se tiene que limbiar,
                inputValor.setText("");
            }
        });

        Button borrarButon = findViewById(R.id.button13);
        borrarButon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Tenemos que eliminar el ultimo elemento
                String valorActual = inputValor.getText().toString();

                if(!valorActual.isEmpty()){
                    String nuevoValor = valorActual.substring(0,valorActual.length() -1);
                    inputValor.setText(nuevoValor);
                }
            }
        });







        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
    private void configurarBotonNumerico(int botonID, String valor, final EditText inputValor){
        Button boton = findViewById(botonID);
        boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                inputValor.append(valor);
            }
        });
    }
    private String realizarCambioConversion(String valorTexto){
        //Este metodo pido el valor actual del input, en que divisa de conversion se encuentra, y finalmente el resultado, para cambiarlo

        double resultado = 0;
        String suffix = "";

        if(!valorTexto.isEmpty()){
            //Convertimos a double para poder operar
            double valor = Double.parseDouble(valorTexto);


            //Seguramente lo cambia a un switch mas adelante
            if(radioButtonDOL.isChecked()){
                //Pillamos el imput y, lo convertimos y devolvemos
                resultado = valor * EUR_TO_DOLL;
                suffix = "UDS";
            }
            if(radioButtonLB.isChecked()){
                resultado = valor * EUR_TO_LIB;
                suffix = "GBP";
            }
            if(radioButtonYEN.isChecked()){
                resultado = valor * EUR_TO_YEN;
                suffix = "JPY";
            }
            if(radioButtonYU.isChecked()){
                resultado = valor * EUR_TO_YUAN;
                suffix = "CNY";
            }


        }


        //Esto sinceramente lo ha hecho GPT xk no tengo ni idea de formatear cosas
        DecimalFormat decimalFormat = new DecimalFormat("#,##0.00");
        String resultadoTexto = decimalFormat.format(resultado) + "  " + suffix;
        return resultadoTexto;
    }

}