package com.example.myapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this, R.array.carreras, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.cmbCareer.setAdapter(adapter);

        binding.btnSave.setOnClickListener(v -> validationAndSave());
        binding.btnEdit.setOnClickListener(v -> showForm());
    }

    private void validationAndSave() {
        String name = binding.txtName.getText().toString().trim();
        String id = binding.txtID.getText().toString().trim();
        boolean careerEmpty = binding.cmbCareer.getSelectedItemPosition() == 0;

        boolean valid = true;

        if (name.isEmpty()) {
            binding.txtName.setError(getString(R.string.error_nombre));
            valid = false;
        }
        if (id.isEmpty()) {
            binding.txtID.setError(getString(R.string.error_matricula));
            valid = false;
        }
        binding.tvErrorCarrera.setVisibility(careerEmpty ? View.VISIBLE : View.GONE);
        if (careerEmpty) {
            valid = false;
        }

        if (!valid) {
            return;
        }

        String career = binding.cmbCareer.getSelectedItem().toString();
        showSummary(name, id, career);
        Toast.makeText(this, R.string.toast_guardado, Toast.LENGTH_SHORT).show();
    }

    private void showSummary(String name, String id, String career) {
        binding.tvSaludo.setText(getString(R.string.saludo, name));
        binding.tvNombre.setText(name);
        binding.tvMatricula.setText(id);
        binding.tvCarrera.setText(career);
        binding.layoutFormulario.setVisibility(View.GONE);
        binding.layoutResumen.setVisibility(View.VISIBLE);
    }

    private void showForm() {
        binding.layoutResumen.setVisibility(View.GONE);
        binding.layoutFormulario.setVisibility(View.VISIBLE);
    }
}
