package de.kojyx.lgscramer;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    // Deklaration SharedPreferences
    private SharedPreferences speicher;
    private SharedPreferences.Editor editor;

    // Deklaration TextView
    private TextView tv_result, tv_versionName;

    // Deklaration Button
    private Button btn_solve, btn_delete;

    // Deklaration Boolean
    private boolean dark;

    // Deklaration EditText
    private EditText et_a0, et_a1, et_a2;
    private EditText et_b0, et_b1, et_b2;
    private EditText et_c0, et_c1, et_c2;
    private EditText et_e0, et_e1, et_e2;

    // Deklaration Double
    private double[] a = new double[5];
    private double[] b = new double[5];
    private double[] c = new double[5];
    private double[] e = new double[5];
    private double[] h = new double[5];

    private double normale_determinante;
    private double[] determinante = new double[3];

    // onCreate-Methode -> Wird beim Start der Klasse ausgeführt
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // Initialisierung der "speicher"-Variable
        speicher = getApplicationContext().getSharedPreferences("Data", 0);
        // Abruf des Wertes für die "dark"-Variable aus dem Speicher (bestimmt, ob DarkMode aktiv ist oder nicht)
        dark = speicher.getBoolean("dark", false);

        // Wenn dark true ist, dann wird das Dark-Theme verwendet, sonst das Light-Theme
        if (dark) {
            setTheme(R.style.AppThemeDark);
        } else {
            setTheme(R.style.AppTheme);
        }
        // Layout wird angezeigt
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialisierung TexView
        tv_result = (TextView) findViewById(R.id.tv_result);
        tv_versionName = (TextView) findViewById(R.id.tv_versionName);

        // VersionName wird angezeigt
        tv_versionName.setText("Version " + BuildConfig.VERSION_NAME);

        // Initialisierung Button
        btn_solve = (Button) findViewById(R.id.btn_solve);
        btn_delete = (Button) findViewById(R.id.btn_delete);

        // Initialisierung EditText
        et_a0 = (EditText) findViewById(R.id.et_a0);
        et_a1 = (EditText) findViewById(R.id.et_a1);
        et_a2 = (EditText) findViewById(R.id.et_a2);

        et_b0 = (EditText) findViewById(R.id.et_b0);
        et_b1 = (EditText) findViewById(R.id.et_b1);
        et_b2 = (EditText) findViewById(R.id.et_b2);

        et_c0 = (EditText) findViewById(R.id.et_c0);
        et_c1 = (EditText) findViewById(R.id.et_c1);
        et_c2 = (EditText) findViewById(R.id.et_c2);

        et_e0 = (EditText) findViewById(R.id.et_e0);
        et_e1 = (EditText) findViewById(R.id.et_e1);
        et_e2 = (EditText) findViewById(R.id.et_e2);

        // Setzen eines OnClickListeners
        btn_solve.setOnClickListener(this);
        btn_delete.setOnClickListener(this);
    }

    // OnClick-Methode -> Wird beim Betätigen eines Buttons aufgerufen
    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_solve:
                /*
                Beim Betätigen wird überprüft, ob alle EditText gefüllt sind
                → nicht gefüllt: Liefern eines Errors
                → gefüllt: Werte in double umwandeln und solve()-Methode aufrufen
                */
                if (TextUtils.isEmpty(et_a0.getText().toString()) || et_a0.getText().toString().equals(".")) {
                    et_a0.setError("Ungültige Eingabe!");
                } else if (TextUtils.isEmpty(et_a1.getText().toString()) || et_a1.getText().toString().equals(".")) {
                    et_a1.setError("Ungültige Eingabe!");
                } else if (TextUtils.isEmpty(et_a2.getText().toString()) || et_a2.getText().toString().equals(".")) {
                    et_a2.setError("Ungültige Eingabe!");
                } else if (TextUtils.isEmpty(et_b0.getText().toString()) || et_b0.getText().toString().equals(".")) {
                    et_b0.setError("Ungültige Eingabe!");
                } else if (TextUtils.isEmpty(et_b1.getText().toString()) || et_b1.getText().toString().equals(".")) {
                    et_b1.setError("Ungültige Eingabe!");
                } else if (TextUtils.isEmpty(et_b2.getText().toString()) || et_b2.getText().toString().equals(".")) {
                    et_b2.setError("Ungültige Eingabe!");
                } else if (TextUtils.isEmpty(et_c0.getText().toString()) || et_c0.getText().toString().equals(".")) {
                    et_c0.setError("Ungültige Eingabe!");
                } else if (TextUtils.isEmpty(et_c1.getText().toString()) || et_c1.getText().toString().equals(".")) {
                    et_c1.setError("Ungültige Eingabe!");
                } else if (TextUtils.isEmpty(et_c2.getText().toString()) || et_c2.getText().toString().equals(".")) {
                    et_c2.setError("Ungültige Eingabe!");
                } else if (TextUtils.isEmpty(et_e0.getText().toString()) || et_e0.getText().toString().equals(".")) {
                    et_e0.setError("Ungültige Eingabe!");
                } else if (TextUtils.isEmpty(et_e1.getText().toString()) || et_e1.getText().toString().equals(".")) {
                    et_e1.setError("Ungültige Eingabe!");
                } else if (TextUtils.isEmpty(et_e2.getText().toString()) || et_e2.getText().toString().equals(".")) {
                    et_e2.setError("Ungültige Eingabe!");
                } else {
                    a[0] = Double.parseDouble(et_a0.getText().toString());
                    a[1] = Double.parseDouble(et_a1.getText().toString());
                    a[2] = Double.parseDouble(et_a2.getText().toString());
                    a[3] = a[0];
                    a[4] = a[1];

                    b[0] = Double.parseDouble(et_b0.getText().toString());
                    b[1] = Double.parseDouble(et_b1.getText().toString());
                    b[2] = Double.parseDouble(et_b2.getText().toString());
                    b[3] = b[0];
                    b[4] = b[1];

                    c[0] = Double.parseDouble(et_c0.getText().toString());
                    c[1] = Double.parseDouble(et_c1.getText().toString());
                    c[2] = Double.parseDouble(et_c2.getText().toString());
                    c[3] = c[0];
                    c[4] = c[1];

                    e[0] = Double.parseDouble(et_e0.getText().toString());
                    e[1] = Double.parseDouble(et_e1.getText().toString());
                    e[2] = Double.parseDouble(et_e2.getText().toString());
                    e[3] = e[0];
                    e[4] = e[1];

                    // Haupt-Methode wird aufgerufen
                    solve();
                }
                break;

            case R.id.btn_delete:
                // Beim Betätigen wird jeglicher Inhalt der EditText und TextView gelöscht
                et_a0.setText("");
                et_a1.setText("");
                et_a2.setText("");
                et_b0.setText("");
                et_b1.setText("");
                et_b2.setText("");
                et_c0.setText("");
                et_c1.setText("");
                et_c2.setText("");
                et_e0.setText("");
                et_e1.setText("");
                et_e2.setText("");

                tv_result.setText("");
                break;
        }
    }

    // solve-Methode -> Berechnung der Ergebnisse mittels Cramer-Verfahren
    private void solve() {
        // Die normale Determinante wird gebildet
        normale_determinante = determinante();

        for (int i = 0; i <= 2; i++) {
            for (int j = 0; j <= 4; j++) {
                if (i == 0) {
                    // a-Werte werden einer Hilfsvariable h zugewiesen und durch die e-Werte erstetzt
                    h[j] = a[j];
                    a[j] = e[j];
                }
                if (i == 1) {
                    // b-Werte werden einer Hilfsvariable h zugewiesen und durch die e-Werte erstetzt
                    h[j] = b[j];
                    b[j] = e[j];
                }
                if (i == 2) {
                    // c-Werte werden einer Hilfsvariable h zugewiesen und durch die e-Werte erstetzt
                    h[j] = c[j];
                    c[j] = e[j];
                }
            }

            // die aktuelle Determinante wird berechnet
            determinante[i] = determinante();

            for (int k = 0; k <= 4; k++) {
                if (i == 0) {
                    // a-Werte werden durch die h-Werte ersetzt und haben somit ihren alten Werte wieder
                    a[k] = h[k];
                }
                if (i == 1) {
                    // b-Werte werden durch die h-Werte ersetzt und haben somit ihren alten Werte wieder
                    b[k] = h[k];
                }
                if (i == 2) {
                    // c-Werte werden durch die h-Werte ersetzt und haben somit ihren alten Werte wieder
                    c[k] = h[k];
                }
            }
        }

        // Wenn alle Determinanten = 0 -> Unendlich viele Lösungen
        if (normale_determinante == 0 && determinante[0] == 0 && determinante[1] == 0 && determinante[2] == 0) {
            tv_result.setText("Unendlich viele Lösungen!");

        // Wenn normale_determinante = 0 -> Keine Lösung
        } else if (normale_determinante == 0) {
            tv_result.setText("Nicht lösbar!");

        // Sonst Berechnung der Lösung
        } else {
            // Berechnung der Parameterwerte a, b und c
            double aa = determinante[0] / normale_determinante;
            double bb = determinante[1] / normale_determinante;
            double cc = determinante[2] / normale_determinante;

            // Werte werden auf drei Nachkommastellen gerundet
            aa = Math.round(aa * 1000.0) / 1000.0;
            bb = Math.round(bb * 1000.0) / 1000.0;
            cc = Math.round(cc * 1000.0) / 1000.0;

            // Ausgeben der Ergebnisse
            tv_result.setText("a = " + aa + "\nb = " + bb + "\nc = " + cc);
        }
    }

    // determinante-Methode -> Berechnet Determinante
    private double determinante() {
        // Wird durch Formel berechnet
        double hl = 0;
        double nl = 0;
        double dm = 0;
        for (int i = 0; i <= 2; i++) {
            hl = hl + (a[i] * b[i + 1] * c[i + 2]);
            nl = nl + (c[i] * b[i + 1] * a[i + 2]);
        }
        dm = hl - nl;
        return dm;
    }

    // onCreateOptionsMenu-Methode -> Initialisiert die MenuBar
    @Override
    public boolean onCreateOptionsMenu (Menu menu) {
        if (dark) {
            getMenuInflater().inflate(R.menu.menu_main_dark, menu);
        } else {
            getMenuInflater().inflate(R.menu.menu_main_light, menu);
        }
        return true;
    }

    // onPotionsItemSelected-Methode -> Methode, die Aktionen in der MenuBar erkennt
    @Override
    public boolean onOptionsItemSelected (MenuItem item) {
        int id = item.getItemId();

        // Wenn "Theme"-Button betätigt wird -> Dark-/Light-Mode wird aktiviert
        if (id == R.id.action_mode) {
            dark = !dark;

            editor = speicher.edit();
            editor.putBoolean("dark", dark);
            editor.apply();

            recreate();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
