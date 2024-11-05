package ivan.vega.divisas;


import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;


import androidx.activity.EdgeToEdge;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.text.DecimalFormat;


public class MainActivity extends AppCompatActivity {
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

        /*
        Usando en metodo configurarBotonNumero, establecemos un listener a los botones, con su valor correspondiente
         */

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
        comaButton.setOnClickListener(view -> {
            //Pillamos el valor actual
            String valorActual = inputValor.getText().toString();
            //Comprovamos si ya existe una coma
            if(!valorActual.contains(".")){
                inputValor.append(".");
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

                //verificamos que no supera los 2 decimales
                String texto = s.toString();

                if(texto.startsWith(".")){
                    s.replace(0,1,"0.");
                    return;
                }
                // Comprueba si el texto contiene un punto decimal
                int indexDecimal = texto.indexOf(".");
                if (indexDecimal >= 0) {
                    // Limita a dos decimales
                    if (texto.length() - indexDecimal - 1 > 2) {
                        s.delete(indexDecimal + 3, texto.length());
                    }
                }
            }
        });

        redioGroupDivisas.setOnCheckedChangeListener((group, checkedId) -> {
            String valorTexto = inputValor.getText().toString();
            if(!valorTexto.isEmpty()){
                resultado.setText(realizarCambioConversion(valorTexto));
            }

        });

        Button ceButton = findViewById(R.id.button12);
        ceButton.setOnClickListener(v -> {
            //Cuando se pulse este boton, el imput se tiene que limbiar,
            inputValor.setText("");
        });

        Button borrarButon = findViewById(R.id.button13);
        borrarButon.setOnClickListener(v -> {
            //Tenemos que eliminar el ultimo elemento
            String valorActual = inputValor.getText().toString();

            if(!valorActual.isEmpty()){
                String nuevoValor = valorActual.substring(0,valorActual.length() -1);
                inputValor.setText(nuevoValor);
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
        boton.setOnClickListener(view -> inputValor.append(valor));
    }
    private String realizarCambioConversion(String valorTexto){
        //Este metodo pido el valor actual del input, en que divisa de conversion se encuentra, y finalmente el resultado, para cambiarlo

        if(valorTexto.isEmpty() || valorTexto.equals(".")) {
            return "0.0";
        }

        double resultado = 0;
        String suffix = "";

        //Convertimos a double para poder operar
        double valor = Double.parseDouble(valorTexto);


        //Seguramente lo cambia a un switch mas adelante
        if(radioButtonDOL.isChecked()){
            //Pillamos el imput y, lo convertimos y devolvemos
            resultado = valor * EUR_TO_DOLL;
            suffix = "USD $";
        }
        if(radioButtonLB.isChecked()){
            resultado = valor * EUR_TO_LIB;
            suffix = "£";
        }
        if(radioButtonYEN.isChecked()){
            resultado = valor * EUR_TO_YEN;
            suffix = "JAP ¥";
        }
        if(radioButtonYU.isChecked()){
            resultado = valor * EUR_TO_YUAN;
            suffix = "CHN ¥";
        }

        //Esto sinceramente lo ha hecho GPT xk no tengo ni idea de formatear cosas
        DecimalFormat decimalFormat = new DecimalFormat("#,##0.00");
        return decimalFormat.format(resultado) + "  " + suffix;
    }

}