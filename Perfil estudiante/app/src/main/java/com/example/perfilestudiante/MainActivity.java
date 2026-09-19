package com.example.perfilestudiante;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.perfilestudiante.databinding.ActivityMainBinding;

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

        binding.txtID.addTextChangedListener(new TextWatcher() {
            private boolean formatting = false;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (formatting) {
                    return;
                }
                formatting = true;
                String digits = s.toString().replaceAll("\\D", "");
                if (digits.length() > 8) {
                    digits = digits.substring(0, 8);
                }
                String formatted = digits.length() > 4
                        ? digits.substring(0, 4) + "-" + digits.substring(4)
                        : digits;
                s.replace(0, s.length(), formatted);
                formatting = false;
            }
        });

        binding.btnSave.setOnClickListener(v -> validationAndSave());
        binding.btnEdit.setOnClickListener(v -> showForm());
    }

    private void validationAndSave() {
        String name = binding.txtName.getText().toString().trim();
        String id = binding.txtID.getText().toString().trim();

        boolean valid = true;

        if (name.isEmpty()) {
            binding.txtName.setError(getString(R.string.error_nombre));
            valid = false;
        }
        if (id.isEmpty()) {
            binding.txtID.setError(getString(R.string.error_matricula));
            valid = false;
        } else if (id.length() < 9) {
            binding.txtID.setError(getString(R.string.error_matricula_formato));
            valid = false;
        }
        boolean careerEmpty = binding.cmbCareer.getSelectedItemPosition() == 0;
        binding.tvCareerError.setVisibility(careerEmpty ? View.VISIBLE : View.GONE);
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
        binding.tvGreeting.setText(getString(R.string.saludo, name));
        binding.tvName.setText(name);
        binding.tvId.setText(id);
        binding.tvCareer.setText(career);
        binding.layoutForm.setVisibility(View.GONE);
        binding.layoutSummary.setVisibility(View.VISIBLE);
    }

    private void showForm() {
        binding.layoutSummary.setVisibility(View.GONE);
        binding.layoutForm.setVisibility(View.VISIBLE);
    }
}
