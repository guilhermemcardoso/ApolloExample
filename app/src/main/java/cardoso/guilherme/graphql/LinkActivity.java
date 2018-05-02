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
import cardoso.guilherme.graphql.api.DeleteLinkMutation;
import cardoso.guilherme.graphql.api.UpdateLinkMutation;

public class LinkActivity extends AppCompatActivity {

    EditText editTextUrl;
    EditText editTextDescription;
    String linkId;

    Button btnDelete;
    Button btnUpdate;

    Bundle extras;

    public final static String BASE_URL = "http://172.16.0.83:3000/graphql";
    private ApolloClient apolloClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_link);

        editTextUrl = findViewById(R.id.link_url);
        editTextDescription = findViewById(R.id.link_description);

        btnDelete = findViewById(R.id.btn_delete);
        btnUpdate = findViewById(R.id.btn_update);

        extras = getIntent().getExtras();
        if(extras != null) {
            linkId = extras.getString("link_id");

            if(linkId == null || linkId.isEmpty())
                finish();

            editTextUrl.setText(extras.getString("link_url"));
            editTextDescription.setText(extras.getString("link_description"));
        }

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String linkUrl = editTextUrl.getText().toString();
                String linkDescription = editTextDescription.getText().toString();
                deleteLink(linkId, linkUrl, linkDescription);
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String linkUrl = editTextUrl.getText().toString();
                String linkDescription = editTextDescription.getText().toString();
                updateLink(linkId, linkUrl, linkDescription);
            }
        });


    }

    private void updateLink(String linkId, String linkUrl, String linkDescription) {

        apolloClient = ApolloClient
                .builder()
                .serverUrl(BASE_URL)
                .build();

        apolloClient.mutate(UpdateLinkMutation.builder()
                .id(linkId)
                .url(linkUrl)
                .description(linkDescription)
                .build()).enqueue(new ApolloCall.Callback<UpdateLinkMutation.Data>() {
            @Override
            public void onResponse(@Nonnull Response<UpdateLinkMutation.Data> response) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(LinkActivity.this, "Link atualizado com sucesso", Toast.LENGTH_SHORT).show();
                    }
                });

                finish();
            }

            @Override
            public void onFailure(@Nonnull ApolloException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(LinkActivity.this, "Erro ao tentar atualizar link", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });

    }

    private void deleteLink(String linkId, String linkUrl, String linkDescription) {

        apolloClient = ApolloClient
                .builder()
                .serverUrl(BASE_URL)
                .build();

        apolloClient.mutate(DeleteLinkMutation.builder()
                .id(linkId)
                .build()).enqueue(new ApolloCall.Callback<DeleteLinkMutation.Data>() {
            @Override
            public void onResponse(@Nonnull Response<DeleteLinkMutation.Data> response) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(LinkActivity.this, "Link removido com sucesso", Toast.LENGTH_SHORT).show();
                    }
                });

                finish();
            }

            @Override
            public void onFailure(@Nonnull ApolloException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(LinkActivity.this, "Erro ao tentar remover link", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });
    }
}
