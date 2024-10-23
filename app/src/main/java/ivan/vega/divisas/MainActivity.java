package ivan.vega.divisas;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    double tasaCambio = 1.0;
    EditText inputValor;
    TextView resultado;
    RadioGroup redioGroupDivisas;
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
        Button borrarButon = findViewById(R.id.button13);








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
}