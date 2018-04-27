package cardoso.guilherme.graphql;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.apollographql.apollo.ApolloCall;
import com.apollographql.apollo.ApolloClient;
import com.apollographql.apollo.api.Response;
import com.apollographql.apollo.exception.ApolloException;

import javax.annotation.Nonnull;

import cardoso.guilherme.graphql.api.CreateLinkMutation;

public class CreateActivity extends AppCompatActivity {

    EditText urlText;
    EditText descriptionText;
    Button createBtn;
    Button cancelBtn;

    public final static String BASE_URL = "http://172.16.0.83:3000/graphql";
    private ApolloClient apolloClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);

        urlText = findViewById(R.id.url_text);
        descriptionText = findViewById(R.id.description_text);
        createBtn = findViewById(R.id.btn_create);
        cancelBtn = findViewById(R.id.btn_cancel);

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        createBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String url;
                String description;
                url = urlText.getText().toString();
                description = descriptionText.getText().toString();
                createLink(url, description);
            }
        });
    }

    private void createLink(String url, String description) {
        apolloClient = ApolloClient
                .builder()
                .serverUrl(BASE_URL)
                .build();

        apolloClient.mutate(CreateLinkMutation.builder()
                .url(url)
                .description(description)
                .build()).enqueue(new ApolloCall.Callback<CreateLinkMutation.Data>() {
            @Override
            public void onResponse(@Nonnull Response<CreateLinkMutation.Data> response) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(CreateActivity.this, "Link criado com sucesso", Toast.LENGTH_SHORT).show();
                    }
                });

                finish();
            }

            @Override
            public void onFailure(@Nonnull ApolloException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(CreateActivity.this, "Erro ao tentar criar novo link", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });

    }
}
