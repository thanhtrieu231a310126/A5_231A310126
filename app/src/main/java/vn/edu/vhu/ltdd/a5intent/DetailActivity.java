package vn.edu.vhu.ltdd.a5intent;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.IntentCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class DetailActivity extends AppCompatActivity {

    private Contact contact;
    private EditText edtHoTenMoi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_detail);

        ViewCompat.setOnApplyWindowInsetsListener(
                findViewById(R.id.main),
                (v, insets) -> {
                    Insets bars = insets.getInsets(
                            WindowInsetsCompat.Type.systemBars()
                    );

                    v.setPadding(
                            bars.left,
                            bars.top,
                            bars.right,
                            bars.bottom
                    );

                    return insets;
                }
        );

        TextView tvThongTin = findViewById(R.id.tvThongTin);
        TextView tvNguoiGui = findViewById(R.id.tvNguoiGui);
        edtHoTenMoi = findViewById(R.id.edtHoTenMoi);

        Button btnLuu = findViewById(R.id.btnLuu);
        Button btnHuy = findViewById(R.id.btnHuy);

        contact = IntentCompat.getParcelableExtra(
                getIntent(),
                MainActivity.EXTRA_CONTACT,
                Contact.class
        );

        String nguoiGui = getIntent().getStringExtra(
                MainActivity.EXTRA_NGUOI_GUI
        );

        if (contact == null) {
            tvThongTin.setText(R.string.no_data);
            btnLuu.setEnabled(false);
        } else {
            tvThongTin.setText(
                    getString(
                            R.string.detail_format,
                            contact.getHoTen(),
                            contact.getDienThoai(),
                            contact.getEmail()
                    )
            );

            edtHoTenMoi.setText(contact.getHoTen());
        }

        tvNguoiGui.setText(
                getString(
                        R.string.sent_by,
                        nguoiGui == null ? "" : nguoiGui
                )
        );

        btnLuu.setOnClickListener(v -> luuVaQuayLai());

        btnHuy.setOnClickListener(v -> {
            setResult(RESULT_CANCELED);
            finish();
        });
    }

    private void luuVaQuayLai() {
        String tenMoi =
                edtHoTenMoi.getText().toString().trim();

        if (tenMoi.isEmpty()) {
            edtHoTenMoi.setError(
                    getString(R.string.err_empty)
            );
            return;
        }

        contact.setHoTen(tenMoi);

        Intent data = new Intent();
        data.putExtra(
                MainActivity.EXTRA_CONTACT,
                contact
        );

        setResult(RESULT_OK, data);
        finish();
    }
}